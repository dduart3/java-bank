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
    private final String accountNumber;

    public AccountOverviewPanel(String accountNumber) {
        this.controller = new ClientController();
        this.accountNumber = accountNumber;

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

        // Last Transaction Info
        String lastTransaction = controller.getLastTransaction(accountNumber);
        JLabel lastTransLabel = new JLabel("Last Transaction");
        lastTransLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lastTransLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(lastTransLabel, gbc);

        JLabel transactionDetails = new JLabel(lastTransaction);
        transactionDetails.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionDetails.setForeground(Color.WHITE);
        gbc.gridy = 2;
        add(transactionDetails, gbc);
    }
}
