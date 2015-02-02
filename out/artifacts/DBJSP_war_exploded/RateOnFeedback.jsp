<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
	<script src="Lib.js">
	</script>
	<script>
		function checkFB(form){
				if (!checkInt(form.ID.value)){
						alert("please enter valid feedback ID!");
						return false;
				}
				return true;
		}
	</script>
</head>

<body>
<%
	String mode = (String) session.getAttribute("mode");
	String loginName = (String) session.getAttribute("loginName");
	if (mode==null || !mode.equals("user") || loginName==null){
		out.println("<a href = 'Main.jsp'> Time out! You have to re-login!. </a>");
	}
	else {
		String step = request.getParameter("step");
		if (step==null){
%>
			<form name = "Feedback3" method = get onsubmit="return checkFB(this)" action = "RateOnFeedback.jsp">
				<input type = hidden name = "step" value = "1">
				Feedback ID: <BR>
				<input type = text name = "ID" length = 20> <BR>
				Rating: <BR>
				<select name="rate">
					<option value="2"> very useful
					<option value="1"> useful
					<option value="0"> useless
				</select> <BR>
				<input type = submit>
			</form>
			<a href = "CustomerMod.jsp"> back </a>
<%
		}
		else {
				 int ID = Integer.parseInt(request.getParameter("ID"));
				 int rate = Integer.parseInt(request.getParameter("rate"));
				 try {
						String result = RateOnFeedback.jspRun(loginName, ID, rate);
						out.println(result+"<BR>");
						out.println("<a href = 'CustomerMod.jsp'> CLick to go back. </a>");
				 }
				 catch (SQLException e){
                out.println("<a href = 'RateOnFeedback.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. <BR> Click to go back! </a>");	
				 }
		}
	}
%>
</body>
