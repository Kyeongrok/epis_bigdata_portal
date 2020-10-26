/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 분모 - 품목
 * @author hyunseongkil
 *
 */
@Mapper
public interface DeminPrdlstInfoSqlMapper {

	@Select({"SELECT A.LCLAS_CODE AS prdlstSetCode"
			+ "		,A.LCLAS_NM AS prdlstSetName"
			+ "		, SUM(B.SUM_FRE) AS sumFre"
			+ "	FROM T_ATMNENT_PRDLST_CODE_INFO A"
			+ "	INNER JOIN ("
			+ "				SELECT PRDLST_SET_CODE"
			+ "					, SUM(FREQUENCY) AS SUM_FRE"
			+ "				FROM FS_DEMIN_PRDLST_INFO"
			+ "				GROUP BY PRDLST_SET_CODE) B "
			+ "	ON A.LCLAS_CODE = B.PRDLST_SET_CODE"
			+ "	GROUP BY A.LCLAS_CODE, A.LCLAS_NM"
			+ "	ORDER BY A.LCLAS_NM"})
	List<Map<String,Object>> gets();
}
