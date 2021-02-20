<%--
  Created by IntelliJ IDEA.
  User: priya
  Date: 20/02/21
  Time: 12:42 PM
  as soon as the jsp page is loaded session gets created by container
  if default behavior needs to be overridden, use session=false
--%>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Success page</title>
</head>
    <body>
        <%
            //allow access only if session exists
            String user = null;
            String username = null;
            String sessionId = null;
            if(session.getAttribute("user") == null) {
                PrintWriter pw = response.getWriter();
                pw.println("Page isnt accessible ");
            } else {
                user = (String) session.getAttribute("user");
                //get cookies from the request
                Cookie[] cookies = request.getCookies();

                if(cookies != null) {
                    for(Cookie cookie : cookies) {
                        if(cookie.getName().equals("user"))
                            username = cookie.getValue();

                        //after a session is created, the jsessionid
                        //is used to keep track of the session
                        if(cookie.getName().equals("JSESSIONID"))
                            sessionId = cookie.getValue();

                    }
                }
            }
        %>
        <h3>Hi <%=username %>, Login successful. Your Session ID=<%=sessionId %></h3>
        <br> User = <%=user %> </br>
    </body>
</html>