package bigdata.portal.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.enums.RetnCnsdrEnum;
import bigdata.portal.enums.RetnFixesCtvtCmprEnum;
import bigdata.portal.enums.RetnSimilrTopEmdCmprEnum;
import bigdata.portal.enums.RetnWghtValEnum;
import bigdata.portal.mapper.ReturnFarmEsMapper;
import bigdata.portal.mapper.ReturnFarmMapper;
import bigdata.portal.service.ReturnFarmService;
import kr.co.ucsit.core.CsConst;
import kr.co.ucsit.core.CsTransferObject;

@Service
public class ReturnFarmServiceImpl extends BigdataServiceImpl implements ReturnFarmService  {

	private static Logger log = LoggerFactory.getLogger(ReturnFarmServiceImpl.class);


	@Autowired private ReturnFarmMapper returnFarmMapper; // 귀농 mysql

	@Autowired private ReturnFarmEsMapper returnFarmEsMapper; //귀농 엘라스틱



	@SuppressWarnings("unchecked")
	@Override
	/*귀농 정보 등록*/
	public void registRetnFmlgInfo(HttpServletRequest request, HttpSession session) throws InstantiationException, IllegalAccessException, IOException {
		EntityMap map = new EntityMap();
		EntityMap dataObjMap = (EntityMap) session.getAttribute("retnFmlgInfo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		map.put("ip", request.getRemoteAddr());
		map.put("famerInfo", dataObjMap);
		map.put("registDtm", sdf.format(new Date()));

		returnFarmEsMapper.registRetnFmlgInfo(map);
	}

	/**품목리스트 조회*/
	@Override
	public <T extends Map> CsTransferObject getsCtvt(T map) {
		CsTransferObject result = new CsTransferObject();
		result.put(CsConst.DATAS, returnFarmMapper.getsCtvt(map));
		return result;
	}

	/**고려사항 리스트*/
	@Override
	public <T extends Map> CsTransferObject getsCnsdr(T map) {
		CsTransferObject result = new CsTransferObject();

		log.debug("RetnCnsdrEnum.values()");
		log.debug("{}", RetnCnsdrEnum.getDescList());
//		log.debug("{}", RetnCnsdrEnum.class.getEnumConstants());
		result.put(CsConst.DATA, RetnCnsdrEnum.getDescList()); // enum List

		return result;
	}



	/**
	 * 유사귀농인 인덱스 조회
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Map> CsTransferObject getRetnFmlgModelIdx(T dataObjMap) {
		CsTransferObject result = new CsTransferObject();

		if(dataObjMap == null ||
			StringUtils.isEmpty(dataObjMap.get("mvtAdmCd")+"") ||
			StringUtils.isEmpty( dataObjMap.get("selfAge")+"" )

				) {
			result.put(CsConst.DATA, null);
			return result;
		}

		log.debug("{}",dataObjMap);

		//주소 코드 구하기
		dataObjMap.put("mvtCtprvnAdmCd", (dataObjMap.get("mvtAdmCd")+"").substring(0, 2) ); // 전출 시,도 행정동 코드
		dataObjMap.put("mvtSignguAdmCd", (dataObjMap.get("mvtAdmCd")+"").substring(0, 5) ); // 전출 시군구 행정동 코드
		dataObjMap.put("mvtEmdAdmCd", (dataObjMap.get("mvtAdmCd")+"").substring(0, 8) ); // 전출 시군구+읍면동 행정동 코드

		//'시도' 데이터 중에서 시로 끝나는 주소는 '시군구'로 옮긴다. (모델 데이터에 '시'로 끝나는 데이터는 시군구 쪽에 데이터가 없다. 예) 서울특별시 강남구나 종로구 같은 시군구 데이터가 없고 서울특별시만 있음)
		String mvtCtprvn = dataObjMap.get("mvtCtprvn")+"";

		dataObjMap.put("srchMvtCtprvn", dataObjMap.get("mvtCtprvn")+"");
		dataObjMap.put("srchMvtSigngu", dataObjMap.get("mvtSigngu")+"");

		if(mvtCtprvn.endsWith("시") || mvtCtprvn.endsWith("제주특별자치도"))
		dataObjMap.put("srchMvtSigngu", mvtCtprvn ); // 광역, 특별시 같은 경우는 '시군구' 항목에 해당 자치시를 넣는다.

		log.debug("{}",dataObjMap);

		//귀농인 나이 구간 구하기
		int selfAge =  Integer.parseInt(dataObjMap.get("selfAge") + "");
		String selfAgeCd = "";

		if(selfAge >= 20 && selfAge <= 39) selfAgeCd = "1";
		else if(selfAge <= 49) selfAgeCd = "2";
		else if(selfAge <= 59) selfAgeCd = "3";
		else if(selfAge >= 60) selfAgeCd = "4";

		dataObjMap.put("mngmtOwnrAgeSctn", selfAgeCd); // 귀농인 나이 코드


		//동거가족 리스트
		List<Map<String, Object>> livtgtList = (List<Map<String, Object>>) dataObjMap.get("livtgt");

		//동거 가족 코드 초기화
		dataObjMap.put("parntsSuportAt", "0"); // 동거 부모님 없음
		dataObjMap.put("schboyBelowChldrnEnnc", "0"); // 초등학생 이하 자녀 없음
		dataObjMap.put("msklsdHgschstChldrnEnnc", "0"); // 중/고등학생 이하 자녀 없음

		for(Map<String, Object> livtgt : livtgtList) {
			if(livtgt.get("relateText").equals("부모")) {
				dataObjMap.put("parntsSuportAt", "1"); //동거 가족 중 부모님이 있으면 부모 여부 '1'

			}else if(livtgt.get("relateText").equals("자녀")) {
				int childrnAge = Integer.parseInt(livtgt.get("relateAge") + "");

				if(childrnAge <= 13) dataObjMap.put("schboyBelowChldrnEnnc", "1"); // 초등학생 이하 자녀 있음
				else if(childrnAge <= 19) dataObjMap.put("msklsdHgschstChldrnEnnc", "1"); // 중/고등학생 이하 자녀 있음
			}
		}

		/*희망재배 품목이 없으면 insteadCtvt키를 삭제한다.*/
		String hopeCtvt = dataObjMap.get("hopeCtvt")+"";
		if(StringUtils.isEmpty(hopeCtvt) || "null".equals(hopeCtvt)) dataObjMap.remove("insteadCtvt");

		log.info("value for first query:{}", dataObjMap);

		EntityMap modelIdxMap = returnFarmMapper.getRetnFmlgModelIdx(dataObjMap);
		log.debug("modelIdxMap:{}",modelIdxMap);
		if(modelIdxMap == null) {
			dataObjMap.remove("srchMvtSigngu");
			modelIdxMap = returnFarmMapper.getRetnFmlgModelIdx(dataObjMap);

			if(modelIdxMap == null) {
				dataObjMap.remove("mngmtOwnrAgeSctn"); // 귀농희망인 나이 구분
				modelIdxMap = returnFarmMapper.getRetnFmlgModelIdx(dataObjMap);
				log.debug("{}",modelIdxMap);
				if(modelIdxMap == null ) {
					//모델 DB에서 index가 검색되지 않으면 동거 가족 정보를 삭제하고 재 검색한다.
					dataObjMap.remove("parntsSuportAt"); // 부모 조건 삭제
					dataObjMap.remove("schboyBelowChldrnEnnc"); // 초등학생 이하 자녀 조건 삭제
					dataObjMap.remove("msklsdHgschstChldrnEnnc"); // 중/고등학생 이하 자녀 조건 삭제
					modelIdxMap = returnFarmMapper.getRetnFmlgModelIdx(dataObjMap);
					if(modelIdxMap == null ) {

						String tempHopeCtvt = dataObjMap.remove("hopeCtvt")+""; //희망재배품목 조건 삭제
						modelIdxMap = returnFarmMapper.getRetnFmlgModelIdx(dataObjMap);

						if(modelIdxMap != null) modelIdxMap.put("insteadCtvt" , tempHopeCtvt); // 사용자가 선택한 희망재배 품목을 삭제한 대신 차순위 품목을 보여주기 위한 key
						if(!StringUtils.isEmpty(tempHopeCtvt) && !"null".equals(tempHopeCtvt)) { //
							dataObjMap.put("hopeCtvt", tempHopeCtvt);
						}
						log.debug("추천품목!!! : ", tempHopeCtvt);
//						dataObjMap.remove("srchMvtCtprvn"); //전출 시,도 삭제
					}
					log.debug("{}",modelIdxMap);
				}
			}
		}


		result.put(CsConst.DATA, modelIdxMap);
		log.debug("코드결과 : {}", result);

		return result;
	}

