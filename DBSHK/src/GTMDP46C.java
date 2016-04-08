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
public class GTMDP46C {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP46C_001.TXT";
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
		String cardNo   =    "";
		String txAc   =    "";
		String postDate   =    "";
		String txTime   =    "";
		String txCode   =    "";
		String txAmt   =    "";
		String txFee   =    "";

		
		try{
			cardNo = line.substring(2, 12).trim();
			txAc = line.substring(2, 12).trim();
			postDate = line.substring(2, 12).trim();
			txTime = line.substring(2, 12).trim();
			txCode = line.substring(2, 12).trim();
			txAmt = line.substring(2, 12).trim();
			txFee = line.substring(2, 12).trim();

			insertDB(cardNo, txAc, postDate, txTime, txCode, txAmt, txFee);


	
		} catch(Exception ex){
			System.out.println("Error cardNumber : "+cardNo);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNo, 	String txAc, 	String postDate, 	String txTime, 	String txCode, 	String txAmt, 	String txFee)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP46C(CARDNO,	TXACC,	POSTDATE,	TXTIME,	TXCODE,	TXAMT,	TXFEE) "
				+ "values ('"+cardNo+"','"+txAc+"','"+postDate+"','"+txTime+"','"+txCode+"','"+txAmt+"','"+txFee+"')";
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
