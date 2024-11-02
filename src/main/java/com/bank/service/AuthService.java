package com.bank.service;

import com.bank.model.AdminAccount;
import com.bank.model.ClientAccount;
import com.bank.repository.AdminAccountRepository;
import com.bank.repository.ClientAccountRepository;

public class AuthService {
    private final ClientAccountRepository clientRepository;
    private final AdminAccountRepository adminRepository;

    public AuthService() {
        this.adminRepository = AdminAccountRepository.getInstance();
        this.clientRepository = ClientAccountRepository.getInstance();
    }

    public String authenticate(String username, String password) {
        AdminAccount adminAccount = adminRepository.findByUsername(username);
        if (adminAccount != null && adminAccount.authenticate(username, password)) {
            return "ADMIN";
        }

        ClientAccount clientAccount = clientRepository.findByUsername(username);
        if (clientAccount != null && clientAccount.authenticate(username, password)) {
            return "CLIENT";
        }
        return "INVALID";
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        AdminAccount adminAccount = adminRepository.findByUsername(username);
        if (adminAccount != null) {
            boolean changed = adminAccount.changePassword(oldPassword, newPassword);
            if (changed) {
                adminRepository.save(adminAccount);
                return true;
            }
        }

        ClientAccount clientAccount = clientRepository.findByUsername(username);
        if (clientAccount != null) {
            boolean changed = clientAccount.changePassword(oldPassword, newPassword);
            if (changed) {
                clientRepository.save(clientAccount);
                return true;
            }
        }
        return false;
    }

    public boolean changeUsername(String currentUsername, String newUsername, String password) {
        AdminAccount adminAccount = adminRepository.findByUsername(currentUsername);
        if (adminAccount != null) {
            boolean changed = adminAccount.changeUsername(newUsername, password);
            if (changed) {
                adminRepository.save(adminAccount);
                return true;
            }
        }

        ClientAccount clientAccount = clientRepository.findByUsername(currentUsername);
        if (clientAccount != null) {
            boolean changed = clientAccount.changeUsername(newUsername, password);
            if (changed) {
                clientRepository.save(clientAccount);
                return true;
            }
        }
        return false;
    }

    
}