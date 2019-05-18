package com.duoxik.logfinder.gui;

import javax.swing.*;
import java.awt.*;

public class EditorJPanel extends JPanel {

    private JEditorPane plainTextPane = new JEditorPane();
    private JScrollPane plainScrollPane = new JScrollPane(plainTextPane);

    private View view;

    public EditorJPanel(String text, View view) {
        this.view = view;
        plainTextPane.setText(text);
        plainTextPane.setEditable(false);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, plainScrollPane);
    }
}
