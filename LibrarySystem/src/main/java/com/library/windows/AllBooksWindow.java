package com.library.windows;

import com.library.domain.Book;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.LibWindow;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.List;

public class AllBooksWindow extends JFrame implements LibWindow {
    @Serial
    private static final long serialVersionUID = 6696979618083051462L;
    public static final AllBooksWindow INSTANCE = new AllBooksWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTable booksTable;
    private List<Book> booksList;

    // Singleton class
    private AllBooksWindow() {
    }

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

    public void defineTopPanel() {
        if (topPanel == null) {
            topPanel = new JPanel();
            JLabel allBooksLabel = new JLabel("All Books and Details");
            Util.adjustLabelFont(allBooksLabel, Util.DARK_BLUE, true);
            topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            topPanel.add(allBooksLabel);
        }
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create the table and configure it
        booksTable = new JTable();
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        booksTable.setFillsViewportHeight(true);

        // Populate the table with the initial book data
        DefaultTableModel tableModel = getBookTableModel();
        booksTable.setModel(tableModel);

        middlePanel.add(new JScrollPane(booksTable), BorderLayout.CENTER);
    }

    public void defineLowerPanel() {
        if (lowerPanel == null) {
            JButton backToMainButn = new JButton("<= Back to Main");
            backToMainButn.addActionListener(new BackToMainListener());

            lowerPanel = new JPanel();
            lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lowerPanel.add(backToMainButn);
        }
    }

    static class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    // Method to set book data in the JTable
    public void setData(List<Book> books) {
        booksList = books;
        if (booksTable != null) {
            DefaultTableModel tableModel = getBookTableModel();
            booksTable.setModel(tableModel);
            booksTable.repaint();
        }
    }

    // Method to create a Table Model for the book data
    private DefaultTableModel getBookTableModel() {
        String[] columnNames = {"ISBN", "Title", "Number of Copies", "Max Checkout Length"};
        String[][] bookData = new String[booksList.size()][4];

        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            bookData[i] = new String[]{
                    book.getIsbn(),                               // ISBN
                    book.getTitle(),                              // Title
                    String.valueOf(book.getCopies().size()),      // Number of Copies
                    String.valueOf(book.getMaxCheckoutLength())   // Max Checkout Length
            };
        }

        return new DefaultTableModel(bookData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
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
