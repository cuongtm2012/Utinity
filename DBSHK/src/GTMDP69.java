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
public class GTMDP69 {

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
		String fileName = "GTMDP69";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 7).trim();

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
		String bin = "";
		String cardNo = "";
		String media = "";
		String seq = "";
		String documentNumber = "";
		String tin = "";
		String template = "";
		String exception = "";

		String sqlInsert = "";
		try {
			bin = line.substring(0, 7).trim();
			cardNo = line.substring(8, 20).trim();
			media = line.substring(21, 26).trim();
			seq = line.substring(27, 30).trim();
			documentNumber = line.substring(31, 51).trim();
			tin = line.substring(52, 61).trim();
			template = line.substring(63, line.length()).trim();
			// exception = line.substring(100,line.length()).trim();

			sqlInsert = insertDB(bin, cardNo, media, seq, documentNumber, tin,
					template, exception);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + bin + " - " + cardNo + media
					+ seq + documentNumber + tin + template + exception);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String bin, String cardNo, String media,
			String seq, String documentNumber, String tin, String template,
			String exception) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP69(BIN,CARDNO,MEDIA,SEQ,DOCUMENTNUMBER,TIN,TEMPLATE,EXCEPTION) "
					+ "values ('"
					+ bin
					+ "','"
					+ cardNo
					+ "','"
					+ media
					+ "','"
					+ seq
					+ "','"
					+ documentNumber
					+ "','"
					+ tin
					+ "','" + template + "','" + exception + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
