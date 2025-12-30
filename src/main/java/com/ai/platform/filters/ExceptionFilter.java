package com.ai.platform.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // TEMPORARY DEBUG MODE
        // DO NOT USE IN PRODUCTION
        try {
            chain.doFilter(req, res);

        } catch (Throwable t) {   // catch EVERYTHING
            t.printStackTrace();  // 🔥 THIS IS WHAT WE NEED
            throw new ServletException(t); // let Tomcat show real error
        }
    }
}
