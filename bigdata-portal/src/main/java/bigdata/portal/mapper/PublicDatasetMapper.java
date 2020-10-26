package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface PublicDatasetMapper {
	/**
	 * 공공데이터 리스트를 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectPublicDataList(HashMap<String, Object> param);
	
	/**
	 * 공공데이터 목록 갯수를 리턴한다.
	 * @param param
	 * @return
	 */
	public int getTotalCount(HashMap<String, Object> param);
	
	/**
	 * 공공데이터 종별, 데이터셋별 갯수를 리턴한다.
	 * @param param
	 * @return
	 */
	public EntityMap getTypeCount(HashMap<String, Object> param);
	
	/**
	 * 공공데이터 코드정보를 리턴한다.
	 * @param param
	 * @return
	 */
	public EntityMap selectBdpTnDataCodeRow(HashMap<String, String> param);
}
