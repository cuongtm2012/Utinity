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
public class GTMDP76 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP76_001.TXT";
	            //String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP70_002.TXT";
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
		String cardNumber   =    "";
		String type   =    "";
		String seq   =    "";
		String cardHolderName   =    "";
		String san1   =    "";
		String san2   =    "";
		String siPan   =    "";
		String siSan1   =    "";
		String siSan2   =    "";
		String acPan   =    "";
		String acSan1   =    "";
		String acSan2   =    "";
		
		try{
			cardNumber = line.substring(2, 12).trim();
			type = line.substring(2, 12).trim();
			seq = line.substring(2, 12).trim();
			cardHolderName = line.substring(2, 12).trim();
			san1 = line.substring(2, 12).trim();
			san2 = line.substring(2, 12).trim();
			siPan = line.substring(2, 12).trim();
			siSan1 = line.substring(2, 12).trim();
			siSan2 = line.substring(2, 12).trim();
			acPan = line.substring(2, 12).trim();
			acSan1 = line.substring(2, 12).trim();
			acSan2 = line.substring(2, 12).trim();
	
			insertDB(cardNumber, type, seq, cardHolderName, san1, san2, siPan, siSan1, siSan2, acPan, acSan1, acSan2);
		
		} catch(Exception ex){
			System.out.println("Error cardNumber : "+cardNumber);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNumber, 	String type, 	String seq, 	String cardHolderName, 	String san1, 	String san2, 	String siPan, 	
			String siSan1, 	String siSan2, 	String acPan, 	String acSan1, 	String acSan2){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP76(CARDNUMBER,	TYPE,	SEQ,	CARDHOLDERNAME,	SAN1,	SAN2,	SIPAN,	SIPAN1,	SIPAN2,	ACPAN,	ACSAN1,	ACSAN2) "
				+ "values ('"+cardNumber+"','"+type+"','"+seq+"','"+cardHolderName+"','"+san1+"','"+san2+"','"+siPan+"','"
				+siSan1+"','"+siSan2+"','"+acPan+"','"+acSan1+"','"+acSan2+"')";
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
