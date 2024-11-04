package com.bank.view.panels.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private final ClientManagementPanel clientManagementPanel;

    public AdminDashboardPanel(String username, ClientManagementPanel clientManagementPanel) {
        this.username = username;
        this.adminController = new AdminController();
        this.transactionController = new TransactionController();
        this.clientManagementPanel = clientManagementPanel;
        

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
        actionsPanel.setLayout(new GridBagLayout());
        
        JButton createAccountBtn = new JButton("Create New Account");
        createAccountBtn.setPreferredSize(new Dimension(200, 50));
        createAccountBtn.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountBtn.addActionListener(e -> showCreateAccountDialog());
        
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.anchor = GridBagConstraints.CENTER;
        
        actionsPanel.add(createAccountBtn, buttonGbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(actionsPanel, gbc);
    }

    private void showCreateAccountDialog() {
        JFrame adminGUI = (JFrame) SwingUtilities.getWindowAncestor(this);
        CreateAccountDialog dialog = new CreateAccountDialog(adminGUI, this, clientManagementPanel);
        dialog.setVisible(true);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100)),
                title,
                TitledBorder.CENTER,
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
        statsPanel.setLayout(new GridBagLayout());
        
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
    
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.insets = new Insets(10, 0, 10, 0);
        labelGbc.anchor = GridBagConstraints.CENTER;
        
        labelGbc.gridy = 0;
        statsPanel.add(totalAccountsLabel, labelGbc);
        
        labelGbc.gridy = 1;
        statsPanel.add(activeAccountsLabel, labelGbc);
        
        labelGbc.gridy = 2;
        statsPanel.add(frozenAccountsLabel, labelGbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(statsPanel, gbc);
    }

    public void refresh() {
        removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        addStatisticsSection(gbc);
        addQuickActionsSection(gbc);
        
        revalidate();
        repaint();
    }
}
