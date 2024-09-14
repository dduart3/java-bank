package com.bank.model;

public class ClientAccount extends Account {
    private static final long serialVersionUID = 1L;

    private double transactionLimit;

    public void setTransactionLimit(double limit) {
        this.transactionLimit = limit;
    }

    public double getTransactionLimit() {
        return transactionLimit;
    }

    public ClientAccount(String accountNumber, String ownerName, String password) {
        super(accountNumber, ownerName, password);
    }

    // Additional client-specific methods
}
