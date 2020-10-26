package bigdata.portal.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.service.JejuWinterService;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

@Controller
@RequestMapping("/bdp/svc/")
public class JejuWinterController extends BigdataController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JejuWinterController.class);
	@Autowired private JejuWinterService jejuWinterService;

	private static final String radish = "월동무";
	private static final String cabbage = "양배추";
	private static final String carrot = "당근";

	// 1d, 1w, 1M, 1y
	private static final String dateReq = "1d";
	private static final String weekReq = "1w";
	private static final String monthReq = "1M";
	private static final String yearReq = "1y";

	// 조회시점
	private static final String lastYearReq = "0";
	private static final String fiveYearReq = "1";
	private static final String commonYearReq = "2";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat commaSdf = new SimpleDateFormat("yyyy.MM.dd");

	private static final String isMobile = "MOBILE";
	private static final String isPhone = "PHONE";

	@RequestMapping(value="jejuDetail.do", method=RequestMethod.GET)
	public String jejuDetail(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, ParseException, JSONException {

		try {

			Calendar cal = Calendar.getInstance();
			int thisYear = cal.get(Calendar.YEAR);
			int thisMonth = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("thisYear", thisYear);
			model.addAttribute("thisMonth", thisMonth);

			// today
			model.addAttribute("today", commaSdf.format(cal.getTime()));

			int predictProdYear = thisYear;
			if(thisMonth <= 4) {
				predictProdYear = thisYear - 1;
			}
			model.addAttribute("predictProdYear", predictProdYear);

			// Get : 최근5년 평균생산량 총평균
			//List<Map<String,Object>> prodFiveYearAvgMapDatas = new ArrayList<>();
			// 0 : 월동무, 1 : 양배추, 2 : 당근

			// 5년 평균 생산량은 1~4월까진 전전년부터 5년간 평균
			int reqYear = 0;
			if(thisMonth < 5) {
				reqYear = thisYear - 2;
			} else {
				reqYear = thisYear - 1;
			}

			double prodFiveYearAvgRadish = (double) jejuWinterService.getProdFiveYearAvg(radish, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			double prodFiveYearAvgCabbage = (double) jejuWinterService.getProdFiveYearAvg(cabbage, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			double prodFiveYearAvgCarrot = (double) jejuWinterService.getProdFiveYearAvg(carrot, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			model.addAttribute("prodFiveYearAvgRadish", prodFiveYearAvgRadish);
			model.addAttribute("prodFiveYearAvgCabbage", prodFiveYearAvgCabbage);
			model.addAttribute("prodFiveYearAvgCarrot", prodFiveYearAvgCarrot);

			// Get : 금년 예상 생산량
			// 계산식 : 품목별총면적 * 0.001 * 단수 => 결과는 Kg일듯 Ton으로 변환해야 하는지 확인 필수
			Map<String, Object> predictProdNow = getPredictProdNow();
			double predictProdNowRadish = (double) predictProdNow.get(radish);
			double predictProdNowCabbage = (double) predictProdNow.get(cabbage);
			double predictProdNowCarrot = (double) predictProdNow.get(carrot);
			model.addAttribute("predictProdNowRadish", predictProdNowRadish);
			model.addAttribute("predictProdNowCabbage", predictProdNowCabbage);
			model.addAttribute("predictProdNowCarrot", predictProdNowCarrot);

			// Get : 최근5년 평균생산량 대비 예상 생산량(%)
			// 예상생산량 / 5년평균생산량 * 100
			if(CsUtil.isEmpty(prodFiveYearAvgRadish) || prodFiveYearAvgRadish == 0) {
				model.addAttribute("predicVsFiveYearProdRadish", 0);
			} else {
				double temp = predictProdNowRadish / prodFiveYearAvgRadish * 100;
				model.addAttribute("predicVsFiveYearProdRadish", temp);
			}
			if(CsUtil.isEmpty(prodFiveYearAvgCabbage ) || prodFiveYearAvgCabbage == 0) {
				model.addAttribute("predicVsFiveYearProdCabbage", 0);
			} else {
				double temp = predictProdNowCabbage / prodFiveYearAvgCabbage * 100;
				model.addAttribute("predicVsFiveYearProdCabbage", temp);
			}
			if(CsUtil.isEmpty(prodFiveYearAvgCarrot) || prodFiveYearAvgCarrot == 0) {
				model.addAttribute("predicVsFiveYearProdCarrot", 0);
			} else {
				double temp = predictProdNowCarrot / prodFiveYearAvgCarrot * 100;
				model.addAttribute("predicVsFiveYearProdCarrot", temp);
			}

			// 최근 5년 년도별 평균생산량
			List<Map<String,Object>> prodFiveYearEachAvgMapDatas = new ArrayList<>();
			// 0 : 월동무, 1 : 양배추, 2 : 당근
			Map<String, Object> radishMap = jejuWinterService.getProdFiveYearEachAvg(radish, Integer.toString(reqYear - 4), Integer.toString(reqYear));
			Map<String, Object> cabbageMap = jejuWinterService.getProdFiveYearEachAvg(cabbage, Integer.toString(reqYear - 4), Integer.toString(reqYear));
			Map<String, Object> carrotMap = jejuWinterService.getProdFiveYearEachAvg(carrot, Integer.toString(reqYear - 4), Integer.toString(reqYear));

			// 데이터 없을 경우 0처리
			Set<String> fiveYearProdKeySet = getPeriodKeySetProd(yearReq);
			radishMap = jejuWinterService.fillMissingKeyValue(radishMap, fiveYearProdKeySet);
			cabbageMap = jejuWinterService.fillMissingKeyValue(cabbageMap, fiveYearProdKeySet);
			carrotMap = jejuWinterService.fillMissingKeyValue(carrotMap, fiveYearProdKeySet);

			radishMap.put(Integer.toString(reqYear + 1), predictProdNowRadish);	// 예상생산량 추가
			cabbageMap.put(Integer.toString(reqYear + 1), predictProdNowCabbage);	// 예상생산량 추가
			carrotMap.put(Integer.toString(reqYear + 1), predictProdNowCarrot);	// 예상생산량 추가

			prodFiveYearEachAvgMapDatas.add(radishMap);
			prodFiveYearEachAvgMapDatas.add(carrotMap);
			prodFiveYearEachAvgMapDatas.add(cabbageMap);
			model.addAttribute("prodFiveYearEachAvgMapDatas", prodFiveYearEachAvgMapDatas);
			model.addAttribute("prodFiveYearEachAvgMapDatasJson", (new Gson().toJson(prodFiveYearEachAvgMapDatas)));

			// Get : 재배의향 - 클릭해서 창 열때 1,2,3,확정재배신고
			// 0 : 월동무, 1 : 양배추, 2 : 당근
			List<Map<String,Object>> willProdFirstMapDatas = new ArrayList<>();
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			model.addAttribute("willProdFirstMapDatas", willProdFirstMapDatas);
			model.addAttribute("willProdFirstMapDatasJson", (new Gson().toJson(willProdFirstMapDatas)));

			List<Map<String,Object>> willProdSecondMapDatas = new ArrayList<>();
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			model.addAttribute("willProdSecondMapDatas", willProdSecondMapDatas);
			model.addAttribute("willProdSecondMapDatasJson", (new Gson().toJson(willProdSecondMapDatas)));

			List<Map<String,Object>> willProdThirdMapDatas = new ArrayList<>();
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			model.addAttribute("willProdThirdMapDatas", willProdThirdMapDatas);
			model.addAttribute("willProdThirdMapDatasJson", (new Gson().toJson(willProdThirdMapDatas)));

			// 화면에 표시되는 재배의향
			Map<String, Object> prodWillDisplayMap = getDisplayProdWill();
			model.addAttribute("prodWillDisplayOrd", prodWillDisplayMap.get("prodWillDisplayOrd"));
			model.addAttribute("prodWillDisplayYearMonth", prodWillDisplayMap.get("yearMonth"));
			model.addAttribute("prodWillDisplayValRadish", prodWillDisplayMap.get(radish));
			model.addAttribute("prodWillDisplayValCabbage", prodWillDisplayMap.get(cabbage));
			model.addAttribute("prodWillDisplayValCarrot", prodWillDisplayMap.get(carrot));

			// Get : 최근 5년 평균 재배 면적
			double fiveYearAvgAreaRadish = (double) jejuWinterService.getAvgAreaByPeriod(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			double fiveYearAvgAreaCabbage = (double) jejuWinterService.getAvgAreaByPeriod(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			double fiveYearAvgAreaCarrot = (double) jejuWinterService.getAvgAreaByPeriod(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			model.addAttribute("fiveYearAvgAreaRadish", CsUtil.isEmpty(fiveYearAvgAreaRadish) ? 0 : fiveYearAvgAreaRadish);
			model.addAttribute("fiveYearAvgAreaCabbage", CsUtil.isEmpty(fiveYearAvgAreaCabbage) ? 0 : fiveYearAvgAreaCabbage);
			model.addAttribute("fiveYearAvgAreaCarrot", CsUtil.isEmpty(fiveYearAvgAreaCarrot) ? 0 : fiveYearAvgAreaCarrot);

			// 확정재배신고 그래프 (생산면적으로도 사용...)
			List<Map<String, Object>> confirmProdMapDatas = new ArrayList<>();
			Map<String, Object> radishAreaMap = jejuWinterService.getCtvtSttemnt(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear));
			Map<String, Object> cabbageAreaMap = jejuWinterService.getCtvtSttemnt(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear));
			Map<String, Object> carrotAreaMap = jejuWinterService.getCtvtSttemnt(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear));

			Set<String> fiveYearSet = getPeriodKeySet(yearReq, false);
			radishAreaMap = jejuWinterService.fillMissingKeyValue(radishAreaMap, fiveYearSet);
			cabbageAreaMap = jejuWinterService.fillMissingKeyValue(cabbageAreaMap, fiveYearSet);
			carrotAreaMap = jejuWinterService.fillMissingKeyValue(carrotAreaMap, fiveYearSet);

			// 2020년 예상 재배면적 - data 없는경우 일단 0......
			radishAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(radish) == null ? 0 : prodWillDisplayMap.get(radish));
			cabbageAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(cabbage) == null ? 0 : prodWillDisplayMap.get(cabbage));
			carrotAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(carrot) == null ? 0 : prodWillDisplayMap.get(carrot));

			confirmProdMapDatas.add(radishAreaMap);
			confirmProdMapDatas.add(cabbageAreaMap);
			confirmProdMapDatas.add(carrotAreaMap);
			model.addAttribute("confirmProdMapDatas", confirmProdMapDatas);
			model.addAttribute("confirmProdMapDatasJson", (new Gson().toJson(confirmProdMapDatas)));

			// 전일가격
			Map<String, Double> todayPriceMap = getLastPrice(0);

			// 작년전일가격
			Map<String, Double> lastYearPriceMap = getLastPrice(-1);

			Double radishTodayUpDownPrice = null;
			Double cabbageTodayUpDownPrice = null;
			Double carrotTodayUpDownPrice = null;

			Double radishTodayUpDownPercent = null;
			Double cabbageTodayUpDownPercent = null;
			Double carrotTodayUpDownPercent = null;

			if(todayPriceMap.get(radish) != null && lastYearPriceMap.get(radish) != null && todayPriceMap.get(radish) != 0.0) {
				radishTodayUpDownPrice = todayPriceMap.get(radish) - lastYearPriceMap.get(radish);
				radishTodayUpDownPercent = (todayPriceMap.get(radish) - lastYearPriceMap.get(radish)) / lastYearPriceMap.get(radish) * 100;
			}

			if(todayPriceMap.get(cabbage) != null && lastYearPriceMap.get(cabbage) != null && todayPriceMap.get(cabbage) != 0.0) {
				cabbageTodayUpDownPrice = todayPriceMap.get(cabbage) - lastYearPriceMap.get(cabbage);
				cabbageTodayUpDownPercent = (todayPriceMap.get(cabbage) - lastYearPriceMap.get(cabbage)) / lastYearPriceMap.get(cabbage) * 100;
			}

			if(todayPriceMap.get(carrot) != null && lastYearPriceMap.get(carrot) != null && todayPriceMap.get(carrot) != 0.0) {
				carrotTodayUpDownPrice = todayPriceMap.get(carrot) - lastYearPriceMap.get(carrot);
				carrotTodayUpDownPercent = (todayPriceMap.get(carrot) - lastYearPriceMap.get(carrot)) / lastYearPriceMap.get(carrot) * 100;
			}

			model.addAttribute("radishTodayPrice", todayPriceMap.get(radish));
		    model.addAttribute("cabbageTodayPrice", todayPriceMap.get(cabbage));
		    model.addAttribute("carrotTodayPrice", todayPriceMap.get(carrot));

			model.addAttribute("radishTodayUpDownPrice", radishTodayUpDownPrice);
	        model.addAttribute("radishTodayUpDownPercent", radishTodayUpDownPercent);
	        model.addAttribute("radishLastYearPrice", lastYearPriceMap.get(radish));

	        model.addAttribute("cabbageTodayUpDownPrice", cabbageTodayUpDownPrice);
	        model.addAttribute("cabbageTodayUpDownPercent", cabbageTodayUpDownPercent);
	        model.addAttribute("cabbageLastYearPrice", lastYearPriceMap.get(cabbage));

	        model.addAttribute("carrotTodayUpDownPrice", carrotTodayUpDownPrice);
	        model.addAttribute("carrotTodayUpDownPercent", carrotTodayUpDownPercent);
	        model.addAttribute("carrotLastYearPrice", lastYearPriceMap.get(carrot));

	        // 기상청 동네예보
			Map<String, Object> vilageFcstInfoMap = jejuWinterService.vilageFcstInfoService();
			if(vilageFcstInfoMap != null && !vilageFcstInfoMap.isEmpty()) {
				model.addAttribute("POP", vilageFcstInfoMap.get("POP"));		// 강수확률
				model.addAttribute("TMN", vilageFcstInfoMap.get("TMN"));		// 최저기온
				model.addAttribute("TMX", vilageFcstInfoMap.get("TMX"));		// 최고기온
				model.addAttribute("VEC", vilageFcstInfoMap.get("VEC"));		// 풍향
				model.addAttribute("WSD", vilageFcstInfoMap.get("WSD"));		// 풍속
				model.addAttribute("R06", vilageFcstInfoMap.get("R06"));		// 강수량
				model.addAttribute("REH", vilageFcstInfoMap.get("REH"));		// 습도
				model.addAttribute("SKY", vilageFcstInfoMap.get("SKY"));		// 0 - 5 : 맑음 , 6 ~ 8 : 구름많음, 9 ~ 10 흐림
			}

		} catch (IOException e) {
			LOGGER.error("{}",e);
		}

		return P_BIGDATA_PORTAL + "svc/jejuDetail";
	}

	@RequestMapping(value="mJejuDetail.do", method=RequestMethod.GET)
	public String mJejuDetail(Model model) throws InstantiationException, IllegalAccessException, ParseException, JSONException {

		try {

			Calendar cal = Calendar.getInstance();
			int thisYear = cal.get(Calendar.YEAR);
			int thisMonth = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("thisYear", thisYear);
			model.addAttribute("thisMonth", thisMonth);

			// today
			model.addAttribute("today", commaSdf.format(cal.getTime()));

			int predictProdYear = thisYear;
			if(thisMonth <= 4) {
				predictProdYear = thisYear - 1;
			}
			model.addAttribute("predictProdYear", predictProdYear);

			// Get : 최근5년 평균생산량 총평균
			//List<Map<String,Object>> prodFiveYearAvgMapDatas = new ArrayList<>();
			// 0 : 월동무, 1 : 양배추, 2 : 당근

			// 5년 평균 생산량은 1~4월까진 전전년부터 5년간 평균
			int reqYear = 0;
			if(thisMonth < 5) {
				reqYear = thisYear - 2;
			} else {
				reqYear = thisYear - 1;
			}

			double prodFiveYearAvgRadish = (double) jejuWinterService.getProdFiveYearAvg(radish, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			double prodFiveYearAvgCabbage = (double) jejuWinterService.getProdFiveYearAvg(cabbage, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			double prodFiveYearAvgCarrot = (double) jejuWinterService.getProdFiveYearAvg(carrot, Integer.toString(reqYear - 4), Integer.toString(reqYear)).get("value");
			model.addAttribute("prodFiveYearAvgRadish", prodFiveYearAvgRadish);
			model.addAttribute("prodFiveYearAvgCabbage", prodFiveYearAvgCabbage);
			model.addAttribute("prodFiveYearAvgCarrot", prodFiveYearAvgCarrot);

			// Get : 금년 예상 생산량
			// 계산식 : 품목별총면적 * 0.001 * 단수 => 결과는 Kg일듯 Ton으로 변환해야 하는지 확인 필수
			Map<String, Object> predictProdNow = getPredictProdNow();
			double predictProdNowRadish = (double) predictProdNow.get(radish);
			double predictProdNowCabbage = (double) predictProdNow.get(cabbage);
			double predictProdNowCarrot = (double) predictProdNow.get(carrot);
			model.addAttribute("predictProdNowRadish", predictProdNowRadish);
			model.addAttribute("predictProdNowCabbage", predictProdNowCabbage);
			model.addAttribute("predictProdNowCarrot", predictProdNowCarrot);

			// Get : 최근5년 평균생산량 대비 예상 생산량(%)
			// 예상생산량 / 5년평균생산량 * 100
			if(CsUtil.isEmpty(prodFiveYearAvgRadish) || prodFiveYearAvgRadish == 0) {
				model.addAttribute("predicVsFiveYearProdRadish", 0);
			} else {
				double temp = predictProdNowRadish / prodFiveYearAvgRadish * 100;
				model.addAttribute("predicVsFiveYearProdRadish", temp);
			}
			if(CsUtil.isEmpty(prodFiveYearAvgCabbage ) || prodFiveYearAvgCabbage == 0) {
				model.addAttribute("predicVsFiveYearProdCabbage", 0);
			} else {
				double temp = predictProdNowCabbage / prodFiveYearAvgCabbage * 100;
				model.addAttribute("predicVsFiveYearProdCabbage", temp);
			}
			if(CsUtil.isEmpty(prodFiveYearAvgCarrot) || prodFiveYearAvgCarrot == 0) {
				model.addAttribute("predicVsFiveYearProdCarrot", 0);
			} else {
				double temp = predictProdNowCarrot / prodFiveYearAvgCarrot * 100;
				model.addAttribute("predicVsFiveYearProdCarrot", temp);
			}

			// 최근 5년 년도별 평균생산량
			List<Map<String,Object>> prodFiveYearEachAvgMapDatas = new ArrayList<>();
			// 0 : 월동무, 1 : 양배추, 2 : 당근
			Map<String, Object> radishMap = jejuWinterService.getProdFiveYearEachAvg(radish, Integer.toString(reqYear - 4), Integer.toString(reqYear));
			Map<String, Object> cabbageMap = jejuWinterService.getProdFiveYearEachAvg(cabbage, Integer.toString(reqYear - 4), Integer.toString(reqYear));
			Map<String, Object> carrotMap = jejuWinterService.getProdFiveYearEachAvg(carrot, Integer.toString(reqYear - 4), Integer.toString(reqYear));

			// 데이터 없을 경우 0처리
			Set<String> fiveYearProdKeySet = getPeriodKeySetProd(yearReq);
			radishMap = jejuWinterService.fillMissingKeyValue(radishMap, fiveYearProdKeySet);
			cabbageMap = jejuWinterService.fillMissingKeyValue(cabbageMap, fiveYearProdKeySet);
			carrotMap = jejuWinterService.fillMissingKeyValue(carrotMap, fiveYearProdKeySet);

			radishMap.put(Integer.toString(reqYear + 1), predictProdNowRadish);	// 예상생산량 추가
			cabbageMap.put(Integer.toString(reqYear + 1), predictProdNowCabbage);	// 예상생산량 추가
			carrotMap.put(Integer.toString(reqYear + 1), predictProdNowCarrot);	// 예상생산량 추가

			prodFiveYearEachAvgMapDatas.add(radishMap);
			prodFiveYearEachAvgMapDatas.add(carrotMap);
			prodFiveYearEachAvgMapDatas.add(cabbageMap);
			model.addAttribute("prodFiveYearEachAvgMapDatas", prodFiveYearEachAvgMapDatas);
			model.addAttribute("prodFiveYearEachAvgMapDatasJson", (new Gson().toJson(prodFiveYearEachAvgMapDatas)));

			// Get : 재배의향 - 클릭해서 창 열때 1,2,3,확정재배신고
			// 0 : 월동무, 1 : 양배추, 2 : 당근
			List<Map<String,Object>> willProdFirstMapDatas = new ArrayList<>();
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			willProdFirstMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 1));
			model.addAttribute("willProdFirstMapDatas", willProdFirstMapDatas);
			model.addAttribute("willProdFirstMapDatasJson", (new Gson().toJson(willProdFirstMapDatas)));

			List<Map<String,Object>> willProdSecondMapDatas = new ArrayList<>();
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			willProdSecondMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 2));
			model.addAttribute("willProdSecondMapDatas", willProdSecondMapDatas);
			model.addAttribute("willProdSecondMapDatasJson", (new Gson().toJson(willProdSecondMapDatas)));

			List<Map<String,Object>> willProdThirdMapDatas = new ArrayList<>();
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			willProdThirdMapDatas.add(jejuWinterService.getWillProd(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear), 3));
			model.addAttribute("willProdThirdMapDatas", willProdThirdMapDatas);
			model.addAttribute("willProdThirdMapDatasJson", (new Gson().toJson(willProdThirdMapDatas)));

			// 화면에 표시되는 재배의향
			Map<String, Object> prodWillDisplayMap = getDisplayProdWill();
			model.addAttribute("prodWillDisplayOrd", prodWillDisplayMap.get("prodWillDisplayOrd"));
			model.addAttribute("prodWillDisplayYearMonth", prodWillDisplayMap.get("yearMonth"));
			model.addAttribute("prodWillDisplayValRadish", prodWillDisplayMap.get(radish));
			model.addAttribute("prodWillDisplayValCabbage", prodWillDisplayMap.get(cabbage));
			model.addAttribute("prodWillDisplayValCarrot", prodWillDisplayMap.get(carrot));

			// Get : 최근 5년 평균 재배 면적
			double fiveYearAvgAreaRadish = (double) jejuWinterService.getAvgAreaByPeriod(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			double fiveYearAvgAreaCabbage = (double) jejuWinterService.getAvgAreaByPeriod(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			double fiveYearAvgAreaCarrot = (double) jejuWinterService.getAvgAreaByPeriod(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear)).get("value");
			model.addAttribute("fiveYearAvgAreaRadish", CsUtil.isEmpty(fiveYearAvgAreaRadish) ? 0 : fiveYearAvgAreaRadish);
			model.addAttribute("fiveYearAvgAreaCabbage", CsUtil.isEmpty(fiveYearAvgAreaCabbage) ? 0 : fiveYearAvgAreaCabbage);
			model.addAttribute("fiveYearAvgAreaCarrot", CsUtil.isEmpty(fiveYearAvgAreaCarrot) ? 0 : fiveYearAvgAreaCarrot);

			// 확정재배신고 그래프 (생산면적으로도 사용...)
			List<Map<String, Object>> confirmProdMapDatas = new ArrayList<>();
			Map<String, Object> radishAreaMap = jejuWinterService.getCtvtSttemnt(radish, Integer.toString(thisYear - 5), Integer.toString(thisYear));
			Map<String, Object> cabbageAreaMap = jejuWinterService.getCtvtSttemnt(cabbage, Integer.toString(thisYear - 5), Integer.toString(thisYear));
			Map<String, Object> carrotAreaMap = jejuWinterService.getCtvtSttemnt(carrot, Integer.toString(thisYear - 5), Integer.toString(thisYear));

			Set<String> fiveYearSet = getPeriodKeySet(yearReq, false);
			radishAreaMap = jejuWinterService.fillMissingKeyValue(radishAreaMap, fiveYearSet);
			cabbageAreaMap = jejuWinterService.fillMissingKeyValue(cabbageAreaMap, fiveYearSet);
			carrotAreaMap = jejuWinterService.fillMissingKeyValue(carrotAreaMap, fiveYearSet);

			// 2020년 예상 재배면적 - data 없는경우 일단 0......
			radishAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(radish) == null ? 0 : prodWillDisplayMap.get(radish));
			cabbageAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(cabbage) == null ? 0 : prodWillDisplayMap.get(cabbage));
			carrotAreaMap.put(Integer.toString(thisYear), prodWillDisplayMap.get(carrot) == null ? 0 : prodWillDisplayMap.get(carrot));

			confirmProdMapDatas.add(radishAreaMap);
			confirmProdMapDatas.add(cabbageAreaMap);
			confirmProdMapDatas.add(carrotAreaMap);
			model.addAttribute("confirmProdMapDatas", confirmProdMapDatas);
			model.addAttribute("confirmProdMapDatasJson", (new Gson().toJson(confirmProdMapDatas)));

			// 전일가격
			Map<String, Double> todayPriceMap = getLastPrice(0);

			// 작년전일가격
			Map<String, Double> lastYearPriceMap = getLastPrice(-1);

			Double radishTodayUpDownPrice = null;
			Double cabbageTodayUpDownPrice = null;
			Double carrotTodayUpDownPrice = null;

			Double radishTodayUpDownPercent = null;
			Double cabbageTodayUpDownPercent = null;
			Double carrotTodayUpDownPercent = null;

			if(todayPriceMap.get(radish) != null && lastYearPriceMap.get(radish) != null && todayPriceMap.get(radish) != 0.0) {
				radishTodayUpDownPrice = todayPriceMap.get(radish) - lastYearPriceMap.get(radish);
				radishTodayUpDownPercent = (todayPriceMap.get(radish) - lastYearPriceMap.get(radish)) / lastYearPriceMap.get(radish) * 100;
			}

			if(todayPriceMap.get(cabbage) != null && lastYearPriceMap.get(cabbage) != null && todayPriceMap.get(cabbage) != 0.0) {
				cabbageTodayUpDownPrice = todayPriceMap.get(cabbage) - lastYearPriceMap.get(cabbage);
				cabbageTodayUpDownPercent = (todayPriceMap.get(cabbage) - lastYearPriceMap.get(cabbage)) / lastYearPriceMap.get(cabbage) * 100;
			}

			if(todayPriceMap.get(carrot) != null && lastYearPriceMap.get(carrot) != null && todayPriceMap.get(carrot) != 0.0) {
				carrotTodayUpDownPrice = todayPriceMap.get(carrot) - lastYearPriceMap.get(carrot);
				carrotTodayUpDownPercent = (todayPriceMap.get(carrot) - lastYearPriceMap.get(carrot)) / lastYearPriceMap.get(carrot) * 100;
			}

			model.addAttribute("radishTodayPrice", todayPriceMap.get(radish));
		    model.addAttribute("cabbageTodayPrice", todayPriceMap.get(cabbage));
		    model.addAttribute("carrotTodayPrice", todayPriceMap.get(carrot));

			model.addAttribute("radishTodayUpDownPrice", radishTodayUpDownPrice);
	        model.addAttribute("radishTodayUpDownPercent", radishTodayUpDownPercent);
	        model.addAttribute("radishLastYearPrice", lastYearPriceMap.get(radish));

	        model.addAttribute("cabbageTodayUpDownPrice", cabbageTodayUpDownPrice);
	        model.addAttribute("cabbageTodayUpDownPercent", cabbageTodayUpDownPercent);
	        model.addAttribute("cabbageLastYearPrice", lastYearPriceMap.get(cabbage));

	        model.addAttribute("carrotTodayUpDownPrice", carrotTodayUpDownPrice);
	        model.addAttribute("carrotTodayUpDownPercent", carrotTodayUpDownPercent);
	        model.addAttribute("carrotLastYearPrice", lastYearPriceMap.get(carrot));

		     // 기상청 동네예보
			Map<String, Object> vilageFcstInfoMap = jejuWinterService.vilageFcstInfoService();
			if(vilageFcstInfoMap != null && !vilageFcstInfoMap.isEmpty()) {
				model.addAttribute("POP", vilageFcstInfoMap.get("POP"));		// 강수확률
				model.addAttribute("TMN", vilageFcstInfoMap.get("TMN"));		// 최저기온
				model.addAttribute("TMX", vilageFcstInfoMap.get("TMX"));		// 최고기온
				model.addAttribute("VEC", vilageFcstInfoMap.get("VEC"));		// 풍향
				model.addAttribute("WSD", vilageFcstInfoMap.get("WSD"));		// 풍속
				model.addAttribute("R06", vilageFcstInfoMap.get("R06"));		// 강수량
				model.addAttribute("REH", vilageFcstInfoMap.get("REH"));		// 습도
				model.addAttribute("SKY", vilageFcstInfoMap.get("SKY"));		// 0 - 5 : 맑음 , 6 ~ 8 : 구름많음, 9 ~ 10 흐림
			}

		} catch (IOException e) {
			LOGGER.error("{}",e);
		}

		return P_BIGDATA_PORTAL + "svc/mJejuDetail";
	}

	/**
	 * 도매시장 거래가격 탭
	 * json형식의 데이터 리턴받기
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="getPriceByGrade.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getPriceByGrade(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, ParseException, IOException {

		CsTransferObject trans = new CsTransferObject();

		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		LOGGER.debug("{}",paramMap);

		// get data from es
		String crop = paramMap.get("crop").toString();				// 월동무, 양배추, 당근
		String interval = paramMap.get("interval").toString();		// 조회 group by : 1d, 1w, 1M, 1y (일별, 주별, 월별, 년별)
		String viewpoint = paramMap.get("viewpoint").toString();	// 조회시점 0 : 전년, 1 : 최근5년간의평균, 2 : 평년
		String grade = paramMap.get("grade").toString();			// 등급 특, 상, 보통

		Calendar fromCal = Calendar.getInstance();
	    Calendar toCal = Calendar.getInstance();

	    int thisYear = toCal.get(Calendar.YEAR);

	    // 전날기준
	    toCal.add(Calendar.DATE, -1);
		if(interval.equals(dateReq)) {				// 14일간
			fromCal.add(Calendar.DATE, -14);
		} else if(interval.equals(weekReq)) {		// 12주간
			fromCal.add(Calendar.DATE, -7 * 12);
		} else if(interval.equals(monthReq)) {		// 12개월간
			fromCal.add(Calendar.YEAR, -1);
		} else if(interval.equals(yearReq)) {		// 5년간
			fromCal.add(Calendar.YEAR, -4);
		}

	    String toDt = sdf.format(toCal.getTime());
	    String fromDt = sdf.format(fromCal.getTime());

	    Set<String> keySet = getPeriodKeySet(interval, true);
	    Set<String> yearKeySet = getPeriodKeySetWithYear(interval);

		// S : 막대그래프 표시데이터
		Map<String, Object> barDataMap = new HashMap<String, Object>();
		barDataMap = jejuWinterService.getPrice(crop, fromDt, toDt, interval, grade);
		barDataMap = jejuWinterService.fillMissingKeyValue(barDataMap, keySet);
		// E : 막대그래프 표시데이터


		// S : 선그래프 표시데이터
		Map<String, Object> lineDataMap = new HashMap<String, Object>();

		if(viewpoint.equals(lastYearReq)) {				// 전년
			fromCal.add(Calendar.YEAR, -1);
			toCal.add(Calendar.YEAR, -1);
		    toDt = sdf.format(toCal.getTime());
		    fromDt = sdf.format(fromCal.getTime());

		    lineDataMap = jejuWinterService.getPrice(crop, fromDt, toDt, interval, grade);
		} else if(viewpoint.equals(fiveYearReq) || viewpoint.equals(commonYearReq)) {		// 최근5년
			fromCal = Calendar.getInstance();
		    toCal = Calendar.getInstance();
		    fromCal.add(Calendar.YEAR, -4);
			// 5년간의 기간동안의 평균값
			Map<String, Object> temp = new HashMap<String, Object>();
			Map<String, List<Object>> valListMap = new HashMap<String, List<Object>>();

			for(int i = thisYear - 4; i <= thisYear; i++) {
				fromCal.add(Calendar.YEAR, -1);
				toCal.add(Calendar.YEAR, -1);
				toDt = sdf.format(toCal.getTime());
			    fromDt = sdf.format(fromCal.getTime());
				temp = jejuWinterService.getPrice(crop, fromDt, toDt, interval, grade);

				Iterator<String> iter = temp.keySet().iterator();
				String key;
				while(iter.hasNext()) {
					key = iter.next();
					if(valListMap.containsKey(key)) {
						List<Object> valList = valListMap.get(key);
						valList.add(temp.get(key));
					} else {
						List<Object> valList = new ArrayList<Object>();
						valList.add(temp.get(key));
						valListMap.put(key, valList);
					}
				}
			}

			if(viewpoint.equals(fiveYearReq)) {
				lineDataMap = getAvgFromListMap(valListMap);
			} else if(viewpoint.equals(commonYearReq)) {				// 최소 최대값 제거한 평균값
				lineDataMap = getAvgFromListMapRemoveMinMax(valListMap);
			}
		}

		lineDataMap = jejuWinterService.fillMissingKeyValue(lineDataMap, keySet);
		// E : 선그래프 표시데이터

		trans.setResultMessage("success");

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

		// 정렬을 위해 일, 주, 월 데이터에 년도 정보를 다시 붙인다...
		if(!interval.equals(yearReq)) {
			Map<String, Object> barDataMapNew = new HashMap<String, Object>();
			for(String yearKey : yearKeySet) {
				barDataMapNew.put(yearKey, barDataMap.get(yearKey.substring(5)));
			}

			Map<String, Object> lineDataMapNew = new HashMap<String, Object>();
			for(String yearKey : yearKeySet) {
				lineDataMapNew.put(yearKey, lineDataMap.get(yearKey.substring(5)));
			}

			datas.add(barDataMapNew);
			datas.add(lineDataMapNew);
		} else {
			datas.add(barDataMap);
			datas.add(lineDataMap);
		}

		trans.putDatas(datas);

		//
		model.addAllAttributes(trans);
		return P_BIGDATA_PORTAL + "svc/jejuDetail";
	}

	/**
	 * 최대 7일 이내에 존재하는 도매시장 가격을 가져온다.
	 * @param yearVal
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	private Map<String, Double> getLastPrice(int yearVal) throws InstantiationException, IllegalAccessException, IOException {
		Map<String, Double> result = new HashMap<String, Double>();

		// 최대 7일까지만 탐색한다.
		final int maxReqCount = 7;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, yearVal);
		cal.add(Calendar.DATE, -1);		// 전일부터 탐색
		// 금년, 작년값 처리
		Double radishTodayPrice = null;
		Double cabbageTodayPrice = null;
		Double carrotTodayPrice = null;

		int reqCount = 0;
		while(radishTodayPrice == null && reqCount < maxReqCount) {
			cal.add(Calendar.DATE, -1);
			radishTodayPrice = (Double) jejuWinterService.getPriceByDate(radish, sdfYear.format(cal.getTime()), sdf.format(cal.getTime())).get("value");
			reqCount++;
		}

		// calendar reset
		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, yearVal);
		cal.add(Calendar.DATE, -1);		// 전일부터 탐색
		reqCount = 0;
		while(cabbageTodayPrice == null && reqCount < maxReqCount) {
			cal.add(Calendar.DATE, -1);
			cabbageTodayPrice = (Double) jejuWinterService.getPriceByDate(cabbage, sdfYear.format(cal.getTime()), sdf.format(cal.getTime())).get("value");
			reqCount++;
		}

		// calendar reset
		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, yearVal);
		cal.add(Calendar.DATE, -1);		// 전일부터 탐색
		reqCount = 0;
		while(carrotTodayPrice == null && reqCount < maxReqCount) {
			cal.add(Calendar.DATE, -1);
			carrotTodayPrice = (Double) jejuWinterService.getPriceByDate(carrot, sdfYear.format(cal.getTime()), sdf.format(cal.getTime())).get("value");
			reqCount++;
		}

		result.put(radish, radishTodayPrice);
		result.put(cabbage, cabbageTodayPrice);
		result.put(carrot, carrotTodayPrice);

		return result;
	}

	private Set<String> getPeriodKeySetProd(String req) {
		// this year, this month
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
		SimpleDateFormat weekFormat = new SimpleDateFormat("MM.W");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

		Calendar cal = Calendar.getInstance();
		int thisMonth = cal.get(Calendar.MONTH) + 1;

		if(thisMonth <= 4) {
			cal.add(Calendar.YEAR, -1);
		}
		int thisYear = cal.get(Calendar.YEAR);

		Set<String> keySet = new HashSet<String>();

		if(req.equals(dateReq)) {
			// 빈값 표시하기 위한 일별 키셋
			for(int i = 0; i < 14; i++) {
				keySet.add(dateFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -1);
			}
		} else if(req.equals(weekReq)) {
			// 빈값 표시하기 위한 주별 키셋
			for(int i = 0; i < 12; i++) {
				keySet.add(weekFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
			}
		} else if(req.equals(monthReq)) {
			for(int i = 0; i < 12; i++) {
				keySet.add(monthFormat.format(cal.getTime()));
				cal.add(Calendar.MONTH, -1);
			}
		} else if(req.equals(yearReq)) {
			for(int i = thisYear - 5; i < thisYear; i++) {
				keySet.add(Integer.toString(i));
			}
		}
		return keySet;
	}

	private Set<String> getPeriodKeySet(String req, boolean isPrice) {
		// this year, this month
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
		SimpleDateFormat weekFormat = new SimpleDateFormat("MM.W");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		Set<String> keySet = new HashSet<String>();

		if(req.equals(dateReq)) {
			// 빈값 표시하기 위한 일별 키셋
			for(int i = 0; i < 14; i++) {
				keySet.add(dateFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -1);
			}
		} else if(req.equals(weekReq)) {
			// 빈값 표시하기 위한 주별 키셋
			for(int i = 0; i < 12; i++) {
				keySet.add(weekFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
			}
		} else if(req.equals(monthReq)) {
			for(int i = 0; i < 12; i++) {
				keySet.add(monthFormat.format(cal.getTime()));
				cal.add(Calendar.MONTH, -1);
			}
		} else if(req.equals(yearReq)) {
			int yearInterval = 0;
			if(isPrice) {
				yearInterval = 4;
			} else {
				yearInterval = 5;
			}
			for(int i = thisYear - yearInterval; i < thisYear; i++) {
				keySet.add(Integer.toString(i));
			}
		}
		return keySet;
	}

	private Set<String> getPeriodKeySetWithYear(String req) {
		// this year, this month
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat weekFormat = new SimpleDateFormat("yyyy.MM.W");
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy.MM");

		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		Set<String> keySet = new HashSet<String>();

		if(req.equals(dateReq)) {
			// 빈값 표시하기 위한 일별 키셋
			for(int i = 0; i < 14; i++) {
				keySet.add(dateFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -1);
			}
		} else if(req.equals(weekReq)) {
			// 빈값 표시하기 위한 주별 키셋
			for(int i = 0; i < 12; i++) {
				keySet.add(weekFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -7);
			}
		} else if(req.equals(monthReq)) {
			for(int i = 0; i < 12; i++) {
				keySet.add(monthFormat.format(cal.getTime()));
				cal.add(Calendar.MONTH, -1);
			}
		} else if(req.equals(yearReq)) {
			for(int i = thisYear - 4; i < thisYear; i++) {
				keySet.add(Integer.toString(i));
			}
		}
		return keySet;
	}

	// List 평균값 구하기
	private Map<String, Object> getAvgFromListMap(Map<String, List<Object>> valListMap) {

		Map<String, Object> result = new HashMap<String, Object>();

		Iterator<String> iter = valListMap.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = iter.next();
			List<Object> valList = valListMap.get(key);
			valList.removeAll(Collections.singletonList(null));
			if(valList.isEmpty()) {
				result.put(key, null);
			} else {
				double val = 0.0;
				for(int i = 0; i < valList.size(); i++) {
					val = val + (double) valList.get(i);
				}
				if(Double.isNaN(val)) {
					result.put(key, null);
				} else {
					val = val / valList.size();
					result.put(key, val);
				}
			}
		}

		return result;
	}

	// List 평균값 min, max 제외한 값 구하기
	private Map<String, Object> getAvgFromListMapRemoveMinMax(Map<String, List<Object>> valListMap) {

		Map<String, Object> result = new HashMap<String, Object>();

		Iterator<String> iter = valListMap.keySet().iterator();
		String key;
		while(iter.hasNext()) {
			key = iter.next();
			List<Object> valList = valListMap.get(key);
			valList.removeAll(Collections.singletonList(null));

			List<Double> dList = new ArrayList<Double>();
			for(Object obj : valList) {
				dList.add((double) obj);
			}

			if(dList.isEmpty()) {
				result.put(key, null);
			} else if(dList.size() == 1) {
				result.put(key, dList.get(0));
			} else if(dList.size() == 2) {
				double val = 0.0;
				for(int i = 0; i < dList.size(); i++) {
					val = val + dList.get(i);
				}
				if(Double.isNaN(val)) {
					result.put(key, null);
				} else {
					val = val / dList.size();
					result.put(key, val);
				}
			} else {
				double min = Collections.min(dList);
				double max = Collections.max(dList);
				dList.remove(min);
				dList.remove(max);
				double val = 0.0;
				for(int i = 0; i < dList.size(); i++) {
					val = val + dList.get(i);
				}

				if(Double.isNaN(val)) {
					result.put(key, null);
				} else {
					val = val / dList.size();
					result.put(key, val);
				}
			}
		}

		return result;
	}

	/**
	 * 예상생산량 계산
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Map<String, Object> getPredictProdNow() throws InstantiationException, IllegalAccessException {
        // 1,2,3,4월         -> 전년도 생산량 (년산표시는 전년도)
		// 5,6,7월           -> 금년 1차 재배의향면적 * 0.001 * 단수(전년도까지의 3개년 이동평균 단수)
		// 8월               -> 금년 1차 재배의향면적 * 0.001 * 단수
        // 9월               -> 금년 2차 재배의향면적 * 0.001 * 단수
		// 10월              -> 금년 3차 재배의향면적 * 0.001 * 단수
		// 11,12월           -> 금년 확정 재배의향면적 * 0,001 * 단수

		Map<String, Object> result = new HashMap<String, Object>();

		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		int thisMonth = cal.get(Calendar.MONTH) + 1;
		Object predicProdNowRadish = new Object();
		Object predicProdNowCabbage = new Object();
		Object predicProdNowCarrot = new Object();

		Object radishDansu = new Object();
		Object cabbageDansu = new Object();
		Object carrotDansu = new Object();

		try {

			if(thisMonth <= 4) {
				// 전년도생산량
				String year = Integer.toString(thisYear - 1);
				Map<String, Object> temp = jejuWinterService.getProdFiveYearEachAvg(radish, year, year);
				if(!temp.isEmpty()) {
					predicProdNowRadish = temp.get(year);
				}
				temp = jejuWinterService.getProdFiveYearEachAvg(cabbage, year, year);
				if(!temp.isEmpty()) {
					predicProdNowCabbage = temp.get(year);
				}
				temp = jejuWinterService.getProdFiveYearEachAvg(carrot, year, year);
				if(!temp.isEmpty()) {
					predicProdNowCarrot = temp.get(year);
				}

				result.put(radish, predicProdNowRadish);
				result.put(cabbage, predicProdNowCabbage);
				result.put(carrot, predicProdNowCarrot);

				return result;

			} else if (thisMonth == 5 || thisMonth == 6 || thisMonth == 7) {
				// 금년 1차 재배의향 / 금년 5, 6, 7
				predicProdNowRadish = jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
				predicProdNowCabbage = jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
				predicProdNowCarrot = jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
			} else if (thisMonth == 8) {
				// 금년 1차 재배의향 / 금년 8월
				predicProdNowRadish = jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
				predicProdNowCabbage = jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
				predicProdNowCarrot = jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 1).get("value");
			} else if (thisMonth == 9) {
				// 금년 2차 재배의향 / 금년 9월
				predicProdNowRadish = jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 2).get("value");
				predicProdNowCabbage = jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 2).get("value");
				predicProdNowCarrot = jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 2).get("value");
			} else if (thisMonth == 10) {
				// 금년 3차 재배의향 / 금년 9월
				predicProdNowRadish = jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 3).get("value");
				predicProdNowCabbage = jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 3).get("value");
				predicProdNowCarrot = jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 3).get("value");
			} else if (thisMonth == 11 || thisMonth == 12) {
				// 금년 확정 재배 신고 / 금년 11, 12월
				predicProdNowRadish = jejuWinterService.getCtvtSttemnt(radish, Integer.toString(thisYear), Integer.toString(thisYear)).get("value");
				predicProdNowRadish = jejuWinterService.getCtvtSttemnt(cabbage, Integer.toString(thisYear), Integer.toString(thisYear)).get("value");
				predicProdNowRadish = jejuWinterService.getCtvtSttemnt(carrot, Integer.toString(thisYear), Integer.toString(thisYear)).get("value");
			}

			if (thisMonth == 5 || thisMonth == 6 || thisMonth == 7) {
				// 5,6,7월은 3년간의 평균 단수로 계산
				radishDansu = jejuWinterService.getDansuByPeriodAvg(radish, Integer.toString(thisYear - 3), Integer.toString(thisYear - 1)).get("value");
				cabbageDansu = jejuWinterService.getDansuByPeriodAvg(cabbage, Integer.toString(thisYear - 3), Integer.toString(thisYear - 1)).get("value");
				carrotDansu = jejuWinterService.getDansuByPeriodAvg(carrot, Integer.toString(thisYear - 3), Integer.toString(thisYear - 1)).get("value");
			} else {
				// 계산식  면적 * 0.001 * 단수
				radishDansu = jejuWinterService.getDansuByYearMon(radish, Integer.toString(thisYear), Integer.toString(thisMonth)).get("value");
				cabbageDansu = jejuWinterService.getDansuByYearMon(cabbage, Integer.toString(thisYear), Integer.toString(thisMonth)).get("value");
				carrotDansu = jejuWinterService.getDansuByYearMon(carrot, Integer.toString(thisYear), Integer.toString(thisMonth)).get("value");
			}

			double dRadish = predicProdNowRadish == null ? 0.0 : (double) predicProdNowRadish;
			double dCabbage = predicProdNowCabbage == null ? 0.0 : (double) predicProdNowCabbage;
			double dCarrot = predicProdNowCarrot == null ? 0.0 : (double) predicProdNowCarrot;

			double dDanRadish = radishDansu == null ? 0.0 : (double) radishDansu;
			double dDanCabbage = cabbageDansu == null ? 0.0 : (double) cabbageDansu;
			double dDanCarrot = carrotDansu == null ? 0.0 : (double) carrotDansu;

			result.put(radish, dRadish * 0.001 * dDanRadish);
			result.put(cabbage, dCabbage * 0.001 * dDanCabbage);
			result.put(carrot, dCarrot * 0.001 * dDanCarrot);

		} catch (IOException e) {
			LOGGER.error("{}",e);
		}

		return result;
	}

	/**
	 * 대시보드에 표출될 재배의향을 현재 시기에 따라 조회하여 가져온다.
	 * 1,2,3,4,5 월 -> 전년도 확정재배의향 표출
	 * 6,7월             -> 금년 1차 재배의향 표출
	 * 8,9월             -> 금년 2차 재배의향 표출
	 * 10월               -> 금년 3차 재배의향 표출
	 * 11,12월          -> 금년 확정 재배의향 표출
	 * @return
	 */
	private Map<String, Object> getDisplayProdWill() throws InstantiationException, IllegalAccessException {
		Map<String, Object> result = new HashMap<String, Object>();

		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		int thisMonth = cal.get(Calendar.MONTH) + 1;
		String yearMonth = "";
		String prodWillDisplayOrd = "";

		try {

			if(thisMonth <= 5) {
				// 전년도 확정재배의향 / 전년 10월
				thisYear = thisYear - 1;
				yearMonth = "10월";
				prodWillDisplayOrd = "확정재배의향";
				result.put(radish, jejuWinterService.getCtvtSttemnt(radish, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
				result.put(cabbage, jejuWinterService.getCtvtSttemnt(cabbage, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
				result.put(carrot, jejuWinterService.getCtvtSttemnt(carrot, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
			} else if (thisMonth == 6 || thisMonth == 7) {
				// 금년 1차 재배의향 / 금년 5월
				yearMonth = "5월";
				prodWillDisplayOrd = "1차 재배의향";
				result.put(radish, jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 1).get(Integer.toString(thisYear)));
				result.put(cabbage, jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 1).get(Integer.toString(thisYear)));
				result.put(carrot, jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 1).get(Integer.toString(thisYear)));
			} else if (thisMonth == 8 || thisMonth == 9) {
				// 금년 2차 재배의향 / 금년 7월
				yearMonth = "7월";
				prodWillDisplayOrd = "2차 재배의향";
				result.put(radish, jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 2).get(Integer.toString(thisYear)));
				result.put(cabbage, jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 2).get(Integer.toString(thisYear)));
				result.put(carrot, jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 2).get(Integer.toString(thisYear)));
			} else if (thisMonth == 10) {
				// 금년 3차 재배의향 / 금년 9월
				yearMonth = "9월";
				prodWillDisplayOrd = "3차 재배의향";
				result.put(radish, jejuWinterService.getWillProd(radish, Integer.toString(thisYear), Integer.toString(thisYear), 3).get(Integer.toString(thisYear)));
				result.put(cabbage, jejuWinterService.getWillProd(cabbage, Integer.toString(thisYear), Integer.toString(thisYear), 3).get(Integer.toString(thisYear)));
				result.put(carrot, jejuWinterService.getWillProd(carrot, Integer.toString(thisYear), Integer.toString(thisYear), 3).get(Integer.toString(thisYear)));
			} else if (thisMonth == 11 || thisMonth == 12) {
				// 금년 확정 재배 신고 / 금년 10월
				//yearMonth = CsUtil.concat("", thisYear, "년", " 10월");
				yearMonth = "10월";
				prodWillDisplayOrd = "확정재배의향";
				result.put(radish, jejuWinterService.getCtvtSttemnt(radish, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
				result.put(cabbage, jejuWinterService.getCtvtSttemnt(cabbage, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
				result.put(carrot, jejuWinterService.getCtvtSttemnt(carrot, Integer.toString(thisYear), Integer.toString(thisYear)).get(Integer.toString(thisYear)));
			}
			String temp = Integer.toString(thisYear);
			temp = temp.substring(temp.length() - 2, temp.length());
			result.put("yearMonth", temp + "년 " + yearMonth);
			result.put("prodWillDisplayOrd", prodWillDisplayOrd);

		} catch (IOException e) {
			LOGGER.error("{}",e);
		}

		return result;
	}

}
