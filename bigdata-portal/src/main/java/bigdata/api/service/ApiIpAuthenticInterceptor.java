package bigdata.api.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.cmm.service.EgovProperties;

/**
 * API 접근 제어 인터셉터
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
 *   2019. 1. 15.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2019. 1. 15.
 */
public class ApiIpAuthenticInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiIpAuthenticInterceptor.class);
	private static String accessIp = EgovProperties.getProperty("okdab.api.access");

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String clientIp = request.getHeader("X-FORWARDED-FOR");
		if (clientIp == null)
			clientIp = request.getRemoteAddr();
		
		LOGGER.debug("access ip = {}, client ip = {}", accessIp, clientIp);

		String [] accessIps = accessIp.split(",");
		for(String allowIp : accessIps) {
			allowIp = allowIp.trim();
			if(allowIp.equals("")) continue;
			
			allowIp = allowIp.replaceAll("[.]", "\\.");
			
			Pattern p = Pattern.compile(allowIp);
			Matcher m = p.matcher(clientIp);
			if(m.find()) return true;
		}

		return false;
	}
}