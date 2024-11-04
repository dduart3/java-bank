package com.bank.controller;

import java.util.List;

import com.bank.model.AccountType;
import com.bank.model.ClientAccount;
import com.bank.service.AdminService;
import com.bank.service.ReportService;

public class AdminController {
    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController() {
        this.adminService = new AdminService();
        this.reportService = new ReportService();
    }

    public void createClientAccount(String username, String firstName, String lastName, String password) {
        adminService.createClientAccount(username, firstName, lastName, password);
    }

    public void createAdminAccount(String username, String firstName, String lastName, String password) {
        adminService.createAdminAccount(username, firstName, lastName, password);
    }

    public String generateReport(String reportType) {
        return reportService.generateReport(reportType);
    }

    public boolean updateClientUsername(String accountNumber, String newUsername) {
        return adminService.updateUsername(accountNumber, newUsername, AccountType.CLIENT);
    }

    public boolean updateClientPassword(String accountNumber, String newPassword) {
        return adminService.updatePassword(accountNumber, newPassword, AccountType.CLIENT);
    }

    public boolean updateClientNames(String accountNumber, String newFirstName, String newLastName) {
        return adminService.updateNames(accountNumber, newFirstName, newLastName, AccountType.CLIENT);
    }

    public boolean toggleFreezeAccount(String accountNumber) {
        return adminService.toggleFreezeAccount(accountNumber);
    }
    
    public boolean isAccountFrozen(String accountNumber) {
        return adminService.isAccountFrozen(accountNumber);
    }

    public List<ClientAccount> getAllClientAccounts() {
        return adminService.getAllClientAccounts();
    }
}