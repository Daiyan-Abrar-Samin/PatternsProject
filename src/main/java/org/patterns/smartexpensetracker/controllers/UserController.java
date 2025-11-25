package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.User;

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
            success.setContentText("The user account has been successfully added");
            success.show();
        } catch (Exception ex) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("User Creation Failed:\n" + ex.getMessage());
            fail.show();
        }
    }
}
