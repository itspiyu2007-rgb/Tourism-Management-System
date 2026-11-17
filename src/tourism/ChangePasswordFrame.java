
package tourism;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordFrame extends JFrame {

    private static final String CURRENT_USERNAME = "admin";

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ChangePasswordFrame() {

        setTitle("Tourism Management System - Change Password");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // PASSWORD FIELDS
        oldPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 15, 18));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        fieldsPanel.add(createLabel("Current Password"));
        fieldsPanel.add(oldPasswordField);

        fieldsPanel.add(createLabel("New Password"));
        fieldsPanel.add(newPasswordField);

        fieldsPanel.add(createLabel("Confirm Password"));
        fieldsPanel.add(confirmPasswordField);

        // BUTTONS
        JButton changeButton = new JButton("Change Password");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(changeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        // BLUE OUTLINE FORM BOX
        JPanel formBox = new JPanel(new BorderLayout());
        formBox.setBackground(Color.WHITE);
        formBox.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 190, 220), 1),
                "Change Password Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(35, 55, 75)
        ));

        formBox.add(fieldsPanel, BorderLayout.CENTER);
        formBox.add(buttonPanel, BorderLayout.SOUTH);

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(new Color(245, 247, 250));
        formContainer.setBorder(BorderFactory.createEmptyBorder(35, 80, 35, 80));
        formContainer.add(formBox);

        mainPanel.add(formContainer, BorderLayout.CENTER);

        // FOOTER
        JLabel footer = new JLabel(
                "Made by Priyanshu Pareek",
                SwingConstants.CENTER
        );
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setPreferredSize(new Dimension(900, 45));
        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // BUTTON ACTIONS
        changeButton.addActionListener(e -> changePassword());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> dispose());
    }

    // HEADER

    private JPanel createProjectHeader() {

        // MAIN HEADER - FULL WIDTH
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 265));

        // TOP WHITE SECTION
        JPanel topSection = new JPanel(new GridBagLayout());
        topSection.setBackground(Color.WHITE);
        topSection.setPreferredSize(new Dimension(1000, 165));

        JPanel logoCard = new JPanel(new GridBagLayout());
        logoCard.setBackground(Color.WHITE);
        logoCard.setPreferredSize(new Dimension(850, 150));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalLogo = null;

        java.net.URL logoURL = getClass().getResource("/tourism/pietlogo.png");

        if (logoURL != null) {
            originalLogo = new ImageIcon(logoURL);
        } else {
            File logoFile = new File("C:/Users/DELL/Downloads/pietlogo.png");

            if (logoFile.exists()) {
                originalLogo = new ImageIcon(logoFile.getAbsolutePath());
            }
        }

        if (originalLogo != null) {

            int originalWidth = originalLogo.getIconWidth();
            int originalHeight = originalLogo.getIconHeight();

            double scale = Math.min(
                    800.0 / originalWidth,
                    130.0 / originalHeight
            );

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            Image image = originalLogo.getImage().getScaledInstance(
                    newWidth,
                    newHeight,
                    Image.SCALE_SMOOTH
            );

            logoLabel.setIcon(new ImageIcon(image));

        } else {
            logoLabel.setText("PIET Logo Not Found");
            logoLabel.setForeground(Color.RED);
        }

        logoCard.add(logoLabel);
        topSection.add(logoCard);

        // BLUE SECTION - FULL WIDTH
        JPanel bottomSection = new JPanel(new GridBagLayout());
        bottomSection.setBackground(new Color(41, 128, 185));
        bottomSection.setPreferredSize(new Dimension(1000, 100));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(41, 128, 185));

        JLabel projectTitle = new JLabel(
                "TOURISM MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        projectTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectTitle.setFont(new Font("Arial", Font.BOLD, 25));
        projectTitle.setForeground(Color.WHITE);

        JLabel collegeName = new JLabel(
                "Poornima Institute of Engineering & Technology",
                SwingConstants.CENTER
        );

        collegeName.setAlignmentX(Component.CENTER_ALIGNMENT);
        collegeName.setFont(new Font("Arial", Font.PLAIN, 18));
        collegeName.setForeground(Color.WHITE);

        textPanel.add(projectTitle);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(collegeName);

        bottomSection.add(textPanel);

        // ADD BOTH SECTIONS
        header.add(topSection, BorderLayout.NORTH);
        header.add(bottomSection, BorderLayout.CENTER);

        return header;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        return label;
    }

    // CHANGE PASSWORD
    private void changePassword() {

        String oldPassword = new String(oldPasswordField.getPassword()).trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password and confirm password do not match.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (newPassword.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must contain at least 6 characters.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // CHECK CURRENT PASSWORD
        String checkSql = "SELECT password FROM tbl_login WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            checkPs.setString(1, CURRENT_USERNAME);

            try (ResultSet rs = checkPs.executeQuery()) {

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "User account not found."
                    );
                    return;
                }

                String databasePassword = rs.getString("password");

                if (!databasePassword.equals(oldPassword)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Current password is incorrect.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }

            // UPDATE PASSWORD
            String updateSql = "UPDATE tbl_login SET password = ? WHERE username = ?";

            try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {

                updatePs.setString(1, newPassword);
                updatePs.setString(2, CURRENT_USERNAME);

                int result = updatePs.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Password changed successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Password could not be changed."
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CLEAR FIELDS
    private void clearFields() {

        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}
