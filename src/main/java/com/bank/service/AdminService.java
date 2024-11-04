package com.bank.service;

import java.util.List;
import java.util.Random;

import com.bank.model.AccountType;
import com.bank.model.AdminAccount;
import com.bank.model.ClientAccount;
import com.bank.repository.AdminAccountRepository;
import com.bank.repository.ClientAccountRepository;

public class AdminService {

    private final AdminAccountRepository adminRepository;
    private final ClientAccountRepository clientRepository;

    public AdminService() {
        this.adminRepository = AdminAccountRepository.getInstance();
        this.clientRepository = ClientAccountRepository.getInstance();
    }

    /* CREATE DATA */
    public void createClientAccount(String username, String firstName, String lastName, String password) {
        String accountNumber = generateAccountNumber();
        ClientAccount newAccount = new ClientAccount(username, firstName, lastName, password, accountNumber);
        clientRepository.save(newAccount);
    }

    public void createAdminAccount(String username, String firstName, String lastName,
            String password) {
        AdminAccount newAccount = new AdminAccount(username, firstName, lastName, password);
        adminRepository.save(newAccount);
    }

    /* READ DATA */
    public List<AdminAccount> getAllAdminAccounts() {
        return adminRepository.findAll();
    }

    public List<ClientAccount> getAllClientAccounts() {
        return clientRepository.findAll();
    }


    /* UPDATE DATA */
    public boolean updateUsername(String accountNumber, String newUsername, AccountType accountType) {
        switch (accountType) {
            case CLIENT:
                ClientAccount clientAccount = clientRepository.findByAccountNumber(accountNumber);
                clientAccount.setUsername(newUsername);
                clientRepository.save(clientAccount);
                return true;
            case ADMIN:
                AdminAccount adminAccount = adminRepository.findById(accountNumber);
                adminAccount.setUsername(newUsername);
                adminRepository.save(adminAccount);
                return true;
            default:
                return false;
        }
    }

    public boolean updatePassword(String accountNumber, String newPassword, AccountType accountType) {
        switch (accountType) {
            case CLIENT:
                ClientAccount clientAccount = clientRepository.findByAccountNumber(accountNumber);
                clientAccount.setPassword(newPassword);
                clientRepository.save(clientAccount);
                return true;
            case ADMIN:
                AdminAccount adminAccount = adminRepository.findById(accountNumber);
                adminAccount.setPassword(newPassword);
                adminRepository.save(adminAccount);
                return true;
            default:
                return false;
        }
    }

    public boolean updateNames(String accountNumber, String newFirstName, String newLastName, AccountType accountType) {
        switch (accountType) {
            case CLIENT:
                ClientAccount clientAccount = clientRepository.findByAccountNumber(accountNumber);
                clientAccount.setFirstName(newFirstName);
                clientAccount.setLastName(newLastName);
                clientRepository.save(clientAccount);
                return true;
            case ADMIN:
                AdminAccount adminAccount = adminRepository.findById(accountNumber);
                adminAccount.setFirstName(newFirstName);
                adminAccount.setLastName(newLastName);
                adminRepository.save(adminAccount);
                return true;
            default:
                return false;
        }
    }

    /*Utility Methods*/
    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber = String.format("%04d", random.nextInt(10000));
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);

        if (account != null) {
            return generateAccountNumber(); //Recursive call to generate a new account number
        }
        return accountNumber;
    }
}