	/**
	 * 선택지역 및 품목 분석
	 * */
	@Override
	public <T extends Map> CsTransferObject getSelectAreaCtvtInfo(T dataObjMap) {
		CsTransferObject result = new CsTransferObject();
		Map<String, Object> paramMap = new HashMap<>();

		String ctprvn = dataObjMap.get("hopeCtprvn")+"";
		String signgu = dataObjMap.get("hopeSigngu")+"";
		String prdlstNm = dataObjMap.get("hopeCtvt")+"";

		paramMap.put("ctprvn", ctprvn);
		paramMap.put("signgu", signgu);
		paramMap.put("prdlstNm", prdlstNm);

		log.debug("{}", paramMap);
		if(StringUtils.isNotEmpty(ctprvn) &&

				StringUtils.isNotEmpty(signgu) &&
				StringUtils.isNotEmpty(prdlstNm)) {
			result.put("allRetnMenSttus", returnFarmMapper.getsAllRetnMenSttus(paramMap)); //선택지역 및 품목 분석 > 농업인 현황 > 재배농업인현황
			result.put("allRetnMenSttusAgeSctn", returnFarmMapper.getsAllRetnMenSttusAgeSctn(paramMap)); // 선택지역 및 품목 분석 > 농업인 현황 > 연령대별 재배농업인현황
			result.put("allRetnCtvtAreaAgeSctn", returnFarmMapper.getsAllRetnCtvtAreaAgeSctn(paramMap)); // 선택지역 및 품목 분석 > 재배면적추이> 연령대별 재배 면적 추이
			result.put("beginRetnSttusMenCnt", returnFarmMapper.getsBeginRetnSttusMenCnt(paramMap)); // 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 연령대별 영농인 수
			result.put("beginRetnSttusThisAreaCnt", returnFarmMapper.getsBeginRetnSttusThisAreaCnt(paramMap)); // 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 현재 전출지역
			result.put("beginRetnSttusEtcAreaCnt", returnFarmMapper.getsBeginRetnSttusEtcAreaCnt(paramMap)); // 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 기타 전출지역
			result.put("beginRetnSttusCtvt", returnFarmMapper.getsBeginRetnSttusCtvt(paramMap)); // 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 재배 품목
			result.put("allRetnCtvtAreaYear", returnFarmMapper.getsAllRetnCtvtAreaYear(paramMap)); // 년도별 재배면적추이(지역 + 품목 )
			paramMap.remove("prdlstNm");
			result.put("allRetnAreaYear", returnFarmMapper.getsAllRetnCtvtAreaYear(paramMap));  // 년도별 재배면적추이(지역 )
			log.debug("{}", result);
		}



		return result;
	}

