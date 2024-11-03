package com.bank.view.panels.client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bank.controller.ClientController;
import com.bank.view.ClientGUI;

public class DepositPanel extends JPanel {

    private final ClientController clientController;
    private final ClientGUI clientGUI;
    private final String accountNumber;
    private final JTextField amountField;

    public DepositPanel(ClientGUI clientGUI, String accountNumber) {
        this.clientGUI = clientGUI;
        this.accountNumber = accountNumber;
        this.clientController = new ClientController();
        this.amountField = new JTextField(20);

        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));

        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount to Deposit:");
        amountLabel.setForeground(Color.WHITE);

        JButton depositButton = new JButton("Make Deposit");
        depositButton.addActionListener(e -> handleDeposit());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(amountLabel, gbc);
        gbc.gridx = 1;
        add(amountField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(depositButton, gbc);
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            boolean success = clientController.deposit(accountNumber, amount);
            showResult(success);
            clientGUI.refreshAccountOverview();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        }
    }

    private void showResult(boolean success) {
        String message = success ? 
            "Deposit successful!" : 
            "Deposit failed. Please try again.";
        JOptionPane.showMessageDialog(this, message);
        if (success) {
            amountField.setText("");
        }
    }
}