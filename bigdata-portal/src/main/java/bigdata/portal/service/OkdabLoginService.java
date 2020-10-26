package bigdata.portal.service;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.OkdabLoginMapper;

/**
 * 옥답 연계로그인 서비스
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
 *   2019. 1. 18.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2019. 1. 18.
 */
@Service
public class OkdabLoginService {
	@Autowired private OkdabLoginMapper okdabLoginMapper;

	/**
	 * 연계 로그인 정보 입력
	 * 
	 * @param uuid
	 * @param id
	 * @param name
	 * @param ip
	 * @param tm
	 * @return
	 */
	public int insertOkdabLogin(String uuid, String id, String name, String ip, Date dttm) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("okcUuid", uuid);
		param.put("okcUserId", id);
		param.put("okcUserName", name.trim());
		param.put("okcAccessIp", ip);
		param.put("okcAccessDttm", dttm);
		
		return okdabLoginMapper.insertOkdabLogin(param);
	}

	/**
	 * 처리 여부 업데이트
	 * 
	 * @param uuid
	 * @return
	 */
	public int updateOkdabLogin(String uuid) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("okcUuid", uuid);

		return okdabLoginMapper.updateOkdabLogin(param);
	}

	/**
	 * 연계 로그인 정보 조회
	 * 
	 * @param uuid
	 * @return
	 */
	public EntityMap selectOkdabLogin(String uuid) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("okcUuid", uuid);

		return okdabLoginMapper.selectOkdabLogin(param);
	}
}
