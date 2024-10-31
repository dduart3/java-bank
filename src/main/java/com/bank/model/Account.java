package com.bank.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String accountNumber;
    protected String password;
    protected double balance;
    protected String ownerName;
    protected String firstName;
    protected String lastName;
    private LocalDateTime creationDate;
    protected List<Transaction> transactions;
    private boolean frozen = false;
    
    public Account(String accountNumber, String ownerName, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.creationDate = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCreationDateFormatted () {
        return creationDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss"));  
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean isFrozen() {
        return frozen;
    }
    
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    
    public void addTransaction(String type, double amount, String description) {
        transactions.add(new Transaction(type, amount, description));
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
    
    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposit", amount, "Regular deposit");
    }
    
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction("Withdrawal", -amount, "Regular withdrawal");
            return true;
        }
        return false;
    }

}