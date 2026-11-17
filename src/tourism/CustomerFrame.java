
package tourism;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class CustomerFrame extends JFrame {

    private JTextField idField, nameField, phoneField, emailField, addressField;
    private DefaultTableModel model;
    private JTable table;

    public CustomerFrame() {

        setTitle("Tourism Management System - Customers");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // FORM FIELDS
        idField = new JTextField(15);
        nameField = new JTextField(15);
        phoneField = new JTextField(15);
        emailField = new JTextField(15);
        addressField = new JTextField(15);

        JPanel fieldsPanel = new JPanel(new GridLayout(1, 5, 12, 10));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fieldsPanel.add(createField("Customer ID", idField));
        fieldsPanel.add(createField("Customer Name", nameField));
        fieldsPanel.add(createField("Phone", phoneField));
        fieldsPanel.add(createField("Email", emailField));
        fieldsPanel.add(createField("Address", addressField));

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
                "Customer Details",
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
                        "Customer ID", "Name", "Phone", "Email", "Address"
                }, 0
        );

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

        loadCustomerData();

        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> dispose());

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = table.getSelectedRow();

                if (row >= 0) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    nameField.setText(model.getValueAt(row, 1).toString());
                    phoneField.setText(model.getValueAt(row, 2).toString());
                    emailField.setText(model.getValueAt(row, 3).toString());
                    addressField.setText(model.getValueAt(row, 4).toString());
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


    private JPanel createField(String labelText, JTextField field) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // DATABASE CONNECTION
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // LOAD CUSTOMER DATA
    private void loadCustomerData() {

        model.setRowCount(0);

        String sql = "SELECT * FROM tbl_customer";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("customer_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Load Error: " + e.getMessage()
            );
        }
    }

    // ADD CUSTOMER
    private void addCustomer() {

        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        String sql = "INSERT INTO tbl_customer "
                + "(customer_id, name, phone, email, address) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idField.getText().trim());
            ps.setString(2, nameField.getText().trim());
            ps.setString(3, phoneField.getText().trim());
            ps.setString(4, emailField.getText().trim());
            ps.setString(5, addressField.getText().trim());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer Added Successfully!");

            loadCustomerData();
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Add Error: " + e.getMessage()
            );
        }
    }

    // EDIT CUSTOMER
    private void editCustomer() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        }

        String sql = "UPDATE tbl_customer SET name=?, phone=?, email=?, "
                + "address=? WHERE customer_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nameField.getText().trim());
            ps.setString(2, phoneField.getText().trim());
            ps.setString(3, emailField.getText().trim());
            ps.setString(4, addressField.getText().trim());
            ps.setString(5, idField.getText().trim());

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Customer Updated!");
                loadCustomerData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Customer Not Found!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Update Error: " + e.getMessage()
            );
        }
    }

    // DELETE CUSTOMER
    private void deleteCustomer() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        }

        String customerId = model.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM tbl_customer WHERE customer_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer Deleted!");

            loadCustomerData();
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Delete Error: " + e.getMessage()
            );
        }
    }

    // CHECK EMPTY FIELDS
    private boolean isEmpty() {

        return idField.getText().trim().isEmpty()
                || nameField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty();
    }

    // CLEAR FIELDS
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");

        table.clearSelection();
    }
}
