package com.ai.platform.servlets;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.model.Experiment;
import com.ai.platform.model.User;
import com.ai.platform.service.ExperimentService;
import jakarta.servlet.ServletException;
import com.ai.platform.dao.ExperimentDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/create-experiment")
public class CreateExperimentServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        // --------------- VALIDATION --------------------
        if (title == null || title.trim().isEmpty()) {
            response.sendRedirect("new-experiment.jsp?error=title");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            response.sendRedirect("new-experiment.jsp?error=desc");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Experiment exp = new Experiment();
        exp.setUserId(user.getId());
        exp.setTitle(title.trim());
        exp.setDescription(description.trim());
        exp.setStatus("Created");
        exp.setAccuracy(0);

        ExperimentDAO dao = new ExperimentDAO();

        // !!! atomic creation
        ExperimentService service = new ExperimentService();
        int expId = service.createExperiment(exp);


        if (expId > 0) {
            response.sendRedirect("experiment-details.jsp?id=" + expId);
        } else {
            response.sendRedirect("new-experiment.jsp?error=db");
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
        response.sendRedirect("new-experiment.jsp?error=exception");
    }
}



}
