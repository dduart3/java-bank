package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.bank.controller.AdminController;

public class SystemSettingsPanel extends JPanel {
    private final AdminController controller;

    public SystemSettingsPanel() {
        this.controller = new AdminController();
        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));

        JTabbedPane settingsTabs = new JTabbedPane();
        settingsTabs.setBackground(new Color(45, 45, 45));
        settingsTabs.setForeground(Color.WHITE);

        settingsTabs.addTab("Security Settings", createSecurityPanel());
        settingsTabs.addTab("Transaction Limits", createLimitsPanel());
        settingsTabs.addTab("System Maintenance", createMaintenancePanel());

        add(settingsTabs, BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSecurityPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        // Add security settings controls
        JCheckBox twoFactorAuth = new JCheckBox("Enable Two-Factor Authentication");
        JCheckBox autoLockout = new JCheckBox("Auto-lockout after failed attempts");
        
        panel.add(twoFactorAuth);
        panel.add(autoLockout);
        
        return panel;
    }

    private JPanel createLimitsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        // Add transaction limit controls
        JTextField dailyLimit = new JTextField("5000", 10);
        JTextField singleTransLimit = new JTextField("1000", 10);
        
        panel.add(new JLabel("Daily Limit: "));
        panel.add(dailyLimit);
        panel.add(new JLabel("Single Transaction Limit: "));
        panel.add(singleTransLimit);
        
        return panel;
    }

    private JPanel createMaintenancePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(33, 33, 33));
        
        JButton backupBtn = new JButton("Backup System");
        JButton restoreBtn = new JButton("Restore System");
        
        panel.add(backupBtn);
        panel.add(restoreBtn);
        
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(45, 45, 45));
        
        JButton saveBtn = new JButton("Save Changes");
        JButton resetBtn = new JButton("Reset to Defaults");
        
        panel.add(saveBtn);
        panel.add(resetBtn);
        
        return panel;
    }
}