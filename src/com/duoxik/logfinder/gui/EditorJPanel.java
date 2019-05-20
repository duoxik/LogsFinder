package com.duoxik.logfinder.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EditorJPanel extends JPanel implements ActionListener {
    private JTextArea textPane = new JTextArea();

    private Button prevMatchButton = new Button("<");
    private Button nextMatchButton = new Button(">");

    private Button selectAllButton = new Button("Select All");
    private Button prevPageButton = new Button("<");
    private Button nextPageButton = new Button(">");
    private Button closeButton = new Button("Close");

    private JLabel countPagesLabel = new JLabel();
    private JLabel matchesLabel = new JLabel("Matches: ");
    private JLabel countMatchesLabel = new JLabel("1/10");

    private int countPages;
    private int currentPage = 1;

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

        initTopPanel();
        initTextPane(text);
        initBottomPanel();
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel matchesPanel = new JPanel();
        matchesPanel.add(matchesLabel);
        matchesPanel.add(prevMatchButton);
        matchesPanel.add(countMatchesLabel);
        matchesPanel.add(nextMatchButton);

        topPanel.add(BorderLayout.EAST, matchesPanel);

        prevMatchButton.addActionListener(this);
        nextMatchButton.addActionListener(this);

        prevMatchButton.setActionCommand("Previous match");
        nextMatchButton.setActionCommand("Next match");

        add(BorderLayout.NORTH, topPanel);
    }

    private void initTextPane(String text) {
        textPane.setText(text);
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textPane);
        add(BorderLayout.CENTER, scrollPane);
    }

    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel pagesPanel = new JPanel();
        pagesPanel.add(prevPageButton);
        pagesPanel.add(countPagesLabel);
        pagesPanel.add(nextPageButton);

        bottomPanel.add(BorderLayout.WEST, selectAllButton);
        bottomPanel.add(BorderLayout.CENTER, pagesPanel);
        bottomPanel.add(BorderLayout.EAST, closeButton);

        selectAllButton.addActionListener(this);
        prevPageButton.addActionListener(this);
        nextPageButton.addActionListener(this);
        closeButton.addActionListener(this);

        prevPageButton.setActionCommand("Previous page");
        nextPageButton.setActionCommand("Next page");

        countPagesLabel.setText("1/" + countPages);

        prevPageButton.setEnabled(false);
        if (countPages == 1) nextPageButton.setEnabled(false);

        add(BorderLayout.SOUTH,bottomPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Previous match":
                //TODO need to do previous match button action
                System.out.println("Previous match");
                break;
            case "Next match":
                //TODO need to do next match button action
                System.out.println("Next match");
                break;
            case "Select All":
                textPane.selectAll();
                textPane.requestFocus();
                break;
            case "Previous page":
                previousPage();
                break;
            case "Next page":
                nextPage();
                break;
            case "Close":
                view.closeTab(file);
        }
    }

    private void nextPage() {
        textPane.setText(view.getPage(file, ++currentPage));
        countPagesLabel.setText(currentPage + "/" + countPages);
        prevPageButton.setEnabled(true);
        if (currentPage + 1 > countPages) nextPageButton.setEnabled(false);
    }

    private void previousPage() {
        textPane.setText(view.getPage(file, --currentPage));
        countPagesLabel.setText(currentPage + "/" + countPages);
        nextPageButton.setEnabled(true);
        if (currentPage - 1 < 1) prevPageButton.setEnabled(false);
    }
}
