package org.patterns.smartexpensetracker.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.patterns.smartexpensetracker.controllers.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private final IntegerProperty userId;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    private final StringProperty role;

    public User(int userId, String username, String password,
                String firstName, String lastName, String phoneNumber,
                String role) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.role = new SimpleStringProperty(role);
    }

    public int getUserId() { return userId.get(); }
    public IntegerProperty userIdProperty() { return userId; }

    public String getUsername() { return username.get(); }
    public StringProperty usernameProperty() { return username; }

    public String getPassword() { return password.get(); }
    public StringProperty passwordProperty() { return password; }

    public String getFirstName() { return firstName.get(); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getLastName() { return lastName.get(); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getPhoneNumber() { return phoneNumber.get(); }
    public StringProperty phoneNumberProperty() { return phoneNumber; }

    public String getRole() { return role.get(); }
    public StringProperty roleProperty() { return role; }

    // ---------- DB operations ----------

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNum"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.out.println("getAllUsers ERROR → " + e.getMessage());
        }
        return users;
    }

    public static ObservableList<User> filterUsers(String username, String firstName, String lastName) {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE username LIKE ? OR firstName LIKE ? OR lastName LIKE ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + username + "%");
            ps.setString(2, "%" + firstName + "%");
            ps.setString(3, "%" + lastName + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNum"),
                        rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.out.println("filterUsers ERROR → " + e.getMessage());
        }
        return users;
    }

    public static void create(User user) {
        String sql = "INSERT INTO users (username, password, firstName, lastName, phoneNum, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, "USER");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User creation failed: " + e.getMessage());
        }
    }


    public static void update(int userId, String username, String password,
                              String firstName, String lastName, String phoneNumber,
                              String role) {
        String sql = "UPDATE users SET username=?, password=?, firstName=?, lastName=?, phoneNum=?, role=? " +
                "WHERE userID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phoneNumber);
            ps.setString(6, role);
            ps.setInt(7, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User update failed: " + e.getMessage());
        }
    }

    public static void delete(int userId) {
        String sql = "DELETE FROM users WHERE userID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User deletion failed: " + e.getMessage());
        }
    }


    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNum"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }

    // ---------- authentication ----------

    public static User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNum"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("AUTH ERROR → " + e.getMessage());
        }
        return null;
    }
}