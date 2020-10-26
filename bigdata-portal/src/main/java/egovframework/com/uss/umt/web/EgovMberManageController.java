package egovframework.com.uss.umt.web;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.FileManageService;
import bigdata.portal.service.UserSettingService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 일반회원관련 요청을 비지니스 클래스로 전달하고 처리된결과를 해당 웹 화면으로 전달하는 Controller를 정의한다
 *
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @seeF
 *
 *       <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.08.26	 정진오			IncludedInfo annotation 추가
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *
 *       </pre>
 */

@Controller
public class EgovMberManageController {

	/** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** mberManageService **/
	@Resource(name = "mberManageService")
	private EgovMberManageService mberManageService;

	/** cmmUseService **/
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovPropertyService **/
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** DefaultBeanValidator beanValidator **/
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** ExtendUserViewService **/
	//@Autowired
	//private ExtendUserViewService extendUserViewService;

	/** HdfsService **/
	//@Autowired
	//private HdfsService hdfsService;

	@Autowired
	private UserSettingService userSettingService;

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;

	@Resource(name = "egovAuthorGroupService")
	private EgovAuthorGroupService egovAuthorGroupService;

	@Autowired
	private FileManageService fileManageService;

	/**
	 * 일반회원목록을 조회한다. (pageing)
	 *
	 * @param userSearchVO
	 *            검색조건정보
	 * @param model
	 *            화면모델
	 * @return uss/umt/EgovMberManage
	 * @throws Exception
	 */

