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
public class GTMDP51 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP51_151.TXT";
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
		String cardNo = "";
		String type = "";
		String transaction = "";
		String accountNum = "";
		String remarks = "";
		String statusChange = "";
		String newStatus = "";
		String octopus = "";
		
		try{
			cardNo = line.substring(0, 18).trim();
			type = line.substring(18, 21).trim();
			transaction = line.substring(21, 48).trim();
			accountNum = line.substring(48, 72).trim();
			remarks = line.substring(72, 120).trim();
			octopus = line.substring(120,130).trim();
			
			insertDB(cardNo, type, transaction, accountNum, remarks, statusChange, newStatus, octopus);
			
		} catch(Exception ex){
			System.out.println("Error CardNo"+cardNo+" - "+type+ transaction+ accountNum + remarks + octopus);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNo, String type, String transaction, String accountNum, 
			String remarks, String statusChange, String newStatus, String octopus){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		statusChange = "";
		newStatus = "";
		
		String sqlInsert = "Insert into GTMDP51 (CARDNO,TYPE,TRANSACTION,ACCOUNTNUMBER,REMARKS,STATUSCHANGE,NEWSTATUS,OCTOPUS) "
				+ "values ('"+cardNo+"','"+type+"','"+transaction+"','"+accountNum+"','"+remarks+"','"+statusChange+"','"+newStatus+"','"+octopus+"')";
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
