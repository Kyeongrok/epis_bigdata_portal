/**
 * 
 */
package bigdata.portal.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.service.AdministInsttCodeSumryService;
import bigdata.portal.service.AtmnentPrdlstCodeInfoService;
import bigdata.portal.service.FixesSportPolicyService;
import bigdata.portal.service.SimilrAtmnentService;
import kr.co.ucsit.core.CsConst;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

/**
 * 맞춤 지원 정책
 * @author hyunseongkil
 *
 */
/**
 * @author hyunseongkil
 *
 */
@Controller
@RequestMapping("/bdp/svc/")
public class FixesSportPolicyController extends BigdataController {
	private static Logger log = LoggerFactory.getLogger(FixesSportPolicyController.class);
	
	@Autowired
	private FixesSportPolicyService service;
	
	/**
	 * 시도,시군구
	 */
	@Autowired
	private AdministInsttCodeSumryService administInsttCodeSumryService;
	
	/**
	 * 품목군, 품목 코드
	 */
	@Autowired
	private AtmnentPrdlstCodeInfoService atmnentPrdlstCodeInfoService;
	
	/**
	 * 유사 경영체
	 */
	@Autowired
	private SimilrAtmnentService similrAtmnentService;
	
	
	/**
	 * 맞춤 지원 정책 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("fixesSportPolicySearchForm.do")
	public String fixesSportPolicySearchForm(HttpServletRequest request, Model model) {
		
		return P_BIGDATA_PORTAL + "svc/fixesSportPolicySearchForm";
	}
	
	
	
	/**
	 * 맞춤 지원 정책 검색 목록 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("fixesSportPolicySearchList.do")
	public String fixesSportPolicySearchList(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		model.addAttribute("paramMap", paramMap);
		
		//지원영역
		model.addAttribute("sportRelmCodes", getSportRelmCodes());
		//성별
		model.addAttribute("sexdstns", getSexdstns());
		//지원유형
		model.addAttribute("sportTyCodes", getSportTyCodes());
		//농업계학교 출신여부
		model.addAttribute("farmngSchulOrginAts", getFarmngSchulOrginAts());
		//재배유형
		model.addAttribute("ctvtTyCodes", getCtvtTyCodes());
		//농업인-재배 품목 군
		model.addAttribute("ctvtPrdlstSetCodes", getCtvtPrdlstSetCodes());
		//농업인-재배 품목
		model.addAttribute("ctvtPrdlstCodes", getCtvtPrdlstCodes());
		//모든 시도
		model.addAttribute("sidoCodes", administInsttCodeSumryService.getAllSidos());
		//모든 시군구
		model.addAttribute("sigunguCodes", administInsttCodeSumryService.getAllSigungus());
		//모든 품목군
		model.addAttribute("prdlstSetCodes", atmnentPrdlstCodeInfoService.getLclass());
		//모든 품목
		model.addAttribute("prdlstCodes", atmnentPrdlstCodeInfoService.getAlls());
		
		
		return P_BIGDATA_PORTAL + "svc/fixesSportPolicySearchList";
	}
	

	/**
	 * 재배 품목군 코드 목록
	 * @return
	 */
	private List<Map<String,Object>> getCtvtPrdlstSetCodes() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> m1 = new HashMap<String, Object>();
		list.add(m1);
		m1.put("ctvtPrdlstSetCode", "CPS_V");
		m1.put("ctvtPrdlstSetName", "채소류");
		
		Map<String,Object> m2 = new HashMap<String, Object>();
		list.add(m2);
		m2.put("ctvtPrdlstSetCode", "CPS_F");
		m2.put("ctvtPrdlstSetName", "과일류");

