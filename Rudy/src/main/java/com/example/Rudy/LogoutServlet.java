package com.example.Rudy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("JSESSIONID")) {
                System.out.println("JSESSIONID=" + cookie.getValue());
            }
        }

        //invalidate the user session
        HttpSession session = request.getSession(false); //get the session, dont create one
        if(session != null) {
            session.invalidate();
        }
        PrintWriter pw = response.getWriter();
        pw.println("Logout successful");
    }
}
