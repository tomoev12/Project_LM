package green.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	// 한번만 new 하도록 고정 - singleton pattern
	// instance 가 한개만 만들어지도록 한다
	// Connection String
	private  static      String      driver = "oracle.jdbc.OracleDriver"; 
	private  static      String      dburl  = "jdbc:oracle:thin:@localhost:1521:xe"; 
	private  static      String      dbuid  = "hr"; 
	private  static      String      dbpwd  = "1234";
	
	private  static      Connection  conn = null;
	
	// 생성자 - private
	private  DBConn() {		
	}
	
	public  static Connection  getInstance() {
		if( conn  != null  ) {
			return  conn;
		}
		
		try {
			Class.forName(driver);
			conn  =  DriverManager.getConnection(dburl, dbuid, dbpwd); 
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return conn;
	}
	
}








