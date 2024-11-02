package com.bank.model;

public enum OperationResult {
    SUCCESS("Operation completed successfully"),
    INSUFFICIENT_FUNDS("Insufficient funds in account"),
    ACCOUNT_FROZEN("Account is currently frozen"),
    INVALID_AMOUNT("Invalid transaction amount"),
    EXCEEDS_LIMIT("Transaction exceeds account limit"),
    INVALID_CREDENTIALS("Invalid username or password"),
    ACCOUNT_NOT_FOUND("Account not found"),
    INVALID_PASSWORD("Current password is incorrect"),
    USERNAME_TAKEN("Username already exists"),
    SYSTEM_ERROR("System error occurred"),
    INVALID_ACCOUNT("Invalid account data"),
    INVALID_ACCOUNT_NUMBER("Invalid account number");

    private final String message;

    OperationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}