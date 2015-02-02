<%@ page language="java" import="acmdb.*" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <script src="Lib.js">
    </script>

    <script LANGUAGE="javascript">
        function checkBook(form){	
			if (!assTrue(form.yearOfPublication.value=="" || checkInt(form.yearOfPublication.value), "Wrong year value")) return false;
			if (!assTrue(form.copies.value=="" || checkInt(form.copies.value), "Wrong copies value")) return false;
			if (!assTrue(form.price.value=="" || checkDouble(form.price.value), "Wrong price value")) return false;

            if (!checkString(form.ISBN.value) || !checkString(form.bookName.value) || !checkString(form.publisher.value)
                    || !checkString(form.format.value) || !checkString(form.authorList.value) || !checkString(form.subject.value)
            || !checkString(form.keywordList.value)) {
                alert("Error in your input. Please do not include any ' in your input");
                return false;
            }
            if (form.ISBN.value == "")
            {
                alert("ISBN can not be empty");
                return false;
            }
            return true;
        }

    </script>


    <title>Add Details</title>
</head>


<body>
<%
	String p = (String)session.getAttribute("mode");
	if  (p ==null || !p.equals("manager")){
		out.println("<a href = 'Main.jsp'> You have no rights to access this page, click to go back. </a>");
	} 
	else {
		String st = request.getParameter("ADstate");
		if (st==null){  /* first time*/
%>
			<form name = "ADform" method = get onsubmit="return checkBook(this)" action = "AddDetails.jsp">
				<input type = hidden name = "ADstate" value = "1">
				Warning: New details will replace the old one!<BR>
				ISBN:<BR>
				<input type = text name = "ISBN" length = 20> <BR>
				book name:<BR>
                <input type = text name = "bookName" length = 100> <BR>
                year of publication(number):<BR>
                <input type = text name = "yearOfPublication"> <BR>
                copies(number):<BR>
                <input type = text name = "copies"> <BR>
                publisher:<BR>
                <input type = text name = "publisher" length = 100> <BR>
                format:<BR>
                <input type = text name = "format" length = 100> <BR>
                author list(Please separate different authors using ,):<BR>
                <input type = text name = "authorList" length = 100> <BR>
                subject:<BR>
                <input type = text name = "subject" length = 100> <BR>
                keyword list(Please separate different keywords using ,):<BR>
                <input type = text name = "keywordList" length = 100> <BR>
                price(real number):<BR>
                <input type = text name = "price"> <BR>	
				<input type = submit>
			</form>
			<a href = "ManagerMod.jsp"> back </a>
<%
		}
		else {
            String ISBN = request.getParameter("ISBN");
            String bookName = request.getParameter("bookName");
			Integer yearOfPublication, copies;
			Double price;
            if (request.getParameter("yearOfPublication")=="") yearOfPublication= null; else yearOfPublication = Integer.parseInt(request.getParameter("yearOfPublication"));
            if (request.getParameter("copies")=="") copies = null; else copies = Integer.parseInt(request.getParameter("copies"));
            if (request.getParameter("price")=="") price= null; else price = Double.parseDouble(request.getParameter("price"));
            String publisher = request.getParameter("publisher");
            String format = request.getParameter("format");
            String subject  = request.getParameter("subject");
            String authorList = request.getParameter("authorList");
            String keywordList = request.getParameter("keywordList");
            try
            {
                String result = AddDetails.jspRun( ISBN,  bookName,  yearOfPublication,  copies,  publisher,  format,  price,  subject,  authorList,  keywordList);
                if (result.equals("$"))
                    out.println("<a href = 'NewBook.jsp' > Details has been successfully updated to book " + ISBN + " . Click to go back. </a>");
                else
                    out.println("<a href = NewBook.jsp>" + result + "Click to go back. </a>");
            }
            catch (SQLException e)
            {
                out.println("<a href = 'NewBook.jsp' >Your Operation has caused an SQL error. Click to go back! </a>");
            }
		}
	}

%>

</body>
