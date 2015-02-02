package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Declare {
	protected static void run(String loginName) throws IOException, SQLException{
		System.out.println("\nWhich customer do you want to declare on? (type in loginName)");
	 		String tmpString = YwkDriver.readString(100);
	 		if (tmpString==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Customer c WHERE c.loginName='"+tmpString+"'");
	 		if (!results.next()){
	 			System.out.println("\nNo such Customer!");
	 			return;
	 		}
	 		
	 		System.out.println("\nHow do you declare him/her? (0:not-trusted  1:trusted)");
	 		Integer comment = YwkDriver.readInt();
	 		if (comment==null || comment<0 || comment>1) {
	 			System.out.println("\nIllegal comment number!");
	 			return;
	 		}
	 		
	 		YwkDriver.executeSqlStat("INSERT INTO Declaration VALUES ((SELECT loginName FROM Customer WHERE loginName='"
	 								 +loginName+"'), (SELECT loginName FROM Customer WHERE loginName='"
	 								 +tmpString+"'),"+ comment+")");
	 		System.out.println("Successfully declare on customer "+tmpString);
	}
	
	public static String jspRun(String loginName, String userName, int dec) throws SQLException{
		Connector con = new Connector();
 		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Customer c WHERE c.loginName='"+userName+"'");
 		if (!results.next()){
 			con.closeConnection();
 			return "No such Customer!";
 		}
 		
 		YwkDriver.executeSqlStat(con, "INSERT INTO Declaration VALUES ((SELECT loginName FROM Customer WHERE loginName='"
 								 +loginName+"'), (SELECT loginName FROM Customer WHERE loginName='"
 								 +userName+"'),"+ dec+")");
 		con.closeConnection();
 		return "Successfully declare on customer "+userName;		
	}
}
