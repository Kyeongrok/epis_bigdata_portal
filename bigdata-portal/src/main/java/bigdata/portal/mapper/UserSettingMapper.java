package bigdata.portal.mapper;

import java.util.HashMap;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface UserSettingMapper {
	public EntityMap selectUserSetting(HashMap<String, Object> param);
	public EntityMap selectUserInfo(HashMap<String, Object> param);
}
