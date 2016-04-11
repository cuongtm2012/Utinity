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
public class GTMDPO02 {

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
		long cardno = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDPO02";
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 16).trim();

					try {
						cardno = Long.parseLong(cardNo);
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
		String debtorAccountNo = "";
		String applicantName = "";
		String debtorRef = "";
		String octopus = "";
		String tranSeq = "";
		String tranDate = "";
		String tranAmount = "";

		String sqlInsert = "";
		try {
			debtorAccountNo = line.substring(0, 16).trim();
			applicantName = line.substring(18, 42).trim();
			debtorRef = line.substring(44, 54).trim();
			octopus = line.substring(57, 65).trim();
			tranSeq = line.substring(67, 73).trim();
			tranDate = line.substring(77, 85).trim();
			tranAmount = line.substring(87, line.length()).trim();

			sqlInsert = insertDB(debtorAccountNo, applicantName, debtorRef,
					octopus, tranSeq, tranDate, tranAmount);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + debtorAccountNo + " - "
					+ applicantName + debtorRef + octopus + tranSeq + tranDate
					+ tranAmount);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String debtorAccountNo, String applicantName,
			String debtorRef, String octopus, String tranSeq, String tranDate,
			String tranAmount) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDPO02(DEBTORACCOUNTNO,APPLICANTNAME,DEBTORREF,OCTOPUS,TRANSEQ,TRANDATE,TRANAMOUNT) "
					+ "values ('"
					+ debtorAccountNo
					+ "','"
					+ applicantName
					+ "','"
					+ debtorRef
					+ "','"
					+ octopus
					+ "','"
					+ tranSeq
					+ "','" + tranDate + "','" + tranAmount + "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
