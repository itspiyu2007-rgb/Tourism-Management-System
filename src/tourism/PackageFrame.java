
package tourism;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageFrame extends JFrame {

    private JTextField packageNameField;
    private JTextField destinationField;
    private JTextField durationField;
    private JTextField priceField;

    private DefaultTableModel model;
    private JTable table;

    public PackageFrame() {

        setTitle("Tourism Management System - Tour Packages");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // FORM FIELDS
        packageNameField = new JTextField(20);
        destinationField = new JTextField(20);
        durationField = new JTextField(20);
        priceField = new JTextField(20);

        JPanel fieldsPanel = new JPanel(new GridLayout(1, 4, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fieldsPanel.add(createLabelPanel("Package Name", packageNameField));
        fieldsPanel.add(createLabelPanel("Destination", destinationField));
        fieldsPanel.add(createLabelPanel("Duration", durationField));
        fieldsPanel.add(createLabelPanel("Price", priceField));

        // BUTTONS
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        // BLUE OUTLINE FORM BOX
        JPanel formBox = new JPanel(new BorderLayout());
        formBox.setBackground(Color.WHITE);
        formBox.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 190, 220), 1),
                "Package Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(35, 55, 75)
        ));

        formBox.add(fieldsPanel, BorderLayout.CENTER);
        formBox.add(buttonPanel, BorderLayout.SOUTH);

        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(new Color(245, 247, 250));
        formContainer.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        formContainer.add(formBox, BorderLayout.CENTER);

        // TABLE
        model = new DefaultTableModel(
                new String[]{
                        "Package ID",
                        "Package Name",
                        "Destination",
                        "Duration",
                        "Price"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(formContainer, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

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

        loadPackageData();

        addButton.addActionListener(e -> addPackage());
        editButton.addActionListener(e -> editPackage());
        deleteButton.addActionListener(e -> deletePackage());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> dispose());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();

                if (row >= 0) {
                    packageNameField.setText(model.getValueAt(row, 1).toString());
                    destinationField.setText(model.getValueAt(row, 2).toString());
                    durationField.setText(model.getValueAt(row, 3).toString());
                    priceField.setText(model.getValueAt(row, 4).toString());
                }
            }
        });
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

    private JPanel createLabelPanel(String labelText, JTextField field) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // LOAD PACKAGE DATA
    private void loadPackageData() {

        model.setRowCount(0);

        String sql = "SELECT * FROM tbl_package";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("package_name"),
                        rs.getString("destination"),
                        rs.getString("duration"),
                        rs.getBigDecimal("price")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading packages: " + e.getMessage()
            );
        }
    }

    // ADD PACKAGE
    private void addPackage() {

        String name = packageNameField.getText().trim();
        String destination = destinationField.getText().trim();
        String duration = durationField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || destination.isEmpty()
                || duration.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        BigDecimal price;

        try {
            price = new BigDecimal(priceText);

            if (price.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.");
            return;
        }

        String sql = "INSERT INTO tbl_package "
                + "(package_name, destination, duration, price) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, destination);
            pst.setString(3, duration);
            pst.setBigDecimal(4, price);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Package added successfully!");

            loadPackageData();
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error adding package: " + e.getMessage()
            );
        }
    }

    // EDIT PACKAGE
    private void editPackage() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a package.");
            return;
        }

        String name = packageNameField.getText().trim();
        String destination = destinationField.getText().trim();
        String duration = durationField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || destination.isEmpty()
                || duration.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        BigDecimal price;

        try {
            price = new BigDecimal(priceText);

            if (price.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.");
            return;
        }

        int packageId = Integer.parseInt(model.getValueAt(row, 0).toString());

        String sql = "UPDATE tbl_package SET package_name = ?, destination = ?, "
                + "duration = ?, price = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, destination);
            pst.setString(3, duration);
            pst.setBigDecimal(4, price);
            pst.setInt(5, packageId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Package updated successfully!");

            loadPackageData();
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error updating package: " + e.getMessage()
            );
        }
    }

    // DELETE PACKAGE
    private void deletePackage() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a package.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this package?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            int packageId = Integer.parseInt(model.getValueAt(row, 0).toString());

            String sql = "DELETE FROM tbl_package WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, packageId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Package deleted successfully!");

                loadPackageData();
                clearFields();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting package: " + e.getMessage()
                );
            }
        }
    }

    // CLEAR FIELDS
    private void clearFields() {

        packageNameField.setText("");
        destinationField.setText("");
        durationField.setText("");
        priceField.setText("");

        table.clearSelection();
    }
}
