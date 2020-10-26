/**
 * 
 */
package bigdata.portal.service;

import java.util.List;
import java.util.Map;

import kr.co.ucsit.core.CsFileVO;

/**
 * 첨부파일
 * @author hyunseongkil
 *
 */
public interface AtchmnflService {

	/**
	 * 목록 조회
	 * @param atchFileId 첨부파일 아이디
	 * @return
	 */
	List<Map<String,Object>> getsByAtchFileId(String atchFileId);

	/**
	 * 조회
	 * @param atchFileId 첨부파일 아이디
	 * @param fileNo 파일 번호
	 * @return
	 */
	Map<String, Object> getByPk(String atchFileId, String fileNo);

	/**
	 * 등록
	 * @param atchFileId 첨부파일 아이디
	 * @param frstRegisterId 최초 등록자 아이디
	 * @param vo 파일 정보
	 * @return 파일 번호
	 */
	String regist(String atchFileId, String frstRegisterId, CsFileVO vo);

	/**
	 * 삭제
	 * @param atchFileId 첨부파일 아이디
	 * @param fileNo 파일 번호
	 */
	void delete(String atchFileId, String fileNo);
}
