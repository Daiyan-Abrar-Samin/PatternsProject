package org.patterns.smartexpensetracker.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {

    private final Button userButton;
    private final Button transactionButton;

    public MainMenuView() {

        // mint color background
        this.setStyle("-fx-background-color: #e9fff4; -fx-padding: 40 0 0 0;");

        Label title = new Label("Smart Expense Tracker");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0b6845;");
        title.setAlignment(Pos.CENTER);

        Label welcome = new Label(
                "Welcome to the Smart Expense Tracker.\n" +
                        "Select the section you wish to access."
        );
        welcome.setStyle("-fx-font-size: 16px;");
        welcome.setAlignment(Pos.CENTER);

        // mint green color
        userButton = new Button("User Information");
        transactionButton = new Button("Expense Records");

        styleButton(userButton);
        styleButton(transactionButton);

        // Both buttons side by side
        HBox buttonRow = new HBox(40);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(userButton, transactionButton);

        // Keep title + welcome at the top
        VBox topSection = new VBox(10, title, welcome);
        topSection.setAlignment(Pos.TOP_CENTER);

        VBox centerBox = new VBox(buttonRow);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefHeight(400); // pushes buttons to true center

        this.getChildren().addAll(topSection, centerBox);
    }

    private void styleButton(Button b) {
        b.setPrefSize(250, 60);
        b.setStyle(
                "-fx-background-color: #2ecc71;" +       // mint green
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-background-radius: 12px;"
        );

        b.setOnMouseEntered(e ->
                b.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 12px;")
        );

        b.setOnMouseExited(e ->
                b.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 12px;")
        );
    }

    public Button getUserButton() {
        return userButton;
    }

    public Button getTransactionButton() {
        return transactionButton;
    }
}
