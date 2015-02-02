<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkFB(form){
            if (!checkInt(form.score.value)) {
                alert("Please input a valid score number!");
                return false;
            }
			if (form.ISBN.value=="") {
				alert("Please enter the ISBN of the book you want to give feedback!");
				return false;
			}
			if (!checkString(form.ISBN.value)) {
				alert("Error in your input. Please do not include any ' in your input");
				return false;
			}
            return true;
        }

    </script>
    <title>Order Books</title>
</head>

<body>
<%
	String mode = (String) session.getAttribute("mode");
	String loginName = (String) session.getAttribute("loginName");
	if (mode==null || !mode.equals("user") || loginName==null){
			out.println("<a href = 'Main.jsp'> Time Out! You have to re-login!. </a>");
	}
	else {
		String step = request.getParameter("step");
		if (step==null){
%>
			<form name = "Feedback" method = get onsubmit="return checkFB(this)" action = "GiveFeedBack.jsp">
				<input type = hidden name = "step" value = "1">
				ISBN: <BR>
				<input type = text name = "ISBN" length = 20> <BR>
				Score: (Integer Between 0 to 10, 0 for terrible and 10 for masterpiece) <BR>
				<input type = text name = "score" length = 3> <BR>
				Comment: <BR>
				<input type = text name = "comment"> <BR>
				<input type = submit>
			</form>
			<a href = "CustomerMod.jsp"> back </a>
<%
		}
		else {
			String ISBN = request.getParameter("ISBN");
			int score = Integer.parseInt(request.getParameter("score"));
			String comment = request.getParameter("comment");
			try{
				String result = GiveFeedback.jspRun(loginName, ISBN, score, comment);
				out.println(result+"<BR>");
				out.println("<a href = 'CustomerMod.jsp'> Click to go back. </a>");
				}
			catch (SQLException e){
                out.println("<a href = 'GiveFeedBack.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. <BR> Click to go back! </a>");	
				}
			}		
	}
%>
</body>
