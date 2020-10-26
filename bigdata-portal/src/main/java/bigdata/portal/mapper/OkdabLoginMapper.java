package bigdata.portal.mapper;

import java.util.HashMap;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface OkdabLoginMapper {
	/**
	 * 옥답연계 로그인 정보 입력
	 * 
	 * @param param
	 * @return
	 */
	public int insertOkdabLogin(HashMap<String, Object> param);

	/**
	 * 옥답연계 로그인 처리 정보 조회
	 * 
	 * @param param
	 * @return
	 */
	public EntityMap selectOkdabLogin(HashMap<String, Object> param);

	/**
	 * 옥답연계 로그인 처리완료 업데이트
	 * 
	 * @param param
	 * @return
	 */
	public int updateOkdabLogin(HashMap<String, Object> param);
}
