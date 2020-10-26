package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface AnalysisMapper {
	/**
	 * 데이터 분석 설정 저장
	 * @param param
	 * @return
	 */
	public int insertBdpAnalysis(HashMap<String, Object> param);
	
	/**
	 * 데이터 분석 목록 리턴
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectBdpAnalysisList(HashMap<String, Object> param);

	/**
	 * 데이터 분석 목록 갯수 리턴
	 * @param param
	 * @return
	 */
	public int selectBdpAnalysisCount(HashMap<String, Object> param);
	
	public int deleteBdpAnalysisRow(HashMap<String, Object> param);
	
	public EntityMap selectBdpAnalysisRow(HashMap<String, Object> param);
	
	public int selectBdpAnalysisRowCnt(HashMap<String, Object> param);
	
	// 데이터 분석 결합하기 저장
	public int insertBdpAnalysisDataMerge(HashMap<String, Object> param);
	
	public int selectBdpAnalysisDataMergeCount(HashMap<String, Object> param);
	
	public List<EntityMap> selectBdpAnalysisDataMerge(HashMap<String, Object> param);
	
	public EntityMap selectBdpAnalysisDataMergeRow(HashMap<String, Object> param);
	
	public int deleteBdpAnalysisDataMergeRow(HashMap<String, Object> param);
	
	public int deleteUserMergeData(String userId);
	
	public int deleteUserAnalysisData(String userId);
}
