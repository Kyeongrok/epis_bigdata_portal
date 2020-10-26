/**
 * 
 */
package kr.co.ucsit.web.atfi.service;

import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.web.service.CsWebService;

/**
 * @author cs1492
 *
 */
public interface AtchmnflService extends CsWebService {
	public static final String ATCHMNFL_GROUP_NO = "atchmnflGroupNo";

	CsTransferObject saveAtchmnfl(CsMap paramMap);
	
	CsTransferObject gets(String atchmnflGroupno);
}
