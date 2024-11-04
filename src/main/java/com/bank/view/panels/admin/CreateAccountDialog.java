package com.bank.view.panels.admin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bank.controller.AdminController;

public class CreateAccountDialog extends JDialog {
    private final AdminController adminController;
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;

    public CreateAccountDialog(JFrame parent) {
        super(parent, "Create New Account", true);
        this.adminController = new AdminController();
        
        setLayout(new GridBagLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        createForm();
    }

    private void createForm() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        addFormField("Username:", usernameField = new JTextField(20), gbc, 0);
        
        // First Name
        addFormField("First Name:", firstNameField = new JTextField(20), gbc, 1);
        
        // Last Name
        addFormField("Last Name:", lastNameField = new JTextField(20), gbc, 2);
        
        // Password
        addFormField("Password:", passwordField = new JPasswordField(20), gbc, 3);
        
        // Create Button
        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> createAccount());
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(createButton, gbc);
    }

    private void addFormField(String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        add(field, gbc);
    }

    private void createAccount() {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = new String(passwordField.getPassword());

        
        if (validateFields()) {
            adminController.createClientAccount(username, firstName, lastName, password);
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
        }
    }

    private boolean validateFields() {
        if (usernameField.getText().isEmpty() || 
            firstNameField.getText().isEmpty() || 
            lastNameField.getText().isEmpty() || 
            passwordField.getPassword().length == 0) {
            
            JOptionPane.showMessageDialog(this, 
                "All fields are required!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}