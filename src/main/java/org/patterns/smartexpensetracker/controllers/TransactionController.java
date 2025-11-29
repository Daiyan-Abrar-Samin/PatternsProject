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

    public ObservableList<Transaction> filterTransactions(Double amount, String text) {
        return Transaction.filter(amount, text);
    }

    public void createTransaction(double amount, String category, String type, String date, String note) {

        try {
            Transaction transaction = new Transaction(1, amount, category, type, date, note);
            Transaction.create(transaction);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("The expense has been successfully added");
            success.show();
        } catch (Exception ex) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Expense Creation Failed:\n" + ex.getMessage());
            fail.show();
        }
    }

    public void deleteTransaction(int transactionID) {
        try {
            Transaction.delete(transactionID);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Deleted");
            success.setHeaderText(null);
            success.setContentText("Expense " + transactionID + " removed successfully.");
            success.show();

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to delete expense with ID: " + transactionID);
            error.show();
        }
    }

    public void updateTransaction(int id, double amount, String category, String type, String date, String note) {
        try {
            Transaction.update(id, amount, category, type, date, note);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Updated");
            success.setHeaderText(null);
            success.setContentText("Expense " + id + " updated successfully.");
            success.show();

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Failed to update expense " + id);
            error.show();
        }
    }
}
