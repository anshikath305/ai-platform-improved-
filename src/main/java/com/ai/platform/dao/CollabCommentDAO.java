package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.CollabComment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollabCommentDAO {

    // Add comment for PROJECT (not post)
    public boolean createComment(CollabComment comment) {
        String sql = "INSERT INTO collab_comments (project_id, user_id, comment) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comment.getProjectId());
            stmt.setInt(2, comment.getUserId());
            stmt.setString(3, comment.getComment());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }
        return false;
    }

    // Get all comments for a project
    public List<CollabComment> getCommentsForProject(int projectId) {
        List<CollabComment> comments = new ArrayList<>();

        String sql = "SELECT * FROM collab_comments WHERE project_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CollabComment c = new CollabComment();
                c.setId(rs.getInt("id"));
                c.setProjectId(rs.getInt("project_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setComment(rs.getString("comment"));
                c.setCreatedAt(rs.getString("created_at"));

                comments.add(c);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return comments;
    }

    // Fetch user name for comment display
    public String getUserName(int userId) {
        String sql = "SELECT name FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return "Unknown User";
    }
    // Fetch ALL comments for admin panel
public List<CollabComment> getAllComments() {
    List<CollabComment> list = new ArrayList<>();

    String sql = "SELECT * FROM collab_comments ORDER BY created_at DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            CollabComment c = new CollabComment(
                rs.getInt("id"),
                rs.getInt("post_id"),
                rs.getInt("user_id"),
                rs.getString("comment"),
                rs.getString("created_at")
            );
            list.add(c);
        }

    } catch (Exception e) {
        ErrorLogger.log(e);

    }

    return list;
}

}
