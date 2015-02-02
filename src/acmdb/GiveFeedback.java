package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GiveFeedback {
	protected static void run(String loginName) throws IOException, SQLException{
	 		System.out.println("\nWhat is the ISBN of the book you want to feedback?");
	 		String ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (!results.next()) {
	 			System.out.println("\nNo such book in the store!");
	 			return;
	 		}
	 		results = YwkDriver.executeSqlQuery("SELECT * FROM Feedback f WHERE f.feedbackTo='"+ISBN+"' AND f.feedbackFrom='"+loginName+"'");
	 		if (results.next()){
	 			System.out.println("\nYou have already feedback to this book!");
	 			return;
	 		}
	 		
	 		System.out.println("\nWhat score will you give to this book? (0=terrible, 10=masterpiece)");
	 		Integer score = YwkDriver.readInt();
	 		if (score==null || score<0 || score>10){
	 			System.out.println("\nInvalid score number!");
	 			return;
	 		}	   	 		
	 		System.out.println("\nPlease enter some more comments to this book: (at most 255 characters)");
	 		String msg = YwkDriver.readString(255);
	 		/*YwkDriver.executeSqlStat("INSERT INTO Feedback VALUES ("+(++YwkDriver.totFeedback)+
	 				",(SELECT loginName from Customer WHERE loginName='"+loginName+"'),"+
	 				"(SELECT ISBN from Book WHERE ISBN='"+ISBN+"'),"+score+",'"+msg+"')");*/
	 		YwkDriver.executeSqlStat("INSERT INTO Feedback(feedbackFrom, feedbackTo, score, text) VALUES ('"+
				loginName + "','" + ISBN + "',"+score+",'"+msg+"')");
	 		System.out.println("\nSuccessfully send your feedback!");
	}
	
	public static String jspRun(String loginName, String ISBN, int score, String comment) throws SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
		if (!results.next()) {
 			con.closeConnection();
 			return "Error! No such book in the store!\n";
 		}
	
 		results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Feedback f WHERE f.feedbackTo='"+ISBN+"' AND f.feedbackFrom='"+loginName+"'");
 		
 		if (results.next()) {
 			con.closeConnection();
 			return "Error! You have already feedback to this book!\n";
 		}

 		if (score<0 || score>10) {
 			con.closeConnection();
 			return "Error! Score should be integer between 0 and 10!\n";
 		}

 		YwkDriver.executeSqlStat(con, "INSERT INTO Feedback(feedbackFrom, feedbackTo, score, text) VALUES ('"+
			loginName + "','" + ISBN + "',"+score+",'"+comment+"')");

 		con.closeConnection();
 		return "You have successfully submitted a feedback to this book!\n";
	}
	
}
