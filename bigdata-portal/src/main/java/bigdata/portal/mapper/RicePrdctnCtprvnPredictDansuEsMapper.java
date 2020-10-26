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
 * 쌀 시도
 * @author hyunseongkil
 *
 */
@Mapper
public class RicePrdctnCtprvnPredictDansuEsMapper {
	
	private static final String INDEX = "ds_rice_prdctn_ctprvn_predict_dansu";

	@Autowired
	private ElasticService elasticService;
	
	public ElasticResultMap getDatas() throws InstantiationException, IllegalAccessException, IOException {
		String s = "{\"size\":10000}";
		
		//
		return elasticService.postSearch(INDEX, s);
	}
	
}
