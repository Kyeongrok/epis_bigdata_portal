/**
 * 
 */
package bigdata.portal.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 축산 요약
 * @author hyunseongkil
 *
 */
@Mapper
public class StallDetailSttusEsMapper {
	
	
	@Autowired
	private ElasticService elasticService;
	
	//TODO
	private static final String INDEX = "ds_fs_stall_detail_sttus";

	/**
	 * 품목코드, 사육두수 구분값으로 중복제거된 농업인 목록 조회
	 * @param brdPrdlstCode (사육)품목코드
	 * @param minCo 최소 사육두수
	 * @param maxCo 최대 사육두수
	 * @return
	 */
	public Set<String> getFrmerNoSet(String brdPrdlstCode, Integer minCo, Integer maxCo) throws Exception{
		//TODO 축산
		String qry = "";
		
		//
		ElasticResultMap resultMap = elasticService.postSearch(INDEX, qry);
		
		return getFrmerNoSet(resultMap.getHitsInHits());
	}
	

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
	
	
	private Integer getValue(Map<String,Object> sctnMap, String gbn) {
		if(null == sctnMap) {
			return null;
		}
		
		//
		if("min".equalsIgnoreCase(gbn)) {
			return Integer.valueOf( "" + sctnMap.get("minValue") );
		}
		
		//
		if("max".equalsIgnoreCase(gbn)) {
			return Integer.valueOf( "" + sctnMap.get("maxValue") );
		}
		
		//
		return null;
	}
}
