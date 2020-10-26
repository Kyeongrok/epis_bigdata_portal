/**
 * 
 */
package bigdata.portal.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ucsit.core.CsTransferObject;

/**
 * 쌀
 * @author hyunseongkil
 *
 */
public interface RiceService {
	static final Logger LOG = LoggerFactory.getLogger(RiceService.class);
	
	
	CsTransferObject getAllDatas() throws InstantiationException, IllegalAccessException, IOException ;
	
	CsTransferObject getSidos();
	
	CsTransferObject getSigungusBySidoCode(String sidoCode);
	
	CsTransferObject getSidosAndSigungus();
}
