package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.bank.controller.AdminController;

public class ReportsPanel extends JPanel {
    private final AdminController controller;
    private JTabbedPane tabbedPane;

    public ReportsPanel() {
        this.controller = new AdminController();
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));

        createTabbedPane();
        createExportControls();
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(45, 45, 45));
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("Transaction Summary", createTransactionSummaryPanel());
        tabbedPane.addTab("Account Statistics", createAccountStatsPanel());
        tabbedPane.addTab("System Activity", createSystemActivityPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTransactionSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        // Add chart or table showing transaction data
        return panel;
    }

    private JPanel createAccountStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        // Add account statistics visualization
        return panel;
    }

    private JPanel createSystemActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        // Add system activity log
        return panel;
    }

    private void createExportControls() {
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exportPanel.setBackground(new Color(45, 45, 45));
        
        JButton exportPdfBtn = new JButton("Export as PDF");
        JButton exportCsvBtn = new JButton("Export as CSV");
        
        exportPanel.add(exportPdfBtn);
        exportPanel.add(exportCsvBtn);
        
        add(exportPanel, BorderLayout.SOUTH);
    }
}