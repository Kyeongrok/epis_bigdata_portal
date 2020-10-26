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
public class NoticeController extends BigdataController {

	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

	protected static final String P_BIGDATA_PORTAL = "bigdata/portal/";

	protected static final String JSON_VIEW = "jsonView";

	protected static final String BBS_GBN = "N";

	@Autowired
	NoticeService noticeService;

	/**
	 * 목록조회
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="noticeList.do", method=RequestMethod.GET)
	public String noticeList(HttpServletRequest request, Model model) {

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

		//
		CsTransferObject trans = noticeService.gets(paramMap);

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

		return P_BIGDATA_PORTAL + "prcuse/noticeList";
	}

	/**
	 * 공지사항 view
	 */
	@RequestMapping(value="noticeView.do", method=RequestMethod.GET)
	public String noticeView(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		//
		model.addAllAttributes(noticeService.getByNttId("" + paramMap.get("nttId")));
		model.addAttribute("paramMap", paramMap);

		return P_BIGDATA_PORTAL + "prcuse/noticeView";
	}

	@RequestMapping("noticeFileUpload.json")
	public String noticeFileUpload(@RequestParam("file") MultipartFile  mpf, Model model) throws IllegalStateException, IOException {

		//업로드 처리
		CsFileVO vo = super.uploadFile(mpf, "");

		//
		model.addAttribute("fileVo", vo);

		return JSON_VIEW;
	}

	/**
	 * 공지사항등록폼
	 */
	@RequestMapping(value="registNoticeForm.do")
	public String registNoticeForm(HttpServletRequest request, Model model) {

		return P_BIGDATA_PORTAL + "prcuse/noticeRegForm";
	}

	/**
	 * 공지사항 등록
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="registNotice.json")
	public String noticeInsert(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", BBS_GBN);
		}

		//첨부된 파일이 있으면
		int i=0;
		List<CsFileVO> fileVos = new ArrayList<>();
		while(true) {
			if(!paramMap.containsKey("file["+i+"]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file["+i+"]"), CsFileVO.class);
			fileVos.add(fileVo);

			//
			i++;
		}

		//등록
		noticeService.regist(paramMap.getString("bbsGbn"), paramMap.getString("nttSj"), paramMap.getString("nttCn"), fileVos, getLoginUserId(), getLoginUserNm());

		return JSON_VIEW;
	}

	@RequestMapping("noticeUpdtForm.do")
	public String visualizePblonsipUpdtForm(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//데이터 조회
		model.addAllAttributes(noticeService.getByNttId(paramMap.getString("nttId")));

		//
		return P_BIGDATA_PORTAL + "prcuse/noticeUpdtForm";
	}

	/**
	 * notice update
	 * @param request
	 * @param model
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("noticeUpdt.json")
	public String noticeUpdt(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {
		CsMap paramMap = CsWebUtil.toParamMap(request);


		//첨부된 파일이 있으면
		List<CsFileVO> fileVos = new ArrayList<>();
		int i=0;
		while(true) {
			if(!paramMap.containsKey("file["+i+"]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file["+i+"]"), CsFileVO.class);
			fileVos.add(fileVo);

			//
			i++;
		}

		//삭제된 기존 파일 번호
		String[] deletedFileNos = null;
		String deletedFileNo = paramMap.getString("deletedFileNo");
		if(deletedFileNo != null && !"".equals(deletedFileNo)) {
			deletedFileNos = deletedFileNo.split(",", -1);
		}

		//업데이트
		noticeService.updt(paramMap.getString("nttId"), paramMap.getString("nttSj"), paramMap.getString("nttCn")
				, deletedFileNos, fileVos, this.getLoginUserId(), this.getLoginUserNm());

		return JSON_VIEW;
	}


	/**
	 * 삭제 처리
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("noticeDelete.json")
	public String visualizePblonsipDelete(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//
		noticeService.deleteByNttId(paramMap.getString("nttId"));

		return JSON_VIEW;
	}


}
