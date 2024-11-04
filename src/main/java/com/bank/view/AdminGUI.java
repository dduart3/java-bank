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

import com.bank.controller.AdminController;
import com.bank.controller.ReportController;
import com.bank.view.panels.admin.AdminDashboardPanel;
import com.bank.view.panels.admin.ClientManagementPanel;
import com.bank.view.panels.admin.ReportsPanel;

public class AdminGUI extends JFrame {
    private final AdminController adminController;
    private final ReportController reportController;
    private JPanel contentPanel;
    private JButton selectedButton;
    private String username;

    public AdminGUI(String username) {
        this.adminController = new AdminController();
        this.reportController = new ReportController();
        this.username = username;

        setTitle("Bank Admin Panel");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(createGradientPanel(), BorderLayout.CENTER);
        
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setOpaque(false);
        
        // Add all panels
        contentPanel.add(new AdminDashboardPanel(username), "Dashboard");
        contentPanel.add(new ClientManagementPanel(), "Manage Accounts");
        contentPanel.add(new ReportsPanel(), "View Reports");
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.add(createNavPanel(), BorderLayout.WEST);
        mainContent.add(contentPanel, BorderLayout.CENTER);
        
        add(mainContent);
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

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(24, 24, 24));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        navPanel.setPreferredSize(new Dimension(250, getHeight()));
        navPanel.setMinimumSize(new Dimension(250, getHeight()));

        String[] options = {
            "Dashboard",
            "Manage Accounts",
            "View Reports",
            "Logout"
        };

        for (String option : options) {
            JButton button = createNavButton(option);
            navPanel.add(button);
            navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return navPanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(230, 40));
        button.setMaximumSize(new Dimension(230, 40));
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        button.addMouseListener(new MouseAdapter() {
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
            
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(60, 60, 60));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(new Color(45, 45, 45));
                }
            }
        });
        
        return button;
    }

    private void updateButtonStyles(JButton clickedButton) {
        if (selectedButton != null) {
            selectedButton.setBackground(new Color(45, 45, 45));
        }
        clickedButton.setBackground(new Color(60, 60, 60));
        selectedButton = clickedButton;
    }

    private void handleLogout() {
        new MainLoginGUI().setVisible(true);
        this.dispose();
    }

    public void refresh() {
        // Refresh all panels that need updating
        ((ClientManagementPanel) getPanel("Manage Accounts")).refresh();
        ((ReportsPanel) getPanel("View Reports")).refresh();
    }

    private JPanel getPanel(String name) {
        for (Component comp : contentPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                return (JPanel) comp;
            }
        }
        return null;
    }
}