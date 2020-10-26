package bigdata.portal.web;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.PublicDatasetService;
import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * 공공데이터 호출 Controller
 *
 * @author THEIMC KDW
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 15.     KDW          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 15.
 */
@RequestMapping("/bdp/dataset/public")
@Controller
public class PublicDatasetController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicDatasetController.class);
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private PublicDatasetService publicDatasetService;
		
	/**
	 * 공공데이터 목록 화면
	 * @param pageIndex
	 * @param searchListSel
	 * @param searchListWord
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,
							@RequestParam(value="searchListSel", required=false, defaultValue="") String searchListSel,
							@RequestParam(value="searchListWord", required=false, defaultValue="") String searchListWord,
							Model model
						) throws Exception {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		// 검색
		if(!searchListSel.isEmpty()) {
			param.put("searchListSel", searchListSel);
		}
		
		if(!searchListWord.isEmpty()) {
			param.put("searchListWord", searchListWord);
		}

		int totalCount = publicDatasetService.getTotalCount(param);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(propertiesService.getInt("pageUnit"));
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(totalCount);		
		
		int articleNo = pageInfo.getArticleNo();
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> publicDataList = publicDatasetService.selectPublicDataList(param);

		model.addAttribute("searchListSel", searchListSel);
		model.addAttribute("searchListWord", searchListWord);

		model.addAttribute("publicDataList", publicDataList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);

		model.addAttribute("menuNum", 1);
		model.addAttribute("pageNum", 3);
		
		return "bigdata/portal/dataset/public/list";
	}
	
}