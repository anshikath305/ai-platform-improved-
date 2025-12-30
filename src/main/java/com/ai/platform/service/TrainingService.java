package com.ai.platform.service;

import com.ai.platform.dao.TrainingJobDAO;
import com.ai.platform.model.TrainingJob;
import com.ai.platform.util.ErrorLogger;

public class TrainingService {

    private final TrainingJobDAO dao = new TrainingJobDAO();

    public int startTrainingJob(TrainingJob job) {

        if (job == null) {
            ErrorLogger.log(new Exception("Training job object null"));
            return -1;
        }

        if (job.getModelName() == null || job.getModelName().trim().isEmpty()) {
            ErrorLogger.log(new Exception("Invalid model name"));
            return -1;
        }

        try {
            return dao.createTrainingJob(job, "Training created");

        } catch (Exception e) {
            ErrorLogger.log(e);
            return -1;
        }
    }

    public boolean updateStatus(int jobId, String status) {
        if (jobId <= 0) return false;
        if (status == null) return false;

        try {
            dao.updateStatus(jobId, status);
            return true;

        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }

    public boolean updateAccuracy(int jobId, float accuracy) {
        if (jobId <= 0) return false;
        if (accuracy < 0 || accuracy > 100) return false;

        try {
            dao.updateAccuracy(jobId, accuracy);
            return true;

        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }
}
