package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.Controller;
import com.duoxik.logfinder.gui.listeners.FrameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class View extends JFrame implements ActionListener {
    private Controller controller;

    private JTabbedPane leftTabbedPane = new JTabbedPane();
    private JTabbedPane rightTabbedPane = new JTabbedPane();
    private OpenJFrame openJFrame = new OpenJFrame(this);

    private Map<File, EditorJPanel> editors = new HashMap<>();

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public View() {
        super("Logs finder");
        init();
    }

    public void findLogs(String path, String type, String text) {
        controller.findLogs(path, type, text);
    }

    public void readFile(File file) {
        controller.readFile(file);
    }

    public void updateFileStructure(File rootDirectory, List<File> files) {
        leftTabbedPane.addTab(rootDirectory.getName(), new FileTreeJPanel(rootDirectory, files, this));
    }

    public void openNewTab(File file, String text) {
        EditorJPanel editor = editors.get(file);

        if (editor == null) {
            editor = new EditorJPanel(this, file, text);
            rightTabbedPane.addTab(file.getName(), editor);
            editors.put(file, editor);
        }

        rightTabbedPane.setSelectedComponent(editor);
    }

    public void removeTab(File file) {
        EditorJPanel editor = editors.remove(file);

        if (editor != null) {
            rightTabbedPane.remove(editor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open":
                showOpenFrame();
                break;
            case "Exit":
                exit();
                break;
            case "About":
                showAbout();
                break;
        }
    }

    public void showDirectoryNotFound() {
        JOptionPane.showMessageDialog(
                null,
                "Directory is not found. Try again...",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        showOpenFrame();
    }

    public void showFileIsNotDirectory() {
        JOptionPane.showMessageDialog(
                null,
                "The specified file is not a directory. Try again...",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        showOpenFrame();
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Author: Moiseev Vladislav",
                "About...",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void hideOpenFrame() {
        openJFrame.setVisible(false);
    }

    public void showOpenFrame() {
        openJFrame.setVisible(true);
    }

    public void exit() {
        controller.exit();
    }

    private void init() {
        addWindowListener(new FrameListener(this));
        initMenuBar();
        initTabbedPanes();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Helper.initFileMenu(this, menuBar);
        Helper.initHelpMenu(this, menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    private void initTabbedPanes() {
        leftTabbedPane.setPreferredSize(new Dimension(300, 500));
        getContentPane().add(leftTabbedPane, BorderLayout.WEST);

        rightTabbedPane.setPreferredSize(new Dimension(700, 500));
        getContentPane().add(rightTabbedPane, BorderLayout.EAST);
    }
}
