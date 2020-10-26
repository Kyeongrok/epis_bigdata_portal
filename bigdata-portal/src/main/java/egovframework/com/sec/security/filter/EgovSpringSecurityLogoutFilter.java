package egovframework.com.sec.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.service.HdfsService;
import egovframework.com.sym.log.service.EgovLogManageAspect;


/**
 *
 * @author 공통서비스 개발팀 서준식
 * @since 2011. 8. 29.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information)
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011. 8. 29.    서준식        최초생성
 *
 *  </pre>
 */

public class EgovSpringSecurityLogoutFilter implements Filter{

	@SuppressWarnings("unused")
	private FilterConfig config;
	
	@Autowired
	private HdfsService hdfsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSpringSecurityLogoutFilter.class);

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String requestURL = ((HttpServletRequest)request).getRequestURI();
		LOGGER.debug(requestURL);
		
		/*
		((HttpServletRequest) request).getSession().setAttribute("loginVO", null);
		((HttpServletRequest) request).getSession().setAttribute("package", null);	
		((HttpServletRequest) request).getSession().setAttribute("loginUserId", null);	
		((HttpServletRequest) request).getSession().setAttribute("loginUserName", null);	
		((HttpServletRequest) request).getSession().setAttribute("isLogin", null);	
		((HttpServletRequest) request).getSession().setAttribute("isAdmin", null);
		((HttpServletRequest) request).getSession().setAttribute("isRoleAdmin", null);
		*/
		
		// ((HttpServletResponse) response).sendRedirect(((HttpServletRequest)request).getContextPath() + "/j_spring_security_logout");
		
		((HttpServletResponse) response).sendRedirect(((HttpServletRequest)request).getContextPath() + "/bdp/auth/logout.do");

		
		// hdfsService.destory();

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;

	}
}
