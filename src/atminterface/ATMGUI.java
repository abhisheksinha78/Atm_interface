/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package atminterface;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATMGUI extends Application {
    private BankAccount account = new BankAccount(1000);
    private Label balanceLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM Machine");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        balanceLabel = new Label("Balance: $" + account.getBalance());
        Button checkBalanceButton = new Button("Check Balance");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");

        checkBalanceButton.setOnAction(e -> checkBalance());
        depositButton.setOnAction(e -> deposit());
        withdrawButton.setOnAction(e -> withdraw());

        vbox.getChildren().addAll(balanceLabel, checkBalanceButton, depositButton, withdrawButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkBalance() {
        balanceLabel.setText("Balance: $" + account.getBalance());
    }

    private void deposit() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter the deposit amount:");
        dialog.setContentText("Amount:");

        dialog.showAndWait().ifPresent(amount -> {
            try {
                double depositAmount = Double.parseDouble(amount);
                account.deposit(depositAmount);
                checkBalance();
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void withdraw() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Enter the withdrawal amount:");
        dialog.setContentText("Amount:");

        dialog.showAndWait().ifPresent(amount -> {
            try {
                double withdrawalAmount = Double.parseDouble(amount);
                if (account.withdraw(withdrawalAmount)) {
                    checkBalance();
                } else {
                    showAlert("Insufficient funds.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            } else {
                return false;
            }
        }
    }
}
