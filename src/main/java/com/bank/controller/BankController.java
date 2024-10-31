package com.bank.controller;

import com.bank.service.BankService;
import com.bank.model.Account;
import com.bank.model.Transaction;
import java.util.List;
import java.time.LocalDateTime;

public class BankController {
    private BankService bankService;

    public BankController() {
        this.bankService = new BankService();
    }

    public void createClientAccount(String accountNumber, String ownerName, String password) {
        bankService.createClientAccount(accountNumber, ownerName, password);
    }

    public void createAdminAccount(String accountNumber, String ownerName, String password) {
        bankService.createAdminAccount(accountNumber, ownerName, password);
    }

    public boolean deposit(String accountNumber, double amount) {
        return bankService.deposit(accountNumber, amount);
    }

    public boolean withdraw(String accountNumber, double amount) {
        return bankService.withdraw(accountNumber, amount);
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        return bankService.transfer(fromAccountNumber, toAccountNumber, amount);
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return bankService.getTransactionHistory(accountNumber);
    }

    public double getAccountBalance(String accountNumber) {
        return bankService.getAccountBalance(accountNumber);
    }

    public String getAccountOwnerName(String accountNumber) {
        return bankService.findAccount(accountNumber)
            .map(Account::getOwnerName)
            .orElse("Unknown");
    }

    public String getTransactionHistoryString(String accountNumber) {
        List<Transaction> history = bankService.getTransactionHistory(accountNumber);
        StringBuilder sb = new StringBuilder();
        for (Transaction t : history) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public String getLastTransaction(String accountNumber) {
        return bankService.getLastTransaction(accountNumber);
    }


    public boolean toggleAccountFreeze(String accountNumber) {
        return bankService.toggleAccountFreeze(accountNumber);
    }

    public boolean setTransactionLimit(String accountNumber, double limit) {
        return bankService.setTransactionLimit(accountNumber, limit);
    }

    public Account getAccount(String accountNumber) {
        return bankService.getAccount(accountNumber);
    }

    public String getAllAccountsInfo() {
        return bankService.getAllAccountsInfo();
    }

    public String authenticateUser(String accountNumber, String password) {
        return bankService.authenticateUser(accountNumber, password);
    }

    public boolean deleteAccount(String accountNumber) {
        return bankService.deleteAccount(accountNumber);
    }

    public String generateReport(String reportType) {
        return bankService.generateReport(reportType);
    }

    public void saveData() {
        bankService.saveData();
    }

    public void loadData() {
        bankService.loadData();
    }

    public List<Transaction> getFilteredTransactionHistory(String accountNumber, String type, LocalDateTime startDate, LocalDateTime endDate) {
        return bankService.getFilteredTransactionHistory(accountNumber, type, startDate, endDate);
    }
}