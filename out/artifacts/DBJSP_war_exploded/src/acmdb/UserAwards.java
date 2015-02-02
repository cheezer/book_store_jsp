package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAwards {
	protected static void run() throws IOException, SQLException
	{
		/*String st = "select p.userName, p.c1-n.c2 as trustScore from (select D1.userBeingDeclared as userName, Count(D1.declaringUser) as c1 "
				+ "from Declaration D1 where D1.comment = 1) As p, "
				+ "(select D2.userBeingDeclared as userName, Count(D2.declaringUser) as c2 from Declaration D2 where D2.comment = 0) As n "
				+ "where p.userName = n.userName";*/
		System.out.println("Please enter the number of the most trusted user you want to know:");
		int m = YwkDriver.readInt();
		String st = "select c.loginName, sum(2 * D.comment - 1) as trustScore from Customer c LEFT JOIN Declaration D on c.loginName = D.userBeingDeclared" +
				" group by c.loginName order by trustScore desc limit " + m + " ;";
		ResultSet results = YwkDriver.executeSqlQuery(st);
		Lib.printResultSet(results);
		System.out.println("Please enter the number of the most useful user you want to know:");
		m = YwkDriver.readInt();
		st = "select c.loginName, avg(rank) as usefulnessScore from Customer c LEFT JOIN (Feedback F1 NATURAL join FeedbackRate F2) ON c.loginName = F1.feedbackFrom group by c.loginName "
				+ " order by usefulnessScore desc limit " + m + ";";
		results = YwkDriver.executeSqlQuery(st);
		Lib.printResultSet(results);
	}

	public static String JSPRun(int m1, int m2) throws IOException, SQLException
	{
		String ans;
		Connector con = new Connector();
		String st = "select c.loginName, sum(2 * D.comment - 1) as trustScore from Customer c LEFT JOIN Declaration D on c.loginName = D.userBeingDeclared" +
				" group by c.loginName order by trustScore desc limit " + m1 + " ;";
		ResultSet results = YwkDriver.executeSqlQuery(con, st);
		ans = Lib.printJSPResultSet(results);
		st = "select c.loginName, avg(rank) as usefulnessScore from Customer c LEFT JOIN (Feedback F1 NATURAL join FeedbackRate F2) ON c.loginName = F1.feedbackFrom group by c.loginName "
				+ " order by usefulnessScore desc limit " + m2 + ";";
		results = YwkDriver.executeSqlQuery(con, st);
		ans += Lib.printJSPResultSet(results);
		con.closeConnection();
		return ans;
	}
}
