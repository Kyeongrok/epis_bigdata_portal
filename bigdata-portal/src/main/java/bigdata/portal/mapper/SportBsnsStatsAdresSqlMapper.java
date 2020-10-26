/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 분자 지역
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportBsnsStatsAdresSqlMapper {

	/**
	 * 시도별 농업인 건수 목록
	 * @return
	 */
	List<Map<String,Object>> getSidos(String bsnsCode);
	
	/**
	 * 시군구별 농업인 건수 목록
	 * @param sidoCode
	 * @return
	 */
	List<Map<String,Object>> getSigungusBySido(@Param("bsnsCode") String bsnsCode, @Param("sidoCode") String sidoCode);
}
