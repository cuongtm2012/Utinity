import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread.State;
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
public class GTMDP52 {

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
		String fileName = "GTMDP52";

		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(0, 16).trim();

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
		String cardNo = "";
		String type = "";
		String seq = "";
		String name = "";
		String description = "";
		String opr = "";
		String appTerm = "";
		String time = "";
		String remarks = "";
		String octopusInd = "";
		String aavsAC = "";
		String designedAC = "";

		String sqlInsert = "";
		try {
			cardNo = line.substring(0, 16).trim();
			type = line.substring(17, 21).trim();
			seq = line.substring(23, 26).trim();
			name = line.substring(28, 52).trim();
			description = line.substring(2, 12).trim();
			opr = line.substring(53, 64).trim();
			appTerm = line.substring(65, 79).trim();
			time = line.substring(72, 76).trim();
			remarks = line.substring(78, 82).trim();
			octopusInd = line.substring(84, 99).trim();
			aavsAC = line.substring(100, 116).trim();
			designedAC = line.substring(116, 130).trim();

			sqlInsert = insertDB(cardNo, type, seq, name, description, opr,
					appTerm, time, remarks, octopusInd, aavsAC, designedAC);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			System.out.println("Error CardNo : " + cardNo);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNo, String type, String seq,
			String name, String description, String opr, String appTerm,
			String time, String remarks, String octopusInd, String aavsAC,
			String designedAC) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP52 (CARDNUMBER,	TYPE,	SEQ,	NAME,	DESCRIPTION,	OPR,	APPTERM,	TIME,	OCTOPUSIND,	AAVSAC,	DESIGNEDAC) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ type
					+ "','"
					+ seq
					+ "','"
					+ name
					+ "','"
					+ description
					+ "','"
					+ opr
					+ "','"
					+ appTerm
					+ "','"
					+ time
					+ "','"
					+ octopusInd
					+ "','"
					+ aavsAC
					+ "','" + designedAC + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
