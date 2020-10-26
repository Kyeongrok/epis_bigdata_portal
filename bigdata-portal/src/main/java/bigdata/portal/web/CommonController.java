package bigdata.portal.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.UserSettingService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;

/**
 * 데이터 분석 컨트롤러
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
public abstract class CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;

	@Autowired private UserSettingService userSettingService;

	/**
	 * 로그인 사용자 아이디 리턴
	 *
	 * @return
	 */
	protected String getLoginUserId() {
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();

		if(user == null || user.getId() == null || user.getId().trim().equals("")) {
			return "";
		}

		return user.getId();
	}

	/**
	 * 로그인 사용자 아이디 리턴
	 *
	 * @return
	 */
	protected String getLoginUserNm() {
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();

		if(user == null || user.getId() == null || user.getId().trim().equals("")) {
			return "";
		}

		return user.getName();
	}

	/**
	 * 로그인 여부 리턴
	 *
	 * @return
	 */
	protected boolean isLogin() {
		return egovUserDetailsService.isAuthenticated();
	}

	/**
	 * 관리자 여부 리턴
	 *
	 * @return
	 */
	protected boolean isRoleAdmin() {

		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = null;
		try {
			userInfo = userSettingService.selectUserInfo(user.getId());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		String authorCode = (String) userInfo.get("authorCode");
		if(authorCode.equals("ROLE_ADMIN")) {
			return true;
		}

		return false;

	}

	/**
	 * 분석가 여부 리턴
	 *
	 * @return
	 */
	protected boolean isRoleAna() {
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = null;
		try {
			userInfo = userSettingService.selectUserInfo(user.getId());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		String authorCode = (String) userInfo.get("authorCode");
		if(authorCode.equals("ROLE_ANONYMOUS")) {
			return true;
		}

		return false;
	}

	/**
	 * 일반 회원 여부 리턴
	 */
	protected boolean isRoleUser() {
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		EntityMap userInfo = null;

		try {
			userInfo = userSettingService.selectUserInfo(user.getId());
		} catch(Exception e) {
			return false;
		}
		String authorCode = (String) userInfo.get("authorCode");
		if(authorCode.equals("ROLE_USER")) {
			return true;
		}
		return false;
	}
}
