package com.duoxik.logfinder.gui.listeners;

import com.duoxik.logfinder.gui.FileTreeJPanel;
import com.duoxik.logfinder.gui.GuiHelper;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.io.File;

public class FileTreeListener implements TreeSelectionListener {
    private FileTreeJPanel fileTree;

    public FileTreeListener(FileTreeJPanel fileTree) {
        this.fileTree = fileTree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath treePath = e.getPath();
        String root = fileTree.getDirectory().getParent();
        File selectedFile = GuiHelper.transformToFilePath(root, treePath);
        if (!selectedFile.isDirectory()) {
            fileTree.readFile(selectedFile);
        }
    }
}
