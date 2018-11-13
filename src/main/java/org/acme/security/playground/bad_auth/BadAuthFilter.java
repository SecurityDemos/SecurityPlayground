package org.acme.security.playground.bad_auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

@WebFilter(filterName="Authentication filter",urlPatterns={"/static/bad-auth/*"})
class BadAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                HttpServletRequest req = (HttpServletRequest)request;
                
                // If we are alread authenticated proceed
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