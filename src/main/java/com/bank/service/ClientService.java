package com.bank.service;

import java.time.format.DateTimeFormatter;

import com.bank.model.ClientAccount;
import com.bank.model.Transaction;
import com.bank.repository.ClientAccountRepository;

public class ClientService {

    private final TransactionService transactionService;
    private final ClientAccountRepository clientRepository;

    public ClientService() {
        this.transactionService = new TransactionService();
        this.clientRepository = ClientAccountRepository.getInstance();
    }

    public String getAccountNumber(String username) {
        ClientAccount account = clientRepository.findByUsername(username);
        return account.getAccountNumber();
    }

    public double getAccountBalance(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        return account.getBalance();
    }

    public String getLastTransaction(String accountNumber) {
        Transaction lastTransaction = transactionService.getLastTransaction(accountNumber);
        if (lastTransaction != null) {
            return String.format("%s: $%.2f on %s",
                    lastTransaction.getType(),
                    lastTransaction.getAmount(),
                    lastTransaction.getTimestamp().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))
            );
        }
        return "No transactions yet";
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

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        ClientAccount account = clientRepository.findByUsername(username);
        if (account.changePassword(oldPassword, newPassword)) {
            clientRepository.save(account);
            return true;
        }
        return false;
    }
}
