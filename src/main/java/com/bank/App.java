package com.bank;

import javax.swing.SwingUtilities;

import com.bank.model.AdminAccount;
import com.bank.model.ClientAccount;
import com.bank.repository.AdminAccountRepository;
import com.bank.repository.ClientAccountRepository;
import com.bank.view.MainLoginGUI;
import com.formdev.flatlaf.FlatDarkLaf;

public final class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminAccountRepository adminRepository = AdminAccountRepository.getInstance();
            ClientAccountRepository clientRepository = ClientAccountRepository.getInstance();

            // Create some sample accounts for testing
            clientRepository.save(new ClientAccount("cliente", "1234", "David", "Duarte", "1234"));
            adminRepository.save(new AdminAccount("admin", "1234", "David", "Duarte"));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Save data when the application closes
                clientRepository.save();
                adminRepository.save();
            }));

            FlatDarkLaf.setup();
            MainLoginGUI loginGUI = new MainLoginGUI();
            loginGUI.setVisible(true);
            
        });
    }
}
