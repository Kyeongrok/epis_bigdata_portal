/**
 * 
 */
package bigdata.portal.service;

import java.util.List;
import java.util.Map;

/**
 * 품목군, 품목 코드
 * @author hyunseongkil
 *
 */
public interface AtmnentPrdlstCodeInfoService {

	/**
	 * (전체) 품목군 목록
	 * @return
	 */
	List<Map<String,Object>> getLclass();
	
	/**
	 * (전체) 품목 목록
	 * @return
	 */
	List<Map<String,Object>> getAlls();
}
