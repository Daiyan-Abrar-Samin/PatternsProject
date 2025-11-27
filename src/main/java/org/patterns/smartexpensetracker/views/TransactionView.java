package org.patterns.smartexpensetracker.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.patterns.smartexpensetracker.controllers.TransactionController;
import org.patterns.smartexpensetracker.models.Transaction;

public class TransactionView extends VBox {
    TextField filterTextField;
    private final TableView<Transaction> tableView;
    private final TransactionController transactionController;

    private TextField amountText;
    private TextField categoryText;
    private TextField typeText;
    private TextField dateText;
    private TextField noteText;


    public TransactionView(TransactionController transactionController) {
        this.transactionController = transactionController;
        tableView = new TableView<>();

        filterInterface();
        createTable();
        bindTableData();
        bindTextFields();
        crudButtons();
        this.getChildren().addAll(filterInterface(), tableView, bindTextFields(), crudButtons());
    }

    private HBox filterInterface() {
        HBox hBox = new HBox(5);
        Label filterLabel = new Label("Data Filtering:");
        filterTextField = new TextField();

        filterTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String textValue = newValue.trim().toLowerCase();
            if (!textValue.isEmpty()) {
                tableView.setItems(TransactionController.filterTransactions(Integer.parseInt(textValue), textValue, textValue));
            } else {
                tableView.setItems(transactionController.getTransactions());
            }
        });

        hBox.getChildren().addAll(filterLabel, filterTextField); // searchButton in case button event handler
        hBox.setPadding(new Insets(5, 0, 10, 0));
        hBox.setAlignment(Pos.CENTER_LEFT);

        return hBox;
    }

    private void createTable() {
        TableColumn<Transaction, Integer> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(column -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value)); // << formatted here
                }
            }
        });

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tableView.getColumns().addAll(transactionIdCol, amountCol, categoryCol, typeCol, dateCol, noteCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    }

    private void bindTableData() {
        tableView.setItems(transactionController.getTransactions());
    }

    private HBox bindTextFields() {
        HBox hBox = new HBox(5);

        Label amountLabel = new Label("Amount: ");
        amountText = new TextField("");
        Label categoryLabel = new Label("Category: ");
        categoryText = new TextField("");
        Label typeLabel = new Label("Type: ");
        typeText = new TextField("");
        Label dateLabel = new Label("Date: ");
        dateText = new TextField("");
        Label noteLabel = new Label("Note: ");
        noteText = new TextField("");

        hBox.getChildren().addAll(amountLabel, amountText,
                categoryLabel, categoryText,
                typeLabel, typeText,
                dateLabel, dateText,
                noteLabel, noteText);
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.setAlignment(Pos.CENTER_LEFT);

        return hBox;
    }

    private HBox crudButtons() {
        HBox hBox = new HBox(10);
        Button createButton = new Button("ADD EXPENSE");

        createButton.setOnAction(event -> {
            transactionController.createTransaction(
                    Double.parseDouble(amountText.getText()),
                    categoryText.getText(),
                    typeText.getText(),
                    dateText.getText(),
                    noteText.getText()
            );
            bindTableData();
            clearForm();
        });

        hBox.getChildren().addAll(createButton);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void clearForm() {
        amountText.clear();
        categoryText.clear();
        typeText.clear();
        dateText.clear();
        noteText.clear();
    }
}
