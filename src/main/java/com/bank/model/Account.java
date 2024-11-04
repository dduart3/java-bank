package com.bank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final LocalDateTime creationDate;
   
    private String username;  
    private String password;
    private String firstName;
    private String lastName;
    private final AccountType accountType;
    
    public Account(String username, String password, AccountType accountType, String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.username = username;
        this.accountType = accountType;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOwnerFullName() {
        return firstName + " " + lastName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCreationDateFormatted () {
        return creationDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss"));  
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean changeUsername(String newUsername, String password) {
        if (this.password.equals(password)) {
            this.username = newUsername;
            return true;
        }
        return false;
    }

    public boolean changeFirstName(String newFirstName, String password) {
        if (this.password.equals(password)) {
            this.firstName = newFirstName;
            return true;
        }
        return false;
    }

    public boolean changeLastName(String newLastName, String password) {
        if (this.password.equals(password)) {
            this.lastName = newLastName;
            return true;
        }
        return false;
    }
}