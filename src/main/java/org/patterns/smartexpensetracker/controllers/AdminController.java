package org.patterns.smartexpensetracker.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.models.User;
import org.patterns.smartexpensetracker.views.AdminView;
import org.patterns.smartexpensetracker.views.TransactionView;

public class AdminController {

    private final AdminView view;
    private final UserController userController;

    public AdminController(AdminView view, UserController userController) {
        this.view = view;
        this.userController = userController;
        wireActions();
    }

    private void wireActions() {
        view.getDeleteButton().setOnAction(e -> deleteSelectedUser());
        view.getViewTransactionsButton().setOnAction(e -> openTransactions());
        view.getCloseButton().setOnAction(e -> ((Stage) view.getScene().getWindow()).close());
    }

    private void deleteSelectedUser() {
        User selected = view.getTableView().getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Please select a user to delete.");
            return;
        }

        // extra safety — prevent admin removal
        if (selected.getRole() != null && selected.getRole().equalsIgnoreCase("ADMIN")) {
            showError("Cannot delete the ADMIN account.");
            return;
        }

        userController.deleteUser(selected.getUserId());
        view.refreshTable();
    }

    private void openTransactions() {
        User selected = view.getTableView().getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Please select a user first.");
            return;
        }

        try {
            Stage stage = new Stage();

            // pass readOnly=true and selected user's ID
            TransactionView tv = new TransactionView(
                    new TransactionController(),
                    true,
                    selected.getUserId()
            );

            stage.setScene(new Scene(tv, 1000, 650));
            stage.setTitle("Transactions of " + selected.getUsername());
            stage.show();

        } catch (Exception e) {
            showError("Could not open transactions.");
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
