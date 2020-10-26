/**
 * 
 */
package bigdata.portal.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.SimilrSctnSqlMapper;
import bigdata.portal.mapper.SportBsnsFrmerEsMapper;
import bigdata.portal.mapper.SportBsnsFrmerSqlMapper;
import bigdata.portal.service.SimilrAtmnentService;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;

/**
 * 유사 경영체
 * @author hyunseongkil
 *
 */
@Service("similrAtmnentService")
public class SimilrAtmnentServiceImpl extends BigdataServiceImpl implements SimilrAtmnentService {
	
	/**
	 * 지원 사업 농업인
	 */
	@Autowired
	private SportBsnsFrmerEsMapper sportBsnsFrmerMapper;
	
	/**
	 * 유사 경영체 구간
	 */
	@Autowired
	private SimilrSctnSqlMapper similrSctnMapper;
	
	
	
	@Override
	public CsTransferObject getCountsByBsnsCode(String[] bsnsCodes, String sexdstn, String age, String farmCareer, String prdlstSetCode, String prdlstCode, String ctvtTyCode, String arOrCo) throws InstantiationException, IllegalAccessException, IOException {
		CsTransferObject trans = new CsTransferObject();

		//
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		//사업 목록
		paramMap.put("bsnsCodes", Arrays.asList(bsnsCodes));
		
		//성별
		if(CsUtil.isNotEmpty(sexdstn)) {
			paramMap.put("sexdstn", sexdstn);
		}

		//나이
		if(CsUtil.isNotEmpty(age)) {
			Map<String,Object> sctnData = similrSctnMapper.getDataByAge(Integer.valueOf(age));
			log.debug("age:{} {}",age, sctnData);

			if(CsUtil.isEmpty(sctnData)) {
				return trans;
			}

			//
			Integer minValue = getValue(sctnData, "min");
			Integer maxYear = DateTime.now().minusYears(minValue).getYear();
			paramMap.put("maxBrthYyyy", (maxYear+1)); //미만

			Integer maxValue = getValue(sctnData, "max");
			Integer minYear = DateTime.now().minusYears(maxValue).getYear();
			paramMap.put("minBrthYyyy", minYear);			
		}

		//영농경력
		if(CsUtil.isNotEmpty(farmCareer)) {
			Map<String,Object> sctnData = similrSctnMapper.getDataByCareer(Integer.valueOf(farmCareer));
			log.debug("farmCareer:{} {}",farmCareer, sctnData);

			if(CsUtil.isEmpty(sctnData)) {
				return trans;
			}

			//
			Integer minValue = getValue(sctnData, "min");
			Integer maxYear = DateTime.now().minusYears(minValue).getYear();
			paramMap.put("maxFarmBeginYm", (maxYear+1) + "01"); //미만

			Integer maxValue = getValue(sctnData, "max");
			Integer minYear = DateTime.now().minusYears(maxValue).getYear();
			paramMap.put("minFarmBeginYm", minYear + "01"); //
		}
		
		//품목 and 면적or두수
		if(CsUtil.isNotEmpty(prdlstCode) && CsUtil.isNotEmpty(arOrCo)) {
			Map<String,Object> sctnData = similrSctnMapper.getDataByPrdlst(ctvtTyCode, prdlstCode, Integer.valueOf(arOrCo));
			log.debug("ctvtTyCode:{} prdlstCode:{} arOrCo:{}",ctvtTyCode, prdlstCode, arOrCo, sctnData);
			if(CsUtil.isEmpty(sctnData)) {
				return trans;
			}
			
			//
			paramMap.put("minArOrCo", getValue(sctnData, "min"));
			paramMap.put("maxArOrCo", getValue(sctnData, "max")+1); //미만
			paramMap.put("prdlstCode", prdlstCode);
			paramMap.put("ctvtTyCode", ctvtTyCode);
		}
		
		//
		log.debug(".getCountsByBsnsCode - paramMap:{}", paramMap);
		ElasticResultMap resultMap = sportBsnsFrmerMapper.getCountsByBsnsCode(paramMap);
		
		//
		return trans.putDatas( toListOfMapFromElasticResult(resultMap) );
	}
	
	
	/**
	 * elastic result map에서 agg부분의 buckets 추출하여 데이터 목록으로 변환
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> toListOfMapFromElasticResult(ElasticResultMap resultMap){
		//
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		
		//
		Map<String,Object> aggMap = resultMap.getAggregations();
		if(CsUtil.isEmpty(aggMap)) {
			return datas;
		}
		
		//
		Map<String,Object> myMap = (Map<String, Object>) aggMap.get("sport_bsns_codes");
		if(CsUtil.isEmpty(myMap)) {
			return datas;
		}
		
		//
		List<Map<String,Object>> buckets = (List<Map<String, Object>>) myMap.get("buckets");
		if(CsUtil.isEmpty(buckets)) {
			return datas;
		}
		
		//
		for(Map<String,Object> map : buckets) {
			Map<String,Object> d = new HashMap<String, Object>();
			datas.add(d);
			d.put("SPORT_BSNS_CODE", map.get("key"));
			d.put("CNT", map.get("doc_count"));
		}
		
		//
		return datas;
	}
	


	private Integer getValue(Map<String,Object> sctnData, String gbn) {
		if(null == sctnData) {
			return null;
		}
		
		//
		if("min".equalsIgnoreCase(gbn)) {
			return Integer.parseInt("" + sctnData.get("minValue"));
		}
		
		//
		if("max".equalsIgnoreCase(gbn)) {
			return Integer.parseInt("" + sctnData.get("maxValue"));
		}
		
		//
		return null;
	}



	@Override
	public CsTransferObject getCounts(String[] bsnsCodes, String sexdstn, String age, String farmCareer,
			String prdlstSetCode, String prdlstCode, String gbn2, String arOrCo) {
		// TODO Auto-generated method stub
		return null;
	}

}
