package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.Transaction;
import org.patterns.smartexpensetracker.session.Session;

public class TransactionController {

    public ObservableList<Transaction> getTransactions() {
        return Transaction.getTransactionsByUser(Session.getCurrentUser().getUserId());
    }

    public ObservableList<Transaction> getTransactionsByUser(int userId) {
        return Transaction.getTransactionsByUser(userId);
    }

    public ObservableList<Transaction> filterTransactions(Double amount, String text) {
        int userId = Session.getCurrentUser().getUserId();
        return Transaction.filter(amount, text, userId);
    }

    public boolean createTransaction(double amount, String category, String type, String date, String note) {

        if (amount <= 0) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Invalid Amount");
            fail.setHeaderText(null);
            fail.setContentText("Amount must be greater than 0.");
            fail.show();
            return false;
        }

        try {
            int userId = Session.getCurrentUser().getUserId();
            Transaction t = new Transaction(0, amount, category, type, date, note, userId);
            Transaction.create(t);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("The expense has been successfully added");
            success.show();
            return true;

        } catch (Exception ex) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Expense Creation Failed:\n" + ex.getMessage());
            fail.show();
            return false;
        }
    }

    public boolean updateTransaction(int id, double amount, String category, String type, String date, String note) {

        if (amount <= 0) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Invalid Amount");
            fail.setHeaderText(null);
            fail.setContentText("Amount must be greater than 0.");
            fail.show();
            return false;
        }

        try {
            Transaction.update(id, amount, category, type, date, note);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Updated");
            success.setHeaderText(null);
            success.setContentText("Expense " + id + " updated successfully.");
            success.show();
            return true;

        } catch (Exception e) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("Failed to update expense " + id);
            fail.show();
            return false;
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
}