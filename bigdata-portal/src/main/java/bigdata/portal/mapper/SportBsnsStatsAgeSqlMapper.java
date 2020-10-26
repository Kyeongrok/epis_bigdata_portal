/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 통계 - 분자 - 연령대별
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportBsnsStatsAgeSqlMapper {

	List<Map<String,Object>> getDatasByAgeGroup(Map<String,Object> paramMap);
}
