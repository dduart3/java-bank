package com.bank.repository;

import java.util.List;

import com.bank.model.AdminAccount;
import com.bank.model.OperationResult;

public class AdminAccountRepository extends IAccountRepository<AdminAccount> {

    private static AdminAccountRepository instance;
    private final List<AdminAccount> adminAccounts;
    private static final String FILE_PATH = "admin_accounts.dat";

    private AdminAccountRepository() {
        this.adminAccounts = loadAccounts();
    }

    public static AdminAccountRepository getInstance() {
        if (instance == null) {
            instance = new AdminAccountRepository();
        }
        return instance;
    }

    public OperationResult save(AdminAccount account) {
        return save(adminAccounts, account, FILE_PATH);
    }

    public OperationResult save() {
        return save(adminAccounts, FILE_PATH);
    }

    public AdminAccount findById(String id) {
        return findById(adminAccounts, id);
    }

    
    public AdminAccount findByUsername(String username) {
        return findByUsername(adminAccounts, username);
    }

    public List<AdminAccount> findAll() {
        return findAll(adminAccounts);
    }

    private List<AdminAccount> loadAccounts() {
        return loadAccountsFromFile(FILE_PATH);
    }
}
