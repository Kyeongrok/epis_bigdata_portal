package egovframework.com.sec.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.UserSettingService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.uat.uap.service.EgovLoginPolicyService;
import egovframework.com.uat.uap.service.LoginPolicyVO;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.sim.service.EgovClntInfo;

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
 *     수정일                 수정자        	  수정내용
 *  -----------    --------   ---------------------------
 *  2011.08.29    	 서준식        	 최초생성
 *  2011.12.12      유지보수        사용자 로그인 정보 간섭 가능성 문제(멤버 변수 EgovUserDetails userDetails를 로컬변수로 변경)
 *  2014.03.07      유지보수        로그인된 상태에서 다시 로그인 시 미처리 되는 문제 수정 (로그인 처리 URL 파라미터화)
 *  2018.10.25		DHKim	 외부 페이지 로그인을 위한 페이지 세션 생성 소스 추가
 *
 *  </pre>
 */

public class EgovSpringSecurityLoginFilter implements Filter {

	private FilterConfig config;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSpringSecurityLoginFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		LOGGER.info("EgovSpringSecurityLoginFilter called...");

		// 로그인 URL
		String loginURL = config.getInitParameter("loginURL");
		loginURL = loginURL.replaceAll("\r", "").replaceAll("\n", "");

		String loginProcessURL = config.getInitParameter("loginProcessURL");
		loginProcessURL = loginProcessURL.replaceAll("\r", "").replaceAll("\n", "");

		ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		EgovLoginService loginService = (EgovLoginService) act.getBean("loginService");
		EgovMessageSource egovMessageSource = (EgovMessageSource) act.getBean("egovMessageSource");
		/* TODO : ambari 제거
		ExtendUserViewService extendUserViewService = (ExtendUserViewService) act.getBean("extendUserViewService");
		*/
		EgovUserDetailsService egovUserDetailsService = (EgovUserDetailsService) act.getBean("egovUserDetailsService");
		UserSettingService userSettingService = (UserSettingService) act.getBean("userSettingService");
		EgovLoginPolicyService egovLoginPolicyService = (EgovLoginPolicyService) act.getBean("egovLoginPolicyService");


		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		//String isLocallyAuthenticated = (String)session.getAttribute("isLocallyAuthenticated");
		String isRemotelyAuthenticated = (String) session.getAttribute("isRemotelyAuthenticated");

		String requestURL = ((HttpServletRequest) request).getRequestURI();

