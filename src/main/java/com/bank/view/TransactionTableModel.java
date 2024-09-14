package com.bank.view;

import com.bank.model.Transaction;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TransactionTableModel extends AbstractTableModel {
    private List<Transaction> transactions;
    private final String[] columnNames = {"Type", "Amount", "Date", "Description"};

    public TransactionTableModel(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0: return transaction.getType();
            case 1: return transaction.getAmount();
            case 2: return transaction.getTimestamp();
            case 3: return transaction.getDescription();
            default: return null;
        }
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        fireTableDataChanged();
    }
}
