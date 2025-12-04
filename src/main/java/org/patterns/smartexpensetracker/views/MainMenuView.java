package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MainMenuView extends BorderPane {

    private final Button settingsButton;
    private final Button transactionButton;
    private final Button logoutButton;

    public MainMenuView() {

        setStyle("-fx-background-color:#e9fff4;");

        logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color:red; -fx-text-fill:white;");
        HBox logoutBox = new HBox(logoutButton);
        logoutBox.setAlignment(Pos.TOP_RIGHT);
        logoutBox.setPadding(new Insets(10, 15, 0, 0));
        setTop(logoutBox);

        Label title = new Label("Smart Expense Tracker");
        title.setStyle("-fx-font-size:36px; -fx-font-weight:bold; -fx-text-fill:#0b6845;");
        title.setAlignment(Pos.CENTER);

        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));

        settingsButton = new Button("Settings");
        transactionButton = new Button("Transactions");

        style(settingsButton);
        style(transactionButton);

        HBox buttons = new HBox(40, settingsButton, transactionButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(header, buttons);
        layout.setSpacing(40);
        layout.setAlignment(Pos.CENTER);

        setCenter(layout);
    }

    private void style(Button b) {
        b.setPrefSize(250, 60);
        b.setStyle(
                "-fx-background-color:#2ecc71; -fx-text-fill:white; -fx-font-size:20px; -fx-background-radius:12px;"
        );
    }

    public Button getSettingsButton() { return settingsButton; }
    public Button getTransactionButton() { return transactionButton; }
    public Button getLogoutButton() { return logoutButton; }
}




//package org.patterns.smartexpensetracker.views;
//
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//public class MainMenuView extends VBox {
//
//    private final Button userButton;
//    private final Button transactionButton;
//
//    public MainMenuView() {
//
//        this.setStyle("-fx-background-color: #e9fff4; -fx-padding: 40 0 0 0;");
//
//        Label title = new Label("Smart Expense Tracker");
//        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0b6845;");
//        title.setAlignment(Pos.CENTER);
//
//        Label welcome = new Label(
//                "Welcome to the Smart Expense Tracker.\n" +
//                        "Choose an option."
//        );
//        welcome.setStyle("-fx-font-size: 16px;");
//        welcome.setAlignment(Pos.CENTER);
//
//        userButton = new Button("User Settings");
//        transactionButton = new Button("My Expense Records");
//
//        styleButton(userButton);
//        styleButton(transactionButton);
//
//        HBox buttonRow = new HBox(40);
//        buttonRow.setAlignment(Pos.CENTER);
//        buttonRow.getChildren().addAll(userButton, transactionButton);
//
//
//
//        VBox topSection = new VBox(10, title, welcome);
//        topSection.setAlignment(Pos.TOP_CENTER);
//
//        VBox centerBox = new VBox(buttonRow);
//        centerBox.setAlignment(Pos.CENTER);
//        centerBox.setPrefHeight(400);
//
//        this.getChildren().addAll(topSection, centerBox);
//    }
//
//    private void styleButton(Button b) {
//        b.setPrefSize(250, 60);
//        b.setStyle(
//                "-fx-background-color: #2ecc71;" +
//                        "-fx-text-fill: white;" +
//                        "-fx-font-size: 20px;" +
//                        "-fx-background-radius: 12px;"
//        );
//    }
//
//    public Button getUserButton() {
//        return userButton;
//    }
//
//    public Button getTransactionButton() {
//        return transactionButton;
//    }
//}