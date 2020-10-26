package bigdata.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.util.CommonUtil;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.mapper.CodeMapper;
import bigdata.portal.mapper.PublicDatasetMapper;

@Service
public class PublicDatasetService {
	@Autowired private CodeMapper codeMapper;
	@Autowired private PublicDatasetMapper publicDataMapper;

	/**
	 * 공공데이터 리스트를 리턴한다.
	 * @param param
	 * @return
	 */
	public List<EntityMap> selectPublicDataList(HashMap<String, Object> param) {

		List<EntityMap> dataList = publicDataMapper.selectPublicDataList(param);
		
		for(EntityMap row : dataList) {
			
			List<String> provdEnncList = new ArrayList<String>();
			
			// 그리드
			if(row.getString("gridProvdEnnc").equals("Y")) {
				provdEnncList.add("G");
			}
			// OpenAPI
			if(row.getString("apiProvdEnnc").equals("Y")) {
				provdEnncList.add("O");
			}
			// File
			if(row.getString("fileProvdEnnc").equals("Y")) {
				provdEnncList.add("F");
			}
			// Link
			if(row.getString("linkProvdEnnc").equals("Y")) {
				provdEnncList.add("L");
			}
			// 원시데이터
			if(row.getString("rawDataProvdEnnc").equals("Y")) {
				provdEnncList.add("R");
			}
			// Chart
			if(row.getString("chartProvdAt").equals("Y")) {
				provdEnncList.add("C");
			}
			// Map
			if(row.getString("mapProvdEnnc").equals("Y")) {
				provdEnncList.add("M");
			}
			
			// 분류 정보
			String clCode = row.getString("clCode");			
			HashMap<String, String> codeParam = new HashMap<String, String>();
			codeParam.put("clCode", clCode);
			codeParam.put("clSeCode", "edpo");
			
			EntityMap clCodeRow = publicDataMapper.selectBdpTnDataCodeRow(codeParam);
			String pClNm = "";
			String clNm = "";
			
			if(clCodeRow != null) {
				pClNm = clCodeRow.getString("clNm");
				clNm = clCodeRow.getString("pClNm");
			}
			
			row.put("pClNm", pClNm);
			row.put("clNm", clNm);

			String provdOpenStatus = CommonUtil.arrayJoin(",", provdEnncList);
			row.put("provdOpenStatus", provdOpenStatus);

			String viewLink = "http://data.mafra.go.kr/opendata/data/indexOpenDataDetail.do?data_id="+row.getString("dataId")+"&service_ty=&filter_ty="+provdEnncList.get(0).toString()+"&sort_id=regist_dt";

			row.put("viewLink", viewLink);
		}
		
		return dataList;
	}
	
	/**
	 * 공공데이터 목록 갯수를 리턴한다.
	 * @param param
	 * @return
	 */
	public int getTotalCount(HashMap<String, Object> param) {
		int totalCount = publicDataMapper.getTotalCount(param);
		return totalCount;
	}
	
	/**
	 * 빅데이터 종별/데이터셋별 개수를 리턴한다.
	 * 
	 * @param param
	 * @return
	 */
	public EntityMap getTypeCount(HashMap<String, Object> param) {
		EntityMap entityMap = publicDataMapper.getTypeCount(param);
		return entityMap;
	}
	
}