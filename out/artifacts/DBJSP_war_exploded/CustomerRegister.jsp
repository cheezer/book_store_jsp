<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <script src="Lib.js">
    </script>
    <script LANGUAGE="javascript">
        function checkReg(form){
            if (!checkString(form.loginName.value) || !checkString(form.password.value) || !checkString(form.fullName.value) || !checkString(form.phone.value)|| !checkString(form.address.value)) {
                alert("Error in your input, please do not contain any ' in your input");
                return false;
            }
            return true;
        }
    </script>
    <title>Register</title>
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
            <form name = "register" method = get onsubmit="return checkReg(this)" action = "CustomerRegister.jsp">
                <input type = hidden name = "step" value = "1">
                Login Name:<BR>
                <input type = text name = "loginName" length = 100> <BR>
                Password:<BR>
                <input type = password name = "password" length = 100> <BR>
                Full name:<BR>
                <input type = text name = "fullName" length = 100> <BR>
                Phone: <BR>
                <input type = text name = "phone" length = 100> <BR>
                Address: <BR>
                <input type = text name = "address" length = 100> <BR>
                <input type = submit>
            </form>
            <a href = "CustomerLogin.jsp"> back </a>
    <%
        }
        else if (st.equals("1"))
        {
            String fullName = request.getParameter("fullName");
            String loginName = request.getParameter("loginName");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            try
            {
                String result = UserRegister.JSPRun(loginName, password, fullName, phone, address);
                if (result.equals("$"))
                {
                    out.println("<a href = 'CustomerLogin.jsp'> Successfully create your Account, now please login! </a>");
                }
                else {
                    out.println("<a href = 'CustomerRegister.jsp'>" + result + "Click to go back. </a>");
                }
            }
            catch (SQLException e)
            {
                out.println("<a href = 'CustomerRegister.jsp' >Your Operation has caused an SQL error. Click to go back! </a>");
            }
        }
    }
%>

</body>