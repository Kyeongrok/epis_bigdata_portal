/**
 * 
 */
package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 년도별 
 * @author hyunseongkil
 *
 */
@Mapper
public interface DeminFrmerInfoSqlMapper {

	@Select({"SELECT	CAST(`YEAR` AS CHAR) AS STDR_YEAR"
			+ "			,`FREQUENCY` AS FRMER_COUNT"
			+ "	FROM FS_DEMIN_FRMER_INFO"
			+ ""})
	List<Map<String,Object>> getDatas();
}
