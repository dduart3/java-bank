package com.bank.view.panels.admin;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
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
        
        // Statistics Section
        addStatisticsSection(gbc);
        
        // Quick Actions Section
        addQuickActionsSection(gbc);
        
        // Recent Activity Section
        addRecentActivitySection(gbc);
    }

    private void addStatisticsSection(GridBagConstraints gbc) {
        JPanel statsPanel = createStyledPanel("System Statistics");
        // Add statistics components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(statsPanel, gbc);
    }

    private void addQuickActionsSection(GridBagConstraints gbc) {
        JPanel actionsPanel = createStyledPanel("Quick Actions");
        // Add action buttons
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(actionsPanel, gbc);
    }

    private void addRecentActivitySection(GridBagConstraints gbc) {
        JPanel activityPanel = createStyledPanel("Recent Activity");
        // Add activity list
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(activityPanel, gbc);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            null,
            Color.WHITE
        ));
        return panel;
    }
}