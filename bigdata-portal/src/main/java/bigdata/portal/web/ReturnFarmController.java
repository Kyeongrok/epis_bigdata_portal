package bigdata.portal.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.ReturnFarmService;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;


/**
 * 아래 파일 목록은 농업on 서버에도 동일하게 구성되어 똑같이 동작합니다.
 * 만약 기능 수정을 하였다면 농업on 서버의 귀농 관련 소스들도 수정해주시기 바랍니다.
 *
 * ReturnFarmController.java
 * ReturnFarmService.java
 * ReturnFarmMapper.java
 * ReturnFarmMapper.xml
 * (추가 예정)...
 *
 *
 * @author whddb
 *
 */
@Controller
/* @RequestMapping("/bdp/svc/retnFmlg/") */
public class ReturnFarmController extends BigdataController {

	private static Logger log = LoggerFactory.getLogger(ReturnFarmController.class);

	protected static final String P_BIGDATA_RETURNFARM = "svc/retnFmlg/";

	@Autowired private ReturnFarmService returnFarmService;

	/**
	 * 귀농인 정보 입력 화면 이동
	 * */
	@RequestMapping(value = "ReturnFarm", method = RequestMethod.GET)
	public String retnFmlgInfo() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "retnFmlgInfo";
	}

	/**
	 * 본인정보 수정 json
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * */
	@RequestMapping(value = "updtRetnFmlgInfo.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updtRetnFmlgInfo(HttpServletRequest request, Model model, HttpSession session) throws InstantiationException, IllegalAccessException, IOException {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		EntityMap entityMap = new EntityMap();

		for(String key : paramMap.keySet()) {
			entityMap.putNor(key, paramMap.get(key));
		}

		session.setAttribute("retnFmlgInfo", entityMap);
		model.addAttribute("dataObj", session.getAttribute("retnFmlgInfo"));

		returnFarmService.registRetnFmlgInfo(request, session); // 귀농 정보 저장
		return JSON_VIEW;
	}


	/**
	 * 입력화면 동작에 필요한 데이터들
	 * */
	@RequestMapping(value = "retnFmlgInfo.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String retnFmlgInfoJson(HttpServletRequest request, Model model) {

		//고려사항 리스트
		model.addAttribute("cnsdr", returnFarmService.getsCnsdr(new HashMap<>()));
		return JSON_VIEW;
	}



	@RequestMapping(value = "retnFmlgDetail.do", method = RequestMethod.GET)
	public String retnFmlgDetail(HttpServletRequest request, Model model
			) {

		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "retnFmlgDetail";
	}

	/**
	 * 결과보기 입력화면 동작에 필요한 데이터들
	 * */
	@RequestMapping(value = "retnFmlgDetail.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("unchecked")
	public String retnFmlgDetailJson(HttpServletRequest request, Model model,
			HttpSession session) throws InstantiationException, IllegalAccessException, IOException {
		Map<String,Object> dataObjMap = (Map<String, Object>) session.getAttribute("retnFmlgInfo");

		// 귀농인 DB에서 인덱스 조회
		Map<String, Object> modelIdxMap = CsUtil.getData(returnFarmService.getRetnFmlgModelIdx(dataObjMap));
 		if(modelIdxMap == null) {
			model.addAttribute("result", "fail");
			model.addAttribute("msg", "검색된 모델이 없습니다");
			model.addAttribute("dataObj", session.getAttribute("retnFmlgInfo"));
			return JSON_VIEW;
		}
		dataObjMap.put("index", modelIdxMap.get("index"));
		if(modelIdxMap.get("insteadCtvt") != null ) dataObjMap.put("insteadCtvt", modelIdxMap.get("insteadCtvt"));



		model.addAttribute("dataObj", session.getAttribute("retnFmlgInfo"));
//		log.debug("{}",session.getAttribute("retnFmlgInfo"));
		model.addAttribute("cnsdr", returnFarmService.getsCnsdr(new HashMap<>())); // 고려사항 리스트
		model.addAttribute("areaInfo", returnFarmService.getRetnFmlgAreaInfo(dataObjMap)); // 지역정보

		model.addAttribute("result", "success");
		return JSON_VIEW;
	}

	/**
	 * 유사 귀농인 정보
	 * 항상 값이 static하여 url를 따로 나눔
	 * */
	@RequestMapping(value = "similrRetnFmlgInfo.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("unchecked")
	public String similrRetnFmlgInfo(HttpServletRequest request, Model model, HttpSession session) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		log.debug("{}",paramMap);
		Map<String,Object> dataObjMap = (Map<String, Object>) session.getAttribute("retnFmlgInfo");
		dataObjMap.put("areaInfo", paramMap.get("areaInfo"));
		model.addAttribute("similrRetnFmlgInfo", returnFarmService.getSimilrRetnFmlgInfo(dataObjMap)); // 유사 귀농인 정보
		dataObjMap.remove("areaInfo");
		model.addAttribute("result", "success");

		return JSON_VIEW;
	}


	/**
	 * 선택지역 및 품목 분석
	 * */
	@RequestMapping(value = "selectAreaCtvtInfo.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("unchecked")
	public String selectAreaCtvtInfo(HttpServletRequest request, Model model, HttpSession session) {

		Map<String,Object> dataObjMap = (Map<String, Object>) session.getAttribute("retnFmlgInfo");

		model.addAttribute("selectAreaCtvtInfo", returnFarmService.getSelectAreaCtvtInfo(dataObjMap)); // 선택지역 및 품목 분석
		model.addAttribute("result", "success");

		return JSON_VIEW;
	}

	/**
	 * 희망 품목 리스트 검색
	 * */
	@RequestMapping(value = "srchCtvt.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String srchCtvtJson(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		paramMap.put("keyword", "%"+paramMap.get("keyword")+"%");

		//품목 리스트
		model.addAttribute("ctvtList", returnFarmService.getsCtvt(paramMap));

		return JSON_VIEW;
	}


	@RequestMapping(value = "retnFmlgPolicySearchList.do", method = RequestMethod.GET)
	public String retnFmlgPolicySearchList() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "retnFmlgPolicySearchList";
	}



	@RequestMapping(value = "test1.do", method = RequestMethod.GET)
	public String testView() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "presnatn1";
	}
	@RequestMapping(value = "test1-2.do", method = RequestMethod.GET)
	public String testView1_2() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "presnatn1-2";
	}
	@RequestMapping(value = "test1-3.do", method = RequestMethod.GET)
	public String testView1_3() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "presnatn1-3";
	}

	@RequestMapping(value = "test2.do", method = RequestMethod.GET)
	public String testView2() {
		return P_BIGDATA_PORTAL + P_BIGDATA_RETURNFARM + "presnatn2";
	}

	@RequestMapping(value = "queryTest1.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String queryTest1(HttpServletRequest request,Model model) throws InstantiationException, IllegalAccessException, IOException {
		Map<String, Object> dataObjMap = new HashMap<>();


		dataObjMap.put("index", "2");
		model.addAllAttributes(returnFarmService.getRetnFmlgAreaInfo(dataObjMap)); // 지역정보)
		return JSON_VIEW;
	}
}












