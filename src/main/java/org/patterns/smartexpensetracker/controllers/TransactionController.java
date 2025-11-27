package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.Transaction;

public class TransactionController {

    public TransactionController() {

    }

    public ObservableList<Transaction> getTransactions() {
        return Transaction.getAllTransactions();
    }

    public static ObservableList<Transaction> filterTransactions(double amount, String category, String date) {
        return Transaction.filterTransactions(amount, category, date);
    }

    public void createTransaction(double amount, String category, String type, String date, String note) {

        try {
            Transaction transaction = new Transaction(1, amount, category, type, date, note);
            Transaction.create(transaction);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("The transaction has been successfully added");
            success.show();
        } catch (Exception ex) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Transaction Creation Failed:\n" + ex.getMessage());
            fail.show();
        }
    }
}
