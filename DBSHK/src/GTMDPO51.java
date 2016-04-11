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
public class GTMDPO51 {

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
		Integer cardno = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDPO51";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 9).trim();

					try {
						cardno = Integer.valueOf(cardNo);
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
		String actind = "";
		String octopus = "";
		String debtorRef = "";
		String octopus = "";
		String tranCode = "";
		String tranSeq = "";
		String tranAmount = "";
		String respCodeDesc = "";

		String sqlInsert = "";
		try {
			debtorAccountNo = line.substring(0, 9).trim();
			applicantName = line.substring(13, 32).trim();
			debtorRef = line.substring(34, 58).trim();
			octopus = line.substring(60, 64).trim();
			tranCode = line.substring(67, 73).trim();
			tranSeq = line.substring(76, 96).trim();
			tranAmount = line.substring(98, 114).trim();
			respCodeDesc = line.substring(98, 114).trim();

			sqlInsert = insertDB(debtorAccountNo, applicantName, debtorRef, octopus,
					tranCode, tranSeq, tranAmount, respCodeDesc);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + debtorAccountNo + " - "
					+ applicantName + debtorRef + octopus + tranCode + tranSeq
					+ respCodeDesc);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String debtorAccountNo, String applicantName,
			String debtorRef, String octopus, String tranCode, String tranSeq,
			String tranAmount, String respCodeDesc) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP17(DEBTORACCOUNTNO,APPLICANTNAME,DEBTORREF,OCTOPUS,TRANCODE,TRANSEQ,TRANAMOUNT,RESPCODEDESC) "
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
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
