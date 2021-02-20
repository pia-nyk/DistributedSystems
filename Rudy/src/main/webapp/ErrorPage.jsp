<%--
  Created by IntelliJ IDEA.
  User: priya
  Date: 20/02/21
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exception/Error Details</title>
</head>
<body>
    <%
        String error = "Exception";
        Integer statusCode = response.getStatus();
        String requestURI = request.getRequestURI();

        if(statusCode != 500) {
            error = "Error";
        }
    %>
    <h3><%= error%> Details</h3>
    <strong>Status Code: <%= statusCode%></strong> <br/>
    <strong>Requested URI: <%=requestURI%> </strong>
</body>
</html>
