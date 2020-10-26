package bigdata.portal.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.DatasetService;
import bigdata.portal.service.HiveService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 
 * 데이터셋 관리 컨트롤러
 *
 * @author THEIMC rlaeodnjs
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2019. 3. 14.     rlaeodnjs          최초 생성
 *      </pre>
 *
 * @since 2019. 3. 14.
 */

@Controller
@RequestMapping("/bdp/admin/dataset")
public class AdminDatasetController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatasetController.class);
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private DatasetService datasetService;
	@Autowired private HiveService hiveService;

	
	/**
	 * 
	 * @param pageIndex
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list.do", method={RequestMethod.GET})
	public String list(
				@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
				@RequestParam(value="searchCondition", required=false, defaultValue="") String searchCondition,
				@RequestParam(value="searchKeyword", required=false, defaultValue="") String searchKeyword,
				@RequestParam(value="searchType1", required=false, defaultValue="") String searchType1,
				@RequestParam(value="searchType2", required=false, defaultValue="") String searchType2,
				@RequestParam(value="searchType3", required=false, defaultValue="") String searchType3,							
				Model model
			) throws Exception {
		
		if(!isRoleAdmin()) {
			model.addAttribute("message", "관리자 권한이 아닙니다.");
			model.addAttribute("redirect", "/bdp/main/main.do");
			return "bigdata/error/message";
		}
		
		String searchType1Upper = searchType1.toUpperCase();
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
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


		List<EntityMap> dataList = datasetService.selectDatasetList(param);
		
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("searchKeyword", searchKeyword);		
		model.addAttribute("searchType1", searchType1Upper);
		model.addAttribute("searchType2", searchType2);
		model.addAttribute("searchType3", searchType3);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("dataList", dataList);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);

		return "bigdata/portal/dataset/admDatasetList";
	}

	
	/**
	 * 데이터셋 개방 여부 업데이트
	 * @param dsId
	 * @param val
	 * @param target
	 * @param mode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/openRangeProc.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String openUpdate(
				@RequestParam(value="dsId", defaultValue="") int dsId,
				@RequestParam(value="val", defaultValue="") String val,
				@RequestParam(value="target", defaultValue="") String target,
				@RequestParam(value="mode", defaultValue="") String mode,
				Model model
			) {
		
		if(dsId > 0) {
			int res = datasetService.openRangeUpdate(dsId, val, target);
		
			model.addAttribute("result", "success");
		} else {
			model.addAttribute("result", "error");
		}
		
		return "jsonView";
	}
	
	@RequestMapping(value="/dsUseAtUpdate.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String dsUseAtUpdate(
				@RequestParam(value="dsId", defaultValue="") int dsId,
				@RequestParam(value="dsUseAt", defaultValue="") String dsUseAt,
				Model model
			) {
		
		int res = 0;
		
		if(dsId > 0) {
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dsId", dsId);
			param.put("dsUseAt", dsUseAt);
			res = datasetService.updateDsUseAt(param);
			
			model.addAttribute("result", res);
		} else {
			model.addAttribute("result", "0");
		}
		
		return "jsonView";		
	}
	
	@RequestMapping(value="/datasetView.do", method=RequestMethod.GET)
	public String view(
				@RequestParam(value="dsId", defaultValue="") int dsId,
				@RequestParam(value="pageIndex", defaultValue="") int pageIndex,
				Model model
			) {
		
		if(!isRoleAdmin()) {
			model.addAttribute("message", "관리자 권한이 아닙니다.");
			model.addAttribute("redirect", "/bdp/main/main.do");
			return "bigdata/error/message";
		}
		
		if(dsId > 0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dsId", dsId);
			EntityMap row = datasetService.selectBdpDataView(param);
			// 데이터 목록정보
			List<EntityMap> bdpDataList = datasetService.selectBdpDataList("Y");
			
			model.addAttribute("row", row);
			model.addAttribute("bdpDataList", bdpDataList);
			model.addAttribute("pageIndex", pageIndex);
		} else {
			model.addAttribute("message", "잘못된 접근입니다.");
			model.addAttribute("redirect", "back");
			return "bigdata/error/message";
		}
		
		return "bigdata/portal/dataset/admDatasetView";
	}
	
	/**
	 * 데이터목록을 JSON으로 제공
	 * @param dlId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getJsonDataList.do", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getJsonDataList(
				@RequestParam(value="dlId", defaultValue="") String dlId,
				Model model
			) {
		
		EntityMap dataList = datasetService.selectBdpDataListRow("Y", dlId);
		LOGGER.debug("dlId", dlId);
		model.addAttribute("bdpDataList", dataList);
		return "jsonView";
	}
	
	
	/**
	 * 데이터셋 수정
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/datasetModify.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String datasetModify(HttpServletRequest request, Model model) {
		
		String mode = String.valueOf(request.getParameter("mode"));
		if(mode.equals("MOD")) {			
			int dsId = Integer.valueOf(request.getParameter("dsId"));
			String dlId= String.valueOf(request.getParameter("dlId"));
			String dlType = String.valueOf(request.getParameter("dlType"));
			String dsStorageType = String.valueOf(request.getParameter("dsStorageType"));
			String dsDataType = String.valueOf(request.getParameter("dsDataType"));
			String htblPartition = String.valueOf(request.getParameter("htblPartition"));
			
			// 데이터셋 분류
			String dsCateDept1 = String.valueOf(request.getParameter("dsCateDept1"));
			String dsCateDept2 = String.valueOf(request.getParameter("dsCateDept2"));
			
			// 데이터셋명, 설명
			String dsName = String.valueOf(request.getParameter("dsName"));
			String dsComment = String.valueOf(request.getParameter("dsComment"));
			
			// 개방여부
			String openRangeSource = String.valueOf(request.getParameter("openRangeSource")).equals("null") ? "N" : String.valueOf(request.getParameter("openRangeSource"));
			String openRangeAnalysis= String.valueOf(request.getParameter("openRangeAnalysis")).equals("null") ? "N" : String.valueOf(request.getParameter("openRangeAnalysis"));
			String openRangeOpen= String.valueOf(request.getParameter("openRangeOpen")).equals("null") ? "N" : String.valueOf(request.getParameter("openRangeOpen"));
			
			String dsUseAt = String.valueOf(request.getParameter("dsUseAt"));
			String dsEndPoint = String.valueOf(request.getParameter("dsEndPoint"));
			
			
			// update
			int res = 0;
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dsId", dsId);
			param.put("dlId", dlId);
			param.put("dsStorageType", dsStorageType);
			param.put("dsDataType", dsDataType);
			param.put("htblPartition", htblPartition);
			
			//dlType이 STA인 경우 dsCateDept1, dsCateDept2를 저장하지 않음.
			param.put("dlType", dlType);
			param.put("dsCateDept1", dsCateDept1);
			param.put("dsCateDept2", dsCateDept2);
			param.put("dsName", dsName);
			param.put("dsComment", dsComment);
			param.put("dsSourceOpen",openRangeSource);
			param.put("dsAnalysisOpen",openRangeAnalysis);
			param.put("dsDataOpen",openRangeOpen);
			param.put("dsUseAt", dsUseAt);
			param.put("dsEndPoint", dsEndPoint);
			
			res = datasetService.updateBdpDatasetRow(param);
			
			model.addAttribute("result", res);
		} else {
			model.addAttribute("result", "0");
		}
		
		return "jsonView";
	}
	
	// 데이터 목록명 리스트
	@RequestMapping(value="/datalist.do", method=RequestMethod.GET)
	public String dataList(
				@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
				@RequestParam(value="searchCondition", required=false, defaultValue="") String searchCondition,
				@RequestParam(value="searchKeyword", required=false, defaultValue="") String searchKeyword,
				@RequestParam(value="searchType1", required=false, defaultValue="") String searchType1,
				Model model
			) throws Exception {
		
		if(!isRoleAdmin()) {
			model.addAttribute("message", "관리자 권한이 아닙니다.");
			model.addAttribute("redirect", "/bdp/main/main.do");
			return "bigdata/error/message";
		}
		
		String searchType1Upper = searchType1.toUpperCase();
		HashMap<String, Object> param = new HashMap<String, Object>();
		
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
		
		int totalCount = datasetService.getTotalCountDataList(param);
		
		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);
		
		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());
		
		List<EntityMap> dataList = datasetService.selectBdpDataList(param);
		
		model.addAttribute("searchCondition", searchCondition);
		model.addAttribute("searchKeyword", searchKeyword);		
		model.addAttribute("searchType1", searchType1Upper);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("dataList", dataList);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);

		return "bigdata/portal/dataset/admDataList";
	}
	
	/**
	 * 데이터목록 사용여부 변경
	 * @param dlId
	 * @param dlUseAt
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dlUseAtUpdate.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String dlUseAtUpdate(
				@RequestParam(value="dlId", defaultValue="") String dlId,
				@RequestParam(value="dlUseAt", defaultValue="") String dlUseAt,
				Model model
			) throws Exception {
		
		int res = 0;
		
		if(!dlId.equals("")) {
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dlId", dlId);
			param.put("dlUseAt", dlUseAt);
			res = datasetService.updateDlUseAt(param);
			
			model.addAttribute("result", res);
		} else {
			model.addAttribute("result", "0");
		}
		
		return "jsonView";
	}
	
	@RequestMapping(value="/dataView.do", method=RequestMethod.GET)
	public String dataView(
				@RequestParam(value="dlId", defaultValue="") String dlId,
				@RequestParam(value="pageIndex", defaultValue="") int pageIndex,
				Model model
			) {
		
		if(!isRoleAdmin()) {
			model.addAttribute("message", "관리자 권한이 아닙니다.");
			model.addAttribute("redirect", "/bdp/main/main.do");
			return "bigdata/error/message";
		}
		
		if(!dlId.equals("")) {
			// row 정보
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dlId", dlId);
			EntityMap row = datasetService.selectBdpDataListRow(param);
			
			HashMap<String, Object> orgPrm = new HashMap<String, Object>();
			List<EntityMap> orgList = datasetService.getBdpOrganization();
			
			model.addAttribute("row", row);
			model.addAttribute("pageIndex", pageIndex);
			// 제공기관
			model.addAttribute("orgList", orgList);
			
		} else {
			model.addAttribute("message", "잘못된 접근입니다.");
			model.addAttribute("redirect", "back");
			return "/bigdata/error/message";
		}

		return "bigdata/portal/dataset/admDataView";
	}
	
	
	/**
	 * 데이터목록 정보를 수정
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dataModify.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String dataModify(
				HttpServletRequest request, 
				Model model
			) {
		
		String mode = String.valueOf(request.getParameter("mode"));
		if(mode.equals("MOD")) {
			int res = 0;
			
			String dlId = String.valueOf(request.getParameter("dlId"));
			String dlName = String.valueOf(request.getParameter("dlName"));
			String dlCateDept1 = String.valueOf(request.getParameter("dlCateDept1"));
			String dlCateDept2 = String.valueOf(request.getParameter("dlCateDept2"));
			String dlComment = String.valueOf(request.getParameter("dlComment"));			
			String dlUseAt = String.valueOf(request.getParameter("dlUseAt"));
			String cpDept = String.valueOf(request.getParameter("cpDept"));
			String cpNm = String.valueOf(request.getParameter("cpNm"));
			String cpTel = String.valueOf(request.getParameter("cpTel"));
			String orgId = String.valueOf(request.getParameter("orgId"));
			

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("dlId", dlId);
			param.put("dlName", dlName);
			param.put("dlCateDept1", dlCateDept1);
			param.put("dlCateDept2", dlCateDept2);
			param.put("dlComment", dlComment);
			param.put("cpDept", cpDept);
			param.put("cpNm", cpNm);
			param.put("cpTel", cpTel);
			param.put("dlUseAt", dlUseAt);
			param.put("orgId", orgId);
			res = datasetService.updateBdpDataListRow(param);
			
			model.addAttribute("result", res);
		} else {
			model.addAttribute("result", "0");
		}
		
		return "jsonView";
	}
}
