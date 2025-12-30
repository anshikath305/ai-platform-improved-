package com.ai.platform.dao;

import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.TrainingJob;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingJobDAO {

    // CREATE JOB + INITIAL LOG (TRANSACTION SAFE)
    public int createTrainingJob(TrainingJob job, String firstLog) {

        String sqlJob = "INSERT INTO training_jobs (dataset_id, model_name, status, accuracy, created_by) VALUES (?, ?, ?, ?, ?)";
        String sqlLog = "INSERT INTO experiment_logs (experiment_id, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmtJob = conn.prepareStatement(sqlJob, Statement.RETURN_GENERATED_KEYS)) {

                stmtJob.setInt(1, job.getDatasetId());
                stmtJob.setString(2, job.getModelName());
                stmtJob.setString(3, job.getStatus());
                stmtJob.setFloat(4, job.getAccuracy());
                stmtJob.setInt(5, job.getCreatedBy());

                stmtJob.executeUpdate();

                ResultSet keys = stmtJob.getGeneratedKeys();
                int jobId = -1;

                if (keys.next()) {
                    jobId = keys.getInt(1);
                }

                if (jobId == -1) {
                    conn.rollback();
                    return -1;
                }

                try (PreparedStatement stmtLog = conn.prepareStatement(sqlLog)) {
                    stmtLog.setInt(1, jobId);
                    stmtLog.setString(2, firstLog);
                    stmtLog.executeUpdate();
                }

                conn.commit();
                return jobId;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return -1;
    }

    // ✅ ADD THIS METHOD (THIS FIXES THE JSP ERROR)
    public List<TrainingJob> getAllTrainingJobs() {

    List<TrainingJob> list = new ArrayList<>();
    String sql = "SELECT * FROM training_jobs ORDER BY id DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            TrainingJob job = new TrainingJob();
            job.setId(rs.getInt("id"));
            job.setDatasetId(rs.getInt("dataset_id"));
            job.setModelName(rs.getString("model_name"));
            job.setStatus(rs.getString("status"));
            job.setAccuracy(rs.getFloat("accuracy"));
            job.setCreatedBy(rs.getInt("created_by"));

            Timestamp ts = rs.getTimestamp("created_at");
            job.setCreatedAt(ts != null ? ts.toString() : null);

            list.add(job);
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
    }

    return list;
}


    public boolean updateStatus(int id, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE training_jobs SET status=? WHERE id=?"
            );
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }

    public boolean updateAccuracy(int id, float accuracy) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE training_jobs SET accuracy=? WHERE id=?"
            );
            ps.setFloat(1, accuracy);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }

    public boolean deleteTrainingJob(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM training_jobs WHERE id=?"
            );
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }
    // FETCH TRAINING JOBS FOR A SPECIFIC USER (RESEARCHER)
public List<TrainingJob> getTrainingJobs(int userId) {

    List<TrainingJob> jobs = new ArrayList<>();

    String sql = """
        SELECT id, dataset_id, model_name, parameters,
               accuracy, status, created_by, created_at
        FROM training_jobs
        WHERE created_by = ?
        ORDER BY created_at DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            TrainingJob job = new TrainingJob();

            job.setId(rs.getInt("id"));
            job.setDatasetId(rs.getInt("dataset_id"));
            job.setModelName(rs.getString("model_name"));
            job.setParameters(rs.getString("parameters"));
            job.setAccuracy(rs.getFloat("accuracy"));
            job.setStatus(rs.getString("status"));
            job.setCreatedBy(rs.getInt("created_by"));
            job.setCreatedAt(
                rs.getTimestamp("created_at") != null
                    ? rs.getTimestamp("created_at").toString()
                    : null
            );

            jobs.add(job);
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
    }

    return jobs;
}

}
