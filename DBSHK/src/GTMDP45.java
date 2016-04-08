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
public class GTMDP45 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP45_104.TXT";
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
		String accNumber   =    "";
		String debitAmt   =    "";
		String branch   =    "";
		String seqNo   =    "";
		String remarks   =    "";
		
		try{
			accNumber = line.substring(2, 12).trim();
			debitAmt = line.substring(2, 12).trim();
			branch = line.substring(2, 12).trim();
			seqNo = line.substring(2, 12).trim();
			remarks = line.substring(2, 12).trim();
			
			insertDB(accNumber, debitAmt, branch, seqNo, remarks);
	
		} catch(Exception ex){
			System.out.println("Error cardNumber : "+accNumber);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String accNumber, 	String debitAmt, 	String branch, 	String seqNo, 	String remarks)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP44(ACCNUMBER,	DEBITAMOUNT,	BRANCH,	SEQNO,	REMARKS) "
				+ "values ('"+accNumber+"','"+debitAmt+"','"+branch+"','"+seqNo+"','"+remarks+"')";
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
