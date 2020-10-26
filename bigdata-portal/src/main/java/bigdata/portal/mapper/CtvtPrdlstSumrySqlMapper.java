/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 영농지원 재배품목 요약
 * @author hyunseongkil
 *
 */
@Mapper
public interface CtvtPrdlstSumrySqlMapper {

	/**
	 * 품목코드, 면적 구간값으로 중복제거된 농업인 목록 조회
	 * @param prdlstCode 품목코드
	 * @param minAr 최소 면적
	 * @param maxAr 최대 면적
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_CTVT_PRDLST_SUMRY"
			+ "	WHERE	CTVT_PRDLST_CODE = #{prdlstCode}"
			+ "	AND	#{minAr} <= CTVT_AR"
			+ "	AND	CTVT_AR < #{maxAr}"})
	Set<String> getDistinctFrmerNods(@Param("prdlstCode") String prdlstCode, @Param("minAr") int minAr, @Param("maxAr") int maxAr);
}
