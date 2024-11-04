package com.bank.service;

import com.bank.model.OperationResult;

public class ValidationService {

    public OperationResult validatePassword(String password) {
        // Password validations with regex
        if (password == null || password.isEmpty()) {
            return OperationResult.INVALID_PASSWORD;
        }
        if (!password.matches("[a-zA-Z0-9!@#$%^&*()_+-=\\[\\]{}|;:,.<>?]+")) {
            return OperationResult.INVALID_PASSWORD;
        }
        if (password.length() < 6) {
            return OperationResult.PASSWORD_TOO_SHORT;
        }
        if (password.length() > 20) {
            return OperationResult.PASSWORD_TOO_LONG;
        }

        return OperationResult.SUCCESS;
    }

    public OperationResult validateUsername(String username) {
        // Username validations
        if (username == null || username.isEmpty()) {
            return OperationResult.INVALID_USERNAME;
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            return OperationResult.INVALID_USERNAME;
        }
        if (username.length() < 6) {
            return OperationResult.USERNAME_TOO_SHORT;
        }
        if (username.length() > 12) {
            return OperationResult.USERNAME_TOO_LONG;
        }

        return OperationResult.SUCCESS;
    }

    public OperationResult validateName(String firstName, String lastName) {
        // Name validations
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            return OperationResult.INVALID_NAME;
        }

        if (firstName.length() > 30 || lastName.length() > 30) {
            return OperationResult.NAME_TOO_LONG;
        }
        return OperationResult.SUCCESS;
    }

    public OperationResult validateRegistration(String username, String password, String firstName, String lastName) {
        OperationResult usernameValidationResult = validateUsername(username);

        if (usernameValidationResult != OperationResult.SUCCESS) {
            return usernameValidationResult;
        }

        OperationResult passwordValidationResult = validatePassword(password);

        if (passwordValidationResult != OperationResult.SUCCESS) {
            return passwordValidationResult;
        }

        OperationResult namesValidationResult = validateName(firstName, lastName);

        if (namesValidationResult != OperationResult.SUCCESS) {
            return namesValidationResult;
        }

        return OperationResult.SUCCESS;
    }

    public OperationResult validateLogin(String username, String password) {
        OperationResult usernameValidationResult = validateUsername(username);

        if (usernameValidationResult != OperationResult.SUCCESS) {
            return usernameValidationResult;
        }

        OperationResult passwordValidationResult = validatePassword(password);

        if (passwordValidationResult != OperationResult.SUCCESS) {
            return passwordValidationResult;
        }
    
        return OperationResult.SUCCESS;
    }

    
}
