package com.bank.service;

import com.bank.model.Account;
import com.bank.model.ClientAccount;
import com.bank.model.Transaction;
import com.bank.model.AdminAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;


public class BankService {
    private List<Account> accounts;
    private static final String DATA_FILE = "bank_data.ser";

    public BankService() {
        this.accounts = new ArrayList<>();
    }

    public void createClientAccount(String accountNumber, String ownerName, String password) {
        accounts.add(new ClientAccount(accountNumber, ownerName, password));
    }

    public void createAdminAccount(String accountNumber, String ownerName, String password) {
        accounts.add(new AdminAccount(accountNumber, ownerName, password));
    }

    public double getAccountBalance(String accountNumber) {
        Optional<Account> account = findAccount(accountNumber);
        return account.map(Account::getBalance).orElse(0.0);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public String getAllAccountsInfo() {
        StringBuilder sb = new StringBuilder();
        for (Account account : accounts) {
            sb.append(account.toString()).append("\n");
        }
        return sb.toString();
    }

    public boolean deposit(String accountNumber, double amount) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent()) {
            account.get().deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent()) {
            return account.get().withdraw(amount);
        }
        return false;
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Optional<Account> fromAccount = findAccount(fromAccountNumber);
        Optional<Account> toAccount = findAccount(toAccountNumber);
        
        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (fromAccount.get().withdraw(amount)) {
                toAccount.get().deposit(amount);
                return true;
            }
        }
        return false;
    }


    public List<Transaction> getTransactionHistory(String accountNumber) {
        Optional<Account> account = findAccount(accountNumber);
        return account.map(Account::getTransactions).orElse(new ArrayList<>());
    }

    public List<Transaction> getFilteredTransactionHistory(String accountNumber, String type, LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent()) {
            return account.get().getTransactions().stream()
                .filter(transaction -> type == null || transaction.getType().equals(type))
                .filter(transaction -> startDate == null || !transaction.getTimestamp().isBefore(startDate))
                .filter(transaction -> endDate == null || !transaction.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public Optional<Account> findAccount(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public String authenticateUser(String accountNumber, String password) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent() && account.get().authenticate(password)) {
            if (account.get() instanceof AdminAccount) {
                return "ADMIN";
            } else if (account.get() instanceof ClientAccount) {
                return "CLIENT";
            }
        }
        return "INVALID";
    }

        public boolean deleteAccount(String accountNumber) {
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public boolean toggleAccountFreeze(String accountNumber) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent()) {
            account.get().setFrozen(!account.get().isFrozen());
            return true;
        }
        return false;
    }

    public boolean setTransactionLimit(String accountNumber, double limit) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent() && account.get() instanceof ClientAccount) {
            ((ClientAccount) account.get()).setTransactionLimit(limit);
            return true;
        }
        return false;
    }

    

    public String getLastTransaction(String accountNumber) {
        Optional<Account> account = findAccount(accountNumber);
        if (account.isPresent()) {
            List<Transaction> transactions = account.get().getTransactions();
            if (!transactions.isEmpty()) {
                return transactions.get(transactions.size() - 1).toString();
            }
        }
        return null;
    }

    public String generateReport(String reportType) {
        switch (reportType) {
            case "Account Summary":
                return generateAccountSummaryReport();
            case "Transaction History":
                return generateTransactionHistoryReport();
            case "Daily Balance":
                return generateDailyBalanceReport();
            default:
                return "Invalid report type";
        }
    }

    private String generateAccountSummaryReport() {
        StringBuilder report = new StringBuilder("Account Summary Report\n\n");
        for (Account account : accounts) {
            report.append(String.format("Account Number: %s\n", account.getAccountNumber()));
            report.append(String.format("Owner: %s\n", account.getOwnerName()));
            report.append(String.format("Balance: $%.2f\n", account.getBalance()));
            report.append(String.format("Account Type: %s\n", account instanceof ClientAccount ? "Client" : "Admin"));
            report.append(String.format("Frozen: %s\n\n", account.isFrozen() ? "Yes" : "No"));
        }
        return report.toString();
    }

    private String generateTransactionHistoryReport() {
        StringBuilder report = new StringBuilder("Transaction History Report\n\n");
        for (Account account : accounts) {
            report.append(String.format("Account Number: %s\n", account.getAccountNumber()));
            for (Transaction transaction : account.getTransactions()) {
                report.append(transaction.toString()).append("\n");
            }
            report.append("\n");
        }
        return report.toString();
    }
    

    private String generateDailyBalanceReport() {
        StringBuilder report = new StringBuilder("Daily Balance Report\n\n");
        Map<String, Double> dailyBalances = new HashMap<>();
        
        for (Account account : accounts) {
            for (Transaction transaction : account.getTransactions()) {
                String date = transaction.getTimestamp().toLocalDate().toString();
                dailyBalances.merge(date, transaction.getAmount(), Double::sum);
            }
        }

        for (Map.Entry<String, Double> entry : dailyBalances.entrySet()) {
            report.append(String.format("%s: $%.2f\n", entry.getKey(), entry.getValue()));
        }

        return report.toString();
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (List<Account>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    
}