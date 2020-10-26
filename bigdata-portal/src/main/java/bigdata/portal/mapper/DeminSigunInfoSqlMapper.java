/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 분모 지역
 * @author hyunseongkil
 *
 */
@Mapper
public interface DeminSigunInfoSqlMapper {

	/**
	 * 시도 목록
	 * @return
	 */
	@Select({"SELECT	BUBJUNG_SD_NM AS sidoName"
			+ "			, SUBSTRING(SIGUNGU_CODE,1,2) AS sidoCode"
			+ "			, SUM(FREQUENCY) AS sumFre"
			+ "	FROM	FS_DEMIN_SIGUN_INFO"
			+ "	GROUP BY BUBJUNG_SD_NM"
			+ "			, SUBSTRING(SIGUNGU_CODE,1,2)"
			+ "	ORDER BY	BUBJUNG_SD_NM"})
	List<Map<String,Object>> getSidos();
	
	
	/**
	 * 시군구 목록
	 * @param sidoCode 시도코드
	 * @return
	 */
	@Select({"SELECT	BUBJUNG_SK_NM as sigunguName"
			+ "			, SIGUNGU_CODE AS sigunguCode"
			+ "			, SUM(FREQUENCY) AS sumFre"
			+ "	FROM	FS_DEMIN_SIGUN_INFO"
			+ "	WHERE	SIGUNGU_CODE LIKE CONCAT(#{sidoCode}, '%')"
			+ "	GROUP BY 	BUBJUNG_SK_NM"
			+ "				,SIGUNGU_CODE"
			+ "	ORDER BY BUBJUNG_SK_NM"})
	List<Map<String,Object>> getSigungusBySido(String sidoCode);
}
