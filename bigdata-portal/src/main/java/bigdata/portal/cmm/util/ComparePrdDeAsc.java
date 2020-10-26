package bigdata.portal.cmm.util;

import java.util.Comparator;
import java.util.Map;

public class ComparePrdDeAsc implements Comparator<Map<String, String>> {
	@Override
	public int compare(Map<String, String> m1, Map<String, String> m2) {
		return m1.get("prdDe").compareTo(m2.get("prdDe"));
	}
}
