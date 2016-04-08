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
public class GTMDP34 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try {
	            String input = "C:\\Users\\e1067720\\Desktop\\HK_project\\4. Example\\text\\GTMDP34_120.TXT";
	            FileInputStream fis = new FileInputStream(new File(input));
	            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	            String line;
	            String accNo = null;
	            long accNum = 0;
	            while ((line = br.readLine()) != null) {
		                if(line.length() > 9){
		                	accNo = line.substring(20,26).trim();
		                	
		                	try{
		                		accNum = Long.parseLong(accNo);
		                	} catch(NumberFormatException ex){
		                		accNum = -1;
		                	}
		                	
		                }
		                if (line != null && !line.isEmpty() && accNum >= 0) {
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
		String ourdate = "";
		String ourtime = "";
		String ourSeqNo = "";
		String jetcoSeqNo = "";
		String accNo = "";
		String cardNumber = "";
		String tranCode = "";
		String tranDesc = "";
		String feeamount = "";
		String feeaccNo = "";
		String errorCode = "";
		
		try{
			ourdate = line.substring(1, 10).trim();
			ourtime = line.substring(10, 19).trim();
			ourSeqNo = line.substring(19, 25).trim();
			jetcoSeqNo = line.substring(26,32).trim();
			accNo = line.substring(33, 50).trim();
			cardNumber = line.substring(51,68).trim();
			tranCode = line.substring(69,72).trim();
			tranDesc = line.substring(74,85).trim();
			feeamount = line.substring(87,100).trim();
			feeaccNo = line.substring(100,117).trim();
			errorCode = line.substring(117,125).trim();
			
			insertDB(ourdate, ourtime, ourSeqNo, jetcoSeqNo, accNo, cardNumber, tranCode, tranDesc, feeamount, feeaccNo, errorCode);

			
		} catch(Exception ex){
			System.out.println("Error CardNo : "+ourdate+" - "+ourtime+ ourSeqNo + jetcoSeqNo 
					+ accNo+ cardNumber + tranCode + tranDesc + feeamount + feeaccNo + errorCode);
			System.out.println(ex.getMessage());
		}
		
	}

	public static void insertDB(String ourdate, 	String ourtime, 	String ourSeqNo, 	String jetcoSeqNo, 	String accNo, 	String cardNumber, 	
			String tranCode, 	String tranDesc, 	String feeamount, 	String feeaccNo, 	String errorCode)
	{
		Connection dbConnection = null;
		Statement statement = null;
		helpConfiguration help = new helpConfiguration();
		
		String sqlInsert = "Insert into GTMDP34(OURDATE,OURTIME,OURSEQNO,JETCOSEQNO,ACCNO,CARDNUMBER,TRANCODE,TRANDESC,AMOUNT,TRANFEEACCNO,ERRORCODE) "
				+ "values ('"+ourdate+"','"+ourtime+"','"+ourSeqNo+"','"+jetcoSeqNo+"','"+accNo+"','"+cardNumber+"','"+tranCode+"','"
				+	tranDesc+"','"+feeamount+"','"+feeaccNo+"','"+errorCode+"')";
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
