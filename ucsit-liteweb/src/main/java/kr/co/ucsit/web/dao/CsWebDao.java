/**
 * 
 */
package kr.co.ucsit.web.dao;

import java.util.List;
import java.util.Map;

/**
 * 모든 dao의 부모 interface
 * @author ucsit
 * @since 2018. 4. 16.
 */
public interface CsWebDao {

	/**
	 * 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> T select(T map);
	
	/**
	 * 목록
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> List<T> selects(T map);
	
	/**
	 * 목록 건수
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> int selectsCount(T map);
	
	/**
	 * 등록
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> void insert(T map);
	
	/**
	 * 수정
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> void update(T map);
	
	/**
	 * 삭제
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> void delete(T map);
	
	/**
	 * 삭제
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> void deletes(T map);
	
	/**
	 * insert or update
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> void upsert(T map);
}
