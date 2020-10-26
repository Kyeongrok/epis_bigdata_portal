package bigdata.portal.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.star.text.SectionFileLink;

import bigdata.portal.cmm.util.CommonUtil;
import bigdata.portal.entity.EntityMap;
import bigdata.portal.service.DatasetService;
import bigdata.portal.service.HdfsService;
import egovframework.com.cmm.service.EgovUserDetailsService;

@Controller
@RequestMapping("/excel/download")
public class ExcelDownloadController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDownloadController.class);
	@Autowired private DatasetService datasetService;
//	@Autowired private EgovUserDetailsService egovUserDetailsService;
//	@Autowired private HdfsService hdfsService;
	@Autowired CsvDownloadService csvDownloadService;
	
	/**
	 * 통계표 엑셀 다운로드
	 * 
	 * @param tblId
	 * @param prdDe
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value= "/staExcelDownload.do", method = RequestMethod.GET)
	public void staExcelDownload(
				@RequestParam(value="dsId", defaultValue="") String dsId,
				@RequestParam(value="prdDe", defaultValue="") String prdDe,
				HttpServletResponse response, Model model
			) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		String prdDeA = prdDe;
		
		HSSFWorkbook objWorkBook = new HSSFWorkbook();
		HSSFSheet objSheet = null;
		HSSFRow objRow = null;
		HSSFCell objCell = null;		// 셀 생성
		
		
		// 제목 폰트
		HSSFFont titFont = objWorkBook.createFont();
		titFont.setFontHeightInPoints((short)12);
		titFont.setBoldweight((short)titFont.BOLDWEIGHT_BOLD);
		titFont.setFontName("맑은고딕");

		// header 폰트
		HSSFFont hdFont = objWorkBook.createFont();
		hdFont.setFontHeightInPoints((short)9);
		hdFont.setBoldweight((short)hdFont.BOLDWEIGHT_BOLD);
		hdFont.setFontName("맑은고딕");		
		
		
		// 내용 폰트
		HSSFFont tableFont = objWorkBook.createFont();
		tableFont.setFontHeightInPoints((short)9);
		tableFont.setFontName("맑은고딕");
		
		
		// 타이틀 폰트 적용, 정렬
		HSSFCellStyle styleTit = objWorkBook.createCellStyle();		// 제목 스타일
		styleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		
		styleTit.setFont(titFont);
		
		// header 폰트 적용, 정렬
		HSSFCellStyle styleHd = objWorkBook.createCellStyle();		// 스타일
		styleHd.setFont(hdFont);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHd.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
		// 배경색
		styleHd.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		// 내용 폰트 적용, 정렬
		HSSFCellStyle styleCont = objWorkBook.createCellStyle();		// 스타일
		styleCont.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCont.setFont(tableFont);
		
		
		EntityMap dsInfo = csvDownloadService.getStaDsInfo(dsId);
		
		String dsName = dsInfo.getString("dsName");
		String fileName = dsName.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
		fileName = fileName.replaceAll(" ", "_");
		fileName = prdDeA+"_"+fileName;
		
		InputStream inputStream = null;
		inputStream = csvDownloadService.getStaTableCsv(dsInfo, prdDeA);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String read;
		
		objSheet = objWorkBook.createSheet("Sheet1");		// 워크시트 생성
		
		objRow = objSheet.createRow(0);
		objRow.setHeight((short) 0x150);
		objCell = objRow.createCell(0);
		objCell.setCellValue(dsName);
		objCell.setCellStyle(styleTit);
		
		int i = 0;
		
		List<String> headerDataList = new ArrayList<String>();
		objRow = objSheet.getRow(2);
		while((read = br.readLine()) != null ) {
			int j = 0;
			objRow = objSheet.createRow(i+3);
			objRow.setHeight((short) 0x150);
			
			String[] rows = read.split(",");
			for(String row : rows) {
				objCell = objRow.createCell(j);
				objCell.setCellValue(row);
				
				// cell 사이즈 조정을 위해
				if(i == 0) {
					headerDataList.add(row);
					objCell.setCellStyle(styleHd);
				} else {
					objCell.setCellStyle(styleCont);
				}
				
				j++;
			}
			
			i++;
		}
		
		
		// title cell 병합
		int firstRowCell = 0;
		int lastRowCell = 0;
		int firstColumnCell = 0;
		int lastColumnCell = headerDataList.size()-1;
		objSheet.addMergedRegion(new CellRangeAddress(firstRowCell, lastRowCell, firstColumnCell, lastColumnCell));
		
		// 표 사이즈 늘이기
		for(i = 0; i < headerDataList.size(); i++) {
			objSheet.autoSizeColumn(i);
		}
		
		
		
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode(fileName,"UTF-8")+".xls");

		OutputStream fileOut  = response.getOutputStream();
		objWorkBook.write(fileOut);
		fileOut.close();

		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
	
	
	/**
	 * 엑셀 파일 다운로드 BDP_STA_DIM_DT RAW 파일
	 * @param tblId
	 * @param prdDe
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/staExcelRawDownload.do", method = RequestMethod.GET)
	public void staExcelRawDownload(
					@RequestParam(value="tblId", defaultValue="") String tblId,
					@RequestParam(value="prdDe", defaultValue="") String prdDe,
					HttpServletResponse response, Model model) throws Exception {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("tblId", tblId);
		
		String prdDeA = prdDe;
		if(prdDeA.equals("")) {
			prdDeA = datasetService.selectBdpStdDimDtPreDe(param);
		}
		param.put("prdDe", prdDeA);
		
		List<String> dataList = datasetService.makeStaTable(param);
		String[] sample = dataList.get(0).split("\\|");
		int length = sample.length - 1; // value값은 빼고

		List<HashMap<String, String>> staList = new ArrayList<HashMap<String, String>>();

		for(String str : dataList) {
			String[] staColAry = str.split("\\|");
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			int i = 0;
			for(i = 0; i < length; i++) {
				map.put("col"+i, staColAry[i]);
			}
			map.put("val", staColAry[i]);
			
			staList.add(map);
		}
		
		param.put("limit", 1);
		List<EntityMap> stblInfoList = datasetService.selectBdpStdStblInfo(param);
		String tblNm = stblInfoList.get(0).getString("tblNm");

		Map<String, Object> mapData = new HashMap<String, Object>();
		
		int count = staList.get(0).size() -1;	// val값은 제외
		
		List<String> col0 = new ArrayList<String>();
		List<String> col1 = new ArrayList<String>();
		List<String> col2 = new ArrayList<String>();
		List<String> col3 = new ArrayList<String>();
		List<String> col4 = new ArrayList<String>();
		List<String> col5 = new ArrayList<String>();
		List<String> col6 = new ArrayList<String>();
		List<String> col7 = new ArrayList<String>();
		
		List<String> val = new ArrayList<String>();
		
		int staListLength = staList.size();
		
		for(int i=0; i < staListLength; i++) {
			if(!String.valueOf(staList.get(i).get("col0")).equals("") && !String.valueOf(staList.get(i).get("col0")).equals("null")) {
				String strCol0 = staList.get(i).get("col0").toString();
				col0.add(strCol0);
			}

			if(!String.valueOf(staList.get(i).get("col1")).equals("") && !String.valueOf(staList.get(i).get("col1")).equals("null")) {
				String strCol1 = staList.get(i).get("col1").toString();
				col1.add(strCol1);
			}
			if(!String.valueOf(staList.get(i).get("col2")).equals("") && !String.valueOf(staList.get(i).get("col2")).equals("null")) {
				String strCol2 = staList.get(i).get("col2").toString();
				col2.add(strCol2);
			}
			if(!String.valueOf(staList.get(i).get("col3")).equals("") && !String.valueOf(staList.get(i).get("col3")).equals("null")) {
				String strCol3 = staList.get(i).get("col3").toString();
				col3.add(strCol3);
			}
			if(!String.valueOf(staList.get(i).get("col4")).equals("") && !String.valueOf(staList.get(i).get("col4")).equals("null")) {
				String strCol4 = staList.get(i).get("col4").toString();
				col4.add(strCol4);
			}
			if(!String.valueOf(staList.get(i).get("col5")).equals("") && !String.valueOf(staList.get(i).get("col5")).equals("null")) {
				String strCol5 = staList.get(i).get("col5").toString();
				col5.add(strCol5);
			}
			if(!String.valueOf(staList.get(i).get("col6")).equals("") && !String.valueOf(staList.get(i).get("col6")).equals("null")) {
				String strCol6 = staList.get(i).get("col6").toString();
				col6.add(strCol6);
			}
			if(!String.valueOf(staList.get(i).get("col7")).equals("") && !String.valueOf(staList.get(i).get("col7")).equals("null")) {
				String strCol7 = staList.get(i).get("col7").toString();
				col7.add(strCol7);
			}

			if(!String.valueOf(staList.get(i).get("val")).equals("") && !String.valueOf(staList.get(i).get("val")).equals("")) {
				String strVal = String.valueOf(staList.get(i).get("val"));
				val.add(strVal);
			}
		}
		
		
		// List 중복값 제거
		List<String> uniqCol0 = CommonUtil.collectionListRemoveDuplicate(col0);
		List<String> uniqCol1 = CommonUtil.collectionListRemoveDuplicate(col1); 
		List<String> uniqCol2 = CommonUtil.collectionListRemoveDuplicate(col2);
		List<String> uniqCol3 = CommonUtil.collectionListRemoveDuplicate(col3);
		List<String> uniqCol4 = CommonUtil.collectionListRemoveDuplicate(col4);
		List<String> uniqCol5 = CommonUtil.collectionListRemoveDuplicate(col5);
		List<String> uniqCol6 = CommonUtil.collectionListRemoveDuplicate(col6);
		List<String> uniqCol7 = CommonUtil.collectionListRemoveDuplicate(col7);
		
		// header set
		List<String> headerDataList = new ArrayList<String>();

		// 2차원 ArrayList 생성
		List<List<String>> gridDataList = new ArrayList<List<String>>();
		
		List<HashMap<String, String>> leftTableTag = new ArrayList<HashMap<String, String>>();
		
		if(count == 2) {
			String chkFilter = staList.get(0).get("col1");

			for(HashMap<String, String> row : staList) {
				// chkFilter이 col1와 같으면 col0값을 변수에 저장
				if(row.get("col1").equals(chkFilter)) {
					HashMap<String, String> left = new HashMap<String, String>();
					left.put("left0", row.get("col0"));
					
					leftTableTag.add(left);
				}
			}
			
			// header set
			headerDataList.add("");
			
			//header cols set(top)
			for(String v : uniqCol1) {
				headerDataList.add(v.toString());
			}
			
			for(HashMap<String, String> v : leftTableTag) {
				
				List<String> rowDataAry = new ArrayList<String>();
				
				// 왼쪽 분류
				rowDataAry.add(v.get("left0"));
				
				for(String vv : uniqCol1) {
					int breakCount = 0;
					
					// 값을 rowDataAry에 저장
					for(HashMap<String, String> item : staList) {
						if( v.get("left0").equals(item.get("col0")) && vv.equals(item.get("col1")) ) {
							rowDataAry.add(item.get("val"));
							breakCount++;
							break;
						}
					}
					
					// breakCount가 0이면 빈이 들어가는 걸로 보고 문자열 0을 입력
					if(breakCount == 0) {
						rowDataAry.add("0");
					}
				}

				// row행 등록
				gridDataList.add(rowDataAry);

			}
			
		} else if(count == 3) {
			String chkFilter = staList.get(0).get("col2");
			
			for(HashMap<String, String> row : staList) {
				if(row.get("col2").equals(chkFilter)) {
					HashMap<String, String> left = new HashMap<String, String>();
					left.put("left0", row.get("col0"));
					left.put("left1", row.get("col1"));
					
					leftTableTag.add(left);
				}
			}
			
			// header set
			headerDataList.add("");
			headerDataList.add("");
			
			// header cols set(top)
			for(String v : uniqCol2) {
				headerDataList.add(v.toString());
			}
			
			for(HashMap<String, String> v : leftTableTag) {
				List<String> rowDataAry = new ArrayList<String>();
				
				// 왼쪽 분류
				rowDataAry.add(v.get("left0"));
				rowDataAry.add(v.get("left1"));
				
				for(String vv : uniqCol2) {
					int breakCount = 0;
					
					for(HashMap<String, String> item : staList) {
						if( v.get("left0").equals(item.get("col0")) && v.get("left1").equals(item.get("col1")) && vv.equals(item.get("col2")) ) {
							rowDataAry.add(item.get("val"));
							breakCount++;
							break;
						}
					}
					
					if(breakCount == 0) {
						rowDataAry.add("0");
					}

				}
				
				gridDataList.add(rowDataAry);
				
			}
			
		} else if(count == 4) {
			String chkFilter = staList.get(0).get("col3");
			
			for(HashMap<String, String> row : staList) {
				if(row.get("col3").equals(chkFilter)) {
					HashMap<String, String> left = new HashMap<String, String>();
					left.put("left0", row.get("col0"));
					left.put("left1", row.get("col1"));
					left.put("left2", row.get("col2"));
					
					leftTableTag.add(left);
				}
			}
			
			// header set
			headerDataList.add("");
			headerDataList.add("");
			headerDataList.add("");
			
			// header cols set(top)
			for(String v : uniqCol3) {
				headerDataList.add(v.toString());
			}
			
			for(HashMap<String, String> v : leftTableTag) {
				List<String> rowDataAry = new ArrayList<String>();
				
				// 왼쪽분류
				rowDataAry.add(v.get("left0"));
				rowDataAry.add(v.get("left1"));
				rowDataAry.add(v.get("left2"));
				
				for(String vv : uniqCol3) {
					int breakCount = 0;
					
					for(HashMap<String, String> item : staList) {
						if( v.get("left0").equals(item.get("col0")) 
								&& v.get("left1").equals(item.get("col1")) 
								&& v.get("left2").equals(item.get("col2"))
								&& vv.equals(item.get("col3"))
								) {
							rowDataAry.add(item.get("val"));
							breakCount++;
							break;
						}
					}
					
					if(breakCount == 0) {
						rowDataAry.add("0");
					}
					
				}
				
				gridDataList.add(rowDataAry);
			}
			
		}
		

		HSSFWorkbook objWorkBook = new HSSFWorkbook();
		HSSFSheet objSheet = null;
		HSSFRow objRow = null;
		HSSFCell objCell = null;		// 셀 생성
		
		
		// 제목 폰트
		HSSFFont titFont = objWorkBook.createFont();
		titFont.setFontHeightInPoints((short)12);
		titFont.setBoldweight((short)titFont.BOLDWEIGHT_BOLD);
		titFont.setFontName("맑은고딕");

		// header 폰트
		HSSFFont hdFont = objWorkBook.createFont();
		hdFont.setFontHeightInPoints((short)9);
		hdFont.setBoldweight((short)hdFont.BOLDWEIGHT_BOLD);
		hdFont.setFontName("맑은고딕");		
		
		// 내용 폰트
		HSSFFont tableFont = objWorkBook.createFont();
		tableFont.setFontHeightInPoints((short)9);
		tableFont.setFontName("맑은고딕");
		
		
		// 타이틀 폰트 적용, 정렬
		HSSFCellStyle styleTit = objWorkBook.createCellStyle();		// 제목 스타일
		styleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		
		styleTit.setFont(titFont);
		
		// header 폰트 적용, 정렬
		HSSFCellStyle styleHd = objWorkBook.createCellStyle();		// 스타일
		styleHd.setFont(hdFont);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHd.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHd.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);
		
		// 내용 폰트 적용, 정렬
		// header 폰트 적용, 정렬
		HSSFCellStyle styleCont = objWorkBook.createCellStyle();		// 스타일
		styleCont.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleCont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCont.setFont(tableFont);
		
		
		objSheet = objWorkBook.createSheet("Sheet1");		// 워크시트 생성
		
		objRow = objSheet.createRow(0);
		objRow.setHeight((short) 0x150);
		objCell = objRow.createCell(0);
		objCell.setCellValue(tblNm);
		objCell.setCellStyle(styleTit);
		
		// header set
		int i = 0;
		objRow = objSheet.createRow(2);
		objRow.setHeight((short) 0x150);
		for(String headerRow : headerDataList) {
			objCell = objRow.createCell(i);
			objCell.setCellValue(headerRow.toString());
			objCell.setCellStyle(styleHd);
			
			i++;
		}
		
		// data set
		i = 0;
		for(List<String> list : gridDataList) {
			int j = 0;
			objRow = objSheet.createRow(i+3);
			objRow.setHeight((short) 0x150);
			for(String row : list) {
				objCell = objRow.createCell(j);
				objCell.setCellValue(row);
				objCell.setCellStyle(styleCont);
				
				j++;
			}
			
			i++;
		}
		
		// 왼쪽 header left 항목명을 가져오기 위해 사용됨
		List<String> cdNmList = new ArrayList<String>();
		if(count == 2) {
			// 왼쪽 left명 정보추출(hedaer left 정보를 사용하기 위해 사용)
			cdNmList.add(gridDataList.get(0).get(0));
		} else if(count == 3) {
			cdNmList.add(gridDataList.get(0).get(0));
			cdNmList.add(gridDataList.get(0).get(1));
		} else if(count == 4) {
			cdNmList.add(gridDataList.get(0).get(0));
			cdNmList.add(gridDataList.get(0).get(1));
			cdNmList.add(gridDataList.get(0).get(2));
		}

		HashMap<String, Object> paramHd = new HashMap<String, Object>();
		paramHd.put("tblId", tblId);
		paramHd.put("cdNmAry", cdNmList);
		List<EntityMap> leftHeaderList = datasetService.selectStaLeftTopNames(paramHd);
		
		i = 0;
		objRow = objSheet.getRow(2);
		for(EntityMap leftRow : leftHeaderList) {
			objCell = objRow.getCell(i);
			objCell.setCellValue(leftRow.getString("cdNm"));
			i++;
		}
		
		// title cell 병합
		int firstRowCell = 0;
		int lastRowCell = 0;
		int firstColumnCell = 0;
		int lastColumnCell = headerDataList.size()-1;
		objSheet.addMergedRegion(new CellRangeAddress(firstRowCell, lastRowCell, firstColumnCell, lastColumnCell));
		
		// 표 사이즈 늘이기
		for(i = 0; i < headerDataList.size(); i++) {
			objSheet.autoSizeColumn(i);
		}
		
		String fileName = tblNm.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "_");
		
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode(fileName,"UTF-8")+".xls");

		OutputStream fileOut  = response.getOutputStream();
		objWorkBook.write(fileOut);
		fileOut.close();

		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}

}
