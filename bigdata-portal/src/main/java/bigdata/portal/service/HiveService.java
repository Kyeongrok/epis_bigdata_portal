package bigdata.portal.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;
import bigdata.portal.hive.HiveData;
import bigdata.portal.hive.HiveJdbcConn;
import bigdata.portal.hive.HiveTable;

/**
 * Hive 데이터 조회 지원을 위한 서비스 클래스
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
 * 
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
 *   2018. 10. 31.     JHY          최초 생성
 *      </pre>
 * 
 * @since 2018. 10. 31.
 */
@Service
public class HiveService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HiveService.class);
	
	@Autowired HiveJdbcConn hiveJdbcConn;
	@Autowired HdfsService hdfsService;

	@Value("${hive.user.homedir}") String hiveHomedir;
	@Value("${hive.tmpdir}") String hiveTmpdir;
	
	public EntityMap selectRawData(HashMap<String, Object> param) {
		return null;
	}

	// sample
	public List<EntityMap> selectSampleMarkerPriceRawData() {

		List<EntityMap> dataList = new ArrayList<EntityMap>();
		Connection con = null;
		
		try {
			con = hiveJdbcConn.getConn();
			Statement stmt = con.createStatement();
			String sql = "select * from market_price limit 100";
			ResultSet res = stmt.executeQuery(sql);
			
			while (res.next()) {
				EntityMap data = new EntityMap();
				
				data.put("meridanDate", res.getString("MERIDAN_DATE"));
				data.put("auctionTime", res.getString("AUCTION_TIME"));
				data.put("auctionDivisionCode", res.getString("AUCTION_DIVISION_CODE"));
				data.put("auctionDivisionCodename", res.getString("AUCTION_DIVISION_CODENAME"));
				data.put("marketCode", res.getString("MARKET_CODE"));
				data.put("marketName", res.getString("MARKET_NAME"));
				data.put("oldMarket", res.getString("OLD_MARKET"));
				data.put("oldMarketName", res.getString("OLD_MARKET_NAME"));
				data.put("marketCorporationCode", res.getString("MARKET_CORPORATION_CODE"));
				data.put("companyName", res.getString("COMPANY_NAME"));
				data.put("oldCompanyCode", res.getString("OLD_COMPANY_CODE"));
				data.put("oldCompanyName", res.getString("OLD_COMPANY_NAME"));
				data.put("auctionItemNum", res.getString("AUCTION_ITEM_NUM"));
				data.put("serialNum", res.getString("SERIAL_NUM"));
				data.put("barcketCode", res.getString("BARCKET_CODE"));
				data.put("className", res.getString("CLASS_NAME"));
				data.put("oldClassCode", res.getString("OLD_CLASS_CODE"));
				data.put("oldClassName", res.getString("OLD_CLASS_NAME"));
				data.put("itemCode", res.getString("ITEM_CODE"));
				data.put("itemName", res.getString("ITEM_NAME"));
				data.put("oldItemCode", res.getString("OLD_ITEM_CODE"));
				data.put("oldItemName", res.getString("OLD_ITEM_NAME"));
				data.put("breedCode", res.getString("BREED_CODE"));
				data.put("breedName", res.getString("BREED_NAME"));
				data.put("oldBreedCode", res.getString("OLD_BREED_CODE"));
				data.put("oldBreedName", res.getString("OLD_BREED_NAME"));
				data.put("tradingUnit", res.getString("TRADING_UNIT"));
				data.put("unitCode", res.getString("UNIT_CODE"));
				data.put("unitName", res.getString("UNIT_NAME"));
				data.put("packingStatusCode", res.getString("PACKING_STATUS_CODE"));
				data.put("packingStatusName", res.getString("PACKING_STATUS_NAME"));
				data.put("sizeCode", res.getString("SIZE_CODE"));
				data.put("sizeName", res.getString("SIZE_NAME"));
				data.put("ratingCode", res.getString("RATING_CODE"));
				data.put("ratingName", res.getString("RATING_NAME"));
				data.put("corporateUseCode", res.getString("CORPORATE_USE_CODE"));
				data.put("corporateUseName", res.getString("CORPORATE_USE_NAME"));
				data.put("transactionPrice", res.getString("TRANSACTION_PRICE"));
				data.put("identificationCode", res.getString("IDENTIFICATION_CODE"));
				data.put("identificationName", res.getString("IDENTIFICATION_NAME"));
				data.put("originCode", res.getString("ORIGIN_CODE"));
				data.put("originName", res.getString("ORIGIN_NAME"));
				data.put("oldOriginCode", res.getString("OLD_ORIGIN_CODE"));
				data.put("oldOriginName", res.getString("OLD_ORIGIN_NAME"));
				data.put("volume", res.getString("VOLUME"));

				dataList.add(data);
			}

			hiveJdbcConn.close(stmt, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			hiveJdbcConn.close(con);
		}

		return dataList;
	}

	/**
	 * 테이블 생성
	 * 
	 * @param hiveTable
	 * @return
	 */
	public boolean createTable(HiveTable hiveTable) {

		boolean rs = hiveJdbcConn.createTable(hiveTable);

		if (rs) {
			// INDEX 생성
			if (hiveTable.getIndex() != null) {
				for (String column : hiveTable.getIndex()) {
					rs &= hiveJdbcConn.createIndex(hiveTable.getTableId(), column);
				}
			}

			return rs;
		}
		return false;
	}

	/**
	 * 파일 구조 CSV 사용
	 * 
	 * @param hiveTable
	 * @return
	 */
	public boolean createTableCSV(HiveTable hiveTable) {
		boolean rs = hiveJdbcConn.createTableCSV(hiveTable);
		if (rs) {
			// INDEX 생성
			if (hiveTable.getIndex() != null) {
				for (String column : hiveTable.getIndex()) {
					rs &= hiveJdbcConn.createIndex(hiveTable.getTableId(), column);
				}
			}

			return rs;
		}
		return false;

	}

	/**
	 * 테이블 삭제
	 * 
	 * @param tableId
	 * @return
	 */
	public boolean dropTable(String tableId) {
		return hiveJdbcConn.dropTable(tableId);
	}

	/**
	 * 테이블 비우기
	 * 
	 * @param tableId
	 * @return
	 */
	public boolean truncateTable(String tableId) {
		return hiveJdbcConn.truncateTable(tableId);
	}

	/**
	 * 테이블 목록
	 * 
	 * @return
	 */
	public List<EntityMap> showTables() {
		List<EntityMap> list = hiveJdbcConn.showTables();
		List<EntityMap> rlist = new ArrayList<EntityMap>();

		for (EntityMap data : list) {
			if (!data.getString("tabName").matches("[a-zA-Z0-9_]*_idx__$")) {
				rlist.add(data);
			}
		}

		return rlist;
	}

	/**
	 * 테이블 정보
	 * 
	 * @param tableId
	 * @return
	 */
	public Map<String, String[]> descTable(String tableId) {
		return hiveJdbcConn.descTable(tableId);
	}
	
	
	/**
	 * @param tableId
	 * @param limit 
	 * @return
	 */
	public List<EntityMap> selectTable(String tableId, int limit) {
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		String sql = "SELECT * FROM " + tableIdClean + " LIMIT " + limit;
		return selectQuery(sql);
	}
	
	/**
	 * 데이터 조회 
	 * 
	 * @param tableId
	 * @param limit
	 * @return
	 */
	public List<Object[]> selectTableArray(String tableId, int limit, boolean incHeader) {
		
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		String sql = "SELECT * FROM " + tableIdClean;
		
		if(limit > 0)  sql = sql + " LIMIT " + limit;
		
		if (sql == null || !sql.trim().toUpperCase().startsWith("SELECT")) {
			return new ArrayList<Object[]>();
		}
		
		return hiveJdbcConn.executeQueryReturnArray(sql, incHeader);
	}
	
	public List<Object[]> selectTableArray(String tableId, int start, int end, boolean incHeader) {
		
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		
		Object[] columnNames = descTable(tableIdClean).keySet().toArray();
		StringBuffer sqlColumnName = new StringBuffer();
		
		// sequence 필드 제거
		for(int i=0; i<columnNames.length; i++) {
			
			if(columnNames[i].toString().toLowerCase().equals("sequence")) {
				
			} else {
				if(i >= 1  && i <= (columnNames.length - 1)) {
					sqlColumnName.append("," + columnNames[i]);
				} else {
					sqlColumnName.append(columnNames[i]);
				}
			}
		}
		
		
		String sql = String.format("SELECT %s FROM %s limit %d",
				sqlColumnName.toString(),
				//columnNames[0],
				tableIdClean,
				end);
		
		
		/*
		// sequence 컬럼이 있을 때 사용하는 쿼
		String sql = String.format("SELECT %s FROM %s where sequence > %d and sequence <= %d",
			sqlColumnName.toString(),
			tableIdClean,
			start,
			end
		);
		
		String sql = String.format("SELECT %s FROM (SELECT ROW_NUMBER() OVER(ORDER BY %s) AS ROWNUM, * FROM %s) t1 where ROWNUM > %d AND ROWNUM <= %d",
				sqlColumnName.toString(),
				columnNames[0],
				tableIdClean,
				start,
				end);
		

		String sql = String.format("SELECT %s FROM %s limit %d, %d",
				sqlColumnName.toString(),
				tableIdClean,				
				start,
				end);
		*/
		
		return hiveJdbcConn.executeQueryReturnArray(sql, incHeader);
	}
	
	/**
	 * 데이터 조회 (prepared statement)
	 * @param tableId
	 * @param where
	 * @param whereValues
	 * @param limit
	 * @param incHeader
	 * @return
	 */
	public List<Object[]> selectTableArray(String tableId, String where, String[] whereValues, int limit, boolean incHeader) {
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		List<Object> whereValueList = new ArrayList<Object>();
		for(String row : whereValues) {
			whereValueList.add(hiveJdbcConn.cleanSql(row, true));
		}
		
		LOGGER.debug("whereValueList = " + whereValueList.toString());
		
		String sql = "SELECT * FROM " + tableIdClean + " WHERE " + where;
		
		if(limit > 0)  sql = sql + " LIMIT " + limit;
		
		if (sql == null || !sql.trim().toUpperCase().startsWith("SELECT")) {
			return new ArrayList<Object[]>();
		}
		
		return hiveJdbcConn.executeQueryReturnArray(sql, whereValueList, incHeader);
	}
	
	
	/**
	 * 데이터 조회 (prepared statement)
	 * @param tableId
	 * @param where
	 * @param params
	 * @param limit
	 * @return
	 */
	public List<Object[]> selectTableArray(String tableId, String where, ArrayList<Object> params, int limit) {
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		String whereClean = hiveJdbcConn.cleanSql(where);
		
		String sql = "SELECT * FROM " + tableIdClean + " WHERE " + whereClean + " LIMIT " + limit;
		
		if (sql == null || !sql.trim().toUpperCase().startsWith("SELECT")) {
			return new ArrayList<Object[]>();
		}
		
		return hiveJdbcConn.executeQueryReturnArray(sql, params, false);
	}	
	
	public String selectTableNumRows(String tableId) {
		String tableIdClean = hiveJdbcConn.cleanSql(tableId);
		Map<String, String> map = hiveJdbcConn.showPropertiesTable(tableIdClean);
		
		return map.get("numRows");
	}


	/**
	 * @param sql
	 * @return
	 */
	public List<EntityMap> selectQuery(String sql) {
		if (sql == null || !sql.trim().toUpperCase().startsWith("SELECT")) {
			return new ArrayList<EntityMap>();
		}
		return hiveJdbcConn.selectQuery(sql);
	}

	/**
	 * HDFS로 부터 데이터 로드
	 * 코드 빅데이터플랫폼에 맞게 수정 필요!!
	 * 
	 * @param hdfsPath
	 * @param inputData
	 * @return
	 */
	public boolean loadFile(HiveData inputData) {
		boolean result = true;

		if (inputData.getTableId() == null || inputData.getTableId().trim().equals("") || inputData.getFileName() == null
								|| inputData.getFileName().trim().equals("")) {
			return false;
		}

		if (inputData.getTableId().indexOf("..") >= 0 || inputData.getFileName().indexOf("..") >= 0) {
			return false;
		}

		String filePath = hiveTmpdir + "/" + inputData.getFileName() + inputData.getExtensions();

		filePath = FilenameUtils.separatorsToUnix(filePath);
		filePath = filePath.replaceAll("[/]+", "/");

		String data = inputData.getData();
		if (data == null || data.trim().equals("")) {
			return false;
		}
		data = data.trim();

		try {
			// 임시파일 업로드
			String tmpFilePath = filePath;
			int num = 1;
			while (hdfsService.isExistsFile(tmpFilePath)) {
				tmpFilePath = filePath + "." + num;
			}
			filePath = tmpFilePath;

			hdfsService.writeFile(data, filePath);

			result = hiveJdbcConn.loadFile(inputData.getTableId(), filePath, inputData.isOverwrite());
			
			// 임시파일 삭제
			hdfsService.deleteFile(filePath);
		} catch (IOException e) {
			result = false;
		}

		return result;
	}

	/**
	 * 데이터 입력 HIVE 디렉터리 아래 파일 쓰기
	 * 코드 빅데이터플랫폼에 맞게 수정 필요!!
	 * 
	 * @param inputData
	 * @return
	 */
	public boolean inputData(HiveData inputData) {
		boolean result = true;
		if (inputData.getTableId() == null || inputData.getTableId().trim().equals("") || inputData.getFileName() == null
								|| inputData.getFileName().trim().equals("")) {
			return false;
		}

		if (inputData.getTableId().indexOf("..") >= 0 || inputData.getFileName().indexOf("..") >= 0) {
			return false;
		}

		String filePath = hiveHomedir + "/" + inputData.getTableId().toLowerCase() + "/" + inputData.getPartition() + "/" + inputData.getFileName()
								+ inputData.getExtensions();

		filePath = FilenameUtils.separatorsToUnix(filePath);
		filePath = filePath.replaceAll("[/]+", "/");

		String data = inputData.getData();
		if (data == null || data.trim().equals("")) {
			return false;
		}
		data = data.trim();

		try {
			// RENAME
			if (!inputData.isOverwrite()) {
				String tmpFilePath = filePath;
				int num = 1;
				while (hdfsService.isExistsFile(tmpFilePath)) {
					tmpFilePath = filePath + "." + num;
				}
				filePath = tmpFilePath;
			}

			// HIVE 파일 덮어쓰기
			hdfsService.writeFile(data, filePath);
			result = true;
		} catch (IOException e) {
			result = false;
		}

		return result;
	}
	
	/**
	 * @param userId
	 * @param tableId
	 * @param tmpTableId
	 * @return
	 */
	public boolean createTmpData(String tableId, String tmpTableId, String[] cols, String regexp, String replace) {
		String userId = "";
		
		String query = "";
		String column = "";

		for(String col : cols) {
			column += " regexp_replace(" + col + ", '" + regexp + "', '" + replace + "') AS " + col + ",";
		}
		
		query += "CREATE TABLE " + userId + "_" + tmpTableId + " ROW FORMAT DELIMITED ";
		query += "FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n' ";
		query += "LOCATION '/tmp/" + userId + "/" + tmpTableId + " AS ";
		query += "SELECT " + column + " FROM " + tableId + ";";
		
		//hadoop fs -getmerge /temp/storage/path/ /local/path/my.csv
		return hiveJdbcConn.execute(query);
	}

}
