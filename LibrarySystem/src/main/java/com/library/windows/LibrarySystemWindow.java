package com.library.windows;

import com.library.interfaces.ControllerInterface;
import com.library.interfaces.LibWindow;
import com.library.services.SystemController;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public abstract class LibrarySystemWindow extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    protected boolean isInitialized = false;
    protected JPanel mainPanel;
    protected JPanel topPanel;
    protected JPanel middlePanel;
    protected JPanel lowerPanel;

    protected String jButtonText = "<  Back to Main";

    public abstract void defineTopPanel();

    public abstract void defineMiddlePanel();

    public void init() {
        if (isInitialized) return;
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

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton(jButtonText);
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
    }

    protected void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
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
