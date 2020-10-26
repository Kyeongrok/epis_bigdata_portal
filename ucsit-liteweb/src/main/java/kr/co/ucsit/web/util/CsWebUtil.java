/**
 * @author	cs1492
 * @since	2017. 9. 1.
 */
package kr.co.ucsit.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpRetryException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import kr.co.ucsit.core.CsConst;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;

/**
 * 웹 관련 유티리티
 * @author	cs1492
 * @since	2017. 10. 27.
 */
public class CsWebUtil extends CsUtil{
	static final Logger LOGGER = LoggerFactory.getLogger(CsWebUtil.class);
	
	public static void main(String[] args) {}
	
	
	public static String nl2br(String str) {
		if(null == str) {
			return "";
		}
		
		return str.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
	}
	
	
	/**
	 * 
	 * 화면 목록에 표시하는 역순의 번호 생성
	 * @date : 2018. 2. 9.
	 * @param csMap
	 * @param totcnt	전체 건수
	 * @param count	현재 row의 번호
	 * @return
	 */
	public static String calcRno(CsMap csMap, String totcnt, String count) {
		if(CsUtil.isNull(csMap) || CsUtil.isEmpty(totcnt) || CsUtil.isEmpty(count)) {
			return "0";
		}
		
		return "" + ((Integer.parseInt(totcnt)+1) - ((csMap.getInt("pageNo")-1)*csMap.getInt("pageSize")+Integer.parseInt(count)));
	}
	

	/**
	 * csrf token 검사
	 * @date : 2018. 3. 11.
	 * @param request
	 * @param paramMap
	 * @return
	 */
	public static boolean checkCsrf(HttpServletRequest request, CsMap paramMap) {
//		System.out.println(CsWebSessionUtil.get(request, CsConst.CSRF_TOKEN));
//		System.out.println(paramMap.get(CsConst.CSRF_TOKEN));
		
		String csrfFromSession = CsWebSessionUtil.<String>getT(request, CsConst.CSRF_TOKEN);
		String csrfFromParam = paramMap.<String>getT(CsConst.CSRF_TOKEN);
		
		//
		return CsUtil.isAllEquals(csrfFromSession, csrfFromParam);
	}
	
	
	
	
	public static boolean contains(List<Map<String,Object>> listOfMap, String key, Object v){
		if(isNull(listOfMap) || isEmpty(key) || isNull(v)){
			return false;
		}
		
		for(Map<String,Object> map : listOfMap){
			
		}
		
		
		return true;
	}
	
	
	
