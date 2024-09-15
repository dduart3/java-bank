package com.bank;

import com.bank.controller.BankController;
import com.bank.view.LoginGUI;
import javax.swing.SwingUtilities;

public final class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankController controller = new BankController();
            controller.loadData(); // Load existing data
            
            // Create some sample accounts for testing
            controller.createClientAccount("C001", "John Doe", "password1");
            controller.createAdminAccount("A001", "Admin User", "adminpass");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                controller.saveData(); // Save data when the application closes
            }));
            
            LoginGUI loginGUI = new LoginGUI(controller);
            loginGUI.setVisible(true);
        });
    }
}