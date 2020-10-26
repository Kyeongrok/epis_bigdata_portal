package bigdata.portal.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
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
import bigdata.portal.service.AtchmnflService;
import bigdata.portal.service.NoticeService;
import egovframework.com.cmm.service.EgovProperties;
import kr.co.ucsit.core.CsFileVO;
import kr.co.ucsit.core.CsMap;
import kr.co.ucsit.core.CsTransferObject;
import kr.co.ucsit.core.CsUtil;
import kr.co.ucsit.web.util.CsWebUtil;

@Controller
@RequestMapping("/bdp/mypage/")
public class MyDataController extends BigdataController {

	private static Logger logger = LoggerFactory.getLogger(MyDataController.class);

	protected static final String P_BIGDATA_PORTAL = "bigdata/portal/";

	protected static final String JSON_VIEW = "jsonView";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 첨부파일
	 */
	@Autowired
	private AtchmnflService atchmnflService;

	@Autowired
	NoticeService noticeService;


	/**
	 * 첨부파일 저장 경로
	 */
	private static String uploadFilePath = EgovProperties.getProperty("app.upload.file.path");
	private static String kafkaHost = EgovProperties.getProperty("app.kafka.hostname");
	private static String kafkaPort = EgovProperties.getProperty("app.kafka.port");
	private static String kafkaTopic = EgovProperties.getProperty("app.kafka.topic");
	private static String kafkaUploadPath = EgovProperties.getProperty("app.kafka.upload.path");

	/**
	 * 목록조회
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="myDataList.do", method=RequestMethod.GET)
	public String noticeList(HttpServletRequest request, Model model) {

		CsMap paramMap = CsWebUtil.toParamMap(request);

		//기본 값 설정
		if(!paramMap.containsKey("pageIndex")) {
			paramMap.put("pageIndex", 1);
		}
		if(!paramMap.containsKey("bbsGbn")) {
			paramMap.put("bbsGbn", "D");
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

		paramMap.put("ntcrId", this.getLoginUserId());

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

		//kafkaTest();

		return P_BIGDATA_PORTAL + "mypage/myDataList";
	}

	/**
	 * mydata view
	 */
	@RequestMapping(value="myDataView.do", method=RequestMethod.GET)
	public String noticeView(HttpServletRequest request, Model model) {
		Map<String,Object> paramMap = CsWebUtil.toParamMap(request);

		//
		CsTransferObject trans = noticeService.getByNttId("" + paramMap.get("nttId"));
		if(trans.get("atchFileId") != null) {
			String atchFileId = trans.get("atchFileId").toString();
			List<Map<String, Object>> files = atchmnflService.getsByAtchFileId(atchFileId);
			model.addAttribute("files", files);
		}
		model.addAllAttributes(trans);
		model.addAttribute("loginUserId", this.getLoginUserId());
		model.addAttribute("paramMap", paramMap);

		return P_BIGDATA_PORTAL + "mypage/myDataView";
	}

	@RequestMapping("myDataFileUpload.json")
	public String myDataFileUpload(@RequestParam("file") MultipartFile  mpf, Model model) throws IllegalStateException, IOException {

		//업로드 처리
		CsFileVO vo = super.uploadFile(mpf, "");

		//
		model.addAttribute("fileVo", vo);

		return JSON_VIEW;
	}

	/**
	 * myData 등록폼
	 */
	@RequestMapping(value="registMyDataForm.do")
	public String registNoticeForm(HttpServletRequest request, Model model) {

		return P_BIGDATA_PORTAL + "mypage/myDataRegForm";
	}

	@RequestMapping(value="myDataDelete.json")
	public String myDataDelete(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		//
		noticeService.deleteByNttId(paramMap.getString("nttId"));

		return JSON_VIEW;
	}

