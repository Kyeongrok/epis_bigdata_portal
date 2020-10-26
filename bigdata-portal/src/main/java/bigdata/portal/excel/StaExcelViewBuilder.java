package bigdata.portal.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import bigdata.portal.entity.EntityMap;
import egovframework.rte.fdl.excel.util.AbstractPOIExcelView;

//@Component("downloadStaExcelXlsxView")
public class StaExcelViewBuilder extends AbstractPOIExcelView {
	private static final Logger LOGGER = LoggerFactory.getLogger(StaExcelViewBuilder.class);

	@Override
	protected void buildExcelDocument(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		XSSFCell cell = null;
		
		XSSFSheet sheet = wb.createSheet("staTable");
		sheet.setDefaultColumnWidth(12);
		
		Map<String, Object> map = (Map<String, Object>) model.get("staListMap");
		String tblNm = (String) map.get("tblNm");
		List<HashMap<String, String>> staList = (List<HashMap<String, String>>) map.get("staList");
		
		// put text in first cell
		cell = getCell(sheet, 0, 0);
		setText(cell, tblNm);
		

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
		List<String> uniqCol0 = collectionListRemoveDuplicate(col0);
		List<String> uniqCol1 = collectionListRemoveDuplicate(col1); 
		List<String> uniqCol2 = collectionListRemoveDuplicate(col2);
		List<String> uniqCol3 = collectionListRemoveDuplicate(col3);
		List<String> uniqCol4 = collectionListRemoveDuplicate(col4);
		List<String> uniqCol5 = collectionListRemoveDuplicate(col5);
		List<String> uniqCol6 = collectionListRemoveDuplicate(col6);
		List<String> uniqCol7 = collectionListRemoveDuplicate(col7);
		
		// header set
		List<String> headerDataList = new ArrayList<String>();
		//List<HashMap<String, Object>> gridDataList = new ArrayList<HashMap<String, Object>>();
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

		// header set
		int i=0;
		for(String headerRow : headerDataList) {
			cell = getCell(sheet, 1, i);
			setText(cell, headerRow.toString());
			i++;
		}

		// data set
		i = 0;
		for(List<String> list : gridDataList) {
			int j = 0;
			for(String row : list) {				
				cell = getCell(sheet, 2+i, j);
				setText(cell, row);
				j++;
			}
			i++;
		}

	}

	// List collection 요소 중복 순차 제거
	public ArrayList<String> collectionListRemoveDuplicate(List<String> list) {
	    ArrayList<String> result = new ArrayList<>();
	    HashSet<String> set = new HashSet<>();
	    for (String item : list) {
	      if (!set.contains(item)) {
	        result.add(item);
	        set.add(item);
	      }
	    }
	    return result;
	}
}
