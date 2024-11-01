package com.bank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 4L;
    private final String id;
    
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;


    public Transaction(TransactionType type, double amount, String description) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }
    
        @Override
        public String toString() {
            return String.format("%s - %s: $%.2f - %s", timestamp, type, amount, description);
        }

        public TransactionType getType() {
            return type;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public double getAmount() {
            return amount;
        }
        
        public String getDescription() {
            return description;
        }

        public String getId() {
            return id;
        }
}
