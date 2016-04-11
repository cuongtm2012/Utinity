import java.io.BufferedReader;
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
public class GTMDP38 {

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
		String fileName = "GTMDP38";

		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(
					prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(0, 6).trim();

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
		String cardNo = "";
		String type = "";
		String accNumber = "";
		String date = "";
		String time = "";
		String seqNo = "";
		String jetcoSeq = "";
		String error = "";
		String tx = "";
		String amount = "";

		String sqlInsert = "";
		try {
			cardNo = line.substring(0, 19).trim();
			type = line.substring(20, 23).trim();
			accNumber = line.substring(23, 42).trim();
			date = line.substring(46, 56).trim();
			time = line.substring(59, 67).trim();
			seqNo = line.substring(70, 77).trim();
			jetcoSeq = line.substring(80, 88).trim();
			error = line.substring(90, 95).trim();
			tx = line.substring(96, 105).trim();
			amount = line.substring(108, 125).trim();

			sqlInsert = insertDB(cardNo, type, accNumber, date, time, seqNo,
					jetcoSeq, error, tx, amount);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + cardNo);
			System.out.println(ex.getMessage());
		}
	}

	public static String insertDB(String cardNo, String type, String accNumber,
			String date, String time, String seqNo, String jetcoSeq,
			String error, String tx, String amount) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP38(CARDNO,	TYPE,	ACCOUNTNUMBER,	DATE38,	TIME38,	SEQNO,	JETCOSEQ,	ERROR,	TX,	AMOUNT) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ type
					+ "','"
					+ accNumber
					+ "','"
					+ date
					+ "','"
					+ time
					+ "','"
					+ seqNo
					+ "','"
					+ jetcoSeq
					+ "','"
					+ error
					+ "','"
					+ tx
					+ "','"
					+ amount
					+ "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
