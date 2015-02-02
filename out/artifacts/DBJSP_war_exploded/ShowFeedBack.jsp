<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkFB(form){
			if (form.ISBN.value=="") {
				alert("Please enter the ISBN of the book you want to search feedback!");
				return false;
			}
			if (!checkString(form.ISBN.value)) {
				alert("Error in your input. Please do not include any ' in your input");
				return false;
			}
			if (!checkInt(form.number.value))
			{
				alert("Invalid number. Type again!")
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
			<form name = "Feedback2" method = get onsubmit="return checkFB(this)" action = "ShowFeedBack.jsp">
				<input type = hidden name = "step" value = "1">
				ISBN: <BR>
				<input type = text name = "ISBN" length = 20> <BR>
				Number: (Indicate how many feedbacks do you want to show. Default is 5.) <BR>
				<input type = text name = "number" length = 3> <BR>
				<input type = submit>
			</form>
			<a href = "CustomerMod.jsp"> back </a>
<%
		}
		else {
			String ISBN = request.getParameter("ISBN");
			int number;
			if (request.getParameter("number").isEmpty()) number = 5;
			else number = Integer.parseInt(request.getParameter("number"));
			try{
				String result = SearchFeedback.jspRun(ISBN, number);
			
				out.println(result+"<BR>");	
				out.println("<a href = 'CustomerMod.jsp'> Click to go back. </a>");

				}
			catch (SQLException e){
                out.println("<a href = 'ShowFeedBack.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. <BR> Click to go back! </a>");	

			//	out.println(e.getMessage());
			//	e.printStackTrace(new java.io.PrintWriter(out));
				} 
			}		
	}
%>
</body>
