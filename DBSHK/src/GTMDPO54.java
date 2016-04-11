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
public class GTMDPO54 {

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
		String fileName = "GTMDPO54";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 12).trim();

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
		String seqNo = "";
		String docNo = "";
		String newOctopus = "";
		String oldOctopus = "";
		String DOB = "";
		String channel = "";
		String merchant = "";
		String updateTime = "";
		String updateBy = "";

		String sqlInsert = "";
		try {
			seqNo = line.substring(0, 12).trim();
			docNo = line.substring(14, 34).trim();
			newOctopus = line.substring(36, 48).trim();
			oldOctopus = line.substring(50, 62).trim();
			DOB = line.substring(64, 72).trim();
			channel = line.substring(75, 82).trim();
			merchant = line.substring(84, 92).trim();
			updateTime = line.substring(94, 108).trim();
			updateBy = line.substring(111, 121).trim();

			sqlInsert = insertDB(seqNo, docNo, newOctopus, oldOctopus, DOB,
					channel, merchant, updateTime, updateBy);
			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + seqNo + " - " + docNo
					+ newOctopus + oldOctopus + DOB + channel + merchant
					+ updateTime + updateBy);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String seqNo, String docNo,
			String newOctopus, String oldOctopus, String DOB, String channel,
			String merchant, String updateTime, String updateBy) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDPO54(SEQNO,DOCNO,NEWOCTOPUS,OLDOCTOPUS,DOB,CHANNEL,MERCHANT,UPDATETIME,UPDATEBY) "
					+ "values ('"
					+ seqNo
					+ "','"
					+ docNo
					+ "','"
					+ newOctopus
					+ "','"
					+ oldOctopus
					+ "','"
					+ DOB
					+ "','"
					+ channel
					+ "','"
					+ merchant
					+ "','"
					+ updateTime
					+ "','"
					+ updateBy
					+ "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
