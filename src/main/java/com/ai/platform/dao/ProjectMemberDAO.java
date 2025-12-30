package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.ProjectMember;
import com.ai.platform.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberDAO {

    // Add member to project
    public boolean addMember(int projectId, int userId) {

    String sql = "INSERT INTO project_members(project_id, user_id) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection()) {

        conn.setAutoCommit(false);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);

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


    // Get user_ids of members
    public List<Integer> getMembersByProject(int projectId) {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT user_id FROM project_members WHERE project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return userIds;
    }

    // Get full user details of members
    public List<User> getMemberDetails(int projectId) {
        List<User> list = new ArrayList<>();

        String sql =
            "SELECT u.id, u.name, u.email, u.role " +
            "FROM project_members pm " +
            "JOIN users u ON pm.user_id = u.id " +
            "WHERE pm.project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }
}
