package bigdata.api.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import bigdata.api.service.ApiRestService;
import bigdata.portal.entity.EntityMap;

/**
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
 *   2018. 12. 19.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 12. 19.
 */
@RequestMapping("/api")
public abstract class ApiRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestController.class);
	@Autowired protected ApiRestService apiRestService;
	
	/**
	 * API 키체크
	 * @return
	 */
	protected boolean authCheck() {
		return true;
	}
}
