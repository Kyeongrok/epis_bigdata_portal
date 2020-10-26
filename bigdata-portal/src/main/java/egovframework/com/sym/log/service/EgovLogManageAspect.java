package egovframework.com.sym.log.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.support.BindingAwareModelMap;

import bigdata.portal.service.UserSettingService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.sec.ram.service.AuthorManage;
import egovframework.com.sec.rgm.service.AuthorGroup;
import egovframework.com.sec.rmt.service.RoleManageVO;
import egovframework.com.sym.log.clg.service.Log;
import egovframework.com.sym.log.clg.service.EgovLogService;
import egovframework.com.uss.umt.service.EntrprsManageVO;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserManageVO;

public class EgovLogManageAspect {

	@Resource(name = "egovLogService")
	private EgovLogService logService;	

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService userDetailService;

	@Autowired
	EgovLogService egovSysLogService;

	@Autowired
	UserSettingService userSettingService;

	/**
	 * 로그인 로그정보를 생성한다. EgovLoginController.actionLogin Method 
	 * @param
	 * @return void
	 * @throws Exception
	 */
	public void logLogin() throws Throwable {

		JSONObject log = new JSONObject();
		JSONObject detail = new JSONObject();
		
		String uniqId = "";
		String ip = "";
		String userId = "";

		/* Authenticated */
		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		
		if(user != null) {
			uniqId = user.getUniqId();
			ip = user.getIp();
			userId = user.getId();
	
			detail.put("userId", user.getId());
			detail.put("uniqId", user.getUniqId());
			detail.put("name", user.getName());
			log.put("success.login", detail);
	
			Log logVO = new Log();
			logVO.setLoginId(uniqId);
			logVO.setUserId(userId);
			logVO.setLogDesc(log.toJSONString());
			logVO.setLoginIp(ip);
			logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
			logVO.setErrOccrrAt("N");
			
			System.out.println("loginVO = " + logVO);
			
			logVO.setErrorCode("");
			
			logService.insertLog(logVO);
		}
	}

	/**
	 * 로그아웃 로그정보를 생성한다. EgovLoginController.actionLogout Method
	 * 
	 * @param
	 * @return void
	 * @throws Exception
	 */
	public void logLogout() throws Throwable {

		JSONObject log = new JSONObject();
		JSONObject detail = new JSONObject();

		String uniqId = "";
		String ip = "";
		String userId = "";

		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		System.out.println("Logout user = " + user.toString());
		
		if(user != null) {
			uniqId = user.getUniqId();
			ip = user.getIp();
			userId = user.getId();
	
			detail.put("userId", user.getId());
			detail.put("uniqId", user.getUniqId());
			detail.put("name", user.getName());
			log.put("success.logout", detail);
	
			Log logVO = new Log();
			logVO.setLoginId(uniqId);
			logVO.setUserId(userId);
			logVO.setLogDesc(log.toJSONString());
			logVO.setLoginIp(ip);
			logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
			logVO.setErrOccrrAt("N");
			logVO.setErrorCode("");
			
			logService.insertLog(logVO);		
		}

	}

	/**
	 * 패스워드 변경 로그 등록
	 * @param jp
	 * @throws Throwable
	 */
	public void logPasswordChange(JoinPoint jp) throws Throwable {

		String uniqId = "";
		String ip = "";
		String userId = "";
		String targetId = "";

		JSONObject log = new JSONObject();
		JSONObject detail = new JSONObject();

		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		uniqId = user.getUniqId();
		ip = user.getIp();
		userId = user.getId();
		
		// Message, user, targetId
		BindingAwareModelMap modelMap = null;
		for (int i = 0; i < jp.getArgs().length; i++) {
			if (jp.getArgs()[i] instanceof BindingAwareModelMap) {
				modelMap = (BindingAwareModelMap) jp.getArgs()[i];
			}
		}

		String resultMsg = (String) modelMap.get("resultMsg");
		if (resultMsg.indexOf("fail.user.passwordUpdate") > -1) {
			return;
		}

		if (modelMap.get("mberManageVO") != null) {
			MberManageVO changePasswordUser = (MberManageVO) modelMap.get("mberManageVO");
			targetId = changePasswordUser.getMberId();
		} else if (modelMap.get("entrprsManageVO") != null) {
			EntrprsManageVO changePasswordUser = (EntrprsManageVO) modelMap.get("entrprsManageVO");
			targetId = changePasswordUser.getEntrprsmberId();

		} else if (modelMap.get("userManageVO") != null) {
			UserManageVO changePasswordUser = (UserManageVO) modelMap.get("userManageVO");
			targetId = changePasswordUser.getEmplyrId();
		}

		detail.put("adminId", userId);
		detail.put("targetId", targetId);
		log.put(modelMap.get("log"), detail);

		Log logVO = new Log();
		logVO.setLoginId(uniqId);
		logVO.setUserId(userId);
		logVO.setLogDesc("비밀번호 변경");
		logVO.setLoginIp(ip);
		logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
		logVO.setErrOccrrAt("N");
		logVO.setErrorCode("");
		
		logService.insertLog(logVO);		

	}

