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
public class GTMDP35 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP35_121.TXT";
	            FileInputStream fis = new FileInputStream(new File(input));
	            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	            String line;
	            String accNo = null;
	            long accNum = 0;
	            while ((line = br.readLine()) != null) {
		                if(line.length() > 9){
		                	accNo = line.substring(20,26).trim();
		                	
		                	try{
		                		accNum = Long.parseLong(accNo);
		                	} catch(NumberFormatException ex){
		                		accNum = -1;
		                	}
		                	
		                }
		                if (line != null && !line.isEmpty() && accNum >= 0) {
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
		String bankCode   =    "";
		String accNumber   =    "";
		String accType   =    "";
		String cardNumber   =    "";
		String cardType   =    "";
		String ourDate   =    "";
		String ourTime   =    "";
		String jetcoSeqNo   =    "";
		String tranCode   =    "";
		String tranDesc   =    "";
		String amount   =    "";
		String feeAccNumber   =    "";
		String rejCode   =    "";

		
		try{
			bankCode = line.substring(0, 4).trim();
			accNumber = line.substring(5, 24).trim();
			accType = line.substring(25, 27).trim();
			cardNumber = line.substring(28, 47).trim();
			cardType = line.substring(48, 50).trim();
			ourDate = line.substring(51, 59).trim();
			ourTime = line.substring(60, 68).trim();
			jetcoSeqNo = line.substring(69, 75).trim();
			tranCode = line.substring(76, 79).trim();
			tranDesc = line.substring(81, 89).trim();
			amount = line.substring(94, 106).trim();
			feeAccNumber = line.substring(107, 126).trim();
			rejCode = line.substring(127, 131).trim();

			
			insertDB(bankCode, accNumber, accType, cardNumber, cardType, ourDate, ourTime, jetcoSeqNo, tranCode, tranDesc, amount, feeAccNumber, rejCode);

			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+bankCode);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String bankCode, 	String accNumber, 	String accType, 	String cardNumber, 	
			String cardType, 	String ourDate, 	String ourTime, 	String jetcoSeqNo, 	String tranCode, 	String tranDesc, 	
			String amount, 	String feeAccNumber, 	String rejCode)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP35(BANKCODE, 	ACCOUNTNUMBER, 	TYPEACC, 	CARDNUMBER, 	TYPECARD, 	OURDATE, 	OURTIME, 	JETCOSEQNO, 	TRANCODE, 	TRANDESC, 	AMOUNT, 	TRANFEEACC, 	ERRORCODE) "
				+ "values ('"+bankCode+"','"+accNumber+"','"+accType+"','"+cardNumber+"','"+cardType+"','"+ourDate+"','"+ourTime+"','"
				+	jetcoSeqNo+"','"+tranCode+"','"+tranDesc+"','"+amount+"','"+feeAccNumber+"','"+rejCode+"')";
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
