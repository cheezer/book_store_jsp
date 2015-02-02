package acmdb;

import java.sql.*;

public class Connector {
	public Connection con;
	public Statement stmt;
	public Connector() {
		try{
			String userName = "acmdbu10";
			String password = "ie2ll8i1";
			String url = "jdbc:mysql://georgia.eng.utah.edu/acmdb10";
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			con = DriverManager.getConnection (url, userName, password);
			//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            		System.err.println(e.getMessage());
			//throw(e);
		}
	}
	
	public void closeConnection() {
		try {
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Unable to close mysql jdbc connection. The error is as follows,\n");
			System.err.println(e.getMessage());
		}
	}
}
//mysql -u acmdbu10 -p -h georgia.eng.utah.edu acmdb10
//scp -r DBJSP/* acmdbu10@georgia.eng.utah.edu:public_html/
//http://georgia.eng.utah.edu:8080/~acmdbu10
