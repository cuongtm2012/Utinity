import java.io.BufferedReader;
import java.io.FileInputStream;
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
public class GTMDP71 {

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
		String fileName = "GTMDP71";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(36, 53).trim();

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
		String authCode   =    "";
		String date71   =    "";
		String time71   =    "";
		String source   =    "";
		String account   =    "";
		String txCode   =    "";
		String sttAmt   =    "";
		String traceNo   =    "";
		String respCode   =    "";
		String remark   =    "";

		String sqlInsert = "";
		try {
			authCode = line.substring(0, 8).trim();
			date71 = line.substring(8, 18).trim();
			time71 = line.substring(18, 28).trim();
			source = line.substring(28, 36).trim();
			account = line.substring(36, 53).trim();
			txCode = line.substring(53, 60).trim();
			sttAmt = line.substring(60, 73).trim();
			traceNo = line.substring(73, 81).trim();
			respCode = line.substring(81, 88).trim();
			remark = line.substring(88, line.length()).trim();

			sqlInsert = insertDB(authCode, date71, time71, source, account, txCode, sttAmt, traceNo, respCode, remark);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String insertDB(String authCode, 	String date71, 	String time71, 	String source, 	String account, 	
			String txCode, 	String sttAmt, 	String traceNo, 	String respCode, 	String remark) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP71(AUTHCODE,	DATE71,	TIME71,	SOURCE,	ACCNUM,	TXCODE,	STTACCOUNT,	TRACENO,	RESPCODE,	REMARKS) "
					+ "values ('"
					+ authCode
					+ "','"
					+ date71
					+ "','"
					+ time71
					+ "','"
					+ source
					+ "','"
					+ account
					+ "','"
					+ txCode
					+ "','" + sttAmt + "','" + traceNo + "','" + respCode + "','" + remark + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
