/**
 *
 */
package bigdata.portal.mapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.ucsit.core.CsUtil;

/**
 * 유사경영체
 * @author hyunseongkil
 *
 */
@Mapper
public class SportBsnsFrmerEsMapper {

	@Autowired
	private ElasticService elasticService;

	private static final String INDEX = "ds_fs_sport_bsns_frmer";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ElasticResultMap getCountsByBsnsCode(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {

		String bsnsCodes = "\"" + String.join("\",\"", (List)paramMap.get("bsnsCodes")) + "\"";

		String s = "";
		s += "{\n";
		s += "	\"query\": {\n";
		s += "		\"bool\": {\n";
		s += "			\"must\": [\n";


		//사업코드
		s += "				{";
		s += "					\"terms\": {";
		s += "						\"sport_bsns_code\": ["+bsnsCodes+"] ";
		s += "					} ";
		s += "				}\n ";


		//성별
		if(CsUtil.isNotEmpty(paramMap.get("sexdstn"))){
			String sexdstn = "" + paramMap.get("sexdstn");
			if("1".equals(sexdstn) || "3".equals(sexdstn)){
				sexdstn = "\"1\",\"3\"";
			}else{
				sexdstn = "\"2\",\"4\"";
			}
			
			s += "			,{ ";
			s += "				\"terms\": { ";
			s += "					\"sexdstn\": [" + sexdstn + "] ";
			s += "				} ";
			s += "			}\n ";
		}


		//연령
		if(CsUtil.isNotEmpty(paramMap.get("minBrthYyyy"))){
			s += "			, { ";
			s += "				\"range\": { ";
			s += "					\"brth_yyyy\": { ";
			s += "						\"gte\": " + paramMap.get("minBrthYyyy") + ", ";
			s += "						\"lt\": " + paramMap.get("maxBrthYyyy") + " ";
			s += "					} ";
			s += "				} ";
			s += "			}\n";
		}
		
		
		//영농 경력
		if(CsUtil.isNotEmpty(paramMap.get("minFarmBeginYm"))){
			s += "			, { ";
			s += "				\"range\": { ";
			s += "					\"farm_begin_ym\": { ";
			s += "						\"gte\": " + paramMap.get("minFarmBeginYm") + ", ";
			s += "						\"lt\": " + paramMap.get("maxFarmBeginYm") + " ";
			s += "					} ";
			s += "				} ";
			s += "			}\n ";
		}
		
		
		//품목
		if(CsUtil.isNotEmpty(paramMap.get("ctvtTyCode"))){
			s += "			, { ";
			s += "				\"term\": { ";
			s += "					\"prdlst_code\": { ";
			s += "						\"value\": \"" + paramMap.get("ctvtTyCode") + "\" ";
			s += "					} ";
			s += "				} ";
			s += "			}\n ";
		}
		
		
		//면적
		if(CsUtil.isNotEmpty(paramMap.get("minArOrCo"))){
			s += "			, { ";
			s += "				\"range\": { ";
			s += "					\"ctvt_ar\": { ";
			s += "						\"gte\": " + paramMap.get("minArOrCo") + ", ";
			s += "						\"lt\": " + paramMap.get("maxArOrCo") + " ";
			s += "					} ";
			s += "				} ";
			s += "			}\n ";			
		}


		s += "			]\n";
		s += "		}\n";
		s += "	}\n";
		
		//통계
		s += "	,\"aggs\" : {";
		s += "		\"sport_bsns_codes\" : {";
		s += "			\"terms\" :	{\"field\" : \"sport_bsns_code\"}";
		s += "		}";
		s += "	}\n";
		
		s += "}\n";
		


		//
		return elasticService.postSearch(INDEX, s);
	}

	/**
	 * 년도별 현황
	 * @param bsnsCode
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public ElasticResultMap getStatsDatasByYear(String bsnsCode) throws InstantiationException, IllegalAccessException, IOException {
		String s = "";
		s += " { ";
		s += "   \"query\" : { ";
		s += "     \"terms\":{ ";
		s += "       \"sport_bsns_code\" : [\""+bsnsCode+"\"] ";
		s += "     } ";
		s += "   }, ";
		s += "     \"aggs\" : { ";
		s += "         \"stdr_years\" : { ";
		s += "             \"terms\" : { \"field\" : \"stdr_year\" } ";
		s += "         } ";
		s += "     } ";
		s += " } ";

		return elasticService.postSearch(INDEX, s);
	}
}