	/**
	 * 권한 등록 / 수정이력 추가 
	 * @param jp
	 * @throws Throwable
	 */
	public void logAuthor(JoinPoint jp) throws Throwable {

		JSONObject log = new JSONObject();
		JSONArray logArray = new JSONArray();
		
		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		BindingAwareModelMap modelMap = null;
		ArrayList<AuthorManage> authorManageList = null;

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		String uniqId = user.getUniqId();
		String ip = user.getIp();
		String userId = user.getId();

		for (int i = 0; i < jp.getArgs().length; i++) {
			if (jp.getArgs()[i] instanceof BindingAwareModelMap) {
				modelMap = (BindingAwareModelMap) jp.getArgs()[i];
			}
		}
				
		if(modelMap.get("authorManage") != null) {
			authorManageList = (ArrayList<AuthorManage>) modelMap.get("authorManageList");
	
			for(int i=0; i<authorManageList.size(); i++) {
				JSONObject detail = new JSONObject();
				detail.put("authorNm", authorManageList.get(i).getAuthorNm());
				detail.put("authorDc", authorManageList.get(i).getAuthorDc());
				logArray.add(detail);
			}
		}
		
		Log logVO = new Log();
		logVO.setLoginId(uniqId);
		logVO.setUserId(userId);
		logVO.setLogDesc("정책정보 변경");
		logVO.setLoginIp(ip);
		logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
		logVO.setErrOccrrAt("N");
		logVO.setErrorCode("");
		
		logService.insertLog(logVO);		

	}

	/**
	 * 계정 생성이력 추가 
	 * @param jp
	 * @throws Throwable
	 */
	public void logAccount(JoinPoint jp) throws Throwable {

		JSONObject log = new JSONObject();
		String msg = null;

		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		String uniqId = user.getUniqId();
		String ip = user.getIp();
		String userId = user.getId();

		// Message, user, targetId
		BindingAwareModelMap modelMap = null;
		JSONArray logArray = new JSONArray();	

		for (int i = 0; i < jp.getArgs().length; i++) {
			if (jp.getArgs()[i] instanceof BindingAwareModelMap) {
				modelMap = (BindingAwareModelMap) jp.getArgs()[i];
			}
		}

		if (modelMap.get("mberManageVO") != null) {
			ArrayList<MberManageVO> mberManageList = (ArrayList<MberManageVO>) modelMap.get("mberManageList");
			
			for(int i=0; i<mberManageList.size(); i++) {
				JSONObject detail = new JSONObject();
				detail.put("adminId", user.getId());
				detail.put("targetId", mberManageList.get(i).getMberId());
				logArray.add(detail);			
			}

			log.put(modelMap.get("log"), logArray);
			
			if(modelMap.get("log").equals("success.account.update")) {
				msg = "회원정보 수정";				
			} else if (modelMap.get("log").equals("success.account.insert")) {
				msg = "신규 회원등록";
			} else if (modelMap.get("log").equals("success.account.delete")) {
				msg = "회원정보 삭제";
			}
			
		}

		if (modelMap.get("entrprsManageVO") != null) {
			ArrayList<EntrprsManageVO> entrprsManageList = (ArrayList<EntrprsManageVO>) modelMap.get("entrprsManageList");
				
			for(int i=0; i < entrprsManageList.size(); i++) {
				JSONObject detail = new JSONObject();
				detail.put("adminId", user.getId());
				detail.put("targetId", entrprsManageList.get(i).getEntrprsmberId());
				logArray.add(detail);			
			}

			log.put(modelMap.get("log"), logArray);			
		}

		if (modelMap.get("userManageVO") != null) {
			ArrayList<UserManageVO> userManageList = (ArrayList<UserManageVO>) modelMap.get("userManageList");
			
			for(int i=0; i < userManageList.size(); i++) {
				JSONObject detail = new JSONObject();
				detail.put("adminId", user.getId());
				detail.put("targetId",userManageList.get(i).getEmplyrId());
				logArray.add(detail);			
			}

			log.put(modelMap.get("log"), logArray);			
		}
		Log logVO = new Log();
		logVO.setLoginId(uniqId);
		logVO.setUserId(userId);
		logVO.setLogDesc(msg);
		logVO.setLoginIp(ip);
		logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
		logVO.setErrOccrrAt("N");
		logVO.setErrorCode("");
		
		logService.insertLog(logVO);		

	}
	
