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
public class GTMDP46C {

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
		String fileName = "GTMDP46C";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(1, 19).trim();

					try {
						accNum = Long.parseLong(accNo);
						if (accNum >= 0) {
							analysis(statement, line);
						}
					} catch (NumberFormatException ex) {
						accNum = -1;
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
		String txAc = "";
		String postDate = "";
		String txTime = "";
		String txCode = "";
		String txAmt = "";
		String txFee = "";

		String sqlInsert = "";

		try {
			cardNo = line.substring(1, 19).trim();
			txAc = line.substring(20, 32).trim();
			postDate = line.substring(33, 43).trim();
			txTime = line.substring(44, 52).trim();
			txCode = line.substring(53, 60).trim();
			txAmt = line.substring(61, 71).trim();
			txFee = line.substring(72, 89).trim();

			sqlInsert = insertDB(cardNo, txAc, postDate, txTime, txCode, txAmt,
					txFee);

			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + cardNo);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNo, String txAc, String postDate,
			String txTime, String txCode, String txAmt, String txFee) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP46C(CARDNO,	TXACC,	POSTDATE,	TXTIME,	TXCODE,	TXAMT,	TXFEE) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ txAc
					+ "','"
					+ postDate
					+ "','"
					+ txTime
					+ "','"
					+ txCode
					+ "','"
					+ txAmt
					+ "','"
					+ txFee + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
