package com.bank.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Account;
import com.bank.model.OperationResult;

public abstract class IAccountRepository<T extends Account> {

    public boolean isValidAccount(T account) {
        return account != null
                && account.getId() != null
                && !account.getId().trim().isEmpty()
                && account.getUsername() != null
                && !account.getUsername().trim().isEmpty();
    }

    public boolean isUsernameTaken(List<T> accounts, String username) {
        return accounts.stream()
                .anyMatch(existingAccount
                        -> existingAccount.getUsername().equals(username));
    }

    protected OperationResult save(List<T> accounts, T account, String FILE_PATH) {
        if (!isValidAccount(account)) {
            return OperationResult.INVALID_ACCOUNT;
        }

        int index = findAccountIndex(accounts, account.getId());

        if (index >= 0) {
            accounts.set(index, account);
        } else {
            if (isUsernameTaken(accounts, account.getUsername())) {
                return OperationResult.USERNAME_TAKEN;
            }
            accounts.add(account);
        }

        saveAccountsToFile(accounts, FILE_PATH);

        return OperationResult.SUCCESS;
    }

    protected OperationResult save(List<T> accounts, String FILE_PATH) {
        saveAccountsToFile(accounts, FILE_PATH);
        return OperationResult.SUCCESS;
    }

    protected T findById(List<T> accounts, String id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    protected T findByUsername(List<T> accounts, String username) {
        return accounts.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    protected List<T> findAll(List<T> accounts) {
        return new ArrayList<>(accounts);
    }

    @SuppressWarnings("unchecked")
    protected List<T> loadAccountsFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void saveAccountsToFile(List<T> accounts, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save accounts to file: " + filePath);
        }
    }

    private int findAccountIndex(List<T> accounts, String id) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
