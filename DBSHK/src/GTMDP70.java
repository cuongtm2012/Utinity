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
public class GTMDP70 {

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
		String date = null;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP35";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					date = line.substring(0, 9).trim();

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
		String ourDate = "";
		String ourTime = "";
		String ourSeqNo = "";
		String traceNo = "";
		String accNo = "";
		String tranCCY = "";
		String tranAmt = "";
		String tranCode = "";
		String tranDesc = "";
		String sttAmount = "";
		String bankCharge = "";
		String cupCharge = "";
		String respCode = "";
		String acqCountry = "";

		String sqlInsert = "";
		try {
			ourDate = line.substring(0, 9).trim();
			ourTime = line.substring(10, 18).trim();
			ourSeqNo = line.substring(19, 25).trim();
			traceNo = line.substring(26, 32).trim();
			accNo = line.substring(33, 49).trim();
			tranCCY = line.substring(51, 54).trim();
			tranAmt = line.substring(55, 68).trim();
			tranCode = line.substring(69, 73).trim();
			tranDesc = line.substring(74, 85).trim();
			sttAmount = line.substring(85, 99).trim();
			bankCharge = line.substring(100, 107).trim();
			cupCharge = line.substring(108, 116).trim();
			respCode = line.substring(118, 122).trim();
			acqCountry = line.substring(123, 130).trim();

			sqlInsert = insertDB(ourDate, ourTime, ourSeqNo, traceNo, accNo,
					tranCCY, tranAmt, tranCode, tranDesc, sttAmount,
					bankCharge, cupCharge, respCode, acqCountry);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error OurDate : " + ourDate);
			System.out.println(ex.getMessage());
		}
	}

	public static String insertDB(String ourDate, String ourTime,
			String ourSeqNo, String traceNo, String accNo, String tranCCY,
			String tranAmt, String tranCode, String tranDesc, String sttAmount,
			String bankCharge, String cupCharge, String respCode,
			String acqCountry) {

		String sqlInsert = "";
		try {

			sqlInsert = "Insert into GTMDP70(OURDATE,	OURTIME,	OURSEQNO,	TRACENO,	ACCNO,	CCY,	AMOUNT,	CODE,	DESCRIPTION,	STTAMOUNT,	BANKCHARGE,	CUPCHARGE,	RESPCODE,	ACQCOUNTRY) "
					+ "values ('"
					+ ourDate
					+ "','"
					+ ourTime
					+ "','"
					+ ourSeqNo
					+ "','"
					+ traceNo
					+ "','"
					+ accNo
					+ "','"
					+ tranCCY
					+ "','"
					+ tranAmt
					+ "','"
					+ tranCode
					+ "','"
					+ tranDesc
					+ "','"
					+ sttAmount
					+ "','"
					+ bankCharge
					+ "','"
					+ cupCharge
					+ "','" + respCode + "','" + acqCountry + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
