package com.library.windows;

import com.library.classes.Book;
import com.library.classes.BookCopy;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;


public class AddBookCopyWindow extends LibrarySystemWindow {
    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    private JButton submitButton;

    private Book book;

    private JPanel bookDescriptionPanel;

    private JLabel bookIsbn;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JLabel bookCopies;

    private JTextField copyNumTextField;

    //Singleton class
    private AddBookCopyWindow() {
    }

    public void init() {
        super.init();
        jButtonText = "<= Back to Search Book";
    }

    public void init(Book book) {
        this.book = book;
        this.getContentPane().removeAll();
        isInitialized = false;
        init();
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Add a new Book Copy");
        Util.adjustLabelFont(titleLabel, Util.DARK_BLUE, true);
        titlePanel.add(titleLabel);

        topPanel.add(titlePanel, BorderLayout.NORTH);

        bookDescriptionPanel = new JPanel();
        bookDescriptionPanel.setLayout(new BoxLayout(bookDescriptionPanel, BoxLayout.PAGE_AXIS));

        bookIsbn = new JLabel("ISBN: " + this.book.getIsbn());
        bookTitle = new JLabel("Title: " + this.book.getTitle());
        bookAuthor = new JLabel("Authors: " + this.book.getAuthorsDisplay());
        bookCopies = new JLabel("Copies: " + this.book.getCopies().size());
        bookDescriptionPanel.add(bookIsbn);
        bookDescriptionPanel.add(bookTitle);
        bookDescriptionPanel.add(bookAuthor);
        bookDescriptionPanel.add(bookCopies);
        bookDescriptionPanel.add(new JSeparator());

        topPanel.add(new JPanel(), BorderLayout.LINE_START);
        topPanel.add(bookDescriptionPanel, BorderLayout.CENTER);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Copy Number");
        copyNumTextField = new JTextField();
        copyNumTextField.setText(String.valueOf(book.getCopies().size() + 1));
        copyNumTextField.setColumns(30);
        copyNumTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!Objects.equals(copyNumTextField.getText(), "")) {
                    submitButton.setEnabled(true);
                } else {
                    submitButton.setEnabled(false);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(copyNumTextField);
        middlePanel.add(panel, BorderLayout.NORTH);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            int copyNumber = Integer.parseInt(copyNumTextField.getText());
            for (BookCopy bookCopy : book.getCopies()) {
                if (bookCopy.getCopyNum() == copyNumber) {
                    JOptionPane.showMessageDialog(this, "A Book Copy with this number already exists.");
                    return;
                }
            }

            book.addCopy(copyNumber);
            ci.saveBook(book);

            ResearchBookWindow.INSTANCE.reloadBooks();

            bookCopies.setText("Copies: " + this.book.getCopies().size());
            bookCopies.repaint();

            JOptionPane.showMessageDialog(this, "Book Copy added successfully.");
        });
        middlePanel.add(submitButton, BorderLayout.SOUTH);
    }

    protected void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            ResearchBookWindow.INSTANCE.setVisible(true);
        });
    }
}


