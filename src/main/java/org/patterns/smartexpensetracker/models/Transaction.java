package org.patterns.smartexpensetracker.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.patterns.smartexpensetracker.controllers.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private final IntegerProperty transactionID;
    private final DoubleProperty amount;
    private final StringProperty category;
    private final StringProperty type;
    private static StringProperty date;
    private final StringProperty note;

    public Transaction(int transactionID, double amount, String category, String type, String date, String note) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.note = new SimpleStringProperty(note);
    }

    public int getTransactionID() {
        return transactionID.get();
    }

    public IntegerProperty transactionIDProperty() {
        return transactionID;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }


    public static ObservableList<Transaction> getAllTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM transactions";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getInt("transactionID"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("type"),
                        resultSet.getString("date"),
                        resultSet.getString("note")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public static ObservableList<Transaction> filterTransactions(double amount, String category, String type) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM transactions WHERE amount = ? OR category LIKE ? OR type LIKE ? OR date = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, " " + amount + " ");
            preparedStatement.setString(2, "%" + category + "%");
            preparedStatement.setString(3, "%" + type + "%");
            preparedStatement.setString(4, "'" + date + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getInt("transactionID"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("type"),
                        resultSet.getString("date"),
                        resultSet.getString("note")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public static void create(Transaction transaction) {
        String sql = "INSERT INTO transactions (amount, category, type, date, note) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(transaction.getAmount()));
            preparedStatement.setString(2, transaction.getCategory());
            preparedStatement.setString(3, transaction.getType());
            preparedStatement.setString(4, transaction.getDate());
            preparedStatement.setString(5, transaction.getNote());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Transaction creation failed: " + e.getMessage());
        }
    }
}
