package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import com.bank.controller.AdminController;
public class AdminDashboardPanel extends JPanel {
    private final AdminController controller;
    private final String username;

    public AdminDashboardPanel(String username) {
        this.username = username;
        this.controller = new AdminController();
        
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        addStatisticsSection(gbc);
        addQuickActionsSection(gbc);
        addRecentActivitySection(gbc);
    }

    private void addStatisticsSection(GridBagConstraints gbc) {
        JPanel statsPanel = createStyledPanel("System Statistics");
        statsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        JLabel totalAccounts = new JLabel("Total Accounts: 156");
        JLabel activeAccounts = new JLabel("Active Accounts: 142");
        JLabel frozenAccounts = new JLabel("Frozen Accounts: 14");
        
        styleLabel(totalAccounts);
        styleLabel(activeAccounts);
        styleLabel(frozenAccounts);
        
        statsPanel.add(totalAccounts);
        statsPanel.add(activeAccounts);
        statsPanel.add(frozenAccounts);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(statsPanel, gbc);
    }

    private void addQuickActionsSection(GridBagConstraints gbc) {
        JPanel actionsPanel = createStyledPanel("Quick Actions");
        actionsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        JButton createAccountBtn = new JButton("Create New Account");
        JButton generateReportBtn = new JButton("Generate Report");
        JButton systemCheckBtn = new JButton("System Check");
        
        actionsPanel.add(createAccountBtn);
        actionsPanel.add(generateReportBtn);
        actionsPanel.add(systemCheckBtn);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(actionsPanel, gbc);
    }

    private void addRecentActivitySection(GridBagConstraints gbc) {
        JPanel activityPanel = createStyledPanel("Recent Activity");
        activityPanel.setLayout(new BorderLayout());
        
        String[] columns = {"Time", "Action", "Details"};
        Object[][] data = {
            {"10:30 AM", "Account Created", "New client account #12345"},
            {"11:15 AM", "Account Frozen", "Account #98765 frozen"},
            {"12:00 PM", "System Update", "Security patch applied"}
        };
        
        JTable activityTable = new JTable(data, columns);
        activityTable.setBackground(new Color(45, 45, 45));
        activityTable.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(activityTable);
        activityPanel.add(scrollPane);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(activityPanel, gbc);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.WHITE
        ));
        return panel;
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}