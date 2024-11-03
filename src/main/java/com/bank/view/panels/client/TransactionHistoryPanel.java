package com.bank.view.panels.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bank.controller.TransactionController;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;

public class TransactionHistoryPanel extends JPanel {
    private final TransactionController transactionController;
    private final String accountNumber;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionHistoryPanel(String accountNumber) {
        this.accountNumber = accountNumber;
        this.transactionController = new TransactionController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));
        
        createTable();
        createFilterControls();
        loadTransactions("All");
    }

    private void createTable() {
        String[] columns = {"Date", "Type", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This makes all cells non-editable
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
    }

    private void createFilterControls() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(33, 33, 33));
        
        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All", "Deposits", "Withdrawals", "Transfers"});
        typeFilter.setBackground(new Color(45, 45, 45));
        typeFilter.setForeground(Color.WHITE);
        
        typeFilter.addActionListener(e -> {
            String selectedFilter = (String) typeFilter.getSelectedItem();
            loadTransactions(selectedFilter);
        });
        
        filterPanel.add(typeFilter);
        add(filterPanel, BorderLayout.NORTH);
    }

    private void loadTransactions(String filter) {
        tableModel.setRowCount(0); // Clear existing rows
        List<Transaction> transactions = transactionController.getTransactionHistory(accountNumber);
        
        for (Transaction transaction : transactions) {
            if (shouldDisplayTransaction(transaction, filter)) {
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

    private boolean shouldDisplayTransaction(Transaction transaction, String filter) {
        if (filter.equals("All")) return true;
        
        switch (filter) {
            case "Deposits":
                return transaction.getType().equals(TransactionType.DEPOSIT);
            case "Withdrawals":
                return transaction.getType().equals(TransactionType.WITHDRAWAL);
            case "Transfers":
                return transaction.getType().equals(TransactionType.TRANSFER_RECEIVED) || transaction.getType().equals(TransactionType.TRANSFER_SENT);
            default:
                return true;
        }
    }

    public void refresh() {
        loadTransactions("All");
    }
}

