package com.ai.platform.servlets;

import com.ai.platform.dao.UserDAO;
import com.ai.platform.dao.ProjectMemberDAO;
import com.ai.platform.model.ProjectMember;
import com.ai.platform.model.User;
import jakarta.servlet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/add-member")
public class AddMemberServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        int projectId = Integer.parseInt(request.getParameter("project_id"));

        UserDAO userDAO = new UserDAO();
        ProjectMemberDAO pmDAO = new ProjectMemberDAO();

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            response.sendRedirect("add-member.jsp?project_id=" + projectId + "&error=1");
            return;
        }

        ProjectMember member = new ProjectMember();
        member.setProjectId(projectId);
        member.setUserId(user.getId());

        boolean success = pmDAO.addMember(projectId, user.getId());

        if (success) {
            response.sendRedirect("add-member.jsp?project_id=" + projectId + "&success=1");
        } else {
            response.sendRedirect("add-member.jsp?project_id=" + projectId + "&error=1");
        }
    }
}
