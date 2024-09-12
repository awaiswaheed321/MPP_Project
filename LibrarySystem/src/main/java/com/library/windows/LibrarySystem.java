package com.library.windows;

import com.library.Main;
import com.library.classes.Author;
import com.library.classes.Book;
import com.library.enums.Auth;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.LibWindow;
import com.library.services.SystemController;
import com.library.utils.DataUtils;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@SuppressWarnings("serial")
public class LibrarySystem extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem allBookIds, addMembers, checkoutBook, addBookCopy, logout, printCheckoutRecord, allAuthors, allAddresses;
    String pathToImage;
    private boolean isInitialized = false;

    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            ResearchBookWindow.INSTANCE,
            AddBookCopyWindow.INSTANCE,
            AddMemberWindow.INSTANCE,
            CheckoutBookWindow.INSTANCE,
            CheckoutRecordWindow.INSTANCE,
            PrintCheckoutRecord.INSTANCE
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

        allBookIds = new JMenuItem("Book Collection");
        allBookIds.addActionListener(new AllBookIdsListener());
        options.add(allBookIds);

        allAuthors = new JMenuItem("Authors");
        allAuthors.addActionListener(new AuthorsListener());
        options.add(allAuthors);

        if (SystemController.currentAuth != Auth.LIBRARIAN) {
            addBookCopy = new JMenuItem("Add Book Copy");
            addBookCopy.addActionListener(new AddBookCopyListener());
            options.add(addBookCopy);

            addMembers = new JMenuItem("Add Members");
            addMembers.addActionListener(new AddMemberListener());
            options.add(addMembers);


        }

        if (SystemController.currentAuth != Auth.ADMIN) {
            checkoutBook = new JMenuItem("Checkout book");
            checkoutBook.addActionListener(new CheckoutBookListener());
            options.add(checkoutBook);

            printCheckoutRecord = new JMenuItem("Print Checkout Record");
            printCheckoutRecord.addActionListener(new printCheckoutRecords());
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
            AllBooksWindow.INSTANCE.init();
            List<Book> books = ci.getAllBooks();
            books.sort(Comparator.comparing(Book::getIsbn));
            AllBooksWindow.INSTANCE.setData(books);
            AllBooksWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllBooksWindow.INSTANCE);
            AllBooksWindow.INSTANCE.setVisible(true);
        }
    }

    class AuthorsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllAuthorsWindow.INSTANCE.init();
            List<Author> authors = ci.getAllAuthors();
            authors.sort(Comparator.comparing(Author::getLastName));
            AllAuthorsWindow.INSTANCE.setData(authors);
            AllAuthorsWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllAuthorsWindow.INSTANCE);
            AllAuthorsWindow.INSTANCE.setVisible(true);
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
            AllBooksWindow.INSTANCE.init();

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

    class AddBookCopyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            ResearchBookWindow.INSTANCE.init();
            ResearchBookWindow.INSTANCE.pack();
            ResearchBookWindow.INSTANCE.setSize((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5));
            Util.centerFrameOnDesktop(ResearchBookWindow.INSTANCE);
            ResearchBookWindow.INSTANCE.setVisible(true);
        }
    }

    class AddMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AddMemberWindow.INSTANCE.init();
            AddMemberWindow.INSTANCE.pack();
            AddMemberWindow.INSTANCE.setSize(670, 400);
            AddMemberWindow.INSTANCE.setVisible(true);
            Main.centerFrameOnDesktop(AddMemberWindow.INSTANCE);
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

    class printCheckoutRecords implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            PrintCheckoutRecord.INSTANCE.init();
            PrintCheckoutRecord.INSTANCE.pack();
            PrintCheckoutRecord.INSTANCE.setSize(400, 200);
            PrintCheckoutRecord.INSTANCE.setVisible(true);
            Main.centerFrameOnDesktop(PrintCheckoutRecord.INSTANCE);
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
