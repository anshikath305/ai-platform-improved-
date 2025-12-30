package com.ai.platform.servlets;

import com.ai.platform.dao.TrainingJobDAO;
import com.ai.platform.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-training")
public class DeleteTrainingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("login.jsp?msg=authFailed");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        TrainingJobDAO dao = new TrainingJobDAO();

        boolean deleted = dao.deleteTrainingJob(id);

        if (deleted) {
            response.sendRedirect("admin-training.jsp?msg=deleted");
        } else {
            response.sendRedirect("admin-training.jsp?msg=failed");
        }
    }
}
