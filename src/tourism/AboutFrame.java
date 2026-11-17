
package tourism;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class AboutFrame extends JFrame {

    public AboutFrame() {

        setTitle("Tourism Management System - About");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // HEADER
        mainPanel.add(createProjectHeader(), BorderLayout.NORTH);

        // CONTENT
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        );

        contentPanel.setBackground(Color.WHITE);

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 60, 20, 60)
        );

        // MAIN HEADING
        JLabel heading = new JLabel(
                "About Tourism Management System"
        );

        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(41, 128, 185));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(heading);
        contentPanel.add(Box.createVerticalStrut(18));

        // ABOUT ME
        JLabel aboutMeTitle = new JLabel("About Me");

        aboutMeTitle.setFont(new Font("Arial", Font.BOLD, 20));
        aboutMeTitle.setForeground(new Color(44, 62, 80));
        aboutMeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(aboutMeTitle);
        contentPanel.add(Box.createVerticalStrut(7));

        JTextArea aboutMe = new JTextArea(
                "I am Priyanshu Pareek, a B.Tech Computer Science "
                        + "Engineering student with an interest in programming "
                        + "and software development. I enjoy learning new "
                        + "technologies and building practical projects."
        );

        aboutMe.setFont(new Font("Arial", Font.PLAIN, 15));
        aboutMe.setLineWrap(true);
        aboutMe.setWrapStyleWord(true);
        aboutMe.setEditable(false);
        aboutMe.setFocusable(false);
        aboutMe.setOpaque(false);
        aboutMe.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutMe.setMaximumSize(new Dimension(720, 55));

        contentPanel.add(aboutMe);
        contentPanel.add(Box.createVerticalStrut(10));

        // SKILLS
        JLabel skills = new JLabel(
                "Skills: C++  |  Java  |  Web Development"
        );

        skills.setFont(new Font("Arial", Font.BOLD, 15));
        skills.setForeground(new Color(41, 128, 185));
        skills.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(skills);
        contentPanel.add(Box.createVerticalStrut(13));

        // PROJECT
        JLabel projectTitle = new JLabel("About the Project");

        projectTitle.setFont(new Font("Arial", Font.BOLD, 20));
        projectTitle.setForeground(new Color(44, 62, 80));
        projectTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(projectTitle);
        contentPanel.add(Box.createVerticalStrut(7));

        JTextArea project = new JTextArea(
                "The Tourism Management System is a Java Swing "
                        + "application developed to manage tourist destinations, "
                        + "tour packages and customer information. The project "
                        + "focuses on providing a simple, user-friendly and "
                        + "organized interface."
        );

        project.setFont(new Font("Arial", Font.PLAIN, 15));
        project.setLineWrap(true);
        project.setWrapStyleWord(true);
        project.setEditable(false);
        project.setFocusable(false);
        project.setOpaque(false);
        project.setAlignmentX(Component.CENTER_ALIGNMENT);
        project.setMaximumSize(new Dimension(720, 65));

        contentPanel.add(project);
        contentPanel.add(Box.createVerticalStrut(12));

        // INSTITUTE
        JLabel institute = new JLabel(
                "Poornima Institute of Engineering & Technology"
        );

        institute.setFont(new Font("Arial", Font.PLAIN, 16));
        institute.setForeground(new Color(70, 70, 70));
        institute.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(institute);
        contentPanel.add(Box.createVerticalStrut(8));

        // DEVELOPER
        JLabel developer = new JLabel(
                "Developed by: Priyanshu Pareek"
        );

        developer.setFont(new Font("Arial", Font.BOLD, 17));
        developer.setForeground(new Color(44, 62, 80));
        developer.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(developer);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // FOOTER
        JPanel footerPanel = new JPanel();

        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setPreferredSize(new Dimension(900, 45));

        JLabel footer = new JLabel(
                "Made by Priyanshu Pareek",
                SwingConstants.CENTER
        );

        footer.setFont(new Font("Arial", Font.BOLD, 14));
        footer.setForeground(Color.WHITE);

        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // DASHBOARD STYLE COMMON HEADER
    private JPanel createProjectHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(900, 265));
        header.setBackground(Color.WHITE);

        // WHITE TOP SECTION
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(900, 165));

        // LOGO CARD
        JPanel logoCard = new JPanel(new GridBagLayout());
        logoCard.setBackground(Color.WHITE);
        logoCard.setPreferredSize(new Dimension(850, 150));

        logoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalLogo = null;

        // FIRST: LOAD LOGO FROM PROJECT RESOURCES
        URL logoURL = getClass().getResource("/tourism/pietlogo.png");

        if (logoURL != null) {
            originalLogo = new ImageIcon(logoURL);
        }

        // SECOND: LOAD LOGO FROM DOWNLOADS
        if (originalLogo == null || originalLogo.getIconWidth() <= 0) {

            File logoFile = new File(
                    "C:/Users/DELL/Downloads/pietlogo.png"
            );

            if (logoFile.exists()) {
                originalLogo = new ImageIcon(logoFile.getAbsolutePath());
            }
        }

        // DISPLAY LOGO
        if (originalLogo != null
                && originalLogo.getIconWidth() > 0
                && originalLogo.getIconHeight() > 0) {

            int originalWidth = originalLogo.getIconWidth();
            int originalHeight = originalLogo.getIconHeight();

            double scale = Math.min(
                    800.0 / originalWidth,
                    130.0 / originalHeight
            );

            int scaledWidth =
                    (int) Math.round(originalWidth * scale);

            int scaledHeight =
                    (int) Math.round(originalHeight * scale);

            Image scaledImage = originalLogo.getImage()
                    .getScaledInstance(
                            scaledWidth,
                            scaledHeight,
                            Image.SCALE_SMOOTH
                    );

            logoLabel.setIcon(new ImageIcon(scaledImage));

        } else {

            logoLabel.setText("PIET LOGO NOT FOUND");
            logoLabel.setForeground(Color.RED);
            logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        }

        logoCard.add(logoLabel);
        topPanel.add(logoCard);

        // BLUE BOTTOM SECTION
        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(
                new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)
        );

        bottomPanel.setBackground(new Color(41, 128, 185));
        bottomPanel.setPreferredSize(new Dimension(900, 100));

        JLabel projectTitle = new JLabel(
                "TOURISM MANAGEMENT SYSTEM"
        );

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

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AboutFrame().setVisible(true);
        });
    }
}
