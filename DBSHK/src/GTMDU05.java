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
public class GTMDU05 {

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
		String fileName = "GTMDU05";
		
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
		String name = "";
		String accountNo = "";
		String address = "";
		String sqlInsert = "";
		try {
			name = line.substring(2, 12).trim();
			accountNo = line.substring(2, 12).trim();
			address = line.substring(2, 12).trim();

			sqlInsert = insertDB(name, accountNo, address);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error name : " + name);
			System.out.println(ex.getMessage());
		}
	}

	public static String insertDB(String name, String accountNo, String address) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDU05 (NAME,	ACCOUNTNO,	ADDRESS) "
					+ "values ('" + name + "','" + accountNo + "','" + address
					+ "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
