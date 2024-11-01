package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class AdminAccount extends Account {
    private static final long serialVersionUID = 3L;
    
    private List<AdminAction> adminActions;

    public AdminAccount(String username, String firstName, String lastName, String password) {
        super(username,password, AccountType.ADMIN, firstName,lastName);
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
