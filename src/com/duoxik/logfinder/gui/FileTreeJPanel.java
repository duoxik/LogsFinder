package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.gui.listeners.FileTreeListener;
import com.duoxik.logfinder.model.LogFile;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeJPanel extends JPanel {
    private File directory = null;
    private List<LogFile> files  = null;

    private View view;

    public FileTreeJPanel(View view) {
        this.view = view;
        setLayout(new BorderLayout());
    }

    public void readFile(File file) {
        view.openFile(file);
    }

    public File getDirectory() {
        return directory;
    }

    public void setFileTree(File directory, List<LogFile> files) {

        this.directory = directory;
        this.files = files;

        JTree tree = createTree(directory, files);
        tree.addTreeSelectionListener(new FileTreeListener(this));
        JScrollPane scrollPane = new JScrollPane(tree);

        removeAll();
        add(BorderLayout.CENTER, scrollPane);
        updateUI();
    }

    private JTree createTree(File directory, List<LogFile> files) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(directory.getName());
        Path rootPath = directory.toPath();

        for (LogFile file : files) {
            Path filePath = file.getPath();
            Path relativePath = rootPath.relativize(filePath);
            addNodes(root, relativePath);
        }

        return new JTree(root);
    }

    private void addNodes(DefaultMutableTreeNode parent, Path path) {
        String[] parts = path.toString().split("/");
        for (String part : parts) {

            DefaultMutableTreeNode node = null;

            Enumeration<DefaultMutableTreeNode> enumeration = parent.children();
            while (enumeration.hasMoreElements()) {
                DefaultMutableTreeNode child = enumeration.nextElement();
                if (child.getUserObject().equals(part)) {
                    node = child;
                    break;
                }
            }

            if (node == null) {
                node = new DefaultMutableTreeNode(part);
                parent.add(node);
            }

            parent = node;
        }
    }
}
