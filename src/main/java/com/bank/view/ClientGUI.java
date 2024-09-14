package com.bank.view;

import com.bank.controller.BankController;
import com.bank.model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientGUI extends JFrame {
    private BankController controller;
    private String accountNumber;
    private JLabel balanceLabel;

    public ClientGUI(BankController controller, String accountNumber) {
        this.controller = controller;
        this.accountNumber = accountNumber;
        setTitle("Client Account: " + accountNumber);
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        updateBalance();
    }

    private void initComponents() {
        // Keep the existing layout
        setLayout(new GridLayout(7, 1));
    
        // Add the dashboard panel at the top
        add(createDashboardPanel());
    
        // Add existing components
        balanceLabel = new JLabel("Balance: $0.00");
        add(balanceLabel);
    
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> deposit());
        add(depositButton);
    
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdraw());
        add(withdrawButton);
    
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> transfer());
        add(transferButton);
    
        JButton historyButton = new JButton("Transaction History");
        historyButton.addActionListener(e -> showTransactionHistory());
        add(historyButton);
    
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        add(logoutButton);
    }
    
    private void updateBalance() {
        double balance = controller.getBalance(accountNumber);
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    private void deposit() {
        String amount = JOptionPane.showInputDialog(this, "Enter deposit amount:");
        if (amount != null) {
            boolean success = controller.deposit(accountNumber, Double.parseDouble(amount));
            JOptionPane.showMessageDialog(this, success ? "Deposit successful" : "Deposit failed");
            updateBalance();
        }
    }

    private void withdraw() {
        String amount = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        if (amount != null) {
            boolean success = controller.withdraw(accountNumber, Double.parseDouble(amount));
            JOptionPane.showMessageDialog(this, success ? "Withdrawal successful" : "Withdrawal failed");
            updateBalance();
        }
    }

    private void transfer() {
        String toAccount = JOptionPane.showInputDialog(this, "Enter recipient's account number:");
        String amount = JOptionPane.showInputDialog(this, "Enter transfer amount:");
        if (toAccount != null && amount != null) {
            boolean success = controller.transfer(accountNumber, toAccount, Double.parseDouble(amount));
            JOptionPane.showMessageDialog(this, success ? "Transfer successful" : "Transfer failed");
            updateBalance();
        }
    }

    private void showTransactionHistory() {
        List<Transaction> transactions = controller.getTransactionHistory(accountNumber);
        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found.");
        } else {
            JPanel panel = new JPanel(new BorderLayout());
            String[] columnNames = {"Date", "Type", "Amount"};
            Object[][] data = new Object[transactions.size()][3];
            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                data[i][0] = t.getTimestamp();
                data[i][1] = t.getType();
                data[i][2] = String.format("$%.2f", t.getAmount());
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
            JOptionPane.showMessageDialog(this, panel, "Transaction History", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Account Dashboard"));
    
        panel.add(new JLabel("Account Number:"));
        panel.add(new JLabel(accountNumber));
    
        panel.add(new JLabel("Balance:"));
        JLabel balanceLabel = new JLabel(String.format("$%.2f", controller.getBalance(accountNumber)));
        panel.add(balanceLabel);
    
        panel.add(new JLabel("Last Transaction:"));
        String lastTransaction = controller.getLastTransaction(accountNumber);
        panel.add(new JLabel(lastTransaction != null ? lastTransaction : "No transactions"));
    
        return panel;
    }

    private void logout() {
        this.dispose();
        new LoginGUI(controller).setVisible(true);
    }
}
