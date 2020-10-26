package bigdata.portal.cmm.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bigdata.portal.web.AnalysisController;

/**
 * 빅데이터포털 공통 유틸
 *
 *
 * @author THEIMC KDW
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 11. 28.     KDW          최초 생성
 *      </pre>
 *
 * @since 2018. 11. 28.
 */
public class CommonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 배열 합치기
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static String arrayJoin(String glue, String[] array) {
		String result = "";

		for (int i = 0; i < array.length; i++) {
			result += array[i];
			if (i < array.length - 1) result += glue;
		}
		return result.trim();
	}

	/**
	 * 배열 합치기
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static String arrayJoin(String glue, List<String> array) {
		String result = "";

		for (int i = 0; i < array.size(); i++) {
			result += array.get(i);
			if (i < array.size() - 1) result += glue;
		}
		return result.trim();
	}

	/**
	 * 배열 합치기
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static String arrayJoin(String glue, Iterator<String> ite) {
		String result = "";
		String glue1 = "";
		while (ite.hasNext()) {
			result += glue1;
			result += ite.next();
			glue1 = glue;
		}
		return result.trim();
	}

	/**
	 * 배열을 리스트로
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static <T> List<T> arrayToList(Object[] array, Class<T> cla) {
		List<T> list = new ArrayList<T>();

		if (array == null || array.length == 0) return list;

		try {
			if (cla.isInstance(array[0])) {
				for (int i = 0; i < array.length; i++) {
					list.add(cla.cast(array[i]));
				}
			}
			return list;
		} catch (Exception e) {
			LOGGER.error("There was an unsupported array operation error");
		}

		return null;
	}

	/**
	 * 배열을 맵으로
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static <K, V> Map<String, V> arrayToMap(Object[] array, Class<? extends V> cla, String keyColumn) {
		return arrayToMap(array, cla, keyColumn, String.class);
	}

	public static <K, V> Map<K, V> arrayToMap(Object[] array, Class<? extends V> cla1, String keyColumn, Class<K> cla2) {
		try {
			if (cla1.getPackage() != null && "java.lang".equals(cla1.getPackage().getName())) {
				throw new CommonException(cla1.getName() + " does not support");
			}

			Map<K, V> map = new HashMap<K, V>();
			if (array == null || array.length == 0) return map;

			if (cla1.getSuperclass() == Map.class) {
				if (cla1.isInstance(array[0])) {
					for (int i = 0; i < array.length; i++) {
						@SuppressWarnings("unchecked")
						HashMap<K, V> method = (HashMap<K, V>) array[i];
						K key = cla2.cast(method.get(keyColumn));
						if (key == null) {
							continue;
						}
						map.put(key, cla1.cast(array[i]));
					}
				}
			} else {
				Field field = cla1.getDeclaredField(keyColumn);
				field.setAccessible(true);

				if (!field.getType().equals(cla2)) {
					return null;
				}

				if (cla1.isInstance(array[0])) {
					for (int i = 0; i < array.length; i++) {
						K key = cla2.cast(field.get(array[i]));
						if (key == null) {
							continue;
						}
						map.put(key, cla1.cast(array[i]));
					}
				}
			}

			return map;
		} catch (Exception e) {
			LOGGER.error("There was an unsupported operation error");
		}

		return null;
	}

	/**
	 * List를 맵으로
	 *
	 * @param glue
	 * @param array
	 * @return
	 */
	public static <K, V> Map<String, V> listToMap(List<? extends V> list, Class<? extends V> cla, String keyColumn) {
		return listToMap(list, cla, keyColumn, String.class);
	}

	public static <K, V> Map<K, V> listToMap(List<? extends V> list, Class<? extends V> cla1, String keyColumn, Class<K> cla2) {
		@SuppressWarnings("unchecked")
		V[] array = (V[]) Array.newInstance(cla1, list.size());
		list.toArray(array);
		return arrayToMap(array, cla1, keyColumn, cla2);
	}

	public static <T> List<? extends T> mapToList(Map<String, ? extends T> map, Class<T> cla) {
		ArrayList<T> values = new ArrayList<T>();
		for (T tmp : map.values()) {
			values.add(tmp);
		}
		return values;
	}

	public static <K, V> List<Map<K, V>> mapToList(Map<String, Map<K, V>> map, Class<K> cla1, Class<V> cla2) {
		List<Map<K, V>> values = new ArrayList<Map<K, V>>();
		for (Map<K, V> tmp : map.values()) {
			values.add(tmp);
		}
		return values;
	}

	public static String cutac(String str, int sindex, int eindex) {
		String retStr = str;
		if (retStr != null) {
			if (retStr.length() >= sindex && retStr.length() >= eindex && sindex <= eindex) retStr = retStr.substring(sindex, eindex);
			if (retStr.length() >= eindex) return (new StringBuilder(String.valueOf(retStr))).append("..").toString();
			else return retStr;
		} else {
			return retStr;
		}
	}

	// List collection 요소 중복 제거후 List에 순차적으로 반환
	public static ArrayList<String> collectionListRemoveDuplicate(List<String> list) {
	    ArrayList<String> result = new ArrayList<>();
	    HashSet<String> set = new HashSet<>();
	    for (String item : list) {
	      if (!set.contains(item)) {
	        result.add(item);
	        set.add(item);
	      }
	    }
	    return result;
	}

	// byte를 단위로 표시
	public static String byteCalculation(String bytes) {
        String retFormat = "0";
       Double size = Double.parseDouble(bytes);

        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };


        if (bytes != "0") {
              int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
              DecimalFormat df = new DecimalFormat("#,###.##");
              double ret = ((size / Math.pow(1024, Math.floor(idx))));
              retFormat = df.format(ret) + " " + s[idx];
         } else {
              retFormat += " " + s[0];
         }

         return retFormat;
	}


	/**
	 * 문자열 XSS 공격을 막기 위해 javaScript 및 html과 관련된 특정 문자를 escape한다.
	 * 동적으로 DB 조건에 값을 넣어야 할때에도 escapte한다.
	 * 예) "&"=>"&amp;"
	 * @param str : 사용할 문자열
	 */
	public static String escape(String str) {
		if(str == null) {
			return null;
		}

		StringBuffer escapedStr = new StringBuffer();
		char[] ch = str.toCharArray();
		int charSize = ch.length;
		for(int i = 0; i < charSize; i++) {
			if(ch[i] == '&') {
				escapedStr.append("&amp;");
			} else if(ch[i] == '<') {
				escapedStr.append("&lt;");
			} else if(ch[i] == '>') {
				escapedStr.append("&gt;");
			} else if(ch[i] == '"') {
				escapedStr.append("&quot;");
			} else if(ch[i] == '\'') {
				escapedStr.append("&#039;");
			} else {
				escapedStr.append(ch[i]);
			}
		}

		return escapedStr.toString();
	}


	/**
     * Object type 변수가 비어있는지 체크
     *
     * @param obj
     * @return Boolean : true / false
     */
    public static Boolean isEmpty(Object obj) {
        if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
        else if (obj instanceof List) return obj == null || ((List<?>) obj).isEmpty();
        else if (obj instanceof Map) return obj == null || ((Map<?, ?>) obj).isEmpty();
        else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
        else return obj == null;
    }

    /**
     * Object type 변수가 비어있지 않은지 체크
     *
     * @param obj
     * @return Boolean : true / false
     */
    public static Boolean isNotEmpty(Object obj) {
    	 return !isEmpty(obj);
    }
}
