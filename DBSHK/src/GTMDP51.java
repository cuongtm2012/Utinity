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
public class GTMDP51 {

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
		String fileName = "GTMDP51";
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
		String cardNo = "";
		String type = "";
		String transaction = "";
		String accountNum = "";
		String remarks = "";
		String statusChange = "";
		String newStatus = "";
		String octopus = "";

		String sqlInsert = "";
		try {
			cardNo = line.substring(0, 16).trim();
			type = line.substring(16, 20).trim();
			transaction = line.substring(21, 48).trim();
			accountNum = line.substring(49, 72).trim();
			remarks = line.substring(73, 98).trim();
			octopus = line.substring(120, 130).trim();

			sqlInsert = insertDB(cardNo, type, transaction, accountNum,
					remarks, statusChange, newStatus, octopus);

			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println("Error CardNo" + cardNo + " - " + type
					+ transaction + accountNum + remarks + octopus);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNo, String type,
			String transaction, String accountNum, String remarks,
			String statusChange, String newStatus, String octopus) {
		statusChange = "";
		newStatus = "";
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP51 (CARDNO,TYPE,TRANSACTION,ACCOUNTNUMBER,REMARKS,STATUSCHANGE,NEWSTATUS,OCTOPUS) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ type
					+ "','"
					+ transaction
					+ "','"
					+ accountNum
					+ "','"
					+ remarks
					+ "','"
					+ statusChange + "','" + newStatus + "','" + octopus + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
