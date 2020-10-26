package bigdata.portal.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.hive.jdbc.HiveConnection;
import org.apache.hive.jdbc.HivePreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bigdata.portal.entity.EntityMap;

@Service
public class HiveJdbcConn {
	private static final Logger LOGGER = LoggerFactory.getLogger(HiveJdbcConn.class);

	@Value("${hive.driverClassName}") String driverClassName;
	@Value("${hive.url}") String url;
	// hive 메타정보 데이터 갯수 조회
	@Value("${hive.url1}") String url1;
	@Value("${hive.interative}") String interative;
	@Value("${hive.username}") String username;
	@Value("${hive.password}") String password;

	@Value("${hive.user.homedir}") String hiveHomedir;
	@Value("${hive.tmpdir}") String hiveTmpdir;

	public HiveJdbcConn() {

	}

	public Connection getConn() throws SQLException {

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver Not Found!");
		}

		//System.out.println(username + ", " + password);
		return DriverManager.getConnection(url1, username, password);

		/*
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver Not Found!");
		}
		

		//System.out.println(username + ", " + password);
		return DriverManager.getConnection(url2, username, password);
		*/
		
	}
	
	public Connection getConn1() throws SQLException {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver Not Found!");
		}

		//System.out.println(username + ", " + password);
		return DriverManager.getConnection(url1, username, password);
	}
	
	public Connection getConn2() throws SQLException {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver Not Found!");
		}

		//System.out.println(username + ", " + password);
		return DriverManager.getConnection(url1, username, password);
	}

	public void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("There was an unsupported operation error");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getSQLState());
				} catch (Exception e) {
					LOGGER.error(e.getLocalizedMessage());
				}
			}
		}
	}

	public void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("There was an unsupported operation error");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error(e.getSQLState());
				} catch (Exception e) {
					LOGGER.error(e.getLocalizedMessage());
				}
			}
		}
		close(conn);
	}

	public void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("There was an unsupported operation error");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LOGGER.error(e.getSQLState());
				} catch (Exception e) {
					LOGGER.error("There was an unsupported operation error");
				}
			}
		}
		close(stmt, conn);
	}

	/**
	 * 컬럼, 테이블명 등에 허용되는 문자 제외 모두 삭제
	 * 
	 * @param str
	 * @return
	 */
	public String cleanSql(String str) {
		// TODO : 쿼리 정리
		String strClean = str;
		strClean = strClean.replaceAll("[^A-Za-z0-9_]", "");
		return strClean;
	}
	
	/**
	 * 컬럼, 테이블명 등에 허용되는 문자 제외 모두 삭제(한글 허용)
	 * @param str
	 * @param kor
	 * @return
	 */
	public String cleanSql(String str, boolean kor) {
		// TODO : 쿼리 정리
		String strClean = str;
		if(kor == true) {
			strClean = strClean.replaceAll("[^A-Za-z0-9가-힣_]", "");
		} else {
			strClean = strClean.replaceAll("[^A-Za-z0-9_]", "");
		}		
		return strClean;
	}		

	/**
	 * 컬럼, 테이블명 등에 허용되는 문자 제외 모두 삭제
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public String cleanSqlJoin(String str, String separator) {
		// TODO : 쿼리 정리
		String strClean = "";
		strClean = str.replaceAll("[^A-Za-z0-9_]", "");

		String rs = "";
		if (!strClean.trim().equals("")) {
			String[] strArr = strClean.split(",");
			for (String s : strArr) {
				rs += ", " + cleanSql(s.trim());
			}
			return rs.substring(1);
		}
		return rs;
	}

	/**
	 * 쿼리 ESCAPE
	 * 
	 * @param str
	 * @return
	 */
	public String escape(String str) {
		return "'" + StringEscapeUtils.escapeSql(str) + "'";
	}

	/**
	 * 쿼리 ESCAPE
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public String escapeJoin(String str, String separator) {
		String rs = "";
		if (!str.trim().equals("")) {
			String[] strArr = str.split(",");
			for (String s : strArr) {
				rs += ",'" + StringEscapeUtils.escapeSql(s.trim()) + "'";
			}
			return rs.substring(1);
		}
		return rs;
	}

	/**
	 * ResultSet to List<HashMap>
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<EntityMap> convertMap(ResultSet rs) throws SQLException {
		List<EntityMap> list = new ArrayList<EntityMap>();

		if (rs != null) {
			ResultSetMetaData rsMeta = rs.getMetaData();
			if (rsMeta != null) {
				String[][] strArr = new String[rsMeta.getColumnCount()][2];
				for (int i = 0; i < rsMeta.getColumnCount(); i++) {
					String col = rsMeta.getColumnName(i + 1);
					String type = rsMeta.getColumnTypeName(i + 1);
					col = col.substring(col.indexOf(".") + 1);
					strArr[i][0] = col;
					strArr[i][1] = type;
				}

				while (rs.next()) {
					EntityMap hash = new EntityMap();
					for (String[] col : strArr) {
						switch (col[1]) {
						case "timestamp":
						case "string":
						case "varchar":
						case "char":
							hash.put(col[0], rs.getString(col[0]));
							break;
						case "int":
						case "integer":
							hash.put(col[0], rs.getInt(col[0]));
							break;
						case "double":
							hash.put(col[0], rs.getDouble(col[0]));
							break;
						case "float":
							hash.put(col[0], rs.getFloat(col[0]));
							break;
						default:
							hash.put(col[0], rs.getObject(col[0]));
						}
					}
					list.add(hash);
				}
			}
		}
		return list;
	}

	/**
	 * ResultSet to List<String[]>
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<Object[]> convertArray(ResultSet rs, boolean incHeader) throws SQLException {
		List<Object[]> list = new ArrayList<Object[]>();

		ResultSetMetaData rsMeta = rs.getMetaData();
		if(incHeader) {
			String[] strArr = new String[rsMeta.getColumnCount()];
			for (int i = 0; i < rsMeta.getColumnCount(); i++) {
				String col = rsMeta.getColumnName(i + 1);
				strArr[i] = col.toUpperCase();
			}
			list.add(strArr);
		}
		
		while (rs.next()) {
			Object[] strArr = new Object[rsMeta.getColumnCount()];
			for (int i = 0; i < rsMeta.getColumnCount(); i++) {
				strArr[i] = rs.getObject(i + 1);
			}
			list.add(strArr);
		}
		return list;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean execute(String sql, List<Object> params) {
		HiveConnection conn = null;
		HivePreparedStatement stmt = null;
		boolean rs = true;

		try {
			conn = (HiveConnection) getConn();
			stmt = (HivePreparedStatement) conn.prepareStatement(sql);

			int i = 0;
			for (Object object : params) {
				stmt.setObject(++i, object);
			}
			rs = stmt.execute();
		} catch (SQLException e) {
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			LOGGER.error("There was an unsupported sql operation error");
		} finally {
			close(stmt, conn);
		}

		return rs;
	}

	/**
	 * 쿼리실행 CREATE, INSERT, UPDATE, DELETE, LOAD
	 * 
	 * @param sql
	 * @return
	 */
	public boolean execute(String sql) {
		LOGGER.debug(sql);

		Connection conn = null;
		Statement stmt = null;
		boolean rs = true;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			LOGGER.error(e.getSQLState());
			rs = false;
		} catch (Exception e) {
			LOGGER.error("There was an unsupported operation error");
		} finally {
			close(stmt, conn);
		}
		return rs;
	}

	/**
	 * 쿼리 실행 후 결과 Map으로 리턴
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<EntityMap> executeQueryReturnMap(String sql, List<Object> params) {
		LOGGER.debug(sql);

		HiveConnection conn = null;
		HivePreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = (HiveConnection) getConn();
			stmt = (HivePreparedStatement) conn.prepareStatement(sql);
			if(params != null) {
				int i = 0;
				for (Object object : params) {
					stmt.setObject(++i, object);
				}
			}
			rs = stmt.executeQuery();

			return convertMap(rs);
		} catch (SQLException e) {
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			LOGGER.error("There was an unsupported sql operation error");
		} finally {
			close(rs, stmt, conn);
		}

		return null;
	}

	/**
	 * 쿼리 실행 후 결과 Map으로 리턴
	 * 
	 * @param sql
	 * @return
	 */
	public List<EntityMap> executeQueryReturnMap(String sql) {
		return executeQueryReturnMap(sql, null);
	}

	/**
	 * 쿼리 실행 후 결과 Array로 리턴
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> executeQueryReturnArray(String sql, List<Object> params, boolean incHeader) {
		LOGGER.debug(sql);

		HiveConnection conn = null;
		HivePreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = (HiveConnection) getConn();
			stmt = (HivePreparedStatement) conn.prepareStatement(sql);

			if(params != null) {
				int i = 0;
				for (Object object : params) {
					stmt.setObject(++i, object);
				}
			}

			rs = stmt.executeQuery();

			return convertArray(rs, incHeader);

		} catch (SQLException e) {
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			LOGGER.error("There was an unsupported sql operation error");
		} finally {
			close(rs, stmt, conn);
		}

		return null;
	}

	/**
	 * 쿼리 실행 후 결과 Array로 리턴
	 * 
	 * @param sql
	 * @return
	 */
	public List<Object[]> executeQueryReturnArray(String sql, boolean incHeader) {
		return executeQueryReturnArray(sql, null, incHeader);
	}

	/**
	 * 테이블 생성
	 * 
	 * @param hiveTable
	 * @return
	 */
	public boolean createTable(HiveTable hiveTable) {
		String sql = "";
		String columns = "";
		for (HiveColumn column : hiveTable.getColumns()) {
			columns += ", " + cleanSql(column.getName()) + " " + column.getType();
		}
		columns = columns.substring(1);

		if (hiveTable.getTableLocation() != null && !hiveTable.getTableLocation().equals("")) {
			sql += "CREATE EXTERNAL TABLE IF NOT EXISTS " + hiveTable.getTableId() + " \n";
			sql += "LOCATION " + escape(hiveTable.getTableLocation()) + " \n";
		} else {
			sql += "CREATE TABLE IF NOT EXISTS " + hiveTable.getTableId() + " \n";
		}
		sql += "(" + columns + ") \n";
		sql += "ROW FORMAT DELIMITED \n";
		sql += "    FIELDS TERMINATED BY \",\" \n";
		sql += "    LINES TERMINATED BY \"\\n\" \n";

		return execute(sql);
	}

	/**
	 * 테이블 생성 (CSV포멧 사용)
	 * 
	 * @param hiveTable
	 * @return
	 */
	public boolean createTableCSV(HiveTable hiveTable) {
		String sql = "";
		String columns = "";
		for (HiveColumn column : hiveTable.getColumns()) {
			columns += ", " + cleanSql(column.getName()) + " " + column.getType();
		}
		columns = columns.substring(1);

		if (hiveTable.getTableLocation() != null && !hiveTable.getTableLocation().equals("")) {
			sql += "CREATE EXTERNAL TABLE IF NOT EXISTS " + hiveTable.getTableId() + " \n";
			sql += "LOCATION " + escape(hiveTable.getTableLocation()) + " \n";
		} else {
			sql += "CREATE TABLE IF NOT EXISTS " + hiveTable.getTableId() + " \n";
		}
		sql += "(" + columns + ") \n";
		sql += "ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde' \n";
		sql += "WITH SERDEPROPERTIES ( \n";
		sql += "	\"separatorChar\" = \",\", \n";
		sql += "	\"quoteChar\" = \"'\", \n";
		sql += "	\"escapeChar\" = \"\\\\\" \n";
		sql += ") \n";

		return execute(sql);
	}

	/**
	 * 인덱스 생성
	 * @param tableId
	 * @param column
	 * @return
	 */
	public boolean createIndex(String tableId, String column) {
		String sql = "";
		String tableIdClean = cleanSql(tableId);
		String columnClean = cleanSql(column);
		// IF NOT EXISTS
		sql += "CREATE INDEX " + columnClean + "_IDX ON TABLE " + tableIdClean + "\n";
		sql += "(" + columnClean + ") AS 'COMPACT' WITH DEFERRED REBUILD";

		return execute(sql);
	}

	/**
	 * 테이블 삭제
	 * @param tableId
	 * @return
	 */
	public boolean dropTable(String tableId) {
		String tableIdClean = cleanSql(tableId);
		String sql = "DROP TABLE IF EXISTS " + tableIdClean;

		return execute(sql);
	}

	/**
	 * 인덱스 삭제
	 * @param tableId
	 * @param column
	 * @return
	 */
	public boolean dropIndex(String tableId, String column) {
		String tableIdClean = cleanSql(tableId);
		String columnClean = cleanSql(column);
		String sql = "DROP INDEX IF EXISTS " + columnClean + "_IDX ON " + tableIdClean;

		return execute(sql);
	}

	/**
	 * 테이블 비우기
	 * @param tableId
	 * @return
	 */
	public boolean truncateTable(String tableId) {
		String tableIdClean = cleanSql(tableId);
		String sql = "TRUNCATE  " + tableIdClean;

		return execute(sql);
	}

	/**
	 * 테이블 목록
	 * @return
	 */
	public List<EntityMap> showTables() {
		String sql = "SHOW TABLES";
		// return tab_name
		return executeQueryReturnMap(sql);
	}

	/**
	 * 테이블 정보
	 * 
	 * @param tableId
	 * @return
	 */
	public Map<String, String[]> descTable(String tableId) {
		Map<String, String[]> meta = new LinkedHashMap<String, String[]>();
		String tableIdClean = cleanSql(tableId);
		String sql = "DESC " + tableIdClean;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = (Connection) getConn();
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// return col_name, data_type, comment
			while (rs.next()) {
				 // col_name
				String colName = rs.getString(1).trim().toUpperCase();
				
				String[] col = {
					colName,
					rs.getString(2), // data_type
					rs.getString(3) // comment
				};
				meta.put(colName, col);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			LOGGER.error("There was an unsupported sql operation error");
		} finally {
			close(rs, stmt, conn);
		}
		return meta;
	}
	
	/**
	 * HIVE 데이터 SELECT
	 * 
	 * @param query
	 * @return
	 */
	public List<EntityMap> selectQueryIncludeMeta(String query) {
		return executeQueryReturnMap(query);
	}
	
	/**
	 * HIVE 데이터 SELECT
	 * 
	 * @param query
	 * @return
	 */
	public List<EntityMap> selectQuery(String query) {
		return executeQueryReturnMap(query);
	}

	public boolean loadFile(String tableId, String filePath, boolean overwrite) {
		String sql = "";
		sql += "LOAD DATA INPATH " + escape(filePath);
		if (overwrite) {
			sql += " OVERWRITE ";
		}
		sql += "INTO TABLE " + tableId;

		return execute(sql);
	}
	
	
	/**
	 * properties count 조회
	 * 
	 * @param tableId
	 * @return
	 */
	public Map<String, String> showPropertiesTable(String tableId) {
		Map<String, String> meta = new HashMap<String, String>();
		String tableIdClean = "" + cleanSql(tableId);
		String sql = "SHOW TBLPROPERTIES " + tableIdClean;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = (Connection) getConn1();
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(sql);
				
			String colName ="";
			String colVal = "";
			while (rs.next()) {
				
				colName = rs.getString(1).trim();
				LOGGER.debug("colName = " + colName);
				
				if(colName.equals("numRows")) {
					colVal = rs.getString(2);
					LOGGER.debug("colVal = " + colVal);
				}
				meta.put(colName, colVal);
				
			}
			if(colVal.equals("")) {
				meta.put("numRows", "0");
			}
			
		} catch (SQLException e) {
			LOGGER.error(e.getSQLState());
		} catch (Exception e) {
			LOGGER.error("There was an unsupported sql operation error");
		} finally {
			close(rs, stmt, conn);
		}
		
		LOGGER.debug(meta.toString());
		
		return meta;
	}
}
