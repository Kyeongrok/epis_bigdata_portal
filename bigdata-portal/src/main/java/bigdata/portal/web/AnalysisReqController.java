package bigdata.portal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.NoticeService;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

@Controller
@RequestMapping("/bdp/prcuse/")
public class AnalysisReqController extends BigdataController {

	private static Logger logger = LoggerFactory.getLogger(AnalysisReqController.class);

	protected static final String P_BIGDATA_PORTAL = "bigdata/portal/";

	protected static final String JSON_VIEW = "jsonView";

	protected static final String BBS_GBN = "A";

	@Autowired
	NoticeService service;

	/**
	 * 분석대행 리스트
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="analysisReqList.do", method=RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		//기본 값 설정
		if(!paramMap.containsKey("pageIndex")) {
			paramMap.put("pageIndex", 1);
		}

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		//int형으로 저장
		paramMap.put("pageIndex", paramMap.getInt("pageIndex"));

		//
		int offset = 10 * (paramMap.getInt("pageIndex")-1);
		paramMap.put("offset", offset);

		// search
		String searchCondition = paramMap.getString("searchCondition");
		if("title".equals(searchCondition)) {
			paramMap.put("nttSj", paramMap.get("searchKeyword"));
		} else if("userName".equals(searchCondition)) {
			paramMap.put("ntcrNm", paramMap.get("searchKeyword"));
		}

		CsTransferObject trans = service.gets(paramMap);

		model.addAllAttributes(trans);
		model.addAttribute("paramMap", paramMap);

		// pagination
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo((int) paramMap.get("pageIndex"));
		pageInfo.setRecordCountPerPage(10);
		pageInfo.setPageSize(10);
		pageInfo.setTotalRecordCount((int) trans.get("totcnt"));
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("articleNo", pageInfo.getArticleNo());

		return P_BIGDATA_PORTAL + "prcuse/analysisReqList";
	}

	/**
	 * 분석대행 view
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="analysisReqView.do", method=RequestMethod.GET)
	public String view(HttpServletRequest request, Model model) {

		Map<String, Object> paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		CsTransferObject trans = service.getByNttId("" + paramMap.get("nttId"));
		model.addAllAttributes(trans);
		paramMap.put("nttNo", trans.getData("nttNo"));
		paramMap.put("parntscttNo", trans.getData("nttNo"));
		model.addAttribute("loginUserId", this.getLoginUserId());
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("rplies", service.getsRply(paramMap).getDatas());

		return P_BIGDATA_PORTAL + "prcuse/analysisReqView";
	}

	/**
	 * file upload
	 * @param mpf
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("analysisReqFileUpload.json")
	public String fileUpload(@RequestParam("file") MultipartFile mpf, Model model) throws IllegalStateException, IOException {

		CsFileVO vo = super.uploadFile(mpf, "");

		model.addAttribute("fileVo", vo);

		return JSON_VIEW;
	}

	/**
	 * 등록폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="registAnalysisReqForm.do")
	public String registForm(HttpServletRequest request, Model model) {
		return P_BIGDATA_PORTAL + "prcuse/analysisReqRegForm";
	}

	/**
	 * 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="registAnalysisReq.json")
	public String insert(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		int i = 0;
		List<CsFileVO> fileVos = new ArrayList<>();
		while(true) {
			if(!paramMap.containsKey("file[" + i + "]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file[" + i + "]"), CsFileVO.class);
			fileVos.add(fileVo);

			i++;
		}

		service.regist(paramMap.getString("bbsGbn"), paramMap.getString("nttSj"), paramMap.getString("nttCn"), fileVos, getLoginUserId(), getLoginUserNm());

		return JSON_VIEW;
	}

	/**
	 * 수정폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("analysisReqUpdtForm.do")
	public String updtForm(HttpServletRequest request, Model model) {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		model.addAllAttributes(service.getByNttId(paramMap.getString("nttId")));

		return P_BIGDATA_PORTAL + "prcuse/analysisReqUpdtForm";
	}

	/**
	 * 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("analysisReqUpdt.json")
	public String updt(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		// 첨부된 파일이 있으면
		List<CsFileVO> fileVos = new ArrayList<>();
		int i=0;
		while(true) {
			if(!paramMap.containsKey("file[" + i + "]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file[" + i + "]"), CsFileVO.class);
			fileVos.add(fileVo);

			//
			i++;
		}

		// 삭제된 기존 파일 번호
		String[] deletedFileNos = null;
		String deletedFileNo = paramMap.getString("deletedFileNo");
		if(deletedFileNo != null && !"".equals(deletedFileNo)) {
			deletedFileNos = deletedFileNo.split(",", -1);
		}

		// 업데이트
		service.updt(paramMap.getString("nttId"), paramMap.getString("nttSj"), paramMap.getString("nttCn")
				, deletedFileNos, fileVos, this.getLoginUserId(), this.getLoginUserNm());

		return JSON_VIEW;
	}

	/**
	 * 삭제
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("analysisReqDelete.json")
	public String visualizePblonsipDelete(HttpServletRequest request, Model model) {

		CsMap paramMap = CsWebUtil.toParamMap(request);
		service.deleteByNttId(paramMap.getString("nttId"));

		return JSON_VIEW;
	}

	@RequestMapping("analysisReqRply.json")
	public String regRply(HttpServletRequest request, Model model) {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		service.registRply(paramMap.getString("nttId"), paramMap.getString("bbsGbn"), null, paramMap.getString("rplyCn"), paramMap.getInt("nttNo"), 0,
				null, getLoginUserId(), getLoginUserNm(), this.isRoleAdmin());

		return JSON_VIEW;
	}
}
