package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.gui.listeners.CloseButtonListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditorJPanel extends JPanel {

    private JTextPane plainTextPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(plainTextPane);

    private View view;
    private File file;

    public EditorJPanel(View view, File file, String text) {
        this.view = view;
        this.file = file;
        init(text);
    }

    private void init(String text) {
        //plainTextPane.setContentType("text/html");
        plainTextPane.setText(text);
        plainTextPane.setEditable(false);
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scrollPane);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        Button button1 = new Button("left");
        buttonsPanel.add(BorderLayout.WEST, button1);

        Button button2 = new Button("center");
        buttonsPanel.add(BorderLayout.CENTER, button2);

        Button closeButton = new Button("Close");
        buttonsPanel.add(BorderLayout.EAST, closeButton);
        closeButton.addActionListener(new CloseButtonListener(view, file));

        add(BorderLayout.SOUTH,buttonsPanel);
    }
}
