package bigdata.portal.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.service.CsService;

public interface ReturnFarmService extends CsService {

	/*귀농 정보 등록*/
	<T extends Map> void registRetnFmlgInfo(HttpServletRequest request, HttpSession session) throws InstantiationException, IllegalAccessException, IOException;

	/*재배 품목*/
	<T extends Map> CsTransferObject getsCtvt(T map);

	/*고려사항*/
	<T extends Map> CsTransferObject getsCnsdr(T map);

	/**귀농인 DB에서 Index 조회*/
	<T extends Map> CsTransferObject getRetnFmlgModelIdx(T map);

	/**유사 귀농인 정보*/
	<T extends Map> CsTransferObject getSimilrRetnFmlgInfo(T map);

	/**지역 정보*/
	<T extends Map> CsTransferObject getRetnFmlgAreaInfo(T map) throws InstantiationException, IllegalAccessException, IOException;

	/**선택지역 및 품목 분석 정보*/
	<T extends Map> CsTransferObject getSelectAreaCtvtInfo(T dataObjMap);




}
