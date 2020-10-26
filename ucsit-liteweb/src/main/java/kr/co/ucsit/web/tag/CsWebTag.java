/**
 * 
 */
package kr.co.ucsit.web.tag;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.core.CsVO;


/**
 * 모든 커스텀 태그의 부모
 * @author cs1492
 *
 */
public abstract class CsWebTag  extends SimpleTagSupport {
	
	static final Logger LOGGER = LoggerFactory.getLogger(CsWebTag.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final String _T = "t";
	protected static final String _V = "v";

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private String id;
	
	private String name;
	
	private String css;
	
	private String valid;
	
	private String title;
	
	private String headerValue;
	
	private String headerText = null;
	
	private String selectedValue;
	
	private String selectedText;
	
	private String afterJs;
	
	/**
	 * select onchange 이벤트 발생시 호출한 js 함수
	 */
	private String onChangeJs;
	
	/**
	 * 데이터
	 * key's key : t, value's key : v
	 * json 문자열 형식의 데이터. key's key:k, value's key:v
	 * ex) [{t:'',v:''},{t:'', v:''},...]
	 */
	private Object dataSource=null;

	/**
	 * maps에서 text에 해당하는 놈의 키
	 */
	private String tKey = null;

	

	/**
	 * maps에서 value에 해당하는 놈의 키
	 */
	private String vKey = null;
	

	public abstract void doTag() throws JspException,IOException;
	
	
	public String getAfterJs() {
		return afterJs;
	}




	public String getCss() {
		return css;
	}
	

	/**
	 * 그룹코드 아이디로부터  상세코드 목록 조회 후 데이터 추출
	 * @param code 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private <T extends Map> List<T> getDataFromCode(String code){
		throw new RuntimeException("not impl");
	}
	
	
	/**
	 * json string으로부터 데이터 추출
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getDataFromJsonString(String jsonString){
		LOGGER.info("<<.getDataFromJsonString");
		return (new Gson()).fromJson(jsonString, List.class);
	}
	


	/**
	 * 리스트로부터 데이터 추출
	 * @param dataSource2 
	 * @date : 2018. 2. 22.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends Map> List<T> getDataFromListOfMap(List<T> listOfMap){
		if(CsUtil.isEmpty(listOfMap)) {
			return listOfMap;
		}
		
		
		//
		if(CsUtil.isEmpty(tKey) || CsUtil.isEmpty(vKey)){
			throw new RuntimeException("tKey or vKey is empty");
		}
		
		//tKey,vKey가 setting된 경우...
		if((null != tKey) && (null != vKey)) {
			for(T map : listOfMap) {
				map.put(_T, map.get(tKey));
				map.put(_V, map.get(vKey));
			}
		}
		
		//
		LOGGER.info("<<.getDataFromList - {}", listOfMap.size());
		return listOfMap;
	}

	/**
	 * vo 리스트이면 map 리스트로 변환 후 리턴
	 * @param <T>
	 * @param listOfVo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends Map> List<T> getDataFromListOfVo(List listOfVo) {
		
		//
		List<T> listOfMap = new ArrayList<T>();
		
		//
		if(CsUtil.isEmpty(listOfVo)) {
			return new ArrayList<T>();
		}
		
		
		try {
			//vo 리스트를 map 리스트으로 변환
			for(Object o : listOfVo) {
				Field[] fields = o.getClass().getDeclaredFields();
				
				Map map = new HashMap();
				listOfMap.add((T)map);
				
				//
				for(Field f : fields) {
					f.setAccessible(true);
					
					//
					map.put(f.getName(), f.get(o));
				}
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error("{}",e);
			return listOfMap;
		}
		
		//
		return (List<T>) getDataFromListOfMap(listOfMap);
	}

	/**
	 * 여러 데이터 소스에서 할당된 데이터 조회하여 리턴
	 * @date : 2018. 2. 7.
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T extends Map> List<T> getDatas(){
		LOGGER.info(">>.getDatas - dataSource:{}", (null != dataSource?dataSource.getClass():"null"));
		
		if(null == dataSource) {
			return new ArrayList<T>();
		}
		
		//
		if(dataSource instanceof Iterable) {
			if(CsUtil.isEmpty(((List)dataSource))){
				return new ArrayList<T>();				
			}
			
			//
			Object element = ((List)dataSource).get(0);
			
			if(element instanceof Map) {
				return getDataFromListOfMap((List)dataSource);
			}else if(element instanceof CsVO) {
				return getDataFromListOfVo((List)dataSource);
			}
			
			//
			return new ArrayList<T>();
		}
		
		//
		if(String.class == dataSource.getClass()) {
			String s = (String)dataSource;
			
			//
			if(s.trim().startsWith("[") && s.trim().endsWith("]")) {
				return getDataFromJsonString((String)dataSource);
			}
			
			//
			if(s.trim().toLowerCase().startsWith("code;") || s.trim().startsWith("GC_")) {
				return getDataFromCode((String)dataSource);
			}
		}
		
		//
		return new ArrayList<T>();
	}

	public Object getDataSource() {
		return dataSource;
	}

	public String getHeaderText() {
		return headerText;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOnChangeJs() {
		return onChangeJs;
	}

	public String getSelectedText() {
		return selectedText;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	
	public String getTitle() {
		return title;
	}

	public String gettKey() {
		return tKey;
	}

	public String getValid() {
		return valid;
	}

	public String getvKey() {
		return vKey;
	}

	public void setAfterJs(String afterJs) {
		this.afterJs = afterJs;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setDataSource(Object dataSource) {
		this.dataSource = dataSource;
		
	}

	public void setHeaderText(String headerText) {
		if("ALL".equals(headerText)) {
			this.headerText = "전체";
		}
		
		else if("SEL".equals(headerText)) {
			this.headerText = "선택";

		}else {
			this.headerText = headerText;
		}
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setOnChangeJs(String onChangeJs) {
		this.onChangeJs = onChangeJs;
	}

	public void setSelectedText(String selectedText) {
		this.selectedText = selectedText;
	}


	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void settKey(String tKey) {
		this.tKey = tKey;
	}


	public void setValid(String valid) {
		this.valid = valid;
	}


	public void setvKey(String vKey) {
		this.vKey = vKey;
	}
}
