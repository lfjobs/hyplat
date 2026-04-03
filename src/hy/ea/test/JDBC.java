package hy.ea.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	private static final String URL = "jdbc:oracle:thin:@192.168.3.9:1521:ttsw";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String USERNAME = "hyplat";
	private static final String PASSWORD = "hyplatttsw";
	private Connection conn = null;
	//<!-- 测试：123.57.26.228 -->
	//<!--本地: 192.168.3.9 -->
	public JDBC(){
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//System.out.println("连接数据库成功。。。。。。。。");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection(){
		
		return this.conn;
	}
	
	public void destroy(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
