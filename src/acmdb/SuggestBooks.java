package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuggestBooks {
	protected static void run(String ISBN) throws IOException, SQLException{
		System.out.println("I suggest the following books for you:");
		String st = "select B.ISBN, B.title, B.price, B.publisher, B.subject, B.yearOfPublication, B.numberOfCopies, B.format, sum(O1.times) as salesForSimilarPersion"
				+ " from Book B, Ordering O1, Ordering O2 where O2.ISBN='" + ISBN + "' and O2.loginName = O1.loginName and O1.ISBN = B.ISBN "
				+ "Group by B.ISBN, B.title, B.price, O1.ISBN order by sum(O1.times) desc limit 10";
		ResultSet results = YwkDriver.executeSqlQuery(st);
		Lib.printResultSet(results);
	}

	protected static String JSPRun(Connector con, String ISBN) throws IOException, SQLException{
		String ans = "I suggest the following books for you:<BR>";
		System.out.println("I suggest the following books for you:");
		String st = "select B.ISBN, B.title, B.price, B.publisher, B.subject, B.yearOfPublication, B.numberOfCopies, B.format, sum(O1.times) as salesForSimilarPersion"
				+ " from Book B, Ordering O1, Ordering O2 where O2.ISBN='" + ISBN + "' and O2.loginName = O1.loginName and O1.ISBN = B.ISBN "
				+ "Group by B.ISBN, B.title, B.price, O1.ISBN order by sum(O1.times) desc limit 10";
		ResultSet results = YwkDriver.executeSqlQuery(con, st);
		ans += Lib.printJSPResultSet(results);
		return ans;
	}
}
