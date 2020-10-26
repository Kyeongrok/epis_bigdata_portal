/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 지침서 파일 메타정보
 * @author hyunseongkil
 *
 */
@Mapper
public interface AglfrsBsnsOpertnManualMapper {

	
	/**
	 * 사업 코드로 조회
	 * @param lclasNmCode
	 * @param mlsfcNmCode
	 * @return
	 */
	@Select({"SELECT	LCLAS_NM_CODE"
			+ "			,MLSFC_NM_CODE"
			+ "			,ORGINL_FILE_NM"
			+ "			,STRE_PATH"
			+ "			,STRE_FILE_NM "
			+ "	FROM T_AGLFRS_BSNS_OPERTN_MANUAL	"
			+ "	WHERE LCLAS_NM_CODE = #{lclasNmCode}"
			+ "	AND	MLSFC_NM_CODE = #{mlsfcNmCode}"})
	Map<String,Object> getByLclasNmCodeAndMlsfcNmCode(@Param("lclasNmCode") String lclasNmCode, @Param("mlsfcNmCode") String mlsfcNmCode);
}
