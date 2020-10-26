package bigdata.portal.mapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 지원사업 품목상세
 * @author hyunseongkil
 *
 */
@Mapper
public class SportPrdlstDetailEsMapper {
	
	@Autowired
	private ElasticService elasticService;
	
	//TODO
	private static final String INDEX = "ds_fs_sport_prdlst_detail";

	public List<Map<String,Object>> getCounts(Set<String> frmerNoSet, String[] bsnsCodes) throws InstantiationException, IllegalAccessException, IOException {
		//TODO
		String qry = "";
		
		
		ElasticResultMap resultMap = elasticService.postSearch(INDEX, qry);
		List<Map<String,Object>> hitsInHits = resultMap.getHitsInHits();
		
		//
		return null;
	}
}
