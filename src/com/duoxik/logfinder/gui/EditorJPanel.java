package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.gui.listeners.CloseButtonListener;
import com.duoxik.logfinder.gui.listeners.SelectAllButtonListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditorJPanel extends JPanel {

    private JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(textPane);

    private View view;
    private File file;

    public EditorJPanel(View view, File file, String text) {
        this.view = view;
        this.file = file;
        init(text);
    }

    private void init(String text) {
        //textPane.setContentType("text/html");
        textPane.setText(text);
        textPane.setEditable(false);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scrollPane);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());

        Button selectAllButton = new Button("Select All");
        buttonsPanel.add(BorderLayout.WEST, selectAllButton);
        selectAllButton.addActionListener(new SelectAllButtonListener(textPane));

        Button button2 = new Button("center");
        buttonsPanel.add(BorderLayout.CENTER, button2);

        Button closeButton = new Button("Close");
        buttonsPanel.add(BorderLayout.EAST, closeButton);
        closeButton.addActionListener(new CloseButtonListener(view, file));

        add(BorderLayout.SOUTH,buttonsPanel);
    }
}
