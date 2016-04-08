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
public class GTMDP53 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP53_160.TXT";
	            //String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP52_152.TXT";
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
		String cardNo   =    "";
		String type   =    "";
		String inpOpr   =    "";
		String inpTerm   =    "";
		String impTime   =    "";
		String appOpr   =    "";
		String appTerm   =    "";
		String appTime   =    "";
		String name   =    "";
		String action   =    "";

		try{
			cardNo = line.substring(2, 12).trim();
			type = line.substring(2, 12).trim();
			inpOpr = line.substring(2, 12).trim();
			inpTerm = line.substring(2, 12).trim();
			impTime = line.substring(2, 12).trim();
			appOpr = line.substring(2, 12).trim();
			appTerm = line.substring(2, 12).trim();
			appTime = line.substring(2, 12).trim();
			name = line.substring(2, 12).trim();
			action = line.substring(2, 12).trim();

			insertDB(cardNo, type, inpOpr, inpTerm, impTime, appOpr, appTerm, appTime, name, action);		
		} catch(Exception ex){
			System.out.println("Error CardNo : "+cardNo);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNo, 	String type, 	String inpOpr, 	String inpTerm, 	String impTime, 	String appOpr, 	
			String appTerm, 	String appTime, 	String name, 	String action){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP53 (CARDNO,	TYPE,	SEQNO,	INPOPR,	INPTERM,	APPOPR,	APPTERM,	NAME,	ACTION) "
				+ "values ('"+cardNo+"','"+type+"','"+inpOpr+"','"+inpTerm+"','"+impTime+"','"+appOpr+"','"+appTerm+"','"+appTime+"','"+name+"','"+action+"')";
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
