package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RateOnFeedback {
	protected static void run(String loginName) throws IOException, SQLException{

	 		System.out.println("\nWhich feedback do you want to rate on? (type in feedback id)");
	 		Integer id = YwkDriver.readInt();
	 		if (id==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT f.feedbackFrom FROM Feedback f WHERE f.feedbackID='"+id+"'");
	 		if (!results.next()) {
	 			System.out.println("\nThere is no such a feedback!");
	 			return;
	 		}
	 		if (results.getString(1).equals(loginName)){
	 			System.out.println("\nYou cannot rate on your own feedback!");
	 			return;
	 		}
	 		
	 		System.out.println("\nHow do you rate on this feedback? (0:useless, 1:useful, 2:very useful)");
	 		Integer rate = YwkDriver.readInt();
	 		if (rate==null || rate<0 || rate>2){
	 			System.out.println("\nInvalid rating!");
	 			return;
	 		}
	 		
	 		YwkDriver.executeSqlStat("INSERT INTO FeedbackRate VALUES ((SELECT feedbackID FROM Feedback "+
	 								 "WHERE feedbackID="+id+"),(SELECT loginName FROM Customer WHERE loginName='"+
	 								 loginName+"'),"+rate+")");
	 		System.out.println("\nSuccessfully rate on this feedback!");
	}
	
	public static String jspRun(String loginName, int id, int rate) throws SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT f.feedbackFrom FROM Feedback f WHERE f.feedbackID='"+id+"'");
 		if (!results.next()) {
 			con.closeConnection();
 			return "There is no such a feedback!";
 		}
 		if (results.getString(1).equals(loginName)){
 			con.closeConnection();
 			return "You cannot rate on your own feedback!";
 		}
 		
 		YwkDriver.executeSqlStat(con, "INSERT INTO FeedbackRate VALUES ((SELECT feedbackID FROM Feedback "+
 								 "WHERE feedbackID="+id+"),(SELECT loginName FROM Customer WHERE loginName='"+
 								 loginName+"'),"+rate+")");
 		con.closeConnection();
 		return "YOU have successfully rated on this feedback!";
	}
}
