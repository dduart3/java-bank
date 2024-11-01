package com.bank.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.ClientAccount;

public class ClientAccountRepository {
    private static ClientAccountRepository instance;
    private final List<ClientAccount> clientAccounts;
    private static final String FILE_PATH = "client_accounts.dat";

    public ClientAccountRepository() {
        this.clientAccounts = loadAccountsFromFile();
    }

    public static ClientAccountRepository getInstance() {
        if (instance == null) {
            instance = new ClientAccountRepository();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private List<ClientAccount> loadAccountsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<ClientAccount>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void save(ClientAccount account) {
        clientAccounts.add(account);
        saveAccountsToFile();
    }

    private void saveAccountsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(clientAccounts);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save accounts to file", e);
        }
    }

    public ClientAccount findByAccountNumber(String accountNumber) {
        return clientAccounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public ClientAccount findByUsername(String username) {
        return clientAccounts.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public ClientAccount findById(String id) {
        return clientAccounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<ClientAccount> findAll() {
        return new ArrayList<>(clientAccounts);
    }

    public boolean existsByAccountNumber(String accountNumber) {
        return clientAccounts.stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }
}
