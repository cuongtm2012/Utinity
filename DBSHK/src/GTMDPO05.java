import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import util.helpConfiguration;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDPO05 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		Properties prop = new Properties();
		InputStream inputPath = null;
		String line = "";
		String cardNo = null;
		Integer cardno = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDPO05";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 9).trim();

					try {
						cardno = Integer.valueOf(cardNo);
						if (cardno >= 0) {
							analysis(statement, line);
						}
					} catch (NumberFormatException ex) {
						cardno = -1;
					}
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Insert DB successfull");
	}

	public static void analysis(Statement statement, String line) {
		String recordID = "";
		String reportTime = "";
		String octopus = "";
		String debtorRef = "";
		String accountNo = "";
		String status = "";

		String sqlInsert = "";
		try {
			recordID = line.substring(0, 9).trim();
			reportTime = line.substring(11, 25).trim();
			octopus = line.substring(28, 36).trim();
			debtorRef = line.substring(39, 57).trim();
			accountNo = line.substring(61, 81).trim();
			status = line.substring(84, 113).trim();

			sqlInsert = insertDB(recordID, reportTime, octopus, accountNo,
					status);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + recordID + " - "
					+ reportTime + octopus + accountNo + status);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String recordID, String reportTime,
			String octopus, String accountNo, String status) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDPO05(RECORDID,REPORTTIME,OCTOPUS,ACCOUNTNO,STATUS) "
					+ "values ('"
					+ recordID
					+ "','"
					+ reportTime
					+ "','"
					+ octopus + "','" + accountNo + "','" + status + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
