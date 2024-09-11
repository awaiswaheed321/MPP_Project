package com.library.windows;

import com.library.classes.CheckoutEntry;
import com.library.controllers.SystemController;
import com.library.exceptions.BookCopyNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.LibraryMemberNotFoundException;
import com.library.interfaces.ControllerInterface;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class CheckoutBookWindow extends LibrarySystemWindow {
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
    ControllerInterface ci = new SystemController();
    private Label memberIdLabel;
    private TextField memberIdTextField;
    private Label isbnLabel;
    private TextField isbnTextField;
    private Button checkoutButton;

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

        memberIdLabel = new Label("Member ID:");
        middlePanel.add(memberIdLabel);

        memberIdTextField = new TextField(20);

        //TODO: remove after testing
        //memberIdTextField.setText("1001");

        middlePanel.add(memberIdTextField);

        isbnLabel = new Label("ISBN:");
        middlePanel.add(isbnLabel);

        isbnTextField = new TextField(20);

        //TODO: remove after testing
        //isbnTextField.setText("23-11451");

        middlePanel.add(isbnTextField);

        checkoutButton = new Button("Checkout");
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
        LibrarySystem.hideAllWindows();
        CheckoutRecordWindow.INSTANCE.setCheckoutEntry(entry);
        CheckoutRecordWindow.INSTANCE.init();
        CheckoutRecordWindow.INSTANCE.pack();
        CheckoutRecordWindow.INSTANCE.setVisible(true);
    }
}
