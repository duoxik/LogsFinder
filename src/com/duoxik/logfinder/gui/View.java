package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.controller.Controller;
import com.duoxik.logfinder.gui.listeners.FrameListener;
import com.duoxik.logfinder.model.LogFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class View extends JFrame implements ActionListener {
    private Controller controller;

    private FileTreeJPanel treePanel = new FileTreeJPanel(this);
    private JTabbedPane tabbedPane = new JTabbedPane();
    private OpenJFrame openFrame = new OpenJFrame(this);

    private Map<File, EditorJPanel> tabs = new HashMap<>();

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

    public void openFile(File file) {
        EditorJPanel editor = tabs.get(file);

        if (editor == null) {
            controller.openFile(file);
        } else {
            tabbedPane.setSelectedComponent(editor);
        }
    }

    public String getPage(File file, int pageNumber) {
        return controller.getPage(file, pageNumber);
    }

    public void updateFileStructure(File rootDirectory, List<LogFile> files) {
        treePanel.setFileTree(rootDirectory, files);
    }

    public void openTab(File file, int countPages, String firstPage) {
        EditorJPanel editor = new EditorJPanel(this, file, firstPage, countPages);
        tabbedPane.addTab(file.getName(), editor);
        tabs.put(file, editor);
        tabbedPane.setSelectedComponent(editor);
    }

    public void closeTab(File file) {
        EditorJPanel editor = tabs.remove(file);

        if (editor != null) {
            tabbedPane.remove(editor);
        }
    }

    public void lockFindButton() {
        openFrame.lockFindButton();
    }

    public void unlockFindButton() {
        openFrame.unlockFindButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open":
                openFrame.setVisible(true);
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
        openFrame.setVisible(true);
    }

    public void showFileIsNotDirectory() {
        JOptionPane.showMessageDialog(
                null,
                "The specified file is not a directory. Try again...",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        openFrame.setVisible(true);
    }

    public void showFilesNotFound() {
        JOptionPane.showMessageDialog(
                null,
                "Files not found. Try again...",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
        openFrame.setVisible(true);
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Author: Moiseev Vladislav",
                "About...",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void exit() {
        controller.exit();
    }

    private void init() {
        setResizable(false);
        addWindowListener(new FrameListener(this));

        initMenuBar();
        initTabbedPane();
        initTreePanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        GuiHelper.initFileMenu(this, menuBar);
        GuiHelper.initHelpMenu(this, menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    private void initTreePanel() {
        treePanel.setPreferredSize(new Dimension(300, 500));
        getContentPane().add(treePanel, BorderLayout.WEST);
    }

    private void initTabbedPane() {
        tabbedPane.setPreferredSize(new Dimension(700, 500));
        getContentPane().add(tabbedPane, BorderLayout.EAST);
    }
}
