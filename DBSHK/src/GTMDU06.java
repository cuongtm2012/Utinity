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
public class GTMDU06 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDU06_141.TXT";
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
		String pbsNoStatus   =    "";
		String tapeSeqNo   =    "";
		String cardNumber   =    "";
		String cardHolderName   =    "";
		String remarks   =    "";
		String preGen   =    "";

		try{
			pbsNoStatus = line.substring(2, 12).trim();
			tapeSeqNo = line.substring(2, 12).trim();
			cardNumber = line.substring(2, 12).trim();
			cardHolderName = line.substring(2, 12).trim();
			remarks = line.substring(2, 12).trim();
			preGen = line.substring(2, 12).trim();

			insertDB(pbsNoStatus, tapeSeqNo, cardNumber, cardHolderName, remarks, preGen);
			
		} catch(Exception ex){
			System.out.println("Error PBS NO STATUS : "+pbsNoStatus);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String pbsNoStatus, 	String tapeSeqNo, 	String cardNumber, 	String cardHolderName, 	String remarks, 	String preGen){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDU06 (NOSTATUS,	TAPESEQNO,	CARDNUMBER,	CARDHOLDERNAME,	REMARKS,	PREGENERATED) "
				+ "values ('"+pbsNoStatus+"','"+tapeSeqNo+"','"+cardNumber+"','"+cardHolderName+"','"+remarks+"','"+preGen+"')";
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
