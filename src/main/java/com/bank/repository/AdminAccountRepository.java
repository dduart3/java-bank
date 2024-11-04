package com.bank.repository;

import java.util.List;

import com.bank.model.AdminAccount;
import com.bank.model.OperationResult;

public class AdminAccountRepository extends AccountRepository<AdminAccount> {

    private static AdminAccountRepository instance;
    private final List<AdminAccount> accounts;
    private static final String FILE_PATH = "admin_accounts.dat";

    private AdminAccountRepository() {
        this.accounts = loadAccounts();
    }

    public static AdminAccountRepository getInstance() {
        if (instance == null) {
            instance = new AdminAccountRepository();
        }
        return instance;
    }

    public OperationResult save(AdminAccount account) {
        return save(accounts, account, FILE_PATH);
    }

    public OperationResult save() {
        return save(accounts, FILE_PATH);
    }

    public AdminAccount findById(String id) {
        return findById(accounts, id);
    }

    
    public AdminAccount findByUsername(String username) {
        return findByUsername(accounts, username);
    }

    public List<AdminAccount> findAll() {
        return findAll(accounts);
    }

    private List<AdminAccount> loadAccounts() {
        return loadAccountsFromFile(FILE_PATH);
    }
}