	/**
	 * 유사귀농인 정보
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Map> CsTransferObject getSimilrRetnFmlgInfo(T dataObjMap) {
		CsTransferObject result = new CsTransferObject();
		int index = (int) Double.parseDouble(dataObjMap.get("index")+"");
		List<List<EntityMap>> beginRetnCtvtCntList = new ArrayList<>();
		List<EntityMap> areaInfoList = (List<EntityMap>) dataObjMap.get("areaInfo");

		result.put("retnFmlgTrnsfrnArea", returnFarmMapper.getsRetnFmlgTrnsfrnArea(dataObjMap)); // 전체 귀농인 전입지역
		result.put("similrRetnFmlgTrnsfrnArea", returnFarmMapper.getsSimilrRetnFmlgTrnsfrnArea(dataObjMap)); // 유사 귀농인 전입지역
		result.put("similrRetnFmlgCtvt", returnFarmMapper.getsSimilrRetnFmlgCtvt(dataObjMap)); // 유사귀농인 재배 품목

		//int maxIndex = (areaInfoList.size() > 3 ? 3 : areaInfoList.size());
		int maxIndex =  areaInfoList.size();
		areaInfoList = areaInfoList.subList(0, maxIndex); // 상위3개 지역만 초기영농인 재배품목을 조회한다.

		for(Map<String, Object> areaInfo : areaInfoList) {
			beginRetnCtvtCntList.add(returnFarmMapper.getsBeginRetnCtvtCnt(areaInfo)); // 초기 영농인 재배 품목
		}

		result.put("beginRetnCtvtCntList", beginRetnCtvtCntList);

		result.put("similrRetnFmlgCnsdrDesc", RetnWghtValEnum.getsCnsdrDesc(index)); // 유사 귀농인 고려사항


		return result;
	}


	/**
	 * 귀농인 지역 정보 > 귀농 추천 지역
	 *   지역정주여건, 귀농지역 맞춤 품목
	 *
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Map> CsTransferObject getRetnFmlgAreaInfo(T dataObjMap) throws InstantiationException, IllegalAccessException, IOException {
		CsTransferObject result = new CsTransferObject();
		List<EntityMap> recomendTop5Area = getsRecomendTop5Area(dataObjMap); // 상위 5개 지역

		recomendTop5Area = getTop5RadarChartData(recomendTop5Area); // 레이더 차트에 사용될 데이터 입력

		//int listSize3 = (recomendTop5Area.size() > 3) ? 3 : recomendTop5Area.size();
		int listSize3 =  recomendTop5Area.size();
		for(int i=0; i<listSize3; i++) {
			Map<String, Object> paramMap = new HashMap<>();

			/** paramMap에 지역 정보 입력*/
			paramMap.put("code", recomendTop5Area.get(i).get("code"));

