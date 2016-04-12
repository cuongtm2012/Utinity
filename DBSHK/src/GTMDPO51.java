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
		String actInd   =    "";
		String octopus   =    "";
		String debCode   =    "";
		String debType   =    "";
		String debAcc   =    "";
		String debName   =    "";
		String debRef   =    "";
		String debDocTYNO   =    "";
		String DOB   =    "";
		String genderACC   =    "";
		String crCode   =    "";
		String crAcc   =    "";
		String crName   =    "";
		String crDocTYNO   =    "";
		String topupVal   =    "";
		String homePhone   =    "";
		String mobiPhone   =    "";
		String lang   =    "";
		
		String sqlInsert = "";
		try {
			actInd = line.substring(2, 12).trim();
			octopus = line.substring(2, 12).trim();
			debCode = line.substring(2, 12).trim();
			debType = line.substring(2, 12).trim();
			debAcc = line.substring(2, 12).trim();
			debName = line.substring(2, 12).trim();
			debRef = line.substring(2, 12).trim();
			debDocTYNO = line.substring(2, 12).trim();
			DOB = line.substring(2, 12).trim();
			genderACC = line.substring(2, 12).trim();
			crCode = line.substring(2, 12).trim();
			crAcc = line.substring(2, 12).trim();
			crName = line.substring(2, 12).trim();
			crDocTYNO = line.substring(2, 12).trim();
			topupVal = line.substring(2, 12).trim();
			homePhone = line.substring(2, 12).trim();
			mobiPhone = line.substring(2, 12).trim();
			lang = line.substring(2, 12).trim();

			insertDB(actInd, octopus, debCode, debType, debAcc, debName, crCode, crAcc, crName, crDocTYNO, topupVal, homePhone, mobiPhone);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String insertDB(String actInd, 	String octopus, 	String debCode, 	String debType, 	String debAcc, 	String debName, 	
			String debRef, 	String debDocTYNO, 	String DOB, 	String genderACC, 	String crCode, 	String crAcc, 	String crName, 	
			String crDocTYNO, 	String topupVal, 	String homePhone, 	String mobiPhone, 	String lang) {
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
