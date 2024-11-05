package com.bank.controller;

import java.util.List;

import com.bank.model.AccountType;
import com.bank.model.ClientAccount;
import com.bank.model.OperationResult;
import com.bank.service.AdminService;
import com.bank.service.ReportService;

public class AdminController {
    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController() {
        this.adminService = new AdminService();
        this.reportService = new ReportService();
    }

    public OperationResult createClientAccount(String username, String password, String firstName, String lastName ) {
        return adminService.createClientAccount(username, password, firstName, lastName );
    }

    public void createAdminAccount(String username, String password, String firstName, String lastName ) {
        adminService.createAdminAccount(username, password, firstName, lastName);
    }

    public String generateReport(String reportType) {
        return reportService.generateReport(reportType);
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

    public boolean deleteClientAccount(String accountNumber) {
        return adminService.deleteClientAccount(accountNumber);
    }
}