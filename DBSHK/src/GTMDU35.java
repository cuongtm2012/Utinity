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
public class GTMDU35 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDU35_001.TXT";
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
		String bin   =    "";
		String cardNo   =    "";
		String media   =    "";
		String seq   =    "";
		String face   =    "";
		String status   =    "";
		String ocl   =    "";
		String result   =    "";
		String remark   =    "";

		try{
			bin = line.substring(2, 12).trim();
			cardNo = line.substring(2, 12).trim();
			media = line.substring(2, 12).trim();
			seq = line.substring(2, 12).trim();
			face = line.substring(2, 12).trim();
			status = line.substring(2, 12).trim();
			ocl = line.substring(2, 12).trim();
			result = line.substring(2, 12).trim();
			remark = line.substring(2, 12).trim();

			insertDB(bin, cardNo, media, seq, face, status, ocl, result, remark);
			
		} catch(Exception ex){
			System.out.println("Error bin : "+bin);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String bin, 	String cardNo, 	String media, 	String seq, 	String face, 	
			String status, 	String ocl, 	String result, 	String remark){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDU35 (BIN,	CARDNO,	MEDIA,	SEQ,	FACE,	STATUS,	OCL,	RESULT,	REMARK) "
				+ "values ('"+bin+"','"+cardNo+"','"+media+"','"+seq+"','"+face+"','"+status+"','"+ocl+"','"+result+"','"+remark+"')";
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
