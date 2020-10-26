/**
 * 
 */
package kr.co.ucsit.web.util;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.core.CsVO;

/**
 * 세션 관련 유틸리티
 * @author cs1492
 * @date   2018. 2. 9.
 *
 */
public class CsWebSessionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CsWebSessionUtil.class);
	
	public static final String PREFIX = "";
	
	
	/**
	 * @title
	 * @date : 2018. 2. 13.
	 * @param request
	 */
	public static void debug(HttpServletRequest request) {
		if(null == request || null == request.getSession()) {
			LOGGER.debug("request or session is null");
		}
		
		Enumeration<String> en = request.getSession().getAttributeNames();
		String k;
		while(en.hasMoreElements()) {
			k = en.nextElement();
			
			LOGGER.debug("{}\t{}", k, request.getSession().getAttribute(k));
		}
	}


	/**
	 * 세션에 저장된 값을 Object 타입으로 리턴
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object get(HttpServletRequest request, String key) {
		if(null == request || null == request.getSession()) {
			return null;
		}
		
		if(CsUtil.isEmpty(PREFIX+key)) {
			return null;
		}
		
		return (Object)request.getSession().getAttribute(PREFIX+key);
	}
	
	/**
	 * 세션에 저장된 값을 string 타입으로 리턴
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getAsString(HttpServletRequest request, String key) {
		if(null == request || null == request.getSession()) {
			return null;
		}
		
		if(CsUtil.isEmpty(PREFIX+key)) {
			return null;
		}
		
		return (String)request.getSession().getAttribute(PREFIX+key);
	}
	
	
	/**
	 * loginResult에 저장된 사용자 아이디 값 추출
	 * @date : 2018. 2. 9.
	 * @param csMap
	 * @return
	 */
	public static String getEmplyrId(CsMap csMap) {
		if(null == csMap) {
			return null;
		}
		
		return csMap.getString("EMPLYR_ID");
	}
	
	/**
	 * 세션의 loginResult에 저장된 사용자 아이디 값 추출
	 * @date : 2018. 2. 9.
	 * @param request
	 * @return
	 */
	public static String getEmplyrId(HttpServletRequest request) {
		return CsWebSessionUtil.getEmplyrId(CsWebSessionUtil.<CsMap>getT(request, CsWebConst.LOGIN_RESULT));
	}
	

	/**
	 * loginResult에 저장된 사용자 명 값 추출
	 * @date : 2018. 2. 9.
	 * @param csMap
	 * @return
	 */
	public static String getEmplyrNm(CsMap csMap) {
		if(null == csMap) {
			return null;
		}
		
		return csMap.getString("EMPLYR_NM");
	}
	

	/**
	 * 세션의 loginResult에 저장된 사용자 명 값 추출
	 * @date : 2018. 2. 9.
	 * @param request
	 * @return
	 */
	public static String getEmplyrNm(HttpServletRequest request) {
		return CsWebSessionUtil.getEmplyrNm(CsWebSessionUtil.<CsMap>getT(request, CsWebConst.LOGIN_RESULT));
	}
	
	
	/**
	 * 세션에 저장된 로그인 정보 추출
	 * @date : 2018. 2. 9.
	 * @param request
	 * @return
	 */
	public static CsMap getLoginResult(HttpServletRequest request) {
		return CsWebSessionUtil.<CsMap>getT(request, CsWebConst.LOGIN_RESULT);
	}
	
	

	/**
	 * 세션에 저장된 로그인 정보 추출
	 * VO는 CsVO를 상속해야 함
	 * @date : 2018. 2. 9.
	 * @param request
	 * @return
	 */
	public static <T extends CsVO> T getLoginVo(HttpServletRequest request) {
		return CsWebSessionUtil.<T>getT(request, CsWebConst.LOGIN_VO);
	}
	
	
	/**
	 * mymenu getter
	 * @param request
	 * @return
	 */
	public static List<Map<String, Object>> getMyMenu(HttpServletRequest request){
		return CsWebSessionUtil.<List<Map<String,Object>>>getT(request, "myMenu");
	}
	

	/**
	 * 세션에 저장된 프로젝트 명 추출
	 * @date : 2018. 2. 13.
	 * @param request
	 * @return
	 */
	public static String getPrjctNm(HttpServletRequest request) {
		return CsWebSessionUtil.<String>getT(request, "prjctNm");
	}
	
	
	/**
	 * 세션에 저장된 프로젝트 번호 추출
	 * @date : 2018. 2. 13.
	 * @param request
	 * @return
	 */
	public static String getPrjctNo(HttpServletRequest request) {
		return CsWebSessionUtil.<String>getT(request, "prjctNo");
	}

	/**
	 * 세션에 저장된 값을 T 타입으로 리턴
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param key
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getT(HttpServletRequest request, String key) {
		if(null == request || null == request.getSession()) {
			return null;
		}
		
		if(CsUtil.isEmpty(PREFIX+key)) {
			return null;
		}
		
		return (T)CsWebSessionUtil.get(request, key);		
	}
	

	/**
	 * 항목 제거
	 * @param request
	 * @param key
	 */
	public static void remove(HttpServletRequest request, String key) {
		HttpSession ss = request.getSession();
		ss.removeAttribute(PREFIX + key);
	}

	/**
	 * 세션에 값 저장
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param key
	 * @param val
	 */
	public static void set(HttpServletRequest request, String key, Object val) {
		if(null == request || null == request.getSession()) {
			return;
		}
		
		if(CsUtil.isEmpty(key)) {
			return;
		}
		
		request.getSession().setAttribute(PREFIX+key, val);
		
		//
		CsWebSessionUtil.debug(request);
	}


	/**
	 * 로그인 정보 세션에 추가하기
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param response
	 */
	public static void setLoginResult(HttpServletRequest request, CsMap loginResult) {
		CsWebSessionUtil.set(request, CsWebConst.LOGIN_RESULT, loginResult);
	}
	
	/**
	 * 로그인 정보 세션에 추가하기
	 * VO는 CsVO를 상속해야 함
	 * @param request
	 * @param response
	 * @since
	 * 	20200131	init
	 */
	public static <T extends CsVO> void setLoginVo(HttpServletRequest request, T loginVo) {
		CsWebSessionUtil.set(request, CsWebConst.LOGIN_VO, loginVo);
	}
	
	
	/**
	 * mymenu setter
	 * @param request
	 * @param myMenus
	 */
	public static void setMyMenu(HttpServletRequest request, List<Map<String,Object>> myMenus) {
		CsWebSessionUtil.set(request, "myMenu", myMenus);
	}
	
	/**
	 * 세션에 프로젝트 명 추가
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param response
	 */
	public static void setPrjctNm(HttpServletRequest request, String prjctNm) {
		CsWebSessionUtil.set(request, "prjctNm", prjctNm);
	}
	
	
	/**
	 * 세션에 프로젝트 번호 추가
	 * @date : 2018. 2. 9.
	 * @param request
	 * @param response
	 */
	public static void setPrjctNo(HttpServletRequest request, String prjctNo) {
		CsWebSessionUtil.set(request, "prjctNo", prjctNo);
	}
}
