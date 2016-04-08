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
public class GTMDP44 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP44_105.TXT";
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
		String cardNumber   =    "";
		String accNumber   =    "";
		String debitAmt   =    "";
		String remarks   =    "";

		
		try{
			cardNumber = line.substring(2, 12).trim();
			accNumber = line.substring(2, 12).trim();
			debitAmt = line.substring(2, 12).trim();
			remarks = line.substring(2, 12).trim();
			
			insertDB(cardNumber, accNumber, debitAmt, remarks);
	
		} catch(Exception ex){
			System.out.println("Error cardNumber : "+cardNumber);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNumber, 	String accNumber, 	String debitAmt, 	String remarks)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP44(CARDNUMBER,	ACCNUMBER,	DEBITAMOUNT,	REMARKS) "
				+ "values ('"+cardNumber+"','"+accNumber+"','"+debitAmt+"','"+remarks+"')";
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
