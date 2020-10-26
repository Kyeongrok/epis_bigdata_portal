package egovframework.com.uss.umt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 일반회원관리에 관한비지니스클래스를 정의한다.
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
@Service("mberManageService")
public class EgovMberManageServiceImpl extends EgovAbstractServiceImpl implements EgovMberManageService {

	/** userManageDAO */
	@Resource(name="userManageDAO")
	private UserManageDAO userManageDAO;

	/** mberManageDAO */
	@Resource(name="mberManageDAO")
	private MberManageDAO mberManageDAO;

	/** entrprsManageDAO */
	@Resource(name="entrprsManageDAO")
	private EntrprsManageDAO entrprsManageDAO;

	/** egovUsrCnfrmIdGnrService */
	@Resource(name="egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	@Resource(name = "egovAuthorGroupService")
	private EgovAuthorGroupService egovAuthorGroupService;

	/* TODO : hdfs, ambari 관련기능 제거
	@Autowired
	private ExtendUserViewService extendUserViewService;

	@Autowired
	private HdfsService hdfsService;
	*/

	private final String AMBARI_DEFAULT_GROUP_NAME = "epis";

	/**
	 * 사용자의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param mberManageVO 일반회원 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	@Override
	public String insertMber(MberManageVO mberManageVO) throws Exception  {
		//고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		mberManageVO.setUniqId(uniqId);
		//패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
		mberManageVO.setPassword(pass);

		String result = mberManageDAO.insertMber(mberManageVO);
		return result;
	}

	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param uniqId 상세조회대상 일반회원아이디
	 * @return mberManageVO 일반회원상세정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectMber(String uniqId) {
		MberManageVO mberManageVO = mberManageDAO.selectMber(uniqId);
		return mberManageVO;
	}

	/**
	 * 기 등록된 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<MberManageVO> 일반회원목록정보
	 */
	@Override
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO) {
		return mberManageDAO.selectMberList(userSearchVO);
	}

    /**
     * 일반회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return 일반회원총갯수(int)
     */
    @Override
	public int selectMberListTotCnt(UserDefaultVO userSearchVO) {
    	return mberManageDAO.selectMberListTotCnt(userSearchVO);
    }

	/**
	 * 화면에 조회된 일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param mberManageVO 일반회원수정정보
	 * @throws Exception
	 */
	@Override
	public void updateMber(MberManageVO mberManageVO) throws Exception {
		//패스워드 암호화
		String pass = EgovFileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
		mberManageVO.setPassword(pass);

		/* TODO : hdfs, ambari 기능 제거
		long quotaSize = hdfsService.getQuotaSize(mberManageVO.getMberId());
		if(quotaSize != mberManageVO.getQuotaSize()) {
			mberManageVO.setQuotaSize(quotaSize);
			hdfsService.setQuotaSize(mberManageVO.getMberId(), quotaSize);
		}
		*/

		mberManageDAO.updateMber(mberManageVO);
	}

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상 일반회원아이디
	 * @throws Exception
	 */
	@Override
	public void deleteMber(String checkedIdForDel)  {
		String [] delId = checkedIdForDel.split(",");
		for (int i=0; i<delId.length ; i++){
			String [] id = delId[i].split(":");
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
	 * 일반회원 약관확인
	 * @param stplatId 일반회원약관아이디
	 * @return 일반회원약관정보(List)
	 * @throws Exception
	 */
	@Override
	public List<?> selectStplat(String stplatId)  {
        return mberManageDAO.selectStplat(stplatId);
	}

	/**
	 * 일반회원암호수정
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public void updatePassword(MberManageVO mberManageVO) {
		mberManageDAO.updatePassword(mberManageVO);
	}

	/**
	 * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 일반회원암호 조회조건정보
	 * @return mberManageVO 일반회원암호정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectPassword(MberManageVO passVO) {
		MberManageVO mberManageVO = mberManageDAO.selectPassword(passVO);
		return mberManageVO;
	}


	/**
	 * 삭제 대상 회원 정보를 불러옴
	 * @param checkedIdForDel
	 * @return ArrayList<MberManageVO> mberList
	 * @throws Exception
	 */
	@Override
	public ArrayList<MberManageVO> selectMbers(String checkedIdForDel) throws Exception {

		ArrayList<MberManageVO> mberList = new ArrayList<MberManageVO>();

		String[] delId = checkedIdForDel.split(",");
		for (int i=0; i<delId.length ; i++){
			String [] id = delId[i].split(":");

			MberManageVO mber = mberManageDAO.selectMber(id[1]);
			mberList.add(mber);

		}

		return mberList;

	}

	/* (non-Javadoc)
	 * @see egovframework.com.uss.umt.service.EgovMberManageService#insertMber(egovframework.com.uss.umt.service.MberManageVO, egovframework.com.sec.rgm.service.AuthorGroupVO)
	 */
	@Override
	public String insertMbers(MberManageVO mberManageVO) {
    	String uniqId = null;
		String pass = null;
        String result = null;

    	String userMberId = null;
    	String userMberPassword = null;


		try {
			// 고유 ID 생성 후 설정
			uniqId = idgenService.getNextStringId();
			mberManageVO.setUniqId(uniqId);

			userMberId = mberManageVO.getMberId();
			userMberPassword = mberManageVO.getPassword();

			AuthorGroupVO authorGroupVO = new AuthorGroupVO();
			authorGroupVO.setSearchKeyword(uniqId);
			authorGroupVO.setAuthorCode("ROLE_USER");

			// 비밀번호 암호화 후 포털 계정 생성
			pass = EgovFileScrty.encryptPassword(userMberPassword, userMberId);
			mberManageVO.setPassword(pass);
			result = mberManageDAO.insertMber(mberManageVO);

			// 포털 계정 최초 권한 설정
			egovAuthorGroupService.insertAuthorGroup(authorGroupVO, mberManageVO);

			/* TODO : 기존 Ambari, HDFS 디렉토리 생성, RStudio 유저생성 주석처리
	        // Ambari 계정과 HDFS 디렉토리 생성
			extendUserViewService.makeAmbariAccount(userMberId, userMberPassword);
			extendUserViewService.joinGroupAmbari(userMberId, AMBARI_DEFAULT_GROUP_NAME);
			extendUserViewService.makeRStudioAccount(userMberId, userMberPassword);

			hdfsService.makeHdfsUserDir(userMberId);
			*/


		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();

			return ExceptionUtils.getRootCause(e).getMessage();
		}

		return result;
	}

}