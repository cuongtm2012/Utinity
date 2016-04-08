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
public class GTMDPO02 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDPO02_001.TXT";
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
		String debtorAccountNo = "";
		String applicantName = "";
		String debtorRef = "";
		String octopus = "";
		String tranSeq = "";
		String tranDate = "";
		String tranAmount = "";
		
		try{
			debtorAccountNo = line.substring(0, 9).trim();
			applicantName = line.substring(13, 32).trim();
			debtorRef = line.substring(34, 58).trim();
			octopus = line.substring(60, 64).trim();
			tranSeq = line.substring(76,96).trim();
			tranDate = line.substring(98,114).trim();
			tranAmount = line.substring(98,114).trim();
			
			insertDB(debtorAccountNo, applicantName, debtorRef, octopus, tranSeq, tranDate, tranAmount);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+debtorAccountNo+" - "+applicantName+ debtorRef + octopus + tranSeq + tranDate + tranAmount);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String debtorAccountNo, String applicantName, String debtorRef, String octopus, 
			String tranSeq, String tranDate, String tranAmount){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDPO02(DEBTORACCOUNTNO,APPLICANTNAME,DEBTORREF,OCTOPUS,TRANSEQ,TRANDATE,TRANAMOUNT) "
				+ "values ('"+debtorAccountNo+"','"+applicantName+"','"+debtorRef+"','"+octopus+"','"+tranSeq+"','"+tranDate+"','"+tranAmount+"')";
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
