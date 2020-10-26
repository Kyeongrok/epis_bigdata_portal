package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface VisualizeMapper {

	/**
	 * 시각화 입력
	 * @param param
	 * @return
	 */
	public int insertVisualize(HashMap<String, Object> param);

	/**
	 * 시각화 목록 개수
	 * @param param
	 * @return
	 */
	public int selectVisualizeCount(HashMap<String, Object> param);

	/**
	 * 시각화 목록 리스트
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectVisualizeList(HashMap<String, Object> param);

	/**
	 * 시각화 정보
	 * @param param
	 * @return
	 */
	public EntityMap selectVisualize(HashMap<String, Object> param);

	/**
	 * 시각화 정보 삭제
	 * @param param
	 * @return
	 */
	public int deleteVisualizeRow(HashMap<String, Object> param);
	
	
	public List<EntityMap> selectVisualizeListMe(HashMap<String, Object> param);
}
