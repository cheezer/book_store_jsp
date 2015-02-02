package acmdb;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YwkDriver extends Applet {
	protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static Connector con=null;
	protected static int totFeedback = 0;
	//private static String password = "acmdbfall14";
	private static String password = " ";
	
	private static void displayMainMenu(){
			 System.out.println("\n        Book Store Management System     ");
	    	 System.out.println("1. Enter the manager model.");
	    	 System.out.println("2. Enter the customer model.");
	    	 System.out.println("3. exit.");
	    	 System.out.println("please enter your choice:");
	}

	public static boolean checkPassword(String st)
	{
		return st.equals(password);

	}
	
	public static void Print(String st)
	{
		System.out.println(st);
	}
	
	protected static void printTable(ResultSet results, String[] name, int rows) throws SQLException{
		System.out.println();
		int[] width = new int[name.length];
		for (int i=0;i<width.length;++i)
			width[i] = name[i].length();
		results.first();
		while (!results.isAfterLast()){
			for (int i=0;i<width.length;++i)
				width[i] = Math.max(width[i], results.getString(i+1).length());
			results.next();
		}
		
		for (int i=0;i<width.length;++i){
			System.out.print(name[i]);
			for (int j=0;j<width[i]-name[i].length()+3;++j)
				System.out.print(" ");
		}
		System.out.println();
		
		results.first();
		while (!results.isAfterLast()){
			for (int i=0;i<width.length;++i){
				System.out.print(results.getString(i+1));
				for (int j=0;j<width[i]-results.getString(i+1).length()+3;++j)
					System.out.print(" ");
			}
			System.out.println();
			results.next();
			if (--rows==0) break;
		}
	}
	
	protected static String readString(int maxLength) throws IOException{//;"?
	 	String des = null;
	 	while (des == null)
	 	{
			while ((des = in.readLine()) == null || des.length() == 0);
		 	if (des.length()>maxLength) {
		 			System.out.println("\nError: Too long Message! (should be in "+maxLength+" characters! Type again!\n");
		 			des = null;
		 	}
		 	if (des.indexOf('\'') != -1)
		 	{
		 		System.out.println("\nError: Please do not include in \' in your input");
		 		des = null;
		 	}
	 	}
	 	return des;
	}
	
	protected static Integer readInt() throws IOException{
		String tmpString;
		Integer tmpInt = null;
		while (tmpInt == null)
		{
			while ((tmpString = in.readLine()) == null || tmpString.length() == 0);
	   	 	try{
	   	 		tmpInt = Integer.parseInt(tmpString);
	   	 	}catch (Exception e) {
	   	 		System.out.println("This is not an Integer! Type again!");
	   	 		tmpInt = null;
	   	 	}
		}
   	 	return tmpInt;
	}
	
	protected static Double readDouble() throws IOException{
		String tmpString;
		Double tmpDouble = null;
		while (tmpDouble == null)
		{
			while ((tmpString = in.readLine()) == null || tmpString.length() == 0);
	   	 	try{
	   	 		tmpDouble = Double.parseDouble(tmpString);
	   	 	}catch (Exception e) {
	   	 		System.out.println("This is not an Double! Type again!");
	   	 		tmpDouble = null;
	   	 	}
		}
   	 	return tmpDouble;
	}
	
	protected static ResultSet executeSqlQuery(String s) throws SQLException{
		//System.out.println("to execute: "+s);
		con = new Connector();
		ResultSet rs =  con.stmt.executeQuery(s);
		con.closeConnection();
		return rs;
	}
	
	protected static void executeSqlStat(String s) throws SQLException{
		//System.out.println("to execute: "+s);
		con = new Connector();
		con.stmt.executeUpdate(s);
		con.closeConnection();
	}

	protected static ResultSet executeSqlQuery(Connector con, String s) throws SQLException{
		//System.out.println("to execute: "+s);
		ResultSet rs = con.stmt.executeQuery(s);
		return rs;
	}

	protected static void executeSqlStat(Connector con, String s) throws SQLException{
		//System.out.println("to execute: "+s);
		con.stmt.executeUpdate(s);
	}

	public static void connect()
	{
		try {
			con = new Connector();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println ("\nCannot connect to database server");
		}
		System.out.println ("\nDatabase connection established");
	}
	
	public static void main(String[] args) {
		String choice;
        int c=0;
         try
		 {
			//remember to replace the password
	         	             
	             while(true)
	             {
					 //connect();
	            	 displayMainMenu();
	            	 while ((choice = in.readLine()) == null || choice.length() == 0);
	            	 try{
	            		 c = Integer.parseInt(choice);
	            	 }catch (Exception e) {continue;}
	            	 
	            	 if (c<1 | c>3)
	            		 continue;
	            	 if (c==1) 
	            	 {
	            		 System.out.println("Please type in your manager password:");
	            		 String st = readString(100);
	            		 if (st.equals(password))
	            			 ManagerMod.run();
	            		 else 
	            		 {
	            			 System.out.println("Invalid password");
	            			 continue;
	            		 }
	            	 }
	            	 else if (c==2)	CustomerMod.run();
	            	 else
	            	 {   
	            		 System.out.println("\nBye!");
	            		 con.stmt.close(); 
	            		 break;
	            	 }
	             }
		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
        	 System.err.println ("\nCannot connect to database server");
         }
         finally //what is this
         {
        	 if (con != null)
        	 {
        		 try
        		 {
        			 con.closeConnection();
        			 System.out.println ("\nDatabase connection terminated");
        		 }
        	 
        		 catch (Exception e) { /* ignore close errors */ }
        	 }	 
         }
	}
}
