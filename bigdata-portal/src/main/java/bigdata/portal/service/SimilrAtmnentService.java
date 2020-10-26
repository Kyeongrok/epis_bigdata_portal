/**
 * 
 */
package bigdata.portal.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.CsTransferObject;

/**
 * 유사 경영체
 * @author hyunseongkil
 *
 */
public interface SimilrAtmnentService {

	static Logger log = LoggerFactory.getLogger(SimilrAtmnentService.class);
	/**
	 * 지원(아그릭스) 사업 코드별 유사 경영체 존재 건수 목록 조회
	 * @param bsnsCodes 지원(아그릭스) 사업 코드 목록. 필수
	 * @param sexdstn 성별. 선택 1:남 2:여
	 * @param age 나이. 선택
	 * @param farmCareer 영농경력. 선택
	 * @param prdlstSetCode 품목군 코드
	 * @param prdlstCode 품목 코드
	 * @param gbn2 TODO
	 * @param arOrCo (농업이면)면적 또는 (축산이면)두수
	 * @return
	 * @throws Exception 
	 */
	CsTransferObject getCounts(String[] bsnsCodes, String sexdstn, String age, String farmCareer, String prdlstSetCode, String prdlstCode, String gbn2, String arOrCo) ;
	
	
	CsTransferObject getCountsByBsnsCode(String[] bsnsCodes, String sexdstn, String age, String farmCareer,	String prdlstSetCode, String prdlstCode, String ctvtTyCode, String arOrCo) throws InstantiationException, IllegalAccessException, IOException ;
}
