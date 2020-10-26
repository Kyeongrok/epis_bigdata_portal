/**
 * 
 */
package bigdata.portal.web.extend.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 빅데이터 포털에서 HDFS SuperUser 권한으로 기능 수행
 *
 * @author THEIMC DHKim
 * @version 1.0
 * @see
 *
 *      <pre>
* << 개정이력(Modification Information) >>
*
*      수정일         수정자           수정내용
*  -------------    --------    ---------------------------
*   2018. 10. 25.     dhkim      최초 생성
 *      </pre>
 *
 * @since 2018. 10. 25.
 */
public class HdfsConnector implements PrivilegedExceptionAction<FileSystem> {
	
	//TODO: properties 값 로드 문제 확인 필요 (@Value 붙은 변수들)
	
	private String hdfsUrl;

	@Value("${hdfs.superuser}")
	private String hdfsSuperUser;
	
	private FileSystem hdfs = null;	
	private static final Logger LOGGER = LoggerFactory.getLogger(HdfsConnector.class);
	
	public HdfsConnector(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}
	
	/**
	 * SuperUser인 hdfs 계정으로 연결
	 */		
	public void connect() {		
		UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hdfs");
		// UserGroupInformation ugi = UserGroupInformation.createRemoteUser(hdfsSuperUser);		
		
		try {
			this.hdfs = ugi.doAs(this);
		} catch (IOException | InterruptedException e) {			
			e.printStackTrace();
		}		
	}

	@Override
	public FileSystem run() throws Exception {

		Configuration confToHdfs = new Configuration();		
		//confToHdfs.set("fs.default.name", "hdfs://118.33.99.62:8020");
		confToHdfs.set("fs.default.name", hdfsUrl);
		
		System.out.println(hdfsUrl);

		return FileSystem.get(confToHdfs);
	}
	
	/**
	 * user directory 제거
	 * @param userId
	 */
	public void rmdirToUser(String userId) {
		
		Path removeDirPath = new Path(String.format("/%s/%s", "user", userId));
		
		try {
			hdfs.delete(removeDirPath, true);
		} catch (IOException e) {
			LOGGER.error("디렉토리 제거 실패");
			e.printStackTrace();			
		}
		
		LOGGER.info(String.format("%s 회원의 HDFS 디렉토리 삭제 완료.", userId));
	}
	
	public boolean rmFileToUser(String path) {
		Path removeFilePath = new Path(String.format("/%s", path));
		boolean isRemoveFile = false;
		
		try {
			isRemoveFile = hdfs.delete(removeFilePath, true);
		} catch (IOException e) {
			LOGGER.error("파일 제거 실패");
			e.printStackTrace();
		}
		
		return isRemoveFile;

	}
	
	/**
	 * user directory 생성
	 * @param userId
	 */
	public void mkdirToUser(String userId) {

		Path makeDirectoryPath = new Path(String.format("/%s/%s/", "user", userId));
		FsPermission permission = new FsPermission("755");

		try {			
			// 파일 생성 체크
			boolean isExistDir = hdfs.exists(makeDirectoryPath);
			if(isExistDir) {
				throw new FileAlreadyExistsException("파일 또는 디렉토리가 이미 생성되어 있음");
			}
			
			hdfs.mkdirs(makeDirectoryPath, permission);
			hdfs.setOwner(makeDirectoryPath, userId, "hdfs");
		
		} catch (FileAlreadyExistsException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			LOGGER.error("디렉토리 생성 실패");
			e.printStackTrace();		
		}
		
		LOGGER.info(String.format("%s 회원의 HDFS 디렉토리 생성 완료.", userId));
	}
	
	
	public void makeHdfsUserDir(String userId) throws IOException {		
		Path userHomeDir = new Path(String.format("/%s/%s/", "user", userId));
		hdfs.mkdirs(userHomeDir);		
		hdfs.setOwner(userHomeDir, userId, "hdfs");
	}
	
	public long getQuotaSize(String userId) throws IOException {
		Path userHomeDir = new Path(String.format("/%s/%s/", "user", userId));
		return ((DistributedFileSystem) hdfs).getContentSummary(userHomeDir).getSpaceQuota();
	}
	
	public void setQuotaSize(String userId, long quotaSize) throws IOException {
		Path userHomeDir = new Path(String.format("/%s/%s/", "user", userId));
		((DistributedFileSystem) hdfs).setQuota(userHomeDir, HdfsConstants.QUOTA_DONT_SET, quotaSize);		
	}

	public long getDirSize(String userId) throws IOException {
		Path userHomeDir = new Path(String.format("/%s/%s/", "user", userId));
		return ((DistributedFileSystem) hdfs).getContentSummary(userHomeDir).getLength();
	}

	public void removeHdfsUserDir(String userId) throws IOException {
		Path userHomeDir = new Path(String.format("/%s/%s/", "user", userId));
		((DistributedFileSystem) hdfs).delete(userHomeDir, true);		
	}	
	
	
	/**
	 * hdfs 연결 해제 - 디렉토리 생성/제거 후 호출 필
	 */
	public void disconnect() {
		
		try {
			hdfs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void hdfsHomeDirList(String homeDirPath) throws FileNotFoundException, IllegalArgumentException, IOException {
		
	    List<String> fileList = new ArrayList<String>();
	    FileStatus[] fileStatus = hdfs.listStatus(new Path(homeDirPath));
	    for (FileStatus fileStat : fileStatus) {
	    	 fileList.add(fileStat.getPath().toString());
	    }
	    
	    System.out.println(fileList.toString());
		
	}
}