	/**
	 * Role 등록 
	 * @param jp
	 * @throws Throwable
	 */
	public void logRole(JoinPoint jp) throws Throwable {

		JSONObject log = new JSONObject();
		
		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		String uniqId = user.getUniqId();
		String ip = user.getIp();
		String userId = user.getId();

		BindingAwareModelMap modelMap = null;
		for (int i = 0; i < jp.getArgs().length; i++) {
			if (jp.getArgs()[i] instanceof BindingAwareModelMap) {
				modelMap = (BindingAwareModelMap) jp.getArgs()[i];
			}
		}

		ArrayList<RoleManageVO> roleManageVOList = (ArrayList<RoleManageVO>) modelMap.get("roleManageVOList");
		
		JSONArray logArray = new JSONArray();
		for (int i = 0; i < roleManageVOList.size(); i++) {
			JSONObject detail = new JSONObject();
			detail.put("roleCode", roleManageVOList.get(i).getRoleCode());
			detail.put("roleNm", roleManageVOList.get(i).getRoleNm());
			detail.put("roleDc", roleManageVOList.get(i).getRoleDc());

			logArray.add(detail);
		}

		Log logVO = new Log();
		logVO.setLoginId(uniqId);
		logVO.setUserId(userId);
		logVO.setLogDesc("정책정보 변경");
		logVO.setLoginIp(ip);
		logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
		logVO.setErrOccrrAt("N");
		logVO.setErrorCode("");
		
		logService.insertLog(logVO);		

	}
	
	/**
	 * 그룹으로 나뉘어진 권한에 유저 등록
	 * @param jp
	 * @throws Throwable
	 */
	public void logAuthorGroup(JoinPoint jp) throws Throwable {
		
		JSONObject log = new JSONObject();
		JSONArray logArray = new JSONArray();
		
		Boolean isAuthenticated = userDetailService.isAuthenticated();
		if (!isAuthenticated.booleanValue()) {
			return;
		}

		LoginVO user = (LoginVO) userDetailService.getAuthenticatedUser();
		String uniqId = user.getUniqId();
		String ip = user.getIp();
		String userId = user.getId();

		BindingAwareModelMap modelMap = null;
		for (int i = 0; i < jp.getArgs().length; i++) {
			if (jp.getArgs()[i] instanceof BindingAwareModelMap) {
				modelMap = (BindingAwareModelMap) jp.getArgs()[i];
			}
		}
		
		ArrayList<AuthorGroup> authorGroupList = (ArrayList<AuthorGroup>) modelMap.get("authorGroupList");
		
		for (int i=0; i<authorGroupList.size(); i++) {
			JSONObject detail = new JSONObject();
			detail.put("userId", authorGroupList.get(i).getUserId());
			detail.put("uniqId", authorGroupList.get(i).getUniqId());
			detail.put("authorCode", authorGroupList.get(i).getAuthorCode());
			detail.put("mberTyCode", authorGroupList.get(i).getMberTyCode());
			logArray.add(detail);			
		}
		
		log.put(modelMap.get("log"), logArray);
		
		String msg = null;
		
		if(modelMap.get("log").equals("success.authorGroup.update")) {
			msg = "권한설정 변경";
		} 
		
		Log logVO = new Log();
		logVO.setLoginId(uniqId);
		logVO.setUserId(userId);
		logVO.setLogDesc(msg);
		logVO.setLoginIp(ip);
		logVO.setLoginMthd(""); // 로그인:I, 로그아웃:O
		logVO.setErrOccrrAt("N");
		logVO.setErrorCode("");
		
		logService.insertLog(logVO);		
		
	}
}