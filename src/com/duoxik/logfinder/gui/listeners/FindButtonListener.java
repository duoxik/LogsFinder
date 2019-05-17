package com.duoxik.logfinder.gui.listeners;

import com.duoxik.logfinder.gui.OpenJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindButtonListener implements ActionListener {

    private OpenJFrame frame;

    public FindButtonListener(OpenJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (frame.validateFields()) {
            frame.setVisible(false);
            frame.findLogs();
        } else {
            frame.setVisible(false);
            JOptionPane.showMessageDialog(
                    null,
                    "Data entered incorrectly. Try again ...",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            frame.setVisible(true);
        }
    }
}
