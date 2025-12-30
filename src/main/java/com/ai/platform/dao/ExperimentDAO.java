package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.Experiment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentDAO {

    // Create experiment + first log atomically
    public int createExperiment(Experiment exp, String firstLog) {

        String sqlExp = "INSERT INTO experiments (user_id, title, description, status, accuracy) VALUES (?, ?, ?, ?, ?)";
        String sqlLog = "INSERT INTO experiment_logs (experiment_id, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmtExp = conn.prepareStatement(sqlExp, Statement.RETURN_GENERATED_KEYS)) {

                stmtExp.setInt(1, exp.getUserId());
                stmtExp.setString(2, exp.getTitle());
                stmtExp.setString(3, exp.getDescription());
                stmtExp.setString(4, exp.getStatus());
                stmtExp.setFloat(5, exp.getAccuracy());

                stmtExp.executeUpdate();

                ResultSet keys = stmtExp.getGeneratedKeys();
                int expId = -1;

                if (keys.next()) {
                    expId = keys.getInt(1);
                }

                if (expId == -1) {
                    conn.rollback();
                    return -1;
                }

                try (PreparedStatement stmtLog = conn.prepareStatement(sqlLog)) {
                    stmtLog.setInt(1, expId);
                    stmtLog.setString(2, firstLog);
                    stmtLog.executeUpdate();
                }

                conn.commit();
                return expId;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return -1;
    }

    // Add log entry - transaction safe
    public boolean addLog(int expId, String message) {

        String sql = "INSERT INTO experiment_logs (experiment_id, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, expId);
                stmt.setString(2, message);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }

            } catch (SQLException e) {
                conn.rollback();
                ErrorLogger.log(e);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }

    // Add results - NEW method based on requirements
    public boolean addResult(int expId, String output, String metrics) {

        String sql = "INSERT INTO experiment_results (experiment_id, output, metrics) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, expId);
                stmt.setString(2, output);
                stmt.setString(3, metrics);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }

            } catch (SQLException e) {
                conn.rollback();
                ErrorLogger.log(e);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }

    // Get experiments by user
    public List<Experiment> getExperimentsByUser(int userId) {
        List<Experiment> list = new ArrayList<>();

        String sql = "SELECT * FROM experiments WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Experiment exp = new Experiment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("created_at")
                );

                exp.setStatus(rs.getString("status"));
                exp.setAccuracy(rs.getFloat("accuracy"));

                list.add(exp);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return list;
    }

    // Get experiment by ID
    public Experiment getExperimentById(int id) {

        String sql = "SELECT * FROM experiments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Experiment exp = new Experiment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("created_at")
                );

                exp.setStatus(rs.getString("status"));
                exp.setAccuracy(rs.getFloat("accuracy"));
                return exp;
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return null;
    }

    // Get Logs
    public List<String> getLogs(int experimentId) {
        List<String> logs = new ArrayList<>();

        String sql = "SELECT message FROM experiment_logs WHERE experiment_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, experimentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(rs.getString("message"));
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return logs;
    }

    // ADMIN: Fetch all experiments with user details
    public List<Experiment> getAllExperiments() {
        List<Experiment> list = new ArrayList<>();

        String sql =
            "SELECT e.*, u.name AS user_name " +
            "FROM experiments e " +
            "JOIN users u ON e.user_id = u.id " +
            "ORDER BY e.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Experiment exp = new Experiment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("created_at")
                );

                exp.setStatus(rs.getString("status"));
                exp.setAccuracy(rs.getFloat("accuracy"));
                exp.setUserName(rs.getString("user_name"));

                list.add(exp);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return list;
    }

    // Delete Experiment
    public boolean deleteExperiment(int id) {

        String sql = "DELETE FROM experiments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
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

}
