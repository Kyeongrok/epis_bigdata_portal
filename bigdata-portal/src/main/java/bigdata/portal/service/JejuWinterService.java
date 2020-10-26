package bigdata.portal.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.util.VilageFcstInfoServiceMap;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.JejuWinterMapper;
import kr.co.ucsit.core.CsUtil;

/**
 * 제주 월동 작물
 * @author hyunseongkil
 *
 */
@Service
public class JejuWinterService {


	@Autowired private JejuWinterMapper jejuWinterMapper;

	/**
	 * map에서 buckets키를 찾아 그 object를 리턴
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getBuckets(Map<String,Object> map){
		Iterator<String> iter = map.keySet().iterator();

		//
		String k;
		while(iter.hasNext()) {
			k = iter.next();

			if("buckets".equals(k)) {
				return (List<Map<String, Object>>) map.get(k);
			}

			//
			if(map.get(k) instanceof Map) {
				return getBuckets((Map<String, Object>) map.get(k));
			}
		}

		//
		return null;
	}

	// TODO Get : 전년대비 상승가격
	public String getElevatedPrice() {
		String result = "";

		return result;
	}

	/* ******************************************************************
	 * 임시 ES 연동
	 *******************************************************************/



	// TODO Get : 최근 5년 평균 재배 면적 총평균
	public Map<String, Object> getFiveYearAvgArea(String crop) throws IOException, InstantiationException, IllegalAccessException {

		//
		ElasticResultMap resultMap = jejuWinterMapper.getAvgAreaTotal();
		return getValueMap(resultMap.getAggregations());
	}

