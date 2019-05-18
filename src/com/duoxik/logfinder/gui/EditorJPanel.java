package com.duoxik.logfinder.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditorJPanel extends JPanel {

    private JTextPane plainTextPane = new JTextPane();
    private JScrollPane plainScrollPane = new JScrollPane(plainTextPane);

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
        add(BorderLayout.CENTER, plainScrollPane);
    }
}
