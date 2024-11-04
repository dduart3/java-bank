package com.bank.service;

import java.util.List;

import com.bank.model.AccountType;
import com.bank.model.AdminAccount;
import com.bank.model.ClientAccount;
import com.bank.model.OperationResult;
import com.bank.repository.AccountRepository;
import com.bank.repository.AdminAccountRepository;
import com.bank.repository.ClientAccountRepository;

public class AdminService {

    private final AdminAccountRepository adminRepository;
    private final ClientAccountRepository clientRepository;
    private final ValidationService validationService;

    public AdminService() {
        this.adminRepository = AdminAccountRepository.getInstance();
        this.clientRepository = ClientAccountRepository.getInstance();
        this.validationService = new ValidationService();
    }

    /* CREATE DATA */
    public OperationResult createClientAccount(String username, String password, String firstName, String lastName) {
        OperationResult inputValidationResult = validationService.validateRegistration(username, password, firstName, lastName);

        if (clientRepository.getAccountCount() >= AccountRepository.MAX_ACCOUNTS) {
            return OperationResult.ACCOUNT_LIMIT_REACHED;
        }

        if (inputValidationResult != OperationResult.SUCCESS) {
            return inputValidationResult;
        }

        if (clientRepository.findByUsername(username) != null) {
            return OperationResult.USERNAME_TAKEN;
        }
        
        String accountNumber = generateAccountNumber();

        ClientAccount newAccount = new ClientAccount(username, password, firstName, lastName, accountNumber);
        clientRepository.save(newAccount);
        return OperationResult.SUCCESS;
    }

    public void createAdminAccount(String username, String password, String firstName, String lastName) {
        AdminAccount newAccount = new AdminAccount(username, password, firstName, lastName);
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

    public boolean toggleFreezeAccount(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            account.setFrozen(!account.isFrozen());
            clientRepository.save(account);
            return true;
        }
        return false;
    }

    public boolean isAccountFrozen(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        return account != null && account.isFrozen();
    }

    /*Utility Methods*/
    private String generateAccountNumber() {
        int totalAccounts = clientRepository.getAccountCount();
        return String.format("%06d", totalAccounts); // First one is 000000
    }
}
