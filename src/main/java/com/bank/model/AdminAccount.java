package com.bank.model;

public class AdminAccount extends Account {
    public AdminAccount(String accountNumber, String ownerName, String password) {
        super(accountNumber, ownerName, password);
    }

    // Additional admin-specific methods
}
