package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class AdminAccount extends Account {
    private static final long serialVersionUID = 3L;
    
    private final List<AdminAction> adminActions;

    public AdminAccount(String username, String password, String firstName, String lastName ) {
        super(username,password, firstName,lastName , AccountType.ADMIN);
        this.adminActions = new ArrayList<>();
    }

    public void logAdminAction(String actionType, String details) {
        AdminAction action = new AdminAction(actionType, details);
        adminActions.add(action);
    }

    public List<AdminAction> getAdminActions() {
        return new ArrayList<>(adminActions);
    }
}
