package com.bank.view;

import com.bank.controller.BankController;
import com.bank.model.Transaction;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ClientGUI extends JFrame {
    private BankController controller;
    private String accountNumber;
    private JPanel contentPanel;

    public ClientGUI(BankController controller, String accountNumber) {
        this.controller = controller;
        this.accountNumber = accountNumber;
        FlatDarkLaf.setup();

        setTitle("Java Bank - Client Dashboard");
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
        contentPanel.add(createDepositPanel(), "Deposit");
        contentPanel.add(createWithdrawPanel(), "Withdraw");
        contentPanel.add(createTransferPanel(), "Transfer");
        contentPanel.add(createTransactionHistoryPanel(), "Transaction History");
        contentPanel.add(createAccountInfoPanel(), "Account Info");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        String[] options = { "Dashboard", "Deposit", "Withdraw", "Transfer", "Transaction History", "Account Info",
                "Logout" };
        for (String option : options) {
            JButton button = new JButton(option);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180, 40));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(52, 152, 219));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            button.addActionListener(e -> {
                if (option.equals("Logout")) {
                    // Handle logout
                    dispose();
                    new LoginGUI(controller).setVisible(true);
                } else {
                    CardLayout cl = (CardLayout) contentPanel.getLayout();
                    cl.show(contentPanel, option);
                }

            });
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(button);
        }

        return sidebar;
    }

    private void updateDashboard() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        contentPanel.remove(contentPanel.getComponent(0)); // Remove the old dashboard
        contentPanel.add(createDashboardPanel(), "Dashboard", 0); // Add the new dashboard
        cl.show(contentPanel, "Dashboard");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome, " + controller.getAccountOwnerName(accountNumber));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, gbc);

        JLabel balanceLabel = new JLabel("Current Balance: $" + controller.getAccountBalance(accountNumber));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(balanceLabel, gbc);

        // Add last transaction information
        List<Transaction> transactions = controller.getTransactionHistory(accountNumber);
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
            String formattedTimestamp = lastTransaction.getTimestamp().format(formatter);
            JLabel lastTransactionLabel = new JLabel("Last Transaction: " +
                    lastTransaction.getType() + " - $" + String.format("%.2f", lastTransaction.getAmount()) +
                    " on " + formattedTimestamp);
            lastTransactionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            panel.add(lastTransactionLabel, gbc);
        }

        return panel;
    }

    private JPanel createDepositPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Deposit Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel amountLabel = new JLabel("Amount to Deposit:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(46, 204, 113));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(depositButton, gbc);

        depositButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                boolean success = controller.deposit(accountNumber, amount);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Deposit successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                    updateDashboard(); // Update the dashboard
                } else {
                    JOptionPane.showMessageDialog(panel, "Deposit failed. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount. Please enter a valid number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Withdraw Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel amountLabel = new JLabel("Amount to Withdraw:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(231, 76, 60));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(withdrawButton, gbc);

        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                boolean success = controller.withdraw(accountNumber, amount);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Withdrawal successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Withdrawal failed. Please check your balance and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount. Please enter a valid number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Transfer Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel recipientLabel = new JLabel("Recipient Account Number:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(recipientLabel, gbc);

        JTextField recipientField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(recipientField, gbc);

        JLabel amountLabel = new JLabel("Amount to Transfer:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(52, 152, 219));
        transferButton.setForeground(Color.WHITE);
        transferButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(transferButton, gbc);

        transferButton.addActionListener(e -> {
            String recipientAccount = recipientField.getText();
            String amountStr = amountField.getText();
            try {
                double amount = Double.parseDouble(amountStr);
                boolean success = controller.transfer(accountNumber, recipientAccount, amount);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Transfer successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    recipientField.setText("");
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Transfer failed. Please check the details and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount. Please enter a valid number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));

        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);

        List<Transaction> transactions = controller.getTransactionHistory(accountNumber);
        String[] columnNames = { "Date", "Type", "Amount" };
        Object[][] data = new Object[transactions.size()][3];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            data[i][0] = t.getTimestamp();
            data[i][1] = t.getType();
            data[i][2] = String.format("$%.2f", t.getAmount());
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAccountInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Account Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        String[] labels = { "Account Number:", "Owner Name:", "Balance:", "Account Type:", "Creation Date:" };
        String[] values = {
                accountNumber,
                controller.getAccountOwnerName(accountNumber),
                String.format("$%.2f", controller.getAccountBalance(accountNumber)),
                "Client",
                controller.getAccount(accountNumber).getCreationDateFormatted() // Assuming creation date is not
                                                                                // available in the current
                                                                                // implementation
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            panel.add(label, gbc);

            JLabel value = new JLabel(values[i]);
            value.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 1;
            panel.add(value, gbc);
        }

        return panel;
    }
}
