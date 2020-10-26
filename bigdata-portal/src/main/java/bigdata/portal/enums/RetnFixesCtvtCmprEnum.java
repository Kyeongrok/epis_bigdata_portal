package bigdata.portal.enums;

import java.util.Comparator;
import java.util.Map;

public enum RetnFixesCtvtCmprEnum{

	INDEX1_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index1")+"");
			double v2 = Double.parseDouble( o2.get("index1")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	INDEX1_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index1")+"");
			double v2 = Double.parseDouble( o2.get("index1")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	INDEX2_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index2")+"");
			double v2 = Double.parseDouble( o2.get("index2")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	INDEX2_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index2")+"");
			double v2 = Double.parseDouble( o2.get("index@")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),
	INDEX3_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index3")+"");
			double v2 = Double.parseDouble( o2.get("index3")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),

	INDEX3_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("index3")+"");
			double v2 = Double.parseDouble( o2.get("index3")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),

	TOTAL_CORE_ASC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("totalScore")+"");
			double v2 = Double.parseDouble( o2.get("totalScore")+"");
			if(v1 > v2) return 1;
			else if (v1 < v2) return -1;
			else return 0;
		}
	}),
	TOTAL_CORE_DESC(new Comparator<Map>() {
		public int compare(Map o1, Map o2) {
			double v1 = Double.parseDouble( o1.get("totalScore")+"");
			double v2 = Double.parseDouble( o2.get("totalScore")+"");
			if(v1 > v2) return -1;
			else if (v1 < v2) return 1;
			else return 0;
		}
	}),



	;

	private Comparator<? extends Map> comparator;

	RetnFixesCtvtCmprEnum(Comparator<? extends Map> comparator){
		this.comparator = comparator;
	}

	public Comparator getComparator() {
		return this.comparator;
	}


}
