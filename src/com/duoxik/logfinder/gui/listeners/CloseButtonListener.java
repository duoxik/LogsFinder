package com.duoxik.logfinder.gui.listeners;

import com.duoxik.logfinder.gui.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CloseButtonListener implements ActionListener {

    private View view;
    private File file;

    public CloseButtonListener(View view, File file) {
        this.view = view;
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.closeTab(file);
    }
}
