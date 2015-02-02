package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMod {
	private static String loginName;
	private static String password;
	private static ResultSet results;
	
	private static boolean login() throws IOException, SQLException{
		Integer tmpInt = 0;
		while (true){
			System.out.println("\nplease enter your choice:");
			System.out.println("1. register.");
			System.out.println("2. login.");
			System.out.println("3. back.");
			tmpInt = YwkDriver.readInt();
	   	 	if (tmpInt==null || tmpInt>3 || tmpInt<1) continue;
	   	 	
	   	 	if (tmpInt==3) return false;
	   	 	else if (tmpInt==1) {
	   	 		UserRegister.run(); 
	   	 	}
	   	 	else if (tmpInt==2){
	   	 		loginName = UserLogin.run();
	   	 		return true;
	   	 	}
   	 	}
	}

	public static String JSPLogin(String loginName, String password) throws IOException, SQLException
	{
		loginName = UserLogin.JSPRun(loginName, password);
		if (loginName != null) return "$";
		else return "Fail to match your LoginName/Password!";
	}
	
	private static void serve() throws IOException, SQLException{
		Integer tmpInt;
		while (true){
			System.out.println("\nWhat do you want to do, my lord!");
			System.out.println("1. Order books.");
			System.out.println("2. Look my ordering list.");
			System.out.println("3. Give feedback to one book.");
			System.out.println("4. Show top n useful feedback of one book.");
			System.out.println("5. Rate on other's feedback.");
			System.out.println("6. Declare on other customer.");
			System.out.println("7. Search for Books.");
			System.out.println("8. Found out degrees of separation");
			System.out.println("0. Logout.");
			tmpInt = YwkDriver.readInt();
	   	 	if (tmpInt==null || tmpInt>8 || tmpInt<0) continue;
	   	 	try{
		   	 	if (tmpInt==0) return;
				else if (tmpInt==8)
				{
					DegreesOfSeparation.run();
				}
		   	 	else if (tmpInt==7){
		   	 		SearchBook.run(loginName);
		   	 	}
		   	 	else if (tmpInt==6){
		   	 		Declare.run(loginName);
		   	 	}
		   	 	else if (tmpInt==5){
		   	 		RateOnFeedback.run(loginName);
		   	 	}
		   	 	else if (tmpInt==4){
		   	 		SearchFeedback.run();
		   	 	}
		   	 	else if (tmpInt==3){
		   	 		GiveFeedback.run(loginName);
		   	 	}
		   	 	else if (tmpInt==2){
		   	 		ShowMyOrdering.run(loginName);
		   	 	}
		   	 	else if (tmpInt==1){
		   	 		OrderBooks.run(loginName);
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
		if (login()) serve();
		else return;
	}
		

		 /*
		 while ((sql = in.readLine()) == null && sql.length() == 0)
			 System.out.println(sql);
		 ResultSetMetaData rsmd = rs.getMetaData();
		 int numCols = rsmd.getColumnCount();
		 while (rs.next())
		 {
			 //System.out.print("cname:");
			 for (int i=1; i<=numCols;i++)
				 System.out.print(rs.getString(i)+"  ");
			 System.out.println("");
		 }
		 System.out.println(" ");
		 rs.close();*/
	}
