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

    public int getTransactionID() { return transactionID.get(); }
    public IntegerProperty transactionIDProperty() { return transactionID; }

    public double getAmount() { return amount.get(); }
    public DoubleProperty amountProperty() { return amount; }

    public String getCategory() { return category.get(); }
    public StringProperty categoryProperty() { return category; }

    public String getType() { return type.get(); }
    public StringProperty typeProperty() { return type; }

    public String getDate() { return date.get(); }
    public StringProperty dateProperty() { return date; }

    public String getNote() { return note.get(); }
    public StringProperty noteProperty() { return note; }

    public static ObservableList<Transaction> getAllTransactions() {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("transactionID"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getString("note")
                ));
            }
        } catch (SQLException e) {
            System.out.println("getAllTransactions ERROR → " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Transaction> filter(Double amount, String text) {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        String sql;

        if (amount != null) {
            sql = "SELECT * FROM transactions WHERE amount = ?";
        } else {
            sql = "SELECT * FROM transactions WHERE category LIKE ? OR date LIKE ?";
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (amount != null) {
                ps.setDouble(1, amount);
            } else {
                String pattern = "%" + text + "%";
                ps.setString(1, pattern);
                ps.setString(2, pattern);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
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
        return list;
    }

    public static void create(Transaction t) {
        String sql = "INSERT INTO transactions (amount, category, type, date, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, t.getAmount());
            ps.setString(2, t.getCategory());
            ps.setString(3, t.getType());
            ps.setString(4, t.getDate());
            ps.setString(5, t.getNote());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Transaction creation failed: " + e.getMessage());
        }
    }

    public static void update(int id, double amount, String category, String type, String date, String note) {
        String sql = "UPDATE transactions SET amount=?, category=?, type=?, date=?, note=? WHERE transactionID=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

    public static void delete(int id) {
        String sql = "DELETE FROM transactions WHERE transactionID=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Transaction deletion failed: " + e.getMessage());
        }
    }

    public static ObservableList<Transaction> getTransactionsByUser(int userId) {
        ObservableList<Transaction> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM transactions WHERE userID = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("transactionID"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getString("note")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

}