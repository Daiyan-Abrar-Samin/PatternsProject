package org.patterns.smartexpensetracker.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.session.Session;
import org.patterns.smartexpensetracker.views.LoginView;
import org.patterns.smartexpensetracker.views.MainMenuView;
import org.patterns.smartexpensetracker.views.TransactionView;
import org.patterns.smartexpensetracker.views.UserSettingsView;

public class MainMenuController {

    private final MainMenuView view;
    private final Stage stage;

    public MainMenuController(MainMenuView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        wireButtons();
    }

    private void wireButtons() {
        setupSettings();
        setupTransactions();
        setupLogout();
    }

    private void setupSettings() {
        view.getSettingsButton().setOnAction(e -> {
            UserSettingsView settingsView = new UserSettingsView(new UserController());
            stage.setScene(new Scene(settingsView, 1000, 650));
            stage.setTitle("Settings - " + Session.getCurrentUser().getUsername());
        });
    }

    private void setupTransactions() {
        view.getTransactionButton().setOnAction(e -> {
            TransactionView tView = new TransactionView(new TransactionController());
            stage.setScene(new Scene(tView, 1000, 650));
            stage.setTitle("Transactions - " + Session.getCurrentUser().getUsername());
        });
    }

    private void setupLogout() {
        view.getLogoutButton().setOnAction(e -> {
            Session.logout();
            Stage loginStage = new Stage();
            LoginView loginView = new LoginView(loginStage);
            loginStage.setScene(new Scene(loginView, 1000, 650));
            stage.close();
            loginStage.show();
        });
    }
}
