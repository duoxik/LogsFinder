package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.Controller;
import com.duoxik.logfinder.gui.listeners.FrameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private OpenJFrame openJFrame = new OpenJFrame(this);

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor() {

        hideOpenFrame();
        htmlTextPane.setContentType("text/html");
        JScrollPane htmlScrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML", htmlScrollPane);
        JScrollPane plainScrollPane = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", plainScrollPane);
        tabbedPane.setPreferredSize(new Dimension(500, 500));
        //tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void hideOpenFrame() {
        openJFrame.setVisible(false);
    }

    public void showOpenFrame() {
        openJFrame.setVisible(true);
    }

    public void findLogs(String path, String type, String text) {
        controller.findLogs(path, type, text);
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
}
