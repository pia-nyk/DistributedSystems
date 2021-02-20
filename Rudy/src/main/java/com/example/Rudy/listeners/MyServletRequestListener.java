package com.example.Rudy.listeners;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    /**
     * triggered when request is received by the servlet container
     * logs the request server address
     * @param event
     */
    public void requestInitialized (ServletRequestEvent event) {
        ServletRequest request = event.getServletRequest();
        System.out.println("Servlet initialized. Remote IP = " + request.getRemoteAddr());
    }

    public void requestDestroyed (ServletRequestEvent event) {
        ServletRequest request = event.getServletRequest();
        System.out.println("Servlet destroyed. Remote IP = " + request.getRemoteAddr());
    }
}
