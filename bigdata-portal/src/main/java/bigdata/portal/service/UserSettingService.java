package bigdata.portal.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.UserSettingMapper;

@Service("userSettingService")
public class UserSettingService {
	@Autowired private UserSettingMapper userSettingMapper;

	/**
	 * 유저별 설정된 서비스 정보 가져오기
	 * 
	 * @param userId
	 * @return
	 */
	public EntityMap selectUserSetting(String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		
		EntityMap entityMap = userSettingMapper.selectUserSetting(param);
		return entityMap;
	}

	/**
	 * 유저별 설정된 서비스 정보 가져오기
	 * @param userId
	 * @return
	 */
	public EntityMap selectUserInfo(String userId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		
		EntityMap entityMap = userSettingMapper.selectUserInfo(param);
		
		return entityMap;
	}
	
	
	/**
	 * 그룹 변경
	 * @param userId
	 */
	public void changePermissionGroup(String userId) {
		
		/*
		 	AuthorGroupVO authorGroupVO = new AuthorGroupVO();
			authorGroupVO.setSearchKeyword(uniqId);
			authorGroupVO.setAuthorCode("ROLE_USER");   			
			
			// 포털 계정 최초 권한 설정
			egovAuthorGroupService.insertAuthorGroup(authorGroupVO, entrprsManageVO); 
		
		*/
		
	}

}
