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
public class GTMDPO06 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDPO06_001.TXT";
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
		String recordID = "";
		String reportTime = "";
		String octopus = "";
		String refNo = "";
		String callerPhoneNo = "";
		String status = "";
		String user = "";
		
		try{
			recordID = line.substring(0, 9).trim();
			reportTime = line.substring(13, 32).trim();
			octopus = line.substring(34, 58).trim();
			refNo = line.substring(60, 64).trim();
			callerPhoneNo = line.substring(67, 73).trim();
			status = line.substring(76,96).trim();
			user = line.substring(98,114).trim();
			
			insertDB(recordID, reportTime, octopus, refNo, callerPhoneNo, status, user);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+recordID+" - "+reportTime+ octopus + refNo + callerPhoneNo+ status + user);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String recordID, String reportTime, String octopus, String refNo, 
			String callerPhoneNo, String status, String user){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDPO06(RECORDID,REPORTTIME,OCTOPUS,REFNO,CALLERPHONENO,STATUS,USER) "
				+ "values ('"+recordID+"','"+reportTime+"','"+octopus+"','"+refNo+"','"+callerPhoneNo+"','"+status+"','"+user+"')";
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
