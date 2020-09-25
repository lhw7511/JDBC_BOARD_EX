package com.board.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

	public Connection getConnect() throws Exception {
		String user = "id";
		String password = "password";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		String driver = "oracle.jdbc.driver.OracleDriver";

		Connection con = null;

		Class.forName(driver);
		//System.out.println("드라이버 로딩 성공!");

		con = DriverManager.getConnection(url, user, password);
		//System.out.println("DB 연결성공!");

		return con;

	}
}
