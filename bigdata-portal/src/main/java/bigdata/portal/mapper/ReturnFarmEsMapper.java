package bigdata.portal.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * 도매시장 경락가격
 * @author whddb
 *
 */
@Mapper
public class ReturnFarmEsMapper {

	private static final Logger logger = LoggerFactory.getLogger(ReturnFarmEsMapper.class);

	@Autowired
	private ElasticService elasticService;

	/**도매시장 경락가격
	 *
	 * <br/>paramMap.get("prdlstNm") > 재배품목
	 * <br/>paramMap.get("srchEsYear") > 조회년도
	 * <br/>paramMap.get("ctprvn") > 시도
	 * <br/>paramMap.get("signgu") > 시군구
	 * */
	public ElasticResultMap getAvgWholeSale(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {
		String ctprvn = paramMap.get("ctprvn")+"";
		String signgu = paramMap.get("signgu")+"";
		String addr = "";
		
		/*jhok
		 * 도매시장 경락가격 2020년도는 최근3개월만 가져오게 변경처리
		 * 
		 * */
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdfMonthDate = new SimpleDateFormat("MMdd");
		
		String WholSaleStartDate = sdfMonthDate.format(cal.getTime());
		
		cal.add(Calendar.MONTH, -3);
		String WholSaleEndDate = sdfMonthDate.format(cal.getTime());
		
		String fromDate = paramMap.get("srchEsYear") + WholSaleEndDate;
		String toDate =   paramMap.get("srchEsYear") + WholSaleStartDate;
		
		
		
		if(!"null".equals(ctprvn)) {
			addr += ctprvn + " ";
		}
		if(!"null".equals(signgu)) {
			addr += signgu;
		}

		String s = "";
		s += "{";
		s += "  \"size\" : 0,";
		s += "  \"query\": {";
		s += "    \"bool\": {";
		s += "      \"must\": [";

		if(!"".equals(addr)) { // 지역
			s += "		  {";
		    s += " 			\"match_phrase\":{";
		    s += "        		\"std_mtc_new_nm\" : 	\""+addr+"\"";
		    s += "        	}";
		    s += "    	  },";
		}
		s += "        {";
		s += "          \"range\": {";
		s += "            \"delng_de\": {";
		if(!"".equals(paramMap.get("srchEsYear")) && "2020".equals(paramMap.get("srchEsYear"))) {
			s += "              \"gte\": "+ fromDate + ",";
			s += "              \"lte\": "+ toDate + "";
		}else {
		s += "              \"gte\": "+ paramMap.get("srchEsYear") + "0101,";
		s += "              \"lte\": "+ paramMap.get("srchEsYear") + "1231";
		}
		s += "            }";
		s += "          }";
		s += "        }";
		s += "      ]";
		s += "      ,";
		s += " \"should\": [";
		s += "            {\"term\": {\"std_prdlst_new_nm\": \""+ paramMap.get("prdlstNm") +"\"}},";
		s += "		            {\"term\": {\"std_prdlst_nm\": \""+ paramMap.get("prdlstNm") +"\"}}";
		s += "		          ]";
		s += "    }";
		s += "  },";
		s += "  \"aggs\" : {";
		s += "        \"avg\" : { \"avg\" : { \"field\" : \"sbid_pric\" } }";
		s += "    }";
		s += "}";
		System.out.println(s);
		return elasticService.postSearch("bds_wholesale_market_price*", s);
	}

	/**빈집 정보
	 *
	 * <br/>paramMap.get("signgu") > 시군구
	 * */
	public ElasticResultMap getsUninhbhousInfo(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {
		String s = "";
		s += "{";
		s += "	  \"size\" : 5,";
		s += "	  \"query\": {";
		s += "	    \"bool\": {";
		s += "	      \"must\": [";
		s += "	        { ";
		s += "	          \"match\" :{";
		s += "	            \"SIGUN_NM\" : \""+ paramMap.get("signgu") +"\"";
		s += "	          }";
		s += "	        }";
		s += "	      ]";
		s += "	    }";
		s += "	  },";
		s += "	  \"sort\" : [{";
		s += "	    \"REG_DT\" : {\"order\" : \"desc\"}";
		s += "	    }";
		s += "	  ]";
		s += "	}";

		try{
			return elasticService.postSearch("ds_fmlg_uninhbhous*", s);
		}catch (Exception e){
			return new ElasticResultMap("");
		}


	}

	/**일자리 정보
	 *
	 * <br/>paramMap.get("ctprvn") > 시도
	 * <br/>paramMap.get("signgu") > 시군구
	 * */
	public ElasticResultMap getsJbhntInfo(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {
		String ctprvn = paramMap.get("ctprvn")+"";
		String signgu = paramMap.get("signgu")+"";

		String addr = ctprvn + " " + signgu;

		//귀농인 모델 DB에 시도와 시군구가 같은 데이터가 있어서 아래와 같이 처리한다.
		if(ctprvn.equals(signgu)) {
			addr = signgu; // 시군구 하나만 데이터 입력(예: 세종특별자치시)
		}

		String s = "";
		s += "{";
		s += "	  \"size\" : 5,";
		s += "	  \"query\": {";
		s += "	    \"bool\": {";
		s += "	      \"must\": [";
		s += "	        {";
		s += "	          \"match_phrase\":{";
		s += "	            \"basicAddr\" : \"" + addr + "\"";
		s += "	          }";
		s += "	        }";
		s += "	      ]";
		s += "	    }";
		s += "	  },";
		s += "	  \"sort\" : [";
		s += "	      {\"regDt\" : {\"order\": \"desc\"}}";
		s += "	    ]";
		s += "	}";
		System.out.println("es query:" + s);
		try{
			return elasticService.postSearch("ds_keis_empmn_info*", s);
		} catch (Exception e){
			return new ElasticResultMap("");
		}

	}

	/**평균 소매 가격 정보
	 *
	 * <br/>paramMap.get("prdlstNm") > 재배품목
	 * <br/>paramMap.get("srchEsYear") > 조회년도
	 * <br/>paramMap.get("stratSrchEsMonthDate") > 검색 시작 월,일
	 * <br/>paramMap.get("endSrchEsMonthDate") > 검색 종료 월,일
	 * */
	public ElasticResultMap getsAvgRtlsalInfo(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {
		String srchEsYear = paramMap.get("srchEsYear")+"";
		String stratSrchEsMonthDate = paramMap.get("stratSrchEsMonthDate")+"";
		String endSrchEsMonthDate = paramMap.get("endSrchEsMonthDate")+"";


		String s = "";
		s +="{";
		s +="	  \"size\" : 0,";
		s +="	  \"query\": {";
		s +="	    \"bool\": {";
		s +="	      \"must\": [";
		s +="	         { ";
		s +="	          \"match\" :{";
		s +="	            \"itemname\" : \""+ paramMap.get("prdlstNm")  +"\"";
		s +="	          }";
		s +="	        }";
			if(!"null".equals(srchEsYear)) {
			s +="	        ,{";
			s +="	          \"match\" :{";
			s +="	            \"yyyy\" : \""+ srchEsYear +"\"";
			s +="	          }";
			s +="	        },";
			s +="	        {";
			s +="	          \"range\": {";
			s +="	            \"regday\": {";
			s +="	              \"gte\": \""+ stratSrchEsMonthDate +"\",";
			s +="	              \"lte\": \""+ endSrchEsMonthDate +"\"";
			s +="	            }";
			s +="	          }";
			s +="	        }";
		}
		s +="	      ]";
		s +="	    }";
		s +="	  },";
		s +="	  \"aggs\" : {\r\n" ;
		s +=		"        \"group_by_state\" : {\r\n";
		s +=		"          \"terms\": {\r\n" ;
		s +=		"            \"field\": \"kindname.keyword\",\r\n";
		s +=		"            \"size\": 10\r\n" ;
		s +=		"          },\r\n" ;
		s +=		"          \"aggs\" : {\r\n" ;
		s +=		"            \"avd_value\" : { \"avg\" : { \"field\" : \"price\" } }\r\n" ;
		s +=		"          }\r\n" ;
		s +=		"        }\r\n" ;
		s +=		"    }";
		s +="	}";

		return elasticService.postSearch("bds_whsal_rtlsal_pc*", s);


	}

	/**귀농인 정보 입력 로그 등록
	 *
	 * <br/>paramMap.get("famerInfo") > 귀농인 정보
	 * */
	@SuppressWarnings("unchecked")
	public void registRetnFmlgInfo(Map<String,Object> paramMap) throws InstantiationException, IllegalAccessException, IOException {

		EntityMap famerInfoMap = (EntityMap)paramMap.get("famerInfo");

		String relates = "";
		String relateTexts = "";
		String relateSexdstns = "";
		String relateSexdstnTexts = "";
		String relateAges = "";

		String upperCnsdrIdxs = "";
		String middleCnsdrIdxs = "";
		String lowerCnsdrIdxs = "";

		List<Map<String, Object>> livtgtList = (List<Map<String, Object>>)famerInfoMap.get("livtgt");
		List<Map<String, Object>> upperCnsdrList = (List<Map<String, Object>>)famerInfoMap.get("upperCnsdr");
		List<Map<String, Object>> middleCnsdrList = (List<Map<String, Object>>)famerInfoMap.get("middleCnsdr");
		List<Map<String, Object>> lowerCnsdrList = (List<Map<String, Object>>)famerInfoMap.get("lowerCnsdr");
		// 동거가족
		for(Map<String, Object> livtgt : livtgtList) {
			for(String key : livtgt.keySet()) {
				switch(key) {
				case "relate" : relates += livtgt.get(key)+","; break;
				case "relateText" : relateTexts += livtgt.get(key)+","; break;
				case "relateSexdstn" : relateSexdstns += livtgt.get(key)+","; break;
				case "relateSexdstnText" : relateSexdstnTexts += livtgt.get(key)+","; break;
				case "relateAge" : relateAges += livtgt.get(key)+","; break;
				}
			}
		}

		// 상위고려사항
		for(Map<String, Object> upperCnsdr : upperCnsdrList) {
			for(String key : upperCnsdr.keySet()) {
				switch(key) {case "idx" : upperCnsdrIdxs +=  upperCnsdr.get(key)+","; break;}
			}
		}
		// 중위고려사항
		for(Map<String, Object> middleCnsdr : middleCnsdrList) {
			for(String key : middleCnsdr.keySet()) {
				switch(key) {case "idx" : middleCnsdrIdxs +=  middleCnsdr.get(key)+","; break;}
			}
		}
		// 하위고려사항
		for(Map<String, Object> lowerCnsdr : lowerCnsdrList) {
			for(String key : lowerCnsdr.keySet()) {
				switch(key) {case "idx" : lowerCnsdrIdxs +=  lowerCnsdr.get(key)+","; break;}
			}
		}

		//문자열 맨 끝 쉼표 제거
		if(relates.length() >= 1)	relates = relates.substring(0, relates.length()-1);
		if(relateTexts.length() >= 1)	relateTexts = relateTexts.substring(0, relateTexts.length()-1);
		if(relateSexdstns.length() >= 1)	relateSexdstns = relateSexdstns.substring(0, relateSexdstns.length()-1);
		if(relateSexdstnTexts.length() >= 1)	relateSexdstnTexts = relateSexdstnTexts.substring(0, relateSexdstnTexts.length()-1);
		if(relateAges.length() >= 1)	relateAges = relateAges.substring(0, relateAges.length()-1);
		if(upperCnsdrIdxs.length() >= 1)	upperCnsdrIdxs = upperCnsdrIdxs.substring(0, upperCnsdrIdxs.length()-1);
		if(middleCnsdrIdxs.length() >= 1)	middleCnsdrIdxs = middleCnsdrIdxs.substring(0, middleCnsdrIdxs.length()-1);
		if(lowerCnsdrIdxs.length() >= 1)	lowerCnsdrIdxs = lowerCnsdrIdxs.substring(0, lowerCnsdrIdxs.length()-1);

		String s = "";

		s +="{";
		s +="	  \"ip\" : \""+paramMap.get("ip")+"\",";
		s +="	  \"registDtm\" : \"" +paramMap.get("registDtm")+ "\",";

		s +="	  \"hopeCtprvn\" : \"" +famerInfoMap.getStringNulltoEmpty("hopeCtprvn")+"\",";
		s +="	  \"mvtRdNmAdr\" : \"" +famerInfoMap.getStringNulltoEmpty("mvtRdNmAdr")+"\",";
		s +="	  \"selfSexdstnText\" : \"" +famerInfoMap.getStringNulltoEmpty("selfSexdstnText")+"\",";
		s +="	  \"srchMvtCtprvn\" : \"" +famerInfoMap.getStringNulltoEmpty("srchMvtCtprvn")+"\",";
		s +="	  \"selfSexdstn\" : \"" +famerInfoMap.getStringNulltoEmpty("selfSexdstn")+"\",";
		s +="	  \"hopeCtvt\" : \"" +famerInfoMap.getStringNulltoEmpty("hopeCtvt")+"\",";
		s +="	  \"mvtCtprvn\" : \"" +famerInfoMap.getStringNulltoEmpty("mvtCtprvn")+"\",";
		s +="	  \"lowerCnsdrIdx\" : \"" +lowerCnsdrIdxs+"\",";
		s +="	  \"relate\" : \"" +relates+"\",";
		s +="	  \"relateText\" : \"" +relateTexts+"\",";
		s +="	  \"relateSexdstn\" : \"" +relateSexdstns+"\",";
		s +="	  \"relateSexdstnText\" : \"" +relateSexdstnTexts+"\",";
		s +="	  \"relateAge\" : \"" +relateAges+"\",";
		s +="	  \"mvtEmd\" : \"" +famerInfoMap.getStringNulltoEmpty("mvtEmd")+"\",";
		s +="	  \"hopeRdNmAdr\" : \"" +famerInfoMap.getStringNulltoEmpty("hopeRdNmAdr")+"\",";
		s +="	  \"hopeEmd\" : \"" +famerInfoMap.getStringNulltoEmpty("hopeEmd")+"\",";
		s +="	  \"mvtSigngu\" : \"" +famerInfoMap.getStringNulltoEmpty("mvtSigngu")+"\",";
		s +="	  \"middleCnsdrIdx\" : \"" +middleCnsdrIdxs+"\",";
		s +="	  \"hopeSigngu\" : \"" +famerInfoMap.getStringNulltoEmpty("hopeSigngu")+"\",";
		s +="	  \"upperCnsdrIdx\" : \"" +upperCnsdrIdxs+"\",";
		s +="	  \"selfAge\" : \"" +famerInfoMap.getStringNulltoEmpty("selfAge")+"\",";
		s +="	  \"mvtAdmCd\" : \"" +famerInfoMap.getStringNulltoEmpty("mvtAdmCd")+"\"";
		s += "}";

		elasticService.postDoc("return_farm_user_input", s);
	}
}
