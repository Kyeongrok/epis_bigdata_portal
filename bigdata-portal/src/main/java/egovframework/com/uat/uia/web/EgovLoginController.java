package egovframework.com.uat.uia.web;

import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.slm.EgovHttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.servlet.GPKIHttpServletRequest;
import com.gpki.servlet.GPKIHttpServletResponse;
*/

/**
 * 일반 로그인, 인증서 로그인을 처리하는 컨트롤러 클래스
 *
 * @author 공통서비스 개발팀 박지욱
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱   	   최초 생성
 *  2011.8.26	정진오	   IncludedInfo annotation 추가
 *  2011.09.07  서준식   	   스프링 시큐리티 로그인 및 SSO 인증 로직을 필터로 분리
 *  2011.09.25  서준식   	   사용자 관리 컴포넌트 미포함에 대한 점검 로직 추가
 *  2011.09.27  서준식   	   인증서 로그인시 스프링 시큐리티 사용에 대한 체크 로직 추가
 *  2011.10.27  서준식   	   아이디 찾기 기능에서 사용자 리름 공백 제거 기능 추가
 *  2017.08.29	허현범	   필요없는 기능 삭제
 *  </pre>
 * @since 2009.03.06
 */
@Controller
public class EgovLoginController {

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EgovLoginController.class);
    /**
     * EgovMessageSource
     */
    @Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;
    /**
     * EgovLoginService
     */
    @Resource(name = "loginService")
    private EgovLoginService loginService;

    /**
     * 로그인 화면으로 들어간다
     *
     * @param vo - 로그인후 이동할 URL이 담긴 LoginVO
     * @return 로그인 페이지
     * @throws Exception 
     */
     
    @IncludedInfo(name = "로그인", listUrl = "/uat/uia/egovLoginUsr.do", order = 10, gid = 10)
    @RequestMapping(value = "/uat/uia/egovLoginUsr.do")
    public String loginUsrView(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        if (EgovComponentChecker.hasComponent("mberManageService")) {
            model.addAttribute("useMemberManage", "true");
        }

        return "bigdata/portal/auth/login";
    }
 

    /**
     * 일반(세션) 로그인을 처리한다
     *
     * @param vo      - 아이디, 비밀번호가 담긴 LoginVO
     * @param request - 세션처리를 위한 HttpServletRequest
     * @return result - 로그인결과(세션정보)
     * @throws Exception
     */
    @RequestMapping(value = "/uat/uia/actionLogin.do")
    public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO resultVO = loginService.actionLogin(loginVO);

        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {            
            return "redirect:/index.do";
        }
        
        model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        return "bigdata/portal/auth/login";
    }

    /**
     * 로그아웃한다.
     *
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/uat/uia/actionLogout.do")
    public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {
    	
        
        return "redirect:/bdp/auth/logout.do";
    }
   
}