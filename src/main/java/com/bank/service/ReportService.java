package com.bank.service;

import java.time.LocalDate;
import java.util.List;

import com.bank.model.ClientAccount;
import com.bank.model.Transaction;
import com.bank.repository.ClientAccountRepository;

public class ReportService {
    private final ClientAccountRepository clientRepository;

    public ReportService() {
        this.clientRepository = ClientAccountRepository.getInstance();
    }

    public String generateReport(String reportType) {
        switch(reportType) {
            case "ACCOUNT_SUMMARY":
                return generateAccountSummaryReport();
            case "TRANSACTION_HISTORY":
                return generateTransactionHistoryReport();
            case "DAILY_BALANCE":
                return generateDailyBalanceReport();
            default:
                return "Invalid report type";
        }
    }

    private String generateAccountSummaryReport() {
        StringBuilder report = new StringBuilder("Account Summary Report\n");
        List<ClientAccount> accounts = clientRepository.findAll();
        
        for (ClientAccount account : accounts) {
            report.append("\nAccount Number: ").append(account.getAccountNumber())
                 .append("\nOwner: ").append(account.getOwnerFullName())
                 .append("\nBalance: $").append(account.getBalance())
                 .append("\n-------------------");
        }
        return report.toString();
    }

    private String generateTransactionHistoryReport() {
        StringBuilder report = new StringBuilder("Transaction History Report\n");
        List<ClientAccount> accounts = clientRepository.findAll();
        
        for (ClientAccount account : accounts) {
            report.append("\nAccount: ").append(account.getAccountNumber());
            for (Transaction transaction : account.getTransactions()) {
                report.append("\n  Type: ").append(transaction.getType())
                      .append(", Amount: $").append(transaction.getAmount())
                      .append(", Date: ").append(transaction.getTimestamp());
            }
            report.append("\n-------------------");
        }
        return report.toString();
    }

    private String generateDailyBalanceReport() {
        StringBuilder report = new StringBuilder("Daily Balance Report\n");
        List<ClientAccount> accounts = clientRepository.findAll();
        LocalDate today = LocalDate.now();
        
        for (ClientAccount account : accounts) {
            report.append("\nAccount: ").append(account.getAccountNumber())
                 .append("\nDate: ").append(today)
                 .append("\nCurrent Balance: $").append(account.getBalance())
                 .append("\n-------------------");
        }
        return report.toString();
    }
}