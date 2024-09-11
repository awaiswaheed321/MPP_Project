package com.library.windows;

import com.library.services.SystemController;
import com.library.interfaces.ControllerInterface;
import com.library.utils.Util;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class AllMemberIdsWindow extends LibrarySystemWindow {
    public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
    ControllerInterface ci = new SystemController();
    private TextArea textArea;

    private AllMemberIdsWindow() {
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("All Member IDs");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8, 20);
        middlePanel.add(textArea);
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
    }

    public void setData(String data) {
        textArea.setText(data);
    }
}