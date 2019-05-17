package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.gui.listeners.CancelButtonListener;
import com.duoxik.logfinder.gui.listeners.FindButtonListener;

import javax.swing.*;
import java.awt.*;

public class OpenJFrame extends JFrame {

    private JTextField path = new JTextField(21);
    private JTextField type = new JTextField(10);
    private JTextField text = new JTextField(32);

    private final View view;

    public OpenJFrame(View view) {
        super("Open folder");
        this.view = view;
        init();
    }

    public void init() {
        setSize(375, 185);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        path.setText("Enter directory path...");
        type.setText("log");
        text.setText("Enter text...");

        JLabel pathLabel = new JLabel("Directory path:");
        JLabel typeLabel = new JLabel("File type:");
        JLabel textLabel = new JLabel("Text:");

        pathLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        typeLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        textLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));

        JButton find = new JButton("Find");
        find.setPreferredSize(new Dimension(75, 25));
        find.setFont(new Font(Font.SANS_SERIF, 0, 11));
        find.addActionListener(new FindButtonListener(this));

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(75, 25));
        cancel.setFont(new Font(Font.SANS_SERIF, 0, 11));
        cancel.addActionListener(new CancelButtonListener(this));

        final JPanel contentCenter = new JPanel(new GridBagLayout());
        contentCenter.add(pathLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(12, 10, 3, 0), 0, 0));
        contentCenter.add(typeLabel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(12, 6, 3, 0), 0, 0));
        contentCenter.add(path, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(3, 10, 3, 0), 0, 0));
        contentCenter.add(type, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(3, 6, 3, 10), 0, 0));
        contentCenter.add(textLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(3, 10, 3, 0), 0, 0));
        contentCenter.add(text, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(3, 10, 5, 0), 0, 0));
        contentCenter.add(find, new GridBagConstraints(0, 4, 2, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 205, 12, 0), 0, 0));
        contentCenter.add(cancel, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(5, 0, 12, 10), 0, 0));

        getContentPane().add(contentCenter, BorderLayout.CENTER);
        contentCenter.setBackground(new Color(239, 235, 231));
        pack();
    }

    public boolean validateFields() {
        return     !path.getText().trim().isEmpty()
                && !type.getText().trim().isEmpty()
                && !text.getText().trim().isEmpty();
    }

    public void findLogs() {
        view.findLogs(path.getText().trim(),
                      type.getText().trim(),
                      text.getText().trim());
    }
}
