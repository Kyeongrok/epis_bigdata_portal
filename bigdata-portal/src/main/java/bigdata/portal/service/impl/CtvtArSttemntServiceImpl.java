/**
 * 
 */
package bigdata.portal.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.CtvtArSttemntMapper;
import bigdata.portal.service.CtvtArSttemntService;
import kr.co.ucsit.core.CsTransferObject;

/**
 * 재배 면적 신고
 * @author hyunseongkil
 *
 */
@Service("ctvtArSttemntService")
public class CtvtArSttemntServiceImpl extends BigdataServiceImpl implements CtvtArSttemntService {
	

	@Autowired
	private CtvtArSttemntMapper mapper;
	
	
	@Override
	public void gets() throws IOException, InstantiationException, IllegalAccessException {
		CsTransferObject trans = mapper.gets();
		ElasticResultMap emap = (ElasticResultMap) trans.get("resultMap");
		
		LOG.debug("data {}", emap.getData());
		LOG.debug("hits {}", emap.getHits());
		LOG.debug("sources {}", emap.getSources());
		LOG.debug("keys {}", emap.getKeys());
		LOG.debug("vos {}", emap.getVos());
	}

}
