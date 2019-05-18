package com.duoxik.logfinder.gui;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {
    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, String text, Action action) {
        JMenuItem menuItem = addMenuItem(parent, action);
        menuItem.setText(text);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, Action action) {
        JMenuItem menuItem = new JMenuItem(action);
        parent.add(menuItem);
        return menuItem;
    }

    public static void initFileMenu(View view, JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        addMenuItem(fileMenu, "Open", view);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", view);
    }

    public static void initHelpMenu(View view, JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        addMenuItem(helpMenu, "About", view);
    }

    public static File transformToFilePath(String directory, TreePath tp) {
        String path = tp.toString().replaceAll("\\]| |\\[|", "").replaceAll(",", File.separator);
        Path resultPath = Paths.get(directory, path);
        return resultPath.toFile();
    }
}
