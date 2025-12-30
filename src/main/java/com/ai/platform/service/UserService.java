package com.ai.platform.service;

import com.ai.platform.dao.UserDAO;
import com.ai.platform.model.User;
import com.ai.platform.util.ErrorLogger;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // ------------------ SIGNUP VALIDATION ------------------
    public String validateSignupData(String name, String email, String password) {

        if (name == null || name.trim().isEmpty()) {
            return "Name required";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Email required";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email format";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password required";
        }

        if (password.length() < 6) {
            return "Password too short";
        }

        // DB unique email check
        try {
            if (userDAO.getUserByEmail(email) != null) {
                return "Email already exists";
            }
        } catch (Exception e) {
            ErrorLogger.log(e);
            return "Server error";
        }

        return null; // null means valid
    }


    // ------------------ SIGNUP ACTION ------------------
    public boolean createUser(String name, String email, String password) {
        try {
            User user = new User();
            user.setName(name.trim());
            user.setEmail(email.trim());
            user.setPassword(password.trim());
            user.setRole("RESEARCHER");

            return userDAO.addUser(user);

        } catch (Exception e) {
            ErrorLogger.log(e);
            return false;
        }
    }


    // ------------------ LOGIN SERVICE ------------------
    public User login(String email, String password) {
        try {
            return userDAO.getUserByEmailAndPassword(email, password);
        } catch (Exception e) {
            ErrorLogger.log(e);
            return null;
        }
    }
}
