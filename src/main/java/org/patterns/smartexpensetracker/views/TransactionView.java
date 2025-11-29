package org.patterns.smartexpensetracker.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.TransactionController;
import org.patterns.smartexpensetracker.models.Transaction;


public class TransactionView extends BorderPane {

    private TableView<Transaction> tableView = new TableView<>();
    private TextField amountText, categoryText, typeText, dateText, noteText;
    private TextField deleteField, filterField;
    private final TransactionController controller;

    public TransactionView(TransactionController controller) {
        this.controller = controller;

        setPadding(new Insets(15));

        this.setTop(buildHeader());
        setCenter(buildTable());
        setBottom(buildFormSection());

        refreshTable();
    }

    // ---------------- HEADER (Filter + Back Button) ----------------

    private HBox buildHeader() {
        HBox top = new HBox(15);

        Label f = new Label("Filter:");
        filterField = new TextField();
        filterField.setPromptText("Search Amount / Category / Date");

        // THIS is the missing connection — filter updates when typing
        filterField.textProperty().addListener((obs, oldV, newV) -> filter(newV));

        Button back = new Button("GO BACK");
        back.setOnAction(e -> goBack());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        top.getChildren().addAll(f, filterField, spacer, back);
        top.setPadding(new Insets(10));
        return top;
    }

    // ---------------- TABLE ----------------

