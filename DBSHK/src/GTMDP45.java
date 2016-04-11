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
public class GTMDP45 {

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
		String fileName = "GTMDP45";
		
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
		String accNumber = "";
		String debitAmt = "";
		String branch = "";
		String seqNo = "";
		String remarks = "";

		try {
			accNumber = line.substring(6, 22).trim();
			debitAmt = line.substring(30, 42).trim();
			branch = line.substring(60, 66).trim();
			seqNo = line.substring(76, 86).trim();
			remarks = line.substring(95, 118).trim();

			insertDB(accNumber, debitAmt, branch, seqNo, remarks);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + accNumber);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String accNumber, String debitAmt,
			String branch, String seqNo, String remarks) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP45(ACCNUMBER,	DEBITAMOUNT,	BRANCH,	SEQNO,	REMARKS) "
				+ "values ('"
				+ accNumber
				+ "','"
				+ debitAmt
				+ "','"
				+ branch
				+ "','" + seqNo + "','" + remarks + "')";
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
