package bigdata.portal.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.AnalysisMapper;
import bigdata.portal.mapper.CodeMapper;
import bigdata.portal.mapper.DatasetMapper;
import bigdata.portal.mapper.PublicDatasetMapper;

/**
 * 데이터 분석 서비스 클래스 
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
@Service
public class AnalysisService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisService.class);
	
	@Autowired private CodeMapper codeMapper;
	@Autowired private DatasetMapper dataListMapper;	
	@Autowired private PublicDatasetMapper publicDataListMapper;
	@Autowired private AnalysisMapper analysisMapper;
	@Autowired private CodeService codeService;
	@Autowired private DatasetService datasetService;
	@Autowired private HdfsService hdfsService;
	@Autowired private HistoryService historyService;
	
	@Value("${hdfs.url}") String hdfsUrl;
	@Value("${hdfs.homedir}") String hdfsHomedir;
	
	
	public EntityMap selectDataList(HashMap<String, Object> param) {
		return null;
	}

	/**
	 * 데이터 분석 설정 저장
	 * @param param
	 * @return
	 */
	public int insertBdpAnalysisDefault(HashMap<String, Object> param) {
		int anaIdx = 0;
		analysisMapper.insertBdpAnalysis(param);
		anaIdx = Integer.valueOf(param.get("anaIdx").toString());
		return anaIdx;
	}
	
	public List<EntityMap> selectBdpAnalysisList(HashMap<String, Object> param) {

		List<EntityMap> dataList = analysisMapper.selectBdpAnalysisList(param);

		List<CodeDetail> codeDetailList = codeService.selectCodeDetail("A0001");
		
		for(EntityMap row : dataList) {
			for(CodeDetail codeRow : codeDetailList) {
				if(row.getString("anaDataKind").equals(codeRow.getCode())) {
					row.put("anaDataKindCodeNm", codeRow.getCodeNm());
					break;
				}
			}
			
			row.put("anaStatStr", getAnaStatToString(row.getString("anaStat")));
		}
				
		return dataList;
	}
	
	public int selectBdpAnalysisCount(HashMap<String, Object> param) {
		int count = analysisMapper.selectBdpAnalysisCount(param);
		return count;
	}
	
	
	/**
	 * 분석정보를 이용하여 hdfs에 파일정보를 로드 후 삭제
	 * @param anaInfo
	 */
	public boolean hdfsDeleteExecAnalysisResult(EntityMap anaInfo) {
		String anaDataResult = String.valueOf(anaInfo.getString("anaDataResult"));
		String hdfsFilePath = hdfsUrl + "/" + anaDataResult;
		
		boolean delOk = deleteHdfsFile(hdfsFilePath);
		return delOk;
	}
	
	/**
	 * 결합정보를 이용하여 hdfs에 파일정보를 로드 후 삭제
	 * @param anaInfo
	 * @return
	 */
	public boolean hdfsDeleteExecMergeResult(EntityMap anaInfo) {
		String anadmResult = String.valueOf(anaInfo.getString("anadmResult"));
		String hdfsFilePath = hdfsUrl + "/" + anadmResult;
		
		boolean delOk = deleteHdfsFile(hdfsFilePath);
		return delOk;
	}
	
	/**
	 * hdfs의 파일을 삭제
	 * @param hdfsFilePath
	 */
	public boolean deleteHdfsFile(String hdfsFilePath) {
		boolean isExist = hdfsService.isExistsFile(hdfsFilePath);
		boolean delExec = false;
		if(isExist) {
			delExec = hdfsService.deleteFile(hdfsFilePath);
			if(delExec) {
				LOGGER.info("분석목록 삭제시 hdfs 파일삭제 : "+hdfsFilePath);
				return delExec;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 분석리스트 글 삭제
	 * @param idx
	 * @return
	 */
	public int deleteBdpAnalysisRow(String idx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anaIdx", idx);
		int res = analysisMapper.deleteBdpAnalysisRow(param);		
		return res;
	}
	
	/**
	 * 분석리스트 글 삭제
	 * @param idx
	 * @param userId
	 * @return
	 */
	public int deleteBdpAnalysisRow(String idx, String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anaIdx", idx);
		param.put("anaUserId", userId);
		int res = analysisMapper.deleteBdpAnalysisRow(param);
		return res;
	}	
	
	/**
	 * 분석 row정보를 리턴
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpAnalysisRow(HashMap<String, Object> param) {
		EntityMap anaInfo = analysisMapper.selectBdpAnalysisRow(param);
		return anaInfo;
	}
	
	/**
	 * 분석  row정보 갯수 리턴
	 * @param param
	 * @return
	 */
	public int selectBdpAnalysisRowCnt(int anaIdx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anaIdx", anaIdx);
		int cnt = analysisMapper.selectBdpAnalysisRowCnt(param);
		return cnt;
	}
	
	
	public EntityMap selectBdpAnalysisRawdata(String anaIdx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anaIdx", anaIdx);
		EntityMap anaInfo = analysisMapper.selectBdpAnalysisRow(param);
		
		String anaTitle = anaInfo.getString("anaTitle");
		List<Object[]> dataList = null;
		String dsDataType = "CSV";
		int limit = 10000;
		String anaDataResult = anaInfo.getString("anaDataResult");
		String filePath = hdfsUrl + "/" + anaDataResult;

		try {
			hdfsService.initialize();
			hdfsService.fileSystemOpen();
			
			//System.out.println("filePath : " + filePath);
			dataList = datasetService.readDatasetFile(filePath, dsDataType, limit);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EntityMap returnMap = new EntityMap();
		returnMap.put("dataList", dataList);
		returnMap.put("anaTitle", anaTitle);
		
		return returnMap;
	}
	
	
	// 데이터 분석 결합하기 저장
	public int insertBdpAnalysisDataMerge(HashMap<String, Object> param) {
		int anadmIdx = 0;
		analysisMapper.insertBdpAnalysisDataMerge(param);
		anadmIdx = Integer.valueOf(param.get("anadmIdx").toString());
		return anadmIdx;
	}
	
	public int selectBdpAnalysisDataMergeCount(HashMap<String, Object> param) {
		return analysisMapper.selectBdpAnalysisDataMergeCount(param);
	}
	
	public List<EntityMap> selectBdpAnalysisDataMergeList(HashMap<String, Object> param) {
		List<EntityMap> dataList = analysisMapper.selectBdpAnalysisDataMerge(param);
		
		List<CodeDetail> codeDetailList = codeService.selectCodeDetail("A0001");
		
		for(EntityMap row : dataList) {
			row.put("anadmStatStr", getAnadmStatToString(row.getString("anadmMergeStat")));
		}
		
		return dataList;
		
	}
	
	
	public EntityMap selectAnalysisDataMergeRawdata(String datasetTab, String dsId, int limit, String userId) {
		// 데이터
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", dsId);
		param.put("anadmUserId", userId);

		// 상세 정보
		EntityMap dsInfo = analysisMapper.selectBdpAnalysisDataMergeRow(param);
		
		String storageType = "HDFS";
		String dsDataType = "CSV";
		String anadmResult = dsInfo.getString("anadmResult");
		String anadmTitle = dsInfo.getString("anadmTitle");
		
		List<Object[]> dataList = null;
		String[] colHeader = null;
				
		switch (storageType) {
		case "HDFS":
			// TODO: HDFS에 파일로 저장된 데이터일 경우
			String path = hdfsUrl+"/"+anadmResult;

			dataList = datasetService.readDatasetFile(path, dsDataType, limit);
			
			Object[] header = dataList.get(0);
			colHeader = new String[header.length];
			int i = 0;
			for(Object column : header) {
				colHeader[i] = String.valueOf(column);
				i++;
			}  
			
			break;
		case "HIVE":
			// TODO: HIVE에서 데이터 조회
			break;
		case "HBASE":
			// TODO: HBASE에서 데이터 조회
			break;
		}

		EntityMap returnMap = new EntityMap();
		returnMap.put("colName", colHeader);
		returnMap.put("dataList", dataList);
		returnMap.put("filePath", anadmResult);
		returnMap.put("anadmTitle", anadmTitle);
		
		return returnMap;
	}
	
	/**
	 * 결합하기 글 삭제 
	 * @param idx
	 * @return
	 */
	public int deleteBdpAnalysisDataMergeRow(String idx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", idx);
		int res = analysisMapper.deleteBdpAnalysisDataMergeRow(param);
		return res;
	}
	
	/**
	 * 결합하기 글 삭제
	 * @param idx
	 * @param userId
	 * @return
	 */
	public int deleteBdpAnalysisDataMergeRow(String idx, String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", idx);
		param.put("anadmUserId", userId);
		int res = analysisMapper.deleteBdpAnalysisDataMergeRow(param);
		return res;
	}
	
	
	public EntityMap selectBdpAnalysisDataMergeRawdata(String idx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", idx);
		EntityMap anaInfo = analysisMapper.selectBdpAnalysisDataMergeRow(param);
		
		String anadmTitle = anaInfo.getString("anadmTitle");
		List<Object[]> dataList = null;
		String dsDataType = "CSV";
		int limit = 2000;
		String anaDataResult = anaInfo.getString("anadmResult");
		String filePath = hdfsUrl + "/" + anaDataResult;

		try {
			hdfsService.initialize();
			hdfsService.fileSystemOpen();
			
			//System.out.println("filePath : " + filePath);			
			dataList = datasetService.readDatasetFile(filePath, dsDataType, limit, true);
			
			//hdfsService.fileSystemClose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EntityMap returnMap = new EntityMap();
		returnMap.put("dataList", dataList);
		returnMap.put("anadmTitle", anadmTitle);
		
		return returnMap;
	}
	
	
	/**
	 * 결합 데이터의 행 정보를 리턴
	 * @param anadmIdx
	 * @return
	 */
	public EntityMap selectBdpAnalysisDataMergeRow(String anadmIdx) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", anadmIdx);
		
		EntityMap dsInfo = analysisMapper.selectBdpAnalysisDataMergeRow(param);
		
		return dsInfo;
	}
	
	/**
	 * 결합 데이터의 행 정보를 리턴
	 * @param anadmIdx
	 * @param anadmUserId
	 * @return
	 */
	public EntityMap selectBdpAnalysisDataMergeRow(String anadmIdx, String anadmUserId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", anadmIdx);
		param.put("anadmUserId", anadmUserId);
		
		EntityMap dsInfo = analysisMapper.selectBdpAnalysisDataMergeRow(param);
		
		return dsInfo;
	}
	
	
	public String getAnaStatToString(String anaStat) {
		String rAnaStat = "";
		switch(anaStat) {
		case "Y":
			rAnaStat = "분석완료";
			break;
			
		case "N":
			rAnaStat = "분석중";
			break;
			
		case "R":
			rAnaStat = "분석중";
			break;
			
		case "E":
			rAnaStat = "오류";
			break;
		}
		
		return rAnaStat;
	}
	
	public String getAnadmStatToString(String anadmStat) {
		String result = "";
		switch(anadmStat) {
		case "Y":
			result = "결합완료";
			break;
		case "N":
			result = "결합중";
			break;
			
		case "R":
			result = "결합중";
			break;
			
		case "E":
			result = "오류";
			break;
		}
		
		return result;
	}
}
