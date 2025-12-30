package com.ai.platform.servlets;

import com.ai.platform.dao.ExperimentLogDAO;
import com.ai.platform.model.ExperimentLog;



import jakarta.servlet.http.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/add-experiment-log")
public class AddExperimentLogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int experimentId = Integer.parseInt(request.getParameter("experiment_id"));
        String logText = request.getParameter("log_text");

        ExperimentLog log = new ExperimentLog();
        log.setExperimentId(experimentId);
        log.setLogText(logText);

        ExperimentLogDAO dao = new ExperimentLogDAO();
        dao.addLog(log);

        response.getWriter().write("OK");
    }
}
