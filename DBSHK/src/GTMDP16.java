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
public class GTMDP16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		InputStream inputPath = null;
		String line = "";
		String octopus = null;
		long cardno = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP16";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					octopus = line.substring(0, 9).trim();

					try {
						cardno = Long.parseLong(octopus);
					} catch (NumberFormatException ex) {
						cardno = -1;
					}

				}
				if (line != null && !line.isEmpty() && cardno >= 0) {
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
		String octopus = "";
		String accountNo = "";
		String refundIndicator = "";
		String amount = "";
		String lastTranTime = "";
		String cardStatus = "";
		String respCodeDesc = "";

		try {
			octopus = line.substring(0, 9).trim();
			accountNo = line.substring(12, 28).trim();
			refundIndicator = line.substring(30, 39).trim();
			amount = line.substring(42, 52).trim();
			lastTranTime = line.substring(56, 70).trim();
			cardStatus = line.substring(81, 87).trim();
			respCodeDesc = line.substring(89, 124).trim();

			insertDB(octopus, accountNo, refundIndicator, amount, lastTranTime,
					cardStatus, respCodeDesc);

		} catch (Exception ex) {
			System.out.println("Error CardNo : " + octopus + " - " + accountNo
					+ refundIndicator + amount + lastTranTime + cardStatus
					+ respCodeDesc);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String octopus, String accountNo,
			String refundIndicator, String amount, String lastTranTime,
			String cardStatus, String respCodeDesc) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP16(OCTOPUS,ACCOUNTNO,REFERENCEINDICATOR,AMOUNT,LASTTRANTIME,CARDSTATUS,RESPCODEDESC) "
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
				+ "','" + cardStatus + "','" + respCodeDesc + "')";
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
