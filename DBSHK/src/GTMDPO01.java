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
public class GTMDPO01 {

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
		String fileName = "GTMDPO01";
		
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
		String debtorAccountNo = "";
		String debtorRef = "";
		String customerID = "";
		String customerName = "";
		String octopus = "";
		String respCode = "";
		String exception = "";

		String sqlInsert = "";
		try {
			debtorAccountNo = line.substring(0, 16).trim();
			debtorRef = line.substring(20, 31).trim();
			customerID = line.substring(33, 49).trim();
			customerName = line.substring(52, 76).trim();
			octopus = line.substring(81, 91).trim();
			respCode = line.substring(94, 103).trim();
			exception = line.substring(106, line.length()).trim();

			sqlInsert = insertDB(debtorAccountNo, debtorRef, customerID,
					customerName, octopus, respCode, exception);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + debtorAccountNo + " - "
					+ debtorRef + customerID + customerName + octopus
					+ respCode + exception);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String debtorAccountNo, String debtorRef,
			String customerID, String customerName, String octopus,
			String respCode, String exception) {
		String sqlInsert = "";

		try {

			sqlInsert = "Insert into GTMDPO01(DEBTORACCOUNTNO,DEBTORREF,CUSTOMERID,CUSTOMERNAME,OCTOPUS,RESPCODE,EXCEPTION) "
					+ "values ('"
					+ debtorAccountNo
					+ "','"
					+ debtorRef
					+ "','"
					+ customerID
					+ "','"
					+ customerName
					+ "','"
					+ octopus
					+ "','" + respCode + "','" + exception + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
