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
public class GTMMU08 {

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
		String fileName = "GTMMU08";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(16, 22).trim();

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
		String cardNo = "";
		String dateIssued = "";
		String dateExp = "";
		String holderName = "";
		String cardStt = "";
		String sttName = "";
		String sqlInsert = "";
		try {
			cardNo = line.substring(16, 38).trim();
			dateIssued = line.substring(40, 51).trim();
			dateExp = line.substring(54, 66).trim();
			holderName = line.substring(69, 101).trim();
			cardStt = line.substring(104, 115).trim();
			sttName = line.substring(118, 129).trim();

			sqlInsert = insertDB(cardNo, dateIssued, dateExp, holderName,
					cardStt, sttName);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error cardNo : " + cardNo);
			System.out.println(ex.getMessage());
		}
	}

	public static String insertDB(String cardNo, String dateIssued,
			String dateExp, String holderName, String cardStt, String sttName) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMMU08 (CARDNO,	DATEISSUED,	DATEEXP,	HOLDERNAME,	CARDSTATUS,	STATUSDATE) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ dateIssued
					+ "','"
					+ dateExp
					+ "','"
					+ holderName
					+ "','"
					+ cardStt
					+ "','"
					+ sttName + "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
