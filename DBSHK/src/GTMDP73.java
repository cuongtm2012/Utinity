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
public class GTMDP73 {

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
		String fileName = "GTMDP73";
		
		try {
			inputPath = new FileInputStream(configFile);
			prop.load(inputPath);

			FileInputStream fis = new FileInputStream(prop.getProperty(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			dbConnection = helpConfiguration.getDBConnection();
			statement = dbConnection.createStatement();

			while ((line = br.readLine()) != null) {
				if (line.length() > 9) {
					cardNo = line.substring(36, 53).trim();

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
		String cupdate   =    "";
		String cuptime   =    "";
		String cuptraceNo   =    "";
		String authCode   =    "";
		String cardnum   =    "";
		String messtype   =    "";
		String proccode   =    "";
		String transDesc   =    "";
		String transAmt   =    "";
		String transCCY   =    "";
		String convRate   =    "";
		String sttAmt   =    "";
		String chargeRebate   =    "";
		String respCode   =    "";
		String chgRebate   =    "";
		String acqIns   =    "";
		String fwdIns   =    "";
		String xmittime   =    "";
		String merc   =    "";
		String acpterm   =    "";
		String acpidcode   =    "";
		String retrievalRef   =    "";
		String rcvins   =    "";
		String poscond   =    "";
		String convDate   =    "";
		String cbbillAmtRate   =    "";
		String swCCYRate   =    "";

		String sqlInsert = "";
		try {
			cupdate = line.substring(2, 12).trim();
			cuptime = line.substring(2, 12).trim();
			cuptraceNo = line.substring(2, 12).trim();
			authCode = line.substring(2, 12).trim();
			cardnum = line.substring(2, 12).trim();
			messtype = line.substring(2, 12).trim();
			proccode = line.substring(2, 12).trim();
			transDesc = line.substring(2, 12).trim();
			transAmt = line.substring(2, 12).trim();
			transCCY = line.substring(2, 12).trim();
			convRate = line.substring(2, 12).trim();
			sttAmt = line.substring(2, 12).trim();
			chargeRebate = line.substring(2, 12).trim();
			respCode = line.substring(2, 12).trim();
			chgRebate = line.substring(2, 12).trim();
			acqIns = line.substring(2, 12).trim();
			fwdIns = line.substring(2, 12).trim();
			xmittime = line.substring(2, 12).trim();
			merc = line.substring(2, 12).trim();
			acpterm = line.substring(2, 12).trim();
			acpidcode = line.substring(2, 12).trim();
			retrievalRef = line.substring(2, 12).trim();
			rcvins = line.substring(2, 12).trim();
			poscond = line.substring(2, 12).trim();
			convDate = line.substring(2, 12).trim();
			cbbillAmtRate = line.substring(2, 12).trim();
			swCCYRate = line.substring(2, 12).trim();


			sqlInsert = insertDB(cupdate, cuptime, cuptraceNo, authCode, cardnum, messtype, proccode, transDesc, transAmt, transCCY, convRate, sttAmt, chargeRebate, 
					respCode, chgRebate, acqIns, fwdIns, xmittime, merc, acpterm, acpidcode, retrievalRef, rcvins, poscond, convDate, cbbillAmtRate, swCCYRate);

			statement.executeUpdate(sqlInsert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String insertDB(String cupdate, 	String cuptime, 	String cuptraceNo, 	String authCode, 	String cardnum, 	String messtype, 	String proccode, 	
			String transDesc, 	String transAmt, 	String transCCY, 	String convRate, 	String sttAmt, 	String chargeRebate, 	String respCode, 	String chgRebate, 	
			String acqIns, 	String fwdIns, 	String xmittime, 	String merc, 	String acpterm, 	String acpidcode, 	String retrievalRef, 	String rcvins, 	
			String poscond, 	String convDate, 	String cbbillAmtRate, 	String swCCYRate) {
		String sqlInsert = "";

		try {
			sqlInsert = "Insert into GTMDP73(CUPDATE,	CUPTIME,	CUPTRACENO,	AUTHCODE,	CARDNUM,	MESSTYPE,	PROCCODE,	TRANSDESC,	TRANSAMT,	TRANSCCY,	"
					+ "CONVRATE,	STTAMT,	CHARGEREBATE,	RESPCODE,	CHGREBRATE,	ACQINS,	FWDINS,	XMITTIME,	MERC,	ACPTERM,	ACPIDCODE,	RETRIEVALREF,	RCVINS,	"
					+ "POSCOND,	CONVDATE,	CBBILLAMTRATE,	SWCCYRATE) "
					+ "values ('"
					+ cupdate
					+ "','"
					+ cuptime
					+ "','"
					+ cuptraceNo
					+ "','"
					+ authCode
					+ "','"
					+ cardnum
					+ "','"
					+ messtype
					+ "','" + proccode + "','" + transDesc + "','" + transAmt + "','" + transCCY + "','" + convRate + "','" 
					+ sttAmt + "','" + chargeRebate + "','" + respCode + "','" + chgRebate + "','" + poscond + "','" 
					+ convDate + "','" + cbbillAmtRate + "','" + swCCYRate + "')";

			System.out.println(sqlInsert);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return sqlInsert;
	}

}
