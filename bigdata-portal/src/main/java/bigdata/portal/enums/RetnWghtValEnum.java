package bigdata.portal.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bigdata.portal.entity.EntityMap;

/**항목별 가중치*/
public enum RetnWghtValEnum {

	/**교육  (인덱스, 가중치)*/
	EDC_1(0.1, 1, RetnCnsdrEnum.SCHUL),
	EDC_2(0.1, 2, RetnCnsdrEnum.SCHUL),
	EDC_3(0.6, 3, RetnCnsdrEnum.SCHUL),
	EDC_4(0.1, 4, RetnCnsdrEnum.SCHUL),
	EDC_5(0.1, 5, RetnCnsdrEnum.SCHUL),
	EDC_6(0.1, 6, RetnCnsdrEnum.SCHUL),
	EDC_7(0.1, 7, RetnCnsdrEnum.SCHUL),

	/**교통 (인덱스, 가중치)*/
	TRNSPORT_1(0.6, 1, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_2(0.3, 2, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_3(0.6, 3, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_4(0.6, 4, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_5(0.6, 5, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_6(0.6, 6, RetnCnsdrEnum.TRNSPORT),
	TRNSPORT_7(0.6, 7, RetnCnsdrEnum.TRNSPORT),

	/**병원 (인덱스, 가중치)*/
	HSPTL_1(0.6, 1, RetnCnsdrEnum.HSPTL),
	HSPTL_2(0.6, 2, RetnCnsdrEnum.HSPTL),
	HSPTL_3(0.6, 3, RetnCnsdrEnum.HSPTL),
	HSPTL_4(0.1, 4, RetnCnsdrEnum.HSPTL),
	HSPTL_5(0.3, 5, RetnCnsdrEnum.HSPTL),
	HSPTL_6(0.1, 6, RetnCnsdrEnum.HSPTL),
	HSPTL_7(0.1, 7, RetnCnsdrEnum.HSPTL),

	/**편의(마트) (인덱스, 가중치)*/
	CNVNC_MRKT_1(0.6, 1, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_2(0.1, 2, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_3(0.6, 3, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_4(0.1, 4, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_5(0.6, 5, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_6(0.1, 6, RetnCnsdrEnum.CMRC),
	CNVNC_MRKT_7(0.1, 7, RetnCnsdrEnum.CMRC),

	/**토지 실거래 (인덱스, 가중치)*/
	LAD_DELNG_1(0.1, 1, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_2(0.1, 2, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_3(0.1, 3, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_4(0.1, 4, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_5(0.1, 5, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_6(0.1, 6, RetnCnsdrEnum.REAL_DLPC),
	LAD_DELNG_7(0.1, 7, RetnCnsdrEnum.REAL_DLPC),

	/**토지 임대 (인덱스, 가중치)*/
	LAD_RENT_1(0.6, 1, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_2(0.6, 2, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_3(0.6, 3, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_4(0.1, 4, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_5(0.1, 5, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_6(0.6, 6, RetnCnsdrEnum.RENT_DLPC),
	LAD_RENT_7(0.1, 7, RetnCnsdrEnum.RENT_DLPC),

	/**유통 (인덱스, 가중치)*/
	DISTB_1(0.6, 1, RetnCnsdrEnum.DISTB),
	DISTB_2(0.6, 2, RetnCnsdrEnum.DISTB),
	DISTB_3(0.6, 3, RetnCnsdrEnum.DISTB),
	DISTB_4(0.1, 4, RetnCnsdrEnum.DISTB),
	DISTB_5(0.1, 5, RetnCnsdrEnum.DISTB),
	DISTB_6(0.1, 6, RetnCnsdrEnum.DISTB),
	DISTB_7(0.1, 7, RetnCnsdrEnum.DISTB),

	/**편의 (인덱스, 가중치)*/
	CNVNC_1(0.6, 1, RetnCnsdrEnum.EDT),
	CNVNC_2(0.6, 2, RetnCnsdrEnum.EDT),
	CNVNC_3(0.1, 3, RetnCnsdrEnum.EDT),
	CNVNC_4(0.1, 4, RetnCnsdrEnum.EDT),
	CNVNC_5(0.1, 5, RetnCnsdrEnum.EDT),
	CNVNC_6(0.6, 6, RetnCnsdrEnum.EDT),
	CNVNC_7(0.1, 7, RetnCnsdrEnum.EDT),

	/**문화 (인덱스, 가중치)*/
	CLTUR_1(0.6, 1, RetnCnsdrEnum.CLTUR),
	CLTUR_2(0.3, 2, RetnCnsdrEnum.CLTUR),
	CLTUR_3(0.6, 3, RetnCnsdrEnum.CLTUR),
	CLTUR_4(0.1, 4, RetnCnsdrEnum.CLTUR),
	CLTUR_5(0.1, 5, RetnCnsdrEnum.CLTUR),
	CLTUR_6(0.3, 6, RetnCnsdrEnum.CLTUR),
	CLTUR_7(0.1, 7, RetnCnsdrEnum.CLTUR),

	/**학원 (인덱스, 가중치)*/
	INSTUT_1(0.1, 1, RetnCnsdrEnum.INSTUT),
	INSTUT_2(0.1, 2, RetnCnsdrEnum.INSTUT),
	INSTUT_3(0.6, 3, RetnCnsdrEnum.INSTUT),
	INSTUT_4(0.1, 4, RetnCnsdrEnum.INSTUT),
	INSTUT_5(0.1, 5, RetnCnsdrEnum.INSTUT),
	INSTUT_6(0.1, 6, RetnCnsdrEnum.INSTUT),
	INSTUT_7(0.1, 7, RetnCnsdrEnum.INSTUT),

	/**지원정책 (인덱스, 가중치)*/
	POLICY_1(0.6, 1, RetnCnsdrEnum.POLICY),
	POLICY_2(0.6, 2, RetnCnsdrEnum.POLICY),
	POLICY_3(0.6, 3, RetnCnsdrEnum.POLICY),
	POLICY_4(0.3, 4, RetnCnsdrEnum.POLICY),
	POLICY_5(0.6, 5, RetnCnsdrEnum.POLICY),
	POLICY_6(0.1, 6, RetnCnsdrEnum.POLICY),
	POLICY_7(0.1, 7, RetnCnsdrEnum.POLICY),

	/**상위고려사항, ENUM은 더미*/
	UPPER(0.6, -1, RetnCnsdrEnum.POLICY),
	/**중위고려사항 ENUM은 더미*/
	MIDDLE(0.3, -1, RetnCnsdrEnum.POLICY),
	/**하위고려사항 ENUM은 더미*/
	LOWER(0.1, -1, RetnCnsdrEnum.POLICY)
	;


	private RetnCnsdrEnum cnsdrEnum;
	private int index;
	private double wghtVal;


	RetnWghtValEnum(double wghtVal, int index, RetnCnsdrEnum cnsdrEnum){
		this.wghtVal = wghtVal;
		this.index = index;
		this.cnsdrEnum = cnsdrEnum;
	}

	public String getScoreStr() {
		return this.cnsdrEnum.getKey();
	}

	public String getCnsdrDesc() {
		return this.cnsdrEnum.getDesc();
	}

	public double getWghtVal() {
		return this.wghtVal;
	}

	public int getIndex() {
		return this.index;
	}


	public static Map<String, Object> getsCnsdrDesc(int index) {
		Map<String, Object> result = new EntityMap();
		List<String> upperCnsdrDesc = new ArrayList<>();
		List<String> middleCnsdrDesc = new ArrayList<>();
		List<String> lowerCnsdrDesc = new ArrayList<>();

		for(RetnWghtValEnum thisEnum : RetnWghtValEnum.values()) {
			if(thisEnum.getIndex() == index) { // 인덱스가 같고

				if(thisEnum.getWghtVal() == 0.6) { // 상위 고려 사항이면
					upperCnsdrDesc.add(thisEnum.getCnsdrDesc());
				}
				else if(thisEnum.getWghtVal() == 0.3) { // 중위 고려 사항이면
					middleCnsdrDesc.add(thisEnum.getCnsdrDesc());
				}
				else if(thisEnum.getWghtVal() == 0.1) { // 하위 고려 사항이면
					lowerCnsdrDesc.add(thisEnum.getCnsdrDesc());
				}

			}
		};

		result.put("upperCnsdrDesc", upperCnsdrDesc);
		result.put("middleCnsdrDesc", middleCnsdrDesc);
		result.put("lowerCnsdrDesc", lowerCnsdrDesc);

		return result;
	}

	/**가중치를 가져온다.*/
	public static double getWghtVal(String str, int index) {
		double result = 0L;
		for(RetnWghtValEnum thisEnum : RetnWghtValEnum.values()) {
			if(thisEnum.getScoreStr().equals(str)&& thisEnum.getIndex() == index) {
				result = thisEnum.getWghtVal();
			}
		};
		return result;
	}
}
