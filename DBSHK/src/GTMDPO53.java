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
public class GTMDPO53 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDPO53_001.TXT";
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
		String octopus = "";
		String actlang = "";
		String merchant = "";
		String doballow = "";
		String name = "";
		String homeNo = "";
		String mobileNo = "";
		String gender = "";
		
		try{
			seqNo = line.substring(0, 9).trim();
			octopus = line.substring(13, 32).trim();
			actlang = line.substring(34, 58).trim();
			merchant = line.substring(60, 64).trim();
			doballow = line.substring(67, 73).trim();
			name = line.substring(76,96).trim();
			homeNo = line.substring(98,114).trim();
			mobileNo = line.substring(98,114).trim();
			gender = line.substring(98,114).trim();
			
			insertDB(seqNo, octopus, actlang, merchant, doballow, name, homeNo, mobileNo, gender);
			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+seqNo+" - "+octopus+ actlang + merchant + doballow+ name + homeNo + mobileNo + gender);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String seqNo, String octopus, String actlang, String merchant, 
			String doballow, String name, String homeNo, String mobileNo, String gender){
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDPO53(SEQNO,OCTOPUS,ACTLANG,MERCHANT,DOBALLOW,NAME,HOMENO,GENDER) "
				+ "values ('"+seqNo+"','"+octopus+"','"+actlang+"','"+merchant+"','"+doballow+"','"+name+"','"+homeNo+"','"+gender+"')";
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
