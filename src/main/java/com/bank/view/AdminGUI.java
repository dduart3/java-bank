
package com.bank.view;

import com.bank.controller.BankController;
import com.bank.model.Account;
import com.bank.model.AdminAccount;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {
    private BankController controller;
    private JPanel contentPanel;

    public AdminGUI(BankController controller) {
        this.controller = controller;
        FlatDarkLaf.setup();

        setTitle("Java Bank - Admin Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Sidebar
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add different content panels
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createAccountManagementPanel(), "Account Management");

    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        String[] options = { "Dashboard", "Account Management", "Logout" };
        for (String option : options) {
            JButton button = new JButton(option);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180, 40));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(52, 152, 219));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (option.equals("Logout")) {
                        dispose();
                        new LoginGUI(controller).setVisible(true);
                    } else {
                        CardLayout cl = (CardLayout) contentPanel.getLayout();
                        cl.show(contentPanel, option);
                    }
                }
            });
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(button);
        }

        return sidebar;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, gbc);

        JLabel totalAccountsLabel = new JLabel("Total Accounts: " + 2);
        totalAccountsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(totalAccountsLabel, gbc);

        // Add more admin-specific summary information here

        return panel;
    }

    private JPanel createAccountManagementPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Account Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);


        JButton searchButton = new JButton("Search Account");
        JButton createAccountButton = new JButton("Create New Account");
        JButton toggleFreezeButton = new JButton("Toggle Account Freeze");
        JButton setLimitButton = new JButton("Set Transaction Limit");
        JButton deleteAccountButton = new JButton("Delete Account");

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(searchButton, gbc);

        gbc.gridy++;
        panel.add(createAccountButton, gbc);

        gbc.gridy++;
        panel.add(toggleFreezeButton, gbc);

        gbc.gridy++;
        panel.add(setLimitButton, gbc);

        gbc.gridy++;
        panel.add(deleteAccountButton, gbc);

        searchButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(panel, "Enter account number:", "Search Account", JOptionPane.QUESTION_MESSAGE);
            if (accountNumber != null && !accountNumber.isEmpty()) {
                Account account = controller.getAccount(accountNumber);
                if (account != null) {
                    String accountInfo = "Account Number: " + account.getAccountNumber() + "\n" +
                                         "Owner Name: " + account.getOwnerName() + "\n" +
                                         "Balance: $" + account.getBalance() + "\n" +
                                         "Account Type: " + (account instanceof AdminAccount ? "Admin" : "Client");
                    JOptionPane.showMessageDialog(panel, accountInfo, "Account Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createAccountButton.addActionListener(e -> {
            JTextField newAccountNumberField = new JTextField(10);
            JTextField ownerNameField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            JComboBox<String> accountTypeCombo = new JComboBox<>(new String[]{"Client", "Admin"});
        
            JPanel inputPanel = new JPanel(new GridLayout(0, 2));
            inputPanel.add(new JLabel("Account Number:"));
            inputPanel.add(newAccountNumberField);
            inputPanel.add(new JLabel("Owner Name:"));
            inputPanel.add(ownerNameField);
            inputPanel.add(new JLabel("Password:"));
            inputPanel.add(passwordField);
            inputPanel.add(new JLabel("Account Type:"));
            inputPanel.add(accountTypeCombo);
        
            int result = JOptionPane.showConfirmDialog(panel, inputPanel, "Create New Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String accountNumber = newAccountNumberField.getText();
                String ownerName = ownerNameField.getText();
                String password = new String(passwordField.getPassword());
                String accountType = (String) accountTypeCombo.getSelectedItem();
        
                if (accountType.equals("Admin")) {
                    controller.createAdminAccount(accountNumber, ownerName, password);
                } else {
                    controller.createClientAccount(accountNumber, ownerName, password);
                }
                JOptionPane.showMessageDialog(panel, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
         

        toggleFreezeButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(panel, "Enter account number:", "Toggle Account Freeze", JOptionPane.QUESTION_MESSAGE);
            if (accountNumber != null && !accountNumber.isEmpty()) {
                Account account = controller.getAccount(accountNumber);
                if (account != null) {
                    boolean success = controller.toggleAccountFreeze(accountNumber);
                    JOptionPane.showMessageDialog(panel, success ? "Account freeze status toggled" : "Failed to toggle account freeze status");
                } else {
                    JOptionPane.showMessageDialog(panel, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        setLimitButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(panel, "Enter account number:", "Set Transaction Limit", JOptionPane.QUESTION_MESSAGE);
            if (accountNumber != null && !accountNumber.isEmpty()) {
                Account account = controller.getAccount(accountNumber);
                if (account != null) {
                    String limitStr = JOptionPane.showInputDialog(panel, "Enter new transaction limit:", "Set Transaction Limit", JOptionPane.QUESTION_MESSAGE);
                    if (limitStr != null && !limitStr.isEmpty()) {
                        try {
                            double limit = Double.parseDouble(limitStr);
                            boolean success = controller.setTransactionLimit(accountNumber, limit);
                            JOptionPane.showMessageDialog(panel, success ? "Transaction limit set" : "Failed to set transaction limit");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(panel, "Invalid limit amount", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteAccountButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(panel, "Enter account number to delete:", "Delete Account", JOptionPane.QUESTION_MESSAGE);
            if (accountNumber != null && !accountNumber.isEmpty()) {
                Account account = controller.getAccount(accountNumber);
                if (account != null) {
                    int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = controller.deleteAccount(accountNumber);
                        JOptionPane.showMessageDialog(panel, success ? "Account deleted successfully" : "Failed to delete account");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }

 
}
