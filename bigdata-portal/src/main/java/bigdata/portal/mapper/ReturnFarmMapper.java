package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
@SuppressWarnings("rawtypes")
public interface ReturnFarmMapper {

	public List<EntityMap> selects(HashMap<String, Object> param);

	public <T extends Map>  List<EntityMap> getsCtvt(T map);

	/**
	 * 귀농 희망인 정보로 모델 Index를 구함
	 * */
	public <T extends Map> EntityMap getRetnFmlgModelIdx(T dataObjMap);

	/**
	 * 유사 귀농인 상위 50개 읍면동 지역 + 분야별 평균 데이터 조회
	 * */
	public <T extends Map> List<EntityMap> getSimilrCntTopEmdAvgVal(T map);

	/**
	 * 지역요인 가중치 정보 적용하여 상위 5개 읍면동 데이터 추출
	 * */
	public <T extends Map> List<EntityMap> getAreaWghtValTopEmd(T map);

	/**
	 * 해당 지역 정주여건 검색
	 * */
	public <T extends Map> EntityMap getAreaSetlCnd(T map);

	/**
	 * 상위 지역 정주여건 검색
	 * */
	public <T extends Map> List<EntityMap> getsUpperAreaSetlCnd(T paramMap);

	/**
	 * 상위지역 연령대별 귀농인
	 * */
	public <T extends Map> List<EntityMap> getsUpperAreaAgeSctn(T paramMap);

	/**
	 * 상위지역 귀농인 전출지역
	 * */
	public <T extends Map> List<EntityMap> getsUpperAreaMvtInfo(T paramMap);

	/**
	 * 상위지역 귀농인 재배품목
	 * */
	public <T extends Map> List<EntityMap> getsUpperAreaCtvtInfo(T paramMap);

	/**
	 * 지역별 특화품목 + 품목지수
	 * */
	public <T extends Map> List<EntityMap> getsFixesCtvt(T paramMap);

	/**
	 * 품목지수 전체 평균
	 * */
	public EntityMap getsAvgPrdlstIndex();

	/**
	 * 지역 > 재배 품목별 > 년도별 > 전체 영농인 재배 면적
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnYearCtvtAra(T paramMap);

	/**
	 * 지역 > 재배 품목별 > 년도별 > 초기 영농인 재배 면적
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnYearCtvtAra(T paramMap);

	/**
	 * 지역 > 재배 품목별 > 연령별 > 전체 영농인 품목별 재배 농가 현황
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnAgeCtvt(T paramMap);

	/**
	 * 지역 > 재배 품목별 > 연령별 > 초기 영농인 품목별 재배 농가 현황
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnAgeCtvt(T paramMap);

	/**
	 * 지역 > 재배 품목별 > 소득정보
	 * */
	public <T extends Map> EntityMap getsPrdlstIncome(T paramMap);

	/**
	 * 지역 > 유통 > 농산물산지유통정보
	 * */
	public <T extends Map> List<EntityMap> getsDistbInfoApc(T paramMap);

	/**
	 * 지역 > 유통 > 로컬푸트매장정보
	 * */
	public <T extends Map> List<EntityMap> getsDistbInfoLocal(T paramMap);

	/**
	 * 지역 > 부가정보 > 대안학교
	 * */
	public <T extends Map> List<EntityMap> getsAdiInfoAltrvSchul(T paramMap);

	/**
	 * 유사귀농인 > 전체 귀농인 전입지역
	 * */
	public <T extends Map> List<EntityMap> getsRetnFmlgTrnsfrnArea(T paramMap);

	/**
	 * 유사귀농인 > 유사 귀농인 전입지역
	 * */
	public <T extends Map> List<EntityMap> getsSimilrRetnFmlgTrnsfrnArea(T paramMap);

	/**
	 * 유사귀농인 > 초기영농인 재배 품목
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnCtvtCnt(T paramMap);

	/**
	 * 유사귀농인 > 유사귀농인 재배 품목
	 * */
	public <T extends Map> List<EntityMap> getsSimilrRetnFmlgCtvt(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 농업인 현황 > 재배농업인현황
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnMenSttus(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 농업인 현황 > 연령대별 재배농업인현황
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnMenSttusAgeSctn(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 재배면적추이 > 년도별 재배 면적 추이
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnCtvtAreaYear(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 재배면적추이> 연령대별 재배 면적 추이
	 * */
	public <T extends Map> List<EntityMap> getsAllRetnCtvtAreaAgeSctn(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 연령대별 영농인 수
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnSttusMenCnt(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 현재 전출지역
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnSttusThisAreaCnt(T paramMap);
	/**
	 * 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 기타 전출지역
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnSttusEtcAreaCnt(T paramMap);

	/**
	 * 선택지역 및 품목 분석 > 초기영농인 영농 현황 > 재배 품목
	 * */
	public <T extends Map> List<EntityMap> getsBeginRetnSttusCtvt(T paramMap);

	/*
	 * 귀농 조회 정보 저장
	 * */
	public <T extends Map> void registRetnFmlgInfo(T map);










}
