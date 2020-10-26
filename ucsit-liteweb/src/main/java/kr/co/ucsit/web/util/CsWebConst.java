package kr.co.ucsit.web.util;

import kr.co.ucsit.core.CsConst;

/**
 * 상수 정의
 * @author	cs1492
 * @since	2017. 10. 25.
 */
public class CsWebConst extends CsConst{
	
	
	public static final String TOTCNT = "totcnt";
	public static final String DATAS = "datas";
	public static final String DATA = "data";
	
	/**
	 * 엑셀 다운로드시 사용
	 */
	public static final int PAGE_SIZE_MAX = 99999;
	
	/**
	 * date format - yyyyMMdd
	 */
	public static final String DF_YMD = "yyyyMMdd";
	
	/**
	 * date format - yyyyMMddHHmmss
	 */
	public static final String DF_YMDHMS = "yyyyMMddHHmmss";

	public static final String PARAMETER_NAME = "paramterMap";
	
	/**
	 * 세션에 저장되는 로그인 정보's key
	 */
	public static final String LOGIN_RESULT= "loginResult";
	
	/**
	 * Map,Session등에 저장되는 로그인 정보 vo's key 
	 */
	public static final String LOGIN_VO= "loginVo";
	
	public static final String CSRF_TOKEN = "csrfToken";

	public static final String SUCCESS = "success";
	
	public static final String FAILED = "failed";
	
	public static final String ACCOUNT_LOCK = "lock";
	
	public static final String RESULT_MESSAGE = "resultMessage";
	public static final String RESULT_CODE = "resultCode";
	
	
	public static final String[] encryptKeys = new String[] {"telno", "mbtlnum", "emailAdresNm"};
}
