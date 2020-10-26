/**
 * 
 */
package kr.co.ucsit.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 유틸리티 클래스
 * @author cs1492
 * @date   2018. 3. 16.
 *
 */
public class CsUtil {
	
	private static final String FORCEDOWN = CsConst.FORCE_DOWN;
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CsUtil.class);
	
	
	
	/**
	 * 디렉터리만 추출
	 * @param path
	 * @param outDirs
	 * @return
	 * @history
	 * 	0707	init
	 */
	public static List<Path> bindDirs(Path path, List<Path> outDirs) {
		if(CsUtil.isEmpty(outDirs)) {
			outDirs = new ArrayList<>();
		}
		
		
		Path p = path;
		if(p.toFile().isFile()) {
			p = p.getParent();
		}
		
		//
		File[] files = p.toFile().listFiles();
		for(File f : files) {
			if(f.isDirectory()) {
				outDirs.add(f.toPath());
				bindDirs(f.toPath(), outDirs);
			}
		}
		
		//
		LOGGER.info("<<.bindDirs - {}", outDirs.size());
		return outDirs;
	}
	
	/**
	 * 파일 목록 바인드
	 * @param path
	 * @param outFiles
	 */
	public static void bindFiles(Path path, List<File> outFiles) {
		bindFiles(path.toString(), outFiles);
	}
	
	/**
	 * @param path
	 * @param outFiles
	 */
	public static void bindFiles(String path, List<File> outFiles) {
		bindFiles(path, outFiles, null, Integer.MAX_VALUE);
	}
	
	
	/**
	 * 하위 디렉터리까지 재귀호출하면서 파일 목록 바인드
	 * @param path 경로
	 * @param outFiles 결과를 저장할 리스트
	 * @param filter 바인드할 조건. 경로 또는 파일명에 존재하는 문자열. 와일드 카드(*, ?,...) 사용 불가. 예) .xls 
	 * @param maxCnt 바인드 최대 갯수
	 */
	public static void bindFiles(String path, List<File> outFiles, String filter, int maxCnt) {
		LOGGER.info(">>.bindFiles - path:{}", path);
		
		if(CsUtil.isEmpty(path)) {
			throw new RuntimeException("path is empty");
		}
		
		if(null == outFiles) {
			throw new RuntimeException("outList is null");
		}
		
		//
		File file = new File(path);
		File[] files = file.listFiles();
		if(CsUtil.isEmpty(files)) {
			return;
		}
		
		//
		for(File f : files) {
			if(f.isDirectory()) {
				CsUtil.bindFiles(f.getPath(), outFiles, filter, maxCnt);
			}else {
				//
				if((null != filter) && (!f.getPath().contains(filter))) {
					continue;
				}
				
				//
				outFiles.add(f);
			}
			
			//
			if(maxCnt < outFiles.size()) {
				break;
			}
		}
		
		//
		LOGGER.debug(".bindFiles - {}", outFiles.size());
	}
	
	
	/**
	 * ceil
	 * @param str
	 * @return
	 */
	public static String ceil(String str) {
		return ""+((int)(Double.parseDouble(str) + (1-(Double.parseDouble(str)%1)) % 1));
	}
	
	/**
	 * 영어,숫자 이외의 문자를 다른 문자(x)로 치환
	 * @param str
	 * @return
	 * @history
	 * 	0621	조회 로직 변경. 기존 로직 주석처리
	 */
	public static String clearHangul(String str) {
		String result = str;
		
		char[] chars = result.toCharArray();
		for(char c : chars) {
			if(126 < (int)c) {
				String s = String.valueOf(c);
				
				result = result.replace(s, "x");
			}
		}
		
		//
		return result;
	}
	
	
	/**
	 * 문자열 더하기
	 * @param deli	구분자
	 * @param objects 오브젝트
	 * @return
	 */
	public static String concat(String deli, Object...objects){
		String s="";
		
		boolean b=true;
		for(Object obj : objects){
			if(b) {
				s += (CsUtil.isNull(obj)?"":obj);
				
				b = false;
			}else {
				s += deli + (CsUtil.isNull(obj)?"":obj);
			}
		}
		
		return s;
	}
	

	/**
	 * object형을 리스트로 변환
	 * @param <T>
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> convertToList(Object obj){
		//리스트이면
		if(List.class == obj || ArrayList.class == obj) {
			return (List<T>)obj;
		}
		
		//배열이면
		if(Object[].class == obj) {
			List<T> list = new ArrayList<T>();
			
			//
			Object[] arr = (Object[])obj;
			for(Object o : arr) {
				list.add((T)o);
			}
			//
			return list;
		}
		
		//
		List<T> list = new ArrayList<T>();
		list.add((T)obj);
		return list;
	}
	
	

	
	/**
	 * map을 clazz의 object로 변환하기
	 * @param map
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @since
	 * 	0205	init
	 */
	public static Object convertToObject(Map<String,Object> map, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if(isEmpty(map) || null == clazz) {
			LOGGER.info("<<.convertToObject - empty map or null clazz");
			return null;
		}
		
		//
		Object obj = clazz.newInstance();
		
		String k;
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			
			String methodString = "set" + k.substring(0, 1).toUpperCase() + k.substring(1);
			Method[] methods = obj.getClass().getDeclaredMethods();
			
			//
			for(Method d : methods) {
				if(d.getName().equals(methodString)) {
					try {
						d.invoke(obj, map.get(k));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.error("{}",e);
					}
				}
			}
		}
		
		//
		return obj;
	}
	
	
	/**
	 * @see kr.co.ucsit.core.CsUtil.createFileForcedown(String)
	 * @throws IOException
	 */
	public static void createFileForcedown() throws IOException {
		createFileForcedown("");
	}
	
	
	/**
	 * forcedown 파일 생성
	 * @param postfix TODO
	 * @throws IOException
	 */
	public static void createFileForcedown(String postfix) throws IOException {
		List<Path> paths = getDefaultPaths();
		
		String filename = FORCEDOWN + (CsUtil.isEmpty(postfix) ? "" : postfix);
		
		//
		for(Path p : paths) {
			if(Files.notExists(p.resolve(filename))) {
				LOGGER.debug("+.createFileForcedown - {}", p.resolve(filename));
				Files.createFile(p.resolve(filename));
			}
		}
	}
	
	
	/**
	 * nanotime으로 hash 값 계산 후 리턴
	 * @return
	 */
	public static String createLongUid(){
		return DigestUtils.sha256Hex(""+System.nanoTime());
	}
	
	/**
	 * row 생성
	 * @param row
	 * @param datas 데이터 목록
	 */
	public static void createRow(Row row, String[] datas) {
		for(int i=0; i<datas.length; i++) {
			row.createCell(i).setCellValue(datas[i]);
		}
	}
	
	
	/**
	 * row 생성
	 * @param row
	 * @param keys 데이터 map의 key 목록
	 * @param data 데이터
	 */
	public static void createRow(Row row, String[] keys, Map<String,Object> data) {
		for(int i=0; i<keys.length; i++) {
			row.createCell(i).setCellValue(data.get(keys[i]).toString());
		}
	}
	
	
	/**
	 * workbook에 sheet 생성
	 * @param workbook workbook
	 * @param sheetName 생성할 sheet의 이름
	 * @param titles 제목줄에 표시할 제목 목록
	 * @param keys map에서 데이터를 추출할 key의 목록
	 * @param datas 데이터 목록
	 */
	public static void createSheet(Workbook workbook, String sheetName, String[] titles, String[] keys, List<Map<String,Object>> datas) {
		Sheet sheet = workbook.createSheet(CsUtil.nvl(sheetName,"sheet").toString());
		
		//
		if(CsUtil.isEmpty(titles) || CsUtil.isEmpty(keys)) {
			return;
		}
		
		//제목 row 생성
		Row row = sheet.createRow(0);
		for(int i=0; i<titles.length; i++) {
			row.createCell(i).setCellValue(titles[i]);
		}
		
		
		//데이터 row 생성
		int rowindex = 1;
		for(Map<String,Object> map : datas) {
			Row r = sheet.createRow(rowindex);
			for(int i=0; i<keys.length; i++) {
				if(CsUtil.isNull(map.get(keys[i]))) {
					r.createCell(i).setCellValue("");
				}else {
					r.createCell(i).setCellValue(map.get(keys[i]).toString());
				}
			}
			
			rowindex++;
		}
	}
	
	
	/**
	 * workbook에 sheet 생성
	 * @param workbook workbook
	 * @param sheetName 생성할 sheet의 이름
	 * @param titles 제목줄에 표시할 제목 목록
	 * @param keys map에서 데이터를 추출할 key의 목록
	 * @param datas 데이터 목록
	 */
	public static void createSheet(Workbook workbook, String sheetName, String[] titles, String[] keys, Map<String,Object> data) {
		List<Map<String,Object>> list = new ArrayList<>();
		list.add(data);
		
		createSheet(workbook, sheetName, titles, keys, list);
	}
	
	
	
	/**
	 * nanotime 리턴
	 * @param prefix 리턴값 앞에 붙일 접두어
	 * @return
	 * @history
	 * 	20180215	prefix 추가
	 */
	public static String createShortUid(String prefix){		
		return (isEmpty(prefix) ? "UID" : prefix) + System.nanoTime(); 
//		return (isEmpty(prefix) ? "UID" : prefix) 
//				+ (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date())
//				+ (new Random()).nextInt(10);
	}
	
	/**
	 * uuid 생성(32bytes)
	 * @return
	 */
	public static String createUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	/**
	 * 중복 제거 & 정렬
	 * @param list 데이터 목록
	 * @param key 맵의 키
	 */
	public static Set<String> deDupl(List<Map<String,Object>> list, String key){
		Set<String> set = new TreeSet<String>();
		
		//
		if(CsUtil.isEmpty(list) || CsUtil.isEmpty(key)){
			return set;
		}
		
		//
		for(Map<String,Object> d : list){
			if(!d.containsKey(key)){
				continue;
			}
			
			//
			if(CsUtil.isEmpty(d.get(key))){
				continue;
			}
			
			//
			set.add((String)d.get(key));
		}
		
		//
		return set;
	}
	
	/**
	 * 중복 제거
	 * @param lines 중복 제거할 문자열 목록
	 * @return 중복 제거된 목록
	 * @history
	 * 	0716	init
	 */
	public static List<String> deDupl(List<String> lines){
		
		Set<String> set = new HashSet<>();
		
		//
		for(String s : lines) {
			set.add(s);
		}
		
		//
		List<String> result = new ArrayList<>();
		result.addAll(set);
		
		//
		int beforeCount = lines.size();
		int afterCount = result.size();
		
		//
		if(beforeCount == afterCount){
			LOGGER.info("<<.deDupl - before:{} after:{} dupl:{}", beforeCount, afterCount, beforeCount-afterCount);
			
		}else {
			LOGGER.warn("<<.deDupl - before:{} after:{} dupl:{}", beforeCount, afterCount, beforeCount-afterCount);
			
		}
		
		//
		return result;
	}
	
	
	/**
	 * 파일의 내용 중복 제거 후 같은 파일로 저장(덮어씀)
	 * @param path
	 * @param filename
	 * @throws IOException
	 * @since
	 * 	0925	init
	 */
	public static void deDupl(Path path, String filename) throws IOException {
		//
		List<String> lines = CsUtil.readAllLines(path, filename);
		LOGGER.info(".deDupl - before:{}", lines.size());
		
		//
		lines = deDupl(lines);
		
		//
		LOGGER.info(".deDupl - after:{}", lines.size());
		CsUtil.saveToFile(path, filename, lines, false);
	}
	
	
	/**
	 * 중복 제거
	 * @param lines 중복 제거할 문자열 목록
	 * @return 중복 제거된 목록
	 */
	public static List<String> deDuplOld(List<String> lines){
		List<String> result = new ArrayList<>();
		
		//
		if(CsUtil.isEmpty(lines)) {
			return result;
		}
		
		
		//맵에 넣었다 빼면 중복 제거됨
		String k;
		Map<String,String> map = new HashMap<String, String>();
		for(String str : lines) {
			if(CsUtil.isEmpty(str)) {
				continue;
			}
			
			k = str;
			if(100 < k.length()) {
				k = k.substring(0, 100);
			}
			
			map.put(k, str);
		}
		
		//
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			
			result.add( map.get(k) );
		}
		
		//
		int duplCount = lines.size()-result.size();
		
		//
		if(0 == duplCount) {
			LOGGER.debug("<<.deDupl - before:{} after:{} dupl:{}", lines.size(), result.size(), duplCount);
		}else {
			LOGGER.warn("<<.deDupl - before:{} after:{} dupl:{}", lines.size(), result.size(), duplCount);
		}
		
		return result;
	}
	
	
	/**
	 * 빈 디렉터리(하위 디렉터리 없음, 하위 파일 없음)이면 삭제
	 * path를 루트로 하위 디렉터리 추출하여 빈 디렉터리들 삭제
	 * @param path
	 * @throws IOException
	 * @history
	 * 	0707	init
	 */
	public static void deleteEmptyDirs(Path path) throws IOException {

		List<Path> outDirs = new ArrayList<>();
		bindDirs(path, outDirs);
		
		//
		Path p;
		for(int i=outDirs.size()-1; i>=0; i--) {
			p = outDirs.get(i);
			
			//
			List<File> outFiles = new ArrayList<>();
			bindFiles(path, outFiles);
			if(CsUtil.isEmpty(outFiles)) {
				FileUtils.deleteDirectory(p.toFile());
				LOGGER.debug("+.deleteEmptyDirs - {}", p);
			}
		}
		
	}
	
	
	/**
	 * @see kr.co.ucsit.core.CsUtil.deleteFileForcedown(String)
	 * @throws IOException
	 */
	public static void deleteFileForcedown() throws IOException {
		deleteFileForcedown("");
	}
	/**
	 * forcedown 파일 삭제
	 * @param postfix TODO
	 * @throws IOException 
	 * @history
	 * 	0106	init
	 */
	public static void deleteFileForcedown(String postfix) throws IOException {
		//
		List<Path> paths = getDefaultPaths();
		
		String filename = FORCEDOWN + (CsUtil.isEmpty(postfix) ? "" : postfix);
		
		//
		for(Path p : paths) {
			//
			if(Files.exists(p.resolve(filename))) {
				LOGGER.info("+.deleteFileForcedown - {}", p);
				forceDelete(p.resolve(filename));
			}
			
		}
	}
	
	
	

	/**
	 * 여러 파일 삭제
	 * @param files
	 * @return
	 */
	public static int deleteFiles(List<File> files) {
		boolean b;

		//
		File f;
		for(int i=files.size()-1; i>=0; i--) {
			f = files.get(i);
			
			b = f.delete();
			
			//
			if(!b) {
				throw new RuntimeException("<<.deleteFiles - " + f.toString());
			}
		}
		
		//
		return files.size();
	}
	
	
	/**
	 * 오래된 디렉터 삭제
	 * @param path 루트 디렉터리
	 * @param diffDate 얼마나 오래된건가.... -1:1일전
	 * @throws IOException
	 */
	public static void deleteOldPath(Path path, int diffDate) throws IOException {
		LOGGER.info(">>.deleteOldPath - {} {}", path, diffDate);
		
		File[] files = path.toFile().listFiles();
		if(CsUtil.isEmpty(files)) {
			return;
		}
		
		//
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, diffDate);
		
		//
		int delCount=0;
		for(File f : files) {
			if(!f.isDirectory()) {
				continue;
			}
			
			//
			if(c.getTimeInMillis() > f.lastModified()) {
				FileUtils.deleteDirectory(f);
				delCount++;
			}
		}
		
		//
		LOGGER.info("<<.deleteOldPath - deleted:{} {}", delCount);
		
	}
	
	/**
	 * @see kr.co.ucsit.core.CsUtil.existsFileForcedown(String)
	 * @return
	 */
	public static boolean existsFileForcedown() {
		return existsFileForcedown("");
	}
	
	
	/**
	 * forcedown파일 존재 여부
	 * @param postfix TODO
	 * @return
	 */
	public static boolean existsFileForcedown(String postfix) {
		//
		List<Path> paths = getDefaultPaths();
		
		String filename = FORCEDOWN + (CsUtil.isEmpty(postfix) ? "" : postfix);
		
		//
		for(Path p : paths) {
			if(Files.exists(p.resolve(filename))) {
				LOGGER.info("<<.existsFileForcedown - {} {}", p, filename);
				return true;
			}
		}
		
		//
		return false;
	}
	
	
	/**
	 * 노드 목록에서 excludeNode에 속한 노드 삭제 & 리턴
	 * @param nodes 노드 목록
	 * @param excludeNode 노드 목록에서 제외할 노드 아이디. 여러개일 경우 구분자:,
	 * @return
	 */
	public static List<Map<String, Object>> filteringNodesByExclude(List<Map<String, Object>> nodes, String excludeNode) {
		if(isEmpty(nodes) || isEmpty(excludeNode)) {
			return nodes;
		}
		
		//
		String[] arr = excludeNode.split(",", -1);
		
		//
		Map<String,Object> node;
		for(int i=nodes.size()-1; i>=0; i--) {
			node = nodes.get(i);
			boolean b = false;
			
			for(String nodeId : arr) {
				if(nodeId.equals(node.get("NODE_ID"))) {
					b = true;
					break;
				}
			}
			
			//
			if(b) {
				nodes.remove(i);
			}
		}
		
		//
		return nodes;
	}
	
	
	/**
	 * 노드 목록에서 includeNode에 속한 노드만 추출 & 리턴
	 * @param nodes 노드 목록
	 * @param includeNode 노드 목록에 포함할 노드 아이디. 여러개일 경우 구분자:,
	 * @return
	 */
	public static List<Map<String, Object>> filteringNodesByInclude(List<Map<String, Object>> nodes, String includeNode) {
		if(isEmpty(nodes) || isEmpty(includeNode)) {
			return nodes;
		}
		
		//
		String[] arr = includeNode.split(",", -1);
		
		//
		Map<String,Object> node;
		for(int i=nodes.size()-1; i>=0; i--) {
			node = nodes.get(i);
			boolean b = false;
			
			for(String nodeId : arr) {
				if(nodeId.equals(node.get("NODE_ID"))) {
					b = true;
					break;
				}
			}
			
			//
			if(!b) {
				nodes.remove(i);
			}
		}
		
		//
		return nodes;
	}
	
	/**
	 * 파일, 디렉터리 모두 삭제 가능
	 * @param path
	 * @throws IOException
	 */
	public static void forceDelete(Path path) throws IOException {
		LOGGER.info(">>.forceDelete - {}", path);
		
		if(!Files.exists(path)) {
			return;
		}
		
		//
		LOGGER.debug(".forceDelete - {}", path);
		FileUtils.forceDelete(path.toFile());
	}
	
	
	
	
	/**
	 * 날짜 형식 변환. yyyy-MM-dd
	 * @param str 문자열
	 * @return
	 */
	public static String formatDate(String str) {
		if(isEmpty(str)) {
			return "";
		}
		
		String s = str.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		
		return formatDate(str, s.length());
	}
	
	
	/**
	 * 날짜 형식 변환
	 * @param str 문자열
	 * @param num 리턴할 날짜의 길이
	 * @return
	 */
	public static String formatDate(String str, Integer num){
		if(isEmpty(str)){
			return "";
		}
		
		//
		String s = str.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		if(14 < s.length()) {
			s = s.substring(0,14);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		if(8 == s.length()) {
			sdf.applyPattern("yyyyMMdd");
		}else if(10 == s.length()) {
			sdf.applyPattern("yyyyMMddHH");
		}else if(12 == s.length()) {
			sdf.applyPattern("yyyyMMddHHmm");
		}else if(14 == s.length()) {
			sdf.applyPattern("yyyyMMddHHmmss");
		}
		
		try {
			Date dt = sdf.parse(s);
			
			SimpleDateFormat sdf2 = null;
			
			if(14 == num){
				sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
			}else if(12 == num){
				sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");				
			}else if(10 == num){
				sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");				
			}else if(8 == num){
				sdf2 = new SimpleDateFormat("yyyy-MM-dd");				
			}
			
			if(null == sdf2){
				return str;
			}
			
			return sdf2.format(dt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * 천단위 콤마
	 * @param str
	 * @return
	 */
	public static String formatNumber(String str){
		if(isEmpty(str)){
			return "";
		}
		
		//
		try {
			return String.format("%,d", Integer.parseInt(str));
		} catch (NumberFormatException e) {
			return str;
		}
	}
	

	/**
	 * 현재 경로 추출
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Path getCurrentPath(Class clz) {
		try {
			return Paths.get(clz.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
		} catch (URISyntaxException e) {
			LOGGER.error("{}", clz.getProtectionDomain().getCodeSource().getLocation());
			LOGGER.error("{}",e);
		}
		
		//
		return Paths.get("").toAbsolutePath();
	}
	
	/**
	 * CsTransferObject의 data 키의 값
	 * @param trans CsTransferObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getData(CsTransferObject trans){
		if(null == trans || !trans.containsKey(CsConst.DATA)) {
			return new HashMap<String,Object>();
		}
		
		return (Map<String, Object>) trans.get(CsConst.DATA);
	}

	/**
	 * CsTransferObject의 datas 키의 값
	 * @param trans CsTransferObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getDatas(CsTransferObject trans){
		if(null == trans || !trans.containsKey(CsConst.DATAS)) {
			return new ArrayList<Map<String,Object>>();
		}
		
		return  (List<Map<String, Object>>) trans.get(CsConst.DATAS);
	}
	
	/**
	 * 기본(?) 경로 목록
	 * os계정의 home 경로 또는 어플리케이션을 실행시킨 경로
	 * @return
	 * @history
	 * 	0108	init
	 */
	public static List<Path> getDefaultPaths(){
		return Arrays.asList(Paths.get("").toAbsolutePath(), Paths.get(System.getProperty("user.dir")));
	}

	/**
	 * 두 날짜 사이의 차이 일수
	 * @param dt1
	 * @param dt2
	 * @return 0 : 두 날짜가 같음, 양수:dt1 < dt2, 음수:dt1 > dt2
	 */
	public static long getDiffDays(String dt1, String dt2) {
		if(CsUtil.isEmpty(dt1) || CsUtil.isEmpty(dt2)) {
			return 0;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date d1, d2;
		
		//
		if("TODAY".equals(dt1)) {
			d1 = new Date();
		}else {
			String s1 = dt1.replaceAll("-", "");
			if(8 > s1.length()) {
				return 0;
			}
			
			try {
				d1 = sdf.parse(s1);
			} catch (ParseException e) {
				return 0;
			}			
		}
		
		//
		if("TODAY".equals(dt2)) {
			d2 = new Date();
		}else {
			String s2 = dt2.replaceAll("-", "");
			if(8 > s2.length()) {
				return 0;
			}
			
			try {
				d2 = sdf.parse(s2);
			} catch (ParseException e) {
				return 0;
			}			
		}
		
		//
		long diff = d2.getTime() - d1.getTime();
		
		return (diff / (24*60*60*1000));
	}
	
	/**
	 * 여유메모리 용량 리턴(MB)
	 * @return
	 */
	public static long getFreeMemory(){
		return Runtime.getRuntime().freeMemory()/1024/1024;
	}

	/**
	 * 파일의 라인수 리턴
	 * Iterator를 사용하여 파일 크기에 관계없이 안정적으로 기능 수행 가능
	 * 공백 라인은 계산하지 않음
	 * @param file
	 * @return
	 * @throws IOException
	 * @history
	 * 	0707	init
	 */
	public static long getLineCount(File file) throws IOException {
		if(!Files.exists(file.toPath())) {
			LOGGER.info("<<.getLineCount - file not found {}", file);
			return 0;
		}
		
		//
		LineIterator iter = FileUtils.lineIterator(file, "utf-8");
		
		long i=0;
		while(iter.hasNext()) {
			if(CsUtil.isNotEmpty(iter.nextLine())){
				i++;
			};
		}
		
		iter.close();
		
		//
		LOGGER.info("<<.getLineCount - {}", i);
		return i;
	}
	
	/**
	 * 메소드명 리턴
	 * @param arr
	 * @return
	 * @since	20191112
	 */
	public static String getMethodName(StackTraceElement[] arr) {
		if(CsUtil.isEmpty(arr)) {
			return "";
		}
		
		//
		return arr[1].getMethodName();
	}
	
	
	/**
	 * 자바 임시 경로 리턴
	 * @return
	 */
	public static Path getTempPath() {
		try {
			return Paths.get(System.getProperty("java.io.tmpdir"), createShortUid("T"));
		} catch (Exception e) {
			LOGGER.error("{}", e);			
		}
		
		//
		return Paths.get("").toAbsolutePath().resolve(createShortUid("T"));
	}
	
	
	/**
	 * 전체메모리 용량 리턴(MB)
	 * @return
	 */
	public static long getTotalMemory(){
		return Runtime.getRuntime().totalMemory()/1024/1024;
	}

	/**
	 * 문자열을 xss피할 수 있는 html 문자열로 생성
	 * @param str
	 * @return
	 */
	public static String html(String str) {
		if(isEmpty(str)) {
			return "";
		}
		
		//
		return str.trim()
					.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;")
					.replaceAll(" ", "&nbsp;")
					.replaceAll("\n", "<br/>");
	}
	
	
	/**
	 * 문자열 배열의 모든 값이 같은지 여부
	 * @param strings
	 * @return
	 */
	public static boolean isAllEquals(String...strings) {
		if(isEmpty(strings)) {
			return true;
		}
		
		boolean b = true;
		String t = strings[0];
		
		//
		for(String s : strings) {
			if(null == t) {
				b &= (null == s);
				
			}else {
				b &= (t.equals(s));
			}
			
			//
			if(!b) {
				return false;
			}
		}
		
		//
		return b;
	}
	
	
	/**
	 * 압축파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isArchiveFile(CsFileVO fileVo) {
		if(null == fileVo || isEmpty(fileVo.getOriginFileName())) {
			return false;
		}
		
		return isArchiveFile(fileVo.getOriginFileName());
	}
	
	
	/**
	 * 압축파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isArchiveFile(File file) {
		if(null == file) {
			return false;
		}
		
		return isArchiveFile(file.getName());
	}
	
	
	/**
	 * 압축파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isArchiveFile(String filename) {
		if(isEmpty(filename)) {
			return false;
		}
		
		String ext = FilenameUtils.getExtension(filename).toLowerCase();
		
		switch(ext) {
		case "zip":
		case "alz":
		case "7z":
			return true;			
		}
		
		//집파일
		if(ext.startsWith("z") && 3 == ext.length()) {
			return true;
		}
		
		//알집파일
		if(ext.startsWith("a") && 3 == ext.length()) {
			return true;
		}
		
		// 
		return false;
	}

	/**
	 * 공백 여부
	 * @param obj 오브젝트
	 * @return
	 * @history
	 * 	20180322	배열, 리스트 처리 추가
	 * 	20200221	Map관련 추가
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
		if(isNull(obj)){
			return true;
		}
		
		//문자열
		if(String.class == obj.getClass() ) {
			return (0 == obj.toString().trim().length());
		}
		
		//
		if(obj instanceof Collection) {
			return (0 ==((Collection)obj).size());
		}
		
		//
		if(obj instanceof Map) {
			return (0 == ((Map)obj).size());
		}
		
		//
		if(Set.class == obj.getClass()) {
			return (0 == ((Set)obj).size());
		}
		
		//리스트
		if(List.class == obj.getClass() || (ArrayList.class == obj.getClass())) {
			return (0 == ((List)obj).size());
		}
		
		
		//배열
		if(obj.getClass().toString().contains("[L")) {
			return (0 == Array.getLength(obj));
		}
		
		//
		return (0 == obj.toString().length());
	}
	
	
	/**
	 * 목록중 하나라도 empty이면 true 리턴
	 * @param objects
	 * @return
	 */
	public static boolean isEmptyAny(Object...objects) {
		boolean b = false;
		
		for(Object o : objects) {
			b = isEmpty(o);
			
			//
			if(b) {
				return b;
			}
		}
		
		return b;
	}
	
	/**
	 * 이미지파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isImageFile(CsFileVO fileVo) {
		if(null == fileVo || isEmpty(fileVo.getOriginFileName())) {
			return false;
		}
		
		return isImageFile(fileVo.getOriginFileName());
	}
	

	
	/**
	 * 이미지파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isImageFile(File file) {
		if(null == file) {
			return false;
		}
		
		return isImageFile(file.getName());
	}
	
	
	/**
	 * 이미지파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isImageFile(String filename) {
		if(isEmpty(filename)) {
			return false;
		}
		
		String ext = FilenameUtils.getExtension(filename).toLowerCase();
		
		switch(ext) {
		case "bmp":
		case "png":
		case "jpg":
		case "jpeg":
		case "gif":
			return true;			
		}
		
		
		// 
		return false;
	}
	
	/**
	 * json 문자열인지 여부
	 * [나 {로 시작하면 json이라고 판단
	 * @param str
	 * @return
	 * @history
	 * 	0102	init
	 */
	public static boolean isJsonString(String str) {
		if(isEmpty(str)) {
			return false;
		}
		
		//
		return str.trim().startsWith("[") || str.trim().startsWith("{");
	}
	
	
	/**
	 * isEmpty의 반대
	 * @param str 문자열
	 * @return true / false
	 * 	true 조건
	 * 		문자열인 경우 공백이 아니면
	 * 		collection(Set, List,...)인 경우 0 < size
	 * 		배열인 경우 0 < length
	 * 		Map인 경우 0 < size
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	
	
	
	/**
	 * !널여부
	 * @param obj 오브젝트
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	

	/**
	 * 널여부 검사
	 * @param obj 오브젝트
	 * @return
	 */
	public static boolean isNull(Object obj){
		return (null == obj);
	}
	

	/**
	 * 문자열이 숫자인지 검사
	 * @param str 문자열
	 * @return true(문자열이 숫자) / false
	 */
	public static boolean isNum(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}catch(Exception e) {
			LOGGER.error("{}",e.getMessage());
			return false;
		}
	}
	
	/**
	 * 오피스 파일인지 여부
	 * @see kr.co.ucsit.core.CsUtil.isOfficeFile(String)
	 * @param fileVo
	 * @return
	 */
	public static boolean isOfficeFile(CsFileVO fileVo) {
		if(null == fileVo || isEmpty(fileVo.getOriginFileName())) {
			return false;
		}
		
		//
		return isOfficeFile(fileVo.getOriginFileName());
	}
	
	
	/**
	 * 오피스 파일인지 여부
	 * @see kr.co.ucsit.core.CsUtil.isOfficeFile(String)
	 * @param file
	 * @return
	 */
	public static boolean isOfficeFile(File file) {
		if(null == file) {
			return false;
		}
		
		return isOfficeFile(file.getName());
	}
	
	/**
	 * 오피스 파일인지 여부. 확장자로만 판단
	 * @param file
	 * @return
	 */
	public static boolean isOfficeFile(String filename) {
		if(isEmpty(filename)) {
			return false;
		}
		
		String ext = FilenameUtils.getExtension(filename).toLowerCase();
		
		switch(ext) {
		case "xls":
		case "xlsx":
		case "doc":
		case "docx":
		case "ppt":
		case "pptx":
		case "hwp":
		case "hwpx":
		case "pdf":
			return true;			
		}
		
		// 
		return false;
	}
	
	/**
	 * 텍스트 파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isTextFile(CsFileVO fileVo) {
		if(null == fileVo || isEmpty(fileVo.getOriginFileName())) {
			return false;
		}
		
		//
		return isTextFile(fileVo.getOriginFileName());
	}
	
	
	/**
	 * 텍스트 파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isTextFile(File file) {
		if(null == file) {
			return false;
		}
		
		return isTextFile(file.getName());
	}
	
	
	/**
	 * 텍스트 파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 */
	public static boolean isTextFile(String filename) {
		if(isEmpty(filename)) {
			return false;
		}
		
		String ext = FilenameUtils.getExtension(filename).toLowerCase();
		
		switch(ext) {
		case "txt":
		case "rtf":
		case "xml":
			return true;			
		}
		
		
		// 
		return false;
	}
	

	
	
	/**
	 * 허용 가능한 파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 * @history
	 * 	0905	init
	 */
	public static boolean isWhiteFileExtension(File file) {
		if(null == file) {
			throw new RuntimeException("null file");
		}
		
		return isWhiteFileExtension(file.getName());
	}
	
	
	/**
	 * 허용 가능한 파일인지 여부. 확장자로 판단
	 * @param file
	 * @return
	 * @history
	 * 	0905	init
	 */
	public static boolean isWhiteFileExtension(String filename) {
		if(isEmpty(filename)) {
			throw new RuntimeException("null file");
		}
		
		//
		if(isOfficeFile(filename)) {
			return true;
		}
		
		//
		if(isArchiveFile(filename)) {
			return true;
		}
		
		//
		if(isTextFile(filename)) {
			return true;
		}
		
		//
		if(isImageFile(filename)) {
			return true;
		}
		
		//
		return false;
	}
	
	
	/**
	 * xml 문자열인지 여부
	 * <xml로 시작하면 xml 문자열이라고 판단
	 * @param str
	 * @return
	 */
	public static boolean isXmlString(String str) {
		if(isEmpty(str)) {
			return false;
		}
		
		//
		String s = str.trim().toLowerCase();
		return s.startsWith("<?xml");
	}
	
	/**
	 * 프로세스 죽이기. kill -9 호출
	 * @param clz
	 * @return
	 */
	public static boolean killProcess(Class<?> clz) {
		String filename = new File(clz.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		
		return killProcess(filename);
	}
	

	/**
	 * 프로세스 죽이기. kill -9 호출
	 * @param name
	 */
	public static boolean killProcess(String name) {

		List<String> cmd = new ArrayList<>();
		cmd.add("ps");
		cmd.add("-ef");
		
		try {
			List<String> results = CsUtil.runProcess(cmd);
			for(String str : results) {
				if(!str.contains(name)) {
					continue;
				}
				
//				System.out.println(str);
				
				String[] arr = str.split(" ");
				for(String s : arr) {
					if(CsUtil.isEmpty(s)) {
						continue;
					}
					if(!NumberUtils.isNumber(s)) {
						continue;
					}
					
					//
					cmd.clear();
					cmd.add("kill");
					cmd.add("-9");
					cmd.add(s);
					CsUtil.runProcess(cmd);
					return true;
				}
				
			}
		} catch (IOException e) {
			return false;
		}
		
		//
		return false;
	
	}
	
	
	/**
	 * map을 object로 변환하기
	 * @param map
	 * @param clz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object mapToVo(Map<String,Object> map, Class clz) throws InstantiationException, IllegalAccessException {
		Object obj = clz.newInstance();
		
		//
		Field[] fields = clz.getDeclaredFields();
		
		//
		for(Field f : fields) {
			if(!map.containsKey(f.getName())) {
				continue;
			}
			
			//
			f.setAccessible(true);
			f.set(obj, map.get(f.getName()));
		}
		
		//
		return obj;
	}
	
	public static void main(String[] args) {
		System.out.println( CsUtil.getDiffDays("TODAY", "20180416") );
	}
	
	/**
	 * 오라클의 nvl()과 유사한 메소드
	 * @param obj 오브젝트
	 * @param defaultValue 기본값
	 * @return
	 */
	public static Object nvl(Object obj, Object defaultValue){
		if(isNull(obj)){
			return defaultValue;
		}
		
		return obj;
	}
	/**
	 * random값 구하기
	 * @param bound	random값 구하기 위한 max값(범위)
	 * @return
	 */
	public static long rand(int bound){
		return (new Random()).nextInt(bound);
	}
	
	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readAllLines(File file) throws IOException{
		return readAllLines(file.toPath().getParent(), file.toPath().getFileName().toString());
	}
	
	
	/**
	 * 파일 내용 읽기
	 * 파일 미존재시 빈 리스트 리턴
	 * 파일 크기 0이면 빈 리스트 리턴
	 * @param path
	 * @param filename
	 * @return
	 * @throws IOException
	 * @history
	 * 	0528	exists추가
	 * 	0821	if문 몇개 추가
	 */
	public static List<String> readAllLines(Path path, String filename) throws IOException{
		if(null == path || isEmpty(filename)) {
			LOGGER.info("<<.readlAllLines - empty path or filename {} {}", path, filename);
			return new ArrayList<>();
		}
		
		//
		if(!Files.exists(path.resolve(filename))) {
			LOGGER.info("<<.readlAllLines - not found {}", path.resolve(filename));
			return new ArrayList<>();
		}
		
		//
		if(0 >= path.resolve(filename).toFile().length()) {
			LOGGER.info("<<.readlAllLines - empty file size {}", path.resolve(filename).toFile().length());
			return new ArrayList<>();
		}
		
		try {
			List<String> lines = Files.readAllLines(path.resolve(filename), StandardCharsets.UTF_8);
			LOGGER.debug(".readAllLines - {} {} lines:{} mem(MB):{}/{}", path, filename, lines.size(), getFreeMemory(), getTotalMemory());
			
			return lines;
		} catch (Exception e) {
			LOGGER.error(".readAllLines - {}", path.resolve(filename));
			LOGGER.error("{}", e);
			throw e;
		}
	}
	
	
	public static List<String> runProcess(List<String> cmd) throws IOException {
		return runProcess(cmd, false);
	}
	
	
	/**
	 * os 명령어 실행
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static List<String> runProcess(List<String> cmd, boolean ignoreError) throws IOException {
		if(CsUtil.isEmpty(cmd)) {
			throw new RuntimeException("cmd is empty");
		}
//		System.out.println(cmd);
		LOGGER.info(">>.runProcess - {} {}", cmd, ignoreError);
		
		Process pr = new ProcessBuilder(cmd).start();
		
		
		//
		BufferedReader br = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
		String line;
		while(null != (line=br.readLine())) {			
			if(CsUtil.isNotEmpty(line) && !ignoreError) {
				throw new RuntimeException(line);
			}
		}
		br.close();
		
		//결과 저장
		List<String> results = new ArrayList<>();
		BufferedReader br2=null;
		Scanner scanner = null;
		try {
			br2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			scanner = new Scanner(br2);
			scanner.useDelimiter(System.getProperty("line.separator"));
			
			while(scanner.hasNext()) {
				results.add(scanner.next());
			}
			
		}finally {
			if(null != scanner) {
				scanner.close();
			}
			
			if(null != br2) {
				br2.close();
			}
			
			//
//			LOGGER.debug(".runProcess - {} {}", CsUtil.isEmpty(results)?"OK":"NG", cmd);
		}
		
		//
		return results;
	}
	
	
	
	/**
	 * os 명령어 실행
	 * @see kr.co.ucsit.core.CsUtil.runProcess(List<String>)
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static List<String> runProcess(String[] cmd) throws IOException {
		return runProcess(Arrays.asList(cmd));
	}
	
	/**
	 * 파일 저장 with bom
	 * @param path
	 * @param filename
	 * @param lines
	 * @param bAppend 
	 * @throws IOException
	 */
	public static void saveFileWithBOM(Path path, String filename, List<String> lines, boolean bAppend) throws IOException {
		if(bAppend) {
			Files.write(path.resolve(filename), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			return;
		}
		
		//
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.resolve(filename).toFile()), StandardCharsets.UTF_8));
		writer.write(CsConst.UTF8_BOM);
		
		//
		for(String s : lines) {
			writer.write(s + "\n");
		}
		
		//
		writer.close();
	}
	
	public static void saveToFile(File file, List<String> lines, boolean isAppend) throws IOException {
		saveToFile(Paths.get(file.getParent()), file.getName(), lines, isAppend);
	}
	
	/**
	 * 파일 생성
	 * 경로 없으면 자동으로 생성
	 * @param p 경로
	 * @param filename 파일명
	 * @param lines 데이터 줄 목록
	 * @param isAppend 기존 파일에 overwrite할지 여부
	 * @throws IOException
	 */
	public static void saveToFile(Path p, String filename, Collection<String> lines, boolean isAppend) throws IOException {

		//
		Collection<String> result = new ArrayList<String>();
		
		//bom문자 제거
		String s;
		Iterator<String> iter = lines.iterator();
		while(iter.hasNext()) {
			s = iter.next();
			
			if(s.startsWith(CsConst.UTF8_BOM)) {
				result.add(s.substring(1));
			}else {
				result.add(s);
			}
		}
		
		
		//
		if(!Files.exists(p)) {
			Files.createDirectories(p);
		}
		
		//
		if(Files.exists(p.resolve(filename)) && isAppend) {
			Files.write(p.resolve(filename), result, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		}else {
			Files.write(p.resolve(filename), result, StandardCharsets.UTF_8);
		}
	
		
		LOGGER.info("<<.saveToFile - {} lines:{}", p.resolve(filename), result.size());
	}
	
	
	
	/**
	 * lines내용을 텍스트 파일로 저장. 동일 파일 존재시 기존 내용 하단에 append
	 * @param p
	 * @param filename
	 * @param lines
	 * @throws IOException
	 * @since
	 * 	1228 문자열 시작이 bom문자일 경우 제거하는 로직 추가
	 */
	public static void saveToFile(Path p, String filename, List<String> lines) throws IOException {
		saveToFile(p, filename, lines, true);
	}
	
	
	/**
	 * @see kr.co.ucsit.core.CsUtil.saveToFile(Path, String, List<String>)
	 * @param p
	 * @param filename
	 * @param lines
	 * @throws IOException
	 * @since
	 * 	1228	문자열이 bom문자열로 시작하면 bom문자열 제거하는 로직 추가 
	 */
	public static void saveToFile(Path p, String filename, String data) throws IOException {				
		List<String> list = new ArrayList<>();
		
		//bom 문자 제거
		if(data.startsWith(CsConst.UTF8_BOM)) {
			data = data.substring(1);
		}
		
		//
		list.add(data);
		
		saveToFile(p, filename, list);
	}
	
	
	/**
	 * system property 정보 출력
	 */
	public static void showSystemProperties() {
		int i=0;
		Object obj;
		Iterator<Object> iter = System.getProperties().keySet().iterator();
		while(iter.hasNext()) {
			obj = iter.next();
			
			//
			LOGGER.debug("#{}\t{}={}", i++, obj, System.getProperty(obj.toString()));
		}
	}
	
	
	
	/**
	 * list 랜덤(asc or desc)으로 정렬
	 * @param list
	 * @return
	 */
	public static List<Map<String,Object>> sortRandom(List<Map<String,Object>> list){
		//
		List<Map<String,Object>> result = list;
		
		if(CsUtil.isEmpty(result)) {
			return result;
		}
		
		//
		if(50 > result.size()) {
			//
			if(0 == CsUtil.rand(2)) {
				LOGGER.debug(".sortRandom - reverse");
				Collections.reverse(result);
			}
			
			return result;			
		}
		
		//
		result = new ArrayList<>();
		
		int fromIndex=0, toIndex=0;
		while(true) {
			fromIndex = toIndex;
			toIndex = fromIndex + 10;
			
			
			//
			if(fromIndex > list.size()) {
				fromIndex = list.size();
			}
			
			//
			if(toIndex > list.size()) {
				toIndex = list.size();
			}
			
			//
			if(fromIndex >= toIndex) {
				break;
			}
			
			//
			LOGGER.debug(".sortRandom - {}~{}/{}", fromIndex, toIndex, list.size());
			result.addAll( sortRandom(list.subList(fromIndex, toIndex)) );
		}
		
		
		//
		return result;
		
	}
	
	
	/**
	 * list를 size크기로 잘라 목록에 담음 & 리턴
	 * @param list
	 * @param size
	 * @return
	 */
	public static <T> List<List<T>> subList(List<T> list, int size){
		List<List<T>> result = new ArrayList<>();
		
		//
		if(CsUtil.isEmpty(list)) {
			return result;
		}
		
		//
		int fromIndex=0, toIndex=0;
		while(true) {
			fromIndex = toIndex;
			toIndex += size;
			
			//
			if(fromIndex > list.size()) {
				fromIndex = list.size();
			}
			
			//
			if(toIndex > list.size()) {
				toIndex = list.size();
			}
			
			//
			if(fromIndex == toIndex) {
				break;
			}
			
			//
			result.add( list.subList(fromIndex, toIndex) );
		}
		
		//
		return result;
	}
	


	/**
	 * substring
	 * str의 길이가 endIndex가 작으면 전체 str 문자열 리턴
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 * @history
	 * 	1230	init
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		if(isEmpty(str)) {
			return str;
		}
		
		try {
			return str.substring(beginIndex, endIndex);
		}catch(Exception e) {
			return str;
		}
	}
	
	
	/**
	 * 언더바 표기 문자열을 낙타법 문자열로 변환
	 * 예)AB_CD => abCd
	 * @param str
	 * @return
	 */
	public static String toCamel(String str) {
		if(CsUtil.isEmpty(str)) {
			return "";
		}
		
		if(!str.contains("_")) {
			return str;
		}
		
		String s = str.toLowerCase();
		
		String t = "";
		
		for(int i=0; i<s.length(); i++) {
			if("_".equals(String.valueOf(s.charAt(i)))){
				i++;
				t += String.valueOf(s.charAt(i)).toUpperCase();
			}else {
				t += s.charAt(i);
			}
		}

		return t;
	}
	
	
	/**
	 * 문자열을 List<String>으로 리턴
	 * @param str
	 * @return
	 * @history
	 * 	0102	init
	 */
	public static List<String> toList(String str){
		if(isEmpty(str)) {
			return new ArrayList<String>();
		}
		
		
		//
		if(!str.contains(",")) {
			List<String> list = new ArrayList<String>();
			list.add(str);
			return list;
		}
		
		//
		return Arrays.asList(str.split(",", -1));
	}

	/**
	 * 숫자형으로 변환
	 * @param numberString ex)100k 100m 100g
	 * @return
	 */
	public static long toNumber(String numberString) {
		if(numberString.toLowerCase().endsWith("k")) {
			return 1000 * Long.parseLong(numberString.replaceAll("k", ""));
		}
		
		if(numberString.toLowerCase().endsWith("m")) {
			return 1000000 * Long.parseLong(numberString.replaceAll("m", ""));
		}
		
		if(numberString.toLowerCase().endsWith("g")) {
			return 1000000000 * Long.parseLong(numberString.replaceAll("g", ""));
		}
		
		//
		return Long.parseLong(numberString);
	}
	
	
	/**
	 * json 문자열 시리얼라이즈
	 * @param jsonString
	 * @return object
	 */
	public static Object toObjectFromJsonString(String jsonString){
		if(CsUtil.isEmpty(jsonString)){
			return null;
		}

		//
		String str = jsonString.trim();

		//
		if(str.startsWith("{")){
			return (new Gson()).fromJson(str, CsMap.class);
		}else if(str.startsWith("[")){
			return (new Gson()).fromJson(str, List.class);
		}else{
			return null;
		}
	}

	
	
	/**
	 * 배열을 콤마 구분자 문자열로 변환
	 * @param arr
	 * @return
	 */
	public static String toString(String[] arr) {
		String str = "";
		
		//
		if(CsUtil.isEmpty(arr)) {
			return str;
		}
		
		//
		for(String s : arr) {
			str += (0==str.length()?"":",") + s;
		}
		
		//
		return str;
	}
	
	
}
