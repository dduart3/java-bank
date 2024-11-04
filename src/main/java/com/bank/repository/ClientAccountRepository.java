package com.bank.repository;

import java.util.List;

import com.bank.model.ClientAccount;
import com.bank.model.OperationResult;

public class ClientAccountRepository extends AccountRepository<ClientAccount> {

    private static ClientAccountRepository instance;
    private final List<ClientAccount> accounts;
    private static final String FILE_PATH = "client_accounts.dat";

    private ClientAccountRepository() {
        this.accounts = loadAccounts();
    }

    public static ClientAccountRepository getInstance() {
        if (instance == null) {
            instance = new ClientAccountRepository();
        }
        return instance;
    }

    public OperationResult save(ClientAccount account) {
        return save(accounts, account, FILE_PATH);
    }

    public OperationResult save() {
        return save(accounts, FILE_PATH);
    }

    public ClientAccount findById(String id) {
        return findById(accounts, id);
    }

    public ClientAccount findByUsername(String username) {
        return findByUsername(accounts, username);
    }

    public ClientAccount findByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    public List<ClientAccount> findAll() {
        return findAll(accounts);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    private List<ClientAccount> loadAccounts() {
        return loadAccountsFromFile(FILE_PATH);
    }
}
