package bigdata.portal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;
import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.DatasetMapper;
import bigdata.portal.mapper.ElasticDatasetMapper;

/**
 * 시각화 생성 및 리스트 서비스 클래스
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 8.     JHY          최초 생성
 *      </pre>
 *
 * @since 2018. 10. 8.
 */
/**
 *
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 31.     JHY          최초 생성
 *      </pre>
 *
 * @since 2018. 10. 31.
 */
@Service
public class DatasetService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatasetService.class);

	@Autowired private HiveService hiveService;
	@Autowired private HdfsService hdfsService;

	@Autowired private DatasetMapper datasetMapper;
	@Autowired private ElasticDatasetMapper elasticDatasetMapper;
	@Autowired private ElasticService elasticService;

	@Autowired
	private JejuWinterService jejuWinterService;


	@Value("${hdfs.url}") String hdfsUrl;
	@Value("${hdfs.dataset.sta}") String staHdfsPath;

	public List<Map<String, Object>> indexSearch(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {

		List<Map<String, Object>> sources = elasticDatasetMapper.indexSearch(param).getSources();
		return sources;
	}

	public List<Map<String, Object>> staIndexSearch(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {

		List<Map<String, Object>> sources = elasticDatasetMapper.staIndexSearch(param).getSources();
		return sources;
	}

	public List<Object> getStaYears(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {

		List<Object> staYearList = new ArrayList<Object>();
		ElasticResultMap resultMap = elasticDatasetMapper.getStaYears(param);

		List<Map<String, Object>> buckets = jejuWinterService.getBuckets(resultMap.getAggregations());

		if(buckets == null) {
			return staYearList;
		}

		for(Map<String, Object> inMap : buckets) {
			staYearList.add(inMap.get("key"));
		}

		return staYearList;
	}

	/**
	 * 데이터 목록 상세보기에서 RAW데이터 표시
	 * @param datasetTab
	 * @param dsId
	 * @param limit
	 * @param prdDe
	 * @return
	 */
	public EntityMap selectDatasetRawdata(String datasetTab, String dsId, int limit, String prdDe) {

		String path = "";

		// 데이터
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);

		// 상세 정보
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);

		String storageType = dsInfo.getStringToUpperCase("dsStorageType", "");
		String dsDataType = dsInfo.getStringToUpperCase("dsDataType", "");
		String dsDatasetPath = dsInfo.getString("dsDatasetPath");
		String htblId = dsInfo.getString("htblId");
		String dsFileName = dsInfo.getString("dsFileName");
		String filePath = "";

		List<Object[]> dataList = null;
		String[] colHeader = null;
		List<HashMap<String, String>> colEngInfoList = new ArrayList<HashMap<String, String>>();

		switch (storageType) {
		case "HDFS":
			if("STA".equals(datasetTab)) {
				// hdfs에서 가져오도록 변경
				path = hdfsUrl+staHdfsPath+"/"+htblId+"/"+prdDe+".csv";
				filePath = staHdfsPath+"/"+htblId+"/"+prdDe+".csv";
				dataList = readDatasetFile(path, "CSV", 10000);
				break;
			}
			// TODO: HDFS에 파일로 저장된 데이터일 경우
			path = dsDatasetPath + "/" + dsFileName;
			dataList = readDatasetFile(path, dsDataType, limit);

			break;
		case "HIVE":

				Map<String, String[]> metaInfo = hiveService.descTable(htblId);
				dataList = hiveService.selectTableArray(htblId, limit, true);
				Object[] header = dataList.get(0);
				colHeader = new String[header.length];
				int i = 0;
				for(Object column : header) {
					String[] colInfo = metaInfo.get(column);
					String colComment = (colInfo[2] == null || colInfo[2].equals("") ? colInfo[0] : colInfo[2]).trim();
					colHeader[i] = colComment;	// hive 컬럼 코멘트

					HashMap<String, String> colEngInfoMap = new HashMap<String, String>();
					colEngInfoMap.put("kor", colComment);
					colEngInfoMap.put("eng", colInfo[0].toLowerCase());

					colEngInfoList.add(colEngInfoMap);
					i++;
				}

				dataList.set(0, colHeader);

			break;
		case "HBASE":
			// TODO: HBASE에서 데이터 조회
			break;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeader);
		returnMap.put("colEngInfoList", colEngInfoList);
		returnMap.put("dataList", dataList);
		returnMap.put("htblId", htblId);
		returnMap.put("filePath", filePath);

		return returnMap;
	}

	/**
	 * 데이터 목록 상세보기에서 hive테이블에 저장된 RAW데이터 표시
	 * @param datasetTab
	 * @param dsId
	 * @param limit
	 * @param prdDe
	 * @param startNum
	 * @param endNum
	 * @return
	 */
	public EntityMap selectDatasetHiveRawData(String datasetTab, String dsId, String prdDe, int startNum, int endNum) {
		String path = "";

		// 데이터
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);

		// 상세 정보
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);

		String storageType = dsInfo.getStringToUpperCase("dsStorageType", "");
		String dsDataType = dsInfo.getStringToUpperCase("dsDataType", "");
		String dsDatasetPath = dsInfo.getString("dsDatasetPath");
		String htblId = dsInfo.getString("htblId");
		String dsFileName = dsInfo.getString("dsFileName");
		String filePath = "";

		List<Object[]> dataList = null;
		String[] colHeader = null;
		List<HashMap<String, String>> colEngInfoList = new ArrayList<HashMap<String, String>>();

		List<Object[]> newDataList = new ArrayList<Object[]>();


		switch (storageType) {
		case "HIVE":

				Map<String, String[]> metaInfo = hiveService.descTable(htblId);
				//dataList = hiveService.selectTableArray(htblId, limit, true);
				dataList = hiveService.selectTableArray(htblId, startNum, endNum, false);
				// 별도로 header값을 가져와서 출력을 하여야 함

				Object[] header = metaInfo.keySet().toArray();
				colHeader = new String[header.length];
				int i = 0;

				for(Object column : header) {
					String[] colInfo = metaInfo.get(column);

					String colComment = (colInfo[2] == null || colInfo[2].equals("") ? colInfo[0] : colInfo[2]).trim();
					colHeader[i] = colComment;	// hive 컬럼 코멘트

					HashMap<String, String> colEngInfoMap = new HashMap<String, String>();
					colEngInfoMap.put("kor", colComment);
					colEngInfoMap.put("eng", colInfo[0].toLowerCase());

					colEngInfoList.add(colEngInfoMap);
					i++;
				}

				newDataList = new ArrayList<Object[]>();

				newDataList.add(colHeader);
				newDataList.addAll(dataList);


			break;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeader);
		returnMap.put("colEngInfoList", colEngInfoList);
		returnMap.put("dataList", newDataList);
		returnMap.put("htblId", htblId);
		returnMap.put("filePath", filePath);

		return returnMap;
	}

	/**
	 * es dataset info를 가져온다.
	 * @param dsId
	 * @return
	 */
	public EntityMap selectEsDatasetInfo(String dataId) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dataId);
		// 상세 정보
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);

		String esColumns = dsInfo.getString("esColumns");
		String esColumnsKorNm = dsInfo.getString("esColumnsKorNm");
		String esOrderBy = dsInfo.getString("esOrderBy");
		int esIndexDocTotcnt = dsInfo.getInteger("esIndexDocTotcnt");

		String[] esColHeader = null;
		String[] esColHeaderKor = null;
		esColHeader = esColumns.split(",");
		esColHeaderKor = esColumnsKorNm.split(",");

		List<HashMap<String, String>> esColEngInfoList = new ArrayList<HashMap<String, String>>();
		int i = 0;
		for(String esColumn : esColHeader) {
			HashMap<String, String> esColEngInfoMap = new HashMap<String, String>();
			esColEngInfoMap.put("kor", esColHeaderKor[i]);
			esColEngInfoMap.put("eng", esColumn);
			esColEngInfoList.add(esColEngInfoMap);
			i++;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("esColName", esColHeader);
		returnMap.put("esColEngInfoList", esColEngInfoList);
		returnMap.put("esColHeader", esColHeader);
		returnMap.put("esOrderBy", esOrderBy);
		returnMap.put("esIndexDocTotcnt", esIndexDocTotcnt);

		return returnMap;
	}


	/**
	 * 데이터 목록 상세보기에서 datasetTab가 BDS, PRI인 경우 DB에 저장된 RAW데이터 표시
	 * @return
	 */
	public EntityMap selectDatasetDbRawData(String datasetTab, String dsId, int startNum, int endNum) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		// 상세 정보
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);
		String htblId = dsInfo.getString("htblId"); // 테이블명
		String columns = dsInfo.getString("columns");
		String columnsKorNm = dsInfo.getString("columnsKorNm");
		String htblOrderBy = dsInfo.getString("htblOrderBy");

		List<EntityMap> dataList = null;
		String[] colHeader  = null;
		String[] colHeaderKor = null;
		colHeader = columns.split(",");
		colHeaderKor = columnsKorNm.split(",");

		HashMap<String, Object> param1 = new HashMap<String, Object>();

		param1.put("tableNm", htblId);
		param1.put("selectColumns", columns);
		param1.put("orderbyString", htblOrderBy);
		param1.put("startNum", startNum);
		param1.put("endNum", endNum);

		dataList = datasetMapper.selectTableDatasetList(param1);
		int totalCount = 0;
		totalCount = datasetMapper.getTableDatasetTotalCount(param1);

		List<HashMap<String, String>> colEngInfoList = new ArrayList<HashMap<String, String>>();
		int i = 0;
		for(String column : colHeader) {
			HashMap<String, String> colEngInfoMap = new HashMap<String, String>();
			colEngInfoMap.put("kor", colHeaderKor[i].toString());
			colEngInfoMap.put("eng", column);
			colEngInfoList.add(colEngInfoMap);
			i++;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeader);
		returnMap.put("colEngInfoList", colEngInfoList);
		returnMap.put("dataList", dataList);
		returnMap.put("htblId", htblId);
		returnMap.put("colHeader", columns);
		returnMap.put("totalCount", totalCount);
		returnMap.put("orderbyString", htblOrderBy);

		return returnMap;

	}


	/**
	 * 빅데이터셋 조건검색 결과값 데이터 리턴
	 * @param datasetTab
	 * @param dsId
	 * @param where
	 * @param whereValues
	 * @param limit
	 * @return
	 */
	public EntityMap selectDatasetRawdataCondition(String datasetTab, String dsId, String wheres, int limit) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);

		// 상세 정보
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);

		String htblId = dsInfo.getString("htblId");
		String columns = dsInfo.getString("columns");
		String columnsKorNm = dsInfo.getString("columnsKorNm");
		String htblOrderBy = dsInfo.getString("htblOrderBy");

		List<EntityMap> dataList = null;
		String[] colHeader = null;
		String[] colHeaderKor = null;
		colHeader = columns.split(",");
		colHeaderKor = columnsKorNm.split(",");

		// 컬럼정보(한글,영문)
		List<HashMap<String, String>> colEngInfoList = new ArrayList<HashMap<String, String>>();

		HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("tableNm", htblId);
		param1.put("selectColumns", columns);
		param1.put("orderbyString", htblOrderBy);
		param1.put("startNum", 0);
		param1.put("endNum", limit);
		param1.put("whereStr", wheres);

		dataList = datasetMapper.selectTableDatasetList(param1);

		int i = 0;
		for(String column : colHeader) {

			HashMap<String, String> colEngInfoMap = new HashMap<String, String>();
			colEngInfoMap.put("kor", colHeaderKor[i].toString());
			colEngInfoMap.put("eng", column);

			colEngInfoList.add(colEngInfoMap);
			i++;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeader);
		returnMap.put("colEngInfoList", colEngInfoList);
		returnMap.put("dataList", dataList);
		returnMap.put("htblId", htblId);
		returnMap.put("colHeader", columns);

		return returnMap;
	}

	/**
	 * 파일데이터 읽기(XLS, XLSX, CSV 만 지원)
	 * @param filePath
	 * @param dataType
	 * @param limit
	 * @return
	 */
	public List<Object[]> readDatasetFile(String filePath, String dataType, int limit) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		String dataTypeUpper = dataType.toUpperCase();

		try {
			// 파일이 XLS일 경우
			if (dataTypeUpper.equals("XLS") || dataTypeUpper.equals("XLSX")) {
				InputStream inputStream = null;
				Workbook workbook = null;

				try {
					// 하둡에 저장된 파일 읽기
					if (filePath.toLowerCase().startsWith("hdfs://")) {
						inputStream = hdfsService.fileStreamOpen(filePath);
						workbook = WorkbookFactory.create(inputStream);
					} else {
						inputStream = new FileInputStream(new File(filePath));
						workbook = WorkbookFactory.create(inputStream);
					}

					// One Sheet
					Sheet sheet = workbook.getSheetAt(0);
					for (Row row : sheet) {
						ArrayList<Object> cellTmp = new ArrayList<Object>();
						for (Cell cell : row) {

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								cellTmp.add(cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								cellTmp.add(cell.getRichStringCellValue().getString());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									cellTmp.add(cell.getDateCellValue());
								} else {
									cellTmp.add(cell.getNumericCellValue());
								}
								break;
							case Cell.CELL_TYPE_FORMULA:
								cellTmp.add(cell.getCellFormula());
								break;
							}
						}

						dataList.add(cellTmp.toArray(new Object[cellTmp.size()]));
						if (limit > 0 && dataList.size() >= limit)
							break;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					e.printStackTrace();
				} finally {
					if (inputStream != null)
						inputStream.close();
					//if (workbook != null)
					//	workbook.close();
				}
			// 파일이 CSV일 경우
			} else if (dataTypeUpper.equals("CSV")) {
				CSVReader reader = null;
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;

				try {
					// 하둡에 저장된 파일 읽기
					if (filePath.toLowerCase().startsWith("hdfs://")) {
						inputStream = hdfsService.fileStreamOpen(filePath);
						inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
						reader = new CSVReader(inputStreamReader);
					} else {
						//reader = new CSVReader(new FileReader(filePath));
						String encodingType = readEncoding(new File(filePath));
						InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), encodingType);
						reader = new CSVReader(isr);
					}

					String[] line;
					while ((line = reader.readNext()) != null) {
						dataList.add(line);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				} finally {
					if (inputStream != null)
						inputStream.close();
					if(inputStreamReader != null)
						inputStreamReader.close();
					if (reader != null)
						reader.close();
				}

			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return dataList;
	}


	/**
	 * 파일데이터 읽기(XLS, XLSX, CSV 만 지원)
	 * @param filePath
	 * @param dataType
	 * @param limit
	 * @param limitCut
	 * @return
	 */
	public List<Object[]> readDatasetFile(String filePath, String dataType, int limit, boolean limitCut) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		String dataTypeUpper = dataType.toUpperCase();

		try {
			// 파일이 XLS일 경우
			if (dataTypeUpper.equals("XLS") || dataTypeUpper.equals("XLSX")) {
				InputStream inputStream = null;
				Workbook workbook = null;

				try {
					// 하둡에 저장된 파일 읽기
					if (filePath.toLowerCase().startsWith("hdfs://")) {
						inputStream = hdfsService.fileStreamOpen(filePath);
						workbook = WorkbookFactory.create(inputStream);
					} else {
						inputStream = new FileInputStream(new File(filePath));
						workbook = WorkbookFactory.create(inputStream);
					}

					// One Sheet
					Sheet sheet = workbook.getSheetAt(0);
					for (Row row : sheet) {
						ArrayList<Object> cellTmp = new ArrayList<Object>();
						for (Cell cell : row) {

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								cellTmp.add(cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								cellTmp.add(cell.getRichStringCellValue().getString());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									cellTmp.add(cell.getDateCellValue());
								} else {
									cellTmp.add(cell.getNumericCellValue());
								}
								break;
							case Cell.CELL_TYPE_FORMULA:
								cellTmp.add(cell.getCellFormula());
								break;
							}
						}

						dataList.add(cellTmp.toArray(new Object[cellTmp.size()]));
						if (limit > 0 && dataList.size() >= limit)
							break;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					e.printStackTrace();
				} finally {
					if (inputStream != null)
						inputStream.close();
					//if (workbook != null)
					//	workbook.close();
				}
			// 파일이 CSV일 경우
			} else if (dataTypeUpper.equals("CSV")) {
				CSVReader reader = null;
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;

				try {
					// 하둡에 저장된 파일 읽기
					if (filePath.toLowerCase().startsWith("hdfs://")) {
						inputStream = hdfsService.fileStreamOpen(filePath);
						inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

						// 큰파일용량인 경우 limit까지만 읽고 csv로 제공
						if(limitCut == true) {
							reader = new CSVReader(inputStreamReader);
						} else {
							reader = new CSVReader(inputStreamReader);
						}
					} else {
						//reader = new CSVReader(new FileReader(filePath));
						String encodingType = readEncoding(new File(filePath));
						InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), encodingType);
						reader = new CSVReader(isr);
					}

					int readerCnt = 0;
					String[] line;
					while ((line = reader.readNext()) != null) {
						dataList.add(line);

						// 파일의 용량이 클 경우 웹에서 보여줄수 없으므로 10000건까지만 나타나도록 처리함.
						readerCnt++;
						if(readerCnt == limit) break;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				} finally {
					if (inputStream != null)
						inputStream.close();
					if(inputStreamReader != null)
						inputStreamReader.close();
					if (reader != null)
						reader.close();
				}

			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return dataList;
	}



	/**
	 * 통계 테이블 생성
	 *
	 * @param htblId
	 * @param limit
	 * @return
	 */
	public EntityMap createStaTable(String htblId, int limit) {
		// 통계 메타데이터 조회
		HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("tblId", htblId);
		//List<EntityMap> metaInfo = kassStaMapper.selectStdCodeInfo(param1);

		List<Object[]> dataList = hiveService.selectTableArray(htblId, limit, false);
		String[] colId = null;
		String[] colName = null;


		// TODO : OUTPUT 형태로 데이터셋 만들기

		// col_name, data_type, comment
		// metaInfo = new ArrayList<EntityMap>();
		// for(EntityMap meta : staMeta) {
		// EntityMap _meta = new EntityMap();
		// String type = _meta.getString("cdTpSe");
		//
		// // 계층형
		// switch(type) {
		// case "I" :
		// break;
		// case "C" :
		// case "B" :
		// String rowIndex = _meta.getString("objVarId") + _meta.getString("itmId");
		// _meta.getString("upItmId");
		// break;
		// }
		//
		// _meta.put("colName", _meta.get("colName"));
		// _meta.put("dataType", _meta.get("dataType"));
		// _meta.put("colName", _meta.get("colName"));
		//
		// metaInfo.add(_meta);
		// }



		EntityMap returnMap = new EntityMap();
		returnMap.put("colId", colId);
		returnMap.put("colName", colName);
		returnMap.put("dataList", dataList);

		return returnMap;
	}

	// 통계 데이터 HIVE대신 DB에서 가져오기 위해 사용(임시)
	public EntityMap createStaTableDb(String tblId, String prdDe) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tblId", tblId);
		param.put("prdDe", prdDe);

		List<String> dataList = makeStaTable(param);
		String[] sample = dataList.get(0).split("\\|");
		int length = sample.length - 1; // value값은 빼고

		Object[] colHeaderAry= new Object[length+1];
		List<Object[]> staList = new ArrayList<Object[]>();

		// header column set
		int i = 0;
		for(i = 0; i<length; i++) {
			colHeaderAry[i] = "COLUMN"+i;
		}
		colHeaderAry[i] = "VALUE";
		staList.add(colHeaderAry);

		for(String str : dataList) {
			Object[] staColAry = str.split("\\|");
			staList.add(staColAry);
		}


		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeaderAry);
		returnMap.put("dataList", staList);
		returnMap.put("htblId", tblId);
		return returnMap;

	}


	/**
	 * 빅데이터셋 목록 개수를 리턴한다.
	 *
	 * @param param
	 * @return
	 */
	public int getTotalCount(HashMap<String, Object> param) {
		int totalCount = datasetMapper.getTotalCount(param);
		return totalCount;
	}


	/**
	 * 빅데이터 종별/데이터셋별 개수를 리턴한다.
	 *
	 * @param param
	 * @return
	 */
	public EntityMap getTypeCount(HashMap<String, Object> param) {
		EntityMap entityMap = datasetMapper.getTypeCount(param);
		return entityMap;
	}


	/**
	 * 빅데이터셋 리스트를 리턴한다.
	 *
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public List<EntityMap> selectDatasetList(HashMap<String, Object> param) throws InstantiationException, IllegalAccessException, IOException {
		List<EntityMap> dataList = datasetMapper.selectDatasetList(param);

		for(EntityMap data : dataList) {
			//공간데이터일 경우 맵보기 경로 추가
			String dsId = data.getString("dsId");
			data.put("mapViewUrl", getMapViewUrl(dsId));

			// Mysql에 있는 데이터 건수 대신 엘라스틱 서치의 실시간 Data 건수를 가져옵니다.
			String esIndexNm = data.getString("esIndexNm")+"*";
			if(StringUtils.isNotEmpty(esIndexNm)) {

				ElasticResultMap esResultMap = elasticService.postCount(esIndexNm, "");

				Map<String,Object> esData = new Gson().fromJson(esResultMap.getRawString(), Map.class);
				Long esIndexDocTotcnt = Math.round(Double.valueOf("" + esData.get("count")));
				data.put("esIndexDocTotcnt", esIndexDocTotcnt);
			}
		}

		return dataList;
	}

	/**
	 * 빅데이터셋목록, 빅데이터셋 row 정보를 리턴한다.
	 *
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpDataView(HashMap<String, Object> param) {
		EntityMap row = datasetMapper.selectBdpDataView(param);

		String dsId = row.getString("dsId");
		String esIndexNm = row.getString("esIndexNm")+"*";

		row.put("mapViewUrl", getMapViewUrl(dsId));
		row.put("esIndexDocTotcnt",getEsIndexDocTotcnt(esIndexNm));
		return row;
	}

	/**
	 * 빅데이터셋목록 조회수 업데이트
	 * @param param
	 * @return
	 */
	public void updateBdpDataViewHits(HashMap<String, Object> param) {
		int res = 0;
		res = datasetMapper.updateBdpDataViewHits(param);
	}

	// sample
	public List<EntityMap> selectSampleMarkerPriceRawData(HashMap<String, Object> param) {
		List<EntityMap> dataList = datasetMapper.selectSampleMarketPriceRawData(param);

		return dataList;
	}

	/**
	 * 통계 테이블 생성
	 *
	 * @param param
	 * @return
	 */
	public List<String> makeStaTable(HashMap<String, Object> param) {

		List<String> staDataList = new ArrayList<String>();

		// OV_L(i)_ID가 몇개인지 계산
		EntityMap ov = datasetMapper.selectBdpStdDimDtOvCount(param);

		List<HashMap<String, String>> ovAry = new ArrayList<HashMap<String, String>>();
		for(int i=0; i < 8; i++) {
			if( !ov.getString("ovL"+(i+1)+"Cls").equals(".") ) {
				HashMap<String, String> ovMap = new HashMap<String, String>();
				ovMap.put("ov", ov.getString("ovL"+(i+1)+"Cls"));
				ovAry.add(ovMap);
			}
		}

		int ovLength = ovAry.size();

		List<EntityMap> dimDtList = datasetMapper.selectBdpStdDimDt(param);
		for(EntityMap dimDtRow : dimDtList) {
			HashMap<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("tblId", param.get("tblId").toString());

			for(int oi = 0; oi < ovLength; oi++) {
				pMap.put("itmId"+(oi+1), dimDtRow.getString("ovL"+(oi+1)+"Id").toString());
			}

			// 상위코드를 포함하여 분류값을 리턴
			List<EntityMap> codeInfoList = datasetMapper.selectBdpStdCodeInfoWhereItmId(pMap);

			String staCodeCdNm = "";

			for(EntityMap codeInfoRow : codeInfoList) {
				if(staCodeCdNm.length() > 0) {
					staCodeCdNm += "|"+codeInfoRow.getString("cdNm");
				} else {
					staCodeCdNm += codeInfoRow.getString("cdNm");
				}
			}

			if(!dimDtRow.getString("charCdNm").equals("")) {
				staCodeCdNm += "|"+dimDtRow.getString("charCdNm");
			}
			staCodeCdNm += "|"+dimDtRow.getString("dtvalCo");

			staDataList.add(staCodeCdNm);
		}

		return staDataList;

	}

	/**
	 * prdDe의 최근 기간의 값을 리턴한다.
	 * @param param
	 * @return
	 */
	public String selectBdpStdDimDtPreDe(HashMap<String, Object> param) {
		EntityMap map = datasetMapper.selectBdpStdDimDtPreDe(param);
		return map.get("prdDe").toString();
	}

	/**
	 * 통계데이터 차원수치 테이블에서 중복제거한 시점값을 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaPrdDeList(HashMap<String, Object> param) {
		List<EntityMap> map = datasetMapper.selectStaPrdDeList(param);
		return map;
	}

	/**
	 * 통계표 상단 타이틀 JSON 호출
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectStaLeftTopNames(HashMap<String, Object> param) {
		List<EntityMap> objVarIdList = datasetMapper.selectStaObjVarId(param);

		HashMap<String, Object> in = new HashMap<String, Object>();
		in.put("tblId", param.get("tblId").toString());
		in.put("objVarIdList", objVarIdList);

		List<EntityMap> dataList = datasetMapper.selectStaLeftTopNames(in);

		List<EntityMap> newDataList = new ArrayList<EntityMap>();
		for(EntityMap row : objVarIdList) {
			for(EntityMap row1 : dataList) {
				if(row.getString("objVarId").equals(row1.getString("objVarId"))) {
					EntityMap item = new EntityMap();
					item.put("cdNm", row1.getString("cdNm"));
					newDataList.add(item);
				}
			}
		}

		return newDataList;
	}


	/**
	 * 통계표정보 및 주석 자동수집통계표 정보를 리턴한다
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpStdStblInfo(HashMap<String, Object> param) {
		List<EntityMap> dataList = datasetMapper.selectBdpStdStblInfo(param);
		return dataList;
	}


	/**
	 * 파일 인코딩 타입을 리턴
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String readEncoding(File file) throws IOException {
		byte[] buf = new byte[4096];
		java.io.FileInputStream fis = new java.io.FileInputStream(file);
		UniversalDetector detector = new UniversalDetector(null);
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		buf = null;
		fis.close();
		return encoding == null?"UTF-8":encoding;
	}


	/**
	 * hdfs로 부터 파일리스트 정보를 리턴
	 * @param tblId
	 * @return
	 */
	public List<HashMap<String, String>> getPrdDeList(String tblId) {
		String hdfsPathStr = hdfsUrl + staHdfsPath + "/" + tblId;

		List<HashMap<String, String>> fileList = null;
		try {
			hdfsService.nonUserfileSystemOpen();

			fileList = hdfsService.fileStreamList(hdfsPathStr, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileList;
	}


	/**
	 * 데이터셋 데이터 개방여부 업데이트
	 * @param dsId
	 * @param val
	 * @param target
	 * @return
	 */
	public int openRangeUpdate(int dsId, String val, String target) {
		int res = 0;

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		param.put("val", val);

		if(target.equals("SOURCE")) {
			res = datasetMapper.updateOpenRangeSource(param);
		} else if(target.equals("ANALYSIS")) {
			res = datasetMapper.updateOpenRangeAnalysis(param);
		} else if(target.equals("DATAOPEN")) {
			res = datasetMapper.updateOpenRangeDataOpen(param);
		}

		return res;
	}

	/**
	 * 기관정보를 리턴
	 * @return
	 */
	public List<EntityMap> getBdpOrganization() {
		int res = 0;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("orgUseAt", "Y");

		List<EntityMap> rows = datasetMapper.selectBdpOrganization(param);
		return rows;
	}

	/**
	 * 데이터 목록명 리스트 정보를 리턴
	 * @param dlUseAt
	 * @return
	 */
	public List<EntityMap> selectBdpDataList(String dlUseAt) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dlUseAt", dlUseAt);

		List<EntityMap> rows = datasetMapper.selectBdpDataList(param);

		return rows;
	}

	/**
	 * 데이터 목록명 리스트 정보를 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpDataList(HashMap<String, Object> param) {
		List<EntityMap> rows = datasetMapper.selectBdpDataList(param);
		return rows;
	}

	/**
	 * 데이터 목록의 행 정보 리턴
	 * @param dlUseAt
	 * @param dlId
	 * @return
	 */
	public EntityMap selectBdpDataListRow(String dlUseAt, String dlId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dlUseAt", dlUseAt);
		param.put("dlId", dlId);

		EntityMap rows = datasetMapper.selectBdpDataListRow(param);

		return rows;
	}

	/**
	 * 데이터 목록의 행 정보 리턴 Overloading
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpDataListRow(HashMap<String, Object> param) {
		EntityMap rows = datasetMapper.selectBdpDataListRow(param);
		return rows;
	}


	/**
	 * 데이터셋 정보를 수정
	 * @param param
	 * @return
	 */
	public int updateBdpDatasetRow(HashMap<String, Object> param) {
		int res = 0;
		res = datasetMapper.updateBdpDatasetRow(param);
		return res;
	}

	/**
	 * 데이터셋 사용여부를 변경
	 * @param param
	 * @return
	 */
	public int updateDsUseAt(HashMap<String, Object> param) {
		int res = 0;
		res = datasetMapper.updateDsUseAt(param);
		return res;
	}

	/**
	 * 데이터목록 개수를 리턴
	 * @param param
	 * @return
	 */
	public int getTotalCountDataList(HashMap<String, Object> param) {
		int cnt = 0;
		cnt = datasetMapper.getTotalCountDataList(param);
		return cnt;
	}

	/**
	 * 데이터목록 사용여부를 변경
	 * @param param
	 * @return
	 */
	public int updateDlUseAt(HashMap<String, Object> param) {
		int res = 0;
		res = datasetMapper.updateDlUseAt(param);
		return res;
	}

	/**
	 * 데이터목록 정보를 수정
	 * @param param
	 * @return
	 */
	public int updateBdpDataListRow(HashMap<String, Object> param) {
		int res = 0;
		res = datasetMapper.updateBdpDataListRow(param);
		return res;
	}

	public EntityMap selectBdpDataSetRow(HashMap<String, Object> param) {
		EntityMap row = datasetMapper.selectBdpDataSetRow(param);
		return row;
	}

	/**
	 * 빅데이터셋 연관 데이터 리스트 리
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectAssociativeDataset(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return datasetMapper.selectAssociativeDataset(param);
	}

	/**
	 * 공간데이터 -> 맵보기 -> 피노지오 클릭 시 url이 한개에서 여러개 분기되어 아래 코드 추가
	 * */
	private String getMapViewUrl(String dsId) {
		String mapViewUrl = "";

		switch(dsId) {
		case "511" : mapViewUrl="http://mango.iptime.org:3413/share/maps-Dz8z2zaVKZ/share=maps"; break;
		case "512" : mapViewUrl="http://mango.iptime.org:3413/share/maps-RWVoOBNzPp/share=maps"; break;
		case "513" : mapViewUrl="http://mango.iptime.org:3413/share/maps-Y5lLsPJhxh/share=maps"; break;
		}

		return mapViewUrl;

	}

	/**
	 * 엘라스틱 서치 -> 인데스 데이터 전체 count 구하기
	 * @param esIndexNm 인덱스명
	 * @return 개수
	 * */
	public Long getEsIndexDocTotcnt(String esIndexNm) {
		return getEsIndexDocTotcnt(esIndexNm , -1L);
	}

	/**
	 * 엘라스틱 서치 -> 인데스 데이터 전체 count 구하기
	 * @param esIndexNm 인덱스명
	 * @param defulatVal 인덱스를 찾기 못했을 때 기본 값
	 * @return 개수
	 * */
	public Long getEsIndexDocTotcnt(String esIndexNm, Long defulatVal) {
		Long cnt = defulatVal;
		try {
			if(StringUtils.isNotEmpty(esIndexNm)) {

				ElasticResultMap esResultMap;
					esResultMap = elasticService.postCount(esIndexNm, "");

				Map<String,Object> esData = new Gson().fromJson(esResultMap.getRawString(), Map.class);
				cnt = Math.round(Double.valueOf("" + esData.get("count")));
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}



}
