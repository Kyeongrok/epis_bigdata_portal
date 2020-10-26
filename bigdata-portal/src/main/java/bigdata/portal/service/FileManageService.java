package bigdata.portal.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.AnalysisMapper;
import bigdata.portal.mapper.FileManageMapper;
import bigdata.portal.mapper.HistoryMapper;

@Service
public class FileManageService {
	@Autowired private FileManageMapper fileManageMapper;
	@Autowired private AnalysisMapper analysisMapper;
	@Autowired private HistoryMapper historyMapper;

	public List<EntityMap> selectDelToPreviousDay(HashMap<String, Object> param) {
		return fileManageMapper.selectDelToPreviousDay(param);		
	}	
	
	/**
	 * 회원 탈퇴 시 
	 * @param userId
	 * @return
	 */
	public HashMap<String, Object> deleteUserDB(String userId) {
		int userUploadLogCnt = 0;
		int userArticleLogCnt = 0;
		int userMergeDataCnt = 0;
		int userAnalysisDataCnt = 0;
		
		try {
			userAnalysisDataCnt = analysisMapper.deleteUserAnalysisData(userId);
			userMergeDataCnt = analysisMapper.deleteUserMergeData(userId);
			userArticleLogCnt = historyMapper.deleteUserArticleLog(userId);
			userUploadLogCnt = historyMapper.deleteUserUploadLog(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
			
		}
		
		HashMap<String, Object> deleteUserInfo = new HashMap<String, Object>();
		deleteUserInfo.put("uploadLogCnt", userUploadLogCnt);
		deleteUserInfo.put("articleLogCnt", userArticleLogCnt);
		deleteUserInfo.put("mergeDataCnt", userMergeDataCnt);
		deleteUserInfo.put("analysisDataCnt", userAnalysisDataCnt);
		
		
		return deleteUserInfo;
	}
}
