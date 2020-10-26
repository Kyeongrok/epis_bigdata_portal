import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhoenixConnectTest {

	private Statement stmt = null;
	private ResultSet rset = null;
	private Connection conn = null;
	private PreparedStatement statement = null;
	
	public void connectToThick() throws SQLException, ClassNotFoundException {
		
		System.out.println("====== connect to thick server ======");
		
		Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");		
		
		// hbase 이름으로 접근하기 위한 설정
		System.setProperty("HADOOP_USER_NAME", "hbase");	
		
		String driverConnectionUrl = "jdbc:phoenix:datanode001.theimc.co.kr:2181:/hbase-unsecure";

		conn = DriverManager.getConnection(driverConnectionUrl);
		stmt = conn.createStatement();
		
		statement = conn.prepareStatement("SELECT * FROM STOCK_SYMBOL");
		rset = statement.executeQuery();
		
		while(rset.next()) {
			System.out.println(rset.getString("SYMBOL") + " " + rset.getString("COMPANY"));
		}
		
		disconnect();
		
		System.out.println("====== disconnect to thick server ======");

		
	}

	public void connectToThin() throws SQLException, ClassNotFoundException {
		
		System.out.println("====== connect to thin server ======");
		
		Class.forName("org.apache.phoenix.queryserver.client.Driver");
		String driverConnectionUrl = "jdbc:phoenix:thin:url=http://118.33.99.62:8765;serialization=PROTOBUF";		


		conn = DriverManager.getConnection(driverConnectionUrl);
		stmt = conn.createStatement();
		

		statement = conn.prepareStatement("SELECT * FROM STOCK_SYMBOL");
		rset = statement.executeQuery();
		
		while(rset.next()) {
			System.out.println(rset.getString("SYMBOL") + " " + rset.getString("COMPANY"));
		}
		
		disconnect();
		
		System.out.println("====== disconnect to thin server ======");
		
	}

	private void disconnect() throws SQLException {
		
		statement.close();
		rset.close();
		conn.close();
		stmt.close();
	}

	public static void main(String[] args) {
		PhoenixConnectTest hct = new PhoenixConnectTest();

		try {
			hct.connectToThick();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {		
			hct.connectToThin();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		

	}

}
