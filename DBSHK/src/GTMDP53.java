import java.io.BufferedReader;
import java.io.File;
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
public class GTMDP53 {

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
		String fileName = "GTMDP53";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 6).trim();

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
		String cardNo = "";
		String type = "";
		String seqNo = "";
		String inpOpr = "";
		String inpTerm = "";
		String impTime = "";
		String appOpr = "";
		String appTerm = "";
		String appTime = "";
		String name = "";
		String action = "";

		String sqlInsert = "";
		try {
			cardNo = line.substring(0, 17).trim();
			type = line.substring(21, 25).trim();
			seqNo = line.substring(29, 35).trim();
			inpOpr = line.substring(37, 41).trim();
			inpTerm = line.substring(43, 47).trim();
			impTime = line.substring(48, 56).trim();
			appOpr = line.substring(59, 63).trim();
			appTerm = line.substring(65, 69).trim();
			appTime = line.substring(70, 78).trim();
			name = line.substring(82, 104).trim();
			action = line.substring(110, 125).trim();

			sqlInsert = insertDB(cardNo, type, seqNo, inpOpr, inpTerm, impTime,
					appOpr, appTerm, appTime, name, action);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + cardNo);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNo, String type, String seqNo,
			String inpOpr, String inpTerm, String impTime, String appOpr,
			String appTerm, String appTime, String name, String action) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP53 (CARDNO,	TYPE,	SEQNO,	INPOPR,	INPTERM,	APPOPR,	APPTERM,	NAME,	ACTION) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ type
					+ "','"
					+ seqNo
					+ "','"
					+ inpOpr
					+ "','"
					+ inpTerm
					+ "','"
					+ appOpr
					+ "','"
					+ appTerm + "','" + name + "','" + action + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
