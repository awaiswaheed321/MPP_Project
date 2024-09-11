package com.library.windows;

import com.library.classes.Book;
import com.library.classes.CheckoutEntry;
import com.library.controllers.SystemController;
import com.library.interfaces.ControllerInterface;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


@SuppressWarnings("serial")
public class CheckoutRecordWindow extends LibrarySystemWindow {
    public static final CheckoutRecordWindow INSTANCE = new CheckoutRecordWindow();
    ControllerInterface ci = new SystemController();

    private CheckoutEntry checkoutEntry;

    private CheckoutRecordWindow() {
    }

    public void setCheckoutEntry(CheckoutEntry checkoutEntry) {
        this.checkoutEntry = checkoutEntry;
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

        JLabel memberIdLabel = new JLabel("Member ID: " + checkoutEntry.getMember().getMemberId());
        memberInfoPanel.add(memberIdLabel);
        JLabel memberNameLabel = new JLabel("Member name: " + checkoutEntry.getMember().getFullName());
        memberInfoPanel.add(memberNameLabel);

        middlePanel.add(memberInfoPanel, BorderLayout.NORTH);
    }

    private DefaultTableModel getCheckoutModel() {
        Book book = checkoutEntry.getBookCopy().getBook();
        String[] columnNames = {"ISBN", "Title", "Copy No", "Due Date"};
        String[][] checkoutData = new String[1][3];
        checkoutData[0] = new String[]{
                book.getIsbn(),
                book.getTitle(),
                String.valueOf(checkoutEntry.getBookCopy().getCopyNum()),
                checkoutEntry.getDueDate().toString()
        };
        return new DefaultTableModel(checkoutData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
