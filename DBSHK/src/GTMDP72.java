import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import util.helpConfiguration;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDP72 {

	/**
	 * @param args
	 */
	static String file = "";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		Properties prop = new Properties();
		InputStream inputPath = null;
		String line = "";
		String date = null;
		Date dateR;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP72";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			file = prop.getProperty(fileName);
			int index = file.lastIndexOf("\\");
			file = file.substring(index+1);
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					date = line.substring(0, 10).trim();

					try { 
						if (helpConfiguration.isValidDate(date)) {
							analysis(statement, line);
						}
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
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
		String ourDate   =    "";
		String ourTime   =    "";
		String ourSeqNo   =    "";
		String traceNo   =    "";
		String accNo   =    "";
		String transCCY   =    "";
		String transAmt   =    "";
		String transCode   =    "";
		String transDesc   =    "";
		String sttAmt   =    "";
		String bankCharge   =    "";
		String cupCharge   =    "";
		String respCode   =    "";
		String acqCountry   =    "";

		String sqlInsert = "";
		try {
			ourDate = line.substring(0, 10).trim();
			ourTime = line.substring(10, 19).trim();
			ourSeqNo = line.substring(19, 26).trim();
			traceNo = line.substring(26, 33).trim();
			accNo = line.substring(33, 51).trim();
			transCCY = line.substring(51, 55).trim();
			transAmt = line.substring(55, 69).trim();
			transCode = line.substring(69, 74).trim();
			transDesc = line.substring(74, 86).trim();
			sttAmt = line.substring(86, 100).trim();
			bankCharge = line.substring(100, 108).trim();
			cupCharge = line.substring(108, 117).trim();
			respCode = line.substring(117, 123).trim();
			acqCountry = line.substring(123, line.length()).trim();

			sqlInsert = insertDB(ourDate, ourTime, ourSeqNo, traceNo, accNo, transCCY, transAmt, transCode, transDesc, sttAmt, bankCharge, cupCharge, respCode, acqCountry);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String insertDB(String ourDate, 	String ourTime, 	String ourSeqNo, 	String traceNo, 	String accNo, 	String transCCY, 	String transAmt, 	String transCode, 	
			String transDesc, 	String sttAmt, 	String bankCharge, 	String cupCharge, 	String respCode, 	String acqCountry) {
		String sqlInsert = "";

		try {
			if(file.contains("GTMDP72_001")){
			sqlInsert = "Insert into GTMDP72_001(OURDATE,	OURTIME,	OURSEQNO,	TRACENO,	TRANSCCY,	TRANSAMT,	TRANSCODE,	TRANSDESC,	STTAMT,	BANKCHARGE,	CUPCHARGE,	RESPCODE,	ACQCOUNTRY) "
					+ "values ('"
					+ ourDate
					+ "','"
					+ ourTime
					+ "','"
					+ ourSeqNo
					+ "','"
					+ traceNo
					+ "','"
					+ transCCY
					+ "','"
					+ transAmt
					+ "','" + transCode + "','" + transDesc + "','" + sttAmt + "','" + bankCharge + "','" + cupCharge + "','" + respCode + "','" + acqCountry + "')";
			} else if(file.contains("GTMDP72_002")) {
			sqlInsert = "Insert into GTMDP72_002(OURDATE,	OURTIME,	OURSEQNO,	TRACENO,	TRANSCCY,	TRANSAMT,	TRANSCODE,	TRANSDESC,	STTAMT,	BANKCHARGE,	CUPCHARGE,	RESPCODE,	ACQCOUNTRY) "
					+ "values ('"
					+ ourDate
					+ "','"
					+ ourTime
					+ "','"
					+ ourSeqNo
					+ "','"
					+ traceNo
					+ "','"
					+ transCCY
					+ "','"
					+ transAmt
					+ "','" + transCode + "','" + transDesc + "','" + sttAmt + "','" + bankCharge + "','" + cupCharge + "','" + respCode + "','" + acqCountry + "')";
			}
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
