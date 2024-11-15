package com.bank.view.panels.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.bank.controller.AdminController;
import com.bank.model.ClientAccount;

public class ClientManagementPanel extends JPanel {

    private final AdminController adminController;
    private JTable clientTable;
    private DefaultTableModel tableModel;

    public ClientManagementPanel() {
        this.adminController = new AdminController();

        setLayout(new BorderLayout());
        setBackground(new Color(33, 33, 33));

        createTable();
        createControlPanel();
        loadClients();
    }

    private void createTable() {
        String[] columns = {"Account Number", "Username", "First Name", "Last Name", "Balance", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        clientTable = new JTable(tableModel);
        clientTable.setBackground(new Color(45, 45, 45));
        clientTable.setForeground(Color.WHITE);
        clientTable.getTableHeader().setBackground(new Color(40, 40, 40));
        clientTable.getTableHeader().setForeground(Color.WHITE);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.getViewport().setBackground(new Color(33, 33, 33));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(33, 33, 33));

        JButton updateNamesBtn = createButton("Update Names");
        JButton updatePasswordBtn = createButton("Update Password");
        JButton freezeAccountBtn = createButton("Toggle Freeze Account");
        JButton viewTransactionsBtn = createButton("View Transactions");
        JButton deleteAccountBtn = createButton("Delete Account");

        updateNamesBtn.addActionListener(e -> handleUpdateNames());
        updatePasswordBtn.addActionListener(e -> handleUpdatePassword());
        //freezeAccountBtn.addActionListener(e -> handleFreezeAccount());
        viewTransactionsBtn.addActionListener(e -> handleViewTransactions());
        deleteAccountBtn.addActionListener(e -> handleDeleteAccount());

        controlPanel.add(updateNamesBtn);
        controlPanel.add(updatePasswordBtn);
        //controlPanel.add(freezeAccountBtn);
        controlPanel.add(viewTransactionsBtn);
        controlPanel.add(deleteAccountBtn);

        add(controlPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void handleUpdateNames() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String currentFirstName = (String) tableModel.getValueAt(selectedRow, 2);
            String currentLastName = (String) tableModel.getValueAt(selectedRow, 3);

            String newFirstName = JOptionPane.showInputDialog(this, "Enter new first name:", currentFirstName);
            String newLastName = JOptionPane.showInputDialog(this, "Enter new last name:", currentLastName);

            if (newFirstName != null && newLastName != null) {
                if (adminController.updateClientNames(accountNumber, newFirstName, newLastName)) {
                    JOptionPane.showMessageDialog(this, "Names updated successfully!");
                    loadClients(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update names!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdatePassword() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");

            if (newPassword != null) {
                if (adminController.updateClientPassword(accountNumber, newPassword)) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFreezeAccount() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            boolean currentStatus = adminController.isAccountFrozen(accountNumber);

            if (adminController.toggleFreezeAccount(accountNumber)) {
                String status = currentStatus ? "unfrozen" : "frozen";
                JOptionPane.showMessageDialog(this, "Account successfully " + status + "!");
                refresh(); // Reload the table data
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update account status!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleViewTransactions() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String firstName = (String) tableModel.getValueAt(selectedRow, 2);
            String lastName = (String) tableModel.getValueAt(selectedRow, 3);

            TransactionHistoryDialog dialog = new TransactionHistoryDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    accountNumber,
                    firstName + " " + lastName
            );
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteAccount() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String clientName = tableModel.getValueAt(selectedRow, 2) + " " + tableModel.getValueAt(selectedRow, 3);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the account for " + clientName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (adminController.deleteClientAccount(accountNumber)) {
                    JOptionPane.showMessageDialog(this, "Account successfully deleted!");
                    refresh();  // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to delete account!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a client first!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadClients() {
        tableModel.setRowCount(0);
        List<ClientAccount> clients = adminController.getAllClientAccounts();

        for (ClientAccount client : clients) {
            Object[] row = {
                client.getAccountNumber(),
                client.getUsername(),
                client.getFirstName(),
                client.getLastName(),
                String.format("$%.2f", client.getBalance()),
                client.isFrozen() ? "Frozen" : "Active"
            };
            tableModel.addRow(row);
        }
    }

    public void refresh() {
        loadClients();
    }
}
