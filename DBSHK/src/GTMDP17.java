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
public class GTMDP17 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		InputStream inputPath = null;
		String line = "";
		String accNo = null;
		long accNum = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP17";
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(0, 17).trim();

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
		String debtorAccountNo = "";
		String applicantName = "";
		String debtorRef = "";
		String octopus = "";
		String tranCode = "";
		String tranSeq = "";
		String tranAmount = "";
		String respCodeDesc = "";

		try {
			debtorAccountNo = line.substring(0, 17).trim();
			applicantName = line.substring(19, 43).trim();
			debtorRef = line.substring(45, 55).trim();
			octopus = line.substring(58, 67).trim();
			tranCode = line.substring(69, 74).trim();
			tranSeq = line.substring(77, 84).trim();
			tranAmount = line.substring(85, 96).trim();
			respCodeDesc = line.substring(97, 132).trim();

			insertDB(debtorAccountNo, applicantName, debtorRef, octopus,
					tranCode, tranSeq, tranAmount, respCodeDesc);

		} catch (Exception ex) {
			System.out.println("Error CardNo : " + debtorAccountNo + " - "
					+ applicantName + debtorRef + octopus + tranCode + tranSeq
					+ respCodeDesc);
			System.out.println(ex.getMessage());
		}

	}

	public static void insertDB(String debtorAccountNo, String applicantName,
			String debtorRef, String octopus, String tranCode, String tranSeq,
			String tranAmount, String respCodeDesc) {
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();

		String sqlInsert = "Insert into GTMDP17(DEBTORACCOUNTNO,APPLICANTNAME,DEBTORREF,OCTOPUS,TRANCODE,TRANSEQ,TRANAMOUNT,RESPCODEDESC) "
				+ "values ('"
				+ debtorAccountNo
				+ "','"
				+ applicantName
				+ "','"
				+ debtorRef
				+ "','"
				+ octopus
				+ "','"
				+ tranCode
				+ "','"
				+ tranSeq + "','" + tranAmount + "','" + respCodeDesc + "')";
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
