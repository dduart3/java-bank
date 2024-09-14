package com.bank.view;

import com.bank.controller.BankController;
import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private BankController controller;
    private JTextField accountNumberField;
    private JPasswordField passwordField;

    public LoginGUI(BankController controller) {
        this.controller = controller;
        setTitle("Bank Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        add(accountNumberField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        add(loginButton);
    }

    private void login() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        
        String userType = controller.authenticateUser(accountNumber, password);
        
        if ("CLIENT".equals(userType)) {
            new ClientGUI(controller, accountNumber).setVisible(true);
            this.dispose();
        } else if ("ADMIN".equals(userType)) {
            new AdminGUI(controller).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}