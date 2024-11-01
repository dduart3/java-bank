package com.bank.controller;

import com.bank.service.ClientService;

public class ClientController {
    private final ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public double getAccountBalance(String accountNumber) {
        return clientService.getAccountBalance(accountNumber);
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        return clientService.transfer(fromAccount, toAccount, amount);
    }

    public boolean deposit(String accountNumber, double amount) {
        return clientService.deposit(accountNumber, amount);
    }

    public boolean withdraw(String accountNumber, double amount) {
        return clientService.withdraw(accountNumber, amount);
    }

    public boolean updateOwnUsername(String accountNumber, String newUsername, String password) {
        return clientService.updateUsername(accountNumber, newUsername, password);
    }

    public boolean updateOwnPassword(String accountNumber, String oldPassword, String newPassword) {
        return clientService.updatePassword(accountNumber, oldPassword, newPassword);
    }
}


