package util_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Util {
	public Connection connectDB() throws Exception{
		Connection conn = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:iip";
		String id = "scott";
		String pwd = "tiger";
		
		Class.forName(driver);
		conn = DriverManager.getConnection(url, id, pwd);
		
		return conn;
	}
	
	public List<String> getTableName(Connection conn){
		List<String> TN_list = new ArrayList<String>();
		String sql = "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = 'SCOTT'";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				TN_list.add(rs.getString(1));
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}finally{
			if ( rs != null) try {rs.close();}catch(Exception e) {}
			if ( stmt != null) try {stmt.close();}catch(Exception e) {}
			if ( conn != null) try {conn.close();}catch(Exception e) {} 
		}
		return TN_list;
	}
	
	public LinkedHashMap<String,String> getTableData(Connection conn, List<String> TN_list){
		List<LinkedHashMap<String, String>> FData = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> TD_hm = new LinkedHashMap<String, String>();
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			String column_name = "";
			String column_data = "";
			for(int i = 0 ; i < TN_list.size(); i++) {
				sql ="SELECT * FROM ?";
				pstmt.setString(2, TN_list.get(i));
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				int column_count = rsmd.getColumnCount();
				while(rs.next()) {
					for(int column = 0 ; column < column_count ; column++ ) {
					column_name = rsmd.getColumnName(column);
					column_data = rs.getString(column_name);
					TD_hm.put(column_name, column_data);
					}
					FData.add(TD_hm);
				}
				
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if( rs != null) try {rs.close();}catch(Exception e) {}
			if( pstmt != null) try {pstmt.close();}catch(Exception e){}
			if( conn != null) try {conn.close();}catch(Exception e) {}
		}
		
		
		return TD_hm; 
	}
}
