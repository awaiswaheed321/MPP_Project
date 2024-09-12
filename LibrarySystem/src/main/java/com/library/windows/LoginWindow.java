package com.library.windows;

import com.library.Main;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;


public class LoginWindow extends LibrarySystemWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();

    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField username;
    private JTextField password;
    private JLabel label;
    private JButton loginButton;

    /* This class is a singleton */
    private LoginWindow() {
    }

    @Override
    public void init() {
        if (isInitialized) return;

        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        setPreferredSize(new Dimension(500, 400));
        // Use GridBagLayout for centering everything
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setLayout(gbl);

        // Adding vertical spacers for centering
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;  // Spacer at the top (expandable space)
        gbc.fill = GridBagConstraints.BOTH; // Fills the space vertically and horizontally
        mainPanel.add(new JPanel(), gbc);  // Empty spacer panel at the top

        // Centered content (upperHalf, middleHalf, lowerHalf)
        gbc.gridy = 1;
        gbc.weighty = 0.0; // No vertical expansion for the content
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;  // Do not stretch components in the center
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(upperHalf, BorderLayout.NORTH);
        contentPanel.add(middleHalf, BorderLayout.CENTER);
        contentPanel.add(lowerHalf, BorderLayout.SOUTH);
        mainPanel.add(contentPanel, gbc);

        // Spacer at the bottom (expandable space)
        gbc.gridy = 2;
        gbc.weighty = 1.0;  // Spacer at the bottom
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JPanel(), gbc);

        getContentPane().add(mainPanel);
        isInitialized = true;
        pack();
    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new GridBagLayout()); // For internal alignment
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        upperHalf.add(topPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        upperHalf.add(middlePanel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        upperHalf.add(lowerPanel, gbc);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);
    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centered alignment for buttons
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Login");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centered alignment for labels
        topPanel.add(intPanel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centered alignment for text fields
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        loginButton = new JButton("Login");
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
    }

    private void defineLeftTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        username = new JTextField(10);
        label = new JLabel("Username");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(username);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.NORTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void defineRightTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        password = new JPasswordField(10);
        label = new JLabel("Password");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(password);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.NORTH);
        rightTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void addLoginButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String usernameValue = username.getText();
            String passwordValue = password.getText();

            try {
                SystemController sc = new SystemController();
                sc.login(usernameValue, passwordValue);
                clearInputs();
                showMainWindow();

            } catch (Exception e) {
                showLoginError(e.getMessage());
            }
        });
    }

    private void clearInputs() {
        username.setText("");
        password.setText("");
    }

    private void showLoginError(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void showMainWindow() {
        LibrarySystem.hideAllWindows();
        LibrarySystem.INSTANCE.setTitle("Library Application");
        LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LibrarySystem.INSTANCE.init();
        Main.centerFrameOnDesktop(LibrarySystem.INSTANCE);
        LibrarySystem.INSTANCE.setVisible(true);
    }
}
