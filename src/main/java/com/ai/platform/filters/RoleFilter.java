package com.ai.platform.filters;

import com.ai.platform.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");

        /* ---------------- PUBLIC PAGES ---------------- */
        if (
                path.endsWith("login.jsp") ||
                path.endsWith("signup.jsp") ||
                path.contains("/login") ||
                path.contains("/assets/") ||
                path.endsWith("error.jsp") ||
                path.endsWith("error403.jsp")
        ) {
            chain.doFilter(req, res);
            return;
        }

        /* ---------------- AUTH CHECK ---------------- */
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        /* ---------------- ADMIN PAGES ---------------- */
        if (path.contains("admin-") || path.contains("/admin")) {
            if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/error403.jsp");
                return;
            }
        }

        /* ---------------- RESEARCHER PAGES ---------------- */
        if (path.contains("researcher-") || path.contains("/researcher")) {
            if (!"RESEARCHER".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/error403.jsp");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
