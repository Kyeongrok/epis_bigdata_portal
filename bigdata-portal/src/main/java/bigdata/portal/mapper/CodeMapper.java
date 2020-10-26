package bigdata.portal.mapper;

import java.util.HashMap;
import java.util.List;

import bigdata.portal.entity.Code;
import bigdata.portal.entity.CodeDetail;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface CodeMapper {
	public Code selectCode(HashMap<String, String> param);

	public List<Code> selectCodeAll();

	public CodeDetail selectCodeDetail(HashMap<String, String> param);

	public List<CodeDetail> selectCodeDetailList(HashMap<String, String> param);

	public List<CodeDetail> selectCodeDetailAll();
}
