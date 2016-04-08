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
public class GTMDP38 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP38_130.TXT";
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
		String type   =    "";
		String accNumber   =    "";
		String date   =    "";
		String time   =    "";
		String seqNo   =    "";
		String jetcoSeq   =    "";
		String error   =    "";
		String tx   =    "";
		String amount   =    "";

		
		try{
			cardNo = line.substring(0, 19).trim();
			type = line.substring(20, 23).trim();
			accNumber = line.substring(23, 42).trim();
			date = line.substring(46, 56).trim();
			time = line.substring(59, 67).trim();
			seqNo = line.substring(70, 77).trim();
			jetcoSeq = line.substring(80, 88).trim();
			error = line.substring(90, 95).trim();
			tx = line.substring(96, 105).trim();
			amount = line.substring(108, 125).trim();
			
			insertDB(cardNo, type, accNumber, date, time, seqNo, jetcoSeq, error, tx, amount);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+cardNo);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNo, 	String type, 	String accNumber, 	String date, 	String time, 	String seqNo, 	
			String jetcoSeq, 	String error, 	String tx, 	String amount)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP38(CARDNO,	TYPE,	ACCOUNTNUMBER,	DATE38,	TIME38,	SEQNO,	JETCOSEQ,	ERROR,	TX,	AMOUNT) "
				+ "values ('"+cardNo+"','"+type+"','"+accNumber+"','"+date+"','"+time+"','"+seqNo+"','"+jetcoSeq+"','"+ error+"','"+tx+"','"+amount+"')";
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
