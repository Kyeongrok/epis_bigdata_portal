package bigdata.portal.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import bigdata.portal.cmm.util.CommonUtil;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.HistoryMapper;

/**
 * 사용자의 파일업로드 및 컨텐츠를 저장한 이력을 남기기 위한 서비스 클래스
 *
 *
 * @author THEIMC rlaeodnjs
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2019. 2. 13.     rlaeodnjs          최초 생성
 *      </pre>
 *
 * @since 2019. 2. 13.
 */
@Service
public class HistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryService.class);
	
	@Autowired private HistoryMapper historyMapper;
	
	/**
	 * 사용자 파일 업로드 로그이력 저장
	 * @param userId
	 * @param filePath
	 * @param fileRealName
	 * @param fileName
	 * @param fileSize
	 * @return
	 */
	public int saveBdpUserUploadLog(String userId, String filePath, String fileRealName, String fileName, String fileSize, String target) {
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadIp = req.getHeader("X-FORWARDED-FOR");
		if (uploadIp == null)
			uploadIp = req.getRemoteAddr();
		
		String uri = req.getRequestURI();
		
		int ulId = 0;
		
		try {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("ulId", 0);	// insertId 값을 받기 위함
			param.put("userId", userId);
			param.put("filePath", filePath);
			param.put("fileRealName", fileRealName);
			param.put("fileName", fileName);
			param.put("fileSize", fileSize);			
			param.put("uploadIp", uploadIp);
			param.put("uri", uri);
			param.put("fileLocation", "LOCAL");
			param.put("target", target);
			
			historyMapper.insertBdpUserUploadLog(param);
			ulId = Integer.valueOf(param.get("ulId").toString());	
		} catch (Exception e) {
			// TODO: handle exception
			ulId = 0;
			LOGGER.error("회원이 파일 업로드한 이력을 저장하는데 실패하였음!!");
		}
		
		
		return ulId;
	}
	
	
	/**
	 * 사용자 파일 업로드 로그에 hdfs파일경로 이력 수정
	 * @param ulId
	 * @param hdfsFilePath
	 * @return
	 */
	public int updatedLogWriteHdfsFilePath(int ulId, String hdfsFilePath) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("ulId", ulId);
		param.put("hdfsFilePath", hdfsFilePath);
		param.put("fileLocation", "HDFS");
		
		int res = historyMapper.updateBdpUserUploadLogHdfsFilePath(param);
		
		return res;
	}

	
	/**
	 * 회원이 빅데이터 포털사이트에 등록한 글(분석/결합/시각화)의 이력을 저장
	 * @param userId
	 * @param artCont
	 * @param artStatus
	 * @return
	 */
	public int saveBdpUserArticleLog(String userId, String artTarget, String artCont, String artStatus, int artIdx, String artUrl) {
		
		int res = 0;
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null)
			ip = req.getRemoteAddr();
		
		String uri = req.getRequestURI();

		try {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			param.put("artTarget", artTarget);
			param.put("artCont", artCont);
			param.put("artUri", uri);
			param.put("artStatus", artStatus);
			param.put("artIp", ip);
			param.put("artIdx", artIdx);
			param.put("artUrl", artUrl);
			
			res = historyMapper.insertBdpUserArticelLog(param);	
		} catch (Exception e) {
			// TODO: handle exception
			res = 0;
			LOGGER.error("회원이 빅데이터 포털사이트에 등록한 글 이력을 저장하는데 실패하였음!!");
		}
		
		return res;
	}
	
	/**
	 * 파일업로드 총 갯수정보를 리턴
	 * @param param
	 * @return
	 */
	public int getTotalCountBdpUserUploadLog(HashMap<String, Object> param) {
		int count = historyMapper.getTotalCountBdpUserUploadLog(param);
		
		return count;
	}

	/**
	 * 파일업로드 이력 리스트 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpUserUploadLogList(HashMap<String, Object> param) {
		// 관리자, 사용자
		List<EntityMap> rows = historyMapper.selectBdpUserUploadLogList(param);
		
		for(EntityMap row : rows) {
			row.put("fileUnit", CommonUtil.byteCalculation(row.getString("fileSize")));
			// 업로드 페이지 변환
			row.put("targetPage", getUploadPageKorMethod(String.valueOf(row.getString("target"))));			

		}
		
		return rows;
		
	}
	
	/**
	 * 파일업로드 이력 정보를 리턴
	 * @param param
	 * @return
	 */
	public EntityMap selectUserUploadLogInfo(HashMap<String, Object> param) {
		EntityMap info = historyMapper.selectUserUploadLogInfo(param);
		
		return info;
	}
	
	// 파일 업로드를 실행한 페이지명을 한글로 변환
	public String getUploadPageKorMethod(String str) {
		String ret = "";
		if(str.equals("null")) {
			ret = "";
		} else if(str.equals("VISUAL")) {
			ret = "시각화";
		} else if(str.equals("MERGE")) {
			ret = "결합";
		} else if(str.equals("ANALY")) {
			ret = "분석";
		}
		
		return ret;
	}
	
	public String getStatusMethod(String str) {
		String ret = "";
		if(str.equals("REG")) {
			ret = "등록";
		} else if(str.equals("MOD")) {
			ret = "수정";
		} else if(str.equals("DEL")) {
			ret = "삭제";
		}
		
		return ret;
	}
	
	/**
	 * 회원이 등록한 글(분석/결합/시각화)의 이력 리스트를 카운트한다
	 * @param param
	 * @return
	 */
	public int getTotalCountBdpUserArticleLog(HashMap<String, Object> param) {
		int count = historyMapper.getTotalCountBdpUserArticleLog(param);
		return count;
	}
	
	/**
	 * 회원이 등록한 글(분석/결합/시각화)의 이력 리스트를 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpUserArticleLogList(HashMap<String, Object> param) {
		List<EntityMap> dataList = historyMapper.selectBdpUserArticleLogList(param);
		
		for(EntityMap row : dataList) {
			row.put( "artTargetStr", getUploadPageKorMethod(String.valueOf(row.get("artTarget"))) );
			row.put( "artStatusStr", getStatusMethod(String.valueOf(row.get("artStatus"))) );
		}
		
		return dataList;
	}
	
}
