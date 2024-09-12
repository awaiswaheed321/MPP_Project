package com.library.windows;

import com.library.classes.Author;
import com.library.interfaces.LibWindow;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.List;

public class AllAuthorsWindow extends JFrame implements LibWindow {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final AllAuthorsWindow INSTANCE = new AllAuthorsWindow();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;

    // Singleton class
    private AllAuthorsWindow() {}

    public void init() {
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

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel allAuthorsLabel = new JLabel("All Authors and Biographies");
        Util.adjustLabelFont(allAuthorsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(allAuthorsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 50, 50);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8, 50);
        middlePanel.add(textArea);
    }

    public void defineLowerPanel() {
        JButton backToMainButn = new JButton("<= Back to Main");
        backToMainButn.addActionListener(new BackToMainListener());
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.add(backToMainButn);
    }

    static class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    // Method to set author data in the TextArea
    public void setData(List<Author> authors) {
        StringBuilder data = new StringBuilder();
        for (Author author : authors) {
            data.append("Author ID: ").append(author.getAuthorId()).append("\n");
            data.append("Name: ").append(author.getFirstName()).append(" ").append(author.getLastName()).append("\n");
            data.append("Bio: ").append(author.getBio()).append("\n\n");
        }
        textArea.setText(data.toString());
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
