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
public class GTMDPO07 {

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
		String fileName = "GTMDPO07";
		
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
		String octopus = "";
		String txnSeq = "";
		String tranDate = "";
		String tranAmt = "";

		String sqlInsert = "";
		try {
			octopus = line.substring(0, 9).trim();
			txnSeq = line.substring(12, 19).trim();
			tranDate = line.substring(21, 30).trim();
			tranAmt = line.substring(33, 41).trim();

			sqlInsert = insertDB(octopus, txnSeq, tranDate, tranAmt);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + octopus + " - " + txnSeq
					+ tranDate);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String octopus, String txnseq,
			String trandate, String tranamt) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDPO07(OCTOPUS,TXNSEQ,TRANDATE,TRANAMT) "
					+ "values ('"
					+ octopus
					+ "','"
					+ txnseq
					+ "','"
					+ trandate
					+ "','" + tranamt + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
