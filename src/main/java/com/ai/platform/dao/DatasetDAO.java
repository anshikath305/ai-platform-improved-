package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.Dataset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatasetDAO {

    // Upload dataset
    public boolean insertDataset(Dataset dataset) {
        boolean inserted = false;

        String sql = "INSERT INTO datasets (name, description, file_path, uploaded_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dataset.getName());
            stmt.setString(2, dataset.getDescription());
            stmt.setString(3, dataset.getFilePath());
            stmt.setInt(4, dataset.getUploadedBy());

            inserted = stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return inserted;
    }

    // Fetch datasets uploaded by user
    public List<Dataset> getDatasetsByUser(int userId) {
        List<Dataset> list = new ArrayList<>();

        String sql = "SELECT * FROM datasets WHERE uploaded_by = ? ORDER BY uploaded_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dataset ds = new Dataset(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("file_path"),
                        rs.getInt("uploaded_by"),
                        rs.getString("uploaded_at")
                );
                list.add(ds);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }

    // Fetch ALL datasets for admin
    public List<Dataset> getAllDatasets() {
        List<Dataset> list = new ArrayList<>();

        String sql = 
            "SELECT d.*, u.name AS uploader_name " +
            "FROM datasets d " +
            "JOIN users u ON d.uploaded_by = u.id " +
            "ORDER BY d.uploaded_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Dataset ds = new Dataset();
                ds.setId(rs.getInt("id"));
                ds.setName(rs.getString("name"));
                ds.setDescription(rs.getString("description"));
                ds.setFilePath(rs.getString("file_path"));
                ds.setUploadedBy(rs.getInt("uploaded_by"));
                ds.setUploadedAt(rs.getString("uploaded_at"));
                ds.setUploaderName(rs.getString("uploader_name"));

                list.add(ds);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }

    // Delete dataset
    public boolean deleteDataset(int id) {

        String sql = "DELETE FROM datasets WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return false;
    }

}
