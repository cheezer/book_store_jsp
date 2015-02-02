<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
	<script src="Lib.js">
	</script>
	<script LANGUAGE="javascript">
		function checkDec(form){
				if (form.userName.value == ""){
						alert("please enter valid user name!");
						return false;
				}
				if (!checkString(form.userName.value)) {
					alert("Error in your input. Please do not include any ' in your input");
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
			if (step==null) {
%>
				<form name = "Declare" method = get onsubmit="return checkDec(this)" action = "Declare.jsp">
					<input type = hidden name = "step" value = "1">
					user loginName: (Who do you want to declare on) <BR>
					<input type = text name = "userName" length=100> <BR>
					<select name="declare">
						<option value="1"> trusted
						<option value="0"> not trusted
					</select> <BR>
					<input type = submit>
				</form>
				<a href = "CustomerMod.jsp"> back </a>
<%
			}
			else {
					String userName = request.getParameter("userName");
					int dec = Integer.parseInt(request.getParameter("declare"));
					try {
							String result = Declare.jspRun(loginName, userName,dec);
							out.println(result+"<BR>");
							out.println("<a href = 'CustomerMod.jsp'> Click to go back. </a>");
					}
				 catch (SQLException e){
                out.println("<a href = 'Declare.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. <BR> Click to go back! </a>");	
				 }
			}
	}
%>
</body>
