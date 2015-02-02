<%@ page language="java" import="acmdb.*" %>
<html>
<head>
    <script src="Lib.js">
    </script>

    <title>Mode Choose</title>
</head>
<body>
<%
session.setAttribute("loginName", null);
    session.setAttribute("mode", null);
%>
<div class="unit">
Welcome to ywk and xqz's bookstore!<BR>
<a href="ManagerLogin.jsp">Enter manager mode</a><br> <!-- ManagerLogin -->
<a href="CustomerLogin.jsp">Enter User mode</a><br>
</div>

</body>