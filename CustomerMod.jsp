<%@ page language="java" import="acmdb.*" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <title>Customer Mode</title>
</head>
<body>
<%
    String p = (String)session.getAttribute("mode");
    if (p == null || !p.equals("user"))
    {
        out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
    }
    else {
    %>
<%
    String loginName = (String)session.getAttribute("loginName");
    out.println("Welcome, " + loginName + "!<BR>");
%>
        How can I help you?<BR>
        <a href = "OrderBooks.jsp"> 1. Order Books.</a><BR>
        <a href = "OrderList.jsp"> 2. Show Ordering List. </a><BR>
        <a href = "GiveFeedBack.jsp"> 3. Give Feedback. </a><BR>
		<a href = "ShowFeedBack.jsp"> 4. Show Top N Useful Feedback Of Some Book. </a><BR>
		<a href = "RateOnFeedback.jsp"> 5. Rate On Some Feedback Of Some Book. </a><BR>
		<a href = "Declare.jsp"> 6. Declare On Other Customer. </a><BR>
		<a href = "SearchBook.jsp"> 7. Search For Books. </a><BR>
		<a href = "DegreesOfSeparation.jsp"> 8. Found Out Degrees Of Separation.</a><BR>

		<a href = "Main.jsp"> Log out. </a><BR>
<%  } %>
</body>
