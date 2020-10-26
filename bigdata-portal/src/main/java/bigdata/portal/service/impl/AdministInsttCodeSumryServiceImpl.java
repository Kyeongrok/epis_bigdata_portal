/**
 * 
 */
package bigdata.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.AdministInsttCodeSumryMapper;
import bigdata.portal.service.AdministInsttCodeSumryService;

/**
 * 시도,시군구
 * @author hyunseongkil
 *
 */
@Service("administInsttCodeSumryService")
public class AdministInsttCodeSumryServiceImpl extends BigdataServiceImpl implements AdministInsttCodeSumryService {

	@Autowired
	private AdministInsttCodeSumryMapper mapper;
	
	
	@Override
	public List<Map<String, Object>> getAllSidos() {
		return mapper.getAllSidos();
	}

	@Override
	public List<Map<String, Object>> getAllSigungus() {
		return mapper.getAllSigungus();
	}

}
