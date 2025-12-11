package org.patterns.smartexpensetracker.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.views.CreateAccountView;

public class CreateAccountController {

    private final CreateAccountView view;
    private final Stage stage;
    private final UserController userController = new UserController();

    public CreateAccountController(CreateAccountView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        wireActions();
    }

    private void wireActions() {
        view.getCreateButton().setOnAction(e -> createAccount());
        view.getCancelButton().setOnAction(e -> stage.close());
    }

    private void createAccount() {
        String username = view.getUsername();
        String password = view.getPassword();
        String confirm = view.getConfirmPassword();
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        String phone = view.getPhone();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            showError("Passwords do not match.");
            return;
        }

        // Required valid username, password and phone number format to create user account
        boolean success = userController.createUser(username, password, firstName, lastName, phone);

        if (success) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("Account created successfully. You can now log in.");
            a.show();

            stage.close();
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}