/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 축산 요약
 * @author hyunseongkil
 *
 */
@Mapper
public interface StallDetailSttusSqlMapper {

	/**
	 * 품목코드, 사육두수 구분값으로 중복제거된 농업인 목록 조회
	 * @param brdPrdlstCode (사육)품목코드
	 * @param minCo 최소 사육두수
	 * @param maxCo 최대 사육두수
	 * @return
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_STALL_DETAIL_STTUS"
			+ "	WHERE	BRD_PRDLST_CODE = #{brdPrdlstCode}"
			+ "	AND	#{minCo} <= BRD_HEAD_CO"
			+ "	AND	BRD_HEAD_CO < #{maxCo}"})
	Set<String> getDistinctFrmerNods(@Param("brdPrdlstCode") String brdPrdlstCode, @Param("minCo") int minCo, @Param("maxCo") int maxCo);
}
