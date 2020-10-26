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
public interface DeminAgeInfoSqlMapper {

	@Select({""
			+ "SELECT	AGE_GROUP"
			+ "			,SUM(`FREQUENCY`) AS SUM_FRE"
			+ "	FROM FS_DEMIN_AGE_INFO"
			+ "	GROUP BY AGE_GROUP"})
	List<Map<String,Object>> getDatasByAgeGroup();
}
