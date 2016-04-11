import java.io.BufferedReader;
import java.io.File;
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
public class GTMDU35 {

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
		String fileName = "GTMDU35";
		
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
		String face = "";
		String status = "";
		String ocl = "";
		String result = "";
		String remark = "";
		String sqlInsert = "";
		try {
			bin = line.substring(0, 7).trim();
			cardNo = line.substring(8, 20).trim();
			media = line.substring(21, 26).trim();
			seq = line.substring(27, 30).trim();
			face = line.substring(31, 35).trim();
			status = line.substring(36, 42).trim();
			ocl = line.substring(43, 50).trim();
			result = line.substring(51, 59).trim();
			remark = line.substring(61, 87).trim();

			sqlInsert = insertDB(bin, cardNo, media, seq, face, status, ocl,
					result, remark);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error bin : " + bin);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String bin, String cardNo, String media,
			String seq, String face, String status, String ocl, String result,
			String remark) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDU35 (BIN,	CARDNO,	MEDIA,	SEQ,	FACE,	STATUS,	OCL,	RESULT,	REMARK) "
					+ "values ('"
					+ bin
					+ "','"
					+ cardNo
					+ "','"
					+ media
					+ "','"
					+ seq
					+ "','"
					+ face
					+ "','"
					+ status
					+ "','"
					+ ocl
					+ "','" + result + "','" + remark + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
