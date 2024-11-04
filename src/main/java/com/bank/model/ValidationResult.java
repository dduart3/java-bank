package com.bank.model;

public enum ValidationResult {
    SUCCESS("Success", "Operation completed successfully"),
    INVALID_USERNAME("Invalid Username", "Username can only contain letters and numbers"), 
    USERNAME_TOO_SHORT("Invalid Username", "Username must be at least 6 characters long"),
    USERNAME_TOO_LONG("Invalid Username", "Username cannot exceed 12 characters"),
    USERNAME_TAKEN("Username Error", "This username is already taken"),
    INVALID_PASSWORD("Invalid Password", "Password must be at least 6 characters long"),
    INVALID_NAME("Invalid Name", "Names must contain only letters"),
    ACCOUNT_LIMIT_REACHED("Account Limit Reached", "You have reached the maximum number of accounts");

    private final String title;
    private final String message;

    ValidationResult(String title, String message) {
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