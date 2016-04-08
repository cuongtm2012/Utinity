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
public class GTMDP47K {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP46_146.TXT";
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
		String tranDate   =    "";
		String tranTime   =    "";
		String tranSeq   =    "";
		String jrnlNo   =    "";
		String txCode   =    "";
		String cardNum   =    "";
		String cardM   =    "";
		String accNo   =    "";
		String tranCode   =    "";
		String tranDesc   =    "";
		String amount   =    "";
		String tranDetails   =    "";
		String errorCode   =    "";
		
		try{
			tranDate = line.substring(2, 12).trim();
			tranTime = line.substring(2, 12).trim();
			tranSeq = line.substring(2, 12).trim();
			jrnlNo = line.substring(2, 12).trim();
			txCode = line.substring(2, 12).trim();
			cardNum = line.substring(2, 12).trim();
			cardM = line.substring(2, 12).trim();
			accNo = line.substring(2, 12).trim();
			tranCode = line.substring(2, 12).trim();
			tranDesc = line.substring(2, 12).trim();
			amount = line.substring(2, 12).trim();
			tranDetails = line.substring(2, 12).trim();
			errorCode = line.substring(2, 12).trim();

			insertDB(tranDate, tranTime, tranSeq, jrnlNo, txCode, cardNum, cardM, accNo, tranCode, tranDesc, amount, tranDetails, errorCode);

		} catch(Exception ex){
			System.out.println("Error cardNumber : "+tranDate);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String tranDate, 	String tranTime, 	String tranSeq, 	String jrnlNo, 	String txCode, 	String cardNum, 	String cardM, 	String accNo, 	String tranCode, 	
			String tranDesc, 	String amount, 	String tranDetails, 	String errorCode)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP47K(TRANDATE,	TRANTIME,	TRANSEQNO,	JRNLNO,	CARDNUMBER,	CARDTYPE,	ACCNO,	TRANCODE,	TRANDESC,	AMOUNT,	TRANDETAILS,	ERRORCODE) "
				+ "values ('"+tranDate+"','"+tranTime+"','"+tranSeq+"','"+jrnlNo+"','"+txCode+"','"+cardNum+"','"+cardM+"','"+accNo+"','"+tranCode+"','"+tranDesc+"','"+amount+"','"+tranDetails+"','"+errorCode+"')";
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
