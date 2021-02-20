package com.example.Rudy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AutheticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    //filterconfig object is created by the container
    //gets conf info from web.xml or elsewhere
    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
        this.context.log("Authentication Filter started");
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                          FilterChain fc) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested resource: " + uri);

        HttpSession session = req.getSession(false); //fetch existing session
        //if logout is tried to access, log error
        if(session == null && uri.endsWith("logout")) {
            this.context.log("Unauthorized access ");
        } else {
            fc.doFilter(request, response);
        }
    }

    public void destroy() {}
}
