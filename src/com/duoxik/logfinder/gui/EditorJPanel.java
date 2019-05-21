package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.model.LogFile;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditorJPanel extends JPanel implements ActionListener {
    private JTextArea textPane = new JTextArea();

    private Highlighter highlighter = textPane.getHighlighter();
    private DefaultHighlighter.DefaultHighlightPainter hYellow =
            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private DefaultHighlighter.DefaultHighlightPainter hCyan =
            new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);

    private Button prevMatchButton = new Button("<");
    private Button nextMatchButton = new Button(">");

    private Button selectAllButton = new Button("Select All");
    private Button prevPageButton = new Button("<");
    private Button nextPageButton = new Button(">");
    private Button closeButton = new Button("Close");

    private JLabel countPagesLabel = new JLabel();
    private JLabel matchesLabel = new JLabel("Matches: ");
    private JLabel countMatchesLabel = new JLabel();

    private int countPages;
    private int countMatches;
    private int currentPage;
    private int currentMatch;

    private View view;
    private LogFile log;
    private List<LogFile.MatchIndex> matchIndexes;

    public EditorJPanel(View view, LogFile log) {
        this.view = view;
        this.log = log;
        this.matchIndexes = log.getMatchIndexes();
        this.countPages = log.getCountPages();
        this.countMatches = log.getMatchIndexes().size();
        this.currentPage = matchIndexes.get(0).getPage();
        this.currentMatch = 1;

        init();
        goToPage(currentPage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Previous match":
                goToMatch(currentMatch - 1);
                break;
            case "Next match":
                goToMatch(currentMatch + 1);
                break;
            case "Select All":
                textPane.selectAll();
                textPane.requestFocus();
                break;
            case "Previous page":
                goToPage(currentPage - 1);
                break;
            case "Next page":
                goToPage(currentPage + 1);
                break;
            case "Close":
                view.closeTab(log);
        }
    }

    public void focus() {
        LogFile.MatchIndex matchIndex = matchIndexes.get(currentMatch - 1);
        textPane.setCaretPosition(matchIndex.getStart());
    }

    private void init() {
        setLayout(new BorderLayout());

        initTopPanel();
        initTextPane();
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

        prevMatchButton.setEnabled(false);
        if (countMatches == 1) nextMatchButton.setEnabled(false);

        add(BorderLayout.NORTH, topPanel);
    }

    private void initTextPane() {
        textPane.setLineWrap(true);
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

        add(BorderLayout.SOUTH,bottomPanel);
    }

    private void highLightMatches() {
        highlighter.removeAllHighlights();

        try {
            for (LogFile.MatchIndex matchIndex : matchIndexes) {
                if (currentPage == matchIndex.getPage()) {
                    highlighter.addHighlight(matchIndex.getStart(), matchIndex.getEnd(), hYellow);
                }
            }
        } catch (BadLocationException ignored) {}
    }

    private void goToMatch(int matchNumber) {
        LogFile.MatchIndex matchIndex = matchIndexes.get(matchNumber - 1);

        if (matchIndex.getPage() != currentPage) {
            goToPage(matchIndex.getPage());
        }

        currentMatch = matchNumber;

        focus();
        lockButtons();
        updateLabels();
    }

    private void goToPage(int pageNumber) {
        textPane.setText(view.getPage(log, pageNumber));
        currentPage = pageNumber;
        highLightMatches();
        lockButtons();
        updateLabels();
    }

    private void lockButtons() {
        if (currentPage + 1 > countPages)
            nextPageButton.setEnabled(false);
        else
            nextPageButton.setEnabled(true);

        if (currentPage - 1 < 1)
            prevPageButton.setEnabled(false);
        else
            prevPageButton.setEnabled(true);

        if (currentMatch + 1 > countMatches)
            nextMatchButton.setEnabled(false);
        else
            nextMatchButton.setEnabled(true);

        if (currentMatch - 1 < 1)
            prevMatchButton.setEnabled(false);
        else
            prevMatchButton.setEnabled(true);
    }

    private void updateLabels() {
        countPagesLabel.setText(currentPage + "/" + countPages);
        countMatchesLabel.setText(currentMatch + "/" + countMatches);
    }
}
