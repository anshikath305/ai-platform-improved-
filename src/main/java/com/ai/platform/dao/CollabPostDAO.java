package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.CollabPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollabPostDAO {

    public boolean createPost(CollabPost post) {
        String sql = "INSERT INTO collab_posts (user_id, content) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getContent());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return false;
    }


    public List<CollabPost> getAllPosts() {
        List<CollabPost> list = new ArrayList<>();

        String sql = "SELECT * FROM collab_posts ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CollabPost post = new CollabPost(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getString("created_at")
                );
                list.add(post);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);

        }

        return list;
    }
}
