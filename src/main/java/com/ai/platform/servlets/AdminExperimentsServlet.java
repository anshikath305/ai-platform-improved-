package com.ai.platform.servlets;

import com.ai.platform.dao.ExperimentDAO;
import com.ai.platform.model.Experiment;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin-experiments")
public class AdminExperimentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ExperimentDAO dao = new ExperimentDAO();
        List<Experiment> experiments = dao.getAllExperiments();

        request.setAttribute("experiments", experiments);
        System.out.println("ADMIN EXPERIMENTS SERVLET HIT");

        request.getRequestDispatcher("admin-experiments.jsp").forward(request, response);
    }
}


