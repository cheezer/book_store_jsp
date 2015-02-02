package acmdb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Lib {
	static String printJSPResultSet(ResultSet results) throws SQLException
	{
		String ans = "<table border = '1'> <tr>";
		ResultSetMetaData rsmd = results.getMetaData();
		int n = rsmd.getColumnCount();
		for (int i = 1; i <= n; ++i)
		{
			ans += "<td>";
			String st = rsmd.getColumnLabel(i);
			ans += st;
			ans += "</td>";
		}
		ans += "<tr>\n";
		results.beforeFirst();
		while (results.next())
		{
			ans += "<tr>";
			for (int i = 1; i <= n; ++i)
			{
				Object o = results.getObject(i);
				if (o == null)
					o = "null";
				String st = o.toString();
				ans += "<td>" + st + "</td>";
			}
			ans += "</tr>\n";
		}
		ans += "</table><BR>";
		return ans;
	}


	static void printResultSet(ResultSet results) throws SQLException
	{
		ResultSetMetaData rsmd = results.getMetaData();
		int n = rsmd.getColumnCount();
		int width[] = new int[n + 1];
		for (int i = 1; i <= n; ++i)
			width[i] = rsmd.getColumnLabel(i).length();
		results.beforeFirst();
		while (results.next())
		{
			for (int i = 1; i <= n; ++i)
			{
				Object o = results.getObject(i);
				if (o == null)
					o = "null";
				width[i] = Math.max(width[i], o.toString().length());
			}
				
		}
		for (int i = 1; i <= n; ++i)
			width[i] += 2;
		for (int i = 1; i <= n; ++i)
		{
			String st = rsmd.getColumnLabel(i);
			System.out.print(st);
			for (int j = 0; j < width[i] - st.length(); ++j)
				System.out.print(' ');
		}
		System.out.println();
		results.beforeFirst();
		while (results.next())
		{
			for (int i = 1; i <= n; ++i)
			{
				Object o = results.getObject(i);
				if (o == null)
					o = "null";
				String st = o.toString();
				System.out.print(st);
				for (int j = 0; j < width[i] - st.length(); ++j)
					System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println();
	}

}
