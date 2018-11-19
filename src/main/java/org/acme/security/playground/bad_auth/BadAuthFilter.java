package org.acme.security.playground.bad_auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Authentication filter", urlPatterns = {"/static/bad-auth/*"})
public class BadAuthFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // If we are alread authenticated proceed
        if (req.getCookies() != null)
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("isAuthenticated")) {
                    chain.doFilter(request, response);
                    return;
                }
            }

        // Otherwise authenticate
        HttpServletResponse res = (HttpServletResponse) response;

        res.sendRedirect("/static/login.html");
    }

    @Override
    public void destroy() {

    }


}