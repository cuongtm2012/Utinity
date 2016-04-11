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
public class GTMDP35 {

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
		String accNo = null;
		long accNum = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP35";

		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(0, 4).trim();

					try {
						accNum = Long.parseLong(accNo);
						if (accNum >= 0) {
							analysis(statement, line);
						}
					} catch (NumberFormatException ex) {
						accNum = -1;
					}

				}
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Insert DB successfull");
	}

	public static void analysis(Statement statement, String line) {
		String bankCode = "";
		String accNumber = "";
		String accType = "";
		String cardNumber = "";
		String cardType = "";
		String ourDate = "";
		String ourTime = "";
		String jetcoSeqNo = "";
		String tranCode = "";
		String tranDesc = "";
		String amount = "";
		String feeAccNumber = "";
		String rejCode = "";

		String sqlInsert = "";
		try {
			bankCode = line.substring(0, 4).trim();
			accNumber = line.substring(5, 24).trim();
			accType = line.substring(25, 27).trim();
			cardNumber = line.substring(28, 47).trim();
			cardType = line.substring(48, 50).trim();
			ourDate = line.substring(51, 59).trim();
			ourTime = line.substring(60, 68).trim();
			jetcoSeqNo = line.substring(69, 75).trim();
			tranCode = line.substring(76, 79).trim();
			tranDesc = line.substring(81, 89).trim();
			amount = line.substring(94, 106).trim();
			feeAccNumber = line.substring(107, 126).trim();
			rejCode = line.substring(127, 131).trim();

			sqlInsert = insertDB(bankCode, accNumber, accType, cardNumber,
					cardType, ourDate, ourTime, jetcoSeqNo, tranCode, tranDesc,
					amount, feeAccNumber, rejCode);
			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println("Error CardNo : " + bankCode);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String bankCode, String accNumber,
			String accType, String cardNumber, String cardType, String ourDate,
			String ourTime, String jetcoSeqNo, String tranCode,
			String tranDesc, String amount, String feeAccNumber, String rejCode) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP35(BANKCODE, 	ACCOUNTNUMBER, 	TYPEACC, 	CARDNUMBER, 	TYPECARD, 	OURDATE, 	OURTIME, 	JETCOSEQNO, 	TRANCODE, 	TRANDESC, 	AMOUNT, 	TRANFEEACC, 	ERRORCODE) "
					+ "values ('"
					+ bankCode
					+ "','"
					+ accNumber
					+ "','"
					+ accType
					+ "','"
					+ cardNumber
					+ "','"
					+ cardType
					+ "','"
					+ ourDate
					+ "','"
					+ ourTime
					+ "','"
					+ jetcoSeqNo
					+ "','"
					+ tranCode
					+ "','"
					+ tranDesc
					+ "','"
					+ amount
					+ "','"
					+ feeAccNumber
					+ "','"
					+ rejCode + "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
