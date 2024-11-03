package com.bank.view.panels.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bank.controller.ClientController;

public class TransactionHistoryPanel extends JPanel {
    private final ClientController clientController;
    private final String accountNumber;
    private JTable transactionTable;

    public TransactionHistoryPanel(String accountNumber) {
        this.accountNumber = accountNumber;
        this.clientController = new ClientController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));
        
        createTable();
        createFilterControls();
    }

    private void createTable() {
        String[] columns = {"Date", "Type", "Amount", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        transactionTable = new JTable(model);
        transactionTable.setBackground(new Color(45, 45, 45));
        transactionTable.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createFilterControls() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(33, 33, 33));
        
        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All", "Deposits", "Withdrawals", "Transfers"});
        filterPanel.add(typeFilter);
        
        add(filterPanel, BorderLayout.NORTH);
    }
}