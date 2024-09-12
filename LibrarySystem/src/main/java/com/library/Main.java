package com.library;

import java.awt.*;

import com.library.utils.Util;
import com.library.windows.LoginWindow;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        });
    }

    public static void centerFrameOnDesktop(Component f) {
        Util.centerFrameOnDesktop(f);
    }
}
