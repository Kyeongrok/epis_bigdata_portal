package bigdata.portal.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 보유데이터 맵 시각화 컨트롤러
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
@RequestMapping("/bdp/datamap")
public class DataMapController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataMapController.class);
	
	
	@RequestMapping(value="/main.do", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) throws Exception {
	
		return "bigdata/portal/datamap/main";
	}
}
