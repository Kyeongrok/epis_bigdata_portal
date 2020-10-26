package bigdata.portal.cmm.util;

import java.util.Comparator;
import java.util.Map;

public class ComparePrdDeDesc implements Comparator<Map<String, String>> {
	@Override
	public int compare(Map<String, String> m1, Map<String, String> m2) {
		return m2.get("prdDe").compareTo(m1.get("prdDe"));
	}
}
