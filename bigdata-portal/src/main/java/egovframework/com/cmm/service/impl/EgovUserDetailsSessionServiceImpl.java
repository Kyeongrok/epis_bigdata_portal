package egovframework.com.cmm.service.impl;

import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 공통서비스 개발팀 서준식
 * @version 1.0
 * @see <pre>
 * 개정이력(Modification Information)
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011. 8. 12.    서준식        최초생성
 *
 *  </pre>
 * @since 2011. 6. 25.
 */

@Service("egovUserDetailsService")
public class EgovUserDetailsSessionServiceImpl extends EgovAbstractServiceImpl implements EgovUserDetailsService {

    public Object getAuthenticatedUser() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }

        return RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);

    }

    public List<String> getAuthorities() {

        // 권한 설정을 리턴한다.
        List<String> listAuth = new ArrayList<String>();
        listAuth.add("IS_AUTHENTICATED_ANONYMOUSLY");
        listAuth.add("IS_AUTHENTICATED_FULLY");
        listAuth.add("IS_AUTHENTICATED_REMEMBERED");
        listAuth.add("ROLE_ADMIN");
        listAuth.add("ROLE_ANONYMOUS");
        listAuth.add("ROLE_RESTRICTED");
        listAuth.add("ROLE_USER");

        return listAuth;
    }

    public Boolean isAuthenticated() {
        // 인증된 유저인지 확인한다.

        if (RequestContextHolder.getRequestAttributes() == null) {
            return false;
        } else {

            return RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION) != null;
        }

    }

}