		//
		return list;
	}
	
	/**
	 * 재배 품목 코드 목록
	 * @return
	 */
	private List<Map<String,Object>> getCtvtPrdlstCodes() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//
		Map<String,Object> m1 = new HashMap<String, Object>();
		list.add(m1);
		m1.put("ctvtPrdlstSetCode", "CPS_V");
		m1.put("ctvtPrdlstCode", "CP_V_1");
		m1.put("ctvtPrdlstName", "상추");
		
		Map<String,Object> m2 = new HashMap<String, Object>();
		list.add(m2);
		m2.put("ctvtPrdlstSetCode", "CPS_V");
		m2.put("ctvtPrdlstCode", "CP_V_2");
		m2.put("ctvtPrdlstName", "오이");
		
		//
		Map<String,Object> m3 = new HashMap<String, Object>();
		list.add(m3);
		m3.put("ctvtPrdlstSetCode", "CPS_F");
		m3.put("ctvtPrdlstCode", "CP_F_1");
		m3.put("ctvtPrdlstName", "사과");
		
		Map<String,Object> m4 = new HashMap<String, Object>();
		list.add(m4);
		m4.put("ctvtPrdlstSetCode", "CPS_F");
		m4.put("ctvtPrdlstCode", "CP_F_2");
		m4.put("ctvtPrdlstName", "배");
		
		//
		return list;
	}



	/**
	 * 재배 유형 코드
	 * @return
	 */
	private List<Map<String,Object>> getCtvtTyCodes() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> m1 = new HashMap<String, Object>();
		list.add(m1);
		m1.put("ctvtTyCode", "CT_Y");
		m1.put("ctvtTyName", "노지");
		
		Map<String,Object> m2 = new HashMap<String, Object>();
		list.add(m2);
		m2.put("ctvtTyCode", "CT_F");
		m2.put("ctvtTyName", "시설");

		//
		return list;
	}



	/**
	 * 데이터 목록 조회
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	@RequestMapping(value="getFixesSportPolicyDatas.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getFixesSportPolicyDatas(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, IOException  {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		log.debug("{}",paramMap);
		
		//
		CsTransferObject trans = service.getDatas(paramMap.get("searchGbn").toString(), paramMap.get("searchKeyword").toString());
		model.addAllAttributes(trans);
		
		//
		return JSON_VIEW;
	}
	
	
	/**
	 * 귀농용 정책 데이터 목록 조회
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	@RequestMapping(value="getFixesSportPolicyDatasForRetnFmlg.json", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getFixesSportPolicyDatasForRetnFmlg(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, IOException  {
		CsMap paramMap = CsWebUtil.toParamMap(request);
		log.debug("{}",paramMap);
		
		//
		CsTransferObject trans = service.getDatasForRetnFmlg(paramMap.getString("searchSido")
																	,paramMap.getString("searchSigungu")
																	,paramMap.getString("searchUmd")
																	,paramMap.getString("searchSportRelmCode"));
		
		//
		model.addAllAttributes(trans);
		
		//
		return JSON_VIEW;
	}
	
	/**
	 * 세부사업코드로 데이터 목록 조회
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	@RequestMapping(value="getFixesSportPolicyDatasByMlsfcNmCode.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getFixesSportPolicyDatasByMlsfcNmCode(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, IOException  {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		log.debug("{}",paramMap);
		
		//
		CsTransferObject trans = service.getDatasByMlsfcNmCode(paramMap.get("mlsfcNmCode").toString());
		model.addAllAttributes(trans);
		
		//
		return JSON_VIEW;
	}
	
	/**
	 * 지침서 파일 정보 리턴
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getManualData.json")
	public String getManualData(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		log.debug(".getManualData - {}", paramMap);
		
		//get 파일 메타정보
		Map<String,Object> fileInfoMap = service.getFileInfoByLclasNmCodeAndMlsfcNmCode(""+paramMap.get("lclasNmCode"), ""+paramMap.get("mlsfcNmCode"));
		
		//보안을 위해 일부 값 제거
		if(!CsUtil.isEmpty(fileInfoMap)) {
			fileInfoMap.put("STRE_PATH", null);
			fileInfoMap.put("STRE_FILE_NM", null);
		}
		
		//
		model.addAttribute("data", fileInfoMap);
				
		//
		return "jsonView";
	}
	
	/**
	 * 지침서 파일 다운로드
	 * @param request
	 * @param response
	 * @param lclasNmCode
	 * @throws IOException
	 */
	@RequestMapping("dwldManual.do")	
	public void dwldManual(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		//get 파일 메타정보
		Map<String,Object> fileInfoMap = service.getFileInfoByLclasNmCodeAndMlsfcNmCode(""+paramMap.get("lclasNmCode"), ""+paramMap.get("mlsfcNmCode"));
		log.debug("fileInfoMap:{}", fileInfoMap);
		
		String fileName = fileInfoMap.get("ORGINL_FILE_NM").toString();
		
		//
		InputStream inputStream = null; 
		
		
		
		//write to output stream
		try {				
			inputStream = new FileInputStream(Paths.get(fileInfoMap.get("STRE_PATH").toString(), fileInfoMap.get("STRE_FILE_NM").toString()).toFile());
			String header = getBrowser(request);
			
			if (header.contains("MSIE")) {
				String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
				response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
			} else if (header.contains("Firefox")) {
				String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			} else if (header.contains("Opera")) {
				String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			} else if (header.contains("Chrome")) {
				String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			}
			
			//
			response.setHeader("Content-Type", "application/octet-stream");
			//response.setContentLength((int)file.getSize());
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			
			//
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			
		} finally {
			if(null != inputStream) {
				inputStream.close();
			}
		}
		
	}
	
	
	/**
	 * 통계용 데이터 목록 리턴
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="getStatsDatas.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getStatsDatas(HttpServletRequest request, Model model)  {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		//
		String gbn = "" + paramMap.get("gbn");
		String bsnsCode = ""+paramMap.get("bsnsCode");
		String sidoCode = "" + paramMap.get("sidoCode");
		
		//
		if("byYear".equals(gbn)) {
			model.addAllAttributes(service.getStatsDatasByYear(bsnsCode));
			
		}else if("bySido".equals(gbn)) {
			model.addAllAttributes(service.getStatsDatasBySido(bsnsCode));
			
		}else if("bySigungu".equals(gbn)) {
			model.addAllAttributes(service.getStatsDatasBySigungu(bsnsCode, sidoCode));
			
		}else if("byAge".equals(gbn)) {
			model.addAllAttributes(service.getStatsDatasByAge(bsnsCode));
			
		}else if("byPrdlstSet".equals(gbn)) {
			model.addAllAttributes(service.getStatsDatasByPrdlstSet(bsnsCode));			
		}
		
		return JSON_VIEW;
	}
	
	
	/**
	 * 유사 경영체 건수 목록 조회
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception 
	 */
	@RequestMapping(value="getSimilrAtmnentCounts.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getSimilrAtmnentCounts(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, IOException  {
		CsMap paramMap = CsWebUtil.toParamMap(request);
		
		//
		CsTransferObject trans = similrAtmnentService.getCountsByBsnsCode( paramMap.getString("bsnsCodes").split(",", -1)
				, paramMap.getString("sexdstn")
				, paramMap.getString("age")
				, paramMap.getString("farmCareer")
				, paramMap.getString("ctvtPrdlstSetCode")
				, paramMap.getString("ctvtPrdlstCode")
				, paramMap.getString("ctvtTyCode")
				, paramMap.getString("arOrCo"));
		
		//
		model.addAllAttributes(trans);
		
		//
		return JSON_VIEW;
	}

	/**
	 * 농업계학교 출신 코드 목록
	 * @return
	 */
	private List<Map<String,Object>> getFarmngSchulOrginAts() {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> d1 = new HashMap<String, Object>();
		results.add(d1);
		d1.put("code", "Y");
		d1.put("codeNm", "농업계학교 출신");
		
		Map<String,Object> d2 = new HashMap<String, Object>();
		results.add(d2);
		d2.put("code", "N");
		d2.put("codeNm", "농업계학교 출신 아님");

		//
		return results;
	}

	/**
	 * 성별 코드 목록
	 * @return
	 */
	private List<Map<String,Object>> getSexdstns() {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> d1 = new HashMap<String, Object>();
		results.add(d1);
		d1.put("code", "M");
		d1.put("codeNm", "남자");
		
		Map<String,Object> d2 = new HashMap<String, Object>();
		results.add(d2);
		d2.put("code", "F");
		d2.put("codeNm", "여자");

		//
		return results;
	}

	/**
	 * 지원영역 목록
	 * @return
	 */
	private List<Map<String,Object>> getSportRelmCodes() {
		List<String> list = Arrays.asList("6차산업/도농교류","안전/복지", "교육/컨설팅", "생산/유통기반 확충", "유통/마케팅", "경영안정지원", "귀농/귀촌/창업");
		
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		//
		for(String s : list) {
			Map<String, Object> m = new HashMap<String, Object>();
			results.add(m);
			m.put("code", s);
			m.put("codeNm", s);
			
		}

		//
		return results;
	}

	/**
	 * 지원유형 코드 목록
	 * @return
	 */
	private List<Map<String,Object>> getSportTyCodes() {
		List<String> list = Arrays.asList("보조사업", "융자사업");
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		//
		for(String s : list) {
			Map<String,Object> d = new HashMap<String, Object>();
			results.add(d);
			d.put("code", s);
			d.put("codeNm", s);
		}
		//
		return results;
	}
	
	
}
