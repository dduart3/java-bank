package com.bank.model;

public enum OperationResult {
    INSUFFICIENT_FUNDS("Not enough", "Insufficient funds in account"),
    ACCOUNT_FROZEN("Account is frozen", "Account is currently frozen, please contact support"),
    INVALID_AMOUNT("Invalid transaction amount", "Transaction amount must be greater than 0"),
    INVALID_CREDENTIALS("Invalid username or password", "Username or password is incorrect"),
    ACCOUNT_NOT_FOUND("Account not found", "This account is not registered"),
    SYSTEM_ERROR("System error occurred", "Something wrong occurred, please try again later"),
    INVALID_ACCOUNT("Invalid account data", "Account data is invalid"),
    INVALID_ACCOUNT_NUMBER("Invalid account number", "Account number must be 10 digits"),
    SUCCESS("Success", "Operation completed successfully"),
    INVALID_USERNAME("Invalid Username", "Username can only contain letters and numbers"), 
    USERNAME_TOO_SHORT("Invalid Username", "Username must be at least 4 characters long"),
    USERNAME_TOO_LONG("Invalid Username", "Username cannot exceed 12 characters"),
    USERNAME_TAKEN("Username Error", "This username is already taken"),
    PASSWORD_TOO_SHORT("Invalid Password", "Password must be at least 6 characters long"),
    INVALID_PASSWORD("Invalid Password", "Password cannot be empty"),
    ACCOUNT_LIMIT_REACHED("Account Limit Reached", "You have reached the maximum number of accounts"),
    PASSWORD_TOO_LONG("Invalid Password", "Password cannot exceed 20 characters"),
    INVALID_NAME("Invalid Name or Last Name", "Names must contain only letters"),
    NAME_TOO_LONG("Name or Last Name too long", "Names cannot exceed 30 characters"),;

    private final String title;
    private final String message;

    OperationResult(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}