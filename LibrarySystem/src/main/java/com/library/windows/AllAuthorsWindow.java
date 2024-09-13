package com.library.windows;

import com.library.classes.Address;
import com.library.classes.Author;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.List;

public class AllAuthorsWindow extends LibrarySystemWindow {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final AllAuthorsWindow INSTANCE = new AllAuthorsWindow();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JTable jtable;
    List<Author> authorsList;

    // Singleton class
    private AllAuthorsWindow() {
    }

    @Override
    public void init() {
        if (!isInitialized) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            defineTopPanel();
            defineMiddlePanel();
            defineLowerPanel();
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(middlePanel, BorderLayout.CENTER);
            mainPanel.add(lowerPanel, BorderLayout.SOUTH);
            getContentPane().add(mainPanel);
            isInitialized = true;
        }
    }

    @Override
    public void defineTopPanel() {
        if (topPanel == null) {
            topPanel = new JPanel();
            JLabel allAuthorsLabel = new JLabel("All Authors and Biographies");
            Util.adjustLabelFont(allAuthorsLabel, Util.DARK_BLUE, true);
            topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            topPanel.add(allAuthorsLabel);
        }
    }

    @Override
    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        jtable = new JTable();
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setFillsViewportHeight(true);
        DefaultTableModel tableModel = getAuthorTableModel();
        jtable.setModel(tableModel);
        jtable.repaint();
        middlePanel.add(new JScrollPane(jtable), BorderLayout.CENTER);
    }

    @Override
    public void defineLowerPanel() {
        if (lowerPanel == null) {
            JButton backToMainBtn = new JButton("<= Back to Main");
            backToMainBtn.addActionListener(new BackToMainListener());

            JButton addAuthorButton = new JButton("Add Author");
            addAuthorButton.addActionListener(new AddAuthorListener());

            lowerPanel = new JPanel();
            lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lowerPanel.add(backToMainBtn);
            lowerPanel.add(addAuthorButton);
        }
    }

    static class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    private static class AddAuthorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AddAuthorWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddAuthorWindow.INSTANCE);
            AddAuthorWindow.INSTANCE.setVisible(true);
        }
    }

    public void setData(List<Author> authors) {
        authorsList = authors;
        if (jtable != null) {
            DefaultTableModel tableModel = getAuthorTableModel();
            jtable.setModel(tableModel);
            jtable.repaint(); // Refresh the table to show updated data
        }
    }

    public DefaultTableModel getAuthorTableModel() {
        String[] columnNames = {"Author ID", "First Name", "Last Name", "Telephone", "Street", "City", "State", "Zip", "Bio"};
        String[][] authorData = new String[authorsList.size()][9];

        for (int i = 0; i < authorsList.size(); i++) {
            Author author = authorsList.get(i);
            Address address = author.getAddress();
            authorData[i] = new String[]{
                    author.getAuthorId(),               // Author ID
                    author.getFirstName(),              // First Name
                    author.getLastName(),               // Last Name
                    author.getTelephone(),              // Telephone
                    address.getStreet(),                // Street
                    address.getCity(),                  // City
                    address.getState(),                 // State
                    address.getZip(),                   // Zip
                    author.getBio()                     // Bio
            };
        }
        return new DefaultTableModel(authorData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
    }
}
