package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CreateAccountView extends BorderPane {

    private final TextField usernameField;
    private final PasswordField passwordField;
    private final PasswordField confirmPasswordField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextField phoneField;
    private final Button createButton;
    private final Button cancelButton;

    public CreateAccountView() {
        setPadding(new Insets(20));

        Label title = new Label("Create New Account");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        createButton = new Button("Create Account");
        cancelButton = new Button("Cancel");

        VBox box = new VBox(10,
                title,
                usernameField,
                passwordField,
                confirmPasswordField,
                firstNameField,
                lastNameField,
                phoneField,
                createButton,
                cancelButton
        );
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));

        setCenter(box);
    }

    public String getUsername() { return usernameField.getText().trim(); }
    public String getPassword() { return passwordField.getText().trim(); }
    public String getConfirmPassword() { return confirmPasswordField.getText().trim(); }
    public String getFirstName() { return firstNameField.getText().trim(); }
    public String getLastName() { return lastNameField.getText().trim(); }
    public String getPhone() { return phoneField.getText().trim(); }

    public Button getCreateButton() { return createButton; }
    public Button getCancelButton() { return cancelButton; }
}