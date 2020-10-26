package bigdata.portal.web;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.service.ExtendUserViewService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

/**
 * 외부 View 호출(Ambari View, Zepplelin, RStudio 등)
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
@RequestMapping("/bdp/exview")
public class ExtendUserViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendUserViewController.class);
	
	@Autowired
	ExtendUserViewService extendUserViewService;

	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;

	/**
	 * 외부 View 호출
	 * 
	 * @param viewId
	 * @param response
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/view.do")
	public String extendUserView(@RequestParam String viewId, HttpServletRequest request, HttpServletResponse response,
			Model model) throws SQLException {
		// response.addHeader("Content-Security-Policy", "child-src 'self' *://118.33.99.62:*");
		
		HttpSession session = request.getSession();
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		Boolean isAuthenticated = egovUserDetailsService.isAuthenticated();

		String viewUrl = "";
		if (isAuthenticated) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("viewId", viewId);
			param.put("userId", user.getId());

			viewUrl = extendUserViewService.selectUserViewFullUrl(param);
		}

		if (viewUrl == null || viewUrl.equals("")) {
			return HttpStatus.NOT_FOUND.toString();
		}
		
		//System.out.println(user.getAmbariSessionValue());
		
		switch(viewId) {
			case "0090":
				model.addAttribute("v", user.getAmbariSessionValue() + "&g=main");
				break;
				
			case "0050":
				model.addAttribute("v", user.getAmbariSessionValue() + "&g=oozie");
				break;
				
			case "0040":
				model.addAttribute("v", user.getZeppelinSessionValue());
				break;
				
			case "0030":
				model.addAttribute("v", session.getAttribute("package"));
				model.addAttribute("viewUrl", viewUrl);
				
				return "bigdata/portal/exview/rstudio";	
				
			case "0020":
				model.addAttribute("v", user.getAmbariSessionValue() + "&g=hive");
				break;
				
			case "0010":
				model.addAttribute("v", user.getAmbariSessionValue() + "&g=file&user=" + user.getId());
				break;

		}
		
		model.addAttribute("viewUrl", viewUrl);
		
		return "bigdata/portal/exview/extendUserView";
	}
}
