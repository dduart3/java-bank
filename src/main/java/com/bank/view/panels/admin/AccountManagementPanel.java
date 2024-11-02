package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bank.controller.AdminController;

public class AccountManagementPanel extends JPanel {
    private final AdminController controller;
    private JTable accountsTable;
    private DefaultTableModel tableModel;

    public AccountManagementPanel() {
        this.controller = new AdminController();
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));

        createControlPanel();
        createAccountsTable();
        createActionButtons();
    }

    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(45, 45, 45));
        
        JButton createAccountBtn = new JButton("Create Account");
        JButton refreshBtn = new JButton("Refresh");
        JTextField searchField = new JTextField(20);
        
        controlPanel.add(createAccountBtn);
        controlPanel.add(refreshBtn);
        controlPanel.add(new JLabel("Search: "));
        controlPanel.add(searchField);
        
        add(controlPanel, BorderLayout.NORTH);
    }

    private void createAccountsTable() {
        String[] columns = {"Account ID", "Username", "Account Type", "Balance", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        accountsTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createActionButtons() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(45, 45, 45));
        
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton freezeBtn = new JButton("Freeze/Unfreeze");
        
        actionPanel.add(editBtn);
        actionPanel.add(deleteBtn);
        actionPanel.add(freezeBtn);
        
        add(actionPanel, BorderLayout.SOUTH);
    }
}