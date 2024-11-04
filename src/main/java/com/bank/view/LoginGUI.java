package com.bank.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bank.controller.AuthController;
import com.bank.model.AccountType;

public class LoginGUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AccountType accountType;
    private final MainLoginGUI mainLoginGUI;
    private final AuthController authController;

    public LoginGUI(AccountType accountType, MainLoginGUI mainLoginGUI) {
        
        this.accountType = accountType;
        this.mainLoginGUI = mainLoginGUI;
        this.authController = new AuthController();

        setTitle("Marabank - " + accountType + " Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Type indicator
        JLabel typeLabel = new JLabel(accountType + " Login");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(typeLabel, gbc);

        // Username field
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> handleBack());

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

         Color accentColor = accountType == AccountType.ADMIN ? 
            new Color(255, 69, 0) :  // Orange-red for admin
            new Color(0, 150, 136);  // Teal for client
        
        typeLabel.setForeground(accentColor);
        loginButton.setBackground(accentColor);
        
        // Add hover effects
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(accentColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(accentColor);
            }
        });

        // Style text fields
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String result = authController.login(username, password);
        
        if (result.equals(accountType.toString())) {
            // Open appropriate window based on account type
            openAppropriateWindow(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void handleBack() {
        mainLoginGUI.setVisible(true);
        this.dispose();
    }

    private void openAppropriateWindow(String username) {
        this.dispose();

        switch (accountType ) {
            case ADMIN:
                new AdminGUI(username).setVisible(true);
                break;
            case CLIENT:
                new ClientGUI(username).setVisible(true);
                break;
            default:
            JOptionPane.showMessageDialog(null, "Invalid account type");
                break;
        }
    }
}
