package com.library.windows;

import com.library.services.SystemController;
import com.library.enums.Auth;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.LibWindow;
import com.library.utils.DataUtils;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collections;
import java.util.List;


@SuppressWarnings("serial")
public class LibrarySystem extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addMembers, checkoutBook, addBookCopy, logout, printCheckoutRecord;
    String pathToImage;
    private boolean isInitialized = false;

    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            CheckoutBookWindow.INSTANCE,
            CheckoutRecordWindow.INSTANCE
    };

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();
        createMenus();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        pathToImage = DataUtils.buildPath(System.getProperty("user.dir"), "LibrarySystem", "src", "main", "resources", "images/library.jpg");
    }

    private void insertSplashImage() {
        ImageIcon imageIcon = new ImageIcon(pathToImage); // Load the image

        JLabel imageLabel = new JLabel();
        mainPanel.add(imageLabel);

// Add a ComponentListener to wait for the panel to be resized and laid out
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = mainPanel.getWidth();
                int panelHeight = mainPanel.getHeight();

                if (panelWidth > 0 && panelHeight > 0) {
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    imageLabel.setIcon(scaledIcon);
                }
            }
        });
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AllBookIdsListener());
        options.add(allBookIds);

        if (SystemController.currentAuth != Auth.LIBRARIAN) {
            addBookCopy = new JMenuItem("Add Book Copy");
            options.add(addBookCopy);

            addMembers = new JMenuItem("Add Members");
            options.add(addMembers);
        }

        if (SystemController.currentAuth != Auth.ADMIN) {
            checkoutBook = new JMenuItem("Checkout book");
            checkoutBook.addActionListener(new CheckoutBookListener());
            options.add(checkoutBook);

            printCheckoutRecord = new JMenuItem("Print Checkout Record");
            options.add(printCheckoutRecord);
        }

        logout = new JMenuItem("Logout");
        logout.addActionListener(new LogoutListener());
        options.add(logout);
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }
    }

    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s).append("\n");
            }
            System.out.println(sb.toString());
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            //AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);
        }
    }

    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();
            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);

            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s).append("\n");
            }
            System.out.println(sb.toString());
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);
        }
    }

    class CheckoutBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            CheckoutBookWindow.INSTANCE.init();
            CheckoutBookWindow.INSTANCE.pack();
            CheckoutBookWindow.INSTANCE.setVisible(true);
        }
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SystemController.currentAuth = null;
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }
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
