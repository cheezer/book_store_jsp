<%@ page language="java" import="acmdb.*" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <title>Manager Mode</title>
</head>
<body>
<%

    String p = (String)session.getAttribute("mode");
    if (p == null || !p.equals("manager"))
    {
        out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
    }
    else {
        %>
        What do you want to do, my lord!<BR>
        <a href = "NewBook.jsp">Record a new Book.</a><BR>
        <a href = "NewCopies.jsp">Arrive copies to a book.</a><BR>
        <a href = "AddDetails.jsp"> Add detailed description to a book.</a><BR>
        <a href = "Statistics.jsp">Get statistics of this semester.</a><BR>
        <a href = "UserAwards.jsp">Get the most trusted users and the most useful users.</a><BR>
        <a href = "Main.jsp"> Back. </a><BR>
<%  }
%>
</body>