    private TableView<Transaction> buildTable() {
        tableView.setPlaceholder(new Label("No content in table"));

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("Transaction ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount (in dollars)");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> catCol = new TableColumn<>("Category of Expense");
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type of Expense");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> noteCol = new TableColumn<>("Note (optional)");
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tableView.getColumns().addAll(idCol, amountCol, catCol, typeCol, dateCol, noteCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        return tableView;
    }

    // ---------------- BOTTOM PANEL ----------------

    private VBox buildFormSection() {

        VBox main = new VBox(25);
        main.setPadding(new Insets(20, 0, 20, 0));

        HBox row = new HBox(120);
        row.setAlignment(Pos.CENTER);


        /* =========  LEFT  (ADD EXPENSE) ========= */
        GridPane add = new GridPane();
        add.setHgap(12);  add.setVgap(12);

        amountText = new TextField();
        categoryText = new TextField();
        typeText = new TextField();
        dateText = new TextField();
        noteText = new TextField();

        add.add(new Label("Amount"),0,0);     add.add(amountText,1,0);
        add.add(new Label("Category"),0,1);   add.add(categoryText,1,1);
        add.add(new Label("Type"),0,2);       add.add(typeText,1,2);
        add.add(new Label("Date"),0,3);       add.add(dateText,1,3);
        add.add(new Label("Note"),0,4);       add.add(noteText,1,4);

        Button addBtn = new Button("Add Expense");
        addBtn.setPrefWidth(150);
        addBtn.setOnAction(e -> addExpense());

        VBox addCol = new VBox(18, add, addBtn);
        addCol.setAlignment(Pos.CENTER_LEFT);



        /* =========  CENTER  (DELETE EXPENSE) ========= */
        VBox deleteCol = new VBox(20);
        deleteCol.setAlignment(Pos.TOP_CENTER);

        deleteField = new TextField();
        deleteField.setPrefWidth(160);

        Button delBtn = new Button("Delete Expense");
        delBtn.setPrefWidth(150);
        delBtn.setOnAction(e -> deleteExpense());

        deleteCol.getChildren().addAll(
                new Label("Transaction ID"),
                deleteField,
                delBtn
        );

        deleteCol.setTranslateY(30);  // << lowers to match picture EXACTLY



        /* =========  RIGHT  (EDIT EXPENSE) ========= */
        GridPane edit = new GridPane();
        edit.setHgap(12); edit.setVgap(12);

        TextField uid = new TextField();
        TextField uAmount = new TextField();
        TextField uCat = new TextField();
        TextField uType = new TextField();
        TextField uDate = new TextField();
        TextField uNote = new TextField();

        edit.add(new Label("Transaction ID"),0,0); edit.add(uid,1,0);
        edit.add(new Label("Amount"),0,1);         edit.add(uAmount,1,1);
        edit.add(new Label("Category"),0,2);       edit.add(uCat,1,2);
        edit.add(new Label("Type"),0,3);           edit.add(uType,1,3);
        edit.add(new Label("Date"),0,4);           edit.add(uDate,1,4);
        edit.add(new Label("Note"),0,5);           edit.add(uNote,1,5);

        Button editBtn = new Button("Edit Expense");
        editBtn.setPrefWidth(150);
        editBtn.setOnAction(e -> controller.updateTransaction(
                Integer.parseInt(uid.getText()),
                Double.parseDouble(uAmount.getText()),
                uCat.getText(), uType.getText(),
                uDate.getText(), uNote.getText()
        ));

        VBox editCol = new VBox(18, edit, editBtn);
        editCol.setAlignment(Pos.CENTER_LEFT);



        /* =========  ADD ALL COLUMNS IN ONE ROW ========= */
        row.getChildren().addAll(addCol, deleteCol, editCol);
        main.getChildren().add(row);

        return main;
    }
//    private VBox buildFormSection() {
//        VBox main = new VBox(5);
//        main.setPadding(new Insets(10));
//
//        HBox row = new HBox(10);
//
//        // ADD EXPENSE FORM
//        GridPane left = new GridPane();
//        left.setVgap(10); left.setHgap(10);
//
//        amountText = new TextField();
//        categoryText = new TextField();
//        typeText = new TextField();
//        dateText = new TextField();
//        noteText = new TextField();
//
//        left.add(new Label("Amount"), 0,0);   left.add(amountText,1,0);
//        left.add(new Label("Category"),0,1);  left.add(categoryText,1,1);
//        left.add(new Label("Type"),0,2);      left.add(typeText,1,2);
//        left.add(new Label("Date"),0,3);      left.add(dateText,1,3);
//        left.add(new Label("Note"),0,4);      left.add(noteText,1,4);
//
//        Button addBtn = new Button("Add Expense");
//        addBtn.setOnAction(e -> addExpense());
//        left.add(addBtn,1,5);
//
//
//        // DELETE EXPENSE FORM (CENTER)
//        VBox center = new VBox(10);
//        Label d = new Label("Transaction ID");
//        deleteField = new TextField();
//
//        Button delBtn = new Button("Delete Expense");
//        delBtn.setOnAction(e -> deleteExpense());
//
//        Button updateBtn = new Button("Update Expense");
//        updateBtn.setOnAction(e -> updateExpense());
//
//        center.getChildren().addAll(d, deleteField, delBtn, updateBtn);
//        center.setAlignment(Pos.TOP_CENTER);
//
//        // |LEFT| |CENTER|  (Right empty for now)
//        Region fill = new Region();
//        row.getChildren().addAll(left, new Separator(), center, fill);
//        row.setPrefHeight(200);
//        HBox.setHgrow(fill, Priority.ALWAYS);
//
//        main.getChildren().add(row);
//        return main;
//    }

    // ---------------- LOGIC ----------------

    private void refreshTable() {
        tableView.setItems(controller.getTransactions());
    }

    private void filter(String v) {
        System.out.println("FILTER TRIGGERED -> " + v); // debug

        if(v == null || v.isEmpty()) {
            refreshTable();
            return;
        }

        // If numbers → filter amount
        if(v.matches("\\d+(\\.\\d+)?")) {
            tableView.setItems(controller.filterTransactions(Double.valueOf(v), ""));
        }
        // Text → filter category OR date
        else {
            tableView.setItems(controller.filterTransactions(null, v));
        }
    }

    private void addExpense() {
        try {
            controller.createTransaction(Double.parseDouble(amountText.getText()), categoryText.getText(), typeText.getText(), dateText.getText(), noteText.getText());
            refreshTable();
        } catch(Exception e) { alert("Invalid input in Amount"); }
    }

    private void deleteExpense() {
        try {
            controller.deleteTransaction(Integer.parseInt(deleteField.getText()));
            refreshTable();
        } catch(Exception e) { alert("Invalid Transaction ID"); }
    }

    private void updateExpense() {
        try {
            controller.updateTransaction(
                    Integer.parseInt(deleteField.getText()),         // TransactionID field reused
                    Double.parseDouble(amountText.getText()),
                    categoryText.getText(),
                    typeText.getText(),
                    dateText.getText(),
                    noteText.getText()
            );

            refreshTable();

        } catch (Exception e) {
            alert("Invalid input. Enter ID + all fields to update.");
        }
    }

    private void goBack(){
        Stage st = (Stage) getScene().getWindow();
        st.setScene(new Scene(new MainMenuView(), 1000, 650));
    }

    private void alert(String t){
        Alert a=new Alert(Alert.AlertType.ERROR); a.setHeaderText(null); a.setContentText(t); a.show();
    }
}
















//public class TransactionView extends VBox {
//    TextField filterTextField;
//    private final TableView<Transaction> tableView;
//    private final TransactionController transactionController;
//
//    private TextField amountText;
//    private TextField categoryText;
//    private TextField typeText;
//    private TextField dateText;
//    private TextField noteText;
//
//
//    public TransactionView(TransactionController transactionController) {
//        this.transactionController = transactionController;
//        tableView = new TableView<>();
//
//        filterInterface();
//        createTable();
//        bindTableData();
//        bindTextFields();
//        crudButtons();
//        this.getChildren().addAll(filterInterface(), tableView, bindTextFields(), crudButtons());
//    }
//
//    private HBox filterInterface() {
//        HBox hBox = new HBox(5);
//        Label filterLabel = new Label("Data Filtering:");
//        filterTextField = new TextField();
//
//        filterTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
//            String textValue = newValue.trim().toLowerCase();
//            if (!textValue.isEmpty()) {
//                tableView.setItems(TransactionController.filterTransactions(Double.parseDouble(textValue), textValue, textValue));
//            } else {
//                tableView.setItems(transactionController.getTransactions());
//            }
//        });
//
//        hBox.getChildren().addAll(filterLabel, filterTextField); // searchButton in case button event handler
//        hBox.setPadding(new Insets(5, 0, 10, 0));
//        hBox.setAlignment(Pos.CENTER_LEFT);
//
//        return hBox;
//    }
//
//    private void createTable() {
//        TableColumn<Transaction, Integer> transactionIdCol = new TableColumn<>("Transaction ID");
//        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
//
//        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
//        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
//        amountCol.setCellFactory(column -> new TableCell<Transaction, Double>() {
//            @Override
//            protected void updateItem(Double value, boolean empty) {
//                super.updateItem(value, empty);
//                if (empty || value == null) {
//                    setText(null);
//                } else {
//                    setText(String.format("%.2f", value)); // << formatted here
//                }
//            }
//        });
//
//        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
//        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
//
//        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
//        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
//
//        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
//        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//
//        TableColumn<Transaction, String> noteCol = new TableColumn<>("Note");
//        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
//
//        tableView.getColumns().addAll(transactionIdCol, amountCol, categoryCol, typeCol, dateCol, noteCol);
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
//
//    }
//
//    private void bindTableData() {
//        tableView.setItems(transactionController.getTransactions());
//    }
//
//    private HBox bindTextFields() {
//        HBox hBox = new HBox(5);
//
//        Label amountLabel = new Label("Amount: ");
//        amountText = new TextField("");
//        Label categoryLabel = new Label("Category: ");
//        categoryText = new TextField("");
//        Label typeLabel = new Label("Type: ");
//        typeText = new TextField("");
//        Label dateLabel = new Label("Date: ");
//        dateText = new TextField("");
//        Label noteLabel = new Label("Note: ");
//        noteText = new TextField("");
//
//        hBox.getChildren().addAll(amountLabel, amountText,
//                categoryLabel, categoryText,
//                typeLabel, typeText,
//                dateLabel, dateText,
//                noteLabel, noteText);
//        hBox.setPadding(new Insets(10, 0, 10, 0));
//        hBox.setAlignment(Pos.CENTER_LEFT);
//
//        return hBox;
//    }
//
//    private HBox crudButtons() {
//        HBox hBox = new HBox(10);
//
//        // CREATE button
//        Button createButton = new Button("Create");
//
//        // DELETE UI
//        TextField deleteField = new TextField();
//        deleteField.setPromptText("Transaction ID");
//        deleteField.setPrefWidth(100);
//        Button deleteButton = new Button("Delete");
//
//        // CREATE Action
//        createButton.setOnAction(event -> {
//            try {
//                double amount = Double.parseDouble(amountText.getText());
//                transactionController.createTransaction(
//                        amount,
//                        categoryText.getText(),
//                        typeText.getText(),
//                        dateText.getText(),
//                        noteText.getText()
//                );
//                bindTableData();
//                clearForm();
//            } catch (NumberFormatException e) {
//                showAlert("Amount must be a number.");
//            }
//        });
//
//        // DELETE Action
//        deleteButton.setOnAction(event -> {
//            try {
//                int id = Integer.parseInt(deleteField.getText());
//                transactionController.deleteTransaction(id);
//                bindTableData();
//                deleteField.clear();
//            } catch (NumberFormatException e) {
//                showAlert("Transaction ID must be a number.");
//            }
//        });
//
//        hBox.getChildren().addAll(createButton, deleteField, deleteButton);
//        hBox.setAlignment(Pos.CENTER);
//
//        return hBox;
//    }
//
//    // Helper popup message
//    private void showAlert(String msg) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setHeaderText(null);
//        alert.setContentText(msg);
//        alert.show();
//    }
//
//    private void clearForm() {
//        amountText.clear();
//        categoryText.clear();
//        typeText.clear();
//        dateText.clear();
//        noteText.clear();
//    }
//}