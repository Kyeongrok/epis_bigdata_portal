/**
 * 
 */
package bigdata.portal.mapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.ucsit.core.CsUtil;

/**
 * 맞춤 지원 정책
 * @author hyunseongkil
 *
 */
@Mapper
public class FixesSportPolicyEsMapper {

	@Autowired
	ElasticService elasticService;
	
	String index = "ds_fixes_sport_systm_info_2019";
//	String index = "ngram_ds_fixes_sport_systm_info2019";
	
	/**
	 * 데이터 조회
	 * @param searchGbn 구분
	 * @param searchKeyword 키워드
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getDatas(String searchGbn, String searchKeyword) throws InstantiationException, IllegalAccessException, IOException{

		String s = "";
		
		//
		if("all".equals(searchGbn) && CsUtil.isEmpty(searchKeyword)) {
			s += "{\"size\":10000}";
			
		}else {
			s = "{\r\n" + 
					"  \"query\": {\r\n" + 
					"    \"bool\": {\r\n" + 
					"      \"must\": [\r\n" + 
					getMustString(searchGbn, searchKeyword).replaceAll("'", "\"") +
					"      ]\r\n";
			
			s += "      , ";
			s += "      \"should\": [ ";
			s += "        { ";
			s += "          \"match\": { ";
			s += "            \"SPORT_SUB\": \"농림축산식품부\" ";
			s += "          } ";
			s += "        } ";
			s += "      ] ";
			
			s += "    }\r\n" + 
					"  }\r\n" + 
					"  ,\"size\" : 10000" +				
					"}";
			
		}


		//
		return elasticService.postSearch(index, s);
	}
	

	

	/**
	 * 키워드 검색 문자열 생성
	 * @param searchGbn 구분
	 * @param searchKeyword 키워드
	 * @return
	 */
	private String getMustString(String searchGbn, String searchKeyword) {
		String s = "";
		
		String qry = " 	{\r\n" + 
				"          \"multi_match\": {\r\n" + 
				"            \"query\": \""+searchKeyword+"\",\r\n" + 
				"            \"fields\": [\"MLSFC_NM\",\"DETAIL_BSNS_NM\",\"SPORT_AREA\",\"SPORT_STLE\",\"SIDO\",\"SIGUN\",\"BSNS_SUMMARY\",\"BSNS_KEY\",\"SIM_BSNS\",\"FMER_SEX_M\",\"FMER_SEX_F\",\"FMER_CTVT_PRDLST_GRP\",\"FMER_CTVT_PRDLST_NM\",\"FMER_TYPE_CTVT\",\"CO_HAND_CTVT_GRP\",\"CO_HAND_CTVT\",\"NM_SEX_M\",\"NM_SEX_F\",\"BSNS_CN\"], \r\n" + 
				"            \"operator\": \"and\"\r\n" + 
				"            , \"type\": \"cross_fields\"\r\n" + 
				"          }\r\n" +
				"		}\r\n";
		
		//
		if(CsUtil.isNotEmpty(searchKeyword)) {
			s += qry;
		}
		
		//농업인
		if("frmer".equals(searchGbn)) {
			s += (CsUtil.isEmpty(s) ? "" : ",") + "{'match':{'FMER_REQST':'Y'}}";
		}
		
		//법인
		if("cpr".equals(searchGbn)) {
			s += (CsUtil.isEmpty(s) ? "" : ",") + "{'match':{'CO_REQST':'Y'}}";
		}
		
		//비농업인
		if("notFrmer".equals(searchGbn)) {
			s += (CsUtil.isEmpty(s) ? "" : ",") + "{'match':{'NM_REQST':'Y'}}";
		}
		
		
		//농업인
		
		//법인
		
		//비농업인
		
		//농업인
//		if("frmer".equals(searchGbn)) {
//			String t = searchKeyword.replaceAll("-", "");
//			if(CsUtil.isNum(t) && 10 == t.length()) {
//				//농업경영체 등록 여부
//				s += "{'match':{'FMER_ATMNENT_ESSNTL_AT':'Y'}} \n";
//				s += ",{'match':{'FMER_REQST':'Y'}} \n";
//				return s;
//			}else {
//				qry += ",{'match':{'FMER_REQST':'Y'}} \n";
//				return qry;
//			}
//		}
		
		
		//법인
//		if("cpr".equals(searchGbn)) {
//			qry += ",{'match':{'CO_REQST':'Y'}}";
//			return qry;
//		}
		
		//비농업인
//		if("notFrmer".equals(searchGbn)) {
//			qry += ",{'match':{'NM_REQST':'Y'}}";
//			return qry;
//		}
		
		//
		return s;		
	}
	
	
	
	/**
	 * 세부사업코드로 사업 정보 조회
	 * @param mlsfcNmCode
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getDatasByMlsfcNmCode(String mlsfcNmCode) throws InstantiationException, IllegalAccessException, IOException{
		String qry = "{\r\n" + 
				"  \"query\": {\r\n" + 
				"    \"bool\": {\r\n" + 
				"      \"must\": [\r\n" + 
				"        {\r\n" + 
				"          \"multi_match\": {\r\n" + 
				"            \"query\": \""+mlsfcNmCode+"\",\r\n" + 
				"            \"fields\": [\"MLSFC_NM_CODE\"], \r\n" + 
				"            \"operator\": \"and\"\r\n" + 
				"            , \"type\": \"cross_fields\"\r\n" + 
				"          }\r\n" + 
				"        }\r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"  ,\"size\" : 10000" +
				"}";
		
		//
		return elasticService.postSearch(index, qry);
	}




	/**
	 * 귀농용 데이터 목록 조회
	 * @param searchSido 시도명
	 * @param searchSigungu 시군구명
	 * @param searchUmd 읍면동명
	 * @param searchSportRelmCode 지원정책 목록. 구분자:콤마
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ElasticResultMap getDatasForRetnFmlg(String searchSido, String searchSigungu, String searchUmd, String searchSportRelmCode) throws InstantiationException, IllegalAccessException, IOException {
		String s = "";
		s += "{ ";
		s += "  \"query\": { ";
		s += "    \"bool\": { ";
		s += "      \"must\": [ ";
		s += "        { ";
		s += "          \"multi_match\": { ";
		s += "            \"query\": \"" + String.join(" ", searchSportRelmCode.split(",")) + "\", ";
		s += "            \"fields\": [\"SPORT_AREA\"] ";
		s += "          } ";
		s += "        } ";
		
		//
		if(CsUtil.isNotEmpty(searchSido)) {
			s += "        ,{ ";
			s += "          \"multi_match\": { ";
			s += "            \"query\": \""+searchSido.trim()+"\", ";
			s += "            \"fields\": [\"SIDO\"] ";
			s += "          } ";
			s += "        } ";
		}
		
		//
		if(CsUtil.isNotEmpty(searchSigungu)) {
			s += "        ,{ ";
			s += "          \"multi_match\": { ";
			s += "            \"query\": \""+searchSigungu.trim()+"\", ";
			s += "            \"fields\": [\"SIGUN\"] ";
			s += "          } ";
			s += "        } ";
		}
		
		//
		s += "      ] ";
		s += "    } ";
		s += "  } ";
		s += "  ,\"size\" : 10000 ";
		s += "  ,\"sort\" : [ ";
		s += "      { ";
		s += "        \"SPORT_SUB.keyword\" : { ";
		s += "          \"order\" : \"asc\" ";
		s += "        } ";
		s += "      } ";
		s += "  ] ";
		s += "} ";
		
		//
		return elasticService.postSearch(index, s);
	}
	
}