		//스프링 시큐리티 인증이 처리 되었는지 EgovUserDetailsHelper.getAuthenticatedUser() 메서드를 통해 확인한다.
		//context-common.xml 빈 설정에 egovUserDetailsSecurityService를 등록 해서 사용해야 정상적으로 동작한다.
		if (egovUserDetailsService.getAuthenticatedUser() == null || requestURL.contains(loginProcessURL)) {

			if (isRemotelyAuthenticated != null && isRemotelyAuthenticated.equals("true")) {
				try {
					//세션 토큰 정보를 가지고 DB로부터 사용자 정보를 가져옴
					LoginVO loginVO = (LoginVO) session.getAttribute("loginVOForDBAuthentication");
					loginVO = loginService.actionLoginByEsntlId(loginVO);

					if (loginVO != null && loginVO.getId() != null && !loginVO.getId().equals("")) {
						//세션 로그인
						session.setAttribute("loginVO", loginVO);

						//로컬 인증결과 세션에 저장
						session.setAttribute("isLocallyAuthenticated", "true");

						//스프링 시큐리티 로그인
						//httpResponse.sendRedirect(httpRequest.getContextPath() + "/j_spring_security_check?j_username=" + loginVO.getUserSe() + loginVO.getId() + "&j_password=" + loginVO.getUniqId());

						UsernamePasswordAuthenticationFilter springSecurity = null;

						Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
						if (beans.size() > 0) {
							springSecurity = (UsernamePasswordAuthenticationFilter) beans.values().toArray()[0];
						} else {
							LOGGER.error("No AuthenticationProcessingFilter");
							throw new IllegalStateException("No AuthenticationProcessingFilter");
						}
						//springSecurity.setContinueChainBeforeSuccessfulAuthentication(false);	// false 이면 chain 처리 되지 않음.. (filter가 아닌 경우 false로...)

						LOGGER.debug("before security filter call....");
						springSecurity.doFilter(new RequestWrapperForSecurity(httpRequest, loginVO.getUserSe() + loginVO.getId(), loginVO.getUniqId()), httpResponse, chain);
						LOGGER.debug("after security filter call....");

					}

				} catch (Exception ex) {
					//DB인증 예외가 발생할 경우 로그를 남기고 로컬인증을 시키지 않고 그대로 진행함.
					LOGGER.debug("Local authentication Fail : {}", ex.getMessage());
				}

			} else if (isRemotelyAuthenticated == null) {
				if (requestURL.contains(loginProcessURL)) {

					boolean loginPolicyYn = true;
					String password = httpRequest.getParameter("password");

					// 보안점검 후속 조치(Password 검증)
					if (password == null || password.equals("") || password.length() < 8 || password.length() > 20) {

						//RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/bigdata/auth/login.do");
						//dispatcher.forward(httpRequest, httpResponse);

						((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/bdp/auth/login.do?error=E001");

						return;
					}

					LoginVO loginVO = new LoginVO();

					String rStudioLoginValue = httpRequest.getParameter("p");
					// System.out.println("rstudio = " + rStudioLoginValue);
					loginVO.setId(httpRequest.getParameter("id"));
					loginVO.setPassword(password);
					loginVO.setUserSe(httpRequest.getParameter("userSe"));

					try {


						String userIp = EgovClntInfo.getClntIP((HttpServletRequest) request);
			            LoginPolicyVO loginPolicyVO = new LoginPolicyVO();
			            loginPolicyVO.setEmplyrId(httpRequest.getParameter("id"));
			            loginPolicyVO = egovLoginPolicyService.selectLoginPolicy(loginPolicyVO);

			            if (loginPolicyVO == null) {
			                loginPolicyYn = true;
			            } else {
			                if (loginPolicyVO.getLmttAt().equals("Y")) {
			                    if (!userIp.equals(loginPolicyVO.getIpInfo())) {
			                        loginPolicyYn = false;
			                    }
			                }
			            }

			            if (!loginPolicyYn) {
			            	((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/bdp/auth/login.do?error=E002");
			                return ;
			            }


						//사용자 입력 id, password로 DB 인증을 실행함
						loginVO = loginService.actionLogin(loginVO);

						if (loginVO != null && loginVO.getId() != null && !loginVO.getId().equals("")) {

							/* TODO : ambari 제거
							String ambariSessionValue = extendUserViewService.makeAmbariLoginSession(httpRequest.getParameter("id"), password);
							loginVO.setAmbariSessionValue(ambariSessionValue);
							*/

							//세션 로그인
							EntityMap userInfo = userSettingService.selectUserInfo(httpRequest.getParameter("id"));

							session.setAttribute("loginVO", loginVO);
							session.setAttribute("package", rStudioLoginValue);
							session.setAttribute("loginUserId", loginVO.getId());
							session.setAttribute("loginUserName", loginVO.getName());
							session.setAttribute("isLogin", true);

							// 로그인 시 접근 권한 설정
							if(userInfo.get("authorCode").equals("ROLE_ADMIN")) {
								session.setAttribute("isAdmin", true);
								session.setAttribute("isRoleAdmin", true);
							}

							if(userInfo.get("authorCode").equals("ROLE_USER")) {
								session.setAttribute("isUser", true);
								session.setAttribute("isRoleUser", true);
							}

							if(userInfo.get("authorCode").equals("ROLE_INTERNAL_ANALYST")) {
								session.setAttribute("isAnalyst", true);
								session.setAttribute("isRoleAnalyst", true);
							}

							//로컬 인증결과 세션에 저장
							session.setAttribute("isLocallyAuthenticated", "true");

							//스프링 시큐리티 로그인
							//httpResponse.sendRedirect(httpRequest.getContextPath() + "/j_spring_security_check?j_username=" + loginVO.getUserSe() + loginVO.getId() + "&j_password=" + loginVO.getUniqId());

							UsernamePasswordAuthenticationFilter springSecurity = null;

							Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
							if (beans.size() > 0) {
								springSecurity = (UsernamePasswordAuthenticationFilter) beans.values().toArray()[0];
							} else {
								LOGGER.error("No AuthenticationProcessingFilter");
								throw new IllegalStateException("No AuthenticationProcessingFilter");
							}
							//springSecurity.setContinueChainBeforeSuccessfulAuthentication(false);	// false 이면 chain 처리 되지 않음.. (filter가 아닌 경우 false로...)

							System.out.println("Auth = " + egovUserDetailsService.getAuthorities().toString());

							LOGGER.debug("before security filter call....");
							springSecurity.doFilter(new RequestWrapperForSecurity(httpRequest, loginVO.getUserSe() + loginVO.getId(), loginVO.getUniqId()), httpResponse, chain);
							LOGGER.debug("after security filter call....");

						} else {
							//사용자 정보가 없는 경우 로그인 화면으로 redirect 시킴
							httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login"));
							RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/bigdata/auth/login.do");
							dispatcher.forward(httpRequest, httpResponse);

							//chain.doFilter(request, response);

							return;

						}

					} catch (Exception ex) {
						//DB인증 예외가 발생할 경우 로그인 화면으로 redirect 시킴
						LOGGER.error("Login Exception : {}", ex.getCause(), ex);
						//RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/bigdata/auth/login.do");
						//dispatcher.forward(httpRequest, httpResponse);

						((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/bdp/auth/login.do?error=E001");

						return;

					}
					return;
				}

			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}
}

class RequestWrapperForSecurity extends HttpServletRequestWrapper {
	private String username = null;
	private String password = null;

	public RequestWrapperForSecurity(HttpServletRequest request, String username, String password) {
		super(request);

		this.username = username;
		this.password = password;
	}

	@Override
	public String getRequestURI() {
		return ((HttpServletRequest) super.getRequest()).getContextPath() + "/j_spring_security_check";
	}

	@Override
	public String getParameter(String name) {
		if (name.equals("j_username")) {
			return username;
		}

		if (name.equals("j_password")) {
			return password;
		}

		return super.getParameter(name);
	}
}
