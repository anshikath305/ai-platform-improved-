package com.ai.platform.service;

import com.ai.platform.util.ErrorLogger;
import com.ai.platform.dao.ExperimentDAO;
import com.ai.platform.model.Experiment;

public class ExperimentService {

    private final ExperimentDAO dao = new ExperimentDAO();

    public int createExperiment(Experiment exp) {

        if (exp == null) {
            ErrorLogger.log(new Exception("experiment object null"));
            return -1;
        }

        if (exp.getTitle() == null || exp.getTitle().trim().isEmpty()) {
            ErrorLogger.log(new Exception("invalid experiment title"));
            return -1;
        }

        try {
            // automatically inserts first log + experiment insert (transaction)
            return dao.createExperiment(exp, "Experiment created");

        } catch (Exception e) {
            ErrorLogger.log(e);
            return -1;
        }
    }

    public boolean updateStatus(int expId, String status) {
        if (expId <= 0 || status == null) return false;

        try {
            dao.updateStatus(expId, status);
            dao.addLog(expId, "Status changed to " + status);
            return true;

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }

    public boolean updateAccuracy(int expId, float accuracy) {
        if (expId <= 0 || accuracy < 0 || accuracy > 100) return false;

        try {
            dao.updateAccuracy(expId, accuracy);
            dao.addLog(expId, "Accuracy updated: " + accuracy);
            return true;

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }
}

