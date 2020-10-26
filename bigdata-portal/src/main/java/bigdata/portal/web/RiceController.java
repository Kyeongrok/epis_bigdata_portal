/**
 * 
 */
package bigdata.portal.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.service.RiceService;
import kr.co.ucsit.core.CsDateUtil;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

/**
 * 쌀
 * @author hyunseongkil
 *
 */
@Controller
@RequestMapping("/bdp/svc/")
public class RiceController extends BigdataController {
	private static Logger log = LoggerFactory.getLogger(RiceController.class);
	
	@Autowired
	private RiceService service;
	
	
	/**
	 * 쌀 - 시도
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("riceDetail.do")
	public String riceDetail(HttpServletRequest request, Model model) {
	
		model.addAllAttributes(service.getSidosAndSigungus());
		
		return P_BIGDATA_PORTAL + "svc/riceDetail";
	}
	
	/**
	 * 쌀 - 시군구
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("riceSigunguDetail.do")
	public String riceSigunguDetail(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		model.addAllAttributes(paramMap);
		model.addAllAttributes(service.getSidosAndSigungus());
		
		return P_BIGDATA_PORTAL + "svc/riceSigunguDetail";
	}
	
	/**
	 * 비교 폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("riceCmprForm.do")
	public String riceCmprForm(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		model.addAllAttributes(paramMap);
		
		return P_BIGDATA_PORTAL + "svc/riceCmprForm";
	}
	
	
	/**
	 * 나의 생산량 예측 폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("ricePredictForm.do")
	public String ricePredictForm(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		model.addAllAttributes(paramMap);
		
		return P_BIGDATA_PORTAL + "svc/ricePredictForm";
	}
	
	
	
	/**
	 * 시도코드로 시군구 목록 구하기
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception 
	 */
	@RequestMapping(value="getAllRiceDatas.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getAllRiceDatas(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, IOException {
		
		CsMap paramMap = CsWebUtil.toParamMap(request);
		log.debug("{}", paramMap);
		
		
		//
		CsTransferObject trans = service.getAllDatas();
		trans.putAll( service.getSidosAndSigungus());
		
		
		//
		model.addAllAttributes(trans);
		
		return "jsonView";
	}
	
	
	/**
	 * 시도 목록
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getSidos.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getSidos(HttpServletRequest request, Model model) {
		
		model.addAllAttributes(service.getSidos());
		
		//
		return "jsonView";
	}
	
	/**
	 * 시도코드로 시군구 목록 구하기
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getSigungusBySidoCode.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getSigungus(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);
		
		//
		model.addAllAttributes( service.getSigungusBySidoCode(paramMap.get("sidoCode").toString()));
		
		return "jsonView";
	}
	
	/**
	 * 전체 시도 코드, 전체 시군구 코드 목록 구하기
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getSidosAndSigungus.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getSidosAndSigungus(HttpServletRequest request, Model model) {
		
		//
		model.addAllAttributes( service.getSidosAndSigungus());
		
		return "jsonView";
	}
}
