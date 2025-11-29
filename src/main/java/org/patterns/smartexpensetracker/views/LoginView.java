package org.patterns.smartexpensetracker.views;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.LoginController;

public class LoginView extends BorderPane {

    private final TextField usernameField;
    private final PasswordField passwordField;

    public LoginView(Stage stage) {

        // 💚 Soft green gradient background
        this.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #c7f5c7, #e8ffe8);"
        );

        // ---------- TITLE ----------
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


        // ---------- INPUT FIELDS ----------
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(180);     // 🔥 shorter width
        usernameField.setStyle(
                "-fx-background-radius: 8;" +
                        "-fx-padding: 6 10;" +
                        "-fx-border-color: #79c579;" +
                        "-fx-border-radius: 8;"
        );

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(180);     // 🔥 shorter width
        passwordField.setStyle(
                "-fx-background-radius: 8;" +
                        "-fx-padding: 6 10;" +
                        "-fx-border-color: #79c579;" +
                        "-fx-border-radius: 8;"
        );

        Label userLabel = new Label("Username:");
        userLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #185418;");

        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #185418;");


        // ---------- LOGIN BUTTON ----------
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(
                "-fx-background-color: #38a738;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 10 30;"
        );

        loginBtn.setOnAction(e -> new LoginController(this).login(stage));

        // FORM ELEMENTS (Stacked neatly)
        VBox form = new VBox(14,
                userLabel, usernameField,
                passLabel, passwordField,
                loginBtn
        );

        form.setAlignment(Pos.CENTER);


        // ---------- CENTER CONTAINER ----------
        VBox center = new VBox(form);
        center.setAlignment(Pos.CENTER);

        // Optional small shadow panel for style
        center.setStyle(
                "-fx-background-color: rgba(255,255,255,0.5);" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 30;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );

        center.setMaxWidth(280); // keeps it slim and elegant

        BorderPane.setMargin(center, new Insets(40, 40, 40, 40));
        this.setCenter(center);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }
}
