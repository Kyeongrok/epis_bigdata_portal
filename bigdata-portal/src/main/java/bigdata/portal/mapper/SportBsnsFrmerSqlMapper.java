/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportBsnsFrmerSqlMapper {

	List<Map<String,Object>> getCountsByBsnsCode(Map<String,Object> paramMap);
}
