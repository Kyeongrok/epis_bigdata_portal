package egovframework.com.uss.umt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import bigdata.portal.service.ExtendUserViewService;
import bigdata.portal.service.HdfsService;
import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;
import egovframework.com.uss.umt.service.EgovEntrprsManageService;
import egovframework.com.uss.umt.service.EntrprsManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 기업회원관리에 관한 비지니스클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *
 * </pre>
 */
@Service("entrprsManageService")
public class EgovEntrprsManageServiceImpl extends EgovAbstractServiceImpl implements EgovEntrprsManageService {

	/** userManageDAO */
    @Resource(name="userManageDAO")
    private UserManageDAO userManageDAO;

    /** mberManageDAO */
    @Resource(name="mberManageDAO")
    private MberManageDAO mberManageDAO;

    /** entrprsManageDAO */
    @Resource(name="entrprsManageDAO")
    private EntrprsManageDAO entrprsManageDAO;
    
	@Resource(name = "egovAuthorGroupService")
	private EgovAuthorGroupService egovAuthorGroupService;	
	
	@Autowired
	private ExtendUserViewService extendUserViewService;
	
	@Autowired
	private HdfsService hdfsService;
	
    /** egovUsrCnfrmIdGnrService */
    @Resource(name="egovUsrCnfrmIdGnrService")
    private EgovIdGnrService idgenService;	
	
	private final String AMBARI_DEFAULT_GROUP_NAME = "epis";

    /**
	 * 기업회원의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param entrprsManageVO 기업회원등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
    @Override
	public String insertEntrprsmber(EntrprsManageVO entrprsManageVO) throws Exception  {
        //고유아이디 셋팅
    	String uniqId = idgenService.getNextStringId();
        entrprsManageVO.setUniqId(uniqId);
        
        //패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(entrprsManageVO.getEntrprsMberPassword(), entrprsManageVO.getEntrprsmberId());
		entrprsManageVO.setEntrprsMberPassword(pass);

        String result = entrprsManageDAO.insertEntrprsmber(entrprsManageVO);
        return result;
    }
    
    
    /**
	 * 기업회원의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 저장 후, Ambari 계정과 HDFS 디렉토리를 생성
	 * @param entrprsManageVO, authorGroupVO
	 * @return result 등록결과
	 * @throws Exception
	 */    
    @Override
	public String insertEntrprsmbers(EntrprsManageVO entrprsManageVO)  {

    	String uniqId = null;
		String pass = null;		
        String result = null;
        
    	String entrprsmberId = null;
    	String entrprsmberPassword = null;
    	    	
		try {
			
			// 고유 ID 생성 후 설정
			uniqId = idgenService.getNextStringId();
			entrprsManageVO.setUniqId(uniqId);
			
			entrprsmberId = entrprsManageVO.getEntrprsmberId();
			entrprsmberPassword = entrprsManageVO.getEntrprsMberPassword();
			
			AuthorGroupVO authorGroupVO = new AuthorGroupVO();
			authorGroupVO.setSearchKeyword(uniqId);
			authorGroupVO.setAuthorCode("ROLE_USER");   			
			
			// 비밀번호 암호화 후 포털 계정 생성
			pass = EgovFileScrty.encryptPassword(entrprsmberPassword, entrprsmberId);
			entrprsManageVO.setEntrprsMberPassword(pass);
			entrprsManageDAO.insertEntrprsmber(entrprsManageVO);
			
			// 포털 계정 최초 권한 설정
			egovAuthorGroupService.insertAuthorGroup(authorGroupVO, entrprsManageVO);   
	        
	        // Ambari 계정과 HDFS 디렉토리 생성
			extendUserViewService.makeAmbariAccount(entrprsmberId, entrprsmberPassword);
			extendUserViewService.joinGroupAmbari(entrprsmberId, AMBARI_DEFAULT_GROUP_NAME);
			extendUserViewService.makeRStudioAccount(entrprsmberId, entrprsmberPassword);
			
			hdfsService.makeHdfsUserDir(entrprsmberId);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ExceptionUtils.getRootCause(e).getMessage();
			
		}
		
        return result;
    }    

