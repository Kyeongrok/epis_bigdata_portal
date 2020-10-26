/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 기준 년도별 농민 목록
 * @author hyunseongkil
 *
 */
@Mapper
public interface AtmnentCropsPrdctnMapper {

	/**
	 * 기준 년도별 건수 목록
	 * @return
	 */
	@Select({"SELECT GIGUN_YEAR AS gigunYear"
			+ "		, COUNT(*) AS CNT AS cnt"
			+ "	FROM	T_ATMNENT_CROPS_PRDCTN2"
			+ "	GROUP BY	GIGUN_YEAR"})
	List<Map<String,Object>> getCountsByYear();
	
	/**
	 * 최근 5년 기준 년도별 건수 목록
	 * @return
	 */
	@Select({"SELECT GIGUN_YEAR AS gigunYear"
			+ "		, COUNT(*) AS CNT AS cnt"
			+ "	FROM	T_ATMNENT_CROPS_PRDCTN2"
			+ "	WHERE DATE_ADD(NOW(), INTERVAL -5 YEAR) <=  STR_TO_DATE(GIGUN_YEAR, '%Y') "
			+ "	GROUP BY	GIGUN_YEAR"
			+ "	ORDER BY GIGUN_YEAR"})
	List<Map<String,Object>> get5YearCountsByYear();
	
	
	/**
	 * 최근 5년 기준 년도별 건수 목록
	 * 시설과 union
	 * @return
	 */
	@Select({"SELECT GIGUN_YEAR, COUNT(*)\r\n" + 
			"FROM (\r\n" + 
			"	SELECT GIGUN_YEAR\r\n" + 
			"	FROM T_ATMNENT_CROPS_PRDCTN2\r\n" + 
			"	WHERE DATE_ADD(NOW(), INTERVAL -5 YEAR) <= STR_TO_DATE(GIGUN_YEAR, '%Y') \r\n" + 
			"	UNION ALL\r\n" + 
			"	SELECT GIGUN_YEAR\r\n" + 
			"	FROM T_ATMNENT_FCLTY\r\n" + 
			"	WHERE DATE_ADD(NOW(), INTERVAL -5 YEAR) <= STR_TO_DATE(GIGUN_YEAR, '%Y') \r\n" + 
			") A\r\n" + 
			"ORDER BY A.GIGUN_YEAR"})
	List<Map<String,Object>> get5YearCountsByYearUnionFclty();
}
