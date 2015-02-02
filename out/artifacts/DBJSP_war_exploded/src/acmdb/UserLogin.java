package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserLogin {
	protected static String run() throws IOException, SQLException{

		System.out.println("\nWhat's your login name?");
		String loginName = YwkDriver.readString(100);
		if (loginName==null) return null;
		System.out.println("\nWhat's your password?");
		String password =  YwkDriver.readString(100);
		if (password==null) return null;
		ResultSet results = YwkDriver.executeSqlQuery("SELECT * FROM Customer c WHERE c.loginName='"+loginName+"' AND c.password='"+password+"'");
		if (results.next()) {
			System.out.println("\nLogin successfully!");
			System.out.println("Welcome "+loginName);
			return loginName;
		}
		else System.out.println("\nFail to match your LoginName/Password!");
		return null;
	}

	public static String JSPRun(String loginName, String password) throws SQLException{
		Connector con = new Connector();
		ResultSet results = YwkDriver.executeSqlQuery(con, "SELECT * FROM Customer c WHERE c.loginName='"+loginName+"' AND c.password='"+password+"'");
		if (results.next()) {
			System.out.println("\nLogin successfully!");
			System.out.println("Welcome "+loginName);
			con.closeConnection();
			return loginName;
		}
		else System.out.println("\n");
		con.closeConnection();
		return null;
	}
}
