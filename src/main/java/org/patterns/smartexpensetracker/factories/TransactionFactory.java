package org.patterns.smartexpensetracker.factories;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.TableView;
import org.patterns.smartexpensetracker.controllers.TransactionController;
import org.patterns.smartexpensetracker.models.Transaction;
import org.patterns.smartexpensetracker.views.TransactionView;

public class TransactionFactory {

    private static TextField idField;
    private static TextField amountField;
    private static TextField categoryField;
    private static TextField typeField;
    private static TextField dateField;
    private static TextField noteField;

    public static Pane createTransactionForm(TransactionController controller,
                                             TransactionView view,
                                             boolean readOnly) {

        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(12);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        idField = new TextField();
        amountField = new TextField();
        categoryField = new TextField();
        typeField = new TextField();
        dateField = new TextField();
        noteField = new TextField();

        idField.setPrefWidth(200);
        amountField.setPrefWidth(200);
        categoryField.setPrefWidth(200);
        typeField.setPrefWidth(200);
        dateField.setPrefWidth(200);
        noteField.setPrefWidth(200);

        grid.add(new Label("Transaction ID:"), 0, 0);
        grid.add(idField, 1, 0);

        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);

        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryField, 1, 2);

        grid.add(new Label("Type:"), 2, 0);
        grid.add(typeField, 3, 0);

        grid.add(new Label("Date:"), 2, 1);
        grid.add(dateField, 3, 1);

        grid.add(new Label("Note:"), 2, 2);
        grid.add(noteField, 3, 2);

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

        if (readOnly) {
            addBtn.setDisable(true);
            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);
            clearBtn.setDisable(true);
        } else {
            addBtn.setOnAction(e -> {
                try {
                    if (controller.createTransaction(
                            Double.parseDouble(amountField.getText()),
                            categoryField.getText(),
                            typeField.getText(),
                            dateField.getText(),
                            noteField.getText()
                    )) {
                        view.refreshTable();
                        clearForm();
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid input. Make sure Amount is a number.");
                }
            });

            updateBtn.setOnAction(e -> {
                try {
                    if (controller.updateTransaction(
                            Integer.parseInt(idField.getText()),
                            Double.parseDouble(amountField.getText()),
                            categoryField.getText(),
                            typeField.getText(),
                            dateField.getText(),
                            noteField.getText()
                    )) {
                        view.refreshTable();
                        clearForm();
                    }
                } catch (NumberFormatException ex) {
                    showError("Enter a valid ID and numeric Amount.");
                }
            });

            deleteBtn.setOnAction(e -> {
                try {
                    controller.deleteTransaction(Integer.parseInt(idField.getText()));
                    view.refreshTable();
                    clearForm();
                } catch (NumberFormatException ex) {
                    showError("Enter a valid Transaction ID.");
                }
            });

            clearBtn.setOnAction(e -> clearForm());
        }

        closeBtn.setOnAction(e -> closeBtn.getScene().getWindow().hide());

        return grid;
    }

    public static void loadSelectedTransactionIntoForm(TableView<Transaction> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getTransactionID()));
                amountField.setText(String.valueOf(newSel.getAmount()));
                categoryField.setText(newSel.getCategory());
                typeField.setText(newSel.getType());
                dateField.setText(newSel.getDate());
                noteField.setText(newSel.getNote());
            }
        });
    }

    private static void clearForm() {
        idField.clear();
        amountField.clear();
        categoryField.clear();
        typeField.clear();
        dateField.clear();
        noteField.clear();
    }

    private static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(message);
        a.show();
    }
}