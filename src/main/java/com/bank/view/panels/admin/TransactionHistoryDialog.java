package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bank.controller.AdminController;
import com.bank.controller.TransactionController;
import com.bank.model.Transaction;

public class TransactionHistoryDialog extends JDialog {
    private final AdminController adminController;
    private final TransactionController transactionController;
    private final String accountNumber;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionHistoryDialog(JFrame parent, String accountNumber, String clientName) {
        super(parent, "Transaction History - " + clientName, true);
        this.accountNumber = accountNumber;
        this.adminController = new AdminController();
        this.transactionController = new TransactionController();

        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(parent);
        
        createTable();
        loadTransactions();
    }

    private void createTable() {
        String[] columns = {"Date", "Type", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionTable = new JTable(tableModel);
        transactionTable.setBackground(new Color(45, 45, 45));
        transactionTable.setForeground(Color.WHITE);
        transactionTable.getTableHeader().setBackground(new Color(40, 40, 40));
        transactionTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.getViewport().setBackground(new Color(33, 33, 33));
        add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(45, 45, 45));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(33, 33, 33));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTransactions() {
        List<Transaction> transactions;
        transactions = transactionController.getTransactionHistory(accountNumber);
        for (Transaction transaction : transactions) {
            Object[] row = {
                transaction.getTimestamp().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")),
                transaction.getType(),
                String.format("$%.2f", transaction.getAmount()),
                transaction.getDescription()
            };
            tableModel.addRow(row);
        }
    }
}
