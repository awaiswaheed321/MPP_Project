package com.library.windows;

import com.library.classes.Book;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ResearchBookWindow extends LibrarySystemWindow {
    public static final ResearchBookWindow INSTANCE = new ResearchBookWindow();
    private JTextField searchField;

    private JTable jtable;

    private List<Book> books;
    private List<Book> filteredBooks;

    private boolean isFiltered = false;
    private DefaultTableModel bookTableModel;
    private String[] columnNames = {"ISBN", "Title", "Authors", "Copies"};

    private int selectedRowIndex = -1;

    private JButton addCopyButton;

    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Singleton class
    private ResearchBookWindow() {
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Research Book");
        Util.adjustLabelFont(titleLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(titleLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        searchPanel.setLayout(fl);
        searchField = new JTextField("");
        searchField.setColumns(30);
        searchPanel.add(searchField);
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!Objects.equals(searchField.getText(), "")) {
                    bookTableModel = bookModel(toBooksData(filterBook()), columnNames);
                    isFiltered = true;
                } else {
                    bookTableModel = bookModel(toBooksData(books), columnNames);
                    isFiltered = false;
                }
                jtable.setModel(bookTableModel);
                jtable.updateUI();

                addCopyButton.setEnabled(false);
            }
        });

        JPanel tablePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        books = ci.allBooks();
        String[][] booksData = toBooksData(books);
        bookTableModel = bookModel(booksData, columnNames);

        jtable = new JTable();
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setFillsViewportHeight(true);
        jtable.setPreferredScrollableViewportSize(new Dimension((int) (screenSize.width * 0.45), (int) (screenSize.height * 0.3)));
        jtable.setModel(bookTableModel);
        jtable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int currentSelectedRow = jtable.getSelectedRow();
                if (currentSelectedRow != -1) {
                    selectedRowIndex = currentSelectedRow;
                    addCopyButton.setEnabled(true);
                }

                if (e.getClickCount() == 2) {
                    moveToAddBookCopyWindow();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tablePanel.add(new JScrollPane(jtable), BorderLayout.CENTER);

        middlePanel.add(searchPanel, BorderLayout.NORTH);
        middlePanel.add(tablePanel, BorderLayout.CENTER);

        addCopyButton = new JButton("Add a new copy");
        addCopyButton.setEnabled(false);
        addCopyButton.addActionListener(e -> {
            moveToAddBookCopyWindow();
        });
        middlePanel.add(addCopyButton, BorderLayout.SOUTH);
    }

    public void reloadBooks() {
        books = ci.allBooks();
        bookTableModel = bookModel(toBooksData(books), columnNames);
        jtable.setModel(bookTableModel);
        jtable.repaint();

        addCopyButton.setEnabled(false);
    }

    private void moveToAddBookCopyWindow() {
        LibrarySystem.hideAllWindows();

        Book book = books.get(selectedRowIndex);
        if (isFiltered) {
            book = filteredBooks.get(selectedRowIndex);
        }

        AddBookCopyWindow.INSTANCE.init(book);
        AddBookCopyWindow.INSTANCE.pack();
        AddBookCopyWindow.INSTANCE.setSize((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5));
        Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
        AddBookCopyWindow.INSTANCE.setVisible(true);
    }

    public String[][] toBooksData(List<Book> books) {
        String[][] booksData = new String[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            booksData[i] = new String[]{book.getIsbn(), book.getTitle(), book.getAuthorsDisplay(), String.valueOf(book.getCopies().size())};
        }

        return booksData;
    }

    public List<Book> filterBook() {
        filteredBooks = new ArrayList<>();
        String searchValue = searchField.getText();
        for (Book book : books) {
            if (book.getIsbn().toLowerCase().contains(searchValue) || book.getTitle().toLowerCase().contains(searchValue)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    private DefaultTableModel bookModel(Object[][] data, String[] headers) {
        return new DefaultTableModel(data, headers) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}


