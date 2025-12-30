package com.ai.platform.servlets;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.model.TrainingJob;
import com.ai.platform.model.User;
import com.ai.platform.service.TrainingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/train-model")
public class TrainingServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        String modelName = request.getParameter("model_name");
        String datasetIdStr = request.getParameter("dataset_id");

        // server-side validation
        if (modelName == null || modelName.trim().isEmpty()) {
            response.sendRedirect("training.jsp?error=model");
            return;
        }

        if (datasetIdStr == null || !datasetIdStr.matches("\\d+")) {
            response.sendRedirect("training.jsp?error=dataset");
            return;
        }

        int datasetId = Integer.parseInt(datasetIdStr);

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        TrainingJob job = new TrainingJob();
        job.setCreatedBy(user.getId());
        job.setDatasetId(datasetId);
        job.setModelName(modelName);
        job.setStatus("Queued");
        job.setAccuracy(0);

        TrainingService service = new TrainingService();
        int jobId = service.startTrainingJob(job);


        if (jobId > 0) {
            response.sendRedirect("training-history.jsp?job=" + jobId);
        } else {
            response.sendRedirect("training.jsp?error=server");
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
        response.sendRedirect("training.jsp?error=exception");
    }
}


}
