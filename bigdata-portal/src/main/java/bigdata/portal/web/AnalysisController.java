package bigdata.portal.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.AnalysisService;
import bigdata.portal.service.CodeService;
import bigdata.portal.service.HdfsService;
import bigdata.portal.service.HistoryService;
import bigdata.portal.service.UserSettingService;
import bigdata.portal.service.VisualizeService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.utl.fcc.service.EgovFormBasedFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 데이터 분석 컨트롤러
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
@RequestMapping("/bdp/analysis")
public class AnalysisController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisController.class);
	@Autowired private AnalysisService analysisService;
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private CodeService codeService;
	@Autowired private HdfsService hdfsService;
	@Autowired private VisualizeService visualizeService;
	@Autowired private UserSettingService userSettingService;
	@Autowired private HistoryService historyService;

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;
	
	private final String uploadDir = EgovProperties.getProperty("bigdata.visualize.datadir");
		
	/**
	 * 데이터 분석하기 화면
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String analysisCreateForm(HttpServletRequest request, Model model) {
		// 권한체크		
		
		String init = String.valueOf(request.getParameter("init"));
		String id = String.valueOf(request.getParameter("id"));		
		String userId = getLoginUserId();
		
		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 1);
		model.addAttribute("init", init);
		model.addAttribute("id", id);
		model.addAttribute("userId", userId);
		
		return "bigdata/portal/analysis/analysisCreateForm";
	}
	
	
	/**
	 * 데이터 분석 설정 저장
	 * @param anaTitle
	 * @param anaTable
	 * @param anaTableStat
	 * @param anaDataColumn
	 * @param anaAndTerms
	 * @param anaOrTerms
	 * @param anaDataKind
	 * @param anaDataClustering
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createDataInsert.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createDataInsert(
			@RequestParam(value="anaTitle", required=false, defaultValue="") String anaTitle,
			@RequestParam(value="anaTable", required=false, defaultValue="") String anaTable,
			@RequestParam(value="anaTableStat", required=false, defaultValue="") String anaTableStat,
			@RequestParam(value="anaDataColumn", required=false, defaultValue="") String anaDataColumn,
			@RequestParam(value="anaAndTerms", required=false, defaultValue="") String anaAndTerms,
			@RequestParam(value="anaOrTerms", required=false, defaultValue="") String anaOrTerms,
			@RequestParam(value="anaDataKind", required=false, defaultValue="") String anaDataKind,
			@RequestParam(value="anaDataClustering", required=false, defaultValue="") String anaDataClustering,
			@RequestParam(value="anaStaParamReq", required=false, defaultValue="") String anaStaParamReq,
			@RequestParam(value="anaPnuKind", required=false, defaultValue="") String anaPnuKind,
			Model model
			) {
		
		String anaUserId = getLoginUserId();
		
		// 데이터 저장
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anaIdx", 0);
		param.put("anaUserId", anaUserId);
		param.put("anaTitle", anaTitle);
		param.put("anaTable", anaTable);
		param.put("anaTableStat", anaTableStat);
		param.put("anaDataColumn", anaDataColumn);
		param.put("anaAndTerms", anaAndTerms);
		param.put("anaOrTerms", anaOrTerms);
		param.put("anaDataKind", anaDataKind);
		param.put("anaDataClustering", anaDataClustering);
		param.put("anaStaParamReq", anaStaParamReq);
		param.put("anaPnuKind", anaPnuKind);
		
		int insertId = analysisService.insertBdpAnalysisDefault(param);
		String result = "FAIL";
		
		if( insertId > 0) {
			result = "OK";
			
			// 사용자가 등록한 글 이력을 저장
			String userId = getLoginUserId();
			String artCont = anaTitle;
			String artTarget = "ANALY";
			String artStatus = "REG";
			int artIdx = insertId;
			String artUrl = "/bdp/analysis/createVisualView.do?anaIdx="+insertId;
			
			int res1 = historyService.saveBdpUserArticleLog(userId, artTarget, artCont, artStatus, artIdx, artUrl);
		}
		model.addAttribute("result", result);

		return "jsonView";
	}
	
	/**
	 * 데이터 분석 목록
	 * 
	 * @param pageIndex
	 * @param searchKeyword
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String analysisCreateList(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
			@RequestParam(value="searchKeyword", required=false, defaultValue="") String searchKeyword,
			Model model) {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		String loginUserId = getLoginUserId();
		
		param.put("anaUserId", loginUserId);
		if(!searchKeyword.equals("")) {
			param.put("searchKeyword", searchKeyword);
		}
		
		EntityMap userInfo = userSettingService.selectUserInfo(loginUserId);
		String authorCode = (String) userInfo.get("authorCode");
		if(authorCode.equals("ROLE_ADMIN")) {
			param.put("anaUserId", "");
		}
		
		
		int totalCount = analysisService.selectBdpAnalysisCount(param);
		
		
		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);
		
		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());
		
		List<EntityMap> anaList = analysisService.selectBdpAnalysisList(param);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("anaList", anaList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 1);
		
		return "bigdata/portal/analysis/analysisCreateList";
	}
	
	/**
	 * 삭제, 그외 다른 처리할때 사용
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/createProc.do", method = RequestMethod.POST)
	public String createProc(HttpServletRequest request, Model model) {
		
		String returnPage = "redirect:/bdp/analysis/createList.do";
		
		String mode = String.valueOf(request.getParameter("mode"));
		String userId = "";
		if(isLogin()) {
			userId = getLoginUserId();
		} else {
			model.addAttribute("message", "잘못된 접근입니다.");
			model.addAttribute("redirect", "/bdp/analysis/createList.do");

			return "bigdata/error/message";
		}
		
		// 리스트페이지 목록 삭제
		if(mode.equals("listDel")) {
			String[] anaIdx = request.getParameterValues("anaIdx");
			String pageIndex = !String.valueOf(request.getParameter("pageIndex")).equals("null") ? request.getParameter("pageIndex") : "1";
			
			try {
				hdfsService.fileSystemOpen();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			for(String idx : anaIdx) {
				// 현재 idx값의 분석 row를 호출
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("anaIdx", idx);
				EntityMap anaInfo = analysisService.selectBdpAnalysisRow(param);
				
				int res = 0;
				// 관리자가 아니면 아이디도 같이 체크
				if(isRoleAdmin()) {					
					res = analysisService.deleteBdpAnalysisRow(idx);
				} else {
					res = analysisService.deleteBdpAnalysisRow(idx, userId);
				}
				
				if(res > 0) {
					
					// 분석정보를 읽어들여 hdfs에 파일정보를 로드 후 삭제함
					boolean hdfsFileDelOK = analysisService.hdfsDeleteExecAnalysisResult(anaInfo);

					// 글 삭제 이력 저장
					String anaTitle = anaInfo.getString("anaTitle");
					String artCont = anaTitle;
					String artTarget = "ANASLY";
					String artStatus = "DEL";
					int artIdx = Integer.valueOf(idx);
					String artUrl = "";
					int res1 = historyService.saveBdpUserArticleLog(userId, artTarget, artCont, artStatus, artIdx, artUrl);
				}
			}
			
			returnPage = "redirect:/bdp/analysis/createList.do?pageIndex="+pageIndex;
		}
		
		return returnPage;
	}
	
	
	@RequestMapping(value="/createVisualView.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String createVisualView(
									@RequestParam(value="anaIdx", defaultValue="") String anaIdx,
									Model model) {
		List<CodeDetail> codeItem = codeService.selectCodeDetail("C0001");
		List<CodeDetail> codeType = codeService.selectCodeDetail("C0002");
		
		if(Integer.valueOf(anaIdx) > 0) {
			int cnt = 0;
			cnt = analysisService.selectBdpAnalysisRowCnt(Integer.valueOf(anaIdx));
			if(cnt < 1) {
				model.addAttribute("message", "존재하지 않는 페이지입니다.");
				model.addAttribute("redirect", "back");
				return "bigdata/error/message";
			}
		} else {
			return "bigdata/error/message";
		}

		model.addAttribute("codeItem", codeItem);
		model.addAttribute("codeType", codeType);		
		model.addAttribute("anaIdx", anaIdx);
		
		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 1);
		return "bigdata/portal/analysis/analysisCreateView"; 
	}
	
	@RequestMapping(value="/getAnalysisCreateGrid.do", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAnalysisCreateGrid(
				@RequestParam(value="anaIdx", defaultValue="0") String anaIdx,
				Model model
			) {

		// hdfs에서 dataList 가져옴
		EntityMap map = analysisService.selectBdpAnalysisRawdata(anaIdx);

		model.addAttribute("result","success");
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("anaTitle", map.get("anaTitle"));

		return "jsonView";
	}
	
	
	/**
	 * @param pageIndex
	 * @param searchKeyword
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mergeList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String analysisMergeList(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
			@RequestParam(value="searchKeyword", required=false, defaultValue="") String searchKeyword,
			Model model
			) {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		String loginUserId = getLoginUserId();
		
		// 임시 로그인 ID
		param.put("anadmUserId", loginUserId);
		if(!searchKeyword.equals("")) {
			param.put("searchKeyword", searchKeyword);
		}
		
		EntityMap userInfo = userSettingService.selectUserInfo(loginUserId);
		String authorCode = (String) userInfo.get("authorCode");
		if(authorCode.equals("ROLE_ADMIN")) {
			param.put("anadmUserId", "");
		}

		int totalCount = analysisService.selectBdpAnalysisDataMergeCount(param);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);

		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> dataList = analysisService.selectBdpAnalysisDataMergeList(param);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("dataList", dataList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 2);
		return "bigdata/portal/analysis/analysisMergeList";
	}

	
	@RequestMapping(value = "/merge.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String analysisMergeList(Model model) {
		
		String userId = getLoginUserId();
		
		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 2);
		model.addAttribute("userId", userId);
		return "bigdata/portal/analysis/analysisMergeForm";
	}
	
	
	@RequestMapping(value = "/mergeDataInsert.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String mergeDataInsert(
			@RequestParam(value="anadmTitle", required=false, defaultValue="") String anadmTitle,
			@RequestParam(value="anadmTable1", required=false, defaultValue="") String anadmTable1,
			@RequestParam(value="anadmTable1Stat", required=false, defaultValue="") String anadmTable1Stat,
			@RequestParam(value="anadmTable1AndTerms", required=false, defaultValue="") String anadmTable1AndTerms,
			@RequestParam(value="anadmTable1OrTerms", required=false, defaultValue="") String anadmTable1OrTerms,
			@RequestParam(value="anadmTable1ParamReq", required=false, defaultValue="") String anadmTable1ParamReq,
			@RequestParam(value="anadmTable1Column", required=false, defaultValue="") String anadmTable1Column,
			@RequestParam(value="anadmTable2", required=false, defaultValue="") String anadmTable2,
			@RequestParam(value="anadmTable2Stat", required=false, defaultValue="") String anadmTable2Stat,
			@RequestParam(value="anadmTable2AndTerms", required=false, defaultValue="") String anadmTable2AndTerms,
			@RequestParam(value="anadmTable2OrTerms", required=false, defaultValue="") String anadmTable2OrTerms,
			@RequestParam(value="anadmTable2ParamReq", required=false, defaultValue="") String anadmTable2ParamReq,
			@RequestParam(value="anadmTable2Column", required=false, defaultValue="") String anadmTable2Column,
			@RequestParam(value="anadmMergeKey", required=false, defaultValue="") String anadmMergeKey,
			Model model) {
		
		String anadmUserId = getLoginUserId();
		
		// 데이터 저장
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("anadmIdx", 0);
		param.put("anadmUserId", anadmUserId);
		param.put("anadmTitle", anadmTitle);
		param.put("anadmTable1", anadmTable1);
		param.put("anadmTable1Stat", anadmTable1Stat);
		param.put("anadmTable1AndTerms", anadmTable1AndTerms);
		param.put("anadmTable1OrTerms", anadmTable1OrTerms);
		param.put("anadmTable1ParamReq", anadmTable1ParamReq);
		param.put("anadmTable1Column", anadmTable1Column);
		param.put("anadmTable2", anadmTable2);
		param.put("anadmTable2Stat", anadmTable2Stat);
		param.put("anadmTable2AndTerms", anadmTable2AndTerms);
		param.put("anadmTable2OrTerms", anadmTable2OrTerms);
		param.put("anadmTable2ParamReq", anadmTable2ParamReq);
		param.put("anadmTable2Column", anadmTable2Column);
		param.put("anadmMergeKey", anadmMergeKey);
		
		int insertId = analysisService.insertBdpAnalysisDataMerge(param);
		String result = "FAIL";
		if(insertId > 0) {
			result = "OK";
			
			// 사용자가 등록한 글 이력을 저장
			String userId = anadmUserId;
			String artCont = anadmTitle;
			String artTarget = "MERGE";
			String artStatus = "REG";
			int artIdx = insertId;
			String artUrl = "/bdp/analysis/mergeView.do?anadmIdx="+insertId;
			
			int res1 = historyService.saveBdpUserArticleLog(userId, artTarget, artCont, artStatus, artIdx, artUrl);

		}
		model.addAttribute("result", result);
		
		return "jsonView";
	}
	

	/**
	 * 분석에서 결합 데이터셋 검색
	 * 
	 * @param keyword 검색어
	 * @param datasetTab MGE:결합데이터셋
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchAnalysisMerge.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String searchDataset(@RequestParam("keyword") String keyword,
							@RequestParam(value = "datasetTab", required = false) String datasetTab,
							@RequestParam(value = "userId", required = false) String userId,
							@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
							Model model) {
		HashMap<String, Object> param = new HashMap<String, Object>();

		// 검색 (키워드 필수)
		if (keyword.isEmpty()) {
			model.addAttribute("result", "fail");
			model.addAttribute("message", "검색어는 최소 2자이상으로 입력해주세요.");
			// model.addAttribute("redirect", "/bdp/dataset/search.do");
			return "jsonView";
		}

		// 검색
		param.put("anadmUserId", userId);
		param.put("searchKeyword", keyword);
		param.put("anadmMergeStat", "Y");

		int pageTotal = analysisService.selectBdpAnalysisDataMergeCount(param);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(pageTotal);

		// 페이징
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> datasetList = analysisService.selectBdpAnalysisDataMergeList(param);
		for(EntityMap row : datasetList) {
			String anadmIdx = row.getString("anadmIdx");
			row.put("dsId", anadmIdx);
			row.put("dlType", "MGE");
			row.put("dsName", row.getString("anadmTitle"));
		}

		model.addAttribute("result", "success");
		model.addAttribute("dataList", datasetList);
		model.addAttribute("pageInfo", pageInfo);

		return "jsonView";
	}
	
	/**
	 * 검색 후 선택된 분석결합 데이터 Table데이터로 리턴
	 * @param datasetTab MGE:결합데이터셋
	 * @param dataId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/analysisDataMergeRawdata.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String selectedDatasetAnalysisMergeRawdata(@RequestParam("datasetTab") String datasetTab, 
							@RequestParam("dataId") String dataId, 
							@RequestParam(value="limit", defaultValue="0") int limit,
							@RequestParam(value="userId", defaultValue="") String userId,
							Model model) {
		
		EntityMap map = null;
		// hdfs에 파일 저장
		try {
			hdfsService.fileSystemOpen();
			// 데이터셋 조회
			map = analysisService.selectAnalysisDataMergeRawdata(datasetTab, dataId, limit, userId);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		model.addAttribute("result", "success");
		//model.addAttribute("colId", map.get("colId"));
		model.addAttribute("dataName", map.get("dataName"));
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("hdfsFilePath", map.get("filePath"));
		model.addAttribute("anadmTitle", map.get("anadmTitle"));
		//model.addAttribute("htblId", map.get("htblId"));

		return "jsonView";
	}
	
	
	/**
	 * 삭제, 그외 다른 처리할때 사용
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mergeProc", method = RequestMethod.POST)
	public String mergeProc(HttpServletRequest request, Model model) {
		
		String returnPage = "redirect:/bdp/analysis/mergeList.do";
		
		String mode = String.valueOf(request.getParameter("mode"));
		String userId = "";
		if(isLogin()) {
			userId = getLoginUserId();
		} else {
			 model.addAttribute("message", "잘못된 접근입니다.");
			 model.addAttribute("redirect", "/bdp/analysis/mergeList.do");
			 
			 return "bigdata/error/message";
		}
		
		// 리스트페이지 목록 삭제
		if(mode.equals("listDel")) {
			String[] anaIdx = request.getParameterValues("anadmIdx");
			String pageIndex = !String.valueOf(request.getParameter("pageIndex")).equals("null") ? request.getParameter("pageIndex") : "1";
			
			try {
				hdfsService.fileSystemOpen();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(String idx : anaIdx) {
				// 현재  idx값의 merge row를 호출
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("anadmIdx", idx);
				EntityMap anadmInfo = analysisService.selectBdpAnalysisDataMergeRow(idx);
								
				int res = 0;
				//  관리자가 아니면 아이디도 같이 체크
				if(isRoleAdmin()) {
					res = analysisService.deleteBdpAnalysisDataMergeRow(idx);
				} else {
					res = analysisService.deleteBdpAnalysisDataMergeRow(idx, userId);
				}
				
				if(res > 0) {
					// 분석정보를 읽어들여 hdfs에 파일정보를 제공한 후 삭제시킴
					boolean hdfsFileDelOk = analysisService.hdfsDeleteExecMergeResult(anadmInfo);
					
					// 글 삭제 이력 저장
					String anaTitle = anadmInfo.getString("anadmTitle");
					String artCont = anaTitle;
					String artTarget = "MERGE";
					String artStatus = "DEL";
					int artIdx = Integer.valueOf(idx);
					String artUrl = "";
					
					int res1 = historyService.saveBdpUserArticleLog(userId, artTarget, artCont, artStatus, artIdx, artUrl);
				}
			}
			
			returnPage = "redirect:/bdp/analysis/mergeList.do?pageIndex="+pageIndex;
		}
		
		return returnPage;
	}
	
	
	@RequestMapping(value = "/mergeView.do", method = RequestMethod.GET)
	public String analysisMergeView(
							@RequestParam(value="", defaultValue="") String anadmIdx,
							Model model
						) {
		
		model.addAttribute("anadmIdx", anadmIdx);
		
		model.addAttribute("menuNum", 2);
		model.addAttribute("pageNum", 2); 
		
		return "bigdata/portal/analysis/analysisMergeView";
		
	}
	
	@RequestMapping(value = "/getAnalysisMergeGrid.do", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAnalysisMergeGrid(
			@RequestParam(value="anadmIdx", defaultValue="0") String anadmIdx,
			Model model
		) {

		// hdfs에서 dataList 가져옴
		EntityMap map = analysisService.selectBdpAnalysisDataMergeRawdata(anadmIdx);
	
		model.addAttribute("result","success");
		model.addAttribute("dataList", map.get("dataList"));
		model.addAttribute("anadmTitle", map.get("anadmTitle"));
	
		return "jsonView";
	}
}
