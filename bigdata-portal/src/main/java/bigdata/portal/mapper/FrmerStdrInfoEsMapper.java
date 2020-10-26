/**
 * 
 */
package bigdata.portal.mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.ucsit.core.CsUtil;

/**
 * 농업인 기준 정보
 * @author hyunseongkil
 *
 */
@Mapper
public class FrmerStdrInfoEsMapper {
	private static Logger log = LoggerFactory.getLogger(FrmerStdrInfoEsMapper.class);
	
	@Autowired
	private ElasticService elasticService;
	
	
	
	//
	private static String INDEX = "ds_fs_frmer_stdr_info";
	
	private static Integer SIZE = 10000;

	/**
	 * 성별로 중복제거된 농업인 목록 조회
	 * @param sexdstn 성별 1:남자 2:여자
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Set<String> getFrmerNoSetBySexdstn(String sexdstn) throws InstantiationException, IllegalAccessException, IOException{
		
		long totcnt = getTotcntBySexdstn(sexdstn);
		
		//
		Set<String> set = new HashSet<String>();
		ElasticResultMap resultMap = null;
		
		//
		String _id="";
		
		int i=0;
		while(true) {
			log.debug("+.getFrmerNoSetBySexdstn - #{} mem:{}/{}", i++, CsUtil.getFreeMemory(), CsUtil.getTotalMemory());
			
			//
			String qry = "{\r\n" + 
					"  \"query\": {\r\n" + 
					"    \"bool\": {\r\n" + 
					"      \"must\": [\r\n" + 
					"        {\r\n" + 
					"          \"match\":{\"sexdstn\":\""+sexdstn+"\"}\r\n" + 
					"        }      \r\n" + 
					"      ]\r\n" + 
					"    }\r\n" + 
					"  }\r\n" + 
					"  ,\"size\":" + SIZE; 
			
			if(CsUtil.isNotEmpty(_id)) {
				qry += ", \"search_after\" : [\""+_id+"\"]";
			}
			
			qry += ", \"sort\":[{\"_id\":\"asc\"}]";
			
			qry += "}";
			
			//
			resultMap = elasticService.postSearch(INDEX, qry);
			_id = getLastId(resultMap);
			
			//
			set.addAll( getFrmerNoSet( resultMap.getHitsInHits()) );	
			
			//
			if(0 == resultMap.getHitsInHits().size()) {
				break;
			}
			
		}
		
		
		//
		log.info("<<.getFrmerNoSetBySexdstn - {} {}", sexdstn, set.size());
		return set;
	}
	
	private String getLastId(ElasticResultMap resultMap) {
		if(null == resultMap) {
			return "";
		}
		
		List<Map<String,Object>> hitsInHits = resultMap.getHitsInHits();
		if(CsUtil.isEmpty(hitsInHits)) {
			return "";
		}
		
		Map<String,Object> data = hitsInHits.get(hitsInHits.size()-1);
		return "" + data.get("_id");
	}

	/**
	 * 성별로 조회했을 때 전체 건수
	 * @param sexdstn
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private long getTotcntBySexdstn(String sexdstn) throws InstantiationException, IllegalAccessException, IOException {
		//
		String qry = "{\r\n" + 
				"  \"query\": {\r\n" + 
				"    \"bool\": {\r\n" + 
				"      \"must\": [\r\n" + 
				"        {\r\n" + 
				"          \"match\":{\"sexdstn\":\""+sexdstn+"\"}\r\n" + 
				"        }      \r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}";		
		
		ElasticResultMap resultMap = elasticService.postCount(INDEX, qry);
		Map<String,Object> data = new Gson().fromJson(resultMap.getRawString(), Map.class);
		return Math.round(Double.valueOf(""+data.get("count")));
	}

	/**
	 * 나이로 중복제거된 농업인 목록 조회
	 * @param sexdstn 성별
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception 
	 */
	public Set<String> getFrmerNoSetByAge(Integer minYear, Integer maxYear) throws InstantiationException, IllegalAccessException, IOException  {
		long totcnt = getTotcntByAge(minYear, maxYear);
		
		//
		ElasticResultMap resultMap;
		Set<String> set = new HashSet<String>();
		
		//
		for(int i=0; i<totcnt; i+=SIZE) {
			//
			String qry = "{\r\n" + 
					"  \"query\": {\r\n" + 
					"    \"bool\": {\r\n" + 
					"      \"must\": [\r\n" + 
					"        {\r\n" + 
					"          \"range\": {\r\n" + 
					"            \"brth_yyyy\": {\r\n" + 
					"              \"gte\": "+minYear+",\r\n" + 
					"              \"lte\": "+maxYear+"\r\n" + 
					"            }\r\n" + 
					"          }\r\n" + 
					"        }      \r\n" + 
					"      ]\r\n" + 
					"    }\r\n" + 
					"  }\r\n" + 
					"  ,\"from\":" + i + ", \"size\" : "+SIZE+"	\r\n" + 
					"}\r\n" + 
					"";
			
			//
			resultMap = elasticService.postSearch(INDEX, qry);
			
			//
			set.addAll( getFrmerNoSet( resultMap.getHitsInHits()) );			
		}
		
		//
		log.info("<<.getFrmerNoSetByAge - {}~{} {}", minYear, maxYear, set.size());
		return set;
	}
	
