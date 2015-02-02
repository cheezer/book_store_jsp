package acmdb;

import java.io.IOException;
import java.sql.SQLException;

public class Statistics {
	protected static void run() throws IOException, SQLException{
		System.out.println("Please enter the number of the most popular books you want to know:");
		int m = YwkDriver.readInt();
		String st = "select B.ISBN, B.title, B.price, B.publisher, B.subject, B.yearOfPublication, B.numberOfCopies, B.format, sum(O.times) as salesAmount from"
				+ " Book B, Ordering O where B.ISBN = O.ISBN group by B.ISBN order by sum(O.times) desc limit " + m + ";";
		Lib.printResultSet(YwkDriver.executeSqlQuery(st));
		System.out.println("Please enter the number of the most popular author you want to know:");
		m = YwkDriver.readInt();
		st = "select ao.authorName, sum(O.times) as sales from AuthorOf ao, Ordering O where O.ISBN = ao.ISBN group by(ao.authorName) order by sum(O.times) desc limit " + m + ";";
		Lib.printResultSet(YwkDriver.executeSqlQuery(st));
		System.out.println("Please enter the number of the most popular publisher you want to know:");
		m = YwkDriver.readInt();
		st = "select B.publisher, sum(O.times) as sales from Book B, Ordering O where O.ISBN = B.ISBN group by(B.publisher) order by sum(O.times) desc limit " + m + ";";
		Lib.printResultSet(YwkDriver.executeSqlQuery(st));
	}

	public static String JSPRun(int m1, int m2, int m3) throws IOException, SQLException{
		Connector con = new Connector();
		String st = "select B.ISBN, B.title, B.price, B.publisher, B.subject, B.yearOfPublication, B.numberOfCopies, B.format, sum(O.times) as salesAmount from"
				+ " Book B, Ordering O where B.ISBN = O.ISBN group by B.ISBN order by sum(O.times) desc limit " + m1 + ";";
		String ans;
		ans = "The top " + m1 + " popular books:<BR>";
		ans += Lib.printJSPResultSet(YwkDriver.executeSqlQuery(con, st));
		st = "select ao.authorName, sum(O.times) as sales from AuthorOf ao, Ordering O where O.ISBN = ao.ISBN group by(ao.authorName) order by sum(O.times) desc limit " + m2 + ";";
		ans += "The top " + m1 + " popular author:<BR>";
		ans += Lib.printJSPResultSet(YwkDriver.executeSqlQuery(con, st));
		ans += "The top " + m1 + " popular publisher:<BR>";
		st = "select B.publisher, sum(O.times) as sales from Book B, Ordering O where O.ISBN = B.ISBN group by(B.publisher) order by sum(O.times) desc limit " + m3 + ";";
		ans += Lib.printJSPResultSet(YwkDriver.executeSqlQuery(con, st));
		con.closeConnection();
		return ans;
	}
}
