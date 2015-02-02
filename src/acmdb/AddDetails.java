package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;


public class AddDetails {
	private static void addDetails(String ISBN) throws IOException, SQLException{
		Integer tmpInt;
		String tmpString;
		ResultSet results;
		while (true){
			System.out.println("\nWhat do you want to modify to the book of ISBN "+ISBN+"?");
			System.out.println("1. title.");
			System.out.println("2. yearOfPublication.");
			System.out.println("3. numberOfCopies.");
			System.out.println("4. publisher.");
			System.out.println("5. format (hardcover/softcover).");
			System.out.println("6. price.");
			System.out.println("7. subject.");
			System.out.println("8. add key word.");
			System.out.println("9. add author.");
			System.out.println("0. back");
			tmpInt = YwkDriver.readInt();
	   	 	if (tmpInt==null || tmpInt>10 || tmpInt<0) continue;
	   	 	if (tmpInt==0) return;
	   	 	
	   	 	System.out.println("\nWhat is the new value?");
	   	 	tmpString = YwkDriver.readString(100);
	   	 	
	   	 	try {
	   	 		results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	   	 		results.first();
	   	 		switch (tmpInt){
		   	 		case 1: 
		   	 			results.updateString("title", tmpString);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the title to "+tmpString+"!");
		   	 			break;
		   	 		case 2:
		   	 			int year;
		   	 			try{
		   	 				year = Integer.parseInt(tmpString);
		   	 			}catch (Exception e){
		   	 				System.out.println("\nInvalid year value!");
		   	 				break;
		   	 			}
		   	 			results.updateInt("yearOfPublication", year);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the publication year to "+year+"!");
		   	 			break;
		   	 		case 3:
		   	 			int copy;
		   	 			try{
		   	 				copy = Integer.parseInt(tmpString);
		   	 			}catch (Exception e){
		   	 				System.out.println("\nInvalid 'copies' value!");
		   	 				break;
		   	 			}
		   	 			if (copy < 0)
		   	 			{
		   	 				System.out.println("\nInvalid 'copies' value!");
		   	 				break;
		   	 			}
		   	 			results.updateInt("numberOfCopies", copy);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the number of copies to "+copy+"!");
		   	 			break;
		   	 		case 4:
		   	 			results.updateString("publisher", tmpString);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the publisher to "+tmpString+"!");
		   	 			break;
		   	 		case 5:
		   	 			if (!tmpString.equals("hardcover") && !tmpString.equals("softcover")){
		   	 				System.out.println("\nFormat can only be one of the value from 'hardcover' or 'softcover'");
		   	 				break;
		   	 			}
		   	 			results.updateString("format", tmpString);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the format to "+tmpString+"!");
		   	 			break;
		   	 		case 6:
		   	 			double price;
		   	 			try{
		   	 				price = Double.valueOf(tmpString);
		   	 			}catch (Exception e){
		   	 				System.out.println("\nInvalid 'price' value!");
		   	 				break;
		   	 			}
		   	 			if (price<0) {
		   	 				System.out.println("\nPrice cannot be smaller than 0!");
		   	 				break;
		   	 			}
		   	 			results.updateDouble("price", price);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the price to "+price+"!");
		   	 			break;
		   	 		case 7:
		   	 			results.updateString("subject", tmpString);
		   	 			results.updateRow();
		   	 			System.out.println("\nSuccessfully modifying the subject to "+tmpString+"!");
		   	 			break;
		   	 		case 8:
		   	 			ResultSet key = YwkDriver.executeSqlQuery("SELECT * FROM KeyWord k WHERE k.keyWord='"+tmpString+"'");
		   	 			if (!key.next()) YwkDriver.executeSqlStat("INSERT INTO KeyWord VALUES ('"+tmpString+"')");
		   	 			key = YwkDriver.executeSqlQuery("SELECT * FROM HaveKeyOf k WHERE k.keyWord='"+tmpString+"' AND " +
		   	 											"k.ISBN='"+ISBN+"'");
		   	 			if (key.next()){
		   	 				System.out.println("This key word has been added to the book previously!");
		   	 				break;
		   	 			}
		   	 			YwkDriver.executeSqlStat("INSERT INTO HaveKeyOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='"+
		   	 									 ISBN+"'), (SELECT keyWord FROM KeyWord WHERE keyWord='"+tmpString+"'))");
		   	 			System.out.println("Successfully add the keyWord "+tmpString+" to book!");
		   	 			break;
		   	 		case 9:
		   	 			results = YwkDriver.executeSqlQuery("SELECT * FROM Author k WHERE k.authorName='"+tmpString+"'");
		   	 			if (!results.next()) YwkDriver.executeSqlStat("INSERT INTO Author VALUES ('"+tmpString+"')");
		   	 			results = YwkDriver.executeSqlQuery("SELECT * FROM AuthorOf k WHERE k.authorName='"+tmpString+"' AND " +
	   	 											"k.ISBN='"+ISBN+"'");
		   	 			if (results.next()){
		   	 				System.out.println("This author has been added to the book previously!");
	   	 					break;
		   	 			}
		   	 			YwkDriver.executeSqlStat("INSERT INTO AuthorOf VALUES ((SELECT ISBN FROM Book WHERE ISBN='"+
	   	 									 	 ISBN+"'), (SELECT authorName FROM Author WHERE AuthorName='"+tmpString+"'))");
		   	 			System.out.println("Successfully add the Author "+tmpString+" to book!");
		   	 			break;
	   	 		
		   	 	}
	   	 	}
	   	 	catch (SQLException e)
	   	 	{
	   	 		System.out.println("You have an error in your input causing a sql error, please try again!");
	   	 		continue;
	   	 	}
		}
	}

	protected static void run() throws IOException, SQLException{
	 		String ISBN;
	 		System.out.println("\nWhat the ISBN of the book you want to add details to?");
	 		ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (!results.next()){
	 			System.out.println("\nThis ISBN has not been recorded in the store!");
	 			return;
	 		}
	 		addDetails(ISBN);
	}
	
	public static  String jspRun(String ISBN, String bookName, Integer yearOfPublication, Integer copies, String publisher, 
			String format, Double price, String subject, String authorList, String keywordList) throws IOException, SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
		if (!results.next()){
			con.closeConnection();
			return "This ISBN has not been recorded in store!";
		}
		
		if (!bookName.isEmpty()) results.updateString("title", bookName);
		if (yearOfPublication!=null) results.updateInt("yearOfPublication", yearOfPublication);
		if (copies!=null) results.updateInt("numberOfCopies", copies);
		if (!publisher.isEmpty()) results.updateString("publisher", publisher);
		if (!format.isEmpty()) results.updateString("format", format);
		if (price!=null) results.updateDouble("price", price);
		if (!subject.isEmpty()) results.updateString("subject", subject);
		results.updateRow();
		
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
		con.closeConnection();
		return "$";
	}
}
