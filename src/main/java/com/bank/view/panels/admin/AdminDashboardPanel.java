package com.bank.view.panels.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.bank.controller.AdminController;
import com.bank.controller.TransactionController;
import com.bank.model.ClientAccount;

public class AdminDashboardPanel extends JPanel {

    private final AdminController adminController;
    private final TransactionController transactionController;
    private final String username;

    public AdminDashboardPanel(String username) {
        this.username = username;
        this.adminController = new AdminController();
        this.transactionController = new TransactionController();

        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        addStatisticsSection(gbc);
        addQuickActionsSection(gbc);

    }

    private void addQuickActionsSection(GridBagConstraints gbc) {
        JPanel actionsPanel = createStyledPanel("Quick Actions");
        actionsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton createAccountBtn = new JButton("Create New Account");
        createAccountBtn.addActionListener(e -> showCreateAccountDialog());

        JButton generateReportBtn = new JButton("Generate Report");
        JButton systemCheckBtn = new JButton("System Check");

        actionsPanel.add(createAccountBtn);
        actionsPanel.add(generateReportBtn);
        actionsPanel.add(systemCheckBtn);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(actionsPanel, gbc);
    }

    private void showCreateAccountDialog() {
        CreateAccountDialog dialog = new CreateAccountDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
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

    private void addStatisticsSection(GridBagConstraints gbc) {
        JPanel statsPanel = createStyledPanel("System Statistics");
        statsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        List<ClientAccount> accounts = adminController.getAllClientAccounts();
        long totalAccounts = accounts.size();
        long activeAccounts = accounts.stream().filter(acc -> !acc.isFrozen()).count();
        long frozenAccounts = accounts.stream().filter(ClientAccount::isFrozen).count();

        JLabel totalAccountsLabel = new JLabel("Total Accounts: " + totalAccounts);
        JLabel activeAccountsLabel = new JLabel("Active Accounts: " + activeAccounts);
        JLabel frozenAccountsLabel = new JLabel("Frozen Accounts: " + frozenAccounts);

        styleLabel(totalAccountsLabel);
        styleLabel(activeAccountsLabel);
        styleLabel(frozenAccountsLabel);

        statsPanel.add(totalAccountsLabel);
        statsPanel.add(activeAccountsLabel);
        statsPanel.add(frozenAccountsLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(statsPanel, gbc);
    }
}
