package com.bank.model;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }


        @Override
        public String toString() {
            return String.format("%s - %s: $%.2f - %s", timestamp, type, amount, description);
        }

        public String getType() {
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
}
