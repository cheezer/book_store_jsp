package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerMod {
	private static void clear() throws SQLException
	{
		YwkDriver.executeSqlStat("Delete from FeedbackRate;");
		YwkDriver.executeSqlStat("Delete from Ordering;");
		YwkDriver.executeSqlStat("Delete from Feedback;");
		YwkDriver.executeSqlStat("Delete from HaveKeyOf;");
		YwkDriver.executeSqlStat("Delete from KeyWord;");
		YwkDriver.executeSqlStat("Delete from Declaration;");
		YwkDriver.executeSqlStat("Delete from AuthorOf;");
		YwkDriver.executeSqlStat("Delete from Author;");
		YwkDriver.executeSqlStat("Delete from Customer;");
		YwkDriver.executeSqlStat("Delete from Book;");
	}
	
	private static void serve() throws IOException, SQLException{
		Integer tmpInt;
		while (true){
			System.out.println("\nWhat do you want to do, my lord!");
			System.out.println("1. Record a new Book.");
			System.out.println("2. Arrive copies to a book.");
			System.out.println("3. Add detailed description to a book.");
			System.out.println("4. Get statistics of this semester.");
			System.out.println("5. Get the most trusted users and the most useful users.");
			System.out.println("0. back.");
			tmpInt = YwkDriver.readInt();
			try{
		   	 	if (tmpInt==null || tmpInt>5 || tmpInt<0) continue;
		   	 	if (tmpInt==0) return;
		   	 	else if (tmpInt == 4)
		   	 	{
		   	 		Statistics.run();
		   	 	}
		   	 	else if (tmpInt == 5)
		   	 	{
		   	 		UserAwards.run();
		   	 	}
		   	 	else if (tmpInt==1){
		   	 		NewBook.run();
		   	 	}
		   	 	else if (tmpInt==2){
		   	 		NewCopies.run();
		   	 	}
		   	 	else if (tmpInt==3){
		   	 		AddDetails.run();
		   	 	}
			}
			catch (SQLException e)
			{
				System.out.println("Your Operation has caused an SQL error. Try again!");
			}
		}
	}
	
	protected static void run() throws IOException, SQLException{
		//clear();
		serve();
	}
}
