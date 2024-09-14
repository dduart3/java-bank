package com.bank.view;

import com.bank.controller.BankController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {
    private BankController controller;

    public AdminGUI(BankController controller) {
        this.controller = controller;
        setTitle("Admin Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(8, 1));

        add(createButton("Create Client Account", e -> createClientAccount()));
        add(createButton("Create Admin Account", e -> createAdminAccount()));
        add(createButton("View All Accounts", e -> viewAllAccounts()));
        add(createButton("Delete Account", e -> deleteAccount()));
        add(createButton("Generate Report", e -> generateReport()));
        add(createButton("Freeze/Unfreeze Account", e -> toggleAccountFreeze()));
        add(createButton("Set Transaction Limit", e -> setTransactionLimit()));
        add(createButton("Logout", e -> logout()));
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
    private void createClientAccount() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter new client account number:");
        String ownerName = JOptionPane.showInputDialog(this, "Enter client name:");
        String password = JOptionPane.showInputDialog(this, "Enter client password:");
        if (accountNumber != null && ownerName != null) {
            controller.createClientAccount(accountNumber, ownerName, password);
            JOptionPane.showMessageDialog(this, "Client account created successfully");
        }
    }

    private void createAdminAccount() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter new admin account number:");
        String ownerName = JOptionPane.showInputDialog(this, "Enter admin name:");
        String password = JOptionPane.showInputDialog(this, "Enter admin password:");
        if (accountNumber != null && ownerName != null) {
            controller.createAdminAccount(accountNumber, ownerName, password);
            JOptionPane.showMessageDialog(this, "Admin account created successfully");
        }
    }

    private void viewAllAccounts() {
        String accounts = controller.getAllAccountsInfo();
        JOptionPane.showMessageDialog(this, accounts, "All Accounts", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteAccount() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number to delete:");
        if (accountNumber != null) {
            boolean success = controller.deleteAccount(accountNumber);
            JOptionPane.showMessageDialog(this, success ? "Account deleted successfully" : "Failed to delete account");
        }
    }

    private void generateReport() {
        String[] reportTypes = {"Account Summary", "Transaction History", "Daily Balance"};
        String selectedReport = (String) JOptionPane.showInputDialog(
            this,
            "Select report type:",
            "Generate Report",
            JOptionPane.QUESTION_MESSAGE,
            null,
            reportTypes,
            reportTypes[0]
        );

        if (selectedReport != null) {
            String report = controller.generateReport(selectedReport);
            JTextArea reportArea = new JTextArea(report);
            reportArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(reportArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, selectedReport + " Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void toggleAccountFreeze() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber != null && !accountNumber.isEmpty()) {
            boolean success = controller.toggleAccountFreeze(accountNumber);
            JOptionPane.showMessageDialog(this, success ? "Account freeze status toggled" : "Failed to toggle account freeze status");
        }
    }

    private void setTransactionLimit() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber != null && !accountNumber.isEmpty()) {
            String limitStr = JOptionPane.showInputDialog(this, "Enter new transaction limit:");
            if (limitStr != null && !limitStr.isEmpty()) {
                try {
                    double limit = Double.parseDouble(limitStr);
                    boolean success = controller.setTransactionLimit(accountNumber, limit);
                    JOptionPane.showMessageDialog(this, success ? "Transaction limit set" : "Failed to set transaction limit");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid limit amount", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void logout() {
        this.dispose();
        new LoginGUI(controller).setVisible(true);
    }
}
