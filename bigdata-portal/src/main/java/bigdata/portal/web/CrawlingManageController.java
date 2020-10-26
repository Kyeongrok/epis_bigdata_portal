package bigdata.portal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.CrawlingManageService;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 데이터 분석 컨트롤러
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 8.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 8.
 */
@Controller
@RequestMapping("/bdp/cmng")
public class CrawlingManageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlingManageController.class);
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private CrawlingManageService crawlingManageService;
	
	
	
	/**
	 * 데이터 스케줄러 리스트 
	 * @param 
	 * @param 
	 * @param 
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/scheManagerList.do", method={RequestMethod.GET, RequestMethod.POST})
	public String scheManager(	Model model ) throws Exception {
		
		List<EntityMap> schedulerList = crawlingManageService.selectSchedulerList();
		model.addAttribute("schedulerList", schedulerList);
		
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 2);
		
		return "bigdata/portal/cmng/dataScheList";
	}
	
	/**
	 * 각 데이터 별 수집 히스토리 내용 보기
	 * @param 
	 * @param 
	 * @param 
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/scheHistoryView.do", method={RequestMethod.GET, RequestMethod.POST})
	public String scheHistoryView( @RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
							       @RequestParam(value="dcsId", defaultValue="") String dcsId,	
								   Model model ) throws Exception {
		
		LOGGER.info("dcsId : %s", dcsId);
		if(dcsId.equals("")) {
			return "error";
		}
		int totalCount =0;
		if(dcsId.equals("DCS_STA")) { // 통계 데이터 히스토리
			totalCount = crawlingManageService.getStaCrawlerTotalcount();
		}else if(dcsId.equals("DCS_PDL")) { // 공공데이터 히스토리
			totalCount = crawlingManageService.getPdlCrawlerTotalcount();		
		}else{ // 빅데이터셋 히스토리
			totalCount = crawlingManageService.getBdsCrawlerTotalcount();		
		}
		
		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		
		//pageInfo.setPageSize(10);
		pageInfo.setTotalRecordCount(totalCount);
		
		int articleNo = pageInfo.getArticleNo();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());
		
		String returnPage = "";
		List<EntityMap> crHistoryList = new ArrayList<EntityMap>();
		if(dcsId.equals("DCS_STA")) { // 통계 데이터 히스토리
			crHistoryList = crawlingManageService.selectStaCrawlerHistoryList(param);
			returnPage = "bigdata/portal/cmng/dataStaCrHistoryList";
		}else if(dcsId.equals("DCS_PDL")) { // 공공데이터 히스토리
			crHistoryList = crawlingManageService.selectPdlCrawlerHistoryList(param);
			returnPage = "bigdata/portal/cmng/dataStaCrHistoryList";			
		}else{ // 빅데이터셋 히스토리
			crHistoryList = crawlingManageService.selectBdsCrawlerHistoryList(param);
			returnPage = "bigdata/portal/cmng/dataStaCrHistoryList";		
		}		
		
		model.addAttribute("crHistoryList", crHistoryList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", pageIndex);
		model.addAttribute("dcsId", dcsId);
		
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 2);
		
		return returnPage;
	}
	
	/**
	 * 변경된 설정 값 업데이트(수집 주기 / 동작여부)
	 * @param 
	 * @param 
	 * @param 
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/scheManagerUpdateData.do", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public String scheManagerUpdateData( @RequestParam(value="dcsId", defaultValue="") String dcsId,
									   @RequestParam(value="cronString", defaultValue="") String cronString,
									   @RequestParam(value="scheEnable", defaultValue="") String scheEnable,
									   Model model ) throws Exception {
		LOGGER.info("dcsId : %s", dcsId);
		LOGGER.info("cronString : %s", cronString);
		LOGGER.info("scheEnable : %s", scheEnable);
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dcsId", dcsId);
		param.put("cronString", cronString);
		param.put("scheEnable", scheEnable);
		
		int res = crawlingManageService.updateSchedulerData(param);
		if(res < 0)
			model.addAttribute("result","fail");
		else
			model.addAttribute("result","success");
		
		return "jsonView";
	}
}
