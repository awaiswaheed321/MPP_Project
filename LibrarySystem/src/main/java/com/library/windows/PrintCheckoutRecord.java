package com.library.windows;

import com.library.classes.CheckoutEntry;
import com.library.classes.LibraryMember;
import com.library.exceptions.LibraryMemberNotFoundException;
import com.library.interfaces.ControllerInterface;
import com.library.services.MemberService;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class PrintCheckoutRecord extends LibrarySystemWindow {
    public static final PrintCheckoutRecord INSTANCE = new PrintCheckoutRecord();
    ControllerInterface ci = new SystemController();
    MemberService ms = new MemberService();

    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField username;
    //    private JTextField password;
    private JLabel label;
    private JButton searchButton;

    /* This class is a singleton */
    private PrintCheckoutRecord() {
    }

    @Override
    public void init() {
        if (isInitialized) return;
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);

    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);

    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Print CheckOut");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLeftTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        username = new JTextField(10);
        label = new JLabel("Username");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(username);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.NORTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);

        searchButton = new JButton("Check Member");
        addSearchButtonListener(searchButton);
        leftTextPanel.add(searchButton);
    }

    private void defineRightTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.NORTH);
    }

    private void addSearchButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String memberId = username.getText();
            if (ci.isValidMember(memberId)) {
                try {
                    LibraryMember lm = ms.getMember(memberId);
                    List<CheckoutEntry> lmCheckout = lm.getCheckouts();
                    if (lmCheckout == null || lmCheckout.size() == 0) {
                        JOptionPane.showMessageDialog(this, "No Checkout Records Exits");
                        return;
                    }
                    for (CheckoutEntry ce : lmCheckout) {
                        System.out.println(ce.toString());
                    }
                    LibrarySystem.hideAllWindows();
                    CheckoutRecordWindow.INSTANCE.setCheckoutEntries(lmCheckout);
                    CheckoutRecordWindow.INSTANCE.init();
                    CheckoutRecordWindow.INSTANCE.pack();
                    CheckoutRecordWindow.INSTANCE.setVisible(true);
                } catch (LibraryMemberNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "The Member ID was not found!");
                    clearInputs();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member Does not Exits");
                clearInputs();
            }
        });
    }

    private void clearInputs() {
        username.setText("");
    }

    private void showLoginError(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
