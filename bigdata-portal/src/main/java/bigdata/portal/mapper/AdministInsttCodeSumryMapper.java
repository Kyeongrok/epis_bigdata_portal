/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @author hyunseongkil
 *
 */
@Mapper
public interface AdministInsttCodeSumryMapper {

	/**
	 * (모든)시도 목록
	 * @return
	 */
	@Select({"SELECT CTPRVN_NM AS sidoName"
			+ "		, CTPRVN_CODE AS sidoCode"
			+ "	FROM T_ADMINIST_INSTT_CODE_SUMRY"
			+ "	GROUP BY CTPRVN_CODE, CTPRVN_NM"
			+ "	ORDER BY CTPRVN_CODE"})
	List<Map<String,Object>> getAllSidos();
	
	/**
	 * (모든)시군구 목록
	 * @return
	 */
	@Select({"SELECT CTPRVN_NM AS sidoName"
			+ "		, CTPRVN_CODE AS sidoCode"
			+ "		, SIGNGU_NM AS sigunguName"
			+ "		, SIGNGU_CODE AS sigunguCode"
			+ "	FROM T_ADMINIST_INSTT_CODE_SUMRY"
			+ "	GROUP BY  CTPRVN_CODE, CTPRVN_NM, SIGNGU_CODE, SIGNGU_NM"
			+ "	ORDER BY SIGNGU_NM"})
	List<Map<String,Object>> getAllSigungus();
	
	/**
	 * 시군구 목록
	 * @param sidoCode 시도 코드
	 * @return
	 */
	@Select({"SELECT DISTINCT SIGNGU_NM AS sigunguName"
			+ "		, SIGNGU_CODE AS sigunguCode"
			+ "	FROM T_ADMINIST_INSTT_CODE_SUMRY"
			+ "	WHERE	CTPRVN_CODE = #{sidoCode} "
			+ "	ORDER BY SIGNGU_NM"})
	List<Map<String,Object>> getSigungusBySido(String sidoCode);
}
