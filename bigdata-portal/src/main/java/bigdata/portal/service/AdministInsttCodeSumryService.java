/**
 * 
 */
package bigdata.portal.service;

import java.util.List;
import java.util.Map;

/**
 * 시도,시군구 
 * @author hyunseongkil
 *
 */
public interface AdministInsttCodeSumryService {

	/**
	 * 전체 시도 목록
	 * @return
	 */
	List<Map<String,Object>> getAllSidos();
	
	/**
	 * 전체 시군구 목록
	 * @return
	 */
	List<Map<String,Object>> getAllSigungus();
}
