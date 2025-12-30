package com.ai.platform.dao;
import com.ai.platform.util.ErrorLogger;
import com.ai.platform.db.DBConnection;
import com.ai.platform.model.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDAO {

    // ---------------- LOGIN AUTH ----------------
    public User getUserByEmailAndPassword(String email, String password) {

    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setString(2, password);

        try (ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        }

    } catch (Exception e) {
        ErrorLogger.log(e);
    }

    return null;
}


    // ---------------- CREATE USER - WITH TRANSACTION + LOG ----------------
    public boolean addUser(User user) {

        String sqlUser =
                "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        String sqlLog =
                "INSERT INTO user_logs(user_id, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false); // begin transaction

            try (PreparedStatement stmtUser =
                         conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

                stmtUser.setString(1, user.getName());
                stmtUser.setString(2, user.getEmail());
                stmtUser.setString(3, user.getPassword());
                stmtUser.setString(4, user.getRole());

                stmtUser.executeUpdate();

                ResultSet keys = stmtUser.getGeneratedKeys();
                int userId = -1;

                if (keys.next()) {
                    userId = keys.getInt(1);
                }

                if (userId == -1) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement stmtLog = conn.prepareStatement(sqlLog)) {
                    stmtLog.setInt(1, userId);
                    stmtLog.setString(2, "User account created successfully");
                    stmtLog.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }

    // ---------------- ALL USERS ----------------
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                list.add(u);
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return list;
    }

    // ---------------- DELETE USER ----------------
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);
        }
        return false;
    }

    // ---------------- FIND USER BY EMAIL ----------------
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return null;
    }

    // ---------------- GET USER BY ID ----------------
    public User getUserById(int id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return null;
    }

    // ---------------- UPDATE ROLE ----------------
    public boolean updateUserRole(int userId, String role) {

        String sql = "UPDATE users SET role=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return false;
    }

}
