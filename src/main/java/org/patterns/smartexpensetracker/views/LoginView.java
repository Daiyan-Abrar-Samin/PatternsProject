package org.patterns.smartexpensetracker.views;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.CreateAccountController;
import org.patterns.smartexpensetracker.controllers.LoginController;

public class LoginView extends BorderPane {

    private final TextField usernameField;
    private final PasswordField passwordField;

    public LoginView(Stage stage) {
        this.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #c7f5c7, #e8ffe8);"
        );

        Label title = new Label("Smart Expense Tracker");
        title.setStyle(
                "-fx-font-size: 32px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #145214;"
        );

        Label subtitle = new Label("Please log in to continue");
        subtitle.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #2d6a2d;" +
                        "-fx-opacity: 0.9;"
        );

        VBox titleBox = new VBox(6, title, subtitle);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(40, 0, 30, 0));
        this.setTop(titleBox);

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(180);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(180);

        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> new LoginController().attemptLogin(getUsername(), getPassword(), stage));

        VBox form = new VBox(14,
                userLabel, usernameField,
                passLabel, passwordField,
                loginBtn
        );
        form.setAlignment(Pos.CENTER);

        VBox center = new VBox(form);
        center.setAlignment(Pos.CENTER);
        center.setStyle(
                "-fx-background-color: rgba(255,255,255,0.5);" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 30;"
        );
        center.setMaxWidth(280);

        BorderPane.setMargin(center, new Insets(40, 40, 40, 40));
        this.setCenter(center);

        // Create Account button
        Button createAccountBtn = new Button("Create Account");
        createAccountBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #145214; -fx-underline: true;");
        createAccountBtn.setOnAction(e -> {
            CreateAccountView cav = new CreateAccountView();
            Stage s = new Stage();
            new CreateAccountController(cav, s);
            s.setScene(new Scene(cav, 500, 400));
            s.setTitle("Create Account");
            s.show();
        });

        HBox bottomBox = new HBox(createAccountBtn);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(0, 0, 20, 20));
        this.setBottom(bottomBox);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }
}