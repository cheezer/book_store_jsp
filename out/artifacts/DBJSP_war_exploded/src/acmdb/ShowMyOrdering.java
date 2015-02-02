package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ShowMyOrdering {
	protected static void run(String loginName) throws IOException, SQLException{
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT o.ISBN,b.title, o.times FROM Ordering o, Book b WHERE b.ISBN=o.ISBN AND o.loginName='"+loginName+"'");
	 		if (!results.next()) {
	 			System.out.println("\nYou misely have not ordered any Book!");
	 			return;
	 		}
	 		//YwkDriver.printTable(results, name, -1);
	 		Lib.printResultSet(results);
	}
	
	public static String runJsp(String loginName) throws SQLException{
		Connector con = new Connector();
 		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT o.ISBN,b.title, o.times FROM Ordering o, Book b WHERE b.ISBN=o.ISBN AND o.loginName='"+loginName+"'");
 		if (!results.next()) {
 			System.out.println("\nYou misely have not ordered any Book!");
 			return null;
 		}
 		System.out.println("\nYou have ordered following book:");
 		//YwkDriver.printTable(results, name, -1);
 		String ans = Lib.printJSPResultSet(results);
 		con.closeConnection();
 		return ans;
	}
}
