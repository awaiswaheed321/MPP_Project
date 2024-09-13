package com.library.windows;

import com.library.classes.LibraryMember;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.LibWindow;
import com.library.services.SystemController;
import com.library.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.List;

public class AllMembersWindow extends JFrame implements LibWindow {
    @Serial
    private static final long serialVersionUID = 6696979618083051462L;
    public static final AllMembersWindow INSTANCE = new AllMembersWindow();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTable membersTable;
    private List<LibraryMember> membersList;

    // Singleton class
    private AllMembersWindow() {
    }

    public void init() {
        if (!isInitialized) {
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
    }

    public void defineTopPanel() {
        if (topPanel == null) {
            topPanel = new JPanel();
            JLabel allMembersLabel = new JLabel("All Members and Details");
            Util.adjustLabelFont(allMembersLabel, Util.DARK_BLUE, true);
            topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            topPanel.add(allMembersLabel);
        }
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create the table and configure it
        membersTable = new JTable();
        membersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        membersTable.setFillsViewportHeight(true);

        // Populate the table with the initial member data
        DefaultTableModel tableModel = getMemberTableModel();
        membersTable.setModel(tableModel);

        middlePanel.add(new JScrollPane(membersTable), BorderLayout.CENTER);
    }

    public void defineLowerPanel() {
        if (lowerPanel == null) {
            JButton backToMainButn = new JButton("<= Back to Main");
            backToMainButn.addActionListener(new BackToMainListener());

            lowerPanel = new JPanel();
            lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            lowerPanel.add(backToMainButn);
        }
    }

    static class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    // Method to set member data in the JTable
    public void setData(List<LibraryMember> members) {
        membersList = members;
        if (membersTable != null) {
            DefaultTableModel tableModel = getMemberTableModel();
            membersTable.setModel(tableModel);
            membersTable.repaint();
        }
    }

    // Method to create a Table Model for the member data
    private DefaultTableModel getMemberTableModel() {
        String[] columnNames = {"Member ID", "First Name", "Last Name", "Telephone"};
        String[][] memberData = new String[membersList.size()][4];

        for (int i = 0; i < membersList.size(); i++) {
            LibraryMember member = membersList.get(i);
            memberData[i] = new String[]{
                    member.getMemberId(),         // Member ID
                    member.getFirstName(),        // First Name
                    member.getLastName(),         // Last Name
                    member.getTelephone()         // Telephone
            };
        }

        return new DefaultTableModel(memberData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
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

