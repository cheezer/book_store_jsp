<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkSta(form){
            if (!checkInt(form.m1.value) || !checkInt(form.m2.value)) {
                alert("Error in your input");
                return false;
            }
            return true;
        }
    </script>
    <title>User Awards</title>
</head>
<body>
<%

    String p = (String)session.getAttribute("mode");
    if (p == null || !p.equals("manager"))
    {
        out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
    }
    else {
        String st = request.getParameter("slot");
        if (st == null)
        {
    %>
        <form name = "copies" method = get onsubmit="return checkSta(this)" action = "UserAwards.jsp">
            <input type = hidden name = "slot" value = "1">
            Please enter the number of the most trusted user you want to know:<BR>
            <input type = text name = "m1" length = 20> <BR>
            Please enter the number of the most useful user you want to know:<BR>
            <input type = text name = "m2" length = 20> <BR>
            <input type = submit>
        </form>
        <a href = "ManagerMod.jsp"> back </a>
    <%
        }
        else if (st.equals("1"))
        {
            int m1 = Integer.parseInt(request.getParameter("m1"));
            int m2 = Integer.parseInt(request.getParameter("m2"));
            try
            {
                String result = UserAwards.JSPRun(m1, m2);
                out.println(result);
                out.println("<a href = 'ManagerMod.jsp'> Click to go back. </a>");
            }
            catch (SQLException e)
            {
                out.println("<a href = 'UserAwards.jsp' >Your Operation has caused an SQL error. Click to go back! </a>");
            }
        }
    }
%>

</body>