import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDP34 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Statement statement = null;
		Properties prop = new Properties();
		InputStream inputPath = null;
		String line = "";
		String accNo = null;
		long accNum = 0;
		String configFile = "DBSHK.properties";
		String fileName = "GTMDP34";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					accNo = line.substring(33, 50).trim();

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
		String ourdate = "";
		String ourtime = "";
		String ourSeqNo = "";
		String jetcoSeqNo = "";
		String accNo = "";
		String cardNumber = "";
		String tranCode = "";
		String tranDesc = "";
		String feeamount = "";
		String feeaccNo = "";
		String errorCode = "";
		String sqlInsert = "";
		try {
			ourdate = line.substring(1, 10).trim();
			ourtime = line.substring(10, 19).trim();
			ourSeqNo = line.substring(19, 25).trim();
			jetcoSeqNo = line.substring(26, 32).trim();
			accNo = line.substring(33, 50).trim();
			cardNumber = line.substring(51, 68).trim();
			tranCode = line.substring(69, 72).trim();
			tranDesc = line.substring(74, 85).trim();
			feeamount = line.substring(87, 100).trim();
			feeaccNo = line.substring(100, 117).trim();
			errorCode = line.substring(117, 125).trim();

			sqlInsert = insertDB(ourdate, ourtime, ourSeqNo, jetcoSeqNo, accNo,
					cardNumber, tranCode, tranDesc, feeamount, feeaccNo,
					errorCode);
			statement.executeUpdate(sqlInsert);

		} catch (Exception ex) {
			System.out.println("Error CardNo : " + ourdate + " - " + ourtime
					+ ourSeqNo + jetcoSeqNo + accNo + cardNumber + tranCode
					+ tranDesc + feeamount + feeaccNo + errorCode);
			System.out.println(ex.getMessage());
		}

	}

	public static String insertDB(String ourdate, String ourtime,
			String ourSeqNo, String jetcoSeqNo, String accNo,
			String cardNumber, String tranCode, String tranDesc,
			String feeamount, String feeaccNo, String errorCode) {
		String sqlInsert = "";
		try {
			sqlInsert = "Insert into GTMDP34(OURDATE,OURTIME,OURSEQNO,JETCOSEQNO,ACCNO,CARDNUMBER,TRANCODE,TRANDESC,AMOUNT,TRANFEEACCNO,ERRORCODE) "
					+ "values ('"
					+ ourdate
					+ "','"
					+ ourtime
					+ "','"
					+ ourSeqNo
					+ "','"
					+ jetcoSeqNo
					+ "','"
					+ accNo
					+ "','"
					+ cardNumber
					+ "','"
					+ tranCode
					+ "','"
					+ tranDesc
					+ "','"
					+ feeamount
					+ "','"
					+ feeaccNo + "','" + errorCode + "')";
			System.out.println(sqlInsert);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}
}
