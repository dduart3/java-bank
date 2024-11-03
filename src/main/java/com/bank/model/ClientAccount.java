package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class ClientAccount extends Account {
    private static final long serialVersionUID = 2L;

    private double transactionLimit;
    private final String accountNumber;
    private final List<Transaction> transactions;
    private boolean frozen = false;
    private double balance;

    public ClientAccount(String username, String password, String firstName, String lastName, String accountNumber) {
        super(username, password, AccountType.CLIENT, firstName, lastName);
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
