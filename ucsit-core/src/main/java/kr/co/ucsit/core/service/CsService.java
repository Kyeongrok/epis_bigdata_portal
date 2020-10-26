/**
 * 
 */
package kr.co.ucsit.core.service;

import java.util.List;
import java.util.Map;

import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;

/**
 * CsWebService와 동일
 * @author hyunseongkil
 * @since
 * 	20200204	init
 */
public interface CsService {
	/**
	 * 상세조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject get(T map);
	
	/**
	 * 상세조회
	 * @param pk
	 * @return
	 */
	CsTransferObject getByPk(String pk);
	
	/**
	 * 상세조회
	 * @param pk1
	 * @param pk2
	 * @return
	 */
	CsTransferObject getByPks(String pk1, String pk2);
	
	/**
	 * 목록
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject gets(T map);
	
	/**
	 * 등록
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject regist(T map);
	
	/**
	 * 수정
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject updt(T map);
	
	/**
	 * 삭제
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject delete(T map);
	
	/**
	 * 삭제
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject deletes(T map);
	
	/**
	 * n건 삭제
	 * @param map
	 */
	CsTransferObject deletes(List<String> pks, CsMap loginResult);
	
	/**
	 * 등록(기 저장된 값이 없으면) 또는 수정(기 저장된 값이 있으면)
	 * oracle의 merge into, phoenix의 upsert 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends Map> CsTransferObject registOrUpdt(T map);
}
