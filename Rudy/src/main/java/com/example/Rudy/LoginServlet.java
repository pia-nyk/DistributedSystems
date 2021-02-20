package com.example.Rudy;

import java.io.*;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final String username = "Priya";
    private final String password = "@br@c@";

    @Override
    public void init() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        //if credentials are right, create a session & store cookies for the
        //session with a timeout
        if (this.username.equals(username) && this.password.equals(password)) {
            //creates a new session if it already doesnt exist
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(1800); //in secs
            Cookie cookie = new Cookie("user", username);
            response.addCookie(cookie);
            response.sendRedirect("LoginSuccess.jsp");

        } else {
            PrintWriter pw = response.getWriter();
            pw.println("Wrong credentials. Request unsuccessful");
        }
    }

    @Override
    public void destroy() {

    }
}