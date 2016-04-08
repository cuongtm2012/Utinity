import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import util.helpConfiguration;

/**
 * 
 */

/**
 * @author e1067720
 *
 */
public class GTMDPO54 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP17_001.TXT";
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
		String seqNo = "";
		String docNo = "";
		String newOctopus = "";
		String oldOctopus = "";
		String DOB = "";
		String channel = "";
		String merchant = "";
		String updateTime = "";
		String updateBy = "";
		
		try{
			seqNo = line.substring(0, 9).trim();
			docNo = line.substring(13, 32).trim();
			newOctopus = line.substring(34, 58).trim();
			oldOctopus = line.substring(60, 64).trim();
			DOB = line.substring(67, 73).trim();
			channel = line.substring(76,96).trim();
			merchant = line.substring(98,114).trim();
			updateTime = line.substring(98,114).trim();
			updateBy = line.substring(98,114).trim();
			
			insertDB(seqNo, docNo, newOctopus, oldOctopus, DOB, channel, merchant, updateTime, updateBy);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+seqNo+" - "+docNo+ newOctopus + oldOctopus + DOB+ channel + merchant + updateTime + updateBy);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String seqNo, String docNo, String newOctopus, String oldOctopus, String DOB, 
			String channel, String merchant, String updateTime, String updateBy){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDPO54(SEQNO,DOCNO,NEWOCTOPUS,OLDOCTOPUS,DOB,CHANNEL,UPDATETIME,UPDATEBY) "
				+ "values ('"+seqNo+"','"+docNo+"','"+newOctopus+"','"+oldOctopus+"','"+DOB+"','"+channel+"','"+merchant+"','"+updateTime+"','"+updateBy+"')";
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
