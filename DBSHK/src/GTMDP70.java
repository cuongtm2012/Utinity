import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.helpConfiguration;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDP70 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP70_001.TXT";
	            //String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP70_002.TXT";
	            FileInputStream fis = new FileInputStream(new File(input));
	            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	            String line;
	            String cardNo = null;
	            Integer cardno = 0;
	            while ((line = br.readLine()) != null) {
		                if(line.length() > 9){
		                	cardNo = line.substring(0,9).trim();
		                	
		                	try{
		                		cardno = Integer.valueOf(cardNo);
		                	} catch(NumberFormatException ex){
		                		cardno = -1;
		                	}
		                	
		                }
		                if (line != null && !line.isEmpty() && cardno >= 0) {
		                    analysis(line);
		                    
		                }
	            }
	            
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 System.out.println("Insert DB successfull");
	}
	
	public static void analysis(String line){
		String ourDate   =    "";
		String ourTime   =    "";
		String ourSeqNo   =    "";
		String traceNo   =    "";
		String accNo   =    "";
		String tranCCY   =    "";
		String tranAmt   =    "";
		String tranCode   =    "";
		String tranDesc   =    "";
		String sttAmount   =    "";
		String bankCharge   =    "";
		String cupCharge   =    "";
		String respCode   =    "";
		String acqCountry   =    "";
		
		try{
			ourDate = line.substring(2, 12).trim();
			ourTime = line.substring(2, 12).trim();
			ourSeqNo = line.substring(2, 12).trim();
			traceNo = line.substring(2, 12).trim();
			accNo = line.substring(2, 12).trim();
			tranCCY = line.substring(2, 12).trim();
			tranAmt = line.substring(2, 12).trim();
			tranCode = line.substring(2, 12).trim();
			tranDesc = line.substring(2, 12).trim();
			sttAmount = line.substring(2, 12).trim();
			bankCharge = line.substring(2, 12).trim();
			cupCharge = line.substring(2, 12).trim();
			respCode = line.substring(2, 12).trim();
			acqCountry = line.substring(2, 12).trim();
			
			insertDB(ourDate, ourTime, ourSeqNo, traceNo, accNo, tranCCY, tranAmt, tranCode, tranDesc, sttAmount, bankCharge, cupCharge, respCode, acqCountry);
		
		} catch(Exception ex){
			System.out.println("Error OurDate : "+ourDate);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String ourDate, 	String ourTime, 	String ourSeqNo, 	String traceNo, 	
			String accNo, 	String tranCCY, 	String tranAmt, 	String tranCode, 	String tranDesc, 	
			String sttAmount, 	String bankCharge, 	String cupCharge, 	String respCode, 	String acqCountry){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP70(OURDATE,	OURTIME,	OUTSEQNO,	TRACENO,	ACCNO,	CCY,	AMOUNT,	CODE,	DESCRIPTION,	STTAMOUNT,	BANKCHARGE,	CUPCHARGE,	RESPCODE,	ACQCOUNTRY) "
				+ "values ('"+ourDate+"','"+ourTime+"','"+ourSeqNo+"','"+traceNo+"','"+accNo+"','"+tranCCY+"','"+tranAmt+"','"+tranCode+"','"+tranDesc+"','"+sttAmount
				+"','"+bankCharge+"','"+cupCharge+"','"+respCode+"','"+acqCountry+"')";
		try{
			dbConnection = help.getDBConnection();
			statement = dbConnection.createStatement();
			
			System.out.println(sqlInsert);
			
			statement.executeUpdate(sqlInsert);
			
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	}
