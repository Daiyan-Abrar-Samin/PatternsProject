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

    public User(int userId, String username, String password, String firstName, String lastName, String phoneNumber) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public int getUserId() {
        return userId.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNum")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static ObservableList<User> filterUsers(String userName, String firstName, String lastName) {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE userName LIKE ? OR firstName LIKE ? OR lastName LIKE ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + userName + "%");
            preparedStatement.setString(2, "%" + firstName + "%");
            preparedStatement.setString(3, "%" + lastName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNum")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static void create(User user) {
        String sql = "INSERT INTO users (username, password, firstName, lastName, phoneNum) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User creation failed: " + e.getMessage());
        }
    }

    public static void update(int userId, String username, String password, String firstName, String lastName, String phoneNumber) {
        String sql = "UPDATE users SET username = ?, password = ?, firstName = ?, lastName = ?, phoneNum = ? WHERE userID = ?";

        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setInt(6, userId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User update failed: " + e.getMessage());
        }
    }

    public static void delete(int userId) {
        String sql = "DELETE FROM users WHERE userID = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("User deletion failed: " + e.getMessage());
        }
    }
}
