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
import bigdata.portal.mapper.NoticeMapper;
import bigdata.portal.service.AtchmnflGroupService;
import bigdata.portal.service.AtchmnflService;
import bigdata.portal.service.NoticeService;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;

/**
 * 공지사항
 * @author hyunseongkil
 *
 */
@Service("noticeService")
public class NoticeServiceImpl extends BigdataServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper mapper;

	/**
	 * 첨부파일 그룹
	 */
	@Autowired
	private AtchmnflGroupService atchmnflGroupService;

	/**
	 * 첨부파일
	 */
	@Autowired
	private AtchmnflService atchmnflService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends Map> CsTransferObject gets(T paramMap) {
		CsTransferObject trans = new CsTransferObject();

		//
		return trans.putDatas(mapper.gets(paramMap))
				.add("totcnt", mapper.getsCount(paramMap));
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public <T extends Map> CsTransferObject getsRply(T paramMap) {
		CsTransferObject trans = new CsTransferObject();

		return trans.putDatas(mapper.getsRply(paramMap));
	}


	@Override
	public void regist(String bbsGbn, String nttSj, String nttCn, List<CsFileVO> fileVos, String ntcrId, String ntcrNm) {
		String atchFileId="";
		if(CsUtil.isNotEmpty(fileVos)) {
			// 첨부파일 존재하면 등록
			atchFileId = atchmnflGroupService.regist(ntcrId, fileVos);
		}

		//게시판에 등록
		mapper.regist(bbsGbn, mapper.getNttNo(bbsGbn), nttSj, nttCn, atchFileId, ntcrId, ntcrNm);
	}


	@Override
	public void registRply(String nttId, String bbsGbn, String nttSj, String nttCn, Integer parntscttNo, Integer answerLc
						, List<CsFileVO> fileVos, String ntcrId, String ntcrNm, boolean adminChecked) {

		String atchFileId="";
		//CsTransferObject trans = getByNttId(nttId);
		int answerLc1 = mapper.getAnswerLc(parntscttNo);
		mapper.registRply(bbsGbn, mapper.getNttNo(bbsGbn), nttSj, nttCn, parntscttNo, answerLc1, atchFileId, ntcrId, ntcrNm);

		// 관리자확인 업데이트
		// 관리자가 댓글을 단 경우에 한한다.
		if(adminChecked) {
			mapper.updateAnswerByNttId(nttId);
		}
	}


	@Override
	public CsTransferObject getByNttId(String nttId) {
		CsTransferObject trans = new CsTransferObject();

		//조회수 증가
		mapper.updateRdcntByNttId(nttId);

		//데이터 조회
		Map<String,Object> data = mapper.getByNttId(nttId);
		trans.putData(data);

		//첨부파일 존재하면 목록 조회
		if(CsUtil.isNotEmpty(data.get("atchFileId"))) {
			trans.put("files", atchmnflService.getsByAtchFileId(""+data.get("atchFileId")));
		}

		//
		return trans;
	}

	@Override
	public CsTransferObject getByNttNo(Integer nttNo, String bbsGbn) {
		CsTransferObject trans = new CsTransferObject();

		//데이터 조회
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nttNo", nttNo);
		paramMap.put("bbsGbn", bbsGbn);
		Map<String,Object> data = mapper.getByNttNo(paramMap);
		trans.putData(data);

		//첨부파일 존재하면 목록 조회
		if(CsUtil.isNotEmpty(data.get("atchFileId"))) {
			trans.put("files", atchmnflService.getsByAtchFileId(""+data.get("atchFileId")));
		}

		//
		return trans;
	}


	@Override
	public void deleteByNttId(String nttId) {
		mapper.deleteByNttId(nttId);

		//TODO 첨부파일 삭제
	}




	@Override
	public void updt(String nttId, String nttSj, String nttCn, String[] deletedFileNos
			, List<CsFileVO> fileVos, String ntcrId, String ntcrNm) {
		Map<String,Object> data = mapper.getByNttId(nttId);

		//
		if(CsUtil.isNotEmpty(deletedFileNos)){
			// 기존 첨부파일 삭제
			for(String fileNo : deletedFileNos) {
				if(fileNo != null && !"".equals(fileNo))
				atchmnflService.delete(""+data.get("atchFileId"), fileNo);
			}
		}

		//
		String atchFileId="";
		if(CsUtil.isNotEmpty(fileVos)) {
			if(CsUtil.isEmpty(data.get("atchFileId"))) {
				//기존 등록된 첨부파일이 없으면
				atchFileId = atchmnflGroupService.regist(ntcrNm, fileVos);
			}else {
				//기존 등록된 첨부파일 있으면
				atchFileId = "" + data.get("atchFileId");
				for(CsFileVO vo : fileVos) {
					atchmnflService.regist(atchFileId, ntcrId, vo);
				}
			}
		}

		// 첨부파일에 변동 없을시 처리
		if(CsUtil.isEmpty(deletedFileNos) && CsUtil.isEmpty(fileVos) && CsUtil.isNotEmpty(data.get("atchFileId"))) {
			atchFileId = "" + data.get("atchFileId");
		}

		//업데이트
		mapper.update(nttId, nttSj, nttCn, atchFileId, ntcrId, ntcrNm);
	}
}
