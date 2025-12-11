package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.User;

import java.util.ArrayDeque;

public class UserController {

    // ArrayDeque to support business logic operation.
    private final ArrayDeque<String> errorLog = new ArrayDeque<>();

    // Validate Username, Password, Phone Number and show errors (Business Logic Operation)
    private boolean validateAndShowErrors(String username, String password, String phone) {
        errorLog.clear(); // clear previous errors

        if (username == null || !username.matches("[a-zA-Z0-9]{4,12}"))
            errorLog.add("Username must be 4-12 letters/digits only");

        if (password == null || !password.matches("[a-zA-Z0-9]{3,}"))
            errorLog.add("Password must be at least 3 letters/digits");

        if (phone == null || !phone.matches("\\(\\d{3}\\) \\d{3}-\\d{4}"))
            errorLog.add("Phone must be in format (ddd) ddd-dddd");

        if (!errorLog.isEmpty()) {
            // Show all errors at once
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Validation Error");
            alert.setHeaderText(null);
            alert.setContentText(String.join("\n", errorLog));
            alert.show();
            return false; // if input is invalid
        }
        return true; // if input is valid
    }

    public ObservableList<User> getUsers() {
        return User.getAllUsers();
    }

    public ObservableList<User> filterUsers(String userName, String firstName, String lastName) {
        return User.filterUsers(userName, firstName, lastName);
    }

    // Used by Create Account
    public boolean createUser(String username, String password,
                           String firstName, String lastName, String phoneNumber) {

        // Invert valid to false and invalid to true
        // so the method stops via return keyword which ensure invalid input is not stored in Database
        // and shows error message
        if (!validateAndShowErrors(username, password, phoneNumber)) return false;

        try {
            User user = new User(0, username, password, firstName, lastName, phoneNumber, "USER");
            User.create(user);
            return true;

        } catch (Exception e) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("User Creation Failed:\n" + e.getMessage());
            fail.show();
            return false;
        }
    }

    // Used by UserSettingsView
    public void updateUser(int userId, String username, String password,
                           String firstName, String lastName, String phoneNumber, String role) {

        if (!validateAndShowErrors(username, password, phoneNumber)) return;

        try {
            User.update(userId, username, password, firstName, lastName, phoneNumber, role);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Updated");
            success.setHeaderText(null);
            success.setContentText("User " + userId + " has been successfully updated");
            success.show();

        } catch (Exception e) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Update failed for User " + userId);
            fail.show();
        }
    }

    // Used by admin
    public void deleteUser(int userId) {
        try {
            User.delete(userId);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Removed");
            success.setHeaderText(null);
            success.setContentText("User " + userId + " has been successfully removed");
            success.show();

        } catch (Exception e) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Failed to remove User " + userId);
            fail.show();
        }
    }
}