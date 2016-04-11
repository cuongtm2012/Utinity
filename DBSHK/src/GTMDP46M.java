import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDP46M {

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
		String fileName = "GTMDP46M";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(0, 19).trim();

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
		String txAc = "";
		String postDate = "";
		String txTime = "";
		String txCode = "";
		String txAmt = "";
		String txFee = "";
		String crossBorderFee = "";

		String sqlInsert = "";
		try {
			cardNo = line.substring(0, 19).trim();
			txAc = line.substring(20, 30).trim();
			postDate = line.substring(31, 41).trim();
			txTime = line.substring(42, 50).trim();
			txCode = line.substring(51, 58).trim();
			txAmt = line.substring(59, 69).trim();
			txFee = line.substring(70, 79).trim();
			crossBorderFee = line.substring(103, 123).trim();

			sqlInsert = insertDB(cardNo, txAc, postDate, txTime, txCode, txAmt,
					txFee, crossBorderFee);

			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println("Error cardNumber : " + cardNo);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String cardNo, String txAc, String postDate,
			String txTime, String txCode, String txAmt, String txFee,
			String crossBorderFee) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP46M(CARDNO,	TXTYPE,	POSTDATE,	TXTIME,	TXCODE,	TXAMT,	TXFEE,	CROSSORDERFEE) "
					+ "values ('"
					+ cardNo
					+ "','"
					+ txAc
					+ "','"
					+ postDate
					+ "','"
					+ txTime
					+ "','"
					+ txCode
					+ "','"
					+ txAmt
					+ "','"
					+ txFee + "','" + crossBorderFee + "')";

			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
