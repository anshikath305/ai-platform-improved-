package com.ai.platform.servlets;

import com.ai.platform.dao.UserDAO;
import com.ai.platform.model.User;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-role")
public class UpdateUserRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User admin = (User) request.getSession().getAttribute("user");

        // FIX: check correct ADMIN role (uppercase)
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("user_id"));
        String role = request.getParameter("role");

        // FIX: convert role to uppercase to match DB format
        role = role.toUpperCase();

        UserDAO dao = new UserDAO();
        dao.updateUserRole(id, role);

        response.sendRedirect("admin-users.jsp");
    }
}
