/**
 * 
 */
package bigdata.portal.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 유사 경영체 구간
 * @author hyunseongkil
 *
 */
@Mapper
public interface SimilrSctnSqlMapper {
	
	/**
	 * 나이 구간값 조회
	 * @param age 나이
	 * @return
	 */
	Map<String,Object> getDataByAge(@Param("age") Integer age);
	
	/**
	 * 영농 경력 구분값 조회
	 * @param career 영농 경력
	 * @return
	 */
	Map<String,Object> getDataByCareer(@Param("career") Integer career);
	
	/**
	 * 품목 구간값 조회
	 * @param ctvtTyCode 시설구분. 시설/노지 sisul/noji
	 * @param prdlstCode 품목 코드
	 * @param arOrCo 면적 or 두수
	 * @return
	 */
	Map<String,Object> getDataByPrdlst(@Param("ctvtTyCode") String ctvtTyCode, @Param("prdlstCode") String prdlstCode, @Param("arOrCo") Integer arOrCo);
	

}
