package com.bank.controller;

import java.util.List;

import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.service.TransactionService;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController() {
        this.transactionService = new TransactionService();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionService.getTransactionHistory(accountNumber);
    }

    public Transaction getLastTransaction(String accountNumber) {
        return transactionService.getLastTransaction(accountNumber);
    }

    public List<Transaction> getTransactionsByType(String accountNumber, TransactionType type) {
        return transactionService.getTransactionsByType(accountNumber, type);
    }
}
