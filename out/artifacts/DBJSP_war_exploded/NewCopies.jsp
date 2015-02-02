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
    <title>New Copies</title>
</head>
<body>
<%
    String p = (String)session.getAttribute("mode");
    if (p == null || !p.equals("manager"))
    {
        out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
    }
    else {
        String st = request.getParameter("step");
        if (st == null)
        {
    %>
    <form name = "copies" method = get onsubmit="return checkCopies(this)" action = "NewCopies.jsp">
        <input type = hidden name = "step" value = "1">
        ISBN:<BR>
        <input type = text name = "ISBN" length = 20> <BR>
        copies(number):<BR>
        <input type = text name = "copies" length = 20> <BR>
        <input type = submit>
    </form>
    <a href = "ManagerMod.jsp"> back </a>
    <%
        }
        else if (st.equals("1"))
        {
            String ISBN = request.getParameter("ISBN");
            int copies = Integer.parseInt(request.getParameter("copies"));
            try
            {
                String result = NewCopies.JSPRun(ISBN, copies);
                if (result.equals("$"))
                    out.println("<a href = 'NewCopies.jsp' >Copies successfully added. Click to go back. </a>");
                else
                    out.println("<a href = NewCopies.jsp>" + result + "Click to go back. </a>");
            }
            catch (SQLException e)
            {
                out.println("<a href = 'NewBook.jsp' >Your Operation has caused an SQL error. Please do not include any ' in your input. Click to go back! </a>");
            }
        }
    }
%>

</body>