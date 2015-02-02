<%--
  Created by IntelliJ IDEA.
  User: michaelxie
  Date: 14-12-24
  Time: 下午9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="acmdb.*" %>
<html>
<head>
    <script src="Lib.js">
    </script>

	<script LANGUAGE="javascript">
		function checkLogin(form){
			if (!checkString(form.userName.value) || !checkString(form.password.value)) {
				alert("Error in your input. Please do not include any ' in your input");
				return false;
			}
		}

	</script>
    <title>Customer Mode</title>
</head>
<body>
<%
	String p = (String)session.getAttribute("mode");
 	if (p != null && !p.equals("user"))
	{
		out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
	}
	else {
		session.setAttribute("mode", "user");
		String slot = request.getParameter("slot");
		if (slot == null)
		{
		%>
		<form name="password" method=post onsubmit="return checkLogin(this)" action="CustomerLogin.jsp">
		  <input type=hidden name="slot" value="login"><BR>
		  Your login name:<BR>
		  <input type = text name = "userName" length = 100><BR>
		  Your password:<BR>
		  <input type=password name="password" length=100><BR>
		  <input type=submit>
		</form>
		<BR>
		<a href = "CustomerRegister.jsp"> Register</a><BR>
		<a href = "Main.jsp">Go back</a><BR>
		<%
		}
		else if (slot.equals("login"))
		{
			String loginName = request.getParameter("userName");
			String password = request.getParameter("password");
		  	try{
		    	String result = UserLogin.JSPRun(loginName, password);
			    if (result != null)
			    {
			      out.println("Welcome back " + result + " ! Refreshing in 2 seconds.");
			      session.setAttribute("loginName", result);
			      out.println("<meta http-equiv=\"Refresh\" content=\"2;url=CustomerMod.jsp\">");
			    }
		    	else {
					%>
					<a href="CustomerLogin.jsp">Invalid login name or password, click to go back.</a><br>
					<%
		      	}
		    }
		    catch (Exception e)
		    {
		      out.println("<a href = 'CustomerLogin.jsp' >Your Operation has caused an SQL error. Click to go back! </a>");
		    }
  		}
  }

%>
</body>
