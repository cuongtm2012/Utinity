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
public class GTMDP52 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP52.TXT";
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
		String seq   =    "";
		String name   =    "";
		String description   =    "";
		String opr   =    "";
		String appTerm   =    "";
		String time   =    "";
		String remarks   =    "";
		String octopusInd   =    "";
		String aavsAC   =    "";
		String designedAC   =    "";
		
		try{
			cardNo = line.substring(2, 12).trim();
			type = line.substring(2, 12).trim();
			seq = line.substring(2, 12).trim();
			name = line.substring(2, 12).trim();
			description = line.substring(2, 12).trim();
			opr = line.substring(2, 12).trim();
			appTerm = line.substring(2, 12).trim();
			time = line.substring(2, 12).trim();
			remarks = line.substring(2, 12).trim();
			octopusInd = line.substring(2, 12).trim();
			aavsAC = line.substring(2, 12).trim();
			designedAC = line.substring(2, 12).trim();

			insertDB(cardNo, type, seq, name, description, opr, appTerm, time, remarks, octopusInd, aavsAC, designedAC);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+cardNo);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String cardNo, 	String type, 	String seq, 	String name, 	String description, 	
			String opr, 	String appTerm, 	String time, 	String remarks, 	String octopusInd, 	String aavsAC, 	String designedAC){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP52 (CARDNUMBER,	TYPE,	SEQ,	NAME,	DESCRIPTION,	OPR,	APPTERM,	TIME,	OCTOPUSIND,	AAVSAC,	DESIGNEDAC) "
				+ "values ('"+cardNo+"','"+type+"','"+seq+"','"+name+"','"+description+"','"+opr+"','"+appTerm+"','"+time+"','"+octopusInd+"','"+aavsAC+"','"+designedAC+"')";
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
