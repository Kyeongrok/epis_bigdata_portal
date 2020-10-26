package bigdata.portal.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.hive.HdfsData;
import bigdata.portal.web.extend.util.HdfsConnector;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovUserDetailsService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

/**
 * Hive 데이터 조회 지원을 위한 서비스 클래스
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 8.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 8.
 */
/**
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 29.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 29.
 */
@Service
public class HdfsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HdfsService.class);

	@Value("${hdfs.url}") String hdfsUrl;
	@Value("${hdfs.homedir}") String hdfsHomedir;
	@Resource(name = "egovUserDetailsService")
	private EgovUserDetailsService egovUserDetailsService;

	public static enum StoreType {
		ADDING, OVERWRITE, OVERWRITE_ALL
	}

	private FileSystem hdfs = null;

//	@PostConstruct
	public void initialize() throws IOException {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			String hadoopHomeDir = getExeLocation();
			System.setProperty("hadoop.home.dir", hadoopHomeDir);
			
			//System.out.println(hadoopHomeDir);

			File file = new File(hadoopHomeDir + "/bin/winutils.exe");
			file.mkdirs();
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					LOGGER.error("file not found winutils.exe");
				}
			}
		}
	}

//	@PreDestroy
	public void destory() throws IOException {
		// CLOSE
		if (hdfs != null)
			hdfs.close();
	}

	/**
	 * winutils 저장 경로 찾기
	 * 
	 * @return
	 */
	public String getExeLocation() {
		String path = "";

		path = this.getClass().getClassLoader().getResource(".").getPath();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			path = path.substring(1);
		}
		
		path = FilenameUtils.getFullPath(path);
		path = path.replaceAll("[/]+(lib)[/]+$", "/");
		return path;
	}

	/**
	 * 로그인 할 때 실행
	 * 
	 * @return
	 */
	public boolean fileSystemOpen() throws IOException {
		// OPEN
		LoginVO user = (LoginVO) egovUserDetailsService.getAuthenticatedUser();
		String hadoopSysUser = System.getProperty("HADOOP_USER_NAME");
		
		//System.out.println(user.toString());
		
		if (user == null || user.getId() == null || user.getId().equals("")) {
			destory();
		}

		if (hdfs == null) {
			try {
				Configuration config = new Configuration();
				config.set("fs.defaultFS", hdfsUrl);

				config.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
				config.set("fs.file.impl", LocalFileSystem.class.getName());
				config.set("hadoop.job.ugi", user.getId());

				System.setProperty("HADOOP_USER_NAME", user.getId());
				System.setProperty("hadoop.home.dir", hdfsUrl + "/" + user.getId());

				hdfs = FileSystem.get(config);
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 비회원 hdfs 파일 접근할때 실행
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public boolean nonUserfileSystemOpen() throws IOException {
		
		String userId = "hdfs";
		String hadoopSysUser = System.getProperty("HADOOP_USER_NAME");
		
		if (hdfs == null) {
			try {
				Configuration config = new Configuration();
				config.set("fs.defaultFS", hdfsUrl);

				config.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
				config.set("fs.file.impl", LocalFileSystem.class.getName());
				config.set("hadoop.job.ugi", userId);

				System.setProperty("HADOOP_USER_NAME", userId);
				System.setProperty("hadoop.home.dir", hdfsUrl + "/" + userId);

				hdfs = FileSystem.get(config);
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}	

	/**
	 * 로그아웃 할 때 실행
	 * 
	 * @return
	 */
	public void fileSystemClose() throws IOException {
		destory();
	}

	/**
	 * 파일 경로 생성
	 * 
	 * @param hdfsStr
	 * @return
	 */
	private Path getHdfsPath(String hdfsStr) {
		if (hdfsStr.trim().startsWith("hdfs:")) {
			return new Path(hdfsStr);
		} else {
			return new Path(hdfsUrl + "/" + hdfsStr);
		}
	}

	/**
	 * 디렉터리 생성
	 * 
	 * @param hdfsStr
	 * @throws IOException
	 */
	public void mkdirs(String hdfsStr) throws IOException {
		Path hdfsPath = getHdfsPath(hdfsStr);
		if (hdfs.isDirectory(hdfsPath))
			return;
		hdfs.mkdirs(hdfsPath);
	}

	/**
	 * 파일 삭제
	 * 
	 * @param hdfsStr
	 * @return
	 */
	public boolean isExistsFile(String hdfsStr) {
		try {
			Path hdfsPath = getHdfsPath(hdfsStr);
			return hdfs.exists(hdfsPath);
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * 파일 삭제
	 * 
	 * @param hdfsStr
	 * @return
	 */
	public boolean deleteFile(String hdfsStr) {
		try {
			Path hdfsPath = getHdfsPath(hdfsStr);
			return hdfs.delete(hdfsPath, true);
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * 파일 전송
	 * 
	 * @param localStr
	 * @param hdfsStr
	 * @throws IOException
	 */
	public void putFile(String localStr, String hdfsStr) throws IOException {
		Path localPath = new Path(localStr);
		Path hdfsPath = getHdfsPath(hdfsStr);
		hdfs.copyFromLocalFile(localPath, hdfsPath);
	}

	/**
	 * 파일 다운로드
	 * 
	 * @param hdfsStr
	 * @param localStr
	 * @throws IOException
	 */
	public void getFile(String hdfsStr, String localStr) throws IOException {
		Path localPath = new Path(localStr);
		Path hdfsPath = getHdfsPath(hdfsStr);
		hdfs.copyToLocalFile(false, hdfsPath, localPath, true);
	}

	/**
	 * 파일 저장
	 * 
	 * @param data
	 * @param hdfsStr
	 * @throws IOException
	 */
	public void writeFile(String data, String hdfsStr) throws IOException {
		FSDataOutputStream fsOutput = fileOutputStreamOpen(hdfsStr);
		fsOutput.write(data.getBytes());
		fsOutput.close();
	}

	/**
	 * TODO: 파일 읽기
	 * 
	 * @param hdfsStr
	 * @return
	 * @throws IOException
	 */
	public String readFile(String hdfsStr) throws IOException {
		return "";
	}
		
	/**
	 * TODO: CSV 파일 읽기
	 * 
	 * @param hdfsStr
	 * @param limit
	 * @return
	 * @throws IOException
	 */
	public List<Object[]> readFileCSV(String hdfsStr, int limit) throws IOException {
		// limit == 0 : 제한없음
		return null;
	}
	
	/**
	 * TODO: 엑셀 파일 읽기
	 * 
	 * @param hdfsStr
	 * @param limit
	 * @return
	 * @throws IOException
	 */
	public List<Object[]> readFileXLS(String hdfsStr, int limit) throws IOException {
		return null;
	}	

	/**
	 * Output 스트림 오픈
	 * 
	 * @param hdfsStr
	 * @return
	 * @throws IOException
	 */
	public FSDataInputStream fileStreamOpen(String hdfsStr) throws IOException {
		Path hdfsPath = getHdfsPath(hdfsStr);
		FSDataInputStream fsInput = hdfs.open(hdfsPath);
		return fsInput;

	}

	/**
	 * Output 스트림 오픈
	 * 
	 * @param hdfsStr
	 * @return
	 * @throws IOException
	 */
	public FSDataOutputStream fileOutputStreamOpen(String hdfsStr) throws IOException {
		Path hdfsPath = getHdfsPath(hdfsStr);
		FSDataOutputStream fsOutput = hdfs.create(hdfsPath);
		return fsOutput;
	}

	/**
	 * 하둡 파일 사이즈
	 * 
	 * @param hdfsStr
	 * @return
	 * @throws IOException
	 */
	public long fileStreamSize(String hdfsStr) throws IOException {
		long fileSize = 0L;
		Path hdfsPath = getHdfsPath(hdfsStr);
		FileStatus fileStatus = hdfs.getFileStatus(hdfsPath);
		fileSize = fileStatus.getLen();
		return fileSize;
	}

	/**
	 * 하둡 파일 리스트
	 * 
	 * @param hdfsStr
	 * @param subDir
	 * @return
	 * @throws IOException
	 */
	public List<HashMap<String, String>> fileStreamList(String hdfsStr, boolean subDir) throws IOException {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		Path hdfsPath = getHdfsPath(hdfsStr);
		RemoteIterator<LocatedFileStatus> hdfsList = hdfs.listFiles(hdfsPath, subDir);

		while (hdfsList.hasNext()) {
			HashMap<String, String> listMap = new HashMap<String, String>();
			LocatedFileStatus hdfsRead = (LocatedFileStatus) hdfsList.next();

			listMap.put("filePath", hdfsRead.getPath().getParent().toString());
			listMap.put("fileName", hdfsRead.getPath().getName().toString());
			listMap.put("fileSize", String.valueOf(hdfsRead.getLen()));
			list.add(listMap);
		}

		return list;
	}

	/**
	 * 데이터 저장
	 * 
	 * @param inputData
	 * @return
	 */
	public boolean inputData(HdfsData inputData) {
		String filePath = inputData.getTableId().toLowerCase() + "/" + inputData.getSubPath() + "/" + inputData.getFileName();

		filePath = FilenameUtils.normalize(filePath);

		String data = inputData.getData();
		if (data == null || data.trim().equals("")) {
			return false;
		}
		data = data.trim();

		// RENAME
		if (!inputData.isOverwrite()) {
			String tmpFilePath = filePath;
			int num = 1;
			while (isExistsFile(tmpFilePath)) {
				tmpFilePath = filePath + "." + num;
			}
			filePath = tmpFilePath;
		}

		try {
			// HDFS 파일 쓰기
			String hdfsPath = hdfsUrl + hdfsHomedir + "/" + filePath;
			writeFile(data, hdfsPath);
		} catch (IOException e) {
			return false;
		}

		return false;
	}
	
	/**
	 * TODO : hdfs 파일 중복 확인
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public boolean isDupFile(String filePath) throws IOException {
		Path path = new Path(filePath);
		if(hdfs.exists(path)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	public boolean inputData(String data, String dataId) {


		if (data == null || data.trim().equals("")) {
			return false;
		}
		data = data.trim();

		
		String hdfsFilePath = "/user/student01/"+dataId+".csv";
		
		try {
			if(isDupFile(hdfsFilePath)) {
				String timeMillis = String.valueOf(System.currentTimeMillis());
				hdfsFilePath = "/user/student01/"+dataId+timeMillis+".csv";
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return false;
		}

		try {
			// HDFS 파일 쓰기
			//String hdfsPath = hdfsUrl + hdfsHomedir + "/" + filePath;
			writeFile(data, hdfsFilePath);
		} catch (IOException e) {
			return false;
		}

		return false;
	}
	*/


	public EntityMap selectRawData(HashMap<String, Object> param) {
		return null;
	}
	
	/**
	 * HDFS에 디렉토리 생성
	 * @param userId
	 */
	public void makeHdfsUserDir(String userId) {		
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		hdfs.mkdirToUser(userId);
		hdfs.disconnect();		
	}
	
	/**
	 * HDFS에 디렉토리 삭제
	 * @param userId
	 */
	public void removeHdfsUserDir(String userId) {		
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		hdfs.rmdirToUser(userId);
		hdfs.disconnect();			
	}
	
	public boolean removeHdfsFile(String path) {
		boolean isRemoveFile = false;
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		isRemoveFile = hdfs.rmFileToUser(path);
		hdfs.disconnect();
		
		return isRemoveFile;
	}
	
	/**
	 * @param userId
	 * @param quotaSize
	 * @throws IOException
	 */
	public void setQuotaSize(String userId, long quotaSize) throws IOException {
		
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		hdfs.setQuotaSize(userId, quotaSize);
	}
	
	/**
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public long getQuotaSize(String userId) throws IOException {
		
		long quotaSize = -1;		
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		quotaSize = hdfs.getQuotaSize(userId);
		
		return quotaSize;
	}
	
	/**
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public long getDirSize(String userId) throws IOException {	
		
		System.out.println("HDFS URL = " + hdfsUrl);
		
		long dirSize = -1;		
		HdfsConnector hdfs = new HdfsConnector(hdfsUrl);
		hdfs.connect();
		dirSize = hdfs.getDirSize(userId);
		
		return dirSize;
	}
	
	public String getDirPath() throws IOException {
		
	    List<String> fileList = new ArrayList<String>();
	    FileStatus[] fileStatus = hdfs.listStatus(hdfs.getHomeDirectory());
	    for (FileStatus fileStat : fileStatus) {	    	
	    	 fileList.add(fileStat.getPath().toString());
	    	 fileList.add(String.valueOf(fileStat.getLen()));
	    	 fileList.add(String.valueOf(new Date(fileStat.getModificationTime())));
	    }
		
		return fileList.toString();
	}
	
	/**
	 * hdfsUrl 정보 리턴
	 * @return
	 */
	public String getHdfsUrl() {
		return hdfsUrl;
	}
	
}
