package com.bank.view;

import com.bank.controller.BankController;
import com.bank.model.Transaction;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BankGUI extends JFrame {
    private BankController controller;
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField toAccountField;
    private JTextArea outputArea;

    public BankGUI(BankController controller) {
        this.controller = controller;
        setTitle("Bank Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);
        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("To Account (for transfer):"));
        toAccountField = new JTextField();
        inputPanel.add(toAccountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> deposit());
        inputPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdraw());
        inputPanel.add(withdrawButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> transfer());
        buttonPanel.add(transferButton);

        JButton historyButton = new JButton("Transaction History");
        historyButton.addActionListener(e -> showTransactionHistory());
        buttonPanel.add(historyButton);

        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }

    private void deposit() {
        String accountNumber = accountNumberField.getText();
        double amount = Double.parseDouble(amountField.getText());
        boolean success = controller.deposit(accountNumber, amount);
        outputArea.append(success ? "Deposit successful\n" : "Deposit failed\n");
    }

    private void withdraw() {
        String accountNumber = accountNumberField.getText();
        double amount = Double.parseDouble(amountField.getText());
        boolean success = controller.withdraw(accountNumber, amount);
        outputArea.append(success ? "Withdrawal successful\n" : "Withdrawal failed\n");
    }

    private void transfer() {
        String fromAccount = accountNumberField.getText();
        String toAccount = toAccountField.getText();
        double amount = Double.parseDouble(amountField.getText());
        boolean success = controller.transfer(fromAccount, toAccount, amount);
        outputArea.append(success ? "Transfer successful\n" : "Transfer failed\n");
    }

    private void showTransactionHistory() {
        String accountNumber = accountNumberField.getText();
        List<Transaction> history = controller.getTransactionHistory(accountNumber);
        outputArea.setText("Transaction History for " + accountNumber + ":\n");
        for (Transaction t : history) {
            outputArea.append(t.toString() + "\n");
        }
    }
}
