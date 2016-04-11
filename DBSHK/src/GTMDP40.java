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
public class GTMDP40 {

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
		String fileName = "GTMDP40";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(1, 13).trim();

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
		String cardNumber = "";
		String seq = "";
		String type = "";
		String inpOpr = "";
		String inpTime = "";
		String appOpr = "";
		String appTime = "";
		String tranCode = "";
		String tranDesc = "";
		String name = "";
		String remarks = "";

		try {
			cardNumber = line.substring(1, 13).trim();
			seq = line.substring(14, 16).trim();
			type = line.substring(17, 18).trim();
			inpOpr = line.substring(20, 24).trim();
			inpTime = line.substring(26, 32).trim();
			appOpr = line.substring(33, 37).trim();
			appTime = line.substring(39, 45).trim();
			tranCode = line.substring(47, 49).trim();
			tranDesc = line.substring(52, 58).trim();
			name = line.substring(65, 89).trim();
			remarks = line.substring(90, 130).trim();

			insertDB(cardNumber, seq, type, inpOpr, inpTime, appOpr, appTime,
					tranCode, tranDesc, name, remarks);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + cardNumber);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String cardNumber, String seq, String type,
			String inpOpr, String inpTime, String appOpr, String appTime,
			String tranCode, String tranDesc, String name, String remarks) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP40(CARDNUMBER,	SEQ,	TYPE,	INPUTOPR,	INPUTTIME,	APPOPR,	APPTIME,	TRANCODE,	TRANDESC,	NAME,	REMARKS) "
				+ "values ('"
				+ cardNumber
				+ "','"
				+ seq
				+ "','"
				+ type
				+ "','"
				+ inpOpr
				+ "','"
				+ inpTime
				+ "','"
				+ appOpr
				+ "','"
				+ appTime
				+ "','"
				+ tranCode
				+ "','"
				+ tranDesc
				+ "','"
				+ name
				+ "','"
				+ remarks
				+ "')";
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
