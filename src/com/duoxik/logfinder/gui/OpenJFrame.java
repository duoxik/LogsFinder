package com.duoxik.logfinder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenJFrame extends JFrame implements ActionListener {
    private JTextField path = new JTextField(21);
    private JTextField type = new JTextField(10);
    private JTextField text = new JTextField(32);

    private JLabel pathLabel = new JLabel("Directory path:");
    private JLabel typeLabel = new JLabel("File type:");
    private JLabel textLabel = new JLabel("Text:");

    private JButton find = new JButton("Find");
    private JButton cancel = new JButton("Cancel");

    private final View view;

    public OpenJFrame(View view) {
        super("Open folder");
        this.view = view;
        init();
    }

    public void lockFindButton() {
        find.setEnabled(false);
    }

    public void unlockFindButton() {
        find.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Find":
                String path = this.path.getText().trim();
                String type = this.type.getText().trim();
                String text = this.text.getText().trim();

                if (!path.isEmpty() && !type.isEmpty() && !text.isEmpty()) {
                    setVisible(false);
                    view.findLogs(path, type, text);
                } else {
                    setVisible(false);
                    JOptionPane.showMessageDialog(
                            null,
                            "Data entered incorrectly. Try again ...",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    setVisible(true);
                }
                break;

            case "Cancel":
                setVisible(false);
        }
    }

    private void init() {
        setSize(375, 185);
        setLocationRelativeTo(null);
        setResizable(false);

        initTextFields();
        initLabels();
        initButtons();
        initGrid();

        pack();
    }

    private void initTextFields() {
        path.setText("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files");
        type.setText("log");
        text.setText("client");
//        path.setText("Enter directory path...");
//        type.setText("log");
//        text.setText("Enter text...");
    }

    private void initLabels() {
        pathLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        typeLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        textLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
    }

    private void initButtons() {
        find.setPreferredSize(new Dimension(75, 25));
        find.setFont(new Font(Font.SANS_SERIF, 0, 11));
        find.addActionListener(this);

        cancel.setPreferredSize(new Dimension(75, 25));
        cancel.setFont(new Font(Font.SANS_SERIF, 0, 11));
        cancel.addActionListener(this);
    }

    private void initGrid() {
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
    }
}
