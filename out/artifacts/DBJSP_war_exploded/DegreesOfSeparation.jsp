<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkDeg(form){
            if (!checkString(form.name1.value) || !checkString(form.name2.value)) {
                alert("Error in your input, please do not contain any ' in your input");
                return false;
            }
            return true;
        }
    </script>
    <title>Degrees Of Separation</title>
</head>
<body>
<%
    String p = (String)session.getAttribute("mode");
    if (p == null || !p.equals("user"))
    {
        out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
    }
    else {
        String st = request.getParameter("step");
        if (st == null)
        {
    %>
    <form name = "DegreesOfSeparation" method = get onsubmit="return checkDeg(this)" action = "DegreesOfSeparation.jsp">
        <input type = hidden name = "step" value = "1">
        The name of the first author:<BR>
        <input type = text name = "name1" length = 100> <BR>
        The name of the second author:<BR>
        <input type = text name = "name2" length = 100> <BR>
        <input type = submit>
    </form>
    <a href = "CustomerMod.jsp"> back </a>
    <%
        }
        else if (st.equals("1"))
        {
            String name1 = request.getParameter("name1");
            String name2 = request.getParameter("name2");
            try
            {
                String result = DegreesOfSeparation.JSPRun(name1, name2);
                out.println(result + "<BR>");
                out.println("<a href = 'CustomerMod.jsp'> back </a>");
            }
            catch (SQLException e)
            {
                out.println("<a href = 'DegreesOfSeparation.jsp' >Your Operation has caused an SQL error. Click to go back! </a>");
            }
        }
    }
%>

</body>