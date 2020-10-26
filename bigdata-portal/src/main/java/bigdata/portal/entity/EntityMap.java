package bigdata.portal.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.text.WordUtils;

import com.google.common.base.CaseFormat;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityMap extends ListOrderedMap<String, Object> {
	private static final long serialVersionUID = 4749128055936967884L;

	/**
	 * XML 데이터 리턴
	 *
	 * @return
	 * @throws Exception
	 */
	@XmlAnyElement
	@SuppressWarnings({ "rawtypes" })
	public List<JAXBElement> getItem() throws Exception {
		return marshal(this);
	}

	/**
	 * Map형태의 데이터를 XML로 변환
	 *
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<JAXBElement> marshal(Map<String, Object> m) throws Exception {
		List elements = new ArrayList();
		for (Map.Entry<String, Object> property : m.entrySet()) {
			if (property.getValue() instanceof EntityMap) {
				elements.add(new JAXBElement(new QName(getCleanLabel(property.getKey())), EntityMap.class, property.getValue()));
			} else if (property.getValue() instanceof Map) {
				EntityMap map = new EntityMap();
				map.putAll((Map) property.getValue());
				elements.add(new JAXBElement(new QName(getCleanLabel(property.getKey())), EntityMap.class, map));
			} else {
				elements.add(new JAXBElement<String>(new QName(getCleanLabel(property.getKey())), String.class, property.getValue().toString()));
			}
		}

		return elements;
	}

	/**
	 * XML Attribute Label 문자열 정리
	 *
	 * @param attributeLabel
	 * @return
	 */
	protected String getCleanLabel(String attributeLabel) {
		String aLabel = attributeLabel;
		aLabel = aLabel.replaceAll("[()]", "").replaceAll("[^\\w\\s]", "_").replaceAll(" ", "_").toUpperCase();
		aLabel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, aLabel);
		return aLabel;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.collections4.map.ListOrderedMap#put(java.lang.Object,
	 * java.lang.Object)
	 * 키를 camelcase로 변경하여 입력
	 */
	@Override
	public Object put(String key, Object value) {
		return super.put(camelCase(key), value);
	}

	/**
	 * 키를 camelcase로 변경하지 않고 그대로 입력
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object putNor(String key, Object value) {
		return super.put(key, value);
	}

	/**
	 * get value (Object to Integer)
	 *
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		Object object = super.get(key);
		if (object == null)
			return null;

		if (object instanceof Number) {
			return ((Number) object).intValue();
		} else if (object instanceof String) {
			return Integer.parseInt((String) object);
		}

		return 0;
	}

	/**
	 * get value (Object to Double)
	 *
	 * @param key
	 * @return
	 */
	public Double getDouble(String key) {
		Object object = super.get(key);
		if (object == null)
			return null;

		if (object instanceof Number) {
			return ((Number) object).doubleValue();
		} else if (object instanceof String) {
			return Double.parseDouble((String) object);
		}

		return 0D;
	}

	/**
	 * get value (Object to Float)
	 *
	 * @param key
	 * @return
	 */
	public Float getFloat(String key) {
		Object object = super.get(key);
		if (object == null)
			return null;

		if (object instanceof Number) {
			return ((Number) object).floatValue();
		} else if (object instanceof String) {
			return Float.parseFloat((String) object);
		}

		return 0F;
	}

	/**
	 * get value (Object to Long)
	 *
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		Object object = super.get(key);
		if (object == null)
			return null;

		if (object instanceof Number) {
			return ((Number) object).longValue();
		} else if (object instanceof String) {
			return Long.parseLong((String) object);
		}

		return 0L;
	}

	/**
	 * get value (Object to String)
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Object object = super.get(key);
		if (object == null)
			return null;

		if (object instanceof String) {
			return (String) object;
		} else {
			return new StringBuilder().append(object).toString();
		}
	}

	/**
	 * get value (Object to String)
	 *
	 * @param key
	 * @return
	 */
	public String getStringNulltoEmpty(String key) {
		Object object = super.get(key);
		if (object == null)
			return ""; // null이면 문자열로 반환

		if (object instanceof String) {
			return (String) object;
		} else {
			return new StringBuilder().append(object).toString();
		}
	}

	/**
	 * 대문자로 문자열 반환
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getStringToUpperCase(String key, String defaultValue) {
		Object object = super.get(key);

		if (object == null) {
			return defaultValue.toUpperCase();
		}

		if (object instanceof String) {
			return ((String) object).toUpperCase();
		} else {
			return new StringBuilder().append(object).toString().toUpperCase();
		}
	}

	/**
	 * 소문자로 문자열 반환
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getStringToLowerCase(String key, String defaultValue) {
		Object object = super.get(key);

		if (object == null) {
			return defaultValue.toUpperCase();
		}

		if (object instanceof String) {
			return ((String) object).toLowerCase();
		} else {
			return new StringBuilder().append(object).toString().toLowerCase();
		}
	}

	/**
	 * 문자열을 CamelCase로 변형
	 * 이미 CamelCase로 표기된 경우 그대로 표시
	 *
	 * @param string
	 * @return
	 */
	public static String camelCase(String string) {
		// Test1_Camel_Case -> test1CamelCase
		// test_Camel_Case -> testCamelCase
		// test_camel_case -> testCamelCase
		// tEST_c_Amel_case -> testCAmelCase
		// TEST_CAMEL_CASE -> testCamelCase
		String ret = string;
		ret = ret.replaceAll("([0-9a-z])([A-Z][0-9a-z])", "$1_$2");
		ret = WordUtils.capitalizeFully(" " + ret.trim(), '_').replaceAll("_", "").trim();
		return ret;
	}

	/**
	 * 첫문자가 대문자로 시작하는 CamelCase로 변형
	 *
	 * @param string
	 * @return
	 */
	public static String pascalCase(String string) {
		// Test1_Camel_Case -> Test1CamelCase
		// test_Camel_Case -> TestCamelCase
		// test_camel_case -> TestCamelCase
		// tEST_c_Amel_case -> TestCAmelCase
		// TEST_CAMEL_CASE -> TestCamelCase
		String ret = string;
		ret = ret.replaceAll("([0-9a-z])([A-Z][0-9a-z])", "$1_$2");
		ret = WordUtils.capitalizeFully(ret.trim(), '_').replaceAll("_", "").trim();
		return ret;
	}

	/**
	 * key를 CamelCase문자열을 컬럼명으로 변경하여 리턴
	 * @return
	 */
	public List<String> getColumns() {
		// camelCase -> CAMEL_CASE
		List<String> key = this.keyList();
		for(int i = 0; i < key.size(); i++) {
			String k = key.get(i);
			k = k.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toUpperCase();
			key.set(i, k);
		}

		return key;
	}
}