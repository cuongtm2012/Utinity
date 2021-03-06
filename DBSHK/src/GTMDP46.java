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
public class GTMDP46 {

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
		String fileName = "GTMDP46";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(20, 26).trim();

					try {
						accNum = Long.parseLong(accNo);
					} catch (NumberFormatException ex) {
						accNum = -1;
					}

				}
				if (line != null && !line.isEmpty() && accNum >= 0) {
					analysis(line);

				}
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Insert DB successfull");
	}

	public static void analysis(String line) {
		String tranCode = "";
		String type = "";
		String cardVol = "";
		String cardRate = "";
		String cardAmt = "";
		String origVol = "";
		String origRate = "";
		String origAmt = "";
		String netDebit = "";
		String amtDebit = "";
		String jetcoCharge = "";

		try {
			tranCode = line.substring(2, 12).trim();
			type = line.substring(2, 12).trim();
			cardVol = line.substring(2, 12).trim();
			cardRate = line.substring(2, 12).trim();
			cardAmt = line.substring(2, 12).trim();
			origVol = line.substring(2, 12).trim();
			origRate = line.substring(2, 12).trim();
			origAmt = line.substring(2, 12).trim();
			netDebit = line.substring(2, 12).trim();
			amtDebit = line.substring(2, 12).trim();
			jetcoCharge = line.substring(2, 12).trim();

			insertDB(tranCode, type, cardVol, cardRate, cardAmt, origVol,
					origRate, origAmt, netDebit, amtDebit, jetcoCharge);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + tranCode);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String tranCode, String type, String cardVol,
			String cardRate, String cardAmt, String origVol, String origRate,
			String origAmt, String netDebit, String amtDebit, String jetcoCharge) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP46(TRANCODE,	TRANTYPE,	CHVOL,	CHRATE,	CHAMOUNT,	OVOL,	ORATE,	OAMOUNT,	NETDEBIT,	BILLCREDIT,	JETCOCHARE) "
				+ "values ('"
				+ tranCode
				+ "','"
				+ type
				+ "','"
				+ cardVol
				+ "','"
				+ cardRate
				+ "','"
				+ cardAmt
				+ "','"
				+ origVol
				+ "','"
				+ origRate
				+ "','"
				+ origAmt
				+ "','"
				+ netDebit
				+ "','"
				+ amtDebit
				+ "','"
				+ jetcoCharge + "')";
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
