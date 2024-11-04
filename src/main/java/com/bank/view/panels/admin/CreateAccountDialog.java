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
import com.bank.model.OperationResult;

public class CreateAccountDialog extends JDialog {
    private final AdminController adminController;
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private final AdminDashboardPanel dashboardPanel;
    private final ClientManagementPanel clientPanel;

    public CreateAccountDialog(JFrame owner, AdminDashboardPanel dashboardPanel, ClientManagementPanel clientPanel) {
        super(owner, "Create New Account", true);
        this.adminController = new AdminController();
        this.dashboardPanel = dashboardPanel;
        this.clientPanel = clientPanel;
        
        setLayout(new GridBagLayout());
        setSize(400, 300);
        setLocationRelativeTo(owner);
        
        createForm();
    }

    private void createForm() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        addFormField("Username:", usernameField = new JTextField(20), gbc, 0);

        // Password
        addFormField("Password:", passwordField = new JPasswordField(20), gbc, 3);
        
        // First Name
        addFormField("First Name:", firstNameField = new JTextField(20), gbc, 1);
        
        // Last Name
        addFormField("Last Name:", lastNameField = new JTextField(20), gbc, 2);
        
        
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
        String password = new String(passwordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
       

        OperationResult result = adminController.createClientAccount(username, password , firstName, lastName);

        if(result == OperationResult.SUCCESS) {
            dashboardPanel.refresh();
            clientPanel.refresh();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account. " + result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}