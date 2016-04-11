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
public class GTMDPO56 {

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
		String fileName = "GTMDPO56";
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
		String recordID = "";
		String octopus = "";
		String debtorRef = "";
		String accountNo = "";
		String sqlInsert = "";
		try {
			recordID = line.substring(0, 9).trim();
			octopus = line.substring(11, 19).trim();
			debtorRef = line.substring(22, 40).trim();
			accountNo = line.substring(44, 64).trim();

			sqlInsert = insertDB(recordID, octopus, debtorRef, accountNo);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + recordID + " - " + octopus
					+ debtorRef + accountNo);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String recordID, String octopus,
			String debtorRef, String accountNo) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDPO56(RECORDID,OCTOPUS,DEBTORREF,ACCOUNTNO) "
					+ "values ('"
					+ recordID
					+ "','"
					+ octopus
					+ "','"
					+ debtorRef + "','" + accountNo + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
