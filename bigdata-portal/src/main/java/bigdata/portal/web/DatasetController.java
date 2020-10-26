package bigdata.portal.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.cmm.util.CommonUtil;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.DatasetService;
import bigdata.portal.service.HdfsService;
import bigdata.portal.service.HistoryService;
import bigdata.portal.service.HiveService;
import bigdata.portal.service.PublicDatasetService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 보유데이터 리스트 컨트롤러
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
@Controller
@RequestMapping("/bdp/dataset")
public class DatasetController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatasetController.class);
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private DatasetService datasetService;
	@Autowired private PublicDatasetService publicDatasetService;
	@Autowired private HiveService hiveService;
	@Autowired private HdfsService hdfsService;
	@Autowired private EgovUserDetailsService egovUserDetailsService;
	@Autowired private HistoryService historyService;
	@Autowired CsvDownloadService csvDownloadService;

    // 첨부파일 위치 지정
    private final String uploadDir = EgovProperties.getProperty("bigdata.dataset.upload");
    // 첨부 최대 파일 크기 지정
    private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)

	/**
	 * 데이터 목록 화면
	 * @param pageIndex
	 * @param searchCondition
	 * @param searchKeyword
	 * @param searchType1
	 * @param searchType2
	 * @param searchType3
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="list.do", method={RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
							@RequestParam(value="searchCondition", required=false, defaultValue="") String searchCondition,
							@RequestParam(value="searchKeyword", required=false, defaultValue="") String searchKeyword,
							@RequestParam(value="searchType1", required=false, defaultValue="BDS") String searchType1,
							@RequestParam(value="searchType2", required=false, defaultValue="") String searchType2,
							@RequestParam(value="searchType3", required=false, defaultValue="") String searchType3,
							@RequestParam(value="spceDataSe", required=false, defaultValue="N") String spceDataSe,
							Model model
						) throws Exception {

		String searchType1Upper = searchType1.toUpperCase();

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsUseAt", "Y");

		//검색
		if(!searchCondition.isEmpty()) {
			param.put("searchCondition", searchCondition);
		}
		if(!searchKeyword.isEmpty()) {
			param.put("searchKeyword", searchKeyword);
		}
		if(!searchType1Upper.isEmpty()) {
			param.put("searchType1", searchType1Upper);
		}
		if(!searchType2.isEmpty()) {
			param.put("searchType2", searchType2);
		}
		if(!searchType3.isEmpty()) {
			param.put("searchType3", searchType3);
		}
		if(!spceDataSe.isEmpty()) {
			param.put("spceDataSe", spceDataSe);
		}

		// 원천, 분석, 개방에 따른 분류
		if(isRoleAdmin()) {
			param.put("dsSourceOpen", "Y");
		} else if(isRoleAna()) {
			param.put("dsAnalysisOpen", "Y");
		} else if(isRoleUser()) {
			param.put("dsDataOpen", "Y");
		} else {
			param.put("dsDataOpen", "Y");
		}


		int totalCount = datasetService.getTotalCount(param);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);

		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> datasetList = datasetService.selectDatasetList(param);

		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchType1", searchType1Upper);
		model.addAttribute("searchType2", searchType2);
		model.addAttribute("searchType3", searchType3);

		model.addAttribute("dataList", datasetList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);

		String returnPage = "";
		if(searchType1Upper.equals("BDS")) {
			returnPage = "bigdata/portal/dataset/datasetList";
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 1);

			// 공간데이터
			if(spceDataSe.equals("Y")) {
				returnPage = "bigdata/portal/dataset/spceList";
				model.addAttribute("menuNum", 1);
				model.addAttribute("pageNum", 6);
			}

		} else if(searchType1Upper.equals("STA")) {
			returnPage = "bigdata/portal/dataset/staList";
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 2);
		} else if(searchType1Upper.equals("PRI")) {
			returnPage = "bigdata/portal/dataset/priList";
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 4);
		}

		return returnPage;
	}

	/**
	 * 데이터 목록 상세보기
	 *
	 * @param pageIndex
	 * @param dsId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="view.do", method={RequestMethod.GET})
	public String view(@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
						@RequestParam(value="dsId") int dsId,
						@RequestParam(value="startNum", defaultValue="0") int startNum,
						@RequestParam(value="limit", defaultValue="100") int limit,
						@RequestParam(value="esIndexNm") String esIndexNm,
						Model model) throws Exception {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		param.put("esIndexNm", esIndexNm);

		// 조회수 업데이트
		datasetService.updateBdpDataViewHits(param);

		EntityMap row = datasetService.selectBdpDataView(param);
		List<EntityMap> associativeDataList = datasetService.selectAssociativeDataset(param);

		// 제공기관, 연락처
		String cpIdInfo = String.valueOf(row.get("cpId"));
		String[] cpId = cpIdInfo.split("/");

		// paging
		if(startNum > 0) {
			limit = limit + startNum;
		}

		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("dsId", dsId);

		model.addAttribute("row", row);
		model.addAttribute("esIndexNm", esIndexNm);
		model.addAttribute("cpId", cpId);

		model.addAttribute("startNum", startNum);
		model.addAttribute("limit", limit);


		if(row.getString("dlType").equals("BDS")) {
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 1);
		} else if (row.getString("dlType").equals("PRI")) {
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 4);
		}

		// 연관검색어 키워드
		StringBuilder keywords = new StringBuilder();
		String seperator = ", ";
		for (int i = 0; i < associativeDataList.size(); i++) {
			keywords.append(associativeDataList.get(i).get("dsName"));
            // if not the last item
            if (i != associativeDataList.size() - 1) {
                keywords.append(seperator);
            }
        }
		model.addAttribute("keywords", keywords);

		model.addAttribute("associativeDataList", associativeDataList);

		return "bigdata/portal/dataset/datasetView";
	}

	@RequestMapping(value="spceView.do", method={RequestMethod.GET})
	public String mapView(@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
						@RequestParam(value="dsId") int dsId,
						@RequestParam(value="startNum", defaultValue="0") int startNum,
						@RequestParam(value="limit", defaultValue="100") int limit,
						@RequestParam(value="esIndexNm") String esIndexNm,
						Model model) throws Exception {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		param.put("esIndexNm", esIndexNm);

		// 조회수 업데이트
		datasetService.updateBdpDataViewHits(param);

		EntityMap row = datasetService.selectBdpDataView(param);
		List<EntityMap> associativeDataList = datasetService.selectAssociativeDataset(param);

		// 제공기관, 연락처
		String cpIdInfo = String.valueOf(row.get("cpId"));
		String[] cpId = cpIdInfo.split("/");

		// paging
		if(startNum > 0) {
			limit = limit + startNum;
		}

		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("dsId", dsId);

		model.addAttribute("row", row);
		model.addAttribute("esIndexNm", esIndexNm);
		model.addAttribute("cpId", cpId);

		model.addAttribute("startNum", startNum);
		model.addAttribute("limit", limit);


		if(row.getString("dlType").equals("BDS")) {
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 6);
		} else if (row.getString("dlType").equals("PRI")) {
			model.addAttribute("menuNum", 1);
			model.addAttribute("pageNum", 4);
		}

		// 연관검색어 키워드
		StringBuilder keywords = new StringBuilder();
		String seperator = ", ";
		for (int i = 0; i < associativeDataList.size(); i++) {
			keywords.append(associativeDataList.get(i).get("dsName"));
            // if not the last item
            if (i != associativeDataList.size() - 1) {
                keywords.append(seperator);
            }
        }
		model.addAttribute("keywords", keywords);

		model.addAttribute("associativeDataList", associativeDataList);

		return "bigdata/portal/dataset/spceView";
	}


	// 도매시장 경락가 sample
	@RequestMapping(value = "/getSampleMarketPriceDataJson.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getSampleMarketPriceDataJson(Model model) {
		List<EntityMap> dataList =  hiveService.selectSampleMarkerPriceRawData();

		model.addAttribute("result", dataList);
		return "jsonView";
	}


	/**
	 * 통계데이터셋 뷰페이지를 리턴한다.
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/staView.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String staList(
				@RequestParam(value="dsId", defaultValue="") String dsId,
				@RequestParam(value="tblId", defaultValue="") String tblId,
				@RequestParam(value="prdDe", defaultValue="") String prdDe,
				@RequestParam(value="pageIndex", defaultValue = "1") int pageIndex,
				@RequestParam(value="esIndexNm") String esIndexNm,
				@RequestParam(value="searchType1", defaultValue = "STA") String searchType1,
				Model model) throws InstantiationException, IllegalAccessException, IOException {

		if(tblId.equals("")) {
			return "error";
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tblId", tblId);
		param.put("dsId", dsId);
		param.put("esIndexNm", esIndexNm);

		EntityMap datasetInfo = datasetService.selectBdpDataView(param);
		List<EntityMap> associativeDataList = datasetService.selectAssociativeDataset(param);

		List<Object> staYears = datasetService.getStaYears(param);

		model.addAttribute("prdDeList", staYears);
		model.addAttribute("menuNum", 1);
		model.addAttribute("pageNum", 2);
		model.addAttribute("dsId", dsId);
		model.addAttribute("tblId", tblId);
		model.addAttribute("prdDe", prdDe);
		model.addAttribute("dsName", datasetInfo.get("dsName"));
		model.addAttribute("dsEndPoint", datasetInfo.get("dsEndPoint"));
		model.addAttribute("esIndexNm", esIndexNm);

		model.addAttribute("associativeDataList", associativeDataList);

		return "bigdata/portal/dataset/staView";
	}

	/**
	 * 통계표 데이터 JSON 호출
	 * @param tblId
	 * @param prdDe
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getStaDataJson.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getStaDataJson(
						@RequestParam(value="tblId", defaultValue="") String tblId,
						@RequestParam(value="prdDe", defaultValue="") String prdDe,
						Model model) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tblId", tblId);

		String prdDeA = prdDe;
		if(prdDeA.equals("")) {
			prdDeA = datasetService.selectBdpStdDimDtPreDe(param);
		}
		param.put("prdDe", prdDeA);

		List<String> dataList = datasetService.makeStaTable(param);
		String[] sample = dataList.get(0).split("\\|");
		int length = sample.length - 1; // value값은 빼고

		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

		for(String str : dataList) {
			String[] staColAry = str.split("\\|");

			HashMap<String, String> map = new HashMap<String, String>();

			int i = 0;
			for(i = 0; i < length; i++) {
				map.put("col"+i, staColAry[i]);
			}
			map.put("val", staColAry[i]);

			mapList.add(map);
		}

		model.addAttribute("result", mapList);

		return "jsonView";
	}

	/**
	 * 통계표 데이터 hdfs로 부터 데이터 읽음
	 * @param dsId
	 * @param tblId
	 * @param prdDe
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getStaTableDataJson.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getStaTableDataJson(
				@RequestParam(value="dsId", defaultValue="") String dsId,
				@RequestParam(value="tblId", defaultValue="") String tblId,
				@RequestParam(value="prdDe", defaultValue="") String prdDe,
				Model model
			) {

		try {
			boolean isLogin =  egovUserDetailsService.isAuthenticated();

			if(isLogin == true) {
				hdfsService.fileSystemOpen();
			} else {
				hdfsService.nonUserfileSystemOpen();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		EntityMap map = datasetService.selectDatasetRawdata("STA", dsId, 0, prdDe);
		model.addAttribute("result", "success");
		model.addAttribute("dataList", map.get("dataList"));

		return "jsonView";
	}

	/**
	 * 통계표 데이터 hdfs로 부터 데이터 읽음
	 * @param dsId
	 * @param tblId
	 * @param prdDe
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/getEsStaDataJson.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getEsStaDataJson(
				@RequestParam(value="dsId", defaultValue="") String dsId,
				@RequestParam(value="esIndexNm", defaultValue="") String esIndexNm,
				@RequestParam(value="prdDe", defaultValue="") String prdDe,
				Model model) throws InstantiationException, IllegalAccessException, IOException {

		EntityMap map = datasetService.selectEsDatasetInfo(dsId);
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("esIndexNm", esIndexNm);
		param.put("startNum", 0);
		param.put("size", 100);
		param.put("esOrderBy", map.get("esOrderBy"));		// order by
		param.put("prdDe", prdDe);
		List<Map<String, Object>> esDataList = datasetService.staIndexSearch(param);

		model.addAttribute("result", "success");
		model.addAttribute("esDataList", esDataList);
		model.addAttribute("esColEngInfoList", map.get("esColEngInfoList"));
		model.addAttribute("esColHeader", map.get("esColHeader"));

		return "jsonView";
	}


	/**
	 * 통계표 상단 타이틀 JSON 호출
	 * @param tblId
	 * @param text
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getStaLeftTopNames.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getStaLeftTopNames(
					@RequestParam(value="tblId", defaultValue="") String tblId,
					@RequestParam(value="cdNmAry", defaultValue="") List<String> cdNmAry,
					Model model) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tblId", tblId);
		param.put("cdNmAry", cdNmAry);

		List<EntityMap> dataList = datasetService.selectStaLeftTopNames(param);

		model.addAttribute("result", dataList);

		return "jsonView";
	}


	/**
	 * 통계표의 시점 정보를 hdfs로 부터 리턴
	 * @param tblId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getPrdDeList.do", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getPrdDeList(
					@RequestParam(value="tblId", defaultValue="") String tblId,
					Model model
				) {

		List<HashMap<String, String>> prdDeList = datasetService.getPrdDeList(tblId);

		model.addAttribute("result", prdDeList);

		return "jsonView";
	}

	/**
	 * 분석/시각화에서 데이터셋 검색
	 *
	 * @param keyword 검색어
	 * @param datasetTab BIG:빅데이터셋, STA:통계데이터, PUB:공공데이터, PRI:민간데이터, VIS:시각화데이터
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/search.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String searchDataset(@RequestParam("keyword") String keyword,
							@RequestParam(value = "datasetTab", required = false) String datasetTab,
							@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
							Model model) throws InstantiationException, IllegalAccessException, IOException {
		HashMap<String, Object> param = new HashMap<String, Object>();

		// 검색 (키워드 필수)
		if (keyword.isEmpty()) {
			model.addAttribute("result", "fail");
			model.addAttribute("message", "검색어는 최소 2자이상으로 입력해주세요.");
			// model.addAttribute("redirect", "/bdp/dataset/search.do");
			return "jsonView";
		}

		// 검색
		param.put("searchCondition", "dataCmtAll");
		param.put("searchKeyword", keyword);
		param.put("searchType1", datasetTab);
		param.put("dsUseAt", "Y");

		int pageTotal = datasetService.getTotalCount(param);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(pageTotal);

		// 페이징
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> datasetList = datasetService.selectDatasetList(param);
		//datasetTab가 STA인경우 prdDe값을 대입
		if(datasetTab.equals("STA")) {
			for(EntityMap rows : datasetList) {
				HashMap<String, Object> rowsParam = new HashMap<String, Object>();
				//rowsParam.put("tblId", rows.getString("htblId"));
				String tblId = rows.getString("htblId");
				// prdDeList를 hdfs의 파일명으로 가져오는것으로 변경
				//List<EntityMap> prdDeList = datasetService.selectStaPrdDeList(rowsParam);
				List<HashMap<String, String>> getPrdDeList = datasetService.getPrdDeList(tblId);
				List<HashMap<String, String>> prdDeList = new ArrayList<HashMap<String, String>>();
				for(HashMap<String, String> prdDeRow : getPrdDeList) {
					String fileName = String.valueOf( prdDeRow.get("fileName") ).replaceAll(".csv", "");
					HashMap<String, String> prdDeHash = new HashMap<String, String>();
					prdDeHash.put("prdDe", fileName);
					prdDeList.add(prdDeHash);
				}
				getPrdDeList = null;

				rows.put("prdDe", prdDeList);
			}
		}

		model.addAttribute("result", "success");
		model.addAttribute("dataList", datasetList);
		model.addAttribute("pageInfo", pageInfo);

		return "jsonView";
	}

	/**
	 * 검색 후 선택된 데이터 Table데이터로 리턴
	 * @param datasetTab BDS:빅데이터셋, STA:통계데이터, PUB:공공데이터, PRI:민간데이터
	 * @param dataId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rawdata.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectedDatasetRawdata(@RequestParam("datasetTab") String datasetTab,
							@RequestParam("dataId") String dataId,
							@RequestParam(value="limit", defaultValue="0") int limit,
							@RequestParam(value="prdDe", defaultValue="") String prdDe,
							@RequestParam(value="startNum", defaultValue="0") int startNum,
							@RequestParam(value="endNum", defaultValue="100") int endNum,
							Model model) {

		EntityMap map = null;

		// STA는 hdfs에서 파일을 읽어 출력 그외 데이터셋은 hive
		if(datasetTab.equals("STA") ) {
			try {
				hdfsService.fileSystemOpen();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			map = datasetService.selectDatasetRawdata(datasetTab, dataId, limit, prdDe);
		} else {
			map = datasetService.selectDatasetHiveRawData(datasetTab, dataId, prdDe, startNum, endNum);
		}
		// 데이터셋 조회

		model.addAttribute("result", "success");
		model.addAttribute("colId", map.get("colId"));
		model.addAttribute("colEngInfoList", map.get("colEngInfoList"));
		model.addAttribute("dataName", map.get("dataName"));
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("htblId", map.get("htblId"));
		model.addAttribute("hdfsFilePath", map.get("filePath"));

		return "jsonView";
	}


	/**
	 * 빅데이터셋 조건검색 결과값 데이터 리턴
	 * @param datasetTab
	 * @param dataId
	 * @param limit
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rawdataCondition.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectDatasetRawdataCondition(
				@RequestParam("datasetTab") String datasetTab,
				@RequestParam("dataId") String dataId,
				@RequestParam(value="limit", defaultValue="0") int limit,
				HttpServletRequest request,
				Model model
			) {
		String colNms = String.valueOf(request.getParameter("columnNames"));
		String colVals = String.valueOf(request.getParameter("columnValues"));

		//LOGGER.debug("columnValues = " + colVals);

		String[] colNmsAry = colNms.split(",");
		String[] colValsAry = colVals.split(",");

		int length = colNmsAry.length;
		String[] wheres = new String[length];
		for(int i = 0; i < length; i++) {
			wheres[i] = "`"+colNmsAry[i]+"` = '"+CommonUtil.escape(colValsAry[i])+"'";
		}
		String whereStr = CommonUtil.arrayJoin(" AND ", wheres);
		EntityMap map = datasetService.selectDatasetRawdataCondition(datasetTab, dataId, whereStr, limit);

		model.addAttribute("result", "success");
		model.addAttribute("colId", map.get("colId"));
		model.addAttribute("colEngInfoList", map.get("colEngInfoList"));
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("htblId", map.get("htblId"));

		return "jsonView";
	}

	/**
	 * 데이터 목록 상세 정보 화면에서 데이터 개수 표시
	 * @param htblId
	 * @param prdDe
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rawdataCount.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectDatasetRawdataCount(
							@RequestParam(value="htblId", defaultValue="") String htblId,
							@RequestParam(value="prdDe", defaultValue="") String prdDe,
							Model model) {

		// 데이터셋 조회
		String tableId = htblId;

		String numRows = hiveService.selectTableNumRows(tableId);

		model.addAttribute("totalCount", numRows);

		return "jsonView";
	}


	/**
	 * 보유데이터 파일 업로드
	 *
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadDataset(HttpServletRequest request,
							Model model) throws Exception {
        String dataId = "";
        String datasetTab = "";
        String dataType = "";
        String fileName = "";
        String fileSize = "";
        String target = String.valueOf(request.getParameter("target"));

		// 파일 업로드
        List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFiles(request, uploadDir, maxFileSize);

        if(list.size() > 0) {
        	EgovFormBasedFileVo vo = list.get(0);
            dataId = vo.getPhysicalName();			// 3068808F93884C53A4AD7AE54754C3D6
            datasetTab = vo.getServerSubPath(); 	// 20180101
            dataType = FilenameUtils.getExtension(vo.getFileName()).toUpperCase();
            fileName = vo.getFileName();
            fileSize = String.valueOf(vo.getSize());


            if(dataType.matches("(XLS[X]?|CSV)")) {

            	// 파일 업로드 이력 저장
                String filePath = uploadDir + "/" + datasetTab;
                String fileRealName = dataId;

                int ulId= 0;	// insertId
                String userId = getLoginUserId();
                ulId = historyService.saveBdpUserUploadLog(userId, filePath, fileRealName, fileName, fileSize, target);

            	// 최초 표시하는 데이터는 샘플로 100건 - 0으로 셋팅하면 존재하는 행만큼 보여짐
                // 서버에 업로드된 파일을 hdfs에 저장함.
            	uploadDataset(datasetTab, dataId,  dataType, 0, model);
            	String hdfsFilePath = uploadFileWriteHdfs(datasetTab, dataId, dataType);

            	if(ulId > 0) {
            		historyService.updatedLogWriteHdfsFilePath(ulId, hdfsFilePath);
            	}


        		model.addAttribute("result", "success");
        		model.addAttribute("datasetTab", datasetTab);
        		model.addAttribute("dataType", dataType);
        		model.addAttribute("dataId", dataId);
        		model.addAttribute("fileName", fileName);
        		model.addAttribute("hdfsFilePath", hdfsFilePath);

        		return "jsonView";
            }

        }

		model.addAttribute("result", "fail");
		return "jsonView";
	}

	/**
	 * 분석페이지 업로드된 파일 조회
	 * @param datasetTab 20180101
	 * @param dataId 3068808F93884C53A4AD7AE54754C3D6
	 * @param limit 100
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/rawdataUploaded.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String uploadDataset(@RequestParam("datasetTab") String datasetTab,
							@RequestParam("dataId") String dataId,
							@RequestParam("dataType") String dataType,
							@RequestParam(value="limit", defaultValue="0") int limit,
							Model model) throws IOException {

		String filePath = uploadDir + "/" + datasetTab + "/" + dataId;

		List<Object[]> dataList = null;

		// CSV, XLS|X 확장자로 된 파일을 hdfs 또는 서버로컬에 저장된것을 스트림으로 읽음
		try {
			hdfsService.fileSystemOpen();

			dataList = datasetService.readDatasetFile(filePath, dataType, limit);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		model.addAttribute("result", "success");
		model.addAttribute("colId", null);
		model.addAttribute("dataName", null);
		model.addAttribute("dataList", dataList);

		return "jsonView";
	}


	/**
	 * 데이터 분석 기능에서 upload된 파일을 hdfs에 저장후 저장된 경로 문자열을 리턴함
	 * @param datasetTab
	 * @param dataId
	 * @param dataType
	 * @param ulId
	 * @return
	 */
	public String uploadFileWriteHdfs(String datasetTab, String dataId, String dataType) {

		// 파일로 부터 데이터를 읽는다
		String filePath = uploadDir + "/" + datasetTab + "/" + dataId;
		String hdfsFilePath = "";
		List<Object[]> dataList = null;

		try {
			hdfsService.fileSystemOpen();

			dataList = datasetService.readDatasetFile(filePath, dataType, 0);

			String userId = getLoginUserId();

			hdfsFilePath = "/user/" + userId + "/" + dataId + ".csv";

			// hdfs에 파일이 존재시 파일명을 변경하여 hdfs에 저장
			if(hdfsService.isDupFile(hdfsFilePath)) {
				String timeMillis = String.valueOf(System.currentTimeMillis());
				hdfsFilePath = "/user/" + userId + "/" + dataId+timeMillis + ".csv";
			}

			String inText = "";
			for (Object[] rows : dataList) {
				int length = rows.length;

				String[] textAry = new String[length];
				for(int i=0; i < length; i++) {
					textAry[i] = rows[i].toString();
				}

				String text = CommonUtil.arrayJoin(",", textAry);
				inText += text+"\n";
			}
			hdfsService.writeFile(inText, hdfsFilePath);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return hdfsFilePath;
	}


	/**
	 * 데이터 셋 검색
	 * @param searchKeyword
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/searchAll.do", method = RequestMethod.GET)
	public String searchDataset(@RequestParam("searchKeyword") String searchKeyword, Model model) throws InstantiationException, IllegalAccessException, IOException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		int totalCount = 0;

		param.put("searchCondition", "dataCmtAll");
		param.put("searchKeyword", searchKeyword);
		param.put("startNum", 0);
		param.put("endNum", 5);
		param.put("dsUseAt", "Y");

		if(isRoleAdmin()) {
			param.put("dsSourceOpen", "Y");
		} else if(isRoleAna()) {
			param.put("dsAnalysisOpen", "Y");
		} else if(isRoleUser()) {
			param.put("dsDataOpen", "Y");
		} else {
			param.put("dsDataOpen", "Y");
		}

		// 빅데이터 목록
		param.put("searchType1", "BDS");
		totalCount = datasetService.getTotalCount(param);
		model.addAttribute("bdsTotal", totalCount);

		List<EntityMap> bdsList = datasetService.selectDatasetList(param);
		model.addAttribute("bdsList", bdsList);


		// 통계 목록
		param.put("searchType1", "STA");
		totalCount = datasetService.getTotalCount(param);
		model.addAttribute("staTotal", totalCount);

		List<EntityMap> staList = datasetService.selectDatasetList(param);
		model.addAttribute("staList", staList);


		// 민간데이터 목록
		param.put("searchType1", "PRI");
		totalCount = datasetService.getTotalCount(param);
		model.addAttribute("priTotal", totalCount);

		List<EntityMap> priList = datasetService.selectDatasetList(param);
		model.addAttribute("priList", priList);


		// 공공데이터 목록
		param.put("searchListSel", "dataCmtAll");
		param.put("searchListWord", searchKeyword);

		totalCount = publicDatasetService.getTotalCount(param);
		model.addAttribute("pdlTotal", totalCount);

		List<EntityMap> pdlList = publicDatasetService.selectPublicDataList(param);
		model.addAttribute("pdlList", pdlList);

		model.addAttribute("menuNum", 1);
		model.addAttribute("pageNum", 0);

		return "bigdata/portal/dataset/dataSearch";
	}


	/**
	 * 빅데이터셋 dataTab가 BDS, PRI인 경우 DB에서 데이터셋 데이터 Table데이터로 리턴
	 * @param datasetTab
	 * @param dataId
	 * @param limit
	 * @param startNum
	 * @param endNum
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value="/rawdatadb.do", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectDatasetRawdataDb(@RequestParam("datasetTab") String datasetTab,
				@RequestParam("dataId") String dataId,
				@RequestParam(value="startNum", defaultValue="0") int startNum,
				@RequestParam(value="endNum", defaultValue="100") int endNum,
				@RequestParam(value="esIndexNm") String esIndexNm,
				Model model
			) throws InstantiationException, IllegalAccessException, IOException {

		/*
		map = datasetService.selectDatasetDbRawData(datasetTab, dataId, startNum, endNum);


		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("esIndexNm", esIndexNm);
		param.put("startNum", startNum);
		param.put("size", endNum);
		param.put("orderbyString", map.get("orderbyString"));		// order by
		List<Map<String, Object>> esDataList = datasetService.indexSearch(param);

		model.addAttribute("result", "success");
		model.addAttribute("colEngInfoList", map.get("colEngInfoList"));
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("esDataList", esDataList);
		model.addAttribute("colHeader", map.get("colHeader"));
		model.addAttribute("dbTotalCount", map.get("totalCount"));
		*/

		EntityMap map = datasetService.selectEsDatasetInfo(dataId);
		HashMap<String, Object> param = new HashMap<String, Object>();
		List<String> notEmptyList = new ArrayList<String>();
		List<String> notMatchList = new ArrayList<String>();
		param.put("esIndexNm", esIndexNm);
		param.put("startNum", startNum);
		param.put("size", endNum);
		param.put("esOrderBy", map.get("esOrderBy"));		// order by

		//not Empty Column Setting
		switch(dataId) {
		case "411" : // 도소매

						notEmptyList.add("price");
						notEmptyList.add("marketname");
						notEmptyList.add("itemname");
						notEmptyList.add("kindname");
						notEmptyList.add("STD_PRDLST_NEW_CODE");
						notMatchList.add("STD_PRDLST_NEW_CODE");
						break;

		}

		param.put("notEmptyList", notEmptyList);
		param.put("notMatchList", notMatchList);
		List<Map<String, Object>> esDataList = datasetService.indexSearch(param);

		//int esTotalCount = 0;

		model.addAttribute("result", "success");
		model.addAttribute("esColEngInfoList", map.get("esColEngInfoList"));
		model.addAttribute("esDataList", esDataList);
		model.addAttribute("esColHeader", map.get("esColHeader"));
		model.addAttribute("esTotalCount", map.get("esIndexDocTotcnt"));


		return "jsonView";
	}

	/**
	 * Excel download
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="/excelDownload.do", method=RequestMethod.GET)
	public void excelDownLoad(
			@RequestParam("datasetTab") String datasetTab,
			@RequestParam("dataId") String dataId,
			@RequestParam(value="startNum", defaultValue="0") int startNum,
			@RequestParam(value="endNum", defaultValue="100") int endNum,
			HttpServletResponse response,
			Model model
			) throws Exception {

		HashMap<String, Object> param = new HashMap<String, Object>();

		HSSFWorkbook objWorkBook = new HSSFWorkbook();
		HSSFSheet objSheet = null;
		HSSFRow objRow = null;
		HSSFCell objCell = null;

		// 제목 폰트
		HSSFFont titFont = objWorkBook.createFont();
		titFont.setFontHeightInPoints((short)12);
		titFont.setBoldweight(titFont.BOLDWEIGHT_BOLD);
		titFont.setFontName("맑은고딕");

		// header 폰트
		HSSFFont hdFont = objWorkBook.createFont();
		hdFont.setFontHeightInPoints((short)9);
		hdFont.setBoldweight(hdFont.BOLDWEIGHT_BOLD);
		hdFont.setFontName("맑은고딕");

		// 내용 폰트
		HSSFFont tableFont = objWorkBook.createFont();
		tableFont.setFontHeightInPoints((short)9);
		tableFont.setFontName("맑은고딕");


		// 타이틀 폰트 적용, 정렬
		HSSFCellStyle styleTit = objWorkBook.createCellStyle();		// 제목 스타일
		styleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleTit.setFont(titFont);

		// header 폰트 적용, 정렬
		HSSFCellStyle styleHd = objWorkBook.createCellStyle();		// 스타일
		styleHd.setFont(hdFont);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHd.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);

		// 내용 폰트 적용, 정렬
		// header 폰트 적용, 정렬
		HSSFCellStyle styleCont = objWorkBook.createCellStyle();		// 스타일
		styleCont.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCont.setFont(tableFont);

		EntityMap entityMap = datasetService.selectDatasetDbRawData(datasetTab, dataId, startNum, endNum);

		/*
		model.addAttribute("result", "success");
		model.addAttribute("colEngInfoList", entityMap.get("colEngInfoList"));
		model.addAttribute("dataList", entityMap.get("dataList"));
		model.addAttribute("colHeader", entityMap.get("colHeader"));
		model.addAttribute("dbTotalCount", entityMap.get("totalCount"));
		*/

		String dsName = entityMap.getString("dsName");
		String preDe = entityMap.get("dsLastUpdateDate").toString();
		String fileName = dsName.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
		fileName = fileName.replaceAll(" ", "_");
		fileName = preDe + "_" + fileName;

		String read;

		//objSheet = objWorkBook.createSheet("Sheet1");

		objRow = objSheet.createRow(0);
		objRow.setHeight((short) 0x150);
		objCell = objRow.createCell(0);
		objCell.setCellValue(dsName);
		objCell.setCellStyle(styleTit);

		int i = 0;

		List<String> headerDataList = new ArrayList<String>();
		objRow = objSheet.getRow(2);
		/*
		while((read = br.readLine()) != null) {
			int j = 0;
			objRow = objSheet.createRow(i+3);
			objRow.setHeight((short) 0x150);

			String[] rows = read.split(",");
			for(String row : rows) {
				objCell = objRow.createCell(j);
				objCell.setCellValue(row);

				// cell 사이즈 조정을 위해
				if(i == 0) {
					headerDataList.add(row);
					objCell.setCellStyle(styleHd);
				} else {
					objCell.setCellStyle(styleCont);
				}

				j++;
			}

			i++;
		}
		*/

		// title cell 병합
		int firstRowCell = 0;
		int lastRowCell = 0;
		int firstColumnCell = 0;
		int lastColumnCell = headerDataList.size() - 1;
		objSheet.addMergedRegion(new CellRangeAddress(firstRowCell, lastRowCell, firstColumnCell, lastColumnCell));

		// 표 사이즈 늘이기
		for(i = 0; i < headerDataList.size(); i++) {
			objSheet.autoSizeColumn(i);
		}

		// check here
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode(fileName, "UTF-8") + ".xls");

		OutputStream fileOut = response.getOutputStream();
		objWorkBook.write(fileOut);
		fileOut.close();

		response.getOutputStream().flush();
		response.getOutputStream().close();

	}
}
