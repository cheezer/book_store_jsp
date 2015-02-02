package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class OrderBooks {
	protected static void run(String loginName) throws IOException, SQLException{
			System.out.println("\nPlease enter the ISBN of the book you want to order:");
	 		String ISBN = YwkDriver.readString(20);
	 		if (ISBN==null) return;
	 		
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
	 		if (!results.next()){
	 			System.out.println("\nNo such book in store!");
	 			return;
	 		}
	 		System.out.println("\nPlease enter the copies you want to order:");
	 		int copies = YwkDriver.readInt();
	 		if (results.getInt("numberOfCopies")<copies){
	 			System.out.println("\nThere is not enough copies of this book in store!");
	 			return;	   	 			
	 		}
	 		results.updateInt("numberOfCopies", results.getInt("numberOfCopies")-copies);
	 		results.updateRow();
			results = YwkDriver.executeSqlQuery("select * from Ordering o where o.ISBN = '" + ISBN + "' and o.loginName = '" + loginName + "';" );
			if (results.next())
			{
				results.updateInt("times", results.getInt("times") + copies);
				results.updateRow();
			}
			else
		 		YwkDriver.executeSqlStat("INSERT INTO Ordering(ISBN, loginName, times) VALUES ('"+ISBN+"','"+loginName+"'," + copies + ")");
	 		System.out.println("Successfully ordered " + copies + " copies book " + ISBN + " for you");
	 		SuggestBooks.run(ISBN);
	}

	public static String JSPRun(String loginName, String ISBN, int copies) throws IOException, SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Book b WHERE b.ISBN='"+ISBN+"'");
		if (!results.next()){
			System.out.println("\nNo such book in store!");
			con.closeConnection();
			return "No such book in store!";
		}
		if (results.getInt("numberOfCopies")<copies){
			System.out.println("\nThere is not enough copies of this book in store!");
			con.closeConnection();
			return "There is not enough copies of this book in store!";
		}
		results.updateInt("numberOfCopies", results.getInt("numberOfCopies")-copies);
		results.updateRow();
		//bug here! if already exist!
		results = YwkDriver.executeSqlQuery(con, "select * from Ordering o where o.ISBN = '" + ISBN + "' and o.loginName = '" + loginName + "';" );
		if (results.next())
		{
			results.updateInt("times", results.getInt("times") + copies);
			results.updateRow();
		}
		else
			YwkDriver.executeSqlStat(con, "INSERT INTO Ordering(ISBN, loginName, times) VALUES ('"+ISBN+"','"+loginName+"'," + copies + ")");
		System.out.println("Successfully ordered " + copies + " copies book " + ISBN + " for you");
		String ans = "Successfully ordered " + copies + " copies book " + ISBN + " for you. <BR>";
		ans += SuggestBooks.JSPRun(con, ISBN);
		con.closeConnection();
		return ans;
	}
}
