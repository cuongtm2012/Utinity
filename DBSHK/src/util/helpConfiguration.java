package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class helpConfiguration {

	public Connection getDBConnection() {

		Connection dbConnection = null;
		String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		String DB_CONNECTION = "jdbc:oracle:thin:@10.74.252.201:1521:lcpldev";
		String DB_USER = "swrpt_app";
		String DB_PASSWORD = "SWRPT_APP123";
	
		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                               DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}
