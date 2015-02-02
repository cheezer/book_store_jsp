<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkCopies(form){
            if (!checkInt(form.copies.value)) {
                alert("Error in your input");
                return false;
            }
            if (!checkString(form.ISBN.value)) {
                alert("Error in your input. Please do not include any ' in your input");
                return false;
            }
            if (form.ISBN.value == "") {
                alert("ISBN should not be empty!");
                return false;
            }
            return true;
        }

    </script>
    <title>Order Books</title>
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
        String loginName = (String)session.getAttribute("loginName");
        if (loginName == null)
            out.println("<a href = 'CustomerLogin.jsp'>You have to login first, click to login.");
        else if (st == null)
        {
    %>
    <form name = "copies" method = get onsubmit="return checkCopies(this)" action = "OrderBooks.jsp">
        <input type = hidden name = "step" value = "1">
        ISBN:<BR>
        <input type = text name = "ISBN" length = 20> <BR>
        copies you want to order(number):<BR>
        <input type = text name = "copies" length = 20> <BR>
        <input type = submit>
    </form>
    <a href = "CustomerMod.jsp"> back </a>
    <%
        }
        else if (st.equals("1"))
        {
            String ISBN = request.getParameter("ISBN");
            int copies = Integer.parseInt(request.getParameter("copies"));
            try
            {
                String result = OrderBooks.JSPRun(loginName, ISBN, copies);
                out.println(result);
                out.println("<a href = 'CustomerMod.jsp'> Click to go back. </a>");
            }
            catch (SQLException e)
            {
                out.println("<a href = 'OrderBooks.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. Click to go back! </a>");
            }
        }
    }
%>

</body>
