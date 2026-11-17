
package tourism;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Tourism Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header
        JPanel header = createProjectHeader();
        mainPanel.add(header, BorderLayout.NORTH);

        // Login panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login heading
        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 26));
        loginTitle.setForeground(new Color(44, 62, 80));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        card.add(loginTitle, gbc);

        // Username
        gbc.gridy++;
        gbc.gridwidth = 1;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);

        gbc.gridx = 1;
        card.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridx = 1;
        card.add(passwordField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(roleLabel, gbc);

        String[] roles = {
                "Admin",
                "Agent",
                "Tourist"
        };

        JComboBox<String> roleBox = new JComboBox<>(roles);

        gbc.gridx = 1;
        card.add(roleBox, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(39, 174, 96));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        card.add(loginButton, gbc);

        // Information
        JLabel demoLabel = new JLabel(
                "<html><center>Login credentials are stored in database."
                        + "<br>Use your registered username and password."
                        + "</center></html>"
        );

        demoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        demoLabel.setForeground(Color.GRAY);

        gbc.gridy++;
        card.add(demoLabel, gbc);

        loginPanel.add(card);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(44, 62, 80));
        footer.setPreferredSize(new Dimension(900, 45));

        JLabel footerLabel = new JLabel("Made by Priyanshu Pareek");
        footerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerLabel.setForeground(Color.WHITE);

        footer.add(footerLabel);
        mainPanel.add(footer, BorderLayout.SOUTH);

        // Login Action - MySQL
        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();

            String password =
                    new String(passwordField.getPassword());

            String selectedRole =
                    (String) roleBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password."
                );

                return;
            }

            String sql = "SELECT * FROM tbl_login "
                    + "WHERE username = ? AND password = ? AND role = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, selectedRole);

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Login Successful!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        dispose();

                        new DashboardFrame().setVisible(true);

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid username, password or role.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        add(mainPanel);
    }

    // Dashboard-style Header
    private JPanel createProjectHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(900, 265));
        header.setBackground(Color.WHITE);

        // White upper section
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(900, 165));

        // Logo card
        JPanel logoCard = new JPanel(new GridBagLayout());
        logoCard.setBackground(Color.WHITE);
        logoCard.setPreferredSize(new Dimension(850, 150));

        logoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // First try project resource folder
        ImageIcon originalLogo = null;

        URL logoURL = getClass().getResource("/tourism/pietlogo.png");

        if (logoURL != null) {
            originalLogo = new ImageIcon(logoURL);
        }

        // If resource is not found, load from Downloads
        if (originalLogo == null || originalLogo.getIconWidth() <= 0) {

            File logoFile = new File(
                    "C:/Users/DELL/Downloads/pietlogo.png"
            );

            if (logoFile.exists()) {
                originalLogo = new ImageIcon(logoFile.getAbsolutePath());
            }
        }

        // Display logo
        if (originalLogo != null
                && originalLogo.getIconWidth() > 0
                && originalLogo.getIconHeight() > 0) {

            int originalWidth = originalLogo.getIconWidth();
            int originalHeight = originalLogo.getIconHeight();

            double scale = Math.min(
                    800.0 / originalWidth,
                    130.0 / originalHeight
            );

            int scaledWidth = (int) Math.round(originalWidth * scale);
            int scaledHeight = (int) Math.round(originalHeight * scale);

            Image scaledImage = originalLogo.getImage().getScaledInstance(
                    scaledWidth,
                    scaledHeight,
                    Image.SCALE_SMOOTH
            );

            logoLabel.setIcon(new ImageIcon(scaledImage));

        } else {

            logoLabel.setText("PIET LOGO NOT FOUND");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
            logoLabel.setForeground(Color.RED);
        }

        logoCard.add(logoLabel);

        topPanel.add(logoCard);

        // Blue lower section
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(41, 128, 185));
        bottomPanel.setPreferredSize(new Dimension(900, 100));

        JLabel projectTitle = new JLabel("TOURISM MANAGEMENT SYSTEM");
        projectTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectTitle.setFont(new Font("Arial", Font.BOLD, 25));
        projectTitle.setForeground(Color.WHITE);

        JLabel collegeName = new JLabel(
                "Poornima Institute of Engineering & Technology"
        );
        collegeName.setAlignmentX(Component.CENTER_ALIGNMENT);
        collegeName.setFont(new Font("Arial", Font.PLAIN, 15));
        collegeName.setForeground(Color.WHITE);

        bottomPanel.add(Box.createVerticalStrut(15));
        bottomPanel.add(projectTitle);
        bottomPanel.add(Box.createVerticalStrut(7));
        bottomPanel.add(collegeName);

        header.add(topPanel, BorderLayout.CENTER);
        header.add(bottomPanel, BorderLayout.SOUTH);

        return header;
    }
}
