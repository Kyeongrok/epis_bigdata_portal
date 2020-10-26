/**
 * 
 */
package bigdata.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.AtchmnflGroupMapper;
import bigdata.portal.service.AtchmnflGroupService;
import bigdata.portal.service.AtchmnflService;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsUtil;

/**
 * 첨부파일 그룹
 * @author hyunseongkil
 *
 */
@Service("atchmnflGroupService")
public class AtchmnflGroupServiceImpl extends BigdataServiceImpl implements AtchmnflGroupService {

	@Autowired
	private AtchmnflGroupMapper mapper;
	
	/**
	 * 첨부파일
	 */
	@Autowired
	private AtchmnflService atchmnflService;
	
	@Override
	public String regist(String frstRegisterId, List<CsFileVO> fileVos) {
		//첨부파일 아이디
		String atchFileId = CsUtil.createShortUid("AF");
		
		//등록
		mapper.regist(atchFileId, frstRegisterId);
		
		//첨부파일 등록
		for(CsFileVO vo : fileVos) {
			atchmnflService.regist(atchFileId, frstRegisterId, vo);
		}
		
		//
		return atchFileId;
	}

	@Override
	public Map<String, Object> getByAtchFileId(String atchFileId) {
		return mapper.get(atchFileId);
	}

}