			recomendTop5Area.get(i).put("areaSetlCnd", returnFarmMapper.getAreaSetlCnd(paramMap)); // 해당지역 정주여건
			recomendTop5Area.get(i).put("upperRecomendArea",getsUpperRecomendTop11Area(paramMap, dataObjMap)); // 해당 상위지역(시,도) 기준 최대 11곳 읍면동 지역 불러오기
			recomendTop5Area.get(i).put("upperAreaAgeSctn", returnFarmMapper.getsUpperAreaAgeSctn(paramMap)); // 귀농인 상위지역 연령대
			recomendTop5Area.get(i).put("upperAreaMvtInfo", returnFarmMapper.getsUpperAreaMvtInfo(paramMap)); // 귀농인 상위지역 전출지
			recomendTop5Area.get(i).put("upperAreaCtvtInfo", returnFarmMapper.getsUpperAreaCtvtInfo(paramMap)); // 귀농인 상위지역 재배품목
			recomendTop5Area.get(i).put("distbInfoApc", returnFarmMapper.getsDistbInfoApc(paramMap)); // 유통 > 농산물산지유통정보
			recomendTop5Area.get(i).put("distbInfoLocal", returnFarmMapper.getsDistbInfoLocal(paramMap)); // 유통 > 로컬푸드매장정보
			recomendTop5Area.get(i).put("altrvSchul", returnFarmMapper.getsAdiInfoAltrvSchul(paramMap)); // 부가정보 > 대안학교

			paramMap.put("ctprvn", recomendTop5Area.get(i).get("ctprvn"));
			paramMap.put("signgu", recomendTop5Area.get(i).get("signgu"));


			recomendTop5Area.get(i).put("uninhbhousInfo", returnFarmEsMapper.getsUninhbhousInfo(paramMap)); // 빈집정보
			recomendTop5Area.get(i).put("jbhntInfo", returnFarmEsMapper.getsJbhntInfo(paramMap)); // 일자리정보


			if(dataObjMap.get("insteadCtvt") == null )	paramMap.put("hopeCtvt", dataObjMap.get("hopeCtvt")); // insteadCtvt 없으면 희망재배 품목 입력

			log.debug("{}",paramMap);
			/**맞춤 재배 품목*/
			List<EntityMap> fixesCtvtList = getsFixesCtvt(paramMap);

