package com.ai.platform.servlets;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.dao.ProjectDAO;
import com.ai.platform.model.Project;
import com.ai.platform.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-project")
public class CreateProjectServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // ---------- VALIDATION ----------
        if (name == null || name.trim().isEmpty()) {
            response.sendRedirect("create-project.jsp?error=name");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            response.sendRedirect("create-project.jsp?error=desc");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ProjectDAO dao = new ProjectDAO();

        // ---------- CREATE PROJECT ATOMICALLY ----------
        int projectId = dao.createProject(user.getId(), name, description, "Project created");

        if (projectId > 0) {
            response.sendRedirect("project-details.jsp?id=" + projectId);
        } else {
            response.sendRedirect("create-project.jsp?error=db");
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
        response.sendRedirect("create-project.jsp?error=exception");
    }
}

}
