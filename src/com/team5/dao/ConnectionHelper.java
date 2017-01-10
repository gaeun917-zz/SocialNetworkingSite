package com.team5.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionHelper {
	
	public static Connection getConnection(String dsn) {
		
		Connection conn = null;
		if (dsn.equals("oracle")) {
			try {
				//1. 직접 Connection 객체 생성
//				Class.forName("oracle.jdbc.OracleDriver");				
//				conn = 
//					DriverManager.getConnection(
//					"jdbc:oracle:thin:@localhost:1521:xe", "demoweb", "oracle");
				//2. Connection Pool에서 연결 객체 반환
				Context initContext = new InitialContext();
				Context envContext  = 
					(Context)initContext.lookup("java:/comp/env");
				DataSource ds = 
					(DataSource)envContext.lookup("jdbc/team5");
				conn = ds.getConnection();
				
			} catch (Exception ex) {				
			}
		} else if (dsn.equals("mysql")) {
			try {
				//직접 연결 객체 관리
	//			//1. 드라이버 로딩
	//			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	//			Class.forName("com.mysql.jdbc.Driver");//			
	//			//2. 연결 생성 
	//			conn = DriverManager.getConnection(
	//				"jdbc:mysql://172.16.2.1:3306/demoweb",//-->서버/DB 정보 
	//				"devadmin", "mysql");//계정 정보
				
				
				
			} catch (Exception ex) {
			}
		}
			
		return conn;
		
	}

}
