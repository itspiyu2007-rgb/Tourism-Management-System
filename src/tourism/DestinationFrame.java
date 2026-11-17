
package tourism;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DestinationFrame extends JFrame {

    private JTextField nameField;
    private JTextField locationField;
    private JTextField descriptionField;

    private DefaultTableModel tableModel;
    private JTable destinationTable;

    public DestinationFrame() {

        setTitle("Tourism Management System - Destinations");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // HEADER
        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createTitledBorder("Destination Details")
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Destination Name:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel descriptionLabel = new JLabel("Description:");

        nameField = new JTextField(15);
        locationField = new JTextField(15);
        descriptionField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(locationLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);

        // BUTTONS
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Destination");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        formPanel.add(buttonPanel, gbc);

        // TABLE
        String[] columns = {
                "Destination ID",
                "Destination Name",
                "Location",
                "Description"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        destinationTable = new JTable(tableModel);
        destinationTable.setRowHeight(28);
        destinationTable.setFont(new Font("Arial", Font.PLAIN, 14));

        destinationTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane = new JScrollPane(destinationTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 247, 250));

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        centerPanel.add(formPanel, BorderLayout.NORTH);
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

        // LOAD DATABASE RECORDS
        loadDestinationData();

        // BUTTON ACTIONS
        addButton.addActionListener(e -> addDestination());

        editButton.addActionListener(e -> editDestination());

        deleteButton.addActionListener(e -> deleteDestination());

        clearButton.addActionListener(e -> clearFields());

        backButton.addActionListener(e -> dispose());

        // SELECT TABLE ROW
        destinationTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = destinationTable.getSelectedRow();

                if (row >= 0) {

                    nameField.setText(
                            tableModel.getValueAt(row, 1).toString()
                    );

                    locationField.setText(
                            tableModel.getValueAt(row, 2).toString()
                    );

                    descriptionField.setText(
                            tableModel.getValueAt(row, 3).toString()
                    );
                }
            }
        });
    }

    // ================= HEADER DESIGN =================

    private JPanel createProjectHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 265));

        // TOP WHITE SECTION
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1000, 155));

        // LOGO CARD WITH SHADOW
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
        logoCard.setPreferredSize(new Dimension(850, 140));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // ACTUAL PIET LOGO
        String logoPath =
                "C:/Users/DELL/Downloads/pietlogo.png";

        ImageIcon originalLogo = new ImageIcon(logoPath);

        if (originalLogo.getIconWidth() > 0) {

            int originalWidth = originalLogo.getIconWidth();
            int originalHeight = originalLogo.getIconHeight();

            double scale = Math.min(
                    800.0 / originalWidth,
                    130.0 / originalHeight
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

            System.out.println("Logo not found: " + logoPath);
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

        JLabel institute = new JLabel(
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

    // ================= LOAD DATA =================

    private void loadDestinationData() {

        tableModel.setRowCount(0);

        String sql = "SELECT * FROM tbl_destination";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description")
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading destinations: " + e.getMessage()
            );
        }
    }

    // ================= ADD =================

    private void addDestination() {

        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty() || location.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String sql = "INSERT INTO tbl_destination(name, location, description) "
                + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, location);
            pst.setString(3, description);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Destination added successfully!"
            );

            loadDestinationData();
            clearFields();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error adding destination: " + e.getMessage()
            );
        }
    }

    // ================= EDIT =================

    private void editDestination() {

        int row = destinationTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a destination to edit."
            );

            return;
        }

        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty() || location.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        int destinationId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        String sql = "UPDATE tbl_destination "
                + "SET name = ?, location = ?, description = ? "
                + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, location);
            pst.setString(3, description);
            pst.setInt(4, destinationId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Destination updated successfully!"
            );

            loadDestinationData();
            clearFields();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating destination: " + e.getMessage()
            );
        }
    }

    // ================= DELETE =================

    private void deleteDestination() {

        int row = destinationTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a destination to delete."
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this destination?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            int destinationId = Integer.parseInt(
                    tableModel.getValueAt(row, 0).toString()
            );

            String sql = "DELETE FROM tbl_destination WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, destinationId);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Destination deleted successfully!"
                );

                loadDestinationData();
                clearFields();

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting destination: " + e.getMessage()
                );
            }
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        nameField.setText("");
        locationField.setText("");
        descriptionField.setText("");

        destinationTable.clearSelection();
    }
}
