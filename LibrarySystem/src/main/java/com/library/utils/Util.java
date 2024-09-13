package com.library.utils;

import com.library.classes.Book;
import com.library.classes.LibraryMember;
import com.library.interfaces.DataAccess;
import com.library.services.DataAccessFacade;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Util {
    public static final Color DARK_BLUE = Color.BLUE.darker();

    public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
    }

    public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
        Font f;
        if (bigger) {
            f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() + 2));
        } else {
            f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() - 2));
        }
        label.setFont(f);
        label.setForeground(color);
    }

    /**
     * Sorts a list of numeric strings in natural number order
     */
    public static List<String> numericSort(List<String> list) {
        list.sort(new NumericSortComparator());
        return list;
    }

    static class NumericSortComparator implements Comparator<String> {
        @Override
        public int compare(String s, String t) {
            if (!isNumeric(s) || !isNumeric(t))
                throw new IllegalArgumentException("Input list has non-numeric characters");
            int sInt = Integer.parseInt(s);
            int tInt = Integer.parseInt(t);
            return Integer.compare(sInt, tInt);
        }
    }

    public static boolean isNumeric(String s) {
        if (s == null) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }
}
