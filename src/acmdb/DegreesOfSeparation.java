package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class DegreesOfSeparation {

	static HashMap<String, Integer> AuthorNum;
	static ArrayList<ArrayList<Integer>> edge;
	static HashMap<String, ArrayList<Integer>> AuthorOf;
	protected static int run() throws IOException, SQLException{
		YwkDriver.Print("Please enter the name of the first author:");
		String s1 = YwkDriver.readString(100);
		YwkDriver.Print("Please enter the name of the second author:");
		String s2 = YwkDriver.readString(100);
		int s, t;
		String st = "select * from Author;";
		AuthorNum = new HashMap<String, Integer>();
		ResultSet results = YwkDriver.executeSqlQuery(st);
		//results.beforeFirst();
		int num = 0;
		while (results.next())
		{
			AuthorNum.put(results.getString("authorName"), num++);
		}
		if (!AuthorNum.containsKey(s1))
		{
			System.out.println("There is no author named " + s1 + "!");
			return -1; 
		}
		else s = AuthorNum.get(s1);
		if (!AuthorNum.containsKey(s2))
		{
			System.out.println("There is no author named " + s2 + "!");
			return -1;
		}
		else t = AuthorNum.get(s2);
		edge = new ArrayList<ArrayList<Integer>>();
		int d[] = new int[num];
		int z[] = new int[num];
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(s);
		for (int i = 0; i < num; ++i)
		{
			d[i] = 1 << 30;
			z[i] = 0;
		}
		d[s] = 0; z[s] = 1;
		for (int i = 0; i < num; ++i)
			edge.add(new ArrayList<Integer>());
		AuthorOf = new HashMap<String, ArrayList<Integer>>();
		results = YwkDriver.executeSqlQuery("select * from AuthorOf;");
		while (results.next())
		{
			String ISBN1 = results.getString("ISBN");
			int p = AuthorNum.get(results.getString("authorName"));
			if (!AuthorOf.containsKey(ISBN1))
				AuthorOf.put(ISBN1, new ArrayList<Integer>());
			ArrayList<Integer> al = AuthorOf.get(ISBN1);
			for (int i = 0; i < al.size(); ++i)
			{
				edge.get(p).add(al.get(i));
				edge.get(al.get(i)).add(p);
			}
			al.add(p);
		}
		while (!q.isEmpty())
		{
			int i = q.removeFirst();
			for (Iterator<Integer> it = edge.get(i).iterator(); it.hasNext(); )
			{
				int j = it.next();
				if (d[i] + 1 < d[j])
				{
					d[j] = d[i] + 1;
					if (z[j] == 0)
					{
						q.add(j);
						z[j] = 1;
					}
				}
			}
			z[i] = 0;
		}
		if (d[t] < (1 << 30))
		{
			System.out.println("Their degree of separation is " + d[t]);
			return d[t];
		}
		else 
		{
			System.out.println("There is no coauthor path connecting them!");
			return -1;
		}
	}

	public static String JSPRun(String s1, String s2) throws IOException, SQLException{
		Connector con = new Connector();
		int s, t;
		String st = "select * from Author;";
		AuthorNum = new HashMap<String, Integer>();
		ResultSet results = YwkDriver.executeSqlQuery(con, st);
		//results.beforeFirst();
		int num = 0;
		while (results.next())
		{
			AuthorNum.put(results.getString("authorName"), num++);
		}
		if (!AuthorNum.containsKey(s1))
		{
			System.out.println("There is no author named " + s1 + "!");
			con.closeConnection();
			return "There is no author named " + s1 + "!";
		}
		else s = AuthorNum.get(s1);
		if (!AuthorNum.containsKey(s2))
		{
			System.out.println("There is no author named " + s2 + "!");
			con.closeConnection();
			return "There is no author named " + s2 + "!";
		}
		else t = AuthorNum.get(s2);
		edge = new ArrayList<ArrayList<Integer>>();
		int d[] = new int[num];
		int z[] = new int[num];
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(s);
		for (int i = 0; i < num; ++i)
		{
			d[i] = 1 << 30;
			z[i] = 0;
		}
		d[s] = 0; z[s] = 1;
		for (int i = 0; i < num; ++i)
			edge.add(new ArrayList<Integer>());
		AuthorOf = new HashMap<String, ArrayList<Integer>>();
		results = YwkDriver.executeSqlQuery(con, "select * from AuthorOf;");
		while (results.next())
		{
			String ISBN1 = results.getString("ISBN");
			int p = AuthorNum.get(results.getString("authorName"));
			if (!AuthorOf.containsKey(ISBN1))
				AuthorOf.put(ISBN1, new ArrayList<Integer>());
			ArrayList<Integer> al = AuthorOf.get(ISBN1);
			for (int i = 0; i < al.size(); ++i)
			{
				edge.get(p).add(al.get(i));
				edge.get(al.get(i)).add(p);
			}
			al.add(p);
		}
		while (!q.isEmpty())
		{
			int i = q.removeFirst();
			for (Iterator<Integer> it = edge.get(i).iterator(); it.hasNext(); )
			{
				int j = it.next();
				if (d[i] + 1 < d[j])
				{
					d[j] = d[i] + 1;
					if (z[j] == 0)
					{
						q.add(j);
						z[j] = 1;
					}
				}
			}
			z[i] = 0;
		}
		if (d[t] < (1 << 30))
		{
			System.out.println("Their degree of separation is " + d[t] + ".");
			con.closeConnection();
			return "Their degree of separation is " + d[t];
		}
		else
		{
			System.out.println("There is no coauthor path connecting them!");
			con.closeConnection();
			return "There is no coauthor path connecting them!";
		}
	}
}
