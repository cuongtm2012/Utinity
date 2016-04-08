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
public class GTMDP69 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP69.TXT";
	            //String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP69_001.TXT";
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
		String bin = "";
		String cardNo = "";
		String media = "";
		String seq = "";
		String documentNumber = "";
		String tin = "";
		String template = "";
		String exception = "";
		
		try{
			bin = line.substring(0, 9).trim();
			cardNo = line.substring(13, 32).trim();
			media = line.substring(34, 58).trim();
			seq = line.substring(60, 64).trim();
			documentNumber = line.substring(67, 73).trim();
			tin = line.substring(76,96).trim();
			template = line.substring(98,114).trim();
			exception = line.substring(98,114).trim();
			
			insertDB(bin, cardNo, media, seq, documentNumber, tin, template, exception);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+bin+" - "+cardNo+ media + seq + documentNumber+ tin + template+ exception);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String bin, String cardNo, String media, String seq, 
			String documentNumber, String tin, String template, String exception){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP69(BIN,CARDNO,MEDIA,SEQ,DOCUMENTNUMBER,TIN,TEMPLATE,EXCEPTION) "
				+ "values ('"+bin+"','"+cardNo+"','"+media+"','"+seq+"','"+documentNumber+"','"+tin+"','"+template+"','"+exception+"')";
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
