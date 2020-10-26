/**
 * 
 */
package bigdata.portal.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.elastic.ElasticResultMap;
import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.entity.FixesSportPolicy;
import bigdata.portal.mapper.AglfrsBsnsOpertnManualMapper;
import bigdata.portal.mapper.DeminAgeInfoSqlMapper;
import bigdata.portal.mapper.DeminFrmerInfoSqlMapper;
import bigdata.portal.mapper.DeminPrdlstInfoSqlMapper;
import bigdata.portal.mapper.DeminSigunInfoSqlMapper;
import bigdata.portal.mapper.FixesSportPolicyEsMapper;
import bigdata.portal.mapper.SportBsnsFrmerEsMapper;
import bigdata.portal.mapper.SportBsnsStatsAdresSqlMapper;
import bigdata.portal.mapper.SportBsnsStatsAgeSqlMapper;
import bigdata.portal.mapper.SportBsnsStatsPrdlstSqlMapper;
import bigdata.portal.mapper.SportBsnsStatsYearSqlMapper;
import bigdata.portal.service.FixesSportPolicyService;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;

/**
 * @author hyunseongkil
 *
 */
@Service("fixesSportPolicyService")
public class FixesSportPolicyServiceImpl extends BigdataServiceImpl implements FixesSportPolicyService {

	@Autowired
	private FixesSportPolicyEsMapper mapper;
	
	@Autowired
	private AglfrsBsnsOpertnManualMapper atchmnflMapper;
	
	/**
	 * 지원 사업-농업인
	 */
	@Autowired
	private SportBsnsFrmerEsMapper sportBsnsFrmerMapper;
	
	/**
	 * 통계 - 분자 - 년도별
	 */
	@Autowired
	private SportBsnsStatsYearSqlMapper statsYearMapper;
	
	/**
	 * 통계 - 분자 - 연령대별
	 */
	@Autowired
	private SportBsnsStatsAgeSqlMapper statsAgeMapper;
	
	/**
	 * 통계 - 분모 - 년도별
	 */
	@Autowired
	private DeminFrmerInfoSqlMapper deminFrmerInfoMapper;
	
	/**
	 * 통계 - 분모 - 연령대별
	 */
	@Autowired
	private DeminAgeInfoSqlMapper deminAgeInfoMapper;
	
	/**
	 * 통계 - 분모 - 지역
	 */
	@Autowired
	private DeminSigunInfoSqlMapper deminSigunInfoMapper;
	
	/**
	 * 통계 - 분자 - 지역
	 */
	@Autowired
	private SportBsnsStatsAdresSqlMapper statsAdresMapper;
	
	/**
	 * 분모 - 품목군
	 */
	@Autowired
	private DeminPrdlstInfoSqlMapper deminPrdlstInfoMapper;
	
	/**
	 * 분자 - 품목군
	 */
	@Autowired
	private SportBsnsStatsPrdlstSqlMapper statsPrdlstMapper;
	
	
	
	@Override
	public CsTransferObject getDatas(String searchGbn, String searchKeyword) throws InstantiationException, IllegalAccessException, IOException  {
		ElasticResultMap resultMap = mapper.getDatas(searchGbn, searchKeyword);
		
		// 
		CsTransferObject trans = new CsTransferObject();
		trans.put("datas", resultMap.getHitsInHits());
		
		//
		return trans;
	}
	
	@Override
	public CsTransferObject getDatasByMlsfcNmCode(String mlsfcNmCode) throws InstantiationException, IllegalAccessException, IOException {
		ElasticResultMap resultMap = mapper.getDatasByMlsfcNmCode(mlsfcNmCode);
		
		// 
		CsTransferObject trans = new CsTransferObject();
		trans.put("datas", resultMap.getHitsInHits());
		
		//
		return trans;
	}
	
	
	
	private List<FixesSportPolicy> dummyDatas(){
		List<FixesSportPolicy> list = new ArrayList<FixesSportPolicy>();
		
		FixesSportPolicy vo = new FixesSportPolicy();
		list.add(vo);
		
		//
		vo.setLclasNm("경영이양직접지불금");
		vo.setLclasNmCode("001");
		vo.setMlsfcNmCode("0011100");
		vo.setDetailBsnsNm("경영이양직접지불금");
		vo.setSportArea("경영안정지원");
		vo.setSportStle("보조사업");
		vo.setSportSub("농림수산식품부");
		vo.setBsnsSummary("농업 경영을 이양하는 고령 은퇴농가의 소득지원으로 소득안정, 전업농 등에 대한 영농규모 확대 지원으로 영농 규모화 촉진");
		vo.setBsnsPd("약정기간까지(최대 80세 12월 31일까지)");
		vo.setApplyTime("연중(2019년 예산 범위 내에서 연중 신청가능)");
		
		vo.setFmerReqst("Y");
		vo.setFmerSportAmntMin("10");
		vo.setFmerSportAmntMax("13");
		
		vo.setCoReqst("N");
		
		vo.setNmReqst("N");
		
		//
		return list;
	}
	
	
	//TODO 지원영역별 건수
	private List<Map<String,Object>> getCountsBySportRelmCode(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//
		Map<String,Object> map1 = new HashMap<String, Object>();
		list.add(map1);
		map1.put("sportRelmCode", "교육컨설팅");
		map1.put("count", CsUtil.rand(10));
		
		//
		Map<String,Object> map2 = new HashMap<String, Object>();
		list.add(map2);
		map2.put("sportRelmCode", "경영안정지원");
		map2.put("count", CsUtil.rand(10));
		//
		return list;
	}

