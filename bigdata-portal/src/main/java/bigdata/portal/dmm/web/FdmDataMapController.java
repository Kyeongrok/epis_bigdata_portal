package bigdata.portal.dmm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import bigdata.portal.dmm.entity.Paging;
import bigdata.portal.dmm.entity.SearchSysInfo;
import bigdata.portal.dmm.entity.TableMetaData;
import bigdata.portal.dmm.entity.TreeMap;
import bigdata.portal.dmm.service.FdmDataMapService;

/**
 * 데이터 지도 메인 컨트롤러
 *
 * @author THEIMC 빅데이터R팀 허현범
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일         수정자           수정내용
 *  -------------    --------    ---------------------------
 *   2017. 8. 28.     허현범          최초 생성
 *      </pre>
 * 
 * @since 2017. 8. 28.
 */
@Controller
public class FdmDataMapController {

	@Autowired FdmDataMapService dataMapService;

	private static final Logger LOGGER = LoggerFactory.getLogger(FdmDataMapController.class);
	private static final int LIMIT_PRINT_NUM = 5;

	/**
	 * 데이터 지도 인덱스 화면
	 *
	 * @param model
	 * @return view url path
	 * @throws Exception
	 */
	@RequestMapping(value = "/bdp/dmm/main.do", method = RequestMethod.GET)
	public String main(HttpServletRequest request, ModelMap model) throws Exception {		
		
		model.addAttribute("menuNum", 1);
		model.addAttribute("pageNum", 5);
		
		return "bigdata/portal/datamap/main2";

	}

