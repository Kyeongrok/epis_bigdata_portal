/**
 * 
 */
package bigdata.portal.mapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 쌀 직불금 신청
 * @author hyunseongkil
 *
 */
@Mapper
public class RiceDirectSbsidyReqstEsMapper {
	
	@Autowired
	ElasticService elasticService;
	
	private String index = "ds_rice_direct_sbsidy_reqst";

	public ElasticResultMap getDatas() throws InstantiationException, IllegalAccessException, IOException {
		
		String s = "{\"size\":10000}";
		
		//
		ElasticResultMap resultMap = elasticService.postSearch(index, s);
		return resultMap;
	}
}