	@RequestMapping("myDataUpdtForm.do")
	public String myDataUpdtForm(HttpServletRequest request, Model model) {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		// 데이터조회
		model.addAllAttributes(noticeService.getByNttId(paramMap.getString("nttId")));

		//
		return P_BIGDATA_PORTAL + "mypage/myDataUpdtForm";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("myDataUpdt.json")
	public String myDataUpdt(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		// 첨부된 파일이 있으면
		List<CsFileVO> fileVos = new ArrayList<>();

		int i = 0;
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
		noticeService.updt(paramMap.getString("nttId"), paramMap.getString("nttSj")
				, paramMap.getString("nttCn"), deletedFileNos, fileVos, this.getLoginUserId(), this.getLoginUserNm());

		return JSON_VIEW;
	}

	/**
	 * mydata 등록
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="registMyData.json")
	public String myDataInsert(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {

		CsMap paramMap = CsWebUtil.toParamMap(request);

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

	@RequestMapping(value="kafkaUpload.do")
	public String uploadKafka(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {
		CsMap paramMap = CsWebUtil.toParamMap(request);

		String nttId = paramMap.getString("nttId");
		CsTransferObject trans = noticeService.getByNttId(nttId);
		String atchFileId = trans.getData().get("atchFileId").toString();
		List<Map<String, Object>> files = atchmnflService.getsByAtchFileId(atchFileId);

		File file = Paths.get(uploadFilePath, ""+files.get(0).get("strePathName"), ""+files.get(0).get("streFileName")).toFile();
		String originFileName = files.get(0).get("originFileName").toString();

		try {
			visualDataUpload(file, originFileName);
			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("result", "fail");
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		//
		CsTransferObject dataViewTrans = noticeService.getByNttId("" + paramMap.get("nttId"));
		if(trans.get("atchFileId") != null) {
			String dataViewAtchFileId = dataViewTrans.get("atchFileId").toString();
			List<Map<String, Object>> dataViewFiles = atchmnflService.getsByAtchFileId(dataViewAtchFileId);
			model.addAttribute("files", dataViewFiles);
		}
		model.addAllAttributes(dataViewTrans);
		model.addAttribute("loginUserId", this.getLoginUserId());
		model.addAttribute("paramMap", paramMap);

		return P_BIGDATA_PORTAL + "mypage/myDataView";
	}

	@SuppressWarnings("unchecked")
	private void visualDataUpload(File file, String originFileName) throws IOException, InvalidFormatException {

		File csvFile = this.convExcelToCsv(file);

		CSVParser parser;
		parser = CSVParser.parse(csvFile, Charset.forName("UTF-8"), CSVFormat.EXCEL.withHeader());
		List<CSVRecord> records = parser.getRecords(); //header를 제외 전체 데이터
		List<String> csvHeader = parser.getHeaderNames(); // 헤더만 따로 저장

		// 임시 json 파일 업로드
		String jsonStr = "";

		for(int i = 0 ; i < records.size() ; i++) //몸통을 돌리고 row(열)...
		{
			JSONObject jb = new JSONObject();
			for(int j = 0 ; j < records.get(i).size() ; j++) //column(행)과 헤더를 돌려서 저장 한다.
			{
				jb.put(csvHeader.get(j), records.get(i).get(j));
			}
			String dataStr = jb.toString();
			jsonStr += dataStr + "\n";
		}

		String path = kafkaUploadPath;
		if(!isRoleAdmin()) {
			path += this.getLoginUserId().toString();
			path += "_";
		}
		path += FilenameUtils.getBaseName(originFileName);
		path += ".json";
		File f = new File(path);
		Writer fileWriter = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
		fileWriter.write(jsonStr);
		fileWriter.flush();
		fileWriter.close();

		parser.close();
	}

	private boolean isExcel(File file) {
		String extension = FilenameUtils.getExtension(file.getName());

		if("xlsx".equals(extension) || "xls".equals(extension)) {
			return true;
		}
		return false;
	}

	private boolean isCsv(File file) {
		String extension = FilenameUtils.getExtension(file.getName());

		if("csv".equals(extension)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("resource")
	private File convExcelToCsv(File xlsxFile)throws InvalidFormatException, IOException {

		if(isCsv(xlsxFile)) {
			return xlsxFile;
		} else if(!isExcel(xlsxFile)) {
			return null;
		}

		String extension = FilenameUtils.getExtension(xlsxFile.getName());

		String path = FilenameUtils.getFullPath(xlsxFile.getAbsolutePath())
				+ FilenameUtils.getBaseName(xlsxFile.getAbsolutePath())
				+ ".csv";

		File csvFile = new File(path);

		Workbook wb = null;
		if("xls".equals(extension)) {
			wb = new HSSFWorkbook(new FileInputStream(xlsxFile));
		} else {
			wb = new XSSFWorkbook(xlsxFile);
		}

		DataFormatter formatter = new DataFormatter();

		PrintStream out = new PrintStream(new FileOutputStream(csvFile), true, "UTF-8");

		for (Sheet sheet : wb) {
			int rowStart = Math.min(1, sheet.getFirstRowNum());
			int rowEnd = Math.max(2, sheet.getLastRowNum());
			for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
				Row r = sheet.getRow(rowNum);
				if (r == null) { // empty row handle
					continue;
				}

				int lastColumn = Math.max(r.getLastCellNum(), 1);
				boolean firstCell = true;
				for (int cn = 0; cn < lastColumn; cn++) {
					if ( ! firstCell ) out.print(',');
					Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					String text = "";
					if (cell != null) {
						// date cell 처리
						if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC
								&& HSSFDateUtil.isCellDateFormatted(cell)) {
							//text = cell.getDateCellValue();
							text = sdf.format(cell.getDateCellValue());
						} else {
							text = formatter.formatCellValue(cell);
						}
						if(text.contains(",")) {
							text = "\"" + text + "\"";
						}
						out.print(text);
					}
					firstCell = false;
				}
				out.println();
			}
		}

		return csvFile;
	}

}