	@IncludedInfo(name = "일반회원관리", order = 470, gid = 50)
	@RequestMapping(value = "/uss/umt/EgovMberManage.do")
	public String selectMberList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model)
			throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */



		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		pageInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		pageInfo.setPageSize(userSearchVO.getPageSize());
		int articleNo = pageInfo.getArticleNo();

		userSearchVO.setFirstIndex(pageInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(pageInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(pageInfo.getRecordCountPerPage());

		List<?> mberList = mberManageService.selectMberList(userSearchVO);
		model.addAttribute("resultList", mberList);

		int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
		pageInfo.setTotalRecordCount(totCnt);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", userSearchVO.getPageIndex());
		model.addAttribute("totalCount", totCnt);

		// 일반회원 상태코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("entrprsMberSttus_result", mberSttus_result);// 기업회원상태코드목록

		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 1);

		return "egovframework/com/uss/umt/EgovMberManage";
	}

	/**
	 * 일반회원등록화면으로 이동한다.
	 *
	 * @param userSearchVO
	 *            검색조건정보
	 * @param mberManageVO
	 *            일반회원초기화정보
	 * @param model
	 *            화면모델
	 * @return uss/umt/EgovMberInsert
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberInsertView.do")
	public String insertMberView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
		// 사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		// 그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
		model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록

		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 1);

		return "egovframework/com/uss/umt/EgovMberInsert";
	}

	/**
	 * 일반회원등록처리후 목록화면으로 이동한다.
	 *
	 * @param mberManageVO
	 *            일반회원등록정보
	 * @param bindingResult
	 *            입력값검증용 bindingResult
	 * @param model
	 *            화면모델
	 * @return forward:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberInsert.do")
	public String insertMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, BindingResult bindingResult,
			Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		ArrayList<MberManageVO> mberManageVOList = new ArrayList<MberManageVO>();

		if (!isAuthenticated) {
			return "index";
		}

		beanValidator.validate(mberManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "egovframework/com/uss/umt/EgovMberInsert";
		} else {
			if (mberManageVO.getGroupId() == null || mberManageVO.getGroupId().equals("")) {
				mberManageVO.setGroupId(null);
			}

			// TODO: setUserInfo 구현
			if (userSettingService.selectUserInfo(mberManageVO.getMberId()) != null) {
				model.addAttribute("resultMsg", "fail.common.insert");

				return "forward:/uss/umt/EgovMberManage.do";
			}

			mberManageService.insertMbers(mberManageVO);
			mberManageVOList.add(mberManageVO);

			// Exception 없이 진행시 등록 성공메시지
			model.addAttribute("resultMsg", "success.common.insert");
			model.addAttribute("log", "success.account.insert");
			model.addAttribute("mberManageList", mberManageVOList);
		}
		return "forward:/uss/umt/EgovMberManage.do";
	}

	/**
	 * 일반회원정보 수정을 위해 일반회원정보를 상세조회한다.
	 *
	 * @param mberId
	 *            상세조회대상 일반회원아이디
	 * @param userSearchVO
	 *            검색조건
	 * @param model
	 *            화면모델
	 * @return uss/umt/EgovMberSelectUpdt
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberSelectUpdtView.do")
	public String updateMberView(@RequestParam("selectedId") String mberId,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}

		// 미인증 사용자에 대한 리다이렉트 처리
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		if(!userInfo.getString("authorCode").equals("ROLE_ADMIN")) {
			if(!mberId.equals(userInfo.getString("esntlId"))) {
				return "redirect:/";
			}
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);

		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);

		// 사용자상태코드를 코드정보로부터 조회
		vo.setCodeId("COM013");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);

		// 그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		model.addAttribute("mberSttus_result", mberSttus_result); // 사용자상태코드목록
		model.addAttribute("groupId_result", groupId_result); // 그룹정보 목록

		MberManageVO mberManageVO = mberManageService.selectMber(mberId);
		model.addAttribute("mberManageVO", mberManageVO);
		model.addAttribute("userSearchVO", userSearchVO);

		// TODO Start : check hdfs 기능
		//long dirSize = hdfsService.getDirSize(mberManageVO.getMberId());
		//long quotaSize = hdfsService.getQuotaSize(mberManageVO.getMberId());

		//model.addAttribute("dirSize", dirSize);
		//model.addAttribute("quotaSize", (long)(quotaSize / Math.pow(1024L, 3)));
		//model.addAttribute("viewDirSizeMB", (long)(dirSize / Math.pow(1024L, 2)));
		//model.addAttribute("viewQuotaSizeMB", (long)(quotaSize / Math.pow(1024L, 3)));
		// TODO END : check hdfs 기능

		model.addAttribute("author", userInfo.getString("authorCode"));

		if(userInfo.getString("authorCode").equals("ROLE_ADMIN")) {
			model.addAttribute("menuNum", 5);
			model.addAttribute("pageNum", 1);
		} else {
			model.addAttribute("menuNum", 6);
			model.addAttribute("pageNum", 1);
		}

		return "egovframework/com/uss/umt/EgovMberSelectUpdt";
	}

	/**
	 * 일반회원정보 수정후 목록조회 화면으로 이동한다.
	 *
	 * @param mberManageVO
	 *            일반회원수정정보
	 * @param bindingResult
	 *            입력값검증용 bindingResult
	 * @param model
	 *            화면모델
	 * @return forward:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberSelectUpdt.do")
	public String updateMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, BindingResult bindingResult,
			Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리

		ArrayList<MberManageVO> mberManageVOList = new ArrayList<MberManageVO>();
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		beanValidator.validate(mberManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "egovframework/com/uss/umt/EgovEntrprsMberSelectUpdt";
		} else {
			if (mberManageVO.getGroupId() == null || mberManageVO.getGroupId().equals("")) {
				mberManageVO.setGroupId(null);
			}

			// TODO Start : check hdfs 기능
			// Update to HDFS Quota & Size & UserInfo
			/*
			long curQuotaSize = (hdfsService.getQuotaSize(mberManageVO.getMberId())) / (1024L * 1024L * 1024L);
			long changeQuotaSize = mberManageVO.getQuotaSize();

			// TODO: 현재 Byte 단위로 계산되지만, MB 단위로 계산되도록 설정 변경해야 함.
			// HDFS SpaceQuota Size change
			if(curQuotaSize != changeQuotaSize) {
				hdfsService.setQuotaSize(mberManageVO.getMberId(), (changeQuotaSize * (1024L * 1024L * 1024L)));
			}
			*/
			// TODO End : check hdfs 기능

			mberManageService.updateMber(mberManageVO);
			mberManageVOList.add(mberManageVO);

			// Exception 없이 진행시 수정성공메시지
			model.addAttribute("resultMsg", "success.common.update");
			model.addAttribute("log", "success.account.update");
			model.addAttribute("mberManageList", mberManageVOList);

			model.addAttribute("status", "success");

			String forwardUrl = String.format("forward:/uss/umt/EgovMberSelectUpdtView.do?selectedId=%s",
					mberManageVO.getUniqId());

			return forwardUrl;
		}
	}

	/**
	 * 일반회원정보삭제후 목록조회 화면으로 이동한다.
	 *
	 * @param checkedIdForDel
	 *            삭제대상 아이디 정보
	 * @param userSearchVO
	 *            검색조건정보
	 * @param model
	 *            화면모델
	 * @return forward:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberDelete.do")
	public String deleteMber(@RequestParam("checkedIdForDel") String checkedIdForDel,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {

		ArrayList<MberManageVO> mberList = null;
		System.out.println("checkedIdForDel = " + checkedIdForDel);

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		try {
			mberList = mberManageService.selectMbers(checkedIdForDel);
			mberManageService.deleteMber(checkedIdForDel);

			if (mberList != null) {
				for (MberManageVO mber : mberList) {
					fileManageService.deleteUserDB(mber.getMberId());
					// TODO Start : check hdfs, ambari 기능
					/*
					hdfsService.removeHdfsUserDir(mber.getMberId());
					extendUserViewService.removeAmbariUser(mber.getMberId());
					extendUserViewService.removeRStudioAccount(mber.getMberId());
					*/
					// TODO End : check hdfs, ambari 기능
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// DB 트랜잭션 실행
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			model.addAttribute("resultMsg", "fail.common.delete");
			return "forward:/uss/umt/EgovMberManage.do";
		}

		// 미인증 사용자에 대한 리다이렉트 처리
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		if(userInfo == null) {
			return "redirect:/bdp/auth/logout.do";
		}


		// Exception 없이 진행시 삭제성공메시지
		model.addAttribute("resultMsg", "success.common.delete");
		model.addAttribute("log", "success.account.delete");
    	model.addAttribute("mberManageVO", " ");
    	model.addAttribute("mberManageList", mberList);

		return "forward:/uss/umt/EgovMberManage.do";
	}

	// 탈퇴 처리 기능에 대한 예시
	@RequestMapping("/uss/umt/EgovMberWithdraw.do")
	public String withdrawMber(Model model) throws Exception {
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();

		String returnPage = "/"; // 탈퇴 처리 후 화면 지정

		if (!isAuthenticated) {
			model.addAttribute("resultMsg", "fail.common.delete");

			return "redirect:" + returnPage;
		}

		mberManageService.deleteMber(user.getUniqId());
		// Exception 없이 진행시 삭제성공메시지
		model.addAttribute("resultMsg", "success.common.delete");

		return "redirect:" + returnPage;
	}

	/**
	 * 일반회원가입신청 등록화면으로 이동한다.
	 *
	 * @param userSearchVO
	 *            검색조건
	 * @param mberManageVO
	 *            일반회원가입신청정보
	 * @param commandMap
	 *            파라메터전달용 commandMap
	 * @param model
	 *            화면모델
	 * @return uss/umt/EgovMberSbscrb
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberSbscrbView.do")
	public String sbscrbMberView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO, @RequestParam Map<String, Object> commandMap,
			Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		// 패스워드힌트목록을 코드정보로부터 조회
		vo.setCodeId("COM022");
		List<?> passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
		// 성별구분코드를 코드정보로부터 조회
		vo.setCodeId("COM014");
		List<?> sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("passwordHint_result", passwordHint_result); // 패스워트힌트목록
		model.addAttribute("sexdstnCode_result", sexdstnCode_result); // 성별구분코드목록
		if (!"".equals(commandMap.get("realname"))) {
			model.addAttribute("mberNm", commandMap.get("realname")); // 실명인증된
																		// 이름 -
																		// 주민번호
																		// 인증
			model.addAttribute("ihidnum", commandMap.get("ihidnum")); // 실명인증된
																		// 주민등록번호
																		// -
																		// 주민번호
																		// 인증
		}
		if (!"".equals(commandMap.get("realName"))) {
			model.addAttribute("mberNm", commandMap.get("realName")); // 실명인증된
																		// 이름 -
																		// ipin인증
		}

		mberManageVO.setMberSttus("DEFAULT");

		return "egovframework/com/uss/umt/EgovMberSbscrb";
	}

	/**
	 * 일반회원가입신청등록처리후로그인화면으로 이동한다.
	 *
	 * @param mberManageVO
	 *            일반회원가입신청정보
	 * @return forward:/uat/uia/egovLoginUsr.do
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovMberSbscrb.do")
	public String sbscrbMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		// 가입상태 초기화
		mberManageVO.setMberSttus("A");
		// 그룹정보 초기화
		// mberManageVO.setGroupId("1");
		// 일반회원가입신청 등록시 일반회원등록기능을 사용하여 등록한다.
		mberManageService.insertMber(mberManageVO);
		return "forward:/uat/uia/egovLoginUsr.do";
	}




	/**
	 * 일반회원 약관확인
	 *
	 * @param model
	 *            화면모델
	 * @return uss/umt/EgovStplatCnfirm
	 * @throws Exception
	 * */

	@RequestMapping("/uss/umt/EgovStplatCnfirmMber.do")
	public String sbscrbEntrprsMber(Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		// 일반회원용 약관 아이디 설정
		String stplatId = "STPLAT_0000000000001";
		// 회원가입유형 설정-일반회원
		String sbscrbTy = "USR01";
		// 약관정보 조회
		List<?> stplatList = mberManageService.selectStplat(stplatId);
		model.addAttribute("stplatList", stplatList); // 약관정보 포함
		model.addAttribute("sbscrbTy", sbscrbTy); // 회원가입유형 포함

		return "egovframework/com/uss/umt/EgovStplatCnfirm";
	}

	/**
	 * @param model
	 *            화면모델
	 * @param commandMap
	 *            파라메터전달용 commandMap
	 * @param userSearchVO
	 *            검색조건
	 * @param mberManageVO
	 *            일반회원수정정보(비밀번호)
	 * @return uss/umt/EgovMberPasswordUpdt
	 * @throws Exception
	 * */

	@RequestMapping(value = "/uss/umt/EgovMberPasswordUpdt.do")
	public String updatePassword(ModelMap model, @RequestParam Map<String, Object> commandMap,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String oldPassword = (String) commandMap.get("oldPassword");
		String newPassword = (String) commandMap.get("newPassword");
		String newPassword2 = (String) commandMap.get("newPassword2");
		String uniqId = (String) commandMap.get("uniqId");

		boolean isCorrectPassword = false;
		MberManageVO resultVO = new MberManageVO();
		mberManageVO.setPassword(newPassword);
		mberManageVO.setOldPassword(oldPassword);
		mberManageVO.setUniqId(uniqId);

		String resultMsg = "";
		resultVO = mberManageService.selectPassword(mberManageVO);
		// 패스워드 암호화
		String encryptPass = EgovFileScrty.encryptPassword(oldPassword, mberManageVO.getMberId());
		if (encryptPass.equals(resultVO.getPassword())) {
			if (newPassword.equals(newPassword2)) {
				isCorrectPassword = true;
			} else {
				isCorrectPassword = false;
				resultMsg = "fail.user.passwordUpdate2";
			}
		} else {
			isCorrectPassword = false;
			resultMsg = "fail.user.passwordUpdate1";
		}

		if (isCorrectPassword) {
			mberManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword, mberManageVO.getMberId()));
			mberManageService.updatePassword(mberManageVO);
			/* TODO : check Rstudio, ambari
			extendUserViewService.changeRStudioAccountPassword(mberManageVO.getMberId(), newPassword);
			extendUserViewService.changeAmbariPassword(mberManageVO.getMberId(), newPassword);
			*/

			model.addAttribute("mberManageVO", mberManageVO);
			resultMsg = "success.common.update";
		} else {
			model.addAttribute("mberManageVO", mberManageVO);
		}
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("log", "success.password.update");

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		if(userInfo.getString("authorCode").equals("ROLE_ADMIN")) {
			model.addAttribute("menuNum", 5);
			model.addAttribute("pageNum", 1);

		} else {
			model.addAttribute("menuNum", 6);
			model.addAttribute("pageNum", 1);
		}


		return "egovframework/com/uss/umt/EgovMberPasswordUpdt";
	}

	/**
	 * 일반회원 암호 수정 화면 이동
	 *
	 * @param model
	 *            화면모델
	 * @param commandMap
	 *            파라메터전달용 commandMap
	 * @param userSearchVO
	 *            검색조건
	 * @param mberManageVO
	 *            일반회원수정정보(비밀번호)
	 * @return uss/umt/EgovMberPasswordUpdt
	 * @throws Exception
	 * */

	@RequestMapping(value = "/uss/umt/EgovMberPasswordUpdtView.do")
	public String updatePasswordView(ModelMap model, @RequestParam Map<String, Object> commandMap,
			@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String userTyForPassword = (String) commandMap.get("userTyForPassword");
		mberManageVO.setUserTy(userTyForPassword);

		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("mberManageVO", mberManageVO);

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = userSettingService.selectUserInfo(user.getId());

		if(userInfo.getString("authorCode").equals("ROLE_ADMIN")) {
			model.addAttribute("menuNum", 5);
			model.addAttribute("pageNum", 1);

		} else {
			model.addAttribute("menuNum", 6);
			model.addAttribute("pageNum", 1);
		}


		return "egovframework/com/uss/umt/EgovMberPasswordUpdt";
	}


	@RequestMapping(value = "/uss/umt/EgovIdCheck.do")
	public @ResponseBody ResponseEntity<String> checkIdDplct(@RequestParam Map<String, Object> commandMap, ModelMap model) throws Exception {

		// HEADER 설정
		HttpHeaders header = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
		header.setContentType(mediaType);

		// 반환을 위한 JSON Object 생성
		JSONObject userInfo = new JSONObject();

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();
		if (!isAuthenticated) {
			// return "index";
		}

		String checkId = (String) commandMap.get("checkId");
		checkId = new String(checkId.getBytes("ISO-8859-1"), "UTF-8");

		if (checkId == null || checkId.equals("")) {
			userInfo.put("statusMessage", "아이디를 입력 해 주세요.");
			return new ResponseEntity<String>(JSONObject.toJSONString(userInfo), header, HttpStatus.OK);
		}

		int usedCnt = userManageService.checkIdDplct(checkId);
		if(usedCnt > 0) {
			userInfo.put("statusMessage", "중복된 아이디가 존재합니다.");
			return new ResponseEntity<String>(JSONObject.toJSONString(userInfo), header, HttpStatus.OK);
		}

		userInfo.put("statusMessage", "사용 가능한 아이디입니다.");
		userInfo.put("status", true);
		return new ResponseEntity<String>(JSONObject.toJSONString(userInfo), header, HttpStatus.OK);
	}



	/**
	 * 농업on에서 '빅데이터 플랫폼 사용'버튼 클릭 시
	 * 농업on 계정 정보를 빅데이터 플랫폼에 등록 함
	 *
	 * @param mberManageVO
	 *            일반회원가입신청정보
	 * @return result_code
	 * @throws Exception
	 * */


	@RequestMapping(value = "/member/join/copyUserAagrion.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String farmOnSbscrbMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, HttpServletRequest request, Model model) throws Exception {

		try {

			// 가입상태 초기화
			mberManageVO.setMberSttus("P");
			// 그룹정보 초기화
			// mberManageVO.setGroupId("1");

			//중복 검사
			if(userManageService.checkIdDplct(mberManageVO.getMberId()) == 1) {
				model.addAttribute("result_code", "P"); // 이미 사용 중일 경우
				return "jsonView";
			}

			//아이디 등록
			mberManageService.insertMbers(mberManageVO);

			//권한등록

			model.addAttribute("result_code", "S"); // 성공
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("result_code", "F"); // 실패
		}
		return "jsonView";
	}

}