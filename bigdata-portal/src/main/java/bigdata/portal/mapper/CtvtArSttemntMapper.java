/**
 * 
 */
package bigdata.portal.mapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.elastic.service.ElasticService;
import bigdata.portal.entity.CtvtArSttemnt;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.ucsit.core.CsTransferObject;

/**
 * 재배 면적 신고
 * @author hyunseongkil
 *
 */
@Mapper
public class CtvtArSttemntMapper {

	@Autowired
	private ElasticService elasticService;
	
	private static final String INDEX_NAME = "ds_ctvt_ar_sttemnt";
	
	public CsTransferObject gets() throws IOException, InstantiationException, IllegalAccessException {
		ElasticResultMap resultMap = elasticService.getSearch(INDEX_NAME, CtvtArSttemnt.class);
		
		return new CsTransferObject().add("resultMap", resultMap);
	}
}
