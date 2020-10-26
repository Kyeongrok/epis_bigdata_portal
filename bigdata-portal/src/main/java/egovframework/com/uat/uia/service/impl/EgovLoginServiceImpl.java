package egovframework.com.uat.uia.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 구현 클래스
 *
 * @author 공통서비스 개발팀 박지욱
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  2014.12.08	이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *  </pre>
 * @since 2009.03.06
 */
@Service("loginService")
public class EgovLoginServiceImpl extends EgovAbstractServiceImpl implements EgovLoginService {

    @Resource(name = "loginDAO")
    private LoginDAO loginDAO;


    /**
     * 2011.08.26
     * EsntlId를 이용한 로그인을 처리한다
     *
     * @param vo LoginVO
     * @return LoginVO
     * @throws Exception
     */
    @Override
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception {

        LoginVO loginVO = loginDAO.actionLoginByEsntlId(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }


    /**
     * 일반 로그인을 처리한다
     *
     * @param vo LoginVO
     * @return LoginVO
     * @throws Exception
     */
    @Override
    public LoginVO actionLogin(LoginVO vo) throws Exception {

        // 1. 입력한 비밀번호를 암호화한다.
        String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());
        vo.setPassword(enpassword);

        // 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
        LoginVO loginVO = loginDAO.actionLogin(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
        	loginVO.setIp(vo.getIp());
        	
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    /**
     * 인증서 로그인을 처리한다
     *
     * @param vo LoginVO
     * @return LoginVO
     * @throws Exception
     */
    @Override
    public LoginVO actionCrtfctLogin(LoginVO vo) throws Exception {

        // 1. DN값으로 ID, PW를 조회한다.
        LoginVO loginVO = loginDAO.actionCrtfctLogin(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    /**
     * 아이디를 찾는다.
     *
     * @param vo LoginVO
     * @return LoginVO
     * @throws Exception
     */
    @Override
    public LoginVO searchId(LoginVO vo) throws Exception {

        // 1. 이름, 이메일주소가 DB와 일치하는 사용자 ID를 조회한다.
        LoginVO loginVO = loginDAO.searchId(vo);

        // 2. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    @Override
    public boolean searchPassword(LoginVO vo) throws Exception {
        return false;
    }
}
