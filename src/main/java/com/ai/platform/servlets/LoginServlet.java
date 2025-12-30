package com.ai.platform.servlets;

import com.ai.platform.model.User;
import com.ai.platform.service.UserService;
import com.ai.platform.util.ErrorLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // 🔍 DEBUG LINE — REMOVE AFTER ISSUE IS FIXED
            // System.out.println("LOGIN INPUT -> email=" + email + ", password=" + password);

            if (email == null || password == null ||
                email.trim().isEmpty() || password.trim().isEmpty()) {

                response.sendRedirect("login.jsp?msg=empty");
                return;
            }

            User user = userService.login(email.trim(), password.trim());

            // 🔍 DEBUG LINE — REMOVE AFTER ISSUE IS FIXED
            // System.out.println("USER FOUND? -> " + (user != null));

            if (user == null) {
                response.sendRedirect("login.jsp?msg=invalid");
                return;
            }

            request.getSession().setAttribute("user", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("admin-dashboard.jsp");
} else {
    response.sendRedirect("researcher-dashboard.jsp"); // ✅ EXISTS
}


        } catch (Exception e) {
            ErrorLogger.log(e);
            response.sendRedirect("login.jsp?msg=error");
        }
    }
}
