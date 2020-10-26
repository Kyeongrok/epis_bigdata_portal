package egovframework.com.cop.bbs.service;

import java.util.Map;


/**
 * 게시판 이용정보를 관리하기 위한 서비스 인터페이스 클래스
 *
 * @author 공통서비스개발팀 이삼섭
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.4.2   이삼섭          최초 생성
 * 	 2011.7.27  안민정			동호회, 커뮤니티 로직 분리  (EgovBBSUseInfoManageService -> EgovCmyBBSUseInfoManageService)
 * </pre>
 * @since 2009.06.01
 */
public interface EgovBBSUseInfoManageService {

    /**
     * 게시판 사용 정보를 삭제한다.
     *
     * @param bdUseInf
     * @throws Exception
     */
    void deleteBBSUseInf(BoardUseInf bdUseInf) throws Exception;

    /**
     * 게시판 사용정보를 등록한다.
     *
     * @param bdUseInf
     * @throws Exception
     */
    void insertBBSUseInf(BoardUseInf bdUseInf) throws Exception;

    /**
     * 게시판 사용정보 목록을 조회한다.
     *
     * @param bdUseVO
     * @return
     * @throws Exception
     */
    Map<String, Object> selectBBSUseInfs(BoardUseInfVO bdUseVO) throws Exception;

    /**
     * 게시판 사용정보를 수정한다.
     *
     * @param bdUseInf
     * @throws Exception
     */
    void updateBBSUseInf(BoardUseInf bdUseInf) throws Exception;

    /**
     * 게시판 사용정보에 대한 상세정보를 조회한다.
     *
     * @param bdUseVO
     * @return
     * @throws Exception
     */
    BoardUseInfVO selectBBSUseInf(BoardUseInfVO bdUseVO) throws Exception;

    /**
     * 게시판에 대한 사용정보를 삭제한다.
     *
     * @param bdUseInf
     * @throws Exception
     */
    void deleteBBSUseInfByBoardId(BoardUseInf bdUseInf) throws Exception;

    /**
     * 커뮤니티, 동호회에 사용되는 게시판 사용정보에 대한 목록을 조회한다.
     *
     * @param bdUseVO
     * @return
     * @throws Exception
     */
    Map<String, Object> selectBBSUseInfsByTrget(BoardUseInfVO bdUseVO) throws Exception;

    /**
     * 커뮤니티, 동호회에 사용되는 게시판 사용정보를 수정한다.
     *
     * @param bdUseInf
     * @throws Exception
     */
    void updateBBSUseInfByTrget(BoardUseInf bdUseInf) throws Exception;

}
