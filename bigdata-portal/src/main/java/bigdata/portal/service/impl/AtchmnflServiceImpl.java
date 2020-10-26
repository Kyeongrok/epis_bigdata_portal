/**
 * 
 */
package bigdata.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.AtchmnflMapper;
import bigdata.portal.service.AtchmnflService;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsUtil;

/**
 * 첨부파일
 * @author hyunseongkil
 *
 */
@Service("atchmnflService")
public class AtchmnflServiceImpl extends BigdataServiceImpl implements AtchmnflService {

	@Autowired
	private AtchmnflMapper mapper;
	
	

	@Override
	public List<Map<String, Object>> getsByAtchFileId(String atchFileId) {
		return mapper.getsByAtchFileId(atchFileId);
	}

	@Override
	public Map<String, Object> getByPk(String atchFileId, String fileNo) {
		//목록 조회
		List<Map<String,Object>> datas = mapper.getsByAtchFileId(atchFileId);
		if(CsUtil.isEmpty(datas)) {
			return new HashMap<String, Object>();
		}
		
		//파일 번호가 같으면 리턴
		for(Map<String,Object> d : datas) {
			if(fileNo.equals(d.get("fileNo"))) {
				return d;
			}
		}
		
		return new HashMap<String, Object>();
	}

	@Override
	public String regist(String atchFileId, String frstRegisterId, CsFileVO vo) {
		//파일 번호
		String fileNo = CsUtil.createShortUid("F");
		
		//등록
		mapper.regist(atchFileId, fileNo, vo.getOriginFileName(), vo.getStreFileName(), vo.getStrePathName(), vo.getContentType(), vo.getSize(), frstRegisterId);
		
		//파일 번호
		return fileNo;
	}

	@Override
	public void delete(String atchFileId, String fileNo) {
		mapper.delete(atchFileId, fileNo);
	}

}
