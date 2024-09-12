package com.library.windows;

import com.library.classes.Address;
import com.library.classes.LibraryMember;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.DataAccess;
import com.library.services.DataAccessFacade;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class AddMemberWindow extends LibrarySystemWindow {
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();

    private Label memberIdLabel;
    private TextField memberIdTextField;
    private Label memberFirstNameLabel;
    private TextField memberFirstNameTextField;
    private Label memberLastNameLabel;
    private TextField memberLastNameTextField;
    private Label memberPhoneNameLabel;
    private TextField memberPhoneTextField;

    // address
    private Label memberStreetLabel;
    private TextField memberStreetTextField;
    private Label memberCityLabel;
    private TextField memberCityTextField;
    private Label memberStateLabel;
    private TextField memberStateTextField;
    private Label memberZipLabel;
    private TextField memberZipTextField;

    private Button checkoutButton;

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
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);

        // ID
        memberIdLabel = new Label("Member ID:");
        middlePanel.add(memberIdLabel);

        memberIdTextField = new TextField(20);
        middlePanel.add(memberIdTextField);

        //TODO: remove after testing
        //memberIdTextField.setText("1001");

        //Firstname
        memberFirstNameLabel = new Label("First Name:");
        middlePanel.add(memberFirstNameLabel);

        memberFirstNameTextField = new TextField(20);
        middlePanel.add(memberFirstNameTextField);

        //Last Name
        memberLastNameLabel = new Label("Last Name:");
        middlePanel.add(memberLastNameLabel);

        memberLastNameTextField = new TextField(20);
        middlePanel.add(memberLastNameTextField);

        //phone
        memberPhoneNameLabel = new Label("Phone Number:");
        middlePanel.add(memberPhoneNameLabel);

        memberPhoneTextField = new TextField(20);
        middlePanel.add(memberPhoneTextField);

        //street
        memberStreetLabel = new Label("street nember:");
        middlePanel.add(memberStreetLabel);

        memberStreetTextField = new TextField(20);
        middlePanel.add(memberStreetTextField);

        //city
        memberCityLabel = new Label("city:");
        middlePanel.add(memberCityLabel);

        memberCityTextField = new TextField(20);
        middlePanel.add(memberCityTextField);

        //state
        memberStateLabel = new Label("state:");
        middlePanel.add(memberStateLabel);

        memberStateTextField = new TextField(20);
        middlePanel.add(memberStateTextField);

        //zip
        memberZipLabel = new Label("Zip code:");
        middlePanel.add(memberZipLabel);

        memberZipTextField = new TextField(20);
        middlePanel.add(memberZipTextField);

        checkoutButton = new Button("Add Member");
        checkoutButton.addActionListener(evt -> {
            String memberId = memberIdTextField.getText();
            String firstName = memberFirstNameTextField.getText();
            String lastName = memberLastNameTextField.getText();
            String phoneNumber = memberPhoneTextField.getText();

            String street = memberStreetTextField.getText();
            String city = memberCityTextField.getText();
            String state = memberStateTextField.getText();
            String zip = memberZipTextField.getText();

            System.out.println("memberId : " + memberId.length());

            if (memberId.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || phoneNumber.length() == 0 || street.length() == 0 || city.length() == 0 || state.length() == 0 || zip.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter data in ALL fields");
                return;
            }

            if (ci.isValidMember(memberId)) {
                JOptionPane.showMessageDialog(this, "The Member ID already exists!");
                return;
            }
            Address address = new Address(street, city, state, zip);
            LibraryMember lm = new LibraryMember(memberId, firstName, lastName, phoneNumber, address);

            System.out.println(lm.toString());

            da.saveNewMember(lm);
            JOptionPane.showMessageDialog(this, "Member Added!");
            memberStreetTextField.setText("");
            memberCityTextField.setText("");
            memberStateTextField.setText("");
            memberZipTextField.setText("");
            memberIdTextField.setText("");
            memberFirstNameTextField.setText("");
            memberLastNameTextField.setText("");
            memberPhoneTextField.setText("");
        });
        middlePanel.add(checkoutButton);
    }
}
