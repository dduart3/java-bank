package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bank.controller.AdminController;
import com.bank.controller.ReportController;

public class ReportsPanel extends JPanel {

    private final AdminController adminController;
    private final ReportController reportController;
    private JTextArea reportDisplay;

    public ReportsPanel() {
        this.adminController = new AdminController();
        this.reportController = new ReportController();

        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));

        createControls();
        createReportDisplay();
    }

    private void createControls() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(33, 33, 33));
    
        JButton accountSummaryBtn = createButton("Account Summary Report");
        JButton transactionHistoryBtn = createButton("Transaction History Report");
        JButton dailyBalanceBtn = createButton("Daily Balance Report");
    
        accountSummaryBtn.addActionListener(e -> generateReport("ACCOUNT_SUMMARY"));
        transactionHistoryBtn.addActionListener(e -> generateReport("TRANSACTION_HISTORY"));
        dailyBalanceBtn.addActionListener(e -> generateReport("DAILY_BALANCE"));
    
        controlPanel.add(accountSummaryBtn);
        controlPanel.add(transactionHistoryBtn);
        controlPanel.add(dailyBalanceBtn);
    
        add(controlPanel, BorderLayout.NORTH);
    }
    
    private void generateReport(String reportType) {
        String report = reportController.generateReport(reportType);
        reportDisplay.setText(report);
    }
    

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void createReportDisplay() {
        reportDisplay = new JTextArea();
        reportDisplay.setEditable(false);
        reportDisplay.setBackground(new Color(45, 45, 45));
        reportDisplay.setForeground(Color.WHITE);
        reportDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(reportDisplay);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generateDailyBalanceReport() {
        String report = reportController.generateReport("DAILY_BALANCE");
        reportDisplay.setText(report);
    }

    private void generateActivityReport() {
        String report = reportController.generateReport("TRANSACTION_HISTORY");
        reportDisplay.setText(report);
    }

    public void refresh() {
    }

    
}
