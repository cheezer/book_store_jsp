package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserRegister {
	protected static void run() throws IOException, SQLException{
		System.out.println("\nWhich login name do you want to register (less than 100 character)?");
		String loginName = YwkDriver.readString(100);
	 		
	 		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Customer c WHERE c.loginName='"+loginName+"'");       	 		
	 		if (results.next()){
	 			System.out.println("\nError: This name has been registered!!!!");
	 			return;
	 		}

	 		
	  		System.out.println("\nWhat's your password?");
	  		String password =  YwkDriver.readString(100);
	  		System.out.println("\nWhat's your fullname?");
	  		String fullname =  YwkDriver.readString(100);
	  		System.out.println("\nWhat's your address?");
	  		String address =  YwkDriver.readString(100);
	  		System.out.println("\nWhat's your phone number?");
	  		String phone =  YwkDriver.readString(100);
	  		YwkDriver.executeSqlStat("INSERT INTO Customer VALUES ('"+loginName+"','" + fullname + "','"+password+"','" + address +"','" + phone + "')");
	  		System.out.println("\nSuccessfully create your Account, now please login!\n");
	}

	public static String JSPRun(String loginName, String password, String fullName, String phone, String address)throws IOException, SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Customer c WHERE c.loginName='"+loginName+"'");
		if (results.next()){
			System.out.println("\nError: This name has been registered!!!!");
			con.closeConnection();
			return "Error: This name has been registered!!!!";
		}
		YwkDriver.executeSqlStat(con, "INSERT INTO Customer VALUES ('"+loginName+"','" + fullName + "','"+password+"','" + address +"','" + phone + "')");
		System.out.println("\nSuccessfully create your Account, now please login!\n");
		con.closeConnection();
		return "$";
	}

}
