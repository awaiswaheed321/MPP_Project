package com.library.windows;

import com.library.classes.Address;
import com.library.classes.Author;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.DataAccess;
import com.library.services.DataAccessFacade;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class AddAuthorWindow extends LibrarySystemWindow {
    ControllerInterface ci = new SystemController();
    public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
    DataAccess da = new DataAccessFacade();

    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField telephoneField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JTextArea bioField;

    // Singleton class
    private AddAuthorWindow() {
    }

    @Override
    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Add New Author");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(titleLabel);
    }

    @Override
    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        defineFormPanel();
        middlePanel.add(formPanel, BorderLayout.CENTER);
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineButtonPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        setTitle("Add New Author");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        isInitialized = true;
    }

    private void defineFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Telephone:"));
        telephoneField = new JTextField();
        formPanel.add(telephoneField);

        formPanel.add(new JLabel("Street:"));
        streetField = new JTextField();
        formPanel.add(streetField);

        formPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        formPanel.add(cityField);

        formPanel.add(new JLabel("State:"));
        stateField = new JTextField();
        formPanel.add(stateField);

        formPanel.add(new JLabel("Zip:"));
        zipField = new JTextField();
        formPanel.add(zipField);

        formPanel.add(new JLabel("Bio:"));
        bioField = new JTextArea(3, 20);
        bioField.setLineWrap(true);
        formPanel.add(new JScrollPane(bioField));
    }

    private void defineButtonPanel() {
        JButton submitButton = new JButton("Add Author");
        submitButton.addActionListener(new SubmitButtonListener());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String telephone = telephoneField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zip = zipField.getText();
            String bio = bioField.getText();
            Address addr = new Address(street, city, state, zip);
            Author newAuthor = new Author(firstName, lastName, telephone, addr, bio);
            da.saveNewAuthor(newAuthor);
            clearInputs();
            dispose();
            AllAuthorsWindow.INSTANCE.init();
            List<Author> authors = ci.getAllAuthors();
            authors.sort(Comparator.comparing(Author::getLastName));
            AllAuthorsWindow.INSTANCE.setData(authors);
            AllAuthorsWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllAuthorsWindow.INSTANCE);
            AllAuthorsWindow.INSTANCE.setVisible(true);
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearInputs();
            dispose(); // Close the window without making changes
        }
    }

    private void clearInputs() {
        firstNameField.setText("");
        lastNameField.setText("");
        telephoneField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        bioField.setText("");
    }
}

