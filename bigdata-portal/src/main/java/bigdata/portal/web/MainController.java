package bigdata.portal.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.AnalysisService;
import bigdata.portal.service.AtchmnflService;
import bigdata.portal.service.DatasetService;
import bigdata.portal.service.HdfsService;
import bigdata.portal.service.HistoryService;
import bigdata.portal.service.NoticeService;
import bigdata.portal.service.PublicDatasetService;
import bigdata.portal.service.UserSettingService;
import bigdata.portal.service.VisualizePblonsipService;
import bigdata.portal.service.VisualizeService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.rte.fdl.property.EgovPropertyService;
import kr.co.ucsit.web.util.CsWebUtil;

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
public class MainController extends CommonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	@Autowired private DatasetService datasetService;
	@Autowired private PublicDatasetService publicDatasetService;
	@Autowired private VisualizeService visualizeService;
	@Autowired private UserSettingService userSettingService;
	@Autowired private HdfsService hdfsService;
	@Autowired private AnalysisService analysisService;
	@Autowired private HistoryService historyService;
	@Autowired private EgovPropertyService propertiesService;

	/**
	 * 첨부파일
	 */
	@Autowired
	private AtchmnflService atchmnflService;

	/**
	 * 시각화 공유
	 */
	@Autowired
	private VisualizePblonsipService visualizePblonsipService;

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;

	@Autowired
	private NoticeService noticeService;


	/**
	 * 최초 페이지 리다이렉션
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String index(Model model) {
		return "redirect:/bdp/main/main.do";
	}



	/**
	 * 메인화면
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bdp/main/main.do", method = RequestMethod.GET)
	public String main(Model model) {
		// 데이터셋 개수
		EntityMap totalCount = null;
		HashMap<String, Object> param = new HashMap<String, Object>();
		//검색

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

		// 빅데이터 셋 개수
		param.put("searchType1", "BDS");
		param.put("dsUseAt", "Y");
		param.put("spceDataSe", "N");
		totalCount = datasetService.getTypeCount(param);
		model.addAttribute("bdsGrpTotal", totalCount.getInteger("listCount"));
		model.addAttribute("bdsTotal", totalCount.getInteger("totalCount"));

		// 통계 목록 개수
		param.put("searchType1", "STA");
		param.put("dsUseAt", "Y");
		param.put("spceDataSe", "N");
		totalCount = datasetService.getTypeCount(param);
		model.addAttribute("staGrpTotal", totalCount.getInteger("listCount"));
		model.addAttribute("staTotal", totalCount.getInteger("totalCount"));

		// 민간데이터 목록 개수
		param.put("searchType1", "PRI");
		param.put("dsUseAt", "Y");
		param.put("spceDataSe", "N");
		totalCount = datasetService.getTypeCount(param);
		model.addAttribute("priGrpTotal", totalCount.getInteger("listCount"));
		model.addAttribute("priTotal", totalCount.getInteger("totalCount"));

		// 공공데이터 목록 개수
		totalCount = publicDatasetService.getTypeCount(param);
		model.addAttribute("pdlGrpTotal", totalCount.getInteger("listCount"));
		model.addAttribute("pdlTotal", totalCount.getInteger("totalCount"));

		param.clear();

		param.put("limit", 3); // 최근 3개
		// 시각화 목록
		param.put("bbsGbn", "V"); // 시각화
		List<Map<String, Object>> visualizeList = CsWebUtil.getDatas(visualizePblonsipService.gets(param));
		for(Map<String, Object> visualize : visualizeList) {
			LOGGER.debug("{}",visualize);
			String atchFileId = visualize.get("atchFileId").toString();
			List<Map<String, Object>> files = atchmnflService.getsByAtchFileId(atchFileId);
			for(Map<String, Object> file : files) {
				String fileNm = file.get("streFileName")+"";
				if(fileNm.lastIndexOf(".") > -1) {
					String extsn = fileNm.substring(fileNm.lastIndexOf("."));
					extsn = extsn.toLowerCase();
					if(fileNm.endsWith(".png") || fileNm.endsWith(".jpg") || fileNm.endsWith(".gif") || fileNm.endsWith(".bmp")) {
						visualize.put("files", files);
					}
				}
			}
		}
		model.addAttribute("visualizeList", visualizeList);


		//공지사항 게시글
		param.put("bbsGbn", "N");
		List<Map<String, Object>> noticeList = CsWebUtil.getDatas(noticeService.gets(param));
		model.addAttribute("noticeList", noticeList);
		//문의사항 게시글
		param.put("bbsGbn", "Q");
		List<Map<String, Object>> questionList = CsWebUtil.getDatas(noticeService.gets(param));
		model.addAttribute("questionList", questionList);
		//신규빅데이터셋 게시글
		param.put("bbsGbn", "B");
		List<Map<String, Object>> newBigdataSetList = CsWebUtil.getDatas(noticeService.gets(param));
		model.addAttribute("newBigdataSetList", newBigdataSetList);

		LOGGER.debug("noticeList:{}",noticeList);
		LOGGER.debug("questionList:{}",questionList);
		LOGGER.debug("newBigdataSetList:{}",newBigdataSetList);



		return "bigdata/portal/main/main";
	}


	@RequestMapping(value = "/bdp/user/mypage.do", method = RequestMethod.GET)
	public String mypage(Model model) {

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		model.addAttribute("author", userInfo.getString("authorCode"));

		return "forward:/uss/umt/EgovMberSelectUpdtView.do?selectedId="+userInfo.getString("esntlId");
	}

	@RequestMapping(value = "/bdp/user/password.do", method = RequestMethod.GET)
	public String passwordChange(Model model) {

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		model.addAttribute("author", userInfo.getString("authorCode"));

		return "forward:/uss/umt/EgovMberPasswordUpdtView.do";
	}

	@RequestMapping(value = "/bdp/user/fileList.do", method = RequestMethod.GET)
	public String fileList(Model model) throws IOException {

		hdfsService.fileSystemOpen();
		System.out.println(hdfsService.getDirPath());

		return "";
	}

	// 내가 실행한 분석, 시각화, 결합데이터 결과 목록보기
	@RequestMapping(value = "/bdp/user/list.do", method = RequestMethod.GET)
	public String analysisList(
				@RequestParam(value="userId", required=false, defaultValue = "") String userId,
				Model model
			) throws IOException {

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();

		HashMap<String, Object> param = new HashMap<String, Object>();

		if(isRoleAdmin()) {
			param.put("userId", userId);
			param.put("anaUserId", userId);
			param.put("anadmUserId", userId);

			if(userId.equals("")) {
				model.addAttribute("message", "잘못된 접근입니다.");
				model.addAttribute("redirect", "/bdp/analysis/createList.do");

				return "bigdata/error/message";
			}
		} else {
			param.put("userId", user.getId());
			param.put("anaUserId", user.getId());
			param.put("anadmUserId", user.getId());
		}
		param.put("startNum", 0);
		param.put("endNum", 10);

		List<EntityMap> analysisList = analysisService.selectBdpAnalysisList(param);
		List<EntityMap> analysisMergeList = analysisService.selectBdpAnalysisDataMergeList(param);
		List<EntityMap> visualizeList = visualizeService.selectVisualizeListMe(param);

		model.addAttribute("analysisList", analysisList);
		model.addAttribute("analysisListSize", analysisList.size());

		model.addAttribute("analysisMergeList", analysisMergeList);
		model.addAttribute("analysisMergeListSize", analysisMergeList.size());

		model.addAttribute("visualizeList", visualizeList);
		model.addAttribute("visualizeListSize", visualizeList.size());

		model.addAttribute("menuNum", 6);
		model.addAttribute("pageNum", 3);

		return "bigdata/portal/mypage/analysisList";
	}


	/**
	 * 파일 업로드 리스트 페이지
	 * @return
	 */
	@RequestMapping(value="/bdp/user/uploadList.do", method = RequestMethod.GET)
	public String mypageUploadList(
				@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
				@RequestParam(value="userId", required=false, defaultValue="") String userId,
				Model model
			) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		List<EntityMap> dataList = null;
		// 관리자
		if(isRoleAdmin()) {
			// 사용자 정보를 파라미터로 받음
		} else {
			userId = getLoginUserId();
		}

		if(userId.equals("") ||  userId.equals("null") || userId == null) {
			 model.addAttribute("message", "잘못된 접근입니다.");
			 model.addAttribute("redirect", "back");
			 return "bigdata/error/message";
		}

		param.put("userId", userId);

		int totalCount = historyService.getTotalCountBdpUserUploadLog(param);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);

		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		dataList = historyService.selectBdpUserUploadLogList(param);

		model.addAttribute("userId", userId);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("dataList", dataList);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);

		model.addAttribute("menuNum", 6);
		model.addAttribute("pageNum", 1);

		return "bigdata/portal/mypage/userUploadList";
	}


	// 회원이 등록한 글(분석/결합/시각화)의 이력 목록 페이지(관리자용)
	@RequestMapping(value = "/bdp/user/articleList.do", method = RequestMethod.GET)
	public String mypageArticleList(
				@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
				@RequestParam(value="userId", required=false, defaultValue="") String userId,
				Model model
			) {
		HashMap<String, Object> param = new HashMap<String, Object>();

		if(!isRoleAdmin()) {
			model.addAttribute("message", "잘못된 접근입니다.");
			model.addAttribute("redirect", "/bdp/analysis/createList.do");

			return "bigdata/error/message";
		} else {
			param.put("userId", userId);
		}

		int totalCount = historyService.getTotalCountBdpUserArticleLog(param);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);

		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> dataList = historyService.selectBdpUserArticleLogList(param);

		model.addAttribute("userId", userId);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("dataList", dataList);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);

		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 1);

		return "bigdata/portal/mypage/userArticleList";
	}
}
