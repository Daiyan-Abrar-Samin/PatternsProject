package org.patterns.smartexpensetracker.factories;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.patterns.smartexpensetracker.controllers.TransactionController;

public class TransactionFactory {

    public static Pane createTransactionForm(TransactionController controller) {

        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(12);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        // ===== TEXT FIELDS =====
        TextField idField = new TextField();
        TextField amountField = new TextField();
        TextField categoryField = new TextField();
        TextField typeField = new TextField();
        TextField dateField = new TextField();
        TextField noteField = new TextField();

        idField.setPrefWidth(200);
        amountField.setPrefWidth(200);
        categoryField.setPrefWidth(200);
        typeField.setPrefWidth(200);
        dateField.setPrefWidth(200);
        noteField.setPrefWidth(200);

        // ===== LEFT & RIGHT LABEL + FIELD SETUP =====

        // LEFT COLUMN
        grid.add(new Label("Transaction ID:"), 0, 0);
        grid.add(idField, 1, 0);

        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);

        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryField, 1, 2);

        // RIGHT COLUMN
        grid.add(new Label("Type:"), 2, 0);
        grid.add(typeField, 3, 0);

        grid.add(new Label("Date:"), 2, 1);
        grid.add(dateField, 3, 1);

        grid.add(new Label("Note:"), 2, 2);
        grid.add(noteField, 3, 2);

        // ===== BUTTONS =====

        VBox buttons = new VBox(12);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button addBtn = new Button("Add Expense");
        Button updateBtn = new Button("Edit Expense");
        Button deleteBtn = new Button("Delete");
        Button clearBtn = new Button("Clear");
        Button closeBtn = new Button("Close");

        addBtn.setPrefWidth(130);
        updateBtn.setPrefWidth(130);
        deleteBtn.setPrefWidth(130);
        clearBtn.setPrefWidth(130);
        closeBtn.setPrefWidth(130);

        buttons.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn, closeBtn);
        grid.add(buttons, 4, 0, 1, 3);

        // ===== BUTTON ACTIONS =====

        addBtn.setOnAction(e -> {
            try {
                controller.createTransaction(
                        Double.parseDouble(amountField.getText()),
                        categoryField.getText(),
                        typeField.getText(),
                        dateField.getText(),
                        noteField.getText()
                );
                showInfo("Expense added successfully.");
            } catch (NumberFormatException ex) {
                showError("Invalid input. Make sure Amount is a number.");
            } catch (Exception ex) {
                showError("Error while adding expense:\n" + ex.getMessage());
            }
        });

        updateBtn.setOnAction(e -> {
            try {
                controller.updateTransaction(
                        Integer.parseInt(idField.getText()),
                        Double.parseDouble(amountField.getText()),
                        categoryField.getText(),
                        typeField.getText(),
                        dateField.getText(),
                        noteField.getText()
                );
                showInfo("Expense updated successfully.");
            } catch (NumberFormatException ex) {
                showError("Enter a valid Transaction ID and numeric Amount.");
            } catch (Exception ex) {
                showError("Error while updating expense:\n" + ex.getMessage());
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                controller.deleteTransaction(Integer.parseInt(idField.getText()));
                showInfo("Expense deleted successfully.");
            } catch (NumberFormatException ex) {
                showError("Enter a valid Transaction ID.");
            } catch (Exception ex) {
                showError("Error while deleting expense:\n" + ex.getMessage());
            }
        });

        clearBtn.setOnAction(e -> {
            idField.clear();
            amountField.clear();
            categoryField.clear();
            typeField.clear();
            dateField.clear();
            noteField.clear();
        });

        // For now, Close just closes the current window.
        // (You already have a GO BACK button in the header for navigation.)
        closeBtn.setOnAction(e -> {
            // This hides the window that contains this form
            closeBtn.getScene().getWindow().hide();
        });

        return grid;
    }

    private static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(message);
        a.show();
    }

    private static void showInfo(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(message);
        a.show();
    }
}