	/**
	 * 나이로 검색 했을 때 전체 건수
	 * @param sexdstn 성별
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private long getTotcntByAge(Integer minYear, Integer maxYear) throws InstantiationException, IllegalAccessException, IOException {
		
		//
		String qry = "{\r\n" + 
				"  \"query\": {\r\n" + 
				"    \"bool\": {\r\n" + 
				"      \"must\": [\r\n" + 
				"        {\r\n" + 
				"          \"range\": {\r\n" + 
				"            \"brth_yyyy\": {\r\n" + 
				"              \"gte\": "+minYear+",\r\n" + 
				"              \"lte\": "+maxYear+"\r\n" + 
				"            }\r\n" + 
				"          }\r\n" + 
				"        }      \r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"";
		
		//
		ElasticResultMap resultMap = elasticService.postCount(INDEX, qry);
		Map<String,Object> data = new Gson().fromJson(resultMap.getRawString(), Map.class);
		
		//
		return Math.round(Double.valueOf("" + data.get("count")));
	}
	
	
	/**
	 * 영농 구간으로 중복제거된 농업인 목록 조회
	 * @param minYear 최소 년도
	 * @param maxYear 최대 년도
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Set<String> getFrmerNoSetByFarmCareer(Integer minValue, Integer maxValue) throws InstantiationException, IllegalAccessException, IOException{
		long totcnt = getTotcntByFarmCareer(minValue, maxValue);
		
		//
		ElasticResultMap resultMap;
		Set<String> set = new HashSet<String>();
		
		//
		for(int i=0; i<totcnt; i+=SIZE) {
			//
			String qry = "{\r\n" + 
					"  \"query\": {\r\n" + 
					"    \"bool\": {\r\n" + 
					"      \"must\": [\r\n" + 
					"        {\r\n" + 
					"          \"range\": {\r\n" + 
					"            \"farm_begin_ym\": {\r\n" + 
					"              \"gte\": " + minValue + "01,\r\n" + 
					"              \"lte\": " + maxValue + "12\r\n" + 
					"            }\r\n" + 
					"          }\r\n" + 
					"        }      \r\n" + 
					"      ]\r\n" + 
					"    }\r\n" + 
					"  }\r\n" + 
					"  ,\"from\":" + i + ", \"size\" : "+SIZE+"	\r\n" + 
					"}\r\n" + 
					"";
			
			resultMap = elasticService.postSearch(INDEX, qry);
			
			//
			set.addAll(getFrmerNoSet( resultMap.getHitsInHits()));			
		}
		
		//
		log.info("<<.getFrmerNoSetByFarmCareer - {}~{} {}", minValue, maxValue, set.size());
		return set;
	}
	
	/**
	 * 영농 구간으로 전체 건수 조회
	 * @param minYear 최소 년도
	 * @param maxYear 최대 년도
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private long getTotcntByFarmCareer(Integer minValue, Integer maxValue) throws InstantiationException, IllegalAccessException, IOException {
		
		//
		String qry = "{\r\n" + 
				"  \"query\": {\r\n" + 
				"    \"bool\": {\r\n" + 
				"      \"must\": [\r\n" + 
				"        {\r\n" + 
				"          \"range\": {\r\n" + 
				"            \"farm_begin_ym\": {\r\n" + 
				"              \"gte\": " + minValue + "01,\r\n" + 
				"              \"lte\": " + maxValue + "12\r\n" + 
				"            }\r\n" + 
				"          }\r\n" + 
				"        }      \r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"";
		
		ElasticResultMap resultMap = elasticService.postCount(INDEX, qry);
		Map<String,Object> data = new Gson().fromJson(resultMap.getRawString(), Map.class);
		
		//
		return Math.round(Double.valueOf("" + data.get("count")));
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
	

	
	
}
