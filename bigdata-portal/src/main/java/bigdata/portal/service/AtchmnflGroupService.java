/**
 * 
 */
package bigdata.portal.service;

import java.util.List;
import java.util.Map;

import kr.co.ucsit.core.CsFileVO;

/**
 * 첨부파일 그룹
 * @author hyunseongkil
 *
 */
public interface AtchmnflGroupService {

	/**
	 * 등록
	 * @param frstRegisterId 최초 등록자 아이디
	 * @param fileVos 파일 정보 목록
	 * @return 첨부파일 아이디
	 */
	String regist(String frstRegisterId, List<CsFileVO> fileVos);
	
	/**
	 * 조회
	 * @param atchFileId 첨부파일 아이디
	 * @return
	 */
	Map<String,Object> getByAtchFileId(String atchFileId);
}
