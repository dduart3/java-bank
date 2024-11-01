package com.bank.controller;

import com.bank.service.AuthService;

public class AuthController {
    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public String login(String username, String password) {
        return authService.authenticate(username, password);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return authService.changePassword(username, oldPassword, newPassword);
    }

    public boolean changeUsername(String currentUsername, String newUsername, String password) {
        return authService.changeUsername(currentUsername, newUsername, password);
    }
}
