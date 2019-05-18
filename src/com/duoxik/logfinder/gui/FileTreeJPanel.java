package com.duoxik.logfinder.gui;

import com.duoxik.logfinder.gui.listeners.FileTreeListener;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeJPanel extends JPanel {
    private File directory;
    private List<File> files;
    private View view;
    private JTree tree;

    public FileTreeJPanel(File directory, List<File> files, View view) {
        this.directory = directory;
        this.files = files;
        this.view = view;
        init();
    }

    public void readFile(File file) {
        view.readFile(file);
    }

    public File getDirectory() {
        return directory;
    }

    private void init() {
        setLayout(new BorderLayout());
        initTree();
        tree.addTreeSelectionListener(new FileTreeListener(this));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(tree);
        add(BorderLayout.CENTER, scrollPane);
    }

    private void initTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(directory.getName());
        Path rootPath = directory.toPath();

        for (File file : files) {
            Path filePath = file.toPath();
            Path relativePath = rootPath.relativize(filePath);
            addNodes(root, relativePath);
        }

        tree = new JTree(root);
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
