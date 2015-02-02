package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;


public class NewCopies {

	protected static void run() throws IOException, SQLException{
	 		String ISBN;
	 		Integer num;
	 		System.out.println("\nWhat's the ISBN of this Book?");
	 		ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (!results.next()){
	 			System.out.println("\nNo such book recorded in store!");
	 			return;
	 		}
	 	
	 		System.out.println("\nHow many new copies do you want to add?");
		num = YwkDriver.readInt();
   	 	if (num==null || num<0) return;
   	 	results.updateInt(4, num+results.getInt(4));
   	 	results.updateRow();
   	 	System.out.println("\n"+num+" copies has been added to this book!");
	}

	public static String JSPRun(String ISBN, Integer num) throws IOException, SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
		if (!results.next()){
			System.out.println("\nNo such book recorded in store!");
			con.closeConnection();
			return "No such book recorded in store!";
		}
		results.updateInt(4, num+results.getInt(4));
		results.updateRow();
		System.out.println("\n"+num+" copies has been added to this book!");
		con.closeConnection();
		return "$";
	}
}
