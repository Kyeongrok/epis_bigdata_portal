package bigdata.portal.mapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.cmm.util.VilageFcstInfoServiceMap;
import bigdata.portal.entity.EntityMap;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public class JejuWinterMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JejuWinterMapper.class);
	
	private static final String marketPriceIndex = "bds_wholesale_market_runtime_price_";
	
	@Autowired
	private ElasticService elasticService;
	
	
	/**
	 * 평균 면적 전체
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getAvgAreaTotal() throws InstantiationException, IllegalAccessException, IOException {
		String requestJson =

				"";
			
		//
		return elasticService.postSearch("ds_ctvt_inten_examin*", requestJson);
	}
	
	public ElasticResultMap getDansu(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson = 
			"{\n" +
			"  \"_source\": [\"YEAR\", \"MT\", \"DAN_SU\"],\n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"range\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";
		return elasticService.postSearch("ds_pswntr_vgetbl_mnby_dansu*", requestJson);
	}
	
	public ElasticResultMap getDansuByPeriodAvg(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson =
			"{\n" +
			"  \"size\": 0,\n" +
			"  \"aggs\": {\n" +
			"    \"aggs_result\": {\n" +
			"      \"avg\": {\n" +
			"        \"field\": \"DAN_SU\"\n" +
			"      }\n" +
			"    }\n" +
			"  },\n" +
			"  \"_source\": [\"YEAR\", \"DAN_SU\"],\n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"range\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";
		return elasticService.postSearch("ds_pswntr_vgetbl_mnby_dansu*", requestJson);
	}
	
	public ElasticResultMap getDansuByYearMon(String crop, String year, String month) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson =
			"{\n" +
			"  \"_source\": [\"YEAR\", \"MT\", \"DAN_SU\"],\n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"value\": " + year + "\n" +
			"            }\n" +
			"          }\n" +
			"        }, \n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"MT\": {\n" +
			"              \"value\": "+ month +"\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
				"";
		
		return elasticService.postSearch("ds_pswntr_vgetbl_mnby_dansu*", requestJson);
	}
	
	public ElasticResultMap getPriceByDate(String crop, String year, String date)
			throws InstantiationException, IllegalAccessException, IOException {
		String requestJson =
			"{\n" +
			"  \"size\": 0,\n" +
			"  \"aggs\": {\n" +
			"    \"aggs_result\": {\n" +
			"      \"avg\": {\n" +
			"        \"field\": \"sbid_pric\"\n" +
			"      }\n" +
			"    }\n" +
			"  },\n" +
			"  \"_source\": [\"sbid_pric\"],\n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"bool\": {\n" +
			"              \"should\": [\n" +
			"                  {\"term\": {\"std_prdlst_nm\": \"" + crop + "\"}},\n" +
			"                  {\"term\": {\"std_spcies_new_nm.keyword\": \"" + crop + "\"}}\n" +
			"              ]\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"std_unit_new_nm.keyword\": {\n" +
			"              \"value\": \"kg\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"delng_prut\": {\n" +
			"              \"value\": \"20\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"delng_de\": {\n" +
			"              \"value\": \"" + date + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";
		
		return elasticService.postSearch(marketPriceIndex + year + "*", requestJson);
	}
	
	public ElasticResultMap getPrice(String crop, String fromDt, String toDt, String interval, String grade) 
			throws InstantiationException, IllegalAccessException, IOException {
		
		String gradeStr = "";
		
		if(!"".equals(grade)) {
			gradeStr = 
					"        {\n" +
					"          \"term\": {\n" +
					"            \"std_qlity_new_nm\": {\n" +
					"              \"value\": \"" + grade + "\"\n" +
					"            }\n" +
					"          }\n" +
					"        },\n";
		}
		
		String fromYear = fromDt.substring(0, 4);
		String toYear = toDt.substring(0, 4);
		
		int from = Integer.parseInt(fromYear);
		int to = Integer.parseInt(toYear);
		
		String fromIndex = ""; 
		for(int i = from; i < to; i++) {
			fromIndex = fromIndex + "              \"" + marketPriceIndex + Integer.toString(i) + "*" + "\",\n";
		}
		
		// index 검색 범위를 최소화 하기 위해 index filter를 적용한다.
		String indexFilter = 
				"      ,\n" +
				"      \"filter\": [\n" +
				"        {\n" +
				"          \"terms\": {\n" +
				"            \"_index\": [\n" +
				fromIndex +
				"              \"" + marketPriceIndex + toYear + "*" + "\"\n" +
				"            ]\n" +
				"          }\n" +
				"        }\n" +
				"      ]\n" +
				"";
		
		String requestJson = 
				"{\n" +
				"  \"size\": 0,\n" +
				"  \"aggs\": {\n" +
				"    \"aggs\": {\n" +
				"      \"date_histogram\": {\n" +
				"        \"field\": \"delng_de\",\n" +
				"        \"calendar_interval\": \"" + interval + "\",\n" +
				"          \"order\": {\n" +
				"            \"_key\": \"asc\"\n" +
				"          }\n" +
				"      },\n" +
				"      \"aggs\": {\n" +
				"        \"aggs_result\": {\n" +
				"          \"avg\": {\n" +
				"            \"field\": \"sbid_pric\"\n" +
				"          }\n" +
				"        }\n" +
				"      }\n" +
				"    }\n" +
				"  }, \n" +
				"  \"_source\": [\"delng_de\"],\n" +
				"  \"query\": {\n" +
				"    \"bool\": {\n" +
				"      \"must\": [\n" +
				"        {\n" +
				"          \"bool\": {\n" +
				"              \"should\": [\n" +
				"                  {\"term\": {\"std_prdlst_nm\": \"" + crop + "\"}},\n" +
				"                  {\"term\": {\"std_spcies_new_nm.keyword\": \"" + crop + "\"}}\n" +
				"              ]\n" +
				"          }\n" +
				"        },\n" +
				"        {\n" +
				"          \"term\": {\n" +
				"            \"std_unit_new_nm.keyword\": {\n" +
				"              \"value\": \"kg\"\n" +
				"            }\n" +
				"          }\n" +
				"        },\n" +
				"        {\n" +
				"          \"term\": {\n" +
				"            \"delng_prut\": {\n" +
				"              \"value\": \"20\"\n" +
				"            }\n" +
				"          }\n" +
				"        },\n" +
				gradeStr +
				"        {\n" +
				"          \"range\": {\n" +
				"            \"delng_de\": {\n" +
				"              \"gte\": " + fromDt + ",\n" +
				"              \"lte\": " + toDt + "\n" +
				"            }\n" +
				"          }\n" +
				"        }\n" +
				"      ]\n" +
				indexFilter +
				"    }\n" +
				"  }\n" +
				"}\n" +
			"";
		
		return elasticService.postSearch(marketPriceIndex + "*", requestJson);
		
	}
	
	
	/**
	 * 확정재배신고
	 * @param crop
	 * @param fromYear
	 * @param toYear
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getCtvtSttemnt(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson = 
			"{ \n" +
			"  \"size\": 0, \n" +
			"  \"aggs\": { \n" +
			"    \"group_by_year\": { \n" +
			"      \"terms\": { \n" +
			"        \"field\": \"STTEMNT_YEAR\" \n" +
			"      }, \n" +
			"      \"aggs\": { \n" +
			"        \"aggs_result\": { \n" +
			"          \"sum\": { \n" +
			"            \"field\": \"STTEMNT_AR\" \n" +
			"          } \n" +
			"        } \n" +
			"      } \n" +
			"    } \n" +
			"  }, \n" +
			"  \"_source\": [\"AR\"],  \n" +
			"  \"query\": { \n" +
			"    \"bool\": { \n" +
			"      \"must\": [ \n" +
			"        { \n" +
			"          \"term\": { \n" +
			"            \"PRDLST_NM\": { \n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            } \n" +
			"          } \n" +
			"        }, \n" +
			"        { \n" +
			"          \"range\": { \n" +
			"            \"STTEMNT_YEAR\": { \n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            } \n" +
			"          } \n" +
			"        } \n" +
			"      ] \n" +
			"    } \n" +
			"  }  \n" +
			"} \n" +
			"";
		
		return elasticService.postSearch("ds_ctvt_ar_sttemnt*", requestJson);
	}
	
	/**
	 * 재배 의향
	 * @param crop
	 * @param odr
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ElasticResultMap getCtvtInten(String crop, String fromYear, String toYear, int odr) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson = 
				"{\n" +
				"  \"size\": 0,\n" +
				"  \"aggs\": {\n" +
				"    \"group_by_year\": {\n" +
				"      \"terms\": {\n" +
				"        \"field\": \"EXMINYEAR.keyword\"\n" +
				"      },\n" +
				"      \"aggs\": {\n" +
				"        \"aggs_result\": {\n" +
				"          \"sum\": {\n" +
				"            \"field\": \"AR\"\n" +
				"          }\n" +
				"        }\n" +
				"      }\n" +
				"    }\n" +
				"  },\n" +
				"  \"_source\": [\"AR\"], \n" +
				"  \"query\": {\n" +
				"    \"bool\": {\n" +
				"      \"must\": [\n" +
				"        {\n" +
				"          \"term\": {\n" +
				"            \"PRDLST_NM\": {\n" +
				"              \"value\": \"" + crop + "\"\n" +
				"            }\n" +
				"          }\n" +
				"        },\n" +
				"        {\n" +
				"          \"term\": {\n" +
				"            \"ODR\": {\n" +
				"              \"value\": \"" + odr + "\"\n" +
				"            }\n" +
				"          }\n" +
				"        },\n" +
				"        {\n" +
				"          \"range\": {\n" +
				"            \"EXMINYEAR.keyword\": {\n" +
				"              \"gte\": " + fromYear + ",\n" +
				"              \"lte\": " + toYear + "\n" +
				"            }\n" +
				"          }\n" +
				"        }\n" +
				"      ]\n" +
				"    }\n" +
				"  } \n" +
				"}\n" +
				"";
		
		//
		return elasticService.postSearch("ds_ctvt_inten_examin*", requestJson);
	}
	
	/**
	 * 기간별 평균 재배 면적
	 * @param crop
	 * @param fromYear
	 * @param toYear
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getAvgAreaByPeriod(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {
		String requestJson =
			"{\n" +
			"  \"aggs\": {\n" +
			"    \"aggs_result\": {\n" +
			"      \"avg\": {\n" +
			"        \"field\": \"AR\"\n" +
			"      }\n" +
			"    }\n" +
			"  },\n" +
			"  \"size\": 0, \n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"range\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";
		
		return elasticService.postSearch("ds_crops_outtrn*", requestJson);
	}
	
	/**
	 * 년도별 총 재배 면적
	 * @param crop
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public ElasticResultMap getAreaByYear(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {

		String requestJson =
			"{\n" +
			"  \"aggs\": {\n" +
			"    \"group_by_year\": {\n" +
			"      \"terms\": {\n" +
			"        \"field\": \"YEAR\"\n" +
			"      },\n" +
			"      \"aggs\": {\n" +
			"        \"aggs_result\": {\n" +
			"          \"sum\": {\n" +
			"            \"field\": \"AR\"\n" +
			"          }\n" +
			"        }\n" +
			"      }\n" +
			"    }\n" +
			"  },\n" +
			"  \"size\": 0, \n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"range\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";
		
		//
		return elasticService.postSearch("ds_crops_outtrn*", requestJson);
	}
	
	/**
	 * 년별 평균 생산량
	 * @param crop
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ElasticResultMap getAvgOuttrnByYear(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException {

		String requestJson =
				"{\n" +
				"    \"aggs\": {\n" +
				"    \"group_by_year\": {\n" +
				"      \"terms\": {\n" +
				"        \"field\": \"YEAR\"\n" +
				"      },\n" +
				"      \"aggs\": {\n" +
				"        \"aggs_result\": {\n" +
				"          \"avg\": {\n" +
				"            \"field\": \"OUTTURN\"\n" +
				"          }\n" +
				"        }\n" +
				"      }\n" +
				"    }\n" +
				"  },\n" +
				"  \"size\": 0, \n" +
				"  \"query\": {\n" +
				"    \"bool\": {\n" +
				"      \"must\": [\n" +
				"        {\n" +
				"          \"term\": {\n" +
				"            \"PRDLST_NM\": {\n" +
				"              \"value\": \"" + crop + "\"\n" +
				"            }\n" +
				"          }\n" +
				"        },\n" +
				"        {\n" +
				"          \"range\": {\n" +
				"            \"YEAR\": {\n" +
				"              \"gte\": " + fromYear + ",\n" +
				"              \"lte\": " + toYear + "\n" +
				"            }\n" +
				"          }\n" +
				"        }\n" +
				"      ]\n" +
				"    }\n" +
				"  }\n" +
				"}\n" +
				"";
		
		//
		return elasticService.postSearch("ds_crops_outtrn*", requestJson);
	}
	
	public ElasticResultMap getAvgOuttrn(String crop, String fromYear, String toYear) throws InstantiationException, IllegalAccessException, IOException{
		// 최근5년 평균생산량 총평균	
		String requestJson = 
			"{\n" +
			"  \"aggs\": {\n" +
			"    \"aggs_result\": {\n" +
			"      \"avg\": {\n" +
			"        \"field\": \"OUTTURN\"\n" +
			"      }\n" +
			"    }\n" +
			"  }, \n" +
			"  \"size\": 0, \n" +
			"  \"query\": {\n" +
			"    \"bool\": {\n" +
			"      \"must\": [\n" +
			"        {\n" +
			"          \"term\": {\n" +
			"            \"PRDLST_NM\": {\n" +
			"              \"value\": \"" + crop + "\"\n" +
			"            }\n" +
			"          }\n" +
			"        },\n" +
			"        {\n" +
			"          \"range\": {\n" +
			"            \"YEAR\": {\n" +
			"              \"gte\": " + fromYear + ",\n" +
			"              \"lte\": " + toYear + "\n" +
			"            }\n" +
			"          }\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}\n" +
			"";

		//
		return elasticService.postSearch("ds_crops_outtrn*", requestJson);
	}
	
	
	/* 테스트용 임시 아무거나 쿼리 */
	public int selectJejuWinterCount(HashMap<String, Object> param) {
		return 0;
	}
	
	/* 테스트용 임시 아무거나 쿼리 */
	public List<EntityMap> selectJejuWinterList(HashMap<String, Object> param){
		return null;
	}
	
	public VilageFcstInfoServiceMap vilageFcstInfoService() throws JSONException, InstantiationException, IllegalAccessException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		
		String url = EgovProperties.getProperty("api.fcst.url");
		String serviceKey = EgovProperties.getProperty("api.fcst.key");
		String numOfRows = "1000";		// 한번에 조회하도록 몽땅 가져오자
		String pageNo = "1";
		String dataType = "XML";
		String base_date = sdf.format(cal.getTime()); // 금일
		String base_time = "0200";		// hhmm - 02시 기준 예보로 통일한다.
		// 격자정보 제주특별자치도 52, 38 / 제주시 : 53, 38 / 서귀포시 52, 33
		String nx = EgovProperties.getProperty("api.fcst.jeju.nx");
		String ny = EgovProperties.getProperty("api.fcst.jeju.ny");
		
		url += "?serviceKey=" + serviceKey;
		url += "&numOfRows=" + numOfRows;
		url += "&pageNo=" + pageNo;
		url += "&dataType=" + dataType;
		url += "&base_date=" + base_date;
		url += "&base_time=" + base_time;
		url += "&nx=" + nx;
		url += "&ny=" + ny;
		
		Tuple<Integer, String> result = requestAndGetData(url);
		
		// success
		if((int) result.x == 200) {
			LOGGER.debug("API REQUEST SUCCESS >>>> {} / response code : {}", "기상청 동네예보 조회", result.y);
			JSONObject jobj = XML.toJSONObject(result.y);
			return new VilageFcstInfoServiceMap(jobj.toString());
		} else {	// write fail log to log table			
			LOGGER.debug("API REQUEST FAIL >>>> {} / response code : {}", "기상청 동네예보 조회", result.x);
			return null;
		}
	}
	
	/**
	 * api request and receive response
	 * @param url
	 * @return
	 */
	private Tuple<Integer, String> requestAndGetData(String url) {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		try {
			URL url2 = new URL(url);
			HttpURLConnection huc = null;
			huc = (HttpURLConnection) url2.openConnection();
			huc.setConnectTimeout(100000);
			huc.setReadTimeout(100000);
			huc.connect();

			if (huc.getResponseCode() == 200) {
				return new Tuple<Integer, String>(huc.getResponseCode(), IOUtils.toString(huc.getInputStream(), "UTF-8"));
			} else {
				LOGGER.info(huc.getResponseMessage());
				LOGGER.info(url);
				// return response code
				return new Tuple<Integer, String>(huc.getResponseCode(), huc.getResponseMessage());
			}

		} catch (MalformedURLException e) {
			LOGGER.error("{}", e);
			return new Tuple<Integer, String>(0, e.getMessage());
		} catch (IOException e) {
			LOGGER.error("{}", e);
			return new Tuple<Integer, String>(0, e.getMessage());
		}
	}
	
	/**
	 * Tuple
	 * @author jae0ha
	 *
	 * @param <X>
	 * @param <Y>
	 */
	public class Tuple<X, Y> { 
		public final X x; 
		public final Y y; 
		public Tuple(X x, Y y) { 
			this.x = x; 
			this.y = y; 
		}
	}

}
