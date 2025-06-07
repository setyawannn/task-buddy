package com.taskbuddy.services;

import com.taskbuddy.models.User;
import com.taskbuddy.utils.DatabaseConnection;
import java.sql.*;

public class UserService {

    public boolean registerUser(String username, String email, String password) {
        try {
            if (isUsernameExists(username)) {
                System.out.println("ERROR: Username already exists!");
                return false;
            }

            if (isEmailExists(email)) {
                System.out.println("ERROR: Email already exists!");
                return false;
            }

            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, "USER");

            int result = stmt.executeUpdate();

            if (result > 0) {
                System.out.println("Registration successful! Welcome " + username + "!");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
        }

        return false;
    }

    public User loginUser(String username, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")));

                System.out.println("Login successful! Welcome back, " + username + "!");
                return user;
            } else {
                System.out.println("ERROR: Invalid username or password!");
            }

        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }

        return null;
    }

    private boolean isUsernameExists(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }

        return false;
    }

    private boolean isEmailExists(String email) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        }

        return false;
    }

    public User getUserById(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")));
            }

        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }

        return null;
    }
}