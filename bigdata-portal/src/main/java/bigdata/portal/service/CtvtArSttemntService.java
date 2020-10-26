/**
 * 
 */
package bigdata.portal.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.service.CsService;

/**
 * 재배 면적 신고
 * @author hyunseongkil
 *
 */
public interface CtvtArSttemntService extends CsService {
	static final Logger LOG = LoggerFactory.getLogger(CtvtArSttemntService.class);
	
	
	/**
	 * 목록 조회
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	void gets() throws IOException, InstantiationException, IllegalAccessException;
}
