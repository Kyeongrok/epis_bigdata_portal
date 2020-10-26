/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 농업인 기준 정보
 * @author hyunseongkil
 *
 */
@Mapper
public interface FrmerStdrInfoSqlMapper {

	/**
	 * 성별로 중복제거된 농업인 목록 조회
	 * @param sexdstn 성별 1:남자 2:여자
	 * @return
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_FRMER_STDR_INFO"
			+ "	WHERE	SEXDSTN = #{sexdstn}"})
	Set<String> getDistinctFrmerNosBySex(@Param("sexdstn") String sexdstn);
	
	/**
	 * 나이로 중복제거된 농업인 목록 조회
	 * @param sexdstn 성별
	 * @return
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_FRMER_STDR_INFO"
			+ "	WHERE BRTH_YYYY = DATE_FORMAT( DATE_ADD(NOW(), INTERVAL (-1 * #{age}) YEAR), '%Y')"})
	Set<String> getDistinctFrmerNosByAge(@Param("age") int age);
	
	
	/**
	 * 연령 년도 구간으로 중복제거된 농업인 목록 조회
	 * @param minYear 최소 년도
	 * @param maxYear 최대 년도
	 * @return
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_FRMER_STDR_INFO"
			+ "	WHERE ${minYear} <= BRTH_YYYY"
			+ "	AND	BRTH_YYYY < ${maxYear}"})
	Set<String> getDistinctFrmerNosByAgeSctn(@Param("minYear") String minYear, @Param("maxYear") String maxYear);
	
	/**
	 * 영농 구간으로 중복제거된 농업인 목록 조회
	 * @param minYear 최소 년도
	 * @param maxYear 최대 년도
	 * @return
	 */
	@Select({"SELECT	DISTINCT FRMER_NO AS frmerNo"
			+ "	FROM	FS_FRMER_STDR_INFO"
			+ "	WHERE CONCAT(#{minYear}, '01') <= FARM_BEGIN_YM"
			+ "	AND	FARM_BEGIN_YM < CONCAT(#{maxYear}, '12')"})
	Set<String> getDistinctFrmerNosByCareerSctn(@Param("minYear") String minYear, @Param("maxYear") String maxYear);
	
}
