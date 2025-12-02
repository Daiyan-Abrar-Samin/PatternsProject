package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.TransactionController;
import org.patterns.smartexpensetracker.factories.TransactionFactory;
import org.patterns.smartexpensetracker.models.Transaction;


public class TransactionView extends BorderPane {

    private final TableView<Transaction> tableView = new TableView<>();
    private TextField filterField;
    private final TransactionController controller;

    public TransactionView(TransactionController controller) {
        this.controller = controller;

        setPadding(new Insets(15));

        // TOP FILTER + BACK BUTTON
        this.setTop(buildHeader());

        // CENTER TABLE
        this.setCenter(buildTable());

        // BOTTOM CRUD FORM FROM FACTORY
        this.setBottom(TransactionFactory.createTransactionForm(controller));

        // INITIAL LOAD
        refreshTable();
    }

    // ---------------- HEADER (Filter + Back Button) ----------------

    private HBox buildHeader() {
        HBox top = new HBox(15);

        Label f = new Label("Filter:");
        filterField = new TextField();
        filterField.setPromptText("Search Amount / Category / Date");

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

    // ---------------- LOGIC ----------------

    public void refreshTable() {
        tableView.setItems(controller.getTransactions());
    }

    private void filter(String v) {
        if (v == null || v.isEmpty()) {
            refreshTable();
            return;
        }

        if (v.matches("\\d+(\\.\\d+)?")) {
            tableView.setItems(controller.filterTransactions(Double.valueOf(v), ""));
        } else {
            tableView.setItems(controller.filterTransactions(null, v));
        }
    }

    private void goBack() {
        Stage st = (Stage) getScene().getWindow();
        st.setScene(new Scene(new MainMenuView(), 1000, 650));
    }

    private void alert(String t) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(t);
        a.show();
    }
}