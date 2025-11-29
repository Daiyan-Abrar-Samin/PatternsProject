package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.User;
import org.patterns.smartexpensetracker.views.MainMenuView;

public class UserController {

    public UserController() {

    }

    public ObservableList<User> getUsers() {
        return User.getAllUsers();
    }

    public ObservableList<User> filterUsers(String userName, String firstName, String lastName) {
        return User.filterUsers(userName, firstName, lastName);
    }

    public void createUser(String username, String password, String firstName, String lastName, String phoneNumber) {

        try {
            User user = new User(0, username, password, firstName, lastName, phoneNumber);
            User.create(user);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("The user has been successfully added");
            success.show();

        } catch (Exception e) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("User Creation Failed:\n" + e.getMessage());
            fail.show();
        }
    }

    public void updateUser(int userId, String username, String password, String firstName, String lastName, String phoneNumber) {

        try {
            User.update(userId, username, password, firstName, lastName, phoneNumber);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
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

    public void deleteUser(int userId) {

        try {
            User.delete(userId);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
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
