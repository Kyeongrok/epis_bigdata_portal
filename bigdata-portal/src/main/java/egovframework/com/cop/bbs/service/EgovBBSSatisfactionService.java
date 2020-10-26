package egovframework.com.cop.bbs.service;

import java.util.Map;

/**
 * 만족도조사를 위한 서비스 인터페이스 클래스
 *
 * @author 공통컴포넌트개발팀 한성곤
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.06.29  한성곤          최초 생성
 *
 * </pre>
 * @since 2009.06.29
 */
public interface EgovBBSSatisfactionService {
    /**
     * 만족도조사 사용 가능 여부를 확인한다.
     *
     * @param bbsId
     * @return
     * @throws Exception
     */
    boolean canUseSatisfaction(String bbsId) throws Exception;

    /**
     * 만족도조사에 대한 목록을 조회 한다.
     *
     * @param satisfactionVO
     * @return
     * @throws Exception
     */
    Map<String, Object> selectSatisfactionList(SatisfactionVO satisfactionVO) throws Exception;

    /**
     * 만족도조사를 등록한다.
     *
     * @param satisfaction
     * @throws Exception
     */
    void insertSatisfaction(Satisfaction satisfaction) throws Exception;

    /**
     * 만족도조사를 삭제한다.
     *
     * @param satisfactionVO
     * @throws Exception
     */
    void deleteSatisfaction(SatisfactionVO satisfactionVO) throws Exception;

    /**
     * 만족도조사에 대한 내용을 조회한다.
     *
     * @param satisfactionVO
     * @return
     * @throws Exception
     */
    Satisfaction selectSatisfaction(SatisfactionVO satisfactionVO) throws Exception;

    /**
     * 만족도조사에 대한 내용을 수정한다.
     *
     * @param satisfaction
     * @throws Exception
     */
    void updateSatisfaction(Satisfaction satisfaction) throws Exception;

    /**
     * 만족도조사 패스워드를 가져온다.
     *
     * @param satisfaction
     * @return
     * @throws Exception
     */
    String getSatisfactionPassword(Satisfaction satisfaction) throws Exception;
}
