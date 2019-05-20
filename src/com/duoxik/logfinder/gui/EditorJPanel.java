package com.duoxik.logfinder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EditorJPanel extends JPanel implements ActionListener {
    private JTextArea textPane = new JTextArea();

    private Button selectAllButton = new Button("Select All");
    private Button prevButton = new Button("Previous");
    private Button nextButton = new Button("Next");
    private Button closeButton = new Button("Close");

    private int countPages;
    private int currentPage = 0;

    private View view;
    private File file;

    public EditorJPanel(View view, File file, String firstPage, int countPages) {
        this.view = view;
        this.file = file;
        this.countPages = countPages;
        init(firstPage);
    }

    private void init(String text) {
        setLayout(new BorderLayout());
        initTextPane(text);
        initButtonsPanel();
    }

    private void initTextPane(String text) {
        textPane.setText(text);
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textPane);
        add(BorderLayout.CENTER, scrollPane);
    }

    private void initButtonsPanel() {
        JPanel buttonsPanel = new JPanel();

        buttonsPanel.add(selectAllButton);
        buttonsPanel.add(prevButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(closeButton);

        selectAllButton.addActionListener(this);
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        closeButton.addActionListener(this);

        prevButton.setEnabled(false);
        if (countPages == 1) nextButton.setEnabled(false);

        add(BorderLayout.SOUTH,buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Select All":
                textPane.selectAll();
                textPane.requestFocus();
                break;
            case "Previous":
                textPane.setText(view.getPage(file, --currentPage));
                nextButton.setEnabled(true);
                if (currentPage - 1 < 1) prevButton.setEnabled(false);
                break;
            case "Next":
                textPane.setText(view.getPage(file, ++currentPage));
                prevButton.setEnabled(true);
                if (currentPage + 1 > countPages) nextButton.setEnabled(false);
                break;
            case "Close":
                view.closeTab(file);
        }
    }
}
