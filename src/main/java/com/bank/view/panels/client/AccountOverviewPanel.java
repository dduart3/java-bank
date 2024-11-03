package com.bank.view.panels.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bank.controller.ClientController;

public class AccountOverviewPanel extends JPanel {
    private final ClientController controller;
    private final String username;
    private final String accountNumber;

    public AccountOverviewPanel(String username) {
        this.controller = new ClientController();
        this.username = username;
        this.accountNumber = controller.getAccountNumber(username);
        
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));
        
        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Balance display
        JLabel balanceLabel = new JLabel("Current Balance: $" + controller.getAccountBalance(accountNumber));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balanceLabel.setForeground(Color.WHITE);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(balanceLabel, gbc);
        
        // Recent transactions
        JLabel recentTransLabel = new JLabel("Recent Transactions");
        recentTransLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        add(recentTransLabel, gbc);
    }
}