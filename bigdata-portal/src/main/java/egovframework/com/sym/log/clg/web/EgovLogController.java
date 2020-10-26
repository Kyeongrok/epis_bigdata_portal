package egovframework.com.sym.log.clg.web;

import java.util.HashMap;

import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.sym.log.clg.service.EgovLogService;
import egovframework.com.sym.log.clg.service.Log;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.PageInfo;

/**
 * @Class Name : EgovLogController.java
 * @Description : 접속로그정보를 관리하기 위한 컨트롤러 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------      -------     -------------------
 *    2009. 3. 11. 이삼섭        최초생성
 *    2011. 7. 01. 이기하        패키지 분리(sym.log -> sym.log.clg)
 *    2011.8.26	정진오			IncludedInfo annotation 추가
 *    2018. 11. 09 DHKim	클래스
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */

@Controller
public class EgovLogController {

	@Resource(name="egovLogService")
	private EgovLogService logService;

	@Resource(name="propertiesService")
	protected EgovPropertyService propertyService;

	/**
	 * 로그인 로그 목록 조회
	 *
	 * @param loginLog
	 * @return sym/log/clg/EgovLoginLogList
	 * @throws Exception
	 */
	@IncludedInfo(name="접속로그관리", order = 1080 ,gid = 60)
	@RequestMapping(value="/sym/log/clg/SelectLogList.do")
	public String selectLoginLogInf(@ModelAttribute("searchVO") Log log,
			ModelMap model) throws Exception{
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(log.getPageIndex());
		pageInfo.setRecordCountPerPage(log.getPageUnit());
		pageInfo.setPageSize(log.getPageSize());
		int articleNo = pageInfo.getArticleNo();

		log.setPageUnit(propertyService.getInt("pageUnit"));
		log.setPageSize(propertyService.getInt("pageSize"));
		log.setRecordCountPerPage(log.getPageUnit());
		log.setFirstIndex(pageInfo.getFirstRecordIndex());
		
		HashMap<?, ?> _map = (HashMap<?, ?>) logService.selectLogInf(log);
		
		System.out.println("LOG => " + log.toString());
		int totCnt = Integer.parseInt((String)_map.get("resultCnt"));
		
		pageInfo.setTotalRecordCount(totCnt);
		
		model.addAttribute("resultList", _map.get("resultList"));
		model.addAttribute("resultCnt", _map.get("resultCnt"));
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", log.getPageIndex());
		model.addAttribute("totalCount", totCnt);
		
        System.out.println("FirstRecordIndex = "+pageInfo.getFirstRecordIndex());
        System.out.println("LastRecordIndex = "+pageInfo.getLastRecordIndex());
        System.out.println("RecordCountPerPage = "+pageInfo.getRecordCountPerPage());
        System.out.println("totCnt = " + totCnt);
				
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 1);

		return "egovframework/com/sym/log/clg/EgovLoginLogList";
	}

	/**
	 * 로그인 로그 상세 조회
	 *
	 * @param loginLog
	 * @param model
	 * @return sym/log/clg/EgovLoginLogInqire
	 * @throws Exception
	 */
	@RequestMapping(value="/sym/log/clg/InqireLog.do")
	public String selectLoginLog(@ModelAttribute("searchVO") Log loginLog,
			@RequestParam("logId") String logId,
			ModelMap model) throws Exception{

		loginLog.setLogId(logId.trim());

		Log vo = logService.selectLog(loginLog);
		model.addAttribute("result", vo);
		return "egovframework/com/sym/log/clg/EgovLoginLogInqire";
	}

}
