<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
	<script src="Lib.js">
	</script>
</head>

<body>
<%
	String p = (String) session.getAttribute("mode");
	String loginName = (String) session.getAttribute("loginName");
	if (p == null || !p.equals("user") || loginName == null){
		out.println("<a href = 'Main.jsp'> You have no rights to access this page, please log in again. Click to go back. </a>");
	}
	else {
		try {
			String result = ShowMyOrdering.runJsp(loginName);
			if (result==null) out.println("You have not ordered any books!");
			else out.println("You have ordered following books!");
			out.println(result);
			out.println("<a href = 'CustomerMod.jsp'> Back. </a>");
		}
		catch (SQLException e){	
                out.println("<a href = 'CustomerMod.jsp' >Your Operation has caused an SQL error. Please contact to the author! </a>");
		}
	}
%>
</body>
