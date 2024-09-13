package com.library.windows;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.exceptions.ValidationException;
import com.library.interfaces.ControllerInterface;
import com.library.services.SystemController;
import com.library.services.ValidationService;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddBookWindow extends LibrarySystemWindow {
    public static final AddBookWindow INSTANCE = new AddBookWindow();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField copiesField;
    private JRadioButton checkout21Days;
    private JRadioButton checkout7Days;
    private JList<Author> authorList;
    private ButtonGroup checkoutGroup; // Group for radio buttons

    private ControllerInterface ci = new SystemController(); // Controller for handling data

    // Singleton class
    private AddBookWindow() {
    }

    @Override
    public void init() {
        if (!isInitialized) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            defineTopPanel();
            defineMiddlePanel();
            defineLowerPanel();

            getContentPane().add(mainPanel);
            isInitialized = true;
        }
    }

    @Override
    public void defineTopPanel() {
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Add New Book");
        Util.adjustLabelFont(label, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(label);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    @Override
    public void defineMiddlePanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(15);

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(15);

        JLabel copiesLabel = new JLabel("Copies:");
        copiesField = new JTextField(15);

        JLabel maxCheckoutLabel = new JLabel("Max Checkout Length:");

        // Radio buttons for checkout length
        checkout21Days = new JRadioButton("21 days");
        checkout7Days = new JRadioButton("7 days");

        // Group the radio buttons
        checkoutGroup = new ButtonGroup();
        checkoutGroup.add(checkout21Days);
        checkoutGroup.add(checkout7Days);

        // Set default selection
        checkout21Days.setSelected(true);

        JLabel authorLabel = new JLabel("Select Authors:");

        authorList = new JList<>(ci.getAllAuthors().toArray(new Author[0])); // Fetch all authors
        authorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        authorList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Author author = (Author) value;
                String displayName = author.getFirstName() + " " + author.getLastName();
                return super.getListCellRendererComponent(list, displayName, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane authorScrollPane = new JScrollPane(authorList);
        authorScrollPane.setPreferredSize(new Dimension(150, 100));

        // Add components to the panel with GridBagConstraints
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10); // Padding between components

        gc.gridx = 0;
        gc.gridy = 0;
        middlePanel.add(isbnLabel, gc);

        gc.gridx = 1;
        middlePanel.add(isbnField, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        middlePanel.add(titleLabel, gc);

        gc.gridx = 1;
        middlePanel.add(titleField, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        middlePanel.add(copiesLabel, gc);

        gc.gridx = 1;
        middlePanel.add(copiesField, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        middlePanel.add(maxCheckoutLabel, gc);

        gc.gridx = 1;
        JPanel radioPanel = new JPanel();
        radioPanel.add(checkout21Days);
        radioPanel.add(checkout7Days);
        middlePanel.add(radioPanel, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        middlePanel.add(authorLabel, gc);

        gc.gridx = 1;
        middlePanel.add(authorScrollPane, gc);

        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }

    @Override
    public void defineLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Add Book");
        saveButton.addActionListener(new AddBookListener());

        JButton clearButton = new JButton("Clear Fields");
        clearButton.addActionListener(e -> clearFields());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
            LibrarySystem.INSTANCE.setVisible(true);
        });

        lowerPanel.add(saveButton);
        lowerPanel.add(clearButton);
        lowerPanel.add(cancelButton);

        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }

    // Clear fields method
    private void clearFields() {
        isbnField.setText("");
        titleField.setText("");
        copiesField.setText("");
        checkoutGroup.clearSelection(); // Clear radio button selection
        authorList.clearSelection();
    }

    class AddBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get input values
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String copiesStr = copiesField.getText().trim();
            List<Author> selectedAuthors = authorList.getSelectedValuesList();
            boolean isCheckoutSelected = checkout21Days.isSelected() || checkout7Days.isSelected();
            int maxCheckoutLength = checkout21Days.isSelected() ? 21 : 7;

            try {
                // Validate inputs using ValidationService
                ValidationService.validateBook(isbn, title, copiesStr, selectedAuthors, isCheckoutSelected);

                // If validation is successful, proceed with book creation
                int copies = Integer.parseInt(copiesStr);
                Book newBook = new Book(isbn, title, maxCheckoutLength, selectedAuthors);

                // Add additional copies
                for (int i = 0; i < copies - 1; i++) {
                    newBook.addCopy();
                }
                ci.saveNewBook(newBook);
                clearFields();
                JOptionPane.showMessageDialog(null, "Book added successfully!");
                LibrarySystem.hideAllWindows();
                AddBookWindow.INSTANCE.setVisible(true);
            } catch (ValidationException ve) {
                // Display validation errors
                JOptionPane.showMessageDialog(null, String.join("\n", ve.getErrors()), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Copies must be a valid number.");
            }
        }
    }


    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
}
