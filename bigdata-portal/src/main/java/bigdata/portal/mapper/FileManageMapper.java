package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface FileManageMapper {

	public List<EntityMap> selectDelToPreviousDay(HashMap<String, Object> param);

}
