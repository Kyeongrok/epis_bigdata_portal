package bigdata.portal.enums;

import java.util.HashMap;
import java.util.Map;

public enum RetnCnsdrEnum {
	TRNSPORT("1","교통시설(터미널/기차역/공항) 접근성 우수 지역", "trnsportAvgAccesPosbltyScore"),
	HSPTL("2","의료시설(공공/종합/일반의원) 접근성 우수 지역", "hsptlAvgAccesPosbltyScore"),
	SCHUL("3","학교시설(초/중/고등학교) 접근성 우수 지역", "edcAvgAccesPosbltyScore"),
	INSTUT("4","교육시설(학원/교습소) 수 우수 지역","instutCoScore"),
	CMRC("5","상업시설(대규모점포/전통시장) 접근성 우수 지역","cnvncMrktAvgAccesPosbltyScore"),
	EDT("6","편의시설(은행/편의점/극장) 수 우수 지역", "cnvncAvgCoScore"),
	CLTUR("7","문화시설 우수 지역", "clturAvgCoScore"),
	POLICY("8","귀농지원정책 우수 지역", "rtrnFrmhsSportPolicyCoScore"),
	DISTB("9","농산물유통여건(직매장/유통센터) 우수 지역", "distbAvgCoScore"),
	REAL_DLPC("10","농지 실거래 가격이 낮은 지역", "ladAvrgDelngAmountScore"),
	RENT_DLPC("11","농지 임대 가격이 낮은 지역", "ladAvrgRentAmountScore");

	private String idx;
	private String desc;
	private String key;

	RetnCnsdrEnum(String idx, String desc, String key){
		this.idx = idx;
		this.desc = desc;
		this.key = key;

	}

	public String getKey() {
		return this.key;
	}
	public String getDesc() {
		return this.desc;
	}
	public String getName() {
		return this.name();
	}
	public String getIdx() {
		return this.idx;
	}

	/**
	 *
	 * idx로 desc 값을 가져온다.
	 * @param idx
	 * @return Map
	 * */
	public static String getDesc(String idx) {
		for(RetnCnsdrEnum retnCnsdrEnum : RetnCnsdrEnum.values()) {
			if(retnCnsdrEnum.getIdx().equals(idx)) return retnCnsdrEnum.getDesc();
		}
		return "";
	}

	/**/
	public static String getKey(String idx) {
		for(RetnCnsdrEnum retnCnsdrEnum : RetnCnsdrEnum.values()) {
			if(retnCnsdrEnum.getIdx().equals(idx)) return retnCnsdrEnum.getKey();
		}
		return "";
	}
	/**
	 * Enum 전체 리스트 출력
	 * @return Map
	 * */
	public static Map<String, Object> getDescList() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(RetnCnsdrEnum retnCnsdrEnum : RetnCnsdrEnum.values()) {
			map.put(retnCnsdrEnum.getIdx(), retnCnsdrEnum.getDesc());
		}
		return map;
	}



}
