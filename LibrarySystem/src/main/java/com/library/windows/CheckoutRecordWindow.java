package com.library.windows;

import com.library.classes.Book;
import com.library.classes.CheckoutEntry;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SuppressWarnings("serial")
public class CheckoutRecordWindow extends LibrarySystemWindow {
    public static final CheckoutRecordWindow INSTANCE = new CheckoutRecordWindow();

    private List<CheckoutEntry> checkoutEntries;

    private CheckoutRecordWindow() {
    }

    public void setCheckoutEntries(List<CheckoutEntry> checkoutEntries) {
        this.checkoutEntries = checkoutEntries;
    }

    public void defineTopPanel() {
        topPanel = new JPanel();

        JLabel checkoutRecordLabel = new JLabel("Checkout Record");
        Util.adjustLabelFont(checkoutRecordLabel, Util.DARK_BLUE, true);
        topPanel.add(checkoutRecordLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        showMemberInfo();

        JTable jtable = new JTable();
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setFillsViewportHeight(true);
        DefaultTableModel tableModel = getCheckoutModel();
        jtable.setModel(tableModel);
        jtable.repaint();

        middlePanel.add(new JScrollPane(jtable), BorderLayout.CENTER);
    }

    private void showMemberInfo() {
        JPanel memberInfoPanel = new JPanel();
        memberInfoPanel.setLayout(new BoxLayout(memberInfoPanel, BoxLayout.Y_AXIS));

        JLabel memberIdLabel = new JLabel("Member ID: " + checkoutEntries.get(0).getMember().getMemberId());
        memberInfoPanel.add(memberIdLabel);
        JLabel memberNameLabel = new JLabel("Member name: " + checkoutEntries.get(0).getMember().getFullName());
        memberInfoPanel.add(memberNameLabel);

        middlePanel.add(memberInfoPanel, BorderLayout.NORTH);
    }

    private DefaultTableModel getCheckoutModel() {
        String[] columnNames = {"ISBN", "Title", "Copy No", "Checkout Date", "Due Date"};
        String[][] checkoutData = new String[checkoutEntries.size()][5];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i < checkoutEntries.size(); i++) {
            CheckoutEntry entry = checkoutEntries.get(i);
            Book book = entry.getBookCopy().getBook();
            checkoutData[i] = new String[]{
                    book.getIsbn(),
                    book.getTitle(),
                    String.valueOf(entry.getBookCopy().getCopyNum()),
                    entry.getCheckOutDate().format(formatter), // Format checkout date
                    entry.getDueDate().format(formatter)       // Format due date
            };
        }
        return new DefaultTableModel(checkoutData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
