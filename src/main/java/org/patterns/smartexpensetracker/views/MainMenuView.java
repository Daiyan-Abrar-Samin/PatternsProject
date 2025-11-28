package org.patterns.smartexpensetracker.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {
    private final Button userButton;
    private final Button transactionButton;

    public MainMenuView() {
        this.userButton = new Button("User Information");
        this.transactionButton = new Button("Expense Records");

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
//        this.setStyle("-fx-padding: 50;");

        this.getChildren().addAll(userButton, transactionButton);
    }

    public Button getUserButton() {
        return userButton;
    }

    public Button getTransactionButton() {
        return transactionButton;
    }
}