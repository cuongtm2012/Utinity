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
public class GTMDP47K {

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
		String fileName = "GTMDP47K";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(18, 24).trim();

					try {
						accNum = Long.parseLong(accNo);
						if (accNum >= 0) {
							analysis(statement, line);

						}
					} catch (NumberFormatException ex) {
						accNum = -1;
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
		String tranDate = "";
		String tranTime = "";
		String tranSeq = "";
		String jrnlNo = "";
		String cardNum = "";
		String cardM = "";
		String accNo = "";
		String tranCode = "";
		String tranDesc = "";
		String amount = "";
		String tranDetails = "";
		String errorCode = "";

		String sqlInsert = "";
		try {
			tranDate = line.substring(0, 8).trim();
			tranTime = line.substring(9, 17).trim();
			tranSeq = line.substring(18, 24).trim();
			jrnlNo = line.substring(25, 31).trim();
			cardNum = line.substring(32, 50).trim();
			cardM = line.substring(51, 52).trim();
			accNo = line.substring(53, 70).trim();
			tranCode = line.substring(73, 77).trim();
			tranDesc = line.substring(78, 92).trim();
			amount = line.substring(92, 103).trim();
			tranDetails = line.substring(103, 123).trim();
			errorCode = line.substring(123, 132).trim();

			sqlInsert = insertDB(tranDate, tranTime, tranSeq, jrnlNo, cardNum,
					cardM, accNo, tranCode, tranDesc, amount, tranDetails,
					errorCode);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + tranDate);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String tranDate, String tranTime,
			String tranSeq, String jrnlNo, String cardNum, String cardM,
			String accNo, String tranCode, String tranDesc, String amount,
			String tranDetails, String errorCode) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP47K(TRANDATE,	TRANTIME,	TRANSEQNO,	JRNLNO,	CARDNUMBER,	CARDTYPE,	ACCNO,	TRANCODE,	TRANDESC,	AMOUNT,	TRANDETAILS,	ERRORCODE) "
					+ "values ('"
					+ tranDate
					+ "','"
					+ tranTime
					+ "','"
					+ tranSeq
					+ "','"
					+ jrnlNo
					+ "','"
					+ cardNum
					+ "','"
					+ cardM
					+ "','"
					+ accNo
					+ "','"
					+ tranCode
					+ "','"
					+ tranDesc
					+ "','"
					+ amount
					+ "','" + tranDetails + "','" + errorCode + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
