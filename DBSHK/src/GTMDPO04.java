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
public class GTMDPO04 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDPO04_001.TXT";
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
		String action = "";
		String bankCode = "";
		String debtorAccountNo = "";
		String nameAAVSApplicant = "";
		String debtorRef = "";
		String topupVal = "";
		String octopus = "";
		String applicantID = "";
		
		try{
			action = line.substring(0, 9).trim();
			bankCode = line.substring(13, 32).trim();
			debtorAccountNo = line.substring(34, 58).trim();
			nameAAVSApplicant = line.substring(60, 64).trim();
			debtorRef = line.substring(67, 73).trim();
			topupVal = line.substring(76,96).trim();
			octopus = line.substring(98,114).trim();
			applicantID = line.substring(98,114).trim();
			
			insertDB(action, bankCode, debtorAccountNo, nameAAVSApplicant, debtorRef, topupVal, octopus, applicantID);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+action+" - "+bankCode+ debtorAccountNo + nameAAVSApplicant + debtorRef+ topupVal + octopus+ applicantID);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String action, String bankcode, String debtoraccountno, String nameaavs, 
			String debtorref, String topupval, String octopus, String applicantid){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDPO04(ACTION,BANKCODE,DEBTORACCOUNTNO,NAMEAAVSAPPLICANT,DEBTORREF,TOPUPVAL,OCTOPUS,APPLICANTID) "
				+ "values ('"+action+"','"+bankcode+"','"+debtoraccountno+"','"+nameaavs+"','"+debtorref+"','"+topupval+"','"+octopus+"','"+applicantid+"')";
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
