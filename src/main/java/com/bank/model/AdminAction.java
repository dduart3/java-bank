package com.bank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdminAction implements Serializable {
    private static final long serialVersionUID = 5L;
    
    private final String id;
    private final String actionType;
    private final String details;
    private final LocalDateTime timestamp;

    public AdminAction(String actionType, String details) {
        this.id = UUID.randomUUID().toString();
        this.actionType = actionType;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getActionType() {
        return actionType;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
