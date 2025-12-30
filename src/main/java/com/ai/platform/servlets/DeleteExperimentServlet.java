package com.ai.platform.servlets;

import com.ai.platform.dao.ExperimentDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.ai.platform.dao.ExperimentDAO;

import java.io.IOException;

@WebServlet("/delete-experiment")
public class DeleteExperimentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        ExperimentDAO dao = new ExperimentDAO();
        dao.deleteExperiment(id);

        resp.sendRedirect("admin-experiments");
    }
}
