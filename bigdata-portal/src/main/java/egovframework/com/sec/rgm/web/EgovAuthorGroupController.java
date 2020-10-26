package egovframework.com.sec.rgm.web;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sec.rgm.service.AuthorGroup;
import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import bigdata.portal.entity.PageInfo;

/**
 * 권한그룹에 관한 controller 클래스를 정의한다.
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
 *   2011.08.04  서준식          mberTyCodes 구분자 부분 추가
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */


@Controller
@SessionAttributes(types=SessionVO.class)
public class EgovAuthorGroupController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "egovAuthorGroupService")
    private EgovAuthorGroupService egovAuthorGroupService;
    
    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Autowired EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    /**
	 * 권한 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/sec/rgm/EgovAuthorGroupListView.do")
    public String selectAuthorGroupListView() throws Exception {

        return "egovframework/com/sec/rgm/EgovAuthorGroupManage";
    }    
    
    // \A/uss/umt/.*\.do.*\Z

	/**
	 * 그룹별 할당된 권한 목록 조회
	 * @param authorGroupVO AuthorGroupVO
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="권한그룹관리", listUrl="/sec/rgm/EgovAuthorGroupList.do", order = 70,gid = 20)
    @RequestMapping(value="/sec/rgm/EgovAuthorGroupList.do")
	public String selectAuthorGroupList(@ModelAttribute("authorGroupVO") AuthorGroupVO authorGroupVO,
			                            @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
			                             ModelMap model) throws Exception {

    	/** paging */

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(authorGroupVO.getPageIndex());
		pageInfo.setRecordCountPerPage(authorGroupVO.getPageUnit());
		pageInfo.setPageSize(authorGroupVO.getPageSize());
		int articleNo = pageInfo.getArticleNo();

		
		authorGroupVO.setFirstIndex(pageInfo.getFirstRecordIndex());
		authorGroupVO.setLastIndex(pageInfo.getLastRecordIndex());
		authorGroupVO.setRecordCountPerPage(pageInfo.getRecordCountPerPage());
		
		authorGroupVO.setAuthorGroupList(egovAuthorGroupService.selectAuthorGroupList(authorGroupVO));
        model.addAttribute("authorGroupList", authorGroupVO.getAuthorGroupList());
        
        int totCnt = egovAuthorGroupService.selectAuthorGroupListTotCnt(authorGroupVO);
        pageInfo.setTotalRecordCount(totCnt);
        
		model.addAttribute("articleNo", articleNo);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageIndex", authorGroupVO.getPageIndex());
		model.addAttribute("totalCount", totCnt);

    	authorManageVO.setAuthorManageList(egovAuthorManageService.selectAuthorAllList(authorManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());

        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
        
		model.addAttribute("menuNum", 5);
		model.addAttribute("pageNum", 4);
        
        return "egovframework/com/sec/rgm/EgovAuthorGroupManage";
	}

	/**
	 * 그룹에 권한정보를 할당하여 데이터베이스에 등록
	 * @param userIds String
	 * @param authorCodes String
	 * @param regYns String
	 * @param authorGroup AuthorGroup
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/sec/rgm/EgovAuthorGroupInsert.do")
	public String insertGroupAuthor(@RequestParam("userIds") String userIds,
									@RequestParam("ids") String ids,
			                        @RequestParam("authorCodes") String authorCodes,
			                        @RequestParam("regYns") String regYns,
			                        @RequestParam("mberTyCodes") String mberTyCodes,// 2011.08.04 수정 부분
			                        @ModelAttribute("authorGroup") AuthorGroup authorGroup,
			                         SessionStatus status,
			                         ModelMap model) throws Exception {
		
    	String [] strUserIds = userIds.split(";");
    	String [] strIds = ids.split(";");
    	String [] strRegYns = regYns.split(";");
    	String [] strAuthorCodes = authorCodes.split(";");    	
    	String [] strMberTyCodes = mberTyCodes.split(";");// 2011.08.04 수정 부분
    	ArrayList<AuthorGroup> authorGroupList = new ArrayList<AuthorGroup>();
    	
    	for(int i=0; i<strUserIds.length;i++) {
    		AuthorGroup ag = new AuthorGroup();    		
    		ag.setUserId(strIds[i]);
    		ag.setUniqId(strUserIds[i]);
    		ag.setAuthorCode(strAuthorCodes[i]);
    		ag.setMberTyCode(strMberTyCodes[i]);// 2011.08.04 수정 부분
    		
    		authorGroupList.add(ag);
    		
    		if(strRegYns[i].equals("N"))
    		    egovAuthorGroupService.insertAuthorGroup(ag);
    		else 
    		    egovAuthorGroupService.updateAuthorGroup(ag);
    		
    	}

        status.setComplete();
        model.addAttribute("message", egovMessageSource.getMessage("success.common.insert"));
        model.addAttribute("log", "success.authorGroup.update");
        model.addAttribute("authorGroupList", authorGroupList);
        
        // 권한 변경 시 서버 재시작 안해도 반영
        databaseSecurityMetadataSource.reload();
        
		return "forward:/sec/rgm/EgovAuthorGroupList.do";
	}
}