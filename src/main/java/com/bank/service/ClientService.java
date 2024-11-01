package com.bank.service;

import com.bank.model.ClientAccount;
import com.bank.repository.ClientAccountRepository;

public class ClientService {
    private final TransactionService transactionService;
    private final ClientAccountRepository clientRepository;

    public ClientService () {
        this.transactionService = new TransactionService();
        this.clientRepository = new ClientAccountRepository();
    }

     public double getAccountBalance(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        return account.getBalance();
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        return transactionService.executeTransfer(fromAccount, toAccount, amount);
    }

    public boolean deposit(String accountNumber, double amount) {
        return transactionService.executeDeposit(accountNumber, amount);
    }

    public boolean withdraw(String accountNumber, double amount) {
        return transactionService.executeWithdraw(accountNumber, amount);
    }

    public boolean updateUsername(String accountNumber, String newUsername, String password) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        if (account.authenticate(account.getUsername(), password)) {
            account.setUsername(newUsername);
            clientRepository.save(account);
            return true;
        }
        return false;
    }

    public boolean updatePassword(String accountNumber, String oldPassword, String newPassword) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        if (account.changePassword(oldPassword, newPassword)) {
            clientRepository.save(account);
            return true;
        }
        return false;
    }
}