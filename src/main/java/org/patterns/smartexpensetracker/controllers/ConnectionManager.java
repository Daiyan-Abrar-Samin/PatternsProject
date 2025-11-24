package org.patterns.smartexpensetracker.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/expense"; // or "jdbc:mysql://localhost:3306/expense"
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }
}
