/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 분자 - 품목
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportBsnsStatsPrdlstSqlMapper {

	@Select({"SELECT	PRDLST_SET_CODE AS prdlstSetCode"
			+ "		,SUM(FMER_FRE) AS sumFmerFre"
			+ "	FROM	T_SPORT_BSNS_STATS_PRDLST"
			+ "	WHERE	SPORT_BSNS_CODE = #{bsnsCode}"
			+ "	GROUP BY PRDLST_SET_CODE"})
	List<Map<String,Object>> gets(String bsnsCode);
}
