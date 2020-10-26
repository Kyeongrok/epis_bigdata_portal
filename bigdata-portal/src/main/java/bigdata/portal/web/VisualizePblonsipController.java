/**
 *
 */
package bigdata.portal.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bigdata.portal.cmm.controller.BigdataController;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.AtchmnflService;
import bigdata.portal.service.VisualizePblonsipService;
import egovframework.com.cmm.service.EgovProperties;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

/**
 * 시각화 공유
 * @author hyunseongkil
 *
 */
@Controller
@RequestMapping("/bdp/prcuse/")
public class VisualizePblonsipController extends BigdataController {
	private static Logger log = LoggerFactory.getLogger(VisualizePblonsipController.class);

	/**
	 * 시각화 공유
	 */
	@Autowired
	private VisualizePblonsipService service;

	/**
	 * 첨부파일
	 */
	@Autowired
	private AtchmnflService atchmnflService;


	/**
	 * 첨부파일 저장 경로
	 */
	private static String uploadFilePath = EgovProperties.getProperty("app.upload.file.path");

	/**
	 * 목록
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("visualizePblonsipList.do")
	public String visualizePblonsipList(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//기본 값 설정
		if(!paramMap.containsKey("pageIndex")) {
			paramMap.put("pageIndex", 1);
		}
		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", "V");
		}

		//int형으로 저장
		paramMap.put("pageIndex", paramMap.getInt("pageIndex"));

		//
		int offset = 10 * (paramMap.getInt("pageIndex")-1);
		paramMap.put("offset", offset);

		//
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


		//
		return P_BIGDATA_PORTAL + "prcuse/visualizePblonsipList";
	}


	/**
	 * 조회
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("visualizePblonsipDetail.do")
	public String visualizePblonsipDetail(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//데이터 조회
		model.addAllAttributes(service.getByNttId(paramMap.getString("nttId")));

		//기본 값 설정
		if(!paramMap.containsKey("pageIndex")) {
			paramMap.put("pageIndex", 1);
		}
		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", "V");
		}

		//int형으로 저장
		paramMap.put("pageIndex", paramMap.getInt("pageIndex"));

		//
		int offset = 10 * (paramMap.getInt("pageIndex")-1);
		paramMap.put("offset", offset);

		model.addAttribute("paramMap", paramMap);

		CsTransferObject trans = service.getByNttId("" + paramMap.get("nttId"));
		if(trans.get("atchFileId") != null) {
			String atchFileId = trans.get("atchFileId").toString();
			List<Map<String, Object>> files = atchmnflService.getsByAtchFileId(atchFileId);
			model.addAttribute("files", files);
		}
		model.addAllAttributes(trans);
		model.addAttribute("loginUserId", this.getLoginUserId());
		model.addAttribute("paramMap", paramMap);

		return P_BIGDATA_PORTAL + "prcuse/visualizePblonsipDetail";
	}

	/**
	 * 등록 폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("visualizePblonsipRegistForm.do")
	public String visualizePblonsipRegistForm(HttpServletRequest request, Model model) {

		return P_BIGDATA_PORTAL + "prcuse/visualizePblonsipRegistForm";
	}

	/**
	 * 등록 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("visualizePblonsipRegist.json")
	public String visualizePblonsipRegist(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		CsMap paramMap = CsWebUtil.toParamMap(request);


		//첨부된 파일이 있으면
		int i=0;
		List<CsFileVO> fileVos = new ArrayList<>();
		while(true) {
			if(!paramMap.containsKey("file["+i+"]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file["+i+"]"), CsFileVO.class);
			fileVo.setOriginFileName(new String(fileVo.getOriginFileName().getBytes("8859_1"), "UTF-8"));
			fileVos.add(fileVo);
			//
			i++;
		}

		log.debug("{}",fileVos);
		//등록
		service.regist(paramMap.getString("bbsGbn"), paramMap.getString("nttSj"), paramMap.getString("nttCn"), fileVos, this.getLoginUserId(), this.getLoginUserNm());

		return JSON_VIEW;
	}

	/**
	 * 수정 폼
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("visualizePblonsipUpdtForm.do")
	public String visualizePblonsipUpdtForm(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//데이터 조회
		model.addAllAttributes(service.getByNttId(paramMap.getString("nttId")));

		return P_BIGDATA_PORTAL + "prcuse/visualizePblonsipUpdtForm";
	}

	/**
	 * 수정 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("visualizePblonsipUpdt.json")
	public String visualizePblonsipUpdt(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		CsMap paramMap = CsWebUtil.toParamMap(request);


		//첨부된 파일이 있으면
		List<CsFileVO> fileVos = new ArrayList<>();
		int i=0;
		while(true) {
			if(!paramMap.containsKey("file["+i+"]")) {
				break;
			}

			CsFileVO fileVo = (CsFileVO) CsUtil.mapToVo((Map<String, Object>) paramMap.get("file["+i+"]"), CsFileVO.class);
			fileVo.setOriginFileName(new String(fileVo.getOriginFileName().getBytes("8859_1"), "UTF-8"));
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
		service.updt(paramMap.getString("nttId"), paramMap.getString("nttSj")
				, paramMap.getString("nttCn"), deletedFileNos, fileVos
				, this.getLoginUserId(), this.getLoginUserNm());

		return JSON_VIEW;
	}

	/**
	 * 삭제 처리
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("visualizePblonsipDelete.json")
	public String visualizePblonsipDelete(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//
		service.deleteByNttId(paramMap.getString("nttId"));

		return JSON_VIEW;
	}


	/**
	 * 파일 업로드
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping("visualizePblonsipUpload.json")
	public String visualizePblonsipUpload(@RequestParam("file") MultipartFile  mpf, Model model) throws IllegalStateException, IOException {

		//업로드 처리
		CsFileVO vo = super.uploadFile(mpf, "image");

		//
		model.addAttribute("fileVo", vo);

		return JSON_VIEW;
	}

	/**
	 * 파일 다운로드
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping("visualizePblonsipDwld.do")
	public void visualizePblonsipDwld(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//첨부파일 데이터 조회
		Map<String,Object> data = atchmnflService.getByPk(paramMap.getString("atchFileId"), paramMap.getString("fileNo"));
		if(CsUtil.isEmpty(data)) {
			return;
		}


		//
		String fileName = ""+data.get("originFileName");

		String header = getBrowser(request);

		if (header.contains("MSIE")) {
		       String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		       response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
		} else if (header.contains("Firefox")) {
		       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
		} else if (header.contains("Opera")) {
		       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
		} else if (header.contains("Chrome")) {
		       String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		       response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
		}

		response.setHeader("Content-Type", "application/octet-stream");
		//response.setContentLength((int)file.getSize());
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		//
		File file = Paths.get(uploadFilePath, ""+data.get("strePathName"), ""+data.get("streFileName")).toFile(); // 이지팜 개발서버
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			log.error("{}",e);
		}finally {
			if(null != inputStream) {
				inputStream.close();
			}
		}
	}


}
