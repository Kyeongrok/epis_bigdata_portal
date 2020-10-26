package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface HistoryMapper {
	
	/**
	 * 사용자 업로드 로그 이력 저장
	 * @param param
	 * @return
	 */
	public int insertBdpUserUploadLog(HashMap<String, Object> param);
	
	/**
	 * hdfs파일 컬럼 업데이트
	 * @param param
	 * @return
	 */
	public int updateBdpUserUploadLogHdfsFilePath(HashMap<String, Object> param);
	
	/**
	 * 회원이 등록한 글(분석/결합/시각화)의 이력을 저장
	 * @param param
	 * @return
	 */
	public int insertBdpUserArticelLog(HashMap<String, Object> param);
	
	
	/**
	 * 파일업로드 총 갯수정보를 리턴
	 * @param param
	 * @return
	 */
	public int getTotalCountBdpUserUploadLog(HashMap<String, Object> param);
	
	/**
	 * 파일업로드 이력 리스트 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpUserUploadLogList(HashMap<String, Object> param);
	
	/**
	 * 회원이 등록한 글(분석/결합/시각화)의 이력 리스트를 카운트한다
	 * @param param
	 * @return
	 */
	public int getTotalCountBdpUserArticleLog(HashMap<String, Object> param);
	
	/**
	 * 회원이 등록한 글(분석/결합/시각화)의 이력 리스트를 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpUserArticleLogList(HashMap<String, Object> param);
	
	/**
	 * 파일 업로드 이력정보 리턴
	 * @param param
	 * @return
	 */
	public EntityMap selectUserUploadLogInfo(HashMap<String, Object> param);
	
	/**
	 * 사용자 글등록, 삭제 내역 제거
	 * @param param
	 * @return
	 */
	public int deleteUserArticleLog(String userId);
	
	
	/**
	 * 업로드 내역 제거
	 * @param param
	 * @return
	 */
	public int deleteUserUploadLog(String userId);
	
	
}
