package egovframework.com.cmm.interceptor;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 사용자IP 체크 인터셉터
 *
 * @author 유지보수팀 이기하
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일     수정자          수정내용
 *  ----------  --------    ---------------------------
 *  2013.03.28	이기하          최초 생성
 *  </pre>
 * @since 2013.03.28
 */

public class IpObtainInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String clientIp = request.getRemoteAddr();

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (loginVO != null) {
            loginVO.setIp(clientIp);
        }

        return true;
    }
}
