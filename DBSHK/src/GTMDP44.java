import java.io.BufferedReader;
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
public class GTMDP44 {

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
		String accNo = null;
		long accNum = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP44";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(1, 20).trim();

					try {
						accNum = Long.parseLong(accNo);
						if (accNum >= 0) {
							analysis(line);
						}
					} catch (NumberFormatException ex) {
						accNum = -1;
					}

				}

			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Insert DB successfull");
	}

	public static void analysis(String line) {
		String cardNumber = "";
		String accNumber = "";
		String debitAmt = "";
		String remarks = "";

		try {
			cardNumber = line.substring(1, 20).trim();
			accNumber = line.substring(30, 50).trim();
			debitAmt = line.substring(64, 80).trim();
			remarks = line.substring(80, 100).trim();

			insertDB(cardNumber, accNumber, debitAmt, remarks);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + cardNumber);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String cardNumber, String accNumber,
			String debitAmt, String remarks) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP44(CARDNUMBER,	ACCNUMBER,	DEBITAMOUNT,	REMARKS) "
				+ "values ('"
				+ cardNumber
				+ "','"
				+ accNumber
				+ "','"
				+ debitAmt + "','" + remarks + "')";
		try {
			dbConnection = help.getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(sqlInsert);

			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
