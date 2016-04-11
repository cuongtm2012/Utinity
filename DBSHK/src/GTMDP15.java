import java.io.BufferedReader;
import java.io.File;
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
public class GTMDP15 {

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
		String fileName = "GTMDP15";

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
						if (cardno >= 0) {
							analysis(line);
						}
					} catch (NumberFormatException ex) {
						cardno = -1;
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
		String octopus = "";
		String cardNumber = "";
		String cardHolderName = "";
		String docType = "";
		String topupAmount = "";
		String debtorReference = "";
		String aavsAccount = "";
		String designated = "";

		try {
			octopus = line.substring(0, 9).trim();
			cardNumber = line.substring(13, 32).trim();
			cardHolderName = line.substring(34, 58).trim();
			docType = line.substring(60, 64).trim();
			topupAmount = line.substring(67, 73).trim();
			debtorReference = line.substring(76, 96).trim();
			aavsAccount = line.substring(98, 114).trim();
			designated = line.substring(116, 130).trim();

			insertDB(octopus, cardNumber, cardHolderName, docType, topupAmount,
					debtorReference, aavsAccount, designated);

		} catch (Exception ex) {
			System.out.println("Error CardNo : " + octopus + " - " + cardNumber
					+ cardHolderName + docType + topupAmount + debtorReference
					+ aavsAccount + designated);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String octopus, String cardNumber,
			String cardHolderName, String docType, String topupAmount,
			String debtorReference, String aavsAccount, String designated) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP15 (OCTOPUS,CARDNUMBER,CARDHOLDERNAME,DOCTYPE,TOPUPAMOUNT,DEBTORREFERENCE,AAVSACCOUNT,DESIGNAC) "
				+ "values ('"
				+ octopus
				+ "','"
				+ cardNumber
				+ "','"
				+ cardHolderName
				+ "','"
				+ docType
				+ "','"
				+ topupAmount
				+ "','"
				+ debtorReference
				+ "','"
				+ aavsAccount
				+ "','"
				+ designated + "')";
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
