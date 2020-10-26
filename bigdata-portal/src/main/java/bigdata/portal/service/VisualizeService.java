package bigdata.portal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.VisualizeMapper;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

/**
 * 시각화 생성 및 리스트 서비스 클래스
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
@Service
public class VisualizeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VisualizeService.class);
	
	public static final int BUFFER_SIZE = 8192;
	public static final String SEPERATOR = File.separator;

	@Autowired private VisualizeMapper visualizeMapper;

	/**
	 * 시각화 리스트 개수 조회
	 * 
	 * @param param
	 * @return
	 */
	public int selectVisualizeCount(HashMap<String, Object> param) {
		int count = visualizeMapper.selectVisualizeCount(param);
		return count;
	}

	/**
	 * 시각화 리스트 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectVisualizeList(HashMap<String, Object> param) {
		List<EntityMap> dataList = visualizeMapper.selectVisualizeList(param);
		return dataList;
	}
	
	public List<EntityMap> selectVisualizeListMe(HashMap<String, Object> param) {
		List<EntityMap> dataList = visualizeMapper.selectVisualizeListMe(param);
		return dataList;
	}

	/**
	 * 시각화 정보 조회
	 * 
	 * @param param
	 * @return
	 */
	public EntityMap selectVisualize(HashMap<String, Object> param) {
		EntityMap data = visualizeMapper.selectVisualize(param);
		return data;
	}

	/**
	 * 시각화 정보 입력
	 * 
	 * @param param
	 * @return
	 */
	public int insertVisualize(HashMap<String, Object> param) {
		int visId = 0;
		visualizeMapper.insertVisualize(param);
		visId = Integer.valueOf(param.get("visId").toString());
		return visId;
	}

	/**
	 * 시각화 정보 삭제
	 * 
	 * @param visId
	 * @param userId
	 * @return
	 */
	public int deleteVisualize(String visId, String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("visId", visId);
		param.put("userId", userId);

		int res = visualizeMapper.deleteVisualizeRow(param);
		return res;
	}

	/**
	 * 시각화 데이터 파일 저장
	 * 
	 * @param is
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public long saveFile(File file, String data) throws IOException {
		// 디렉토리 생성
		byte[] buffer = data.getBytes("UTF-8");
		return saveFile(file, buffer);
	}
	
	/**
	 * 시각화 데이터 파일 저장
	 * 
	 * @param file
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public long saveFile(File file, byte[] buffer) throws IOException {
		// 디렉토리 생성
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		OutputStream os = null;
		long size = 0L;

		try {		
			os = new FileOutputStream(file);
			os.write(buffer);
			size = buffer.length;
		} finally {
			EgovResourceCloseHelper.close(os);
		}

		return size;
	}
	
	/**
	 * 시각화 데이터 파일 읽기
	 * 
	 * @param dataPath
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public long readFile(String dataPath, OutputStream os) throws IOException {
		
		File file = null;
		InputStream is = null;
		int size = 0;
		long filesize = 0L;
		
		try {
			file = new File(dataPath);
			is = new FileInputStream(file);
			byte[] buffer = new byte[BUFFER_SIZE];
			while((size = is.read(buffer)) > -1) {
				os.write(buffer, 0, size);
				filesize += size;
			}
			os.flush();
		} catch(IOException e) {
			LOGGER.error("호출된 시각화 이미지 파일을 찾을수 없습니다.");
		} finally {
			EgovResourceCloseHelper.close(is);
		}
		
		return filesize;
	}
	
	/**
	 * 섬네일 생성
	 * @param imageFile
	 * @param w
	 * @param h
	 * @return
	 * @throws Exception
	 */
	public boolean makeThumbnail(File imageFile, int w, int h) throws Exception { 
		if(imageFile.exists()) {
			Thumbnails.of(imageFile)
				.crop(Positions.CENTER)
			    .size(w, h)
			    .outputFormat("png")
			    .toFile(imageFile);
			
			return true;
		}
		
		return false;
	}
}
