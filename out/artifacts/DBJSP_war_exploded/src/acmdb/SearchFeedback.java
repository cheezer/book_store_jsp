package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SearchFeedback {
	protected static void run() throws IOException, SQLException{
	 		System.out.println("\nWhose feedbacks do you want to look? (Type ISBN)");
	 		String ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (!results.next()) {
	 			System.out.println("\nNo such book in the store!");
	 			return;
	 		}

	 		System.out.println("\nHow many useful feedback do you want to look? (type -1 to scan for all)");
	 		Integer num = YwkDriver.readInt();
	 		if (num==null || num<-1){
	 			System.out.println("\nInvalid number of feedbacks!");
	 			return;
	 		}
	 		
	 		if (num<0) {
	 			//results = YwkDriver.executeSqlQuery("SELECT f.feedbackID, f.feedbackTo , f.feedbackFrom ,f.score ,f.text FROM Feedback f WHERE f.feedbackTo='"+ISBN+"'");
	 			results = YwkDriver.executeSqlQuery("SELECT f.feedbackID, f.feedbackTo , f.feedbackFrom ,f.score ,f.text, AVG(r.rank) AS usefulness " +
						 "FROM Feedback f LEFT JOIN FeedbackRate r " +
						 "ON r.feedbackID=f.feedbackID WHERE f.feedbackTo='"+ISBN+"'" +
						 "Group BY f.feedbackID;");
	   	 		if (!results.next()){
	   	 			System.out.println("\nNo feedback to this book currently!");
	   	 			return;
	   	 		}
	   	 		System.out.println("\nThe comment of this book is as follows:\n");
	   	 		//YwkDriver.printTable(results, name, num);
	   	 		Lib.printResultSet(results);
	 		}
	 		else {
	 			results = YwkDriver.executeSqlQuery("SELECT f.feedbackID, f.feedbackTo , f.feedbackFrom ,f.score ,f.text, AVG(r.rank) AS usefulness " +
	 												 "FROM Feedback f left join FeedbackRate r " +
	 												 "ON r.feedbackID=f.feedbackID WHERE f.feedbackTo='"+ISBN+"' " +
	 												 "Group BY f.feedbackID limit " + num +";");
	   	 		if (!results.next()){
	   	 			System.out.println("\nNo feedback to this book currently!");
	   	 			return;
	   	 		}
	   	 		System.out.println("\nThe comment of this book is as follows:\n");
	   	 		//YwkDriver.printTable(results, name, num);
	   	 		Lib.printResultSet(results);
	 	}
	}	
	
	public static String jspRun(String ISBN, int number) throws SQLException{
 		Connector con = new Connector();
 		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
 		if (!results.next()) {
 			con.closeConnection();
 			return "No such book in the store!";
 		}

 		results = YwkDriver.executeSqlQuery(con, "SELECT f.feedbackID, f.feedbackTo , f.feedbackFrom ,f.score ,f.text, AVG(r.rank) AS usefulness " +
 												 "FROM Feedback f left join FeedbackRate r " +
 												 "ON r.feedbackID=f.feedbackID WHERE f.feedbackTo='"+ISBN+"' " +
 												 "Group BY f.feedbackID limit " + number +";");
   	
   	 	if (!results.next()) {
   	 		con.closeConnection();
   	 		return "No feedback to this book currently!";
   	 	}
   	 	
   	 	String ans = "The comment of this book is as follows: <BR>" + Lib.printJSPResultSet(results);
   	 	con.closeConnection();
   	 	return ans;
 	}
}
