package com.bank.view.panels.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bank.controller.ClientController;
import com.bank.controller.TransactionController;

public class AccountOverviewPanel extends JPanel {

    private final ClientController clientController;
    private final TransactionController transactionController;
    private final String accountNumber;

    public AccountOverviewPanel(String accountNumber) {
        this.clientController = new ClientController();
        this.transactionController = new TransactionController();
        this.accountNumber = accountNumber;

        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));

        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // User Greeting
        String fullName = clientController.getClientFullName(accountNumber);
        JLabel greetingLabel = new JLabel("Welcome, " + fullName);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 28));
        greetingLabel.setForeground(Color.WHITE);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(greetingLabel, gbc);
        
        // Account Number
        JLabel accountLabel = new JLabel("Account number: " + accountNumber);
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        accountLabel.setForeground(Color.WHITE);
        
        gbc.gridy = 1;
        add(accountLabel, gbc);
        
        // Balance display
        JLabel balanceLabel = new JLabel("Current Balance: $" + clientController.getAccountBalance(accountNumber));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.WHITE);
        
        gbc.gridy = 2;
        add(balanceLabel, gbc);
        
        // Last Transaction Info
        String lastTransaction = transactionController.getLastTransactionFormatted(accountNumber);
        JLabel lastTransLabel = new JLabel("Last Transaction");
        lastTransLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lastTransLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        add(lastTransLabel, gbc);

        JLabel transactionDetails = new JLabel(lastTransaction);
        transactionDetails.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionDetails.setForeground(Color.WHITE);
        gbc.gridy = 4;
        add(transactionDetails, gbc);
    }
}
