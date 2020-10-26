package bigdata.portal.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.util.Calendar;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.ExtendUserViewService;
import bigdata.portal.service.OkdabLoginService;
import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.uat.uia.service.EgovLoginService;

/**
 * 로그인 화면 컨트롤러
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
public class LoginController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ExtendUserViewService extendUserViewService;
	
	@Autowired
	private OkdabLoginService okdabLoginService;
	
    @Resource(name = "loginService")
    private EgovLoginService loginService;
    
	private String okdabAuthority = EgovProperties.getProperty("okdab.api.default.authority");
	

	/**
	 * Okdab 로그인 연결 컨트롤러
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/bdp/auth/okdabLoginLink.do", method = RequestMethod.GET)	
	public String okdabLoginLink(Model model, 
			@RequestParam(value="UUID", defaultValue="") String uuid,
			ServletRequest request,
			ServletResponse response) throws ParseException, IOException {
		
		// Date 설정
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long thisTime = Calendar.getInstance().getTimeInMillis();
		
		if(uuid == null || uuid.length() <= 0) {
			LOGGER.debug("UUID VALUE NULL.");
			
			model.addAttribute("message", "로그인을 위한 회원정보를 입력받지 못하였습니다.");
			
			return null;
		}
		
		EntityMap userInfo = okdabLoginService.selectOkdabLogin(uuid);
		
		if(userInfo == null) {
			LOGGER.debug("USERINFO VALUE NULL.");
			
			model.addAttribute("message", "로그인을 위한 회원정보를 입력받지 못하였습니다.");
			
			return null;
		}
				
		Date loginTime = transFormat.parse(userInfo.getString("okcAccessDttm"));
		
		System.out.println(uuid);
		System.out.println(userInfo.getString("okcUuid"));
		System.out.println(userInfo.toString());
		
		// TODO: 세션 생성 소스 추가
		if (!uuid.equals(userInfo.get("okcUuid"))) {
			LOGGER.debug("생성된 UUID가 일치하지 않습니다.");
			model.addAttribute("message", "생성된 UUID가 일치하지 않습니다.");
			
			return null;
			
		}
		
		// 시간체크 -> UUID 생성 후 5분이 지났는가?
		if ((thisTime - 300000) > loginTime.getTime()) {
			LOGGER.debug("Account Login Failed.");
			
			model.addAttribute("message", "세션이 만료되었습니다.");
			
			return null;			
		}
		
		LOGGER.debug("Login OK");
		
		LoginVO loginVO = new LoginVO();
		
		// 로그인 세션 설정
		loginVO.setId(userInfo.getString("okcUserId"));
		loginVO.setUserSe("GNR");
		loginVO.setName(userInfo.getString("okcUserName"));
		loginVO.setUniqId(userInfo.getString("okcUuid").substring(0, 7));
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		
		// 스프링 시큐리티 로그인 설정
		Authentication authentication  = new UsernamePasswordAuthenticationToken(
				userInfo.getString("okcUserId"), " ", AuthorityUtils.createAuthorityList(okdabAuthority)
		);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);   // 세션에 spring security context 넣음
		
		session.setAttribute("loginVO", loginVO);
		session.setAttribute("loginUserId", loginVO.getId());
		session.setAttribute("loginUserName", loginVO.getName());
		session.setAttribute("isLogin", true);
		session.setAttribute("isLocallyAuthenticated", "true");
		
		return "redirect:/bdp/main/main.do";
				
	}
	
	
	/**
	 * 로그인 화면
	 * 
	 * @param model
	 * @return
	 */
		
	@RequestMapping(value = "/bdp/auth/login.do", method = RequestMethod.GET)
	public String main(Model model, 
			@RequestParam(value="error", required=false, defaultValue="") String error) {
		
		if(error.length() > 0 && error.equals("E001")) {
			model.addAttribute("message", "로그인 정보가 올바르지 않습니다. ");
		}
		
		if(error.length() > 0 && error.equals("E002")) {
			model.addAttribute("message", "IP 제한 접속이 설정 된 계정입니다. ");
		}
		
		if (EgovComponentChecker.hasComponent("mberManageService")) {
			model.addAttribute("useMemberManage", "true");
		}
		
		return "bigdata/portal/auth/login";
	}
	
	/**
	 * 로그인 콜백 메소드
	 * @return 
	 */
	@RequestMapping(value = "/bdp/auth/loginOK.do", method = RequestMethod.GET)
	public String loginCallback() {
		return "redirect:/index.do";
	}
	
	/**
	 * 로그아웃 메소드
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/bdp/auth/logout.do", method = RequestMethod.GET)
	public String logout(ServletRequest request, ServletResponse response) throws IOException {		
		return "redirect:/bdp/auth/logoutOK.do";
	}
	
	/**
	 * 로그아웃 콜백 메소드
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/bdp/auth/logoutOK.do", method = RequestMethod.GET)
	public String logoutCallback(ServletRequest request, ServletResponse response) throws IOException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return "redirect:/j_spring_security_logout";
	}
	
	
	/**
	 * 로그인 실패 시, 시큐리티 Login Filter에서 
	 * getRequestDispatcher을 통해 로그인페이지로 forward시키기 위한 프록시 컨트롤러 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bigdata/auth/login.do", method = RequestMethod.GET)
	public String dispacherToMain(Model model) {
		
		if (EgovComponentChecker.hasComponent("mberManageService")) {
			model.addAttribute("useMemberManage", "true");
		}
		
		return "redirect:/bdp/auth/login.do";
	}
	
	@RequestMapping(value="/bdp/auth/check.do")
	public @ResponseBody String authPublicKey() {
		
		String publicKey = null;
		
		try {
			publicKey = extendUserViewService.getRStudioPublicKey();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return publicKey;
	}
	
	
}
