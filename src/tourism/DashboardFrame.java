
package tourism;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("Tourism Management System - Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // HEADER
        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // WELCOME
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        );

        JLabel welcome = new JLabel(
                "Welcome to Dashboard",
                SwingConstants.CENTER
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        welcome.setForeground(new Color(44, 62, 80));

        centerPanel.add(welcome, BorderLayout.NORTH);

        // DASHBOARD CARDS
        JPanel cardPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        cardPanel.setBackground(new Color(245, 247, 250));
        cardPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 20, 30)
        );

        JButton destinationButton =
                createCard("DESTINATIONS", "Explore Tourist Destinations");

        JButton packageButton =
                createCard("TOUR PACKAGES", "View Available Tour Packages");

        JButton customerButton =
                createCard("CUSTOMERS", "Manage Customer Details");

        JButton passwordButton =
                createCard("CHANGE PASSWORD", "Update Your Password");

        JButton aboutButton =
                createCard("ABOUT / CONTACT", "Project Information & Contact");

        JButton logoutButton =
                createCard("LOGOUT", "Return to Login Screen");

        cardPanel.add(destinationButton);
        cardPanel.add(packageButton);
        cardPanel.add(customerButton);
        cardPanel.add(passwordButton);
        cardPanel.add(aboutButton);
        cardPanel.add(logoutButton);

        centerPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // FOOTER
        JLabel footer = new JLabel(
                "Made by Priyanshu Pareek",
                SwingConstants.CENTER
        );

        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setPreferredSize(new Dimension(1000, 45));
        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // BUTTON ACTIONS
        destinationButton.addActionListener(e ->
                new DestinationFrame().setVisible(true));

        packageButton.addActionListener(e ->
                new PackageFrame().setVisible(true));

        customerButton.addActionListener(e ->
                new CustomerFrame().setVisible(true));

        passwordButton.addActionListener(e ->
                new ChangePasswordFrame().setVisible(true));

        aboutButton.addActionListener(e ->
                new AboutFrame().setVisible(true));

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    // CARD DESIGN
    private JButton createCard(String title, String subtitle) {

        JButton button = new JButton(
                "<html><center><b><font size='5'>"
                        + title
                        + "</font></b><br><br>"
                        + "<font size='3'>"
                        + subtitle
                        + "</font></center></html>"
        );

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(new Color(44, 62, 80));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220), 1
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 10, 20, 10
                        )
                )
        );

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // HEADER DESIGN
    private JPanel createProjectHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 265));

        // TOP WHITE SECTION
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1000, 155));

        // LOGO CARD
        JPanel logoCard = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(0, 0, 0, 35));

                g2.fillRoundRect(
                        2, 5,
                        getWidth() - 4,
                        getHeight() - 5,
                        22, 22
                );

                g2.setColor(Color.WHITE);

                g2.fillRoundRect(
                        0, 0,
                        getWidth() - 4,
                        getHeight() - 9,
                        22, 22
                );

                g2.dispose();
            }
        };

        logoCard.setOpaque(false);
        logoCard.setPreferredSize(new Dimension(850, 150));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // YOUR ACTUAL LOGO IMAGE LOCATION
        String logoPath =
                "C:/Users/DELL/Downloads/pietlogo.png";

        ImageIcon originalLogo = new ImageIcon(logoPath);

        if (originalLogo.getIconWidth() > 0) {

            int originalWidth = originalLogo.getIconWidth();
            int originalHeight = originalLogo.getIconHeight();

            // Maintain original image ratio
            double scale = Math.min(
                    800.0 / originalWidth,
                    115.0 / originalHeight
            );

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            Image scaledImage = originalLogo.getImage()
                    .getScaledInstance(
                            newWidth,
                            newHeight,
                            Image.SCALE_SMOOTH
                    );

            logoLabel.setIcon(new ImageIcon(scaledImage));

        } else {

            logoLabel.setText("PIET LOGO NOT FOUND");
            logoLabel.setForeground(Color.RED);

            System.out.println(
                    "Logo not found at: " + logoPath
            );
        }

        logoCard.add(logoLabel, BorderLayout.CENTER);
        topPanel.add(logoCard);

        // BOTTOM BLUE SECTION
        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(
                new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)
        );

        bottomPanel.setBackground(new Color(41, 128, 185));

        JLabel projectTitle =
                new JLabel("TOURISM MANAGEMENT SYSTEM");

        projectTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectTitle.setFont(
                new Font("Arial", Font.BOLD, 32)
        );
        projectTitle.setForeground(Color.WHITE);

        JLabel institute =
                new JLabel(
                        "Poornima Institute of Engineering & Technology"
                );

        institute.setAlignmentX(Component.CENTER_ALIGNMENT);
        institute.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );
        institute.setForeground(Color.WHITE);

        bottomPanel.add(Box.createVerticalStrut(12));
        bottomPanel.add(projectTitle);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(institute);
        bottomPanel.add(Box.createVerticalStrut(15));

        header.add(topPanel, BorderLayout.NORTH);
        header.add(bottomPanel, BorderLayout.CENTER);

        return header;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new DashboardFrame().setVisible(true)
        );
    }
}
