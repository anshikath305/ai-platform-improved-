package com.ai.platform.servlets;

import com.ai.platform.dao.ExperimentResultDAO;
import com.ai.platform.model.ExperimentResult;



import jakarta.servlet.http.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-experiment-result")
public class AddExperimentResultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int experimentId = Integer.parseInt(request.getParameter("experiment_id"));
        String metricName = request.getParameter("metric_name");
        String metricValue = request.getParameter("metric_value");

        ExperimentResult result = new ExperimentResult();
        result.setExperimentId(experimentId);
        result.setMetricName(metricName);
        result.setMetricValue(metricValue);

        ExperimentResultDAO dao = new ExperimentResultDAO();
        dao.addResult(result);

        response.getWriter().write("OK");
    }
}
