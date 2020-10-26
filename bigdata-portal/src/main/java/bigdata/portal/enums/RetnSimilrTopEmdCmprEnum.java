package bigdata.portal.enums;

import java.util.Comparator;
import java.util.Map;

/**
 * 유사 귀농인 상위 50개 읍면동 지역 + 분야별 평균 데이터에서
 * 분야별로 정렬
 * */
public enum RetnSimilrTopEmdCmprEnum {

	/**교육 접근성 평균 데이터 오름차순*/
	EDC_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("edcAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("edcAvgAccesPosblty")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**교육 접근성 평균 데이터 내림차순*/
	EDC_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("edcAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("edcAvgAccesPosblty")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**교육 접근성 점수 오름차순*/
	EDC_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("edcAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("edcAvgAccesPosbltyScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	/**교육 접근성 점수 데이터 내림차순*/
	EDC_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("edcAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("edcAvgAccesPosbltyScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**교통 접근성 평균데이터 오름차순*/
	TRNSPORT_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("trnsportAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("trnsportAvgAccesPosblty")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	/**교통 접근성 평균데이터 내림차순*/
	TRNSPORT_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("trnsportAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("trnsportAvgAccesPosblty")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**교통 접근성 점수 오름차순*/
	TRNSPORT_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("trnsportAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("trnsportAvgAccesPosbltyScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	/**교통 접근성 점수 내림차순*/
	TRNSPORT_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("trnsportAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("trnsportAvgAccesPosbltyScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**병원 접근성 평균데이터 오름차순*/
	HSPTL_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("hsptlAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("hsptlAvgAccesPosblty")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**병원 접근성 평균데이터 내림차순*/
	HSPTL_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("hsptlAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("hsptlAvgAccesPosblty")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**병원 접근성 점수 오름차순*/
	HSPTL_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("hsptlAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("hsptlAvgAccesPosbltyScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	/**병원 접근성 점수 내림차순*/
	HSPTL_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("hsptlAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("hsptlAvgAccesPosbltyScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**편의-마트 접근성 평균데이터 오름차순*/
	CNVNC_MRKT_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncMrktAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("cnvncMrktAvgAccesPosblty")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**편의-마트 접근성 평균데이터 내림차순*/
	CNVNC_MRKT_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncMrktAvgAccesPosblty")+"");
			double v2= Double.parseDouble( o2.get("cnvncMrktAvgAccesPosblty")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**편의-마트 접근성 점수 오름차순*/
	CNVNC_MRKT_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncMrktAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("cnvncMrktAvgAccesPosbltyScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**편의-마트 접근성 점수 내림차순*/
	CNVNC_MRKT_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncMrktAvgAccesPosbltyScore")+"");
			double v2= Double.parseDouble( o2.get("cnvncMrktAvgAccesPosbltyScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**토지 실거래 평균 금액 오름차순*/
	LAD_DELNG_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgDelngAmount")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgDelngAmount")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**토지 실거래 평균 금액 내림차순*/
	LAD_DELNG_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgDelngAmount")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgDelngAmount")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**토지 실거래 점수오름차순*/
	LAD_DELNG_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgDelngAmountScore")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgDelngAmountScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**토지 실거래 점수 내림차순*/
	LAD_DELNG_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgDelngAmountScore")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgDelngAmountScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**토지 임대 금액 오름차순*/
	LAD_RENT_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgRentAmount")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgRentAmount")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**토지 임대 금액 내림차순*/
	LAD_RENT_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("ladAvrgRentAmount")+"");
			double v2= Double.parseDouble( o2.get("ladAvrgRentAmount")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/*유통시설 수 오름차순*/
	DISTB_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("distbAvgCo")+"");
			double v2= Double.parseDouble( o2.get("distbAvgCo")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/*유통시설 수 내림차순*/
	DISTB_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("distbAvgCo")+"");
			double v2= Double.parseDouble( o2.get("distbAvgCo")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**편의시설 수 오름차순*/
	CNVNC_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncAvgCo")+"");
			double v2= Double.parseDouble( o2.get("cnvncAvgCo")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**편의시설 수 내림차순*/
	CNVNC_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("cnvncAvgCo")+"");
			double v2= Double.parseDouble( o2.get("cnvncAvgCo")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**문화시설 수 오름차순*/
	CLTUR_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("clturAvgCo")+"");
			double v2= Double.parseDouble( o2.get("clturAvgCo")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**문화시설 수 내림차순*/
	CLTUR_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("clturAvgCo")+"");
			double v2= Double.parseDouble( o2.get("clturAvgCo")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**문화시설 점수 오름차순*/
	CLTUR_SCORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("clturAvgCoscore")+"");
			double v2= Double.parseDouble( o2.get("clturAvgCoscore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**문화시설 점수 내림차순*/
	CLTUR_SCORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("clturAvgCoscore")+"");
			double v2= Double.parseDouble( o2.get("clturAvgCoscore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**학원 수 오름차순*/
	INSTUT_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("instutCo")+"");
			double v2= Double.parseDouble( o2.get("instutCo")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**학원 수 내림차순*/
	INSTUT_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("instutCo")+"");
			double v2= Double.parseDouble( o2.get("instutCo")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	/**귀농정책 수 오름차순*/
	POLICY_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("rtrnFrmhsSportPolicyCo")+"");
			double v2= Double.parseDouble( o2.get("rtrnFrmhsSportPolicyCo")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	/**귀농정책 수 내림차순*/
	POLICY_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("rtrnFrmhsSportPolicyCo")+"");
			double v2= Double.parseDouble( o2.get("rtrnFrmhsSportPolicyCo")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	/**가중치 적용된 점수  내림차순*/
	TOTAL_SCORE_WGHT_VAL_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("totalScoreWghtval")+"");
			double v2= Double.parseDouble( o2.get("totalScoreWghtval")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	;
	private Comparator<? extends Map> comparator;

	RetnSimilrTopEmdCmprEnum(Comparator<? extends Map> comparator){
		this.comparator = comparator;
	}

	public Comparator getComparator() {
		return this.comparator;
	}


}
