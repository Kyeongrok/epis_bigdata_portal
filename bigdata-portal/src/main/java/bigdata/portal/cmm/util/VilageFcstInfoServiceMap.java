package bigdata.portal.cmm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import kr.co.ucsit.core.CsUtil;

/**
 * 기상청 API 호출 결과 저장 맵
 * @author jae0ha
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class VilageFcstInfoServiceMap extends HashMap {

	private static final Logger LOG = LoggerFactory.getLogger(VilageFcstInfoServiceMap.class);

	/**
	 * 원본 문자열
	 */
	public static final String KEY_RAW_STRING = "rawstring";

	/**
	 * response
	 */
	public static final String RESPONSE = "response";

	/**
	 * data
	 */
	public static final String KEY_DATA = "data";

	/**
	 * header
	 */
	public static final String HEADER = "header";

	/**
	 * body
	 */
	public static final String BODY = "body";

	/**
	 * items
	 */
	public static final String ITEMS = "items";

	/**
	 * item
	 */
	public static final String ITEM = "item";

	/**
	 * _sources 목록
	 */
	public static final String KEY_SOURCES = "sources";
	/**
	 * _source의 키 목록
	 */
	public static final String KEY_KEYS = "keys";
	/**
	 * vo 목록
	 */
	public static final String KEY_VOS = "vos";

	@SuppressWarnings("unchecked")
	public VilageFcstInfoServiceMap(String jsonString) throws InstantiationException, IllegalAccessException {
		super();

		if(CsUtil.isEmpty(jsonString)) {
			LOG.info("<<.VilageFcstInfoServiceMap - empty jsonString");
			return;
		}

		if(!jsonString.trim().startsWith("{")) {
			LOG.info("<<.VilageFcstInfoServiceMap - not jsonString:{}", jsonString);
			return;
		}

		putRawString(jsonString);

		putData((String)this.get(KEY_RAW_STRING));

		putItems((Map<String,Object>)this.get(KEY_DATA));
	}

	@SuppressWarnings("unchecked")
	private void putRawString(String jsonString) {
		this.put(KEY_RAW_STRING, jsonString);
	}

	@SuppressWarnings("unchecked")
	private void putData(String jsonString) {
		Map<String,Object> dataMap = new Gson().fromJson(jsonString, Map.class);

		//
		this.put(KEY_DATA, dataMap);
	}

	@SuppressWarnings("unchecked")
	private void putItems(Map<String, Object> dataMap) {

		if(CsUtil.isEmpty(dataMap) || !dataMap.containsKey(RESPONSE)) {
			this.put(RESPONSE, new ArrayList<Map<String,Object>>());
			return;
		}

		Map<String,Object> responseMap = (Map<String,Object>)dataMap.get(RESPONSE);
		if(responseMap == null || responseMap.isEmpty()) {
			return;
		}
		Map<String,Object> bodyMap = (Map<String,Object>)responseMap.get(BODY);
		if(bodyMap == null || bodyMap.isEmpty()) {
			return;
		}
		Map<String,Object> itemsMap = (Map<String,Object>)bodyMap.get(ITEMS);
		if(itemsMap == null || itemsMap.isEmpty()) {
			return;
		}
		List<Map<String,Object>> listItemMap = (List<Map<String,Object>>)itemsMap.get(ITEM);

		//
		this.put(ITEMS, listItemMap);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getItems() {
		return (List<Map<String,Object>>)this.get(ITEMS);
	}

}
