/**
 * 
 */
package bigdata.portal.mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 영농지원 재배품목 요약
 * @author hyunseongkil
 *
 */
@Mapper
public class CtvtPrdlstSumryEsMapper {
	
	@Autowired
	private ElasticService elasticService;
	
	//TODO
	private static final String INDEX = "ds_fs_ctvt_prdlst_sumry";

	

	@SuppressWarnings("unchecked")
	private Set<String> getFrmerNoSet(List<Map<String,Object>> hitsInHits){
		Set<String> set = new HashSet<String>();
		
		//
		Map<String,Object> sourceMap;
		for(Map<String,Object> d : hitsInHits) {
			sourceMap = (Map<String, Object>) d.get("_source");
			
			set.add("" + sourceMap.get("FRMER_NO"));
		}
		
		//
		return set;
	}


	/**
	 * @param prdlstCode
	 * @param minAr
	 * @param maxAr
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public Set<String> getFrmerNoSet(String prdlstCode, Integer minAr, Integer maxAr) throws InstantiationException, IllegalAccessException, IOException {
		//TODO 농업
		String qry = "";

		//
		ElasticResultMap resultMap = elasticService.postSearch(INDEX, qry);

		return getFrmerNoSet(resultMap.getHitsInHits());
	}
	

}
