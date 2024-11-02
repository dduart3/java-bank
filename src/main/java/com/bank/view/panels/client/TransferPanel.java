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

public class TransferPanel extends JPanel {
    private final ClientController controller;
    private final String username;
    private final JTextField recipientField;
    private final JTextField amountField;

    public TransferPanel(String username) {
        this.username = username;
        this.controller = new ClientController();
        
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Recipient field
        JLabel recipientLabel = new JLabel("Recipient Account Number:");
        recipientLabel.setForeground(Color.WHITE);
        recipientField = new JTextField(20);
        
        // Amount field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.WHITE);
        amountField = new JTextField(20);
        
        // Transfer button
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> handleTransfer());
        
        // Layout components
        gbc.gridx = 0; gbc.gridy = 0;
        add(recipientLabel, gbc);
        gbc.gridx = 1;
        add(recipientField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(amountLabel, gbc);
        gbc.gridx = 1;
        add(amountField, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(transferButton, gbc);
    }

    private void handleTransfer() {
        String recipient = recipientField.getText();
        try {
            double amount = Double.parseDouble(amountField.getText());
            // Call controller method for transfer
            // Show success/error message
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        }
    }
}