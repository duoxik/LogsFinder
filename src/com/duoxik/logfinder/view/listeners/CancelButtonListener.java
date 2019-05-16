package com.duoxik.logfinder.view.listeners;

import com.duoxik.logfinder.view.OpenJFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelButtonListener implements ActionListener {

    private OpenJFrame frame;

    public CancelButtonListener(OpenJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
    }
}
