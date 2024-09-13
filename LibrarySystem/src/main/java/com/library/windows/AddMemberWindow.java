package com.library.windows;

import com.library.classes.Address;
import com.library.classes.LibraryMember;
import com.library.exceptions.ValidationException;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.DataAccess;
import com.library.services.DataAccessFacade;
import com.library.services.SystemController;
import com.library.services.ValidationService;
import com.library.utils.IDGeneratorUtil;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;

public class AddMemberWindow extends LibrarySystemWindow {
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();

    private TextField memberFirstNameTextField;
    private TextField memberLastNameTextField;
    private TextField memberPhoneTextField;

    private TextField memberStreetTextField;
    private TextField memberCityTextField;
    private TextField memberStateTextField;
    private TextField memberZipTextField;

    private AddMemberWindow() {
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Add a new Member");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gc.fill = GridBagConstraints.HORIZONTAL;

        // Firstname
        gc.gridx = 0;
        gc.gridy = 0;
        Label memberFirstNameLabel = new Label("First Name:");
        middlePanel.add(memberFirstNameLabel, gc);

        gc.gridx = 1;
        memberFirstNameTextField = new TextField(20);
        middlePanel.add(memberFirstNameTextField, gc);

        // Last Name
        gc.gridx = 0;
        gc.gridy = 1;
        Label memberLastNameLabel = new Label("Last Name:");
        middlePanel.add(memberLastNameLabel, gc);

        gc.gridx = 1;
        memberLastNameTextField = new TextField(20);
        middlePanel.add(memberLastNameTextField, gc);

        // Phone
        gc.gridx = 0;
        gc.gridy = 2;
        Label memberPhoneNameLabel = new Label("Phone Number:");
        middlePanel.add(memberPhoneNameLabel, gc);

        gc.gridx = 1;
        memberPhoneTextField = new TextField(20);
        middlePanel.add(memberPhoneTextField, gc);

        // Street
        gc.gridx = 0;
        gc.gridy = 3;
        Label memberStreetLabel = new Label("Street Number:");
        middlePanel.add(memberStreetLabel, gc);

        gc.gridx = 1;
        memberStreetTextField = new TextField(20);
        middlePanel.add(memberStreetTextField, gc);

        // City
        gc.gridx = 0;
        gc.gridy = 4;
        Label memberCityLabel = new Label("City:");
        middlePanel.add(memberCityLabel, gc);

        gc.gridx = 1;
        memberCityTextField = new TextField(20);
        middlePanel.add(memberCityTextField, gc);

        // State
        gc.gridx = 0;
        gc.gridy = 5;
        Label memberStateLabel = new Label("State:");
        middlePanel.add(memberStateLabel, gc);

        gc.gridx = 1;
        memberStateTextField = new TextField(20);
        middlePanel.add(memberStateTextField, gc);

        // Zip
        gc.gridx = 0;
        gc.gridy = 6;
        Label memberZipLabel = new Label("Zip Code:");
        middlePanel.add(memberZipLabel, gc);

        gc.gridx = 1;
        memberZipTextField = new TextField(20);
        middlePanel.add(memberZipTextField, gc);

        // Add Member Button
        gc.gridx = 0;
        gc.gridy = 7;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.CENTER;
        Button checkoutButton = new Button("Add Member");
        middlePanel.add(checkoutButton, gc);

        // Add action listener for Add Member button
        checkoutButton.addActionListener(evt -> {
            String firstName = memberFirstNameTextField.getText();
            String lastName = memberLastNameTextField.getText();
            String phoneNumber = memberPhoneTextField.getText();
            String street = memberStreetTextField.getText();
            String city = memberCityTextField.getText();
            String state = memberStateTextField.getText();
            String zip = memberZipTextField.getText();

            try {
                ValidationService.validateMember(firstName, lastName, phoneNumber, street, city, state, zip);

                Address address = new Address(street, city, state, zip);
                String memberId = IDGeneratorUtil.getNextMemberId();
                LibraryMember lm = new LibraryMember(memberId, firstName, lastName, phoneNumber, address);

                da.saveNewMember(lm);
                JOptionPane.showMessageDialog(this, "Member Added! Member ID: " + memberId);
                clearFields();
            } catch (ValidationException ve) {
                // If validation errors occur, display them in a message dialog
                JOptionPane.showMessageDialog(this, String.join("\n", ve.getErrors()), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    private void clearFields() {
        memberFirstNameTextField.setText("");
        memberLastNameTextField.setText("");
        memberPhoneTextField.setText("");
        memberStreetTextField.setText("");
        memberCityTextField.setText("");
        memberStateTextField.setText("");
        memberZipTextField.setText("");
    }
}
