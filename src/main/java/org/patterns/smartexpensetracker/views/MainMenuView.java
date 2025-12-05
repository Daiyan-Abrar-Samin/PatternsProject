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