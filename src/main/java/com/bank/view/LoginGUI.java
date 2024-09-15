package com.bank.view;

import com.bank.controller.BankController;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField accountNumberField;
    private JPasswordField passwordField;
    private JButton loginButton;
    public LoginGUI(BankController controller) {
        FlatDarkLaf.setup();

        setTitle("Java Bank - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(44, 62, 80);
                Color color2 = new Color(52, 152, 219);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        setContentPane(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Logo placeholder
        JLabel logoLabel = new JLabel("JAVA BANK", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 32));
        logoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 40;
        mainPanel.add(logoLabel, gbc);

        // Reset ipady
        gbc.ipady = 0;

        // Account Number Field
        JLabel accountLabel = new JLabel("Account Number");
        accountLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(accountLabel, gbc);

        accountNumberField = new JTextField(20);
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        mainPanel.add(accountNumberField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 4;
        mainPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                String password = new String(passwordField.getPassword());
                String accountType = controller.authenticateUser(accountNumber, password);
                if (accountType != "INVALID") {
                    if (accountType.equals("ADMIN")) {
                        new AdminGUI(controller).setVisible(true);
                        dispose();
                    } else if (accountType.equals("CLIENT")) {
                        new ClientGUI(controller, accountNumber).setVisible(true);
                        dispose();
                    }
                } else {
                    System.out.println("Showing error message");
                    JOptionPane.showMessageDialog(LoginGUI.this,
                        "Invalid credentials. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });
    }
}