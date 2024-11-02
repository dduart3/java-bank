package com.bank.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.bank.model.AccountType;
import com.formdev.flatlaf.FlatDarkLaf;

public class MainLoginGUI extends JFrame {

    private final JButton adminLoginButton;
    private final JButton clientLoginButton;

    static {
        FlatDarkLaf.setup();
    }

    public MainLoginGUI() {
        setTitle("Bank System - Select Login Type");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 15, 15, 15);

        // Title
        JLabel titleLabel = new JLabel("Welcome to Bank System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Buttons with distinct styling
        adminLoginButton = createStyledButton("Admin Login", new Color(255, 69, 0), AccountType.ADMIN);
        clientLoginButton = createStyledButton("Client Login", new Color(0, 150, 136), AccountType.CLIENT);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 15, 5, 15);
        panel.add(adminLoginButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(5, 15, 15, 15);
        panel.add(clientLoginButton, gbc);

        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH));
       
        JLabel iconLabel = new JLabel(logoIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        gbc.gridy = 1;
        panel.add(iconLabel, gbc);

         // Gradient panel background
         panel.setOpaque(false);
         panel.setBackground(new Color(0, 0, 0, 0));
         setContentPane(new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 Graphics2D g2d = (Graphics2D) g;
                 g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                 int w = getWidth(), h = getHeight();
                 GradientPaint gp = new GradientPaint(0, 0, new Color(33, 33, 33),
                         0, h, new Color(18, 18, 18));
                 g2d.setPaint(gp);
                 g2d.fillRect(0, 0, w, h);
             }
         });
 
         // Add floating effect to buttons
         Timer timer = new Timer(50, e -> {
             float offset = 0;
 
             offset += 0.1;
             adminLoginButton.setBorder(BorderFactory.createEmptyBorder(
                     (int) (Math.sin(offset) * 2) + 5, 0, 5, 0));
             clientLoginButton.setBorder(BorderFactory.createEmptyBorder(
                     (int) (Math.sin(offset + Math.PI) * 2) + 5, 0, 5, 0));
             panel.repaint();
             panel.revalidate();
         });
         timer.start();

        add(panel);
}

private JButton createStyledButton(String text, Color color, AccountType accountType) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.WHITE);


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (accountType) {
                    case ADMIN:
                        openAdminLogin();
                        break;
                    case CLIENT:
                        openClientLogin();
                        break;
                    default:
                        openClientLogin();
                        break;
                }
            }
        });

        return button;
    }

    private void openAdminLogin() {
        new LoginGUI(AccountType.ADMIN, this).setVisible(true);
        this.setVisible(false);
    }

    private void openClientLogin() {
        new LoginGUI(AccountType.CLIENT, this).setVisible(true);
        this.setVisible(false);
    }
}
