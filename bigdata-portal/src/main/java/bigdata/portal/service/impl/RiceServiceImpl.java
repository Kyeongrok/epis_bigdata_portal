/**
 * 
 */
package bigdata.portal.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bigdata.portal.cmm.service.impl.BigdataServiceImpl;
import bigdata.portal.mapper.AdministInsttCodeSumryMapper;
import bigdata.portal.mapper.RiceDirectSbsidyReqstEsMapper;
import bigdata.portal.mapper.RicePrdctnCtprvnPredictDansuEsMapper;
import bigdata.portal.mapper.RicePrdctnSignguPredictDansuEsMapper;
import bigdata.portal.service.RiceService;
import kr.co.ucsit.core.CsTransferObject;

/**
 * 쌀
 * @author hyunseongkil
 *
 */
@Service("riceService")
public class RiceServiceImpl extends BigdataServiceImpl implements RiceService {

	/**
	 * 직불금 신청
	 */
	@Autowired
	private RiceDirectSbsidyReqstEsMapper directSbsidyReqstMapper;
	
	/**
	 * 주소
	 */
	@Autowired
	private AdministInsttCodeSumryMapper adresMapper;
	
	/**
	 * 시도
	 */
	@Autowired
	private RicePrdctnCtprvnPredictDansuEsMapper sidoDansuMapper;
	
	/**
	 * 시군구
	 */
	@Autowired
	private RicePrdctnSignguPredictDansuEsMapper sigunguDansuMapper;
	
	@SuppressWarnings("unchecked")
	private CsTransferObject getDatasBySido(List<Map<String,Object>> hitsInHits, Boolean isRiceCtvtQyPredict, Boolean isDebitAmountDscnAr, Boolean isDebitAmountReqstAr) {

		double sumPredict=0.0, sumDcsnAr=0.0, sumReqstAr = 0.0;
		
		//시도별
		List<Map<String,Object>> listBySido = distinctBySido(hitsInHits);
		
		//
		for(Map<String,Object> sidoMap : listBySido) {
			
			//
			for(Map<String,Object> d : hitsInHits) {
				Map<String,Object> sourceMap = (Map<String, Object>) d.get("_source");

				//
				if(sidoMap.get("sidoCode").equals(sourceMap.get("ctprvn_code"))) {
					//
					if(true == isRiceCtvtQyPredict) {
						//TODO 예측
					}

					if(true == isDebitAmountDscnAr) {
						// 확정
						sumDcsnAr += Double.parseDouble(""+sourceMap.get("dcsn_ar"));
					}

					if(true == isDebitAmountReqstAr) {
						//신청
						sumReqstAr += Double.parseDouble(""+sourceMap.get("reqst_ar"));
					}
				}		
			}
			
			//
			sidoMap.put("sumPredict", sumPredict);
			sidoMap.put("sumDcsnAr", sumDcsnAr);
			sidoMap.put("sumReqstAr", sumReqstAr);
		}
		
		//
		return new CsTransferObject().add("datas", listBySido);
		
	
	}

	
	@SuppressWarnings("unchecked")
	private Set<String> distictSidoCodes(List<Map<String, Object>> hitsInHits){
		Set<String> list = new HashSet<String>();
		
		//
		for(Map<String,Object> d : hitsInHits) {
			Map<String,Object> sourceMap = (Map<String, Object>) d.get("_source");
			
			//
			list.add("" + sourceMap.get("ctprvn_code"));			
		}
		
		//
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> distinctBySido(List<Map<String, Object>> hitsInHits) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		Set<String> sidoCodes = distictSidoCodes(hitsInHits);
		
		//
		for(String sidoCode : sidoCodes	) {
			Map<String,Object> d = new HashMap<String, Object>();
			list.add(d);
			
			d.put("sidoCode", sidoCode);
			d.put("sidoName", getSidoName(hitsInHits, sidoCode));
			d.put("sumAr", getSumArBySido(hitsInHits, sidoCode, "reqst_ar"));
		}
		
		
		
		// 
		return list;
	}

	@SuppressWarnings("unchecked")
	private Double getSumArBySido(List<Map<String, Object>> hitsInHits, String sidoCode, String key) {
		double sumAr=0.0;
		
		for(Map<String,Object> d : hitsInHits) {
			Map<String,Object> sourceMap = (Map<String, Object>) d.get("_source");
			
			//
			if(sidoCode.equals(sourceMap.get("ctprvn_code"))) {
				sumAr += Double.parseDouble("" + sourceMap.get(key));
			}
		}
		
		return sumAr;
	}

	@SuppressWarnings("unchecked")
	private String getSidoName(List<Map<String, Object>> hitsInHits, String sidoCode) {
		for(Map<String,Object> d : hitsInHits) {
			Map<String,Object> sourceMap = (Map<String, Object>) d.get("_source");
			
			//
			if(sidoCode.equals(sourceMap.get("ctprvn_code"))) {
				return "" + sourceMap.get("ctprvn_nm");
			}
		}
		
		//
		return "";
	}

	@Override
	public CsTransferObject getSidos() {
		return new CsTransferObject().add("datas", adresMapper.getAllSidos());
	}

	@Override
	public CsTransferObject getSigungusBySidoCode(String sidoCode) {
		return new CsTransferObject().add("datas", adresMapper.getSigungusBySido(sidoCode));
	}

	@Override
	public CsTransferObject getSidosAndSigungus() {
		CsTransferObject trans = new CsTransferObject();
		
		//
		trans.put("sidos", adresMapper.getAllSidos());
		trans.put("sigungus", adresMapper.getAllSigungus());
		
		//
		return trans;
	}

	@Override
	public CsTransferObject getAllDatas() throws InstantiationException, IllegalAccessException, IOException  {
		CsTransferObject trans = new CsTransferObject();
		
		//직불금 신청
		trans.put("directSbsidyReqsts", directSbsidyReqstMapper.getDatas().getHitsInHits());
		//시도 단수
		trans.put("sidoDansus", sidoDansuMapper.getDatas().getHitsInHits());
		//시군구 단수
		trans.put("sigunguDansus", sigunguDansuMapper.getDatas().getHitsInHits());
		
		
		// 
		return trans;
	}
}
