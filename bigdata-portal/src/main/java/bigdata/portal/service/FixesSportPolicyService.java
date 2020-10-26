/**
 * 
 */
package bigdata.portal.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.service.CsService;

/**
 * @author hyunseongkil
 *
 */
public interface FixesSportPolicyService extends CsService {
	static Logger log = LoggerFactory.getLogger(FixesSportPolicyService.class);
	
	/**
	 * 구분과 키워드로 사업 목록 조회
	 * @param searchGbn
	 * @param searchKeyword
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	CsTransferObject getDatas(String searchGbn, String searchKeyword) throws InstantiationException, IllegalAccessException, IOException ;
	
	/**
	 * 세부 사업 코드로 사업 정보 조회
	 * @param mlsfcNmCode
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	CsTransferObject getDatasByMlsfcNmCode(String mlsfcNmCode) throws InstantiationException, IllegalAccessException, IOException ;
	
	/**
	 * 사업 코드로 지침서 파일 메타정보 조회
	 * @param lclasNmCode
	 * @param mlsfcNmCode
	 * @return
	 */
	Map<String,Object> getFileInfoByLclasNmCodeAndMlsfcNmCode(String lclasNmCode, String mlsfcNmCode);

	/**
	 * 귀농용 데이터 목록 조회
	 * @param searchSido 시도명
	 * @param searchSigungu 시군구명
	 * @param searchUmd 읍면동명
	 * @param searchSportRelmCode 지원정책 목록. 구분자:콤마
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	CsTransferObject getDatasForRetnFmlg(String searchSido, String searchSigungu, String searchUmd, String searchSportRelmCode) throws InstantiationException, IllegalAccessException, IOException;

	/**
	 * 년도별 현황
	 * @param bsnsCode
	 * @return
	 * @throws Exception 
	 */
	CsTransferObject getStatsDatasByYear(String bsnsCode) ;
	
	/**
	 * 시도별 현황
	 * @param bsnsCode
	 * @return
	 */
	CsTransferObject getStatsDatasBySido(String bsnsCode);
	
	/**
	 * 시군구별 현황
	 * @param bsnsCode
	 * @return
	 */
	CsTransferObject getStatsDatasBySigungu(String bsnsCode, String sidoCode);
	
	/**
	 * 연령별 현황
	 * @param bsnsCode
	 * @return
	 */
	CsTransferObject getStatsDatasByAge(String bsnsCode);
	
	/**
	 * 품목군별 현황
	 * @param bsnsCode
	 * @return
	 */
	CsTransferObject getStatsDatasByPrdlstSet(String bsnsCode);
}
