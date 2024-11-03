package com.bank.view.panels.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.bank.controller.ClientController;

public class SettingsPanel extends JPanel {
    private final ClientController controller;
    private final String username;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public SettingsPanel(String username) {
        this.username = username;
        this.controller = new ClientController();
        
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));
        
        createPasswordChangeSection();
    }

    private void createPasswordChangeSection() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Current Password
        JLabel currentLabel = new JLabel("Current Password:");
        currentLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(currentLabel, gbc);

        currentPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(currentPasswordField, gbc);

        // New Password
        JLabel newLabel = new JLabel("New Password:");
        newLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(newLabel, gbc);

        newPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(confirmLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Change Button
        JButton changeButton = new JButton("Change Password");
        changeButton.setBackground(new Color(45, 45, 45));
        changeButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(changeButton, gbc);

        changeButton.addActionListener(e -> handlePasswordChange());
    }

    private void handlePasswordChange() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.equals(confirmPassword)) {
            if (controller.updatePassword(username, currentPassword, newPassword)) {
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update password. Please check your current password.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "New passwords don't match!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}