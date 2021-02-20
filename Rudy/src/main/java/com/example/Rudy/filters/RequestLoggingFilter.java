package com.example.Rudy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter("/RequestLoggingFilter")
public class RequestLoggingFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
        this.context.log("RequestLoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain fc) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> params = req.getParameterNames();
        //log all request params
        while(params.hasMoreElements()) {
            String name = params.nextElement();
            String val = request.getParameter(name);
            this.context.log(req.getRemoteAddr() + " Param name: " + name + " Param val: " + val);
        }

        //get cookies from request
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie: cookies) {
            this.context.log(req.getRemoteAddr() + " Cookie name: "
                    + cookie.getName() + " Cookie val: " + cookie.getValue());
        }
        fc.doFilter(request, response);
    }

    public void destroy() {

    }
}
