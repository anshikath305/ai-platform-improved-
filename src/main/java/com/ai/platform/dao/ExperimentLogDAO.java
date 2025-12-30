package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.ExperimentLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentLogDAO {

    public boolean addLog(ExperimentLog log) {

        String sql = "INSERT INTO experiment_logs (experiment_id, log_text) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getExperimentId());
            stmt.setString(2, log.getLogText());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return false;
    }

    public List<ExperimentLog> getLogs(int experimentId) {
        List<ExperimentLog> list = new ArrayList<>();

        String sql = "SELECT * FROM experiment_logs WHERE experiment_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, experimentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ExperimentLog log = new ExperimentLog(
                        rs.getInt("id"),
                        rs.getInt("experiment_id"),
                        rs.getString("log_text"),
                        rs.getString("created_at")
                );

                list.add(log);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }
}
