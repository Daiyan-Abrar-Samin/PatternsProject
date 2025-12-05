package org.patterns.smartexpensetracker.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.models.User;
import org.patterns.smartexpensetracker.session.Session;
import org.patterns.smartexpensetracker.views.AdminView;
import org.patterns.smartexpensetracker.views.LoginView;
import org.patterns.smartexpensetracker.views.MainMenuView;

public class LoginController {

    public void attemptLogin(String username, String password, Stage loginStage) {

        // 1. Authenticate through DB
        User loggedUser = User.login(username, password);

        if (loggedUser == null) {
            showError("Invalid username or password.");
            return;
        }

        // 2. Store session
        Session.setCurrentUser(loggedUser);

        // 3. Close login screen
        loginStage.close();

        // 4. Route based on role
        if (loggedUser.getRole().equalsIgnoreCase("ADMIN")) {
            openAdminPanel();
        } else {
            openUserMenu();
        }
    }

    /* ========= ROUTING ========= */

    private void openAdminPanel() {
        try {
            Stage stage = new Stage();

            AdminView adminView = new AdminView();
            Scene scene = new Scene(adminView, 1000, 650);

            stage.setScene(scene);
            stage.setTitle("Admin Panel");
            stage.show();

        } catch (Exception e) {
            showError("Error opening admin screen:\n" + e.getMessage());
        }
    }

    private void openUserMenu() {
        try {
            Stage stage = new Stage();

            MainMenuView menuView = new MainMenuView();
            new MainMenuController(menuView, stage);

            Scene scene = new Scene(menuView, 1000, 650);

            stage.setScene(scene);
            stage.setTitle("Smart Expense Tracker");
            stage.show();

        } catch (Exception e) {
            showError("Error opening main menu:\n" + e.getMessage());
        }
    }

    /* ========= ERROR POPUP ========= */

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
