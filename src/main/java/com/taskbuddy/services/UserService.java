package com.taskbuddy.services;

import com.taskbuddy.models.User;
import com.taskbuddy.utils.DatabaseConnection;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        users = new LinkedList<>();
        // Automatically generate 10 users
        for (int i = 1; i <= 10; i++) {
            String username = "user" + i;
            String email = username + "@mail.com";
            User.Role role = (i == 1) ? User.Role.ADMIN : User.Role.USER; // Use enum
            users.add(new User(i, username, email, "password" + i, role)); // Add password if needed
        }
    }

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

    public User findByUsername(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String roleStr = rs.getString("role");
                User.Role role = User.Role.valueOf(roleStr.toUpperCase());
                return new User(id, username, email, password, role);
            }
        } catch (Exception e) {
            System.err.println("Error finding user by username: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}