	@RequestMapping(value = "/bdp/dmm/printTreeMap.do", method = RequestMethod.GET)
	public void treeMap(HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		String fdmorgCode = request.getParameter("fdmorgCode");
		String syslstCode = request.getParameter("syslstCode");
		String dbCode = request.getParameter("dbCode");

		List<TreeMap> treeMapList = dataMapService.getTreeMapFdmorg();
		List<TreeMap> sysTreeNodeData = null;
		List<TreeMap> dbTreeNodeData = null;
		List<TreeMap> tableTreeNodeData = null;
		HashMap<String, String> param = new HashMap<String, String>();

		if (!fdmorgCode.equals("")) {
			param.put("fdmorgCode", fdmorgCode);
			sysTreeNodeData = dataMapService.getTreeMapSysInfo(param);
			treeMapList.addAll(sysTreeNodeData);
		}

		if (!syslstCode.equals("")) {
			param.put("syslstCode", syslstCode);
			dbTreeNodeData = dataMapService.getTreeMapDatabase(param);
			treeMapList.addAll(dbTreeNodeData);
		} else {
			param.put("syslstCode", sysTreeNodeData.get(0).getId());
			dbTreeNodeData = dataMapService.getTreeMapDatabase(param);
			treeMapList.addAll(dbTreeNodeData);
		}

		if (!dbCode.equals("")) {
			param.put("dbCode", dbCode);
			treeMapList.addAll(dataMapService.getTreeMapTable(param));
		} else {
			if (dbTreeNodeData != null && dbTreeNodeData.size() > 0) {
				param.put("dbCode", dbTreeNodeData.get(0).getId());
				tableTreeNodeData = dataMapService.getTreeMapTable(param);
				treeMapList.addAll(tableTreeNodeData);
			}

		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("graph", treeMapList);

		try {
			String convertJSON = mapper.writeValueAsString(data);
			LOGGER.debug(convertJSON);
			response.getWriter().print(convertJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 기관 그래프 노드 로드
	 *
	 * @param codeType
	 * @param code
	 * @param response
	 * @param request
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/bdp/dmm/graph.do", method = RequestMethod.POST)
	public void graph(@RequestParam("codeType") String codeType,
							@RequestParam("code") String code,
							@RequestParam("mode") String mode,
							HttpServletResponse response,
							HttpServletRequest request,
							ModelMap model) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, String> dataCode = new HashMap<String, String>();
		HashMap<String, Object> data = null;
		
		//System.out.println("CODETYPE = " + codeType.toUpperCase());

		dataCode.put("codeType", codeType.toUpperCase());
		dataCode.put("code", code);

		if (codeType.equals("fdmorg")) {
			data = dataMapService.getFDMORGGraphData(dataCode);
		} else {
			dataCode.put("rootNodeName", "농림축산식품부");
			data = dataMapService.getGraphData(dataCode);
		}

		try {
			String convertJSON = mapper.writeValueAsString(data);
			LOGGER.debug(convertJSON);
			response.getWriter().print(convertJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 정보시스템 그래프 노드 로드
	 *
	 * @param codeType
	 * @param code
	 * @param id
	 * @param totalNodeCnt
	 * @param response
	 * @param request
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/bdp/dmm/childNode.do", method = RequestMethod.POST)
	public void childNode(@RequestParam("codeType") String codeType,
							@RequestParam("code") String code,
							@RequestParam("lastID") int lastID,
							@RequestParam("rootID") int rootID,
							HttpServletResponse response,
							HttpServletRequest request,
							ModelMap model) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, String> dataCode = new HashMap<String, String>();
		dataCode.put("codeType", codeType.toUpperCase());
		dataCode.put("code", code);
		dataCode.put("lastID", String.valueOf(lastID));
		dataCode.put("rootID", String.valueOf(rootID));

		HashMap<String, Object> data = dataMapService.getGraphChildData(dataCode);

		try {
			String convertJSON = mapper.writeValueAsString(data);
			LOGGER.debug(convertJSON);
			response.getWriter().print(convertJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bdp/dmm/erdImg", method = RequestMethod.GET)
	public String erdImg(HttpServletRequest request, ModelMap model) throws Exception {

		HashMap<String, String> dataCode = new HashMap<String, String>();

		String syslst = request.getParameter("syslst");
		String dblst = request.getParameter("dblst");
		String modelType = request.getParameter("modelType");

		if (syslst != null) {
			dataCode.put("codeType", "SYSLST");
			dataCode.put("code", syslst);
		}

		if (dblst != null) {
			dataCode.put("codeType", "DBLIST");
			dataCode.put("code", dblst);
		}

		if (modelType == null) {
			modelType = "l";
		}

		if (!modelType.equals("l") && !modelType.equals("p")) {
			modelType = "l";
		}

		dataCode.put("modelType", modelType);

		model.addAttribute("modelType", modelType);
		model.addAttribute("erdImages", dataMapService.getErdImgInfo(dataCode));

		return "datamap/single/dmm/erd";

	}

	@RequestMapping(value = "/bdp/dmm/search.do", method = RequestMethod.POST)
	public void search(@RequestParam("keyword") String keyword, HttpServletResponse response, HttpServletRequest request) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", keyword);
		param.put("offset", 0);
		param.put("limit", LIMIT_PRINT_NUM);

		int searchDbInfoCnt = dataMapService.getSearchDbInfoCount(param);
		int searchSysInfoCnt = dataMapService.getSearchSysInfoCount(param);

		ArrayList<SearchSysInfo> returnSearchSysInfo = dataMapService.selectSearchSysInfo(param);
		List<TableMetaData> returnSearchTableInfo = dataMapService.selectSearchTableInfo(param);

		HashMap<String, Object> dataList = new HashMap<String, Object>();
		dataList.put("searchDbInfoCnt", searchDbInfoCnt);
		dataList.put("searchSysInfoCnt", searchSysInfoCnt);
		dataList.put("searchSysInfo", returnSearchSysInfo);
		dataList.put("searchTableInfo", returnSearchTableInfo);

		Paging sysPaging = new Paging(LIMIT_PRINT_NUM, searchSysInfoCnt);
		dataList.put("sysPaging", sysPaging.makePaging(0, "system", 1));

		Paging tablePaging = new Paging(LIMIT_PRINT_NUM, searchDbInfoCnt);
		dataList.put("tablePaging", tablePaging.makePaging(0, "table", 1));

		try {
			String convertJSON = mapper.writeValueAsString(dataList);
			LOGGER.debug(convertJSON);
			response.getWriter().print(convertJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bdp/dmm/paging.do", method = RequestMethod.POST)
	public void pagingToNext(@RequestParam("searchSystemPage") String searchSystemPage,
							@RequestParam("searchTablePage") String searchTablePage,
							@RequestParam("pagingType") String pagingType,
							@RequestParam("keyword") String keyword,
							HttpServletResponse response,
							HttpServletRequest request) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, Object> sysParam = new HashMap<String, Object>();
		HashMap<String, Object> tableParam = new HashMap<String, Object>();

		sysParam.put("offset", (Integer.parseInt(searchSystemPage) - 1) * LIMIT_PRINT_NUM);
		tableParam.put("offset", (Integer.parseInt(searchTablePage) - 1) * LIMIT_PRINT_NUM);
		sysParam.put("keyword", keyword);
		tableParam.put("keyword", keyword);
		sysParam.put("limit", LIMIT_PRINT_NUM);
		tableParam.put("limit", LIMIT_PRINT_NUM);

		int sysCount = dataMapService.getSearchSysInfoCount(sysParam);
		int tableCount = dataMapService.getSearchDbInfoCount(tableParam);

		HashMap<String, Object> dataList = new HashMap<String, Object>();
		Paging sysPaging = new Paging(LIMIT_PRINT_NUM, sysCount);
		dataList.put("sysPaging", sysPaging.makePaging(Integer.parseInt(searchSystemPage) - 1, "system", Integer.parseInt(searchSystemPage)));

		Paging tablePaging = new Paging(LIMIT_PRINT_NUM, tableCount);
		dataList.put("tablePaging", tablePaging.makePaging(Integer.parseInt(searchTablePage) - 1, "table", Integer.parseInt(searchTablePage)));

		dataList.put("searchSystemPage", searchSystemPage);
		dataList.put("searchTablePage", searchTablePage);
		dataList.put("pageType", pagingType);

		ArrayList<SearchSysInfo> returnSearchSysInfo = dataMapService.selectSearchSysInfo(sysParam);
		dataList.put("searchSysInfo", returnSearchSysInfo);

		List<TableMetaData> returnSearchTableInfo = dataMapService.selectSearchTableInfo(tableParam);
		dataList.put("searchTableInfo", returnSearchTableInfo);

		dataList.put("searchDbInfoCnt", tableCount);
		dataList.put("searchSysInfoCnt", sysCount);

		try {
			String convertJSON = mapper.writeValueAsString(dataList);
			LOGGER.debug(convertJSON);
			response.getWriter().print(convertJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메타데이터 데이터 JSON 형태 로드
	 *
	 * @param code
	 * @param codeType
	 * @param response
	 */
	@RequestMapping(value = "/bdp/dmm/metadata.do", method = RequestMethod.POST)
	public void metadata(@RequestParam("code") String code, @RequestParam("codeType") String codeType, HttpServletResponse response) {

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, Object> data = new HashMap<String, Object>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("code", code);

		if (codeType.equals("system")) {
			data.put("sysMetaInfo", dataMapService.selectSysMetadataInfo(param));
		} else if (codeType.equals("database")) {
			data.put("dbMetaInfo", dataMapService.selectDbMetadataInfo(param));
		} else {
			data.put("tableMetaInfo", dataMapService.selectTableMetadataInfo(param));
		}

		try {
			response.getWriter().print(mapper.writeValueAsString(data));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/bdp/dmm/dbMetadata.do", method = RequestMethod.POST)
	public void dbMetaData(@RequestParam("sysCode") String sysCode, @RequestParam("dbNm") String dbNm, HttpServletResponse response) {
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");

		HashMap<String, Object> data = new HashMap<String, Object>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("sysCode", sysCode);
		param.put("dbNm", dbNm);

		data.put("dbMetaInfo", dataMapService.selectDbMetadataInfo(param));

		try {
			response.getWriter().print(mapper.writeValueAsString(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