	// 기간별 평균 재배 면적
	public Map<String, Object> getAvgAreaByPeriod(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {
		ElasticResultMap resultMap = jejuWinterMapper.getAvgAreaByPeriod(crop, fromYear, toYear);
		return getValueMap(resultMap.getAggregations());
	}

	// 최근 5년 년도별 총 재배 면적
	public Map<String, Object> getFiveYearEachArea(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {

		ElasticResultMap resultMap = jejuWinterMapper.getAreaByYear(crop, fromYear, toYear);
		return getValueMap(resultMap.getAggregations());
	}

	// TODO Get : 증감률
	public String getIncreaseRate() {
		String result = "";

		return result;
	}

	// 최근5년 평균생산량 총평균
	public Map<String, Object> getProdFiveYearAvg(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {

		//
		ElasticResultMap resultMap = jejuWinterMapper.getAvgOuttrn(crop, fromYear, toYear);
		return getValueMap(resultMap.getAggregations());
	}

	// TODO Get : 최근5년 평균생산량 대비 예상 생산량
	public String getProdFiveYearContrast() {
		String result = "";

		return result;
	}

	public Map<String, Object> getDansuByYearMon(String crop, String year, String month) throws IOException, InstantiationException, IllegalAccessException {

		ElasticResultMap resultMap = jejuWinterMapper.getDansuByYearMon(crop, year, month);
		return getValueMap(resultMap.getAggregations());
	}

	public Map<String, Object> getDansuByPeriodAvg(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {
		ElasticResultMap resultMap = jejuWinterMapper.getDansuByPeriodAvg(crop, fromYear, toYear);
		return getValueMap(resultMap.getAggregations());
	}

	public Map<String, Object> getPriceByDate(String crop, String year, String date) throws IOException, InstantiationException, IllegalAccessException {

		ElasticResultMap resultMap = jejuWinterMapper.getPriceByDate(crop, year, date);
		return getValueMap(resultMap.getAggregations());
	}

	// 최근5년 년별 평균 생산량
	// TODO : data가 없어서 2013년인지 모르겠지만 앞으론 현년도 부터 5년간으로 계산해야할듯
	public Map<String, Object> getProdFiveYearEachAvg(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {

		//
		ElasticResultMap resultMap = jejuWinterMapper.getAvgOuttrnByYear(crop, fromYear, toYear);
		return getValueMap(resultMap.getAggregations());
	}

	// TODO Get : 금년 예상 생산량
	public int getThisYearPredictProd() {
		int result = 0;

		return result;
	}


	/**
	 * 결과맵에서 해당 값 추출
	 * @param aggregationsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getValueMap(Map<String,Object> aggregationsMap) {
		Map<String,Object> map = new HashMap<String, Object>();

		//
		if(aggregationsMap.containsKey("aggs_result")) {
			Map<String,Object> d = (Map<String, Object>) aggregationsMap.get("aggs_result");
			map.put("value", d.get("value"));
			return map;
		}

		//
		List<Map<String,Object>> buckets = getBuckets(aggregationsMap);
		if(CsUtil.isEmpty(buckets)) {
			return map;
		}

		//
		String k="";
		for(Map<String,Object> d : buckets) {
			if(d.containsKey("key_as_string")) {
				k = "" + d.get("key_as_string");
			}else if(d.containsKey("key")) {
				k = "" + d.get("key");
			}else {
				throw new RuntimeException("");
			}

			//
			map.put(k, ((Map<String,Object>)d.get("aggs_result")).get("value"));
		}


		//
		return map;
	}

	// 도매시장 거래가격 기간별집계(일별, 주간별, 월별, 년별 등 - interval : 1d, 1w, 1M, 1y, etc)
	public Map<String, Object> getPrice(String crop, String fromDt, String toDt, String interval, String grade)
			throws IOException, InstantiationException, IllegalAccessException, ParseException {
		ElasticResultMap resultMap = jejuWinterMapper.getPrice(crop, fromDt, toDt, interval, grade);

		Map<String, Object> modifyKeyMap = getValueMap(resultMap.getAggregations());
		Map<String, Object> tempMap = new HashMap<String, Object>();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
		SimpleDateFormat weekFormat = new SimpleDateFormat("MM.W");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

//		System.out.println(modifyKeyMap);

		if(modifyKeyMap.isEmpty()) return modifyKeyMap;

		for(String key : modifyKeyMap.keySet()) {
			Date date = transFormat.parse(key);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if("1w".equals(interval)) {
				//String strKey = CsUtil.concat("", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, ".", cal.get(Calendar.WEEK_OF_MONTH) + 1);
				String strKey = weekFormat.format(cal.getTime());
				tempMap.put(strKey, modifyKeyMap.get(key));
			} else if("1M".equals(interval)) {
				//String strKey = CsUtil.concat("", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
				String strKey = monthFormat.format(cal.getTime());
				tempMap.put(strKey, modifyKeyMap.get(key));
			} else if("1y".equals(interval)) {
				String strKey = CsUtil.concat("", cal.get(Calendar.YEAR));
				tempMap.put(strKey, modifyKeyMap.get(key));
			} else {
				String strKey = dateFormat.format(cal.getTime());
				tempMap.put(strKey, modifyKeyMap.get(key));
			}
		}

		return tempMap;
	}

	// Get : 재배의향(1차) - 클릭해서 창 열때 1,2,3
	public Map<String, Object> getWillProd(String crop, String fromYear, String toYear, int odr) throws IOException, InstantiationException, IllegalAccessException {
		ElasticResultMap resultMap = jejuWinterMapper.getCtvtInten(crop, fromYear, toYear, odr);
		return getValueMap(resultMap.getAggregations());
	}

	// GET 확정재배신고
	public Map<String, Object> getCtvtSttemnt(String crop, String fromYear, String toYear) throws IOException, InstantiationException, IllegalAccessException {
		ElasticResultMap resultMap = jejuWinterMapper.getCtvtSttemnt(crop, fromYear, toYear);

		// TODO : 확정재배신고의 단위를 확인한 후 이데로 쓰거나 쿼리를 손보자... 쿼리가 좋은데 어렵네
		// 자리수 조정
		Map<String, Object> temp = getValueMap(resultMap.getAggregations());
		Set<String> keys = temp.keySet();

		for (String key : keys) {
		  temp.replace(key, (double) temp.get(key) * 0.0001);
		}
		return temp;
	}

	// 기상청 동네예보 api 요청
	@SuppressWarnings("unchecked")
	public Map<String, Object> vilageFcstInfoService() throws JSONException, InstantiationException, IllegalAccessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		VilageFcstInfoServiceMap result = jejuWinterMapper.vilageFcstInfoService();
		if(result == null || result.isEmpty()) {
			return null;
		}

		List<Map<String, Object>> items = result.getItems();
		if(items == null || items.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> temp = new ArrayList(items);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String toDay = sdf.format(cal.getTime());
		// 오늘 날짜가 아닌 데이터는 모두 지운다.
		for(Map<String, Object> map : items) {
			double val = ((double) map.get("fcstDate"));
			int val2 = (int) val;
			if(!toDay.equals(Integer.toString(val2))) {
				temp.remove(map);
			}
		}

		List<Map<String, Object>> temp2 = new ArrayList(temp);
		int hourNow = cal.get(Calendar.HOUR_OF_DAY);
		hourNow = hourNow * 100;
		int hourMin = 9999;
		for(Map<String, Object> map : temp) {
			// 몇몇데이터는 0200시에만 있으니 그냥 있으면 냅다 넣는다.
			if("TMN".equals(map.get("category"))) {
				resultMap.put(map.get("category").toString(), map.get("fcstValue"));
			} else if("TMX".equals(map.get("category"))) {
				resultMap.put(map.get("category").toString(), map.get("fcstValue"));
			} else if("R06".equals(map.get("category"))) {
				resultMap.put(map.get("category").toString(), map.get("fcstValue"));
			} else if("SKY".equals(map.get("category"))) {
				resultMap.put(map.get("category").toString(), map.get("fcstValue"));
			}

			double val = ((double) map.get("fcstTime"));
			int fcstHour = (int) val;
			// 현재시각 보다 뒤의 예보면 삭제
			if(hourNow < fcstHour) {
				temp2.remove(map);
			} else {
				if(hourMin > fcstHour) {
					hourMin = fcstHour;
				}
			}
		}

		// 오늘 가장 가까운 시간이 아닌 데이터는 모두 지운다.
		List<Map<String, Object>> temp3 = new ArrayList(temp2);
		for(Map<String, Object> map : temp2) {
			double val = ((double) map.get("fcstTime"));
			if(hourMin != (int) val) {
				temp3.remove(map);
			}
		}

		// 데이터를 맵에 넣는다.
		for(Map<String, Object> map : temp3) {
			// 풍향처리 (VEC)
			if("VEC".equals(map.get("category"))) {
				double val = (double) map.get("fcstValue");
				String vec = "";
				val = (val + 22.5 * 0.5) / 22.5;
				int intVec = (int) Math.floor(val);

				if(intVec == 0	) { vec = "북"; }
				else if(intVec == 1	) { vec = "북북동"; }
				else if(intVec == 2	) { vec = "북동"; }
				else if(intVec == 3	) { vec = "동북동"; }
				else if(intVec == 4	) { vec = "동"; }
				else if(intVec == 5	) { vec = "동남동"; }
				else if(intVec == 6	) { vec = "남동"; }
				else if(intVec == 7	) { vec = "남남동"; }
				else if(intVec == 8	) { vec = "남"; }
				else if(intVec == 9	) { vec = "남남서"; }
				else if(intVec == 10) { vec = "남서"; }
				else if(intVec == 11) { vec = "서남서"; }
				else if(intVec == 12) { vec = "서"; }
				else if(intVec == 13) { vec = "서북서"; }
				else if(intVec == 14) { vec = "북서"; }
				else if(intVec == 15) { vec = "북북서"; }
				else if(intVec == 16) { vec = "북"; }

				resultMap.put(map.get("category").toString(), vec);
			} else {
				resultMap.put(map.get("category").toString(), map.get("fcstValue"));
			}
		}

		return resultMap;
	}

	// fromYear에서 toYear 사이 없는 년도  값 처리
	public Map<String, Object> fillMissingKeyValue(Map<String, Object> inMap, Set<String> keys) {
		Map<String, Object> resultMap = inMap;
		double zero = 0.0;

		for(String key : keys) {
			if(!resultMap.containsKey(key)) {
				//resultMap.put(key, null);
				// json 변환에서 null은 제거되나... 일단 0
				resultMap.put(key, zero);
			} else if(resultMap.containsKey(key) && (resultMap.get(key) == null)) {
				resultMap.put(key, zero);
			}
		}
		return resultMap;
	}

	public int selectJejuWinterCount(HashMap<String, Object> param) {
		int count = jejuWinterMapper.selectJejuWinterCount(param);
		return count;
	}


	public List<EntityMap> selectJejuWinterList(HashMap<String, Object> param) {
		List<EntityMap> dataList = jejuWinterMapper.selectJejuWinterList(param);
		return dataList;
	}
}
