/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 지원 사업 통계 년도
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportBsnsStatsYearSqlMapper {

	@Select({"SELECT STDR_YEAR"
			+ "		,SUM(FMER_FRE) AS SUM_FMER_FRE"
			+ "	FROM	T_SPORT_BSNS_STATS_YEAR"
			+ "	WHERE	SPORT_BSNS_CODE = #{bsnsCode}"
			+ "	GROUP BY STDR_YEAR"})
	List<Map<String,Object> >getDatasByStdrYear(String bsnsCode);
}
