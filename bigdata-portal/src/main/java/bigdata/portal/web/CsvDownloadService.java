package bigdata.portal.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.DatasetMapper;
import bigdata.portal.mapper.HistoryMapper;
import bigdata.portal.service.HdfsService;
import egovframework.com.cmm.service.EgovUserDetailsService;

/**
 * CSV 파일 다운로드 서비스
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
 *   2018. 12. 27.     rlaeodnjs          최초 생성
 *      </pre>
 *
 * @since 2018. 12. 27.
 */
@Service
public class CsvDownloadService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvDownloadService.class);
	
	@Autowired private HdfsService hdfsService;
	@Autowired private DatasetMapper datasetMapper;
	@Autowired private EgovUserDetailsService egovUserDetailsService;
	@Autowired private HistoryMapper historyMapper;
	
	@Value("${hdfs.url}") String hdfsUrl;
	@Value("${hdfs.dataset.sta}") String staHdfsPath;
	
	
	// 데이터 결합 csv 다운로드
	public InputStream getAnalysisMergeCsv(EntityMap dsInfo) {
		
		InputStream inputStream = null;
		
		try {
			hdfsService.fileSystemOpen();
			
			String anadmResult = dsInfo.getString("anadmResult");
			String filePath = "";
			filePath = hdfsUrl+"/"+anadmResult;
			//System.out.println("filePath : " + filePath);
			inputStream = hdfsService.fileStreamOpen(filePath);	
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
		}

		
		return inputStream;
	}
	
	
	/**
	 * 통계정보 데이터 리턴
	 * @param dsId
	 * @return
	 */
	public EntityMap getStaDsInfo(String dsId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		EntityMap dsInfo = datasetMapper.selectDatasetDetail(param);
		
		return dsInfo;
	}
	
	/**
	 * 통계표 테이블 csv 다운로드
	 * @param dsInfo
	 * @param prdDe
	 * @return
	 */
	public InputStream getStaTableCsv(EntityMap dsInfo, String prdDe) {
	
		InputStream inputStream = null;
		
		try {
			boolean isLogin =  egovUserDetailsService.isAuthenticated();
			
			if(isLogin == true) {
				hdfsService.fileSystemOpen();
			} else {
				hdfsService.nonUserfileSystemOpen();
			}
			
			String htblId = dsInfo.getString("htblId");
			String dsDatasetPath = dsInfo.getString("dsDatasetPath");
			
			String path = hdfsUrl+"/"+dsDatasetPath+htblId+"/"+prdDe+".csv";
			String filePath = staHdfsPath+"/"+htblId+"/"+prdDe+".csv";
			
			inputStream = hdfsService.fileStreamOpen(path);	
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
		}
		
		return inputStream;
	}
	
	
	/**
	 * 파일 업로드 이력 정보를 리턴 
	 * @param ulId
	 * @param userId
	 * @return
	 */
	public EntityMap selectUserUploadLogInfo(int ulId, String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("ulId", ulId);
		param.put("userId", userId);
		EntityMap logInfo = historyMapper.selectUserUploadLogInfo(param);
		
		return logInfo; 
	}
	public EntityMap selectUserUploadLogInfo(int ulId) {		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("ulId", ulId);
		EntityMap logInfo = historyMapper.selectUserUploadLogInfo(param);
		
		return logInfo;
	}
	
	
	public InputStream getInputStreamHdfsFilePath(String hdfsFilePath) {
		InputStream inputStream = null;
		
		try {
			boolean isLogin =  egovUserDetailsService.isAuthenticated();
			
			if(isLogin == true) {
				hdfsService.fileSystemOpen();
			} else {
				hdfsService.nonUserfileSystemOpen();
			}

			inputStream = hdfsService.fileStreamOpen(hdfsFilePath);
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
		}
		
		return inputStream;
	}
	
	
	// 서버 로컬시스템에서 다운로드
	public FileInputStream getInputStreamLocalFielPath(String filePath) {
		
		FileInputStream fileInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException  e) {
			// TODO: handle exception
			LOGGER.debug(e.getMessage());
		}
		
		return fileInputStream;
	}

}
