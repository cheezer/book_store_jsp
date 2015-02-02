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
      if (!checkString(form.value.value)) {
        alert("Error in your input. Please do not include any ' in your input");
        return false;
      }
    }

  </script>
  <title>Manager Mode</title>
</head>
<body>
<%
  String p = (String)session.getAttribute("mode");
  if (p != null && !p.equals("manager"))
  {
    out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
  }
  else {
    session.setAttribute("mode", "manager");
  String slot = request.getParameter("slot");
  if (slot == null)
  {
%>
Please enter your manager password:
<form name="password" method=post onsubmit="return checkLogin(this)" action="ManagerLogin.jsp">
  <input type=hidden name="slot" value="password">
  <input type=password name="value" length=10>
  <input type=submit>
</form>
<a href = "Main.jsp">Go back</a>
<%
}
else if (slot.equals("password"))
{
  String value = request.getParameter("value");
  if (!YwkDriver.checkPassword(value))
  {
%>
<a href="ManagerLogin.jsp">Invalid password, click to go back.</a><br>
<%
  }
  else
  {
%>
Successfully logging in! Refreshing in 2 seconds.
<meta http-equiv="Refresh" content="2;url=ManagerMod.jsp">

<%
    }
  }
  }
%>
</body>
