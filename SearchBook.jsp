<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
	<script src="Lib.js">
	</script>
	<script LANGUAGE=javascript>
		function checkSearch(form){
				if (!checkInt(form.style.value)){
						alert("please select searching style!");
						return false;
				}
			if (!checkString(form.author.value) || !checkString(form.publisher.value)|| !checkString(form.title.value)|| !checkString(form.subject.value)) {
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
		if (step==null){
%>
			<form name = "Search" method = get onsubmit="return checkSearch(this)" action = "SearchBook.jsp">
				<input type = hidden name = "step" value = "1">
				Search Style : <BR>
				<input type = radio name = "style" value = "1"> "AND" f following condition <BR>
				<input type = radio name = "style" value = "2"> "OR" of following condition <BR>
				Author : <BR>
				<input type = text name = "author" length = 100 > <BR>
				Publisher : <BR>
				<input type = text name = "publisher" length = 100> <BR>
				Title : <BR>
				<input type = text name = "title" length = 100> <BR>
				Subject : <BR>
				<input type = text name = "subject" length = 100> <BR>
				List Order: <BR>
				<select name = "order">
					<option value="1"> by publish year
					<option value="2"> by feedback score
					<option value="3"> by trusted feedback score	
				</select>
				<BR>
				<input type = submit>
			</form>
			<a href = "CustomerMod.jsp"> back </a>
<%
		}
		else {
				 int style = Integer.parseInt(request.getParameter("style"));
				 int order = Integer.parseInt(request.getParameter("order"));
				 String author = request.getParameter("author");
				 if (author.isEmpty()) author = "%";
				 String publisher = request.getParameter("publisher");
				 if (publisher.isEmpty()) publisher = "%";
				 String title = request.getParameter("title");
				 if (title.isEmpty()) title = "%";
				 String subject = request.getParameter("subject");
				 if (subject.isEmpty()) subject = "%";

				 try {
						String result = SearchBook.jspRun(loginName, style, order, author, publisher, title, subject);
						out.println(result);
						out.println("<a href = 'CustomerMod.jsp'> Click to go back. </a>");
				 }
				 catch (SQLException e){
                out.println("<a href = 'SearchBook.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. <BR> Click to go back! </a>");	
				 }
		}
	}
%>
</body>
