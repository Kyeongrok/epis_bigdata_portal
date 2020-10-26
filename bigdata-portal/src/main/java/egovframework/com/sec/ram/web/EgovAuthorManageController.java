package egovframework.com.sec.ram.web;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import bigdata.portal.entity.PageInfo;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.sec.ram.service.AuthorManage;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 권한관리에 관한 controller 클래스를 정의한다.
 * @author 공통서비스 개발팀 이문준
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이문준          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가s
 *
 * </pre>
 */
 

@Controller
@SessionAttributes(types=SessionVO.class)
public class EgovAuthorManageController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Autowired
	private DefaultBeanValidator beanValidator;
    
    /**
	 * 권한 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/sec/ram/EgovAuthorListView.do")
    public String selectAuthorListView()
            throws Exception {
    	
        return "egovframework/com/sec/ram/EgovAuthorManage";
    }    
    
    /**
	 * 권한 목록을 조회한다
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="권한관리", listUrl="/sec/ram/EgovAuthorList.do", order = 60,gid = 20)
    @RequestMapping(value="/sec/ram/EgovAuthorList.do")
    public String selectAuthorList(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, ModelMap model) throws Exception {
    	   	
    	/** paging */
    	
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(authorManageVO.getPageIndex());
		pageInfo.setRecordCountPerPage(authorManageVO.getPageUnit());		
		pageInfo.setPageSize(authorManageVO.getPageSize());
		int articleNo = pageInfo.getArticleNo();
		
		authorManageVO.setFirstIndex(pageInfo.getFirstRecordIndex());
		authorManageVO.setLastIndex(pageInfo.getLastRecordIndex());
		authorManageVO.setRecordCountPerPage(pageInfo.getRecordCountPerPage());
		
		authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorList(authorManageVO));
        model.addAttribute("authorList", authorManageVO.getAuthorManageList());
        
        int totCnt = egovAuthorManageService.selectAuthorListTotCnt(authorManageVO);
        pageInfo.setTotalRecordCount(totCnt);
        
        
        System.out.println("FirstRecordIndex = "+pageInfo.getFirstRecordIndex());
        System.out.println("LastRecordIndex = "+pageInfo.getLastRecordIndex());
        System.out.println("RecordCountPerPage = "+pageInfo.getRecordCountPerPage());
        System.out.println("totCnt = " + totCnt);

        model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", authorManageVO.getPageIndex());
		model.addAttribute("totalCount", totCnt);

        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
        
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 4);

        return "egovframework/com/sec/ram/EgovAuthorManage";
    } 
    
    /**
	 * 권한 세부정보를 조회한다.
	 * @param authorCode String
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */   
    @RequestMapping(value="/sec/ram/EgovAuthor.do")
    public String selectAuthor(@RequestParam("authorCode") String authorCode,
    	                       @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, 
    		                    ModelMap model) throws Exception {
    	
    	authorManageVO.setAuthorCode(authorCode);

    	model.addAttribute("authorManage", egovAuthorManageService.selectAuthor(authorManageVO));
    	model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
    	
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 4);
    	return "egovframework/com/sec/ram/EgovAuthorUpdate";
    }     

    /**
	 * 권한 등록화면 이동
	 * @return String
	 * @exception Exception
	 */     
    @RequestMapping("/sec/ram/EgovAuthorInsertView.do")
    public String insertAuthorView() throws Exception {
    	

        return "egovframework/com/sec/ram/EgovAuthorInsert";
    }
    
    /**
	 * 권한 세부정보를 등록한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return String
	 * @exception Exception
	 */ 
    @RequestMapping(value="/sec/ram/EgovAuthorInsert.do")
    public String insertAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    BindingResult bindingResult,
    		                    SessionStatus status, 
    		                    ModelMap model) throws Exception {
    	
    	beanValidator.validate(authorManage, bindingResult); //validation 수행
    	ArrayList<AuthorManage> authorManageList = new ArrayList<AuthorManage>();
    	
		if (bindingResult.hasErrors()) { 
			return "egovframework/com/sec/ram/EgovAuthorInsert";
		} else {
	    	egovAuthorManageService.insertAuthor(authorManage);
	    	authorManageList.add(authorManage);
	    	
	        status.setComplete();
	        model.addAttribute("resultMsg", "success.common.insert");
	        model.addAttribute("log", "success.author.insert");
	        model.addAttribute("authorManageList", authorManageList);
	        
			model.addAttribute("menuNum", 5);
			model.addAttribute("pageNum", 4);
	        
	        return "forward:/sec/ram/EgovAuthorList.do";
		}
    }
    
    /**
	 * 권한 세부정보를 수정한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return String
	 * @exception Exception
	 */   
    @RequestMapping(value="/sec/ram/EgovAuthorUpdate.do")
    public String updateAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    BindingResult bindingResult,
    		                    SessionStatus status, 
    		                    Model model) throws Exception {

    	beanValidator.validate(authorManage, bindingResult); //validation 수행
    	ArrayList<AuthorManage> authorManageList = new ArrayList<AuthorManage>();
    	
		if (bindingResult.hasErrors()) {
			return "egovframework/com/sec/ram/EgovAuthorUpdate";
		} else {
	    	egovAuthorManageService.updateAuthor(authorManage);
	    	authorManageList.add(authorManage); 
	    	
	        status.setComplete();	        
	        model.addAttribute("resultMsg", "success.common.update");
	        model.addAttribute("log", "success.author.update");
	        model.addAttribute("authorManageList", authorManageList);
			model.addAttribute("menuNum", 5);
			model.addAttribute("pageNum", 4);
	        
	        return "forward:/sec/ram/EgovAuthorList.do";
		}
    }    

    /**
	 * 권한 세부정보를 삭제한다.
	 * @param authorManage AuthorManage
	 * @return String
	 * @exception Exception
	 */  
    @RequestMapping(value="/sec/ram/EgovAuthorDelete.do")
    public String deleteAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    SessionStatus status,
    		                    Model model) throws Exception {
    	
    	ArrayList<AuthorManage> authorManageList = new ArrayList<AuthorManage>();
    	egovAuthorManageService.deleteAuthor(authorManage);
    	authorManageList.add(authorManage);  
    	
    	status.setComplete();    	
        model.addAttribute("resultMsg", "success.common.delete");
    	model.addAttribute("log", "success.author.delete");
    	model.addAttribute("authorManageList", authorManageList);
    	
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 4);
    	
        return "forward:/sec/ram/EgovAuthorList.do";
    }   
    
    /**
	 * 권한목록을 삭제한다.
	 * @param authorCodes String
	 * @param authorManage AuthorManage
	 * @return String
	 * @exception Exception
	 */  
    @RequestMapping(value="/sec/ram/EgovAuthorListDelete.do")
    public String deleteAuthorList(@RequestParam("authorCodes") String authorCodes,
    		                       @ModelAttribute("authorManage") AuthorManage authorManage, 
    		                        SessionStatus status, Model model) throws Exception {
    	
    	ArrayList<AuthorManage> authorManageList = new ArrayList<AuthorManage>();
    	
    	String [] strAuthorCodes = authorCodes.split(";");
    	for(int i=0; i<strAuthorCodes.length;i++) {
    		AuthorManageVO authorManageVO = new AuthorManageVO();
    		authorManageVO.setAuthorCode(strAuthorCodes[i]);
    		authorManage.setAuthorCode(strAuthorCodes[i]);
    		authorManageVO = egovAuthorManageService.selectAuthor(authorManageVO);
    		egovAuthorManageService.deleteAuthor(authorManage);
    		authorManageList.add(authorManageVO);
    		
    	}
    	
    	status.setComplete();    	
        model.addAttribute("resultMsg", "success.common.delete");
    	model.addAttribute("log", "success.author.delete");
    	model.addAttribute("authorManageList", authorManageList);
    	
    	
    	
        return "forward:/sec/ram/EgovAuthorList.do";
    }    
    
    /**
	 * 권한제한 화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/sec/ram/accessDenied.do")
    public String accessDenied(ModelMap model) throws Exception {
    	model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
    	
        return "egovframework/com/sec/accessDenied";
    } 
}
