package com.bank.repository;

import java.util.List;

import com.bank.model.ClientAccount;
import com.bank.model.OperationResult;

public class ClientAccountRepository extends IAccountRepository<ClientAccount> {

    private static ClientAccountRepository instance;
    private final List<ClientAccount> clientAccounts;
    private static final String FILE_PATH = "client_accounts.dat";

    private ClientAccountRepository() {
        this.clientAccounts = loadAccounts();
    }

    public static ClientAccountRepository getInstance() {
        if (instance == null) {
            instance = new ClientAccountRepository();
        }
        return instance;
    }

    public boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d+");
    }

    public OperationResult save(ClientAccount account) {
        if(!isValidAccountNumber(account.getAccountNumber())){
            return OperationResult.INVALID_ACCOUNT_NUMBER;
        }
        return save(clientAccounts, account, FILE_PATH);
    }

    public OperationResult save() {
        return save(clientAccounts, FILE_PATH);
    }

    public ClientAccount findById(String id) {
        return findById(clientAccounts, id);
    }
    
    public ClientAccount findByUsername(String username) {
        return findByUsername(clientAccounts, username);
    }

    public ClientAccount findByAccountNumber(String accountNumber) {
        return clientAccounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    public List<ClientAccount> findAll() {
        return findAll(clientAccounts);
    }

    private List<ClientAccount> loadAccounts() {
        return loadAccountsFromFile(FILE_PATH);
    }
}
