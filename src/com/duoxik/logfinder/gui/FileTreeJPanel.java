package com.duoxik.logfinder.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeJPanel extends JPanel {
    public FileTreeJPanel(File dir, List<File> files) {
        setLayout(new BorderLayout());
        JTree tree = createFileTree(dir, files);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
                        .getPath().getLastPathComponent();
                System.out.println(e.getPath());
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(tree);
        add(BorderLayout.CENTER, scrollPane);
        setPreferredSize(new Dimension(300, 500));
    }


    private JTree createFileTree(File dir, List<File> files) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(dir.getName());
        Path rootPath = dir.toPath();

        for (File file : files) {
            Path filePath = file.toPath();
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
