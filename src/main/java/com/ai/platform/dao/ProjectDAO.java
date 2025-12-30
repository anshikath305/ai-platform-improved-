package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public int createProject(int ownerId, String name, String description, String firstLog) {
    String sqlProject = "INSERT INTO projects (name, description, owner_id) VALUES (?, ?, ?)";
    String sqlLog = "INSERT INTO project_members (project_id, user_id) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection()) {

        conn.setAutoCommit(false);

        // 1️⃣ Insert project
        try (PreparedStatement stmtProject =
                     conn.prepareStatement(sqlProject, Statement.RETURN_GENERATED_KEYS)) {

            stmtProject.setString(1, name);
            stmtProject.setString(2, description);
            stmtProject.setInt(3, ownerId);
            stmtProject.executeUpdate();

            ResultSet rs = stmtProject.getGeneratedKeys();
            int projectId = -1;

            if (rs.next()) {
                projectId = rs.getInt(1);
            }

            if (projectId == -1) {
                conn.rollback();
                return -1;
            }

            // 2️⃣ auto add owner as member
            try (PreparedStatement stmtMember = conn.prepareStatement(sqlLog)) {
                stmtMember.setInt(1, projectId);
                stmtMember.setInt(2, ownerId);
                stmtMember.executeUpdate();
            }

            // everything passed
            conn.commit();
            return projectId;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
    }

    return -1;
}



    public List<Project> getAllProjects() {
        List<Project> list = new ArrayList<>();

        String sql = "SELECT * FROM projects ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Project p = new Project();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setDescription(rs.getString("description"));
                p.setCreatedBy(rs.getInt("created_by"));
                p.setCreatedAt(rs.getString("created_at"));

                list.add(p);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }

    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return false;
    }
}
