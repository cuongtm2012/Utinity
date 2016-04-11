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
public class GTMDP76 {

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
		String fileName = "GTMDP76";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 18).trim();

					try {
						cardno = Long.parseLong(cardNo);
						if (line != null && !line.isEmpty() && cardno >= 0) {
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
		String cardNumber = "";
		String type = "";
		String seq = "";
		String cardHolderName = "";
		String san1 = "";
		String san2 = "";
		String siPan = "";
		String siSan1 = "";
		String siSan2 = "";
		String acPan = "";
		String acSan1 = "";
		String acSan2 = "";

		String sqlInsert = "";
		try {
			cardNumber = line.substring(0, 18).trim();
			type = line.substring(19, 23).trim();
			seq = line.substring(25, 28).trim();
			cardHolderName = line.substring(30, 55).trim();
			san1 = line.substring(55, 66).trim();
			san2 = line.substring(66, 78).trim();
			siPan = line.substring(78, 88).trim();
			siSan1 = line.substring(88, 95).trim();
			siSan2 = line.substring(95, 102).trim();
			acPan = line.substring(102, 111).trim();
			acSan1 = line.substring(111, 117).trim();
			acSan2 = line.substring(117, 127).trim();

			sqlInsert = insertDB(cardNumber, type, seq, cardHolderName, san1,
					san2, siPan, siSan1, siSan2, acPan, acSan1, acSan2);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + cardNumber);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNumber, String type, String seq,
			String cardHolderName, String san1, String san2, String siPan,
			String siSan1, String siSan2, String acPan, String acSan1,
			String acSan2) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP76(CARDNUMBER,	TYPE,	SEQ,	CARDHOLDERNAME,	SAN1,	SAN2,	SIPAN,	SISAN1,	SISAN2,	ACPAN,	ACSAN1,	ACSAN2) "
					+ "values ('"
					+ cardNumber
					+ "','"
					+ type
					+ "','"
					+ seq
					+ "','"
					+ cardHolderName
					+ "','"
					+ san1
					+ "','"
					+ san2
					+ "','"
					+ siPan
					+ "','"
					+ siSan1
					+ "','"
					+ siSan2
					+ "','"
					+ acPan
					+ "','" + acSan1 + "','" + acSan2 + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