	/**
	 * 메시지 맵 생성 
	 * @param code
	 * @param message
	 * @return
	 */
	public static Map<String,Object> createMessageMap(String code, String message){
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("code", code);
		map.put("message", message);
		
		return map;
	}
	
	
	public static CsMap getParamMap(HttpServletRequest request) {
		if(null == request.getAttribute(CsConst.PARAM_MAP)) {
			return null;
		}
		
		return (CsMap)request.getAttribute(CsConst.PARAM_MAP);
	}
	
	
	/**
	 * CsTransferObject에 저장된 데이터 1개 리턴
	 * @date : 2018. 2. 2.
	 * @param trans
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getData(CsTransferObject trans){
		if(null == trans) {
			return null;
		}
		
		return (Map<String, Object>) trans.get(CsWebConst.DATA);
	}
	
	/**
	 * generic 사용
	 * @param trans
	 * @param key	map's key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getData(CsTransferObject trans, String key){
		Map<String,Object> data = getData(trans);
		if(null == data) {
			return null;
		}
		
		if(!data.containsKey(key))	{
			return null;
		}
		
		return (T)data.get(key);
	}
	
	/**
	 * CsTransferObject에 저장된 데이터 목록 리턴
	 * @date : 2018. 2. 2.
	 * @param trans
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getDatas(CsTransferObject trans){
		if(null == trans) {
			return null;
		}
		
		return (List<Map<String, Object>>) trans.get(CsWebConst.DATAS);
	}
	
	
	
	/**
	 * map에서 키가 keys에 속하면해당하는 놈들의 값을 value로 put함
	 * @param map
	 * @param value
	 * @param keys
	 */
	public static void putValueIfIn(Map<String,Object> map, Object value, String...keys){
		if(null == map){
			return;
		}
		
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String keyOfMap = iter.next();
			
			for(String key : keys){
				if(keyOfMap.equals(key)){
					map.put(keyOfMap, value);
				}
			}
		}
	}
	
	/**
	 * map의 key가 keys에 속하지 않으면 value로 put함
	 * @param map
	 * @param value
	 * @param keys
	 */
	public static void putValueIfNotIn(Map<String,Object> map, Object value, String...keys){
		if(null == map){
			return;
		}
		
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String keyOfMap = iter.next();
			
			boolean b = false;
			for(String key : keys){
				if(keyOfMap.equals(key)){
					b = true;
					break;
				}
			}
			
			if(!b){
				map.put(keyOfMap, value);
			}
		}
	}
	
	/**
	 * map 목록을  json 문자열로 변환. fwk:select, fwk:radio등의 jsonString 값 세팅을 위해 사용될 수 있음
	 * @date : 2018. 2. 8.
	 * @param datas
	 * @param t	텍스트 값을 가지고 있는 맵의 키
	 * @param v	값을 가지고 있는 맵의 키
	 * @return
	 */
	public static String toJsonString(List<Map<String,Object>> datas, String t, String v) {
		
		if(null == datas || 0 == datas.size()) {
			return "[]";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		for(int i=0; i<datas.size(); i++) {
			if(0 != i) {
				sb.append(",");
			}
			
			sb.append("{t:'"+datas.get(i).get(t)+"',v:'"+datas.get(i).get(v)+"'}");
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	public static CsMap toParamMap(HttpServletRequest request)  {
		if(request.getRequestURI().contains(".json")) {
			//.json 요청이면
			if(null != request.getQueryString()) {
				//파라미터가 get/post방식으로 전달되었으면
				return toParameterMap(request);
			}else {
				try {
					return jsonToParamMap(request);
				} catch (IOException e) {
					LOGGER.error("{}",e);
					return new CsMap();
				}
			}
		}else {
			//.do 요청이면
			return toParameterMap(request);
		}
	}
	
	/**
	 * 
	 * @date : 2018. 2. 2.
	 * @param request
	 * @return
	 */
	private static CsMap toParameterMap(HttpServletRequest request) {
		LOGGER.trace(".toParameterMap");
		
		CsMap paramMap = new CsMap();
		
		Enumeration<String> e = request.getAttributeNames();
		String k;
		
		while(e.hasMoreElements()) {
			k = e.nextElement();
			
			if(null == k) {
				continue;
			}
			
			if(30 < k.length()) {
				continue;
			}
		
			paramMap.put(k, request.getAttribute(k));
		}
		
		//
		e = request.getParameterNames();
		
		while(e.hasMoreElements()) {
			k = e.nextElement();
			
			if(null == k) {
				continue;
			}
			
			if(30 < k.length()) {
				continue;
			}
			
			if(1 == request.getParameterValues(k).length) {
				paramMap.put(k, request.getParameter(k));
			}else {
				paramMap.put(k, request.getParameterValues(k));
			}
		}
		
		return paramMap;
	}
	
	
	/**
	 * payload body를 CsMap에 put함
	 * @date : 2018. 2. 2.
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static CsMap jsonToParamMap(HttpServletRequest request) throws IOException {
		LOGGER.trace(".jsonToParameterMap");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line="";
		StringBuffer sb = new StringBuffer();
		
		while(null != (line = br.readLine())) {
			if(0 == line.length()) {
				continue;
			}
			
			sb.append(line);
		}
		
		String str = sb.toString();
		
		if(!str.startsWith("{")) {
			return null;
		}
		
		if(!str.endsWith("}")) {
			return null;
		}
		
		//
		CsMap paramMap = (new Gson()).fromJson(str, CsMap.class);
		request.setAttribute(CsConst.PARAM_MAP, paramMap);
		
		return paramMap;
	}
	
	
	
	/**
	 * response에 jsonp string write할 때 사용
	 * @param pw	보통 response의 print writer
	 * @param jsonp	jsonp의 키
	 * @param map	write할 데이터를 저장하고 있는 맵
	 */
	public static void writeJsonpString(PrintWriter pw, String jsonp, Map<String,Object> map){
		if(null == pw || isEmpty(jsonp) || null == map){
			return;
		}
		
		String s = String.format("%s(%s)", jsonp, (new Gson()).toJson(map));
		
		pw.write(s);
		pw.flush();
	}
}
