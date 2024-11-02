package com.bank.view.panels.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.bank.controller.ClientController;

public class SettingsPanel extends JPanel {
    private final ClientController controller;
    private final String username;
    private final JPasswordField currentPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;

    public SettingsPanel(String username) {
        this.username = username;
        this.controller = new ClientController();
        
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 33, 33));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Password change section
        JLabel passwordLabel = new JLabel("Change Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        
        JButton changePasswordButton = new JButton("Update Password");
        changePasswordButton.addActionListener(e -> handlePasswordChange());
        
        // Layout components
        addComponents(gbc, passwordLabel, currentPasswordField, 
                     newPasswordField, confirmPasswordField, changePasswordButton);
    }

    private void addComponents(GridBagConstraints gbc, JComponent... components) {
        int gridy = 0;
        for (JComponent component : components) {
            gbc.gridy = gridy++;
            add(component, gbc);
        }
    }

    private void handlePasswordChange() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New passwords don't match");
            return;
        }
        
        // Call controller method to change password
    }
}