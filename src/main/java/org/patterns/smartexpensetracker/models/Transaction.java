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
    private final StringProperty date;
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

    public static ObservableList<Transaction> filter(Double amount, String text) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        String sql;

        if (amount != null) {
            sql = "SELECT * FROM transactions WHERE amount = ?";
        } else {
            sql = "SELECT * FROM transactions WHERE category LIKE ? OR date LIKE ?";
        }

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (amount != null) {
                ps.setDouble(1, amount);
            } else {
                String pattern = "%" + text + "%";
                ps.setString(1, pattern);
                ps.setString(2, pattern);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transactionID"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getString("note")
                ));
            }

        } catch (SQLException e) {
            System.out.println("FILTER ERROR → " + e.getMessage());
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

    public static void delete(int transactionID) {
        String sql = "DELETE FROM transactions WHERE transactionID = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, transactionID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Transaction deletion failed: " + e.getMessage());
        }
    }

    public static void update(int id, double amount, String category, String type, String date, String note) {
        String sql = "UPDATE transactions SET amount=?, category=?, type=?, date=?, note=? WHERE transactionID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, category);
            ps.setString(3, type);
            ps.setString(4, date);
            ps.setString(5, note);
            ps.setInt(6, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Transaction update failed: " + e.getMessage());
        }
    }
}
