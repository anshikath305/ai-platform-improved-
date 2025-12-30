package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.ExperimentResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentResultDAO {

    public boolean addResult(ExperimentResult res) {

        String sql = "INSERT INTO experiment_results (experiment_id, metric_name, metric_value) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, res.getExperimentId());
            stmt.setString(2, res.getMetricName());
            stmt.setString(3, res.getMetricValue());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return false;
    }


    public List<ExperimentResult> getResults(int experimentId) {
        List<ExperimentResult> list = new ArrayList<>();

        String sql = "SELECT * FROM experiment_results WHERE experiment_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, experimentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ExperimentResult res = new ExperimentResult(
                        rs.getInt("id"),
                        rs.getInt("experiment_id"),
                        rs.getString("metric_name"),
                        rs.getString("metric_value"),
                        rs.getString("created_at")
                );

                list.add(res);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }
}