    /**
	 * 기 등록된 사용자 중 검색조건에 맞는기업회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param uniqId 조회대상 기업회원아이디
	 * @return entrprsManageVO 기업회원정보
	 * @throws Exception
	 */
    @Override
	public EntrprsManageVO selectEntrprsmber(String uniqId) {
        EntrprsManageVO entrprsManageVO = entrprsManageDAO.selectEntrprsmber(uniqId);
        return entrprsManageVO;
    }

	/**
	 * 화면에 조회된 기업회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param entrprsManageVO 기업회원수정정보
	 * @throws Exception
	 */
    @Override
	public void updateEntrprsmber(EntrprsManageVO entrprsManageVO) throws Exception {
    	//패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(entrprsManageVO.getEntrprsMberPassword(), entrprsManageVO.getEntrprsmberId());
		entrprsManageVO.setEntrprsMberPassword(pass);
		
		long quotaSize = hdfsService.getQuotaSize(entrprsManageVO.getEntrprsmberId());		
		if(quotaSize != entrprsManageVO.getQuotaSize()) {
			entrprsManageVO.setQuotaSize(quotaSize);			
			hdfsService.setQuotaSize(entrprsManageVO.getEntrprsmberId(), quotaSize);
		
		}		
		entrprsManageDAO.updateEntrprsmber(entrprsManageVO);
    }

	/**
	 * 화면에 조회된 기업회원의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상기업회원아이디
	 * @throws Exception
	 */
    @Override
	public void deleteEntrprsmber(String checkedIdForDel)  {
        //log.debug("jjyser_delete-->"+checkedIdForDel);
        String [] delId = checkedIdForDel.split(",");
        for (int i=0; i<delId.length ; i++){
            String [] id = delId[i].split(":");
            //log.debug("id[0]:"+id[0]);
            if (id[0].equals("USR03")){
                //업무사용자(직원)삭제
                userManageDAO.deleteUser(id[1]);
            }else if(id[0].equals("USR01")){
                //일반회원삭제
                mberManageDAO.deleteMber(id[1]);
            }else if(id[0].equals("USR02")){
                //기업회원삭제
                entrprsManageDAO.deleteEntrprsmber(id[1]);
            }
        }
    }

	/**
	 * 기업회원용 약관정보 조회
	 * @param stplatId 기업회원약관아이디
	 * @return stplatList 기업회원약관정보
	 * @throws Exception
	 */
    @Override
	public List<?> selectStplat(String stplatId) {
    	List<?> stplatList = entrprsManageDAO.selectStplat(stplatId);
        return stplatList;
    }

	/**
	 * 기업회원 암호 수정
	 * @param passVO 기업회원수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public void updatePassword(EntrprsManageVO passVO) {
		entrprsManageDAO.updatePassword(passVO);
	}

	/**
	 * 기업회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 기업회원암호 조회조건정보
	 * @return entrprsManageVO 기업회원암호정보
	 * @throws Exception
	 */
	@Override
	public EntrprsManageVO selectPassword(EntrprsManageVO passVO) {
		EntrprsManageVO entrprsManageVO = entrprsManageDAO.selectPassword(passVO);
		return entrprsManageVO;
	}

	/**
	 * 기 등록된기업 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<EntrprsManageVO> 기업회원목록정보
	 * @throws Exception
	 */
	@Override
	public List<?> selectEntrprsMberList(UserDefaultVO userSearchVO) {
		return entrprsManageDAO.selectEntrprsMberList(userSearchVO);
	}

    /**
     * 기업회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return 사용자 총 갯수(int)
     * @throws Exception
     */
    @Override
	public int selectEntrprsMberListTotCnt(UserDefaultVO userSearchVO) {
    	return entrprsManageDAO.selectEntrprsMberListTotCnt(userSearchVO);
    }

	/**
	 * 삭제 대상 회원 정보를 불러옴
	 * @param checkedIdForDel
	 * @return ArrayList<MberManageVO> mberList
	 * @throws Exception
	 */
	@Override
	public ArrayList<EntrprsManageVO> selectEntrprsmbers(String checkedIdForDel) throws Exception {
		ArrayList<EntrprsManageVO> entrprsmbers = new ArrayList<EntrprsManageVO>();
		
		String[] delId = checkedIdForDel.split(",");
		for (int i=0; i<delId.length ; i++){
			String [] id = delId[i].split(":");
			
			EntrprsManageVO mber = entrprsManageDAO.selectEntrprsmber(id[1]);
			entrprsmbers.add(mber);
			
		}
		
		return entrprsmbers;	
	}


}