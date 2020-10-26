/**
 *
 */
package bigdata.portal.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.NoticeMapper;
import bigdata.portal.service.AtchmnflGroupService;
import bigdata.portal.service.AtchmnflService;
import bigdata.portal.service.VisualizePblonsipService;
import kr.co.ucsit.core.CsConst;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;

/**
 * 시각화 공유
 * @author hyunseongkil
 *
 */
@Service("visualizePblonsipService")
public class VisualizePblonsipServiceImpl extends BigdataServiceImpl implements VisualizePblonsipService {

	private static final Logger logger = LoggerFactory.getLogger(VisualizePblonsipServiceImpl.class);

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

	/*첨부파일 root 경로*/
	@Value("${app.upload.file.path}")
	private String uploadFilePath;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends Map> CsTransferObject gets(T paramMap) {
		CsTransferObject trans = new CsTransferObject();

		//
		return trans.putDatas(mapper.gets(paramMap))
				.add("totcnt", mapper.getsCount(paramMap));
	}




	@Override
	public void regist(String bbsGbn, String nttSj, String nttCn, List<CsFileVO> fileVos, String ntcrId, String ntcrNm) {
		String atchFileId="";
		if(CsUtil.isNotEmpty(fileVos)) {
			// 첨부파일 존재하면 등록
			atchFileId = atchmnflGroupService.regist(ntcrId, fileVos);
		}

		//게시판에 등록
		mapper.regist(bbsGbn, null, nttSj, nttCn, atchFileId, ntcrId, ntcrNm);
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
		if(null != data) {
			if(CsUtil.isNotEmpty(data.get("atchFileId"))) {
				trans.put("files", atchmnflService.getsByAtchFileId(""+data.get("atchFileId")));
			}
		}

		//
		return trans;
	}




	@Override
	public void deleteByNttId(String nttId) {
		try {
			//데이터 조회
			Map<String,Object> data = mapper.getByNttId(nttId);
			//파일 조회
			List<Map<String,Object>> files = atchmnflService.getsByAtchFileId(""+data.get("atchFileId"));

			//게시글 삭제
			mapper.deleteByNttId(nttId);
			//첨부파일 삭제
			for(Map<String, Object> file : files) {
				logger.debug("{}",uploadFilePath + file.get("strePathName") + "/"+ file.get("streFileName"));
				FileUtils.forceDelete(new File(uploadFilePath + file.get("strePathName") + "/"+ file.get("streFileName")));
				atchmnflService.delete(String.valueOf(file.get("atchFileId")), String.valueOf(file.get("fileNo")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	@Override
	public void updt(String nttId, String nttSj, String nttCn, String[] deletedFileNos
			, List<CsFileVO> fileVos, String ntcrId, String ntcrNm) {
		Map<String,Object> data = mapper.getByNttId(nttId);

		//
		if(CsUtil.isNotEmpty(deletedFileNos)){
			// 기존 첨부파일 삭제
			for(String fileNo : deletedFileNos) {
				atchmnflService.delete(""+data.get("atchFileId"), fileNo);
			}
		}

		//
		String atchFileId= (data.get("atchFileId") == null)? "" : data.get("atchFileId")+"";
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

		//업데이트
		mapper.update(nttId, nttSj, nttCn, atchFileId, ntcrId, ntcrNm);
	}


}
