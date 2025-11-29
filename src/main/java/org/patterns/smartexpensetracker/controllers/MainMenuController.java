package org.patterns.smartexpensetracker.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.views.MainMenuView;
import org.patterns.smartexpensetracker.views.TransactionView;

public class MainMenuController {

    private final MainMenuView view;
    private final Stage stage;

    public MainMenuController(MainMenuView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        wireButtons();
    }

    private void wireButtons() {
        setupUserButton();
        setupTransactionButton();
    }

    // 🔹 USER BUTTON → load UserView.fxml on SAME stage
    private void setupUserButton() {
        view.getUserButton().setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/org/patterns/smartexpensetracker/UserView.fxml"
                ));
                Parent root = loader.load();

                Scene scene = new Scene(root, 1000, 650);
                stage.setScene(scene);
                stage.setTitle("User Information");

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to open User screen:\n" + ex.getMessage());
                alert.showAndWait();
            }
        });
    }

    // 🔹 TRANSACTION BUTTON → use TransactionView + controller directly
    private void setupTransactionButton() {
        view.getTransactionButton().setOnAction(event -> {
            try {
                TransactionController controller = new TransactionController();
                TransactionView transactionView = new TransactionView(controller);

                Scene scene = new Scene(transactionView, 1000, 650);
                stage.setScene(scene);
                stage.setTitle("Expense Records");

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to open Expense Records screen:\n" + ex.getMessage());
                alert.showAndWait();
            }
        });
    }
}