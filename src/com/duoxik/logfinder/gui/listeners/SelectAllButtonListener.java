package com.duoxik.logfinder.gui.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectAllButtonListener implements ActionListener {

    private JTextPane textPane;

    public SelectAllButtonListener(JTextPane textPane) {
        this.textPane = textPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textPane.selectAll();
        textPane.requestFocus();
    }
}
