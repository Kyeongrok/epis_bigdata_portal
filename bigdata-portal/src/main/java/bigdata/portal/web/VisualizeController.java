package bigdata.portal.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bigdata.portal.entity.CodeDetail;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.entity.PageInfo;
import bigdata.portal.service.CodeService;
import bigdata.portal.service.HistoryService;
import bigdata.portal.service.VisualizeService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFormBasedFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 시각화 목록 및 상세보기 컨트롤러
 *
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 10. 8.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 8.
 */
/**
 * @author THEIMC JHY
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2018. 12. 5.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 12. 5.
 */
@Controller
@RequestMapping("/bdp/visual")
public class VisualizeController extends CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(VisualizeController.class);
	@Autowired private EgovPropertyService propertiesService;
	@Autowired private VisualizeService visualizeService;
	@Autowired private CodeService codeService;
	@Autowired private HistoryService historyService;

	private final String uploadDir = EgovProperties.getProperty("bigdata.visualize.datadir");

	/**
	 * 시각화리스트
	 * 
	 * @param keyword
	 * @param stype1
	 * @param stype2
	 * @param searchMy
	 * @param pageIndex
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String visualizeList(@RequestParam(value = "keyword", required = false) String keyword,
							@RequestParam(value = "stype1", required = false) String stype1,
							@RequestParam(value = "stype2", required = false) String stype2,
							@RequestParam(value = "searchMy", defaultValue = "N") String searchMy,
							@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
							Model model) {
		int pageUnit = 16; // 4x4

		HashMap<String, Object> param = new HashMap<String, Object>();

		// 검색
		param.put("searchCondition", "visTitle");
		param.put("searchKeyword", keyword);
		
		if(isLogin()) {
			param.put("searchMy", searchMy);
		}

		// 카테고리 검색
		param.put("searchType1", stype1);
		param.put("searchType2", stype2);

		param.put("userId", getLoginUserId());

		int pageTotal = visualizeService.selectVisualizeCount(param);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNo(pageIndex);
		pageInfo.setRecordCountPerPage(pageUnit);
		pageInfo.setPageSize(propertiesService.getInt("pageSize"));
		pageInfo.setTotalRecordCount(pageTotal);

		// 페이징
		param.put("startNum", pageInfo.getFirstRecordIndex());
		param.put("endNum", pageInfo.getRecordCountPerPage());

		List<EntityMap> datasetList = visualizeService.selectVisualizeList(param);

		model.addAttribute("result", "success");
		model.addAttribute("dataList", datasetList);
		model.addAttribute("pageInfo", pageInfo);

		// 카테고리
		List<CodeDetail> codeItem = codeService.selectCodeDetail("C0001");
		List<CodeDetail> codeType = codeService.selectCodeDetail("C0002");

		model.addAttribute("codeItem", codeItem);
		model.addAttribute("codeType", codeType);

		model.addAttribute("menuNum", 3);
		model.addAttribute("pageNum", 1);
		return "bigdata/portal/visual/visualizeList";
	}

	/**
	 * 시각화 상세 보기
	 * 
	 * @param visId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String visualizeView(@RequestParam("visId") String visId, Model model) {
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("visId", visId);
		param.put("userId", getLoginUserId());

		EntityMap data = visualizeService.selectVisualize(param);
		if (data == null || data.get("visId") == null) {
			model.addAttribute("message", "데이터가 이미 삭제되었거나 찾을 수 없습니다.");
			model.addAttribute("redirect", "/bdp/visual/list.do");

			return "bigdata/message";
		}

		// 카테고리
		CodeDetail codeItem = codeService.selectCodeDetail("C0001", data.getString("visCate1"));
		CodeDetail codeType = codeService.selectCodeDetail("C0002", data.getString("visCate2"));

		data.put("visCate1Name", codeItem.getCodeNm());
		data.put("visCate2Name", codeType.getCodeNm());
		model.addAttribute("data", data);

		model.addAttribute("menuNum", 3);
		model.addAttribute("pageNum", 1);
		return "bigdata/portal/visual/visualizeView";
	}

	/**
	 * 시각화 보기에서 데이터 불러오기
	 * 
	 * @param visId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewData.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void visualizeViewData(HttpServletResponse response, @RequestParam("visId") String visId, Model model) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("visId", visId);
		param.put("userId", getLoginUserId());

		EntityMap data = visualizeService.selectVisualize(param);
		if (data == null || data.get("visId") == null) {
			response.getOutputStream().print("NODATA");
			return;
		}

		String visDataPath = data.getString("visDataPath");		
		long filesize = 0;
		filesize = visualizeService.readFile(uploadDir + "/" + visDataPath, response.getOutputStream());			

		if (filesize == 0) {
			response.getOutputStream().print("NODATA");
		}
	}

	/**
	 * 섬네일 표시
	 * 
	 * @param response
	 * @param date
	 * @param thumbId
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/thumbnail.do", method = RequestMethod.GET)
	public void visualizeThumbnail(HttpServletResponse response,
							@RequestParam("date") String date,
							@RequestParam("thumbId") String thumbId,
							Model model) throws Exception {

		if (!date.matches("^[0-9]{8}")) {
			return;
		}
		if (!thumbId.matches("^[0-9A-Za-z]+$")) {
			return;
		}

		response.setContentType("image/png");
		response.setHeader("Content-Disposition", "attachment; filename=" + date + "_" + thumbId + ".png");
		visualizeService.readFile(uploadDir + "/" + date + "/" + thumbId + ".png", response.getOutputStream());
	}

	/**
	 * 시각화 생성 폼
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String visualizeCreateForm(Model model) {
		List<CodeDetail> codeItem = codeService.selectCodeDetail("C0001");
		List<CodeDetail> codeType = codeService.selectCodeDetail("C0002");

		model.addAttribute("codeItem", codeItem);
		model.addAttribute("codeType", codeType);

		model.addAttribute("menuNum", 3);
		model.addAttribute("pageNum", 2);
		return "bigdata/portal/visual/visualizeCreateForm";
	}

	/**
	 * 시각화 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String visualizeInsert(HttpServletRequest request, Model model) throws Exception {
		int result = 0;

		// 차트프로퍼티 및 데이터 JSON 검증
		String visChartProperty = request.getParameter("visChartProperty");
				
		try {
			//JSONObject가 맞는지 체크
			JSONParser parser = new JSONParser();
			parser.parse(visChartProperty);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "시각화 옵션 설정값이 잘못되었습니다.");
			model.addAttribute("result", "fail");
			return "jsonView";
		}
		
		String fileNameTmp = EgovFormBasedFileUtil.getTodayString() + "/" + EgovFormBasedFileUtil.getPhysicalFileName();
		
		// 시각화 데이터 저장
		String visDataPath = fileNameTmp + ".json";
		String sheetdata = request.getParameter("sheetdata");
		File file = null;
		try {	
			// JSONArray가 맞는지 체크
			JSONParser parser = new JSONParser();
			parser.parse(sheetdata);

			file = new File(uploadDir + "/" + visDataPath);
			long fileSize = visualizeService.saveFile(file, sheetdata);

			if (fileSize < 1) {
				model.addAttribute("message", "시각화 데이터를 저장하지 못하였습니다.");
				model.addAttribute("result", "fail");
				return "jsonView";
			}
		} catch (Exception e) {
			model.addAttribute("message", "시각화 데이터값이 잘못되었습니다.");
			model.addAttribute("result", "fail");
			return "jsonView";
		}
		
		String thumbnail = request.getParameter("thumbnail");
		String visThumbPath = "";
		File fileThunb = null;
		if(thumbnail != null && thumbnail.trim().length() > 20) {
			visThumbPath = fileNameTmp + ".png";
			try {
				thumbnail = thumbnail.replaceAll("data:image/png;base64,", "");
	
				byte[] thumbData = Base64.decodeBase64(thumbnail);
				fileThunb = new File(uploadDir + "/" + visThumbPath);
				long fileThunbSize = visualizeService.saveFile(fileThunb, thumbData);

				// 기본 이미지 대체
				if (fileThunbSize < 1) {
					visThumbPath = "";
				} else {
					visualizeService.makeThumbnail(fileThunb, 900, 579);
				}
				
			} catch (IOException e) {
				visThumbPath = "";
			}
		}
		
		String userId = getLoginUserId();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("visId", 0);
		map.put("visTitle", request.getParameter("visTitle"));
		map.put("visDataPath", visDataPath);
		map.put("visCate1", request.getParameter("visCate1"));
		map.put("visCate2", request.getParameter("visCate2"));
		map.put("visOpenAt", request.getParameter("visOpenAt"));
		map.put("userId", userId);
		map.put("visChartName", request.getParameter("visChartName"));
		map.put("visChartType", request.getParameter("visChartType"));
		map.put("visChartX", request.getParameter("visChartX"));
		map.put("visChartY", request.getParameter("visChartY"));
		map.put("visChartZ", request.getParameter("visChartZ"));
		map.put("visChartXLabel", request.getParameter("visChartXLabel"));
		map.put("visChartYLabel", request.getParameter("visChartYLabel"));
		map.put("visChartZLabel", request.getParameter("visChartZLabel"));
		map.put("visChartProperty", visChartProperty);
		map.put("visChartDesc", request.getParameter("visChartDesc"));
		map.put("visSource", request.getParameter("visSource"));
		map.put("visThumbPath", visThumbPath);

		// 시각화 정보 저장
		int insertId = 0;
		insertId = visualizeService.insertVisualize(map);		
		if (insertId > 0) {
			
			// 시각화 등록 이력 저장
			//userId
			// 사용자가 등록한 글 이력을 저장
			String artCont = request.getParameter("visTitle");
			String artTarget = "VISUAL";
			String artStatus = "REG";
			int artIdx = insertId;
			String artUrl = "/bdp/visual/view.do?visId="+artIdx;
			
			int res1 = historyService.saveBdpUserArticleLog(userId, artTarget, artCont, artStatus, artIdx, artUrl);
		} else {
			model.addAttribute("message", "시각화 정보를 저장하지 못하였습니다.");
			model.addAttribute("result", "fail");
			// 파일 삭제
			if(file != null) file.delete();
			if(fileThunb != null) fileThunb.delete();
			
			return "jsonView";
		}

		model.addAttribute("result", "success");
		return "jsonView";
	}

	/**
	 * 시각화 삭제
	 * @param visId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String visualizeDelete(@RequestParam("visId") String visId, Model model) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("visId", visId);
		param.put("userId", getLoginUserId());

		EntityMap data = visualizeService.selectVisualize(param);
		if (data == null || data.get("visId") == null) {
			model.addAttribute("message", "데이터가 이미 삭제되었거나 찾을 수 없습니다.");
			model.addAttribute("redirect", "/bdp/visual/list.do");

			return "bigdata/error/message";
		}

		// 시각화 정보 삭제
		int res = visualizeService.deleteVisualize(visId, getLoginUserId());
		if(res > 0) {
			// 시각화 정보 삭제 이력 저장
			String artCont = data.getString("visTitle");
			String artTarget = "VISUAL";
			String artStatus = "DEL";
			int artIdx = Integer.valueOf(visId);
			String artUrl = "";
			int res1 = historyService.saveBdpUserArticleLog(getLoginUserId(), artTarget, artCont, artStatus, artIdx, artUrl);
		}

		// 시각화 데이터 삭제
		String visDataPath = data.getString("visDataPath");
		if (visDataPath.endsWith(".json")) {
			File file = new File(visDataPath);

			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}

		model.addAttribute("result", "fail");
		return "redirect:list.do";
	}

	/**
	 * 시각화 지도 섬네일을 생성하기위한 vworld 연계 프록시
	 * 
	 * @param response
	 * @param key
	 * @param type
	 * @param z
	 * @param y
	 * @param x
	 */
	@RequestMapping(value = "/proxy/map/{key}/{type}/{z}/{y}/{x}.do", method = RequestMethod.GET)
	public void proxyMapBackground(HttpServletResponse response,
							@PathVariable String key,
							@PathVariable String type,
							@PathVariable String z,
							@PathVariable String y,
							@PathVariable String x) {

		InputStream is = null;
		OutputStream os = null;
		try {
			response.setContentType("image/png");
			response.setHeader("Content-Disposition", String.format("attachment; filename=%s_%s_%s.png", z, y, x));

			String path = String.format("http://api.vworld.kr/req/wmts/1.0.0/%s/%s/%s/%s/%s.png", key, type, z, y, x);
			//System.out.println(path);

			URL url = new URL(path);
			URLConnection conn = url.openConnection();

			is = conn.getInputStream();
			os = response.getOutputStream();

			int size = 0;
			byte[] buffer = new byte[1024];
			while ((size = is.read(buffer)) != -1) {
				os.write(buffer, 0, size);
			}
			os.flush();
		} catch (Exception e) {} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e1) {}
			try {
				if (os != null)
					os.close();
			} catch (IOException e1) {}
		}
	}

}
