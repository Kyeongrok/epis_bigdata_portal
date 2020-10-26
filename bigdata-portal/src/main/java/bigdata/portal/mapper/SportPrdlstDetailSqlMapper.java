package bigdata.portal.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 지원사업 품목상세
 * @author hyunseongkil
 *
 */
@Mapper
public interface SportPrdlstDetailSqlMapper {

	/**
	 * @param paramMap	bsnsCodes, sexdstn, minBrthYear, maxBrthYear, minFarmCareerYm, maxFarmCareerYm, prdlstCode, minArOrCo, maxArOrCo
	 * @return
	 */
	List<Map<String,Object>> getCountsByBsnsCode(Map<String,Object> paramMap);
}
