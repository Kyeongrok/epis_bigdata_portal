package bigdata.portal.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.Code;
import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.CodeMap;
import bigdata.portal.mapper.CodeMapper;

@Service
public class CodeService {
	@Autowired private CodeMapper codeMapper;
	
	/**
	 * @param codeId
	 * @return
	 */
	public Code selectCode(String codeId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("codeId", codeId);
		
		Code code = codeMapper.selectCode(param);
		return code;
	}
	
	/**
	 * @param codeId
	 * @return
	 */
	public List<CodeDetail> selectCodeDetail(String codeId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("codeId", codeId);
		
		List<CodeDetail> codeDetailList = codeMapper.selectCodeDetailList(param);
		return codeDetailList;
	}
	
	/**
	 * @param codeId
	 * @param code
	 * @return
	 */
	public CodeDetail selectCodeDetail(String codeId, String code) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("codeId", codeId);
		param.put("code", code);
		
		CodeDetail codeDetail = codeMapper.selectCodeDetail(param);
		return codeDetail;
	}

	/**
	 * @param param
	 * @return
	 */
	public CodeMap selectCodeAll() {
		List<Code> codeList = codeMapper.selectCodeAll();
		List<CodeDetail> codeDetailList = codeMapper.selectCodeDetailAll();
		
		CodeMap codeMap = new CodeMap();
		for(Code code : codeList) {
			codeMap.put(code.getCodeId(), code);
		}
		for(CodeDetail codeDetail : codeDetailList) {
			codeMap.get(codeDetail.getCodeId()).putCodeDetail(codeDetail);
		}
		return codeMap;
	}	
}
