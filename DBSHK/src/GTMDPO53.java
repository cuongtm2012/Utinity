import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInput;
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
public class GTMDPO53 {

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
		String fileName = "GTMDPO53";
		
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
		String seqNo = "";
		String octopus = "";
		String actlang = "";
		String merchant = "";
		String doballow = "";
		String name = "";
		String homeNo = "";
		String mobileNo = "";
		String gender = "";

		String sqlInsert = "";
		try {
			seqNo = line.substring(0, 11).trim();
			octopus = line.substring(13, 21).trim();
			actlang = line.substring(34, 58).trim();
			merchant = line.substring(60, 64).trim();
			doballow = line.substring(67, 73).trim();
			name = line.substring(76, 96).trim();
			homeNo = line.substring(98, 114).trim();
			mobileNo = line.substring(98, 114).trim();
			gender = line.substring(98, 114).trim();

			insertDB(seqNo, octopus, actlang, merchant, doballow, name, homeNo,
					mobileNo, gender);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + seqNo + " - " + octopus
					+ actlang + merchant + doballow + name + homeNo + mobileNo
					+ gender);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String seqNo, String octopus, String actlang,
			String merchant, String doballow, String name, String homeNo,
			String mobileNo, String gender) {

		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDPO53(SEQNO,OCTOPUS,ACTLANG,MERCHANT,DOBALLOW,NAME,HOMENO,GENDER) "
					+ "values ('"
					+ seqNo
					+ "','"
					+ octopus
					+ "','"
					+ actlang
					+ "','"
					+ merchant
					+ "','"
					+ doballow
					+ "','"
					+ name
					+ "','"
					+ homeNo + "','" + gender + "')";
			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
