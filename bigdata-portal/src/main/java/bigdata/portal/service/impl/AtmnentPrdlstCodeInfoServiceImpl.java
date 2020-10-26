/**
 * 
 */
package bigdata.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.AtmnentPrdlstCodeInfoMapper;
import bigdata.portal.service.AtmnentPrdlstCodeInfoService;

/**
 * 품목군, 품목 코드
 * @author hyunseongkil
 *
 */
@Service("atmnentPrdlstCodeInfoService")
public class AtmnentPrdlstCodeInfoServiceImpl extends BigdataServiceImpl implements AtmnentPrdlstCodeInfoService {

	@Autowired
	private AtmnentPrdlstCodeInfoMapper mapper;
	
	
	@Override
	public List<Map<String, Object>> getLclass() {
		return mapper.getLclass();
	}

	@Override
	public List<Map<String, Object>> getAlls() {
		return mapper.getAlls();
	}

}
