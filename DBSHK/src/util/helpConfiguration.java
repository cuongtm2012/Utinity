package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class helpConfiguration {

	public static Connection getDBConnection() {

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

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static boolean isValidDateYear(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
}
