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
public class GTMDPO04 {

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
		String fileName = "GTMDPO04";
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(14, 30).trim();

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
		String action = "";
		String bankCode = "";
		String debtorAccountNo = "";
		String nameAAVSApplicant = "";
		String debtorRef = "";
		String topupVal = "";
		String octopus = "";
		String applicantID = "";

		String sqlInsert = "";
		try {
			action = line.substring(0, 6).trim();
			bankCode = line.substring(8, 12).trim();
			debtorAccountNo = line.substring(14, 30).trim();
			nameAAVSApplicant = line.substring(32, 56).trim();
			debtorRef = line.substring(58, 68).trim();
			topupVal = line.substring(71, 81).trim();
			octopus = line.substring(83, 91).trim();
			applicantID = line.substring(93, 109).trim();

			sqlInsert = insertDB(action, bankCode, debtorAccountNo,
					nameAAVSApplicant, debtorRef, topupVal, octopus,
					applicantID);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + action + " - " + bankCode
					+ debtorAccountNo + nameAAVSApplicant + debtorRef
					+ topupVal + octopus + applicantID);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String action, String bankcode,
			String debtoraccountno, String nameaavs, String debtorref,
			String topupval, String octopus, String applicantid) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDPO04(ACTION,BANKCODE,DEBTORACCOUNT,NAMEAAVS,DEBTORREF,TOPUPVAL,OCTOPUS,APPLICANTID) "
					+ "values ('"
					+ action
					+ "','"
					+ bankcode
					+ "','"
					+ debtoraccountno
					+ "','"
					+ nameaavs
					+ "','"
					+ debtorref
					+ "','"
					+ topupval
					+ "','"
					+ octopus
					+ "','"
					+ applicantid
					+ "')";

			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
