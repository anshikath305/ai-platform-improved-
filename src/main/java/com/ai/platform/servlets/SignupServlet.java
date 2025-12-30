package com.ai.platform.servlets;

import com.ai.platform.util.ErrorLogger;
import com.ai.platform.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserService service = new UserService();

            // validate input
            String errorMsg = service.validateSignupData(name, email, password);

            if (errorMsg != null) {
                response.sendRedirect("signup.jsp?error=" + errorMsg);
                return;
            }

            // Create user
            boolean created = service.createUser(name, email, password);

            if (created) {
                response.sendRedirect("login.jsp?signup=success");
            } else {
                response.sendRedirect("signup.jsp?error=server");
            }

        } catch (Exception e) {

            ErrorLogger.log(e);
            response.sendRedirect("signup.jsp?error=exception");
        }
    }
}
