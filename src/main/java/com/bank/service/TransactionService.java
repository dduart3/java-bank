package com.bank.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bank.model.ClientAccount;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.repository.ClientAccountRepository;

public class TransactionService {

    private final ClientAccountRepository clientRepository;

    public TransactionService() {
        this.clientRepository = ClientAccountRepository.getInstance();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        return account.getTransactions();
    }

    public Transaction getLastTransaction(String accountNumber) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }

    public List<Transaction> getTransactionsByType(String accountNumber, TransactionType type) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        return account.getTransactions().stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Transaction> getFilteredTransactionHistory(String accountNumber, TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            return account.getTransactions().stream()
                    .filter(transaction -> type == null || transaction.getType().equals(type))
                    .filter(transaction -> startDate == null || !transaction.getTimestamp().isBefore(startDate))
                    .filter(transaction -> endDate == null || !transaction.getTimestamp().isAfter(endDate))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public boolean executeTransfer(String fromAccountNumber, String toAccountNumber, double amount) {
        ClientAccount sender = clientRepository.findByAccountNumber(fromAccountNumber);
        ClientAccount receiver = clientRepository.findByAccountNumber(toAccountNumber);

        if (sender == null || receiver == null || amount <= 0) {
            return false;
        }

        if (sender.getBalance() < amount || amount > sender.getTransactionLimit()) {
            return false;
        }

        // Execute transfer
        sender.withdraw(amount);
        receiver.deposit(amount);

        // Log transactions
        Transaction transferSentTransaction = new Transaction(TransactionType.TRANSFER_SENT, amount,
                "Transfer to account: " + toAccountNumber);
        Transaction transferReceivedTransaction = new Transaction(TransactionType.TRANSFER_RECEIVED, amount,
                "Transfer from account: " + fromAccountNumber);

        sender.addTransaction(transferSentTransaction);
        receiver.addTransaction(transferReceivedTransaction);

        return true;
    }

    public boolean executeDeposit(String accountNumber, double amount) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);

        if (account == null || amount <= 0) {
            return false;
        }

        account.deposit(amount);

        Transaction depositTransaction = new Transaction(
                TransactionType.DEPOSIT,
                amount,
                "Deposit to account"
        );

        account.addTransaction(depositTransaction);
        return true;
    }

    public boolean executeWithdraw(String accountNumber, double amount) {
        ClientAccount account = clientRepository.findByAccountNumber(accountNumber);

        if (account == null || amount <= 0 || account.isFrozen() || amount > account.getTransactionLimit()) {
            return false;
        }

        account.withdraw(amount);

        Transaction withdrawTransaction = new Transaction(
                TransactionType.WITHDRAWAL,
                amount,
                "Withdrawal from account"
        );

        account.addTransaction(withdrawTransaction);
        return true;
    }
}
