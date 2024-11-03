package com.bank.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bank.controller.ClientController;
import com.bank.view.panels.client.AccountOverviewPanel;
import com.bank.view.panels.client.DepositPanel;
import com.bank.view.panels.client.SettingsPanel;
import com.bank.view.panels.client.TransactionHistoryPanel;
import com.bank.view.panels.client.TransferPanel;
import com.bank.view.panels.client.WithdrawPanel;
import com.formdev.flatlaf.FlatDarkLaf;

public class ClientGUI extends JFrame {

    static {
        FlatDarkLaf.setup();
    }

    private final String username;
    private final String accountNumber;
    private final ClientController clientController;
    private JPanel contentPanel;  // Add this field
    private JButton selectedButton;
    


    public ClientGUI(String username) {
        this.username = username;
        this.clientController = new ClientController();
        this.accountNumber = clientController.getAccountNumber(username);
        this.contentPanel = new JPanel(new CardLayout());


        setTitle("Client Dashboard - " + username);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = createGradientPanel();
        mainPanel.setLayout(new BorderLayout());

        initializePanels();
        
        JPanel navPanel = createNavPanel();
        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void initializePanels() {
        contentPanel.add(new AccountOverviewPanel(accountNumber), "Account Overview");
        contentPanel.add(new TransferPanel(this, accountNumber), "Transfer Money");
        contentPanel.add(new DepositPanel(this, accountNumber), "Deposit");
        contentPanel.add(new WithdrawPanel(this, accountNumber), "Withdraw");
        contentPanel.add(new TransactionHistoryPanel(accountNumber), "Transaction History");
        contentPanel.add(new SettingsPanel(username), "Settings");
    }

    public void refreshAccountOverview() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        // Recreate the account overview panel with fresh data
        contentPanel.remove(0);
        
        contentPanel.add(new AccountOverviewPanel(accountNumber), "Account Overview");
        // Show the updated panel
        cl.show(contentPanel, "Account Overview");
    }

    public void refreshTransactionHistory() {
        // Recreate the transaction history panel with fresh data
        contentPanel.remove(3);
        contentPanel.add(new TransactionHistoryPanel(accountNumber), "Transaction History");
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(24, 24, 24));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        navPanel.setPreferredSize(new Dimension(250, getHeight()));
        navPanel.setMinimumSize(new Dimension(250, getHeight()));

        String[] options = {
            "Account Overview",
            "Transfer Money",
            "Deposit",
            "Withdraw",
            "Transaction History",
            "Settings",
            "Logout"
        };

        for (String option : options) {
            JButton button = createNavButton(option);
            navPanel.add(button);
            navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return navPanel;
    }

    private JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(33, 33, 33),
                        w, h, new Color(18, 18, 18));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(60, 60, 60));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 45, 45));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (text.equals("Logout")) {
                    handleLogout();
                } else {
                    updateButtonStyles(button);
                    CardLayout cl = (CardLayout) contentPanel.getLayout();
                    cl.show(contentPanel, text);
                }
            }
        });

        return button;
    }

    private void updateButtonStyles(JButton clickedButton) {
        if (selectedButton != null) {
            selectedButton.setBackground(new Color(45, 45, 45));
            selectedButton.setBorder(null);
        }
        
        selectedButton = clickedButton;
        clickedButton.setBackground(new Color(60, 60, 60));
        clickedButton.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(0, 150, 136)));
    }



    private void handleLogout() {
        new MainLoginGUI().setVisible(true);
        this.dispose();
    }
}