			for(EntityMap fixesCtvt : fixesCtvtList) {
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("code", paramMap.get("code")); // 재배품목
				paramMap2.put("prdlstNm", fixesCtvt.get("prdlstNm")); // 재배품목

				fixesCtvt.put("avgPrdlstIndex", returnFarmMapper.getsAvgPrdlstIndex()); // 품목 지수 전체 평균

				// 품목별 재배 면적
				fixesCtvt.put("allRetnYearCtvtAra", returnFarmMapper.getsAllRetnYearCtvtAra(paramMap2)); // 전체 영농인
				fixesCtvt.put("beginRetnYearCtvtAra", returnFarmMapper.getsBeginRetnYearCtvtAra(paramMap2)); // 초기 영농인

				// 연령대별 재배 농가 현황
				fixesCtvt.put("allRetnAgeCtvt", returnFarmMapper.getsAllRetnAgeCtvt(paramMap2)); // 전체 영농인
				fixesCtvt.put("beginRetnAgeCtvt", returnFarmMapper.getsBeginRetnAgeCtvt(paramMap2)); // 초기 영농인

				//소득정보
				fixesCtvt.put("prdlstIncome", returnFarmMapper.getsPrdlstIncome(paramMap2));

				//2018년 도매시장 경락가격(도매시장 경락가격이 2018~2019년만 있음)
				paramMap2.put("srchEsYear", "2018");

				fixesCtvt.put("allAvgWholeSale2018", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2018년 전체

				paramMap2.put("ctprvn", paramMap.get("ctprvn"));
				paramMap2.put("signgu", paramMap.get("signgu"));
				fixesCtvt.put("areaAvgWholeSale2018", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2018년 지역

				//2019년 도매시장 경락가격
				paramMap2.put("srchEsYear", "2019");
				paramMap2.remove("ctprvn");
				paramMap2.remove("signgu");
				fixesCtvt.put("allAvgWholeSale2019", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2019년 전체

				paramMap2.put("ctprvn", paramMap.get("ctprvn"));
				paramMap2.put("signgu", paramMap.get("signgu"));
				fixesCtvt.put("areaAvgWholeSale2019", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2019년 지역
				
				//2020년 도매시장 경락가격
				paramMap2.put("srchEsYear", "2020");
				paramMap2.remove("ctprvn");
				paramMap2.remove("signgu");
				fixesCtvt.put("allAvgWholeSale2020", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2020년 전체

				paramMap2.put("ctprvn", paramMap.get("ctprvn"));
				paramMap2.put("signgu", paramMap.get("signgu"));
				fixesCtvt.put("areaAvgWholeSale2020", returnFarmEsMapper.getAvgWholeSale(paramMap2)); // 2020년 지역

				//소매 가격 조회
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
				SimpleDateFormat sdfMonthDate = new SimpleDateFormat("MMdd");

				//당일 소매 가격
				paramMap2.put("srchEsYear", sdfYear.format(cal.getTime()));
				paramMap2.put("stratSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				paramMap2.put("endSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				fixesCtvt.put("rtlsalToday", returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));

				//3일 전 소매 가격조회
				cal.add(Calendar.DATE, -3);
				log.debug("{}",cal);
				log.debug("{}",cal);
				paramMap2.put("srchEsYear", sdfYear.format(cal.getTime()));
				paramMap2.put("stratSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				paramMap2.put("endSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				fixesCtvt.put("rtlsalBefore3Date",returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));


				//7일 전 소매 가격조회
				cal.add(Calendar.DATE, -4);
				log.debug("{}",cal);
				paramMap2.put("srchEsYear", sdfYear.format(cal.getTime()));
				paramMap2.put("stratSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				paramMap2.put("endSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				fixesCtvt.put("rtlsalBefore7Date",returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));

				//1달 전 소매 가격조회
				cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -1);
				log.debug("{}",cal);
				paramMap2.put("srchEsYear", sdfYear.format(cal.getTime()));
				paramMap2.put("stratSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				paramMap2.put("endSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				fixesCtvt.put("rtlsalBefore1Month",returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));

				//1년 전 소매 가격조회
				cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -1);
				paramMap2.put("srchEsYear", sdfYear.format(cal.getTime()));
				paramMap2.put("stratSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				paramMap2.put("endSrchEsMonthDate", sdfMonthDate.format(cal.getTime()));
				fixesCtvt.put("rtlsalBefore1Year",returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));

				//평년 소매 가격 조회
				paramMap2.remove("srchEsYear");
				paramMap2.remove("stratSrchEsMonthDate");
				paramMap2.remove("endSrchEsMonthDate");
				fixesCtvt.put("rtlsalAvgYear",returnFarmEsMapper.getsAvgRtlsalInfo(paramMap2));




				log.debug("{}",paramMap2);

			}//for

			// 차순위 품목 키가 null이 아닐 경우, 희망재배 품목 대신 차순위 재배 품목이 셋팅되도록 한다.
			if(dataObjMap.get("insteadCtvt") != null ) {
				recomendTop5Area.get(i).put("fixesCtvt", fixesCtvtList.subList(0, 1));

				if(fixesCtvtList.size() > 0)	dataObjMap.put("hopeCtvt", fixesCtvtList.get(0).get("prdlstNm"));
			}else {
				recomendTop5Area.get(i).put("fixesCtvt", fixesCtvtList);
			}

		}

		result.put(CsConst.DATAS, recomendTop5Area);
		return result;
	}

	/** 1개 지역의 상위(시,도)지역에서 최대 11개 지역에 대한 데이터를 조회함*/
	@SuppressWarnings("unchecked")
	private <T extends Map> List<EntityMap> getsUpperRecomendTop11Area(T paramMap, T dataObjMap) {
		List<EntityMap> upperAreaSetlCndList = returnFarmMapper.getsUpperAreaSetlCnd(paramMap);
		upperAreaSetlCndList = getTotalWghtValRank(upperAreaSetlCndList, dataObjMap, 11);
		return upperAreaSetlCndList;
	}


	/**5개지역 추천*/
	@SuppressWarnings("unchecked")
	public <T extends Map> List<EntityMap> getsRecomendTop5Area(T dataObjMap){
		List<EntityMap> similrCntTopEmdAvgValList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();

		String index = dataObjMap.get("index")+"";
		if(StringUtils.isEmpty(index)) {
				return null;
		}

		paramMap.put("index", index);
		paramMap.put("hopeCtprvn", dataObjMap.get("hopeCtprvn"));
		paramMap.put("hopeSigngu", dataObjMap.get("hopeSigngu"));

		if(dataObjMap.get("insteadCtvt") == null) paramMap.put("hopeCtvt", dataObjMap.get("hopeCtvt"));


		similrCntTopEmdAvgValList = returnFarmMapper.getSimilrCntTopEmdAvgVal(paramMap); // 유사귀농인 상위 50개 읍면동 지역 점수
		log.debug("paramMap:{}", paramMap);
		log.debug("similrCntTopEmdAvgValList:{}", similrCntTopEmdAvgValList);


		if(similrCntTopEmdAvgValList.size() == 0) { // 데이터가 검색되지 않으면 귀농모델 그룹(index)를 적용하지 않는다.
			paramMap.remove("index");
			similrCntTopEmdAvgValList = returnFarmMapper.getSimilrCntTopEmdAvgVal(paramMap); // 유사귀농인 상위 50개 읍면동 지역 점수


		}
		//jhok
		similrCntTopEmdAvgValList = getTotalWghtValRank(similrCntTopEmdAvgValList, dataObjMap, 10);

		log.debug("{}", similrCntTopEmdAvgValList);
		return similrCntTopEmdAvgValList;

	}


	/**
	 * 레이더 차트를 그리기 위해 Top5 지역끼리 다시
	 * 점수를 구한다.
	 * */
	@SuppressWarnings("unchecked")
	private <T extends Map<String,Object>> List<EntityMap> getTop5RadarChartData(List<EntityMap> areaList){

		areaList.sort(RetnSimilrTopEmdCmprEnum.EDC_SCORE_DESC.getComparator()); // 교육
		addScoreKey(areaList,"edcAvgAccesPosbltyScore");

		areaList.sort(RetnSimilrTopEmdCmprEnum.TRNSPORT_SCORE_DESC.getComparator()); // 교통
		addScoreKey(areaList,"trnsportAvgAccesPosbltyScore");

		areaList.sort(RetnSimilrTopEmdCmprEnum.HSPTL_SCORE_DESC.getComparator()); // 의료
		addScoreKey(areaList,"hsptlAvgAccesPosbltyScore");

		areaList.sort(RetnSimilrTopEmdCmprEnum.CNVNC_MRKT_SCORE_DESC.getComparator()); // 편의
		addScoreKey(areaList,"cnvncMrktAvgAccesPosbltyScore");

		areaList.sort(RetnSimilrTopEmdCmprEnum.CLTUR_SCORE_ASC.getComparator()); // 문화
		addScoreKey(areaList,"clturAvgCoscore");

		areaList.sort(RetnSimilrTopEmdCmprEnum.LAD_DELNG_SCORE_DESC.getComparator()); // 경제(토지 실거래 금액)
		addScoreKey(areaList,"ladAvrgDelngAmountScore");


		return areaList;
	}


	/**여러 지역들의 분야별 평균데이터를 통해 가중치 적용, 가중치 적용된 값의 총 합 + 순위를 구한다.
	 * @param areaList 귀농 모델 DB List
	 * @param 몇 순위까지 가져올 것인지
	 * @return List
	 * */
	@SuppressWarnings("unchecked")
	private <T extends Map<String,Object>> List<EntityMap> getTotalWghtValRank(List<EntityMap> areaList, T dataObjMap, int getRank ){
		List<EntityMap> result = new ArrayList<>();
		log.debug("{}", areaList);

		/*교육 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.EDC_DESC.getComparator());
		addScoreKey(areaList,"edcAvgAccesPosblty");

		/*교통 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.TRNSPORT_DESC.getComparator());
		addScoreKey(areaList,"trnsportAvgAccesPosblty");

		/*의료 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.HSPTL_DESC.getComparator());
		addScoreKey(areaList,"hsptlAvgAccesPosblty");

		/*편의-마트 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.CNVNC_MRKT_DESC.getComparator());
		addScoreKey(areaList,"cnvncMrktAvgAccesPosblty");

		/*유통 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.DISTB_ASC.getComparator());
		addScoreKey(areaList,"distbAvgCo");

		/*편의 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.CNVNC_ASC.getComparator());
		addScoreKey(areaList,"cnvncAvgCo");

		/*문화 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.CLTUR_ASC.getComparator());
		addScoreKey(areaList,"clturAvgCo");

		/*학원 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.INSTUT_ASC.getComparator());
		addScoreKey(areaList,"instutCo");

		/*귀농 지원 정책 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.POLICY_ASC.getComparator());
		addScoreKey(areaList,"rtrnFrmhsSportPolicyCo");

		/*토지 실거래 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.LAD_DELNG_DESC.getComparator());
		addScoreKey(areaList,"ladAvrgDelngAmount");

		/*토지 임대 데이터 순으로 정렬*/
		areaList.sort(RetnSimilrTopEmdCmprEnum.LAD_RENT_DESC.getComparator());
		addScoreKey(areaList,"ladAvrgRentAmount");

		// 점수 값에 가중치 적용
		areaList = addWghtValKey(areaList, dataObjMap);

		// 점수 값 총 합 구하기 및 점수순으로 정렬
		areaList = addTotalWghtValKey(areaList);
		areaList.sort(RetnSimilrTopEmdCmprEnum.TOTAL_SCORE_WGHT_VAL_DESC.getComparator());


		//지역 개수보다 rank값이 더 크거나 rank값이 음수면 getRank 변수의 값은 리스트의 총 개수로 설정한다.
		if(areaList.size() < getRank || getRank < 0) getRank = areaList.size();

		//최고 점수 getRank개 지역 표출
		result = areaList.subList(0, getRank);
		return result;
	}

	/**여러 지역들의 분야별 평균데이터를 통해 상대 점수를 구한다.*/
	private <T extends Map> void addScoreKey(List<EntityMap> similrCntTopEmdAvgValList, String key) {

		int score = 0;
		double prevValueDouble = -999999999L;

		for(EntityMap map : similrCntTopEmdAvgValList) { // 점수 적용한다.
			double thisVal = Double.parseDouble(map.get(key)+"");
			if(prevValueDouble == thisVal)	{
				map.put(key+"_SCORE", score); // 데이터가 이전 데이터와 같다면 점수는 그대로 적용
			}
			else {
				prevValueDouble = thisVal;
				map.put(key+"_SCORE", ++score); // 데이터가 이전 데이터와 다르면 점수 + 1
			}

//			log.debug("key:{},   thisVal:{},  Score:{}", key, thisVal, score);
		}
	}


	/**점수 값에 가중치를 적용한다.*/
	@SuppressWarnings("unchecked")
	private <T extends Map<String,Object>> List<EntityMap> addWghtValKey(List<EntityMap> similrCntTopEmdAvgValList, T dataObjMap ) {
		List<EntityMap> result = new ArrayList<EntityMap>();
		/*고려사항이 있으면 기본 가중치, 있으면 중요도에 따라 가중치를 다르게 적용한다.*/
		int index = Integer.parseInt(dataObjMap.get("index")+"");

		for(EntityMap map : similrCntTopEmdAvgValList) {
			EntityMap copyMap = new EntityMap();
			copyMap.putAll(map); // map 복사

			for(String key : map.keyList()) {
				if(key.endsWith("Score")) { // Score로 끝나는 키를 찾고 그 값에 가중치를 적용한다.
					double score = map.getDouble(key);
					double wghtVal = RetnWghtValEnum.getWghtVal(key, index); // 기본 가중치

					List<EntityMap> upperCnsdrList = (List<EntityMap>) dataObjMap.get("upperCnsdr"); // 상위 고려사항
					List<EntityMap> middleCnsdrList = (List<EntityMap>) dataObjMap.get("middleCnsdr"); // 중위 고려사항
					List<EntityMap> lowerCnsdrList = (List<EntityMap>) dataObjMap.get("lowerCnsdr"); // 하위 고려사항

					//고려사항이 하나라도 있으면 기본 가중치는 없앤다.
					if(upperCnsdrList.size() > 0 ||
						middleCnsdrList.size() > 0 ||
						lowerCnsdrList.size() > 0 ) {
						wghtVal = 0;
					}

					// 상위 가중치
					for(Map<String, Object> cnsdr : upperCnsdrList) {
						String idx = cnsdr.get("idx")+"";
						String mapKey = RetnCnsdrEnum.getKey(idx);
						if(mapKey.equals(key)) {
							wghtVal = RetnWghtValEnum.UPPER.getWghtVal();
						}
					}

					// 중위 가중치
					for(Map<String, Object> cnsdr : middleCnsdrList) {
						String idx = cnsdr.get("idx")+"";
						String mapKey = RetnCnsdrEnum.getKey(idx);
						if(mapKey.equals(key)) {
							wghtVal = RetnWghtValEnum.MIDDLE.getWghtVal();
						}
					}

					// 하위 가중치
					for(Map<String, Object> cnsdr : middleCnsdrList) {
						String idx = cnsdr.get("idx")+"";
						String mapKey = RetnCnsdrEnum.getKey(idx);
						if(mapKey.equals(key)) {
							wghtVal = RetnWghtValEnum.LOWER.getWghtVal();
						}
					}


					score *= wghtVal; // 가중치 적용
					copyMap.put(key + "_WGHTVAL", score);
				}

			}
			result.add(copyMap);
		}
		return result;
	}

	/**가중치 총 합 구하기*/
	private <T extends Map<String,Object>> List<EntityMap> addTotalWghtValKey(List<EntityMap> dataList) {
		List<EntityMap> result = new ArrayList<EntityMap>();

		for(EntityMap map : dataList) {
			EntityMap copyMap = new EntityMap();
			double totalScore = 0;
			copyMap.putAll(map); // map 복사

			for(String key : map.keyList()) {
				if(key.endsWith("Wghtval")) { // Wghtval로 끝나는 키를 찾고 그 값의 합을 구한다.
					totalScore += Double.parseDouble(map.get(key)+"");
				}

			}
			copyMap.put("totalScoreWghtval", totalScore);
			result.add(copyMap);
		}

		return result;
	}

	/** 점수의 총 합 구하기*/
	private <T extends Map<String,Object>> List<EntityMap> addTotalScoreKey(List<EntityMap> dataList) {
		List<EntityMap> result = new ArrayList<EntityMap>();

		for(EntityMap map : dataList) {
			EntityMap copyMap = new EntityMap();
			double totalScore = 0;
			copyMap.putAll(map); // map 복사

			for(String key : map.keyList()) {
				if(key.endsWith("Score")) { // Score로 끝나는 키를 찾고 그 값의 합을 구한다.
					totalScore += Double.parseDouble(map.get(key)+"");
				}

			}
			copyMap.put("totalScore", totalScore);
			result.add(copyMap);
		}

		return result;
	}

	/**지역별 특화정보 및 품목지수 불러오기*/
	@SuppressWarnings("unchecked")
	private <T extends Map<String,Object>> List<EntityMap> getsFixesCtvt(T paramMap) {
		List<EntityMap> fixesCtvtList = returnFarmMapper.getsFixesCtvt(paramMap);

		/*품목지수 index1로 오름차순 정렬*/
		fixesCtvtList.sort(RetnFixesCtvtCmprEnum.INDEX1_ASC.getComparator());
		addScoreKey(fixesCtvtList, "index1");

		/*품목지수 index2로 오름차순 정렬*/
		log.debug("{}",fixesCtvtList);
		addScoreKey(fixesCtvtList, "index2");

		/*품목지수 index3로 오름차순 정렬*/
		fixesCtvtList.sort(RetnFixesCtvtCmprEnum.INDEX3_DESC.getComparator());
		addScoreKey(fixesCtvtList, "index3");

		/*점수 총 합*/
		fixesCtvtList = addTotalScoreKey(fixesCtvtList);
		fixesCtvtList.sort(RetnFixesCtvtCmprEnum.TOTAL_CORE_DESC.getComparator());

		/*최고 3개 품목만 반환한다.*/
		int listIdx = (fixesCtvtList.size() < 3) ? fixesCtvtList.size() : 3;
		fixesCtvtList = fixesCtvtList.subList(0, listIdx);

		return fixesCtvtList;
	}







}

