/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 품목군,품목 코드
 * @author hyunseongkil
 *
 */
@Mapper
public interface AtmnentPrdlstCodeInfoMapper {

	/**
	 * 품목군 목록
	 * @return
	 */
	@Select({"SELECT	LCLAS_CODE AS lclasCode"
			+ "			,LCLAS_NM AS lclasNm"
			+ "	FROM T_ATMNENT_PRDLST_CODE_INFO"
			+ "	GROUP BY LCLAS_CODE, LCLAS_NM"
			+ "	ORDER BY LCLAS_NM"})
	List<Map<String,Object>> getLclass();
	
	
	/**
	 * 전체 품목 목록
	 * @return
	 */
	@Select({"SELECT	LCLAS_CODE AS lclasCode"
			+ "			,LCLAS_NM AS lclasNm"
			+ "			,PRDLST_CODE AS prdlstCode"
			+ "			,SCLAS_NM AS sclasNm"
			+ "	FROM T_ATMNENT_PRDLST_CODE_INFO"
			+ "	ORDER BY LCLAS_NM, SCLAS_NM"})
	List<Map<String,Object>> getAlls();
}