	@Override
	public Map<String, Object> getFileInfoByLclasNmCodeAndMlsfcNmCode(String lclasNmCode, String mlsfcNmCode) {
		log.info(">>getFileInfoByLclasNmCodeAndMlsfcNmCode - {} {}", lclasNmCode, mlsfcNmCode);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lclasNmCode", lclasNmCode);
		map.put("mlsfcNmCode", mlsfcNmCode);
		
		//
		return atchmnflMapper.getByLclasNmCodeAndMlsfcNmCode(lclasNmCode, mlsfcNmCode);
	}

	@Override
	public CsTransferObject getDatasForRetnFmlg(String searchSido, String searchSigungu, String searchUmd, String searchSportRelmCode) throws InstantiationException, IllegalAccessException, IOException {
		ElasticResultMap resultMap = mapper.getDatasForRetnFmlg(searchSido, searchSigungu, searchUmd, searchSportRelmCode);
		
		// 
		CsTransferObject trans = new CsTransferObject();
		trans.put("datas", resultMap.getHitsInHits());
		
		//
		return trans;
	}

	@Override
	public CsTransferObject getStatsDatasByYear(String bsnsCode) {
		return new CsTransferObject()
					.add("denominators", deminFrmerInfoMapper.getDatas())
					.add("numerators", statsYearMapper.getDatasByStdrYear(bsnsCode));
						
		
//		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
//		
//		//
//		ElasticResultMap resultMap = sportBsnsFrmerMapper.getStatsDatasByYear(bsnsCode);
//		if(null == resultMap) {
//			return datas;
//		}
//		
//		//
//		Map<String,Object> aggMap = resultMap.getAggregations();
//		if(CsUtil.isEmpty(aggMap)) {
//			return datas;
//		}
//		
//		//
//		Map<String,Object> myMap = (Map<String, Object>) aggMap.get("stdr_years");
//		if(CsUtil.isEmpty(myMap)) {
//			return datas;
//		}
//		
//		//
//		List<Map<String,Object>> buckets = (List<Map<String, Object>>) myMap.get("buckets");
//		if(CsUtil.isEmpty(buckets)) {
//			return datas;
//		}
//		
//		//
//		for(Map<String,Object> d : buckets) {
//			Map<String,Object> map = new HashMap<String, Object>();
//			datas.add(map);
//			map.put("stdr_year", d.get("key"));
//			map.put("cnt", d.get("doc_count"));
//		}
//		
//		// 
//		return datas;
	}

	@Override
	public CsTransferObject getStatsDatasBySido(String bsnsCode) {
		return new CsTransferObject()
					.add("denominators", deminSigunInfoMapper.getSidos())
					.add("numerators", statsAdresMapper.getSidos(bsnsCode));
	}

	@Override
	public CsTransferObject getStatsDatasBySigungu(String bsnsCode, String sidoCode) {
		return new CsTransferObject()
				.add("denominators", deminSigunInfoMapper.getSigungusBySido(sidoCode))
				.add("numerators", statsAdresMapper.getSigungusBySido(bsnsCode, sidoCode));
	}

	@Override
	public CsTransferObject getStatsDatasByAge(String bsnsCode) {

		String year40 = "" + DateTime.now().minusYears(40).getYear();
		String year50 = "" + DateTime.now().minusYears(50).getYear();
		String year60 = "" + DateTime.now().minusYears(60).getYear();
		String year65 = "" + DateTime.now().minusYears(65).getYear();
		
		//
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bsnsCode", bsnsCode);
		paramMap.put("year40", year40);
		paramMap.put("year50", year50);
		paramMap.put("year60", year60);
		paramMap.put("year65", year65);
		
		//
		return new CsTransferObject()
					.add("denominators", deminAgeInfoMapper.getDatasByAgeGroup())
					.add("numerators", statsAgeMapper.getDatasByAgeGroup(paramMap));
	}

	@Override
	public CsTransferObject getStatsDatasByPrdlstSet(String bsnsCode) {
		return new CsTransferObject()
				.add("denominators", deminPrdlstInfoMapper.gets())
				.add("numerators", statsPrdlstMapper.gets(bsnsCode));
	}

}
