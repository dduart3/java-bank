package com.bank.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.AdminAccount;

public class AdminAccountRepository {
    private static AdminAccountRepository instance;
    private final List<AdminAccount> adminAccounts;
    private static final String FILE_PATH = "admin_accounts.dat";

    public AdminAccountRepository() {
        this.adminAccounts = loadAccountsFromFile();
    }

    public static AdminAccountRepository getInstance() {
        if (instance == null) {
            instance = new AdminAccountRepository();
        }
        return instance;
    }

    public AdminAccount findByUsername(String username) {
        return adminAccounts.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public AdminAccount findById(String id) {
        return adminAccounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<AdminAccount> findAll() {
        return new ArrayList<>(adminAccounts);
    }

    @SuppressWarnings("unchecked")
    private List<AdminAccount> loadAccountsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<AdminAccount>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void save(AdminAccount account) {
        adminAccounts.add(account);
        saveAccountsToFile();
    }

    private void saveAccountsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(adminAccounts);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save accounts to file", e);
        }
    }
}
