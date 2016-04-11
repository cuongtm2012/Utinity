import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
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
public class GTMDU06 {

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
		String fileName = "GTMDU06";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(36, 48).trim();
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
		String pbsNoStatus = "";
		String tapeSeqNo = "";
		String cardNumber = "";
		String cardHolderName = "";
		String remarks = "";
		String preGen = "";
		String sqlInsert = "";
		try {
			pbsNoStatus = line.substring(0, 18).trim();
			tapeSeqNo = line.substring(21, 32).trim();
			cardNumber = line.substring(36, 48).trim();
			cardHolderName = line.substring(52, 76).trim();
			remarks = line.substring(79, 99).trim();
			preGen = line.substring(101, line.length()).trim();

			sqlInsert = insertDB(pbsNoStatus, tapeSeqNo, cardNumber,
					cardHolderName, remarks, preGen);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error PBS NO STATUS : " + pbsNoStatus);
			System.out.println(ex.getMessage());
		}
	}

	public static String insertDB(String pbsNoStatus, String tapeSeqNo,
			String cardNumber, String cardHolderName, String remarks,
			String preGen) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDU06 (NOSTATUS,	TAPESEQNO,	CARDNUMBER,	CARDHOLDERNAME,	REMARKS,	PREGENERATED) "
					+ "values ('"
					+ pbsNoStatus
					+ "','"
					+ tapeSeqNo
					+ "','"
					+ cardNumber
					+ "','"
					+ cardHolderName
					+ "','"
					+ remarks
					+ "','" + preGen + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
