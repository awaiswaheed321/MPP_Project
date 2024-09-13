package com.library.windows;

import com.library.classes.CheckoutEntry;
import com.library.exceptions.LibrarySystemException;
import com.library.services.SystemController;
import com.library.exceptions.BookCopyNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.LibraryMemberNotFoundException;
import com.library.interfaces.ControllerInterface;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("serial")
public class CheckoutBookWindow extends LibrarySystemWindow {
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
    ControllerInterface ci = new SystemController();
    private TextField memberIdTextField;
    private TextField isbnTextField;

    private CheckoutBookWindow() {
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Checkout a book");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);

        Label memberIdLabel = new Label("Member ID:");
        middlePanel.add(memberIdLabel);

        memberIdTextField = new TextField(20);

        middlePanel.add(memberIdTextField);

        Label isbnLabel = new Label("ISBN:");
        middlePanel.add(isbnLabel);

        isbnTextField = new TextField(20);

        middlePanel.add(isbnTextField);

        Button checkoutButton = new Button("Checkout");
        checkoutButton.addActionListener(evt -> {
            String memberId = memberIdTextField.getText();
            String isbn = isbnTextField.getText();

            try {
                CheckoutEntry entry = ci.checkout(memberId, isbn);
                showCheckoutEntry(entry);
            } catch (LibraryMemberNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "The Member ID was not found!");
            } catch (BookNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "No book was found with the ISBN!");
            } catch (BookCopyNotAvailableException ex) {
                JOptionPane.showMessageDialog(this, "No copy of the book is available now!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Something went wrong, can not checkout now!");
            }
        });

        middlePanel.add(checkoutButton);
    }

    private void showCheckoutEntry(CheckoutEntry entry) {
        clearFields();
        LibrarySystem.hideAllWindows();
        CheckoutRecordWindow.INSTANCE.addCheckoutEntry(entry);
        CheckoutRecordWindow.INSTANCE.updateMemberInfoView();
        CheckoutRecordWindow.INSTANCE.init();
        CheckoutRecordWindow.INSTANCE.pack();
        CheckoutRecordWindow.INSTANCE.setVisible(true);
    }

    private void clearFields() {
        this.isbnTextField.setText("");
        this.memberIdTextField.setText("");
    }
}
