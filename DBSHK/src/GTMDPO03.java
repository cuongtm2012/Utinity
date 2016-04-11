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
public class GTMDPO03 {

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
		String fileName = "GTMDPO03";
		
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
		String octopus = "";
		String accountNo = "";
		String refundIndicator = "";
		String amount = "";
		String lastTranTime = "";
		String sqlInsert = "";
		try {
			octopus = line.substring(0, 8).trim();
			accountNo = line.substring(11, 27).trim();
			refundIndicator = line.substring(29, 38).trim();
			amount = line.substring(41, 51).trim();
			lastTranTime = line.substring(55, 69).trim();

			sqlInsert = insertDB(octopus, accountNo, refundIndicator, amount,
					lastTranTime);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + octopus + " - " + accountNo
					+ refundIndicator + amount + lastTranTime);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String octopus, String accountNo,
			String refundIndicator, String amount, String lastTranTime) {

		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDPO03(OCTOPUS,ACCOUNTNO,REFUNDINDICATOR,AMOUNT,LASTTRANTIME) "
					+ "values ('"
					+ octopus
					+ "','"
					+ accountNo
					+ "','"
					+ refundIndicator
					+ "','"
					+ amount
					+ "','"
					+ lastTranTime
					+ "')";

			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
