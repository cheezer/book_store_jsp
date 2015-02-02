package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewBook {
	protected static void run() throws IOException, SQLException{
	 		String bookName, ISBN;
	 		System.out.println("\nWhat's the ISBN of new Book?");
	 		ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (results.next()){
	 			System.out.println("\nThis book has been recorded in store!");
	 			return;
	 		}
	 		
	 		System.out.println("\nWhat's the title of new Book?");
	 		bookName = YwkDriver.readString(100);
	 		if (bookName==null) return;
	 		System.out.println("\nWhat year is it published?");
	 		int yearOfPublication = YwkDriver.readInt();
	 		System.out.println("\nHow many copies have arrived?");
	 		int copies = YwkDriver.readInt();
	 		System.out.println("\nWhat is its publisher?");
	 		String publisher = YwkDriver.readString(100);
	 		System.out.println("\nWhat is its format?");
	 		String format = YwkDriver.readString(100);
	 		System.out.println("\nWhat is its price?");
	 		double price = YwkDriver.readDouble();
	 		System.out.println("\nWhat is its subject?");
	 		String subject = YwkDriver.readString(100);
	 		
	 		YwkDriver.executeSqlStat("INSERT INTO Book VALUES ('"+ISBN+"','"+bookName+"'," + yearOfPublication + "," + copies + ",'" + publisher + "','" + format + "'," + price + ",'" + subject + "');");
	 		System.out.println("\nHow many authors does it have?");
	 		int authorNum = YwkDriver.readInt();
	 		for (int i = 1; i <= authorNum; ++i)
	 		{
	 			System.out.println("\nWhat is its " + i + "th author?");
	 			String author = YwkDriver.readString(100);

   	 			results = YwkDriver.executeSqlQuery("SELECT * FROM Author k WHERE k.authorName='"+author+"'");
   	 			if (!results.next()) YwkDriver.executeSqlStat("INSERT INTO Author VALUES ('"+author+"')");
   	 			YwkDriver.executeSqlStat("INSERT INTO AuthorOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='"+
	 									 	 ISBN+"'), (SELECT authorName FROM Author WHERE AuthorName='"+author+"'))");
	 		}
	 		System.out.println("\nHow many keywords does it have?");
	 		int keywordNum = YwkDriver.readInt();
	 		for (int i = 1; i <= keywordNum; ++i)
	 		{
	 			System.out.println("\nWhat is its " + i + "th keyword?");
	 			String keyword = YwkDriver.readString(100);
   	 			ResultSet key = YwkDriver.executeSqlQuery("SELECT * FROM KeyWord k WHERE k.keyWord='"+keyword+"'");
   	 			if (!key.next()) YwkDriver.executeSqlStat("INSERT INTO KeyWord VALUES ('"+keyword+"')");
   	 			YwkDriver.executeSqlStat("INSERT INTO HaveKeyOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='"+
   	 									 ISBN+"'), (SELECT keyWord FROM KeyWord WHERE keyWord='"+keyword+"'))");
	 		}
	 		System.out.println("\nNew book has been added!");
	}

	public static String jspRun(String ISBN, String bookName, int yearOfPublication, int copies, String publisher, String format, double price, String subject, String authorList, String keywordList) throws IOException, SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
		if (results.next()){
			System.out.println("\nThis book has been recorded in store!");
			con.closeConnection();
			return "This book has been recorded in store!";
		}
		YwkDriver.executeSqlStat(con, "INSERT INTO Book VALUES ('"+ISBN+"','"+bookName+"'," + yearOfPublication + "," + copies + ",'" + publisher + "','" + format + "'," + price + ",'" + subject + "');");
		int pos = 0, last = 0;
		while (last < authorList.length()) {
			pos = authorList.indexOf(',', last);
			if (pos == -1)
				pos = authorList.length();
			String author = authorList.substring(last, pos);
			last = pos + 1;
			results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Author k WHERE k.authorName='" + author + "'");
			if (!results.next()) YwkDriver.executeSqlStat(con, "INSERT INTO Author VALUES ('" + author + "')");
			YwkDriver.executeSqlStat(con, "INSERT INTO AuthorOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='" +
					ISBN + "'), (SELECT authorName FROM Author WHERE AuthorName='" + author + "'))");
		}
		last = 0;
		while (last < keywordList.length())
		{
			pos = authorList.indexOf(',', last);
			if (pos == -1)
				pos = authorList.length();
			String keyword = keywordList.substring(last, pos);
			last = pos + 1;
			ResultSet key = YwkDriver.executeSqlQuery(con, "SELECT * FROM KeyWord k WHERE k.keyWord='"+keyword+"'");
			if (!key.next()) YwkDriver.executeSqlStat(con, "INSERT INTO KeyWord VALUES ('"+keyword+"')");
			YwkDriver.executeSqlStat(con, "INSERT INTO HaveKeyOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='"+
					ISBN+"'), (SELECT keyWord FROM KeyWord WHERE keyWord='"+keyword+"'))");
		}
		System.out.println("\nNew book has been added!");
		con.closeConnection();
		return "$";
	}
}
