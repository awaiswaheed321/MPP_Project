package com.library.utils;

public class DataUtils {
    private static final String folderSeparator = System.getProperty("file.separator")
            + System.getProperty("file.separator");

    public static String buildPath(String... paths) {
        String path = "";
        for (String pathString : paths) {
            path += pathString + folderSeparator;
        }
        return path;
    }
}
