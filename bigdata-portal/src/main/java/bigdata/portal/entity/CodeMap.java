package bigdata.portal.entity;

import java.util.HashMap;
import java.util.Map;

public class CodeMap {
	private Map<String, Code> codeMap = new HashMap<String, Code>();

	public Map<String, Code> getCodeMap() {
		return codeMap;
	}

	public void setCodeMap(Map<String, Code> codeMap) {
		this.codeMap = codeMap;
	}

	public void put(String key, Code value) {
		this.codeMap.put(key, value);
	}

	public Code get(String key) {
		return this.codeMap.get(key);
	}
		
	public CodeDetail get(String key, String subKey) {
		return this.get(key).getCodeDetail().get(subKey);
	}
	
	public boolean containsKey(String key) {
		return this.codeMap.containsKey(key);
	}
	
	public boolean containsKey(String key, String subKey) {
		return this.containsKey(key) ? this.get(key).getCodeDetail().containsKey(subKey) : false;
	}
}
