package com.duoxik.logfinder.controllers;

import com.duoxik.logfinder.exceptions.DirectoryNotFoundException;
import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import com.duoxik.logfinder.gui.View;
import com.duoxik.logfinder.model.LogFile;
import com.duoxik.logfinder.model.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Controller {
    private final Model model;
    private final View view;
    private final LogFinderController logFinder = new LogFinderController();
    private final FileReaderController fileReader = new FileReaderController();

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void findLogs(String path, String type, String text) {
        try {
            List<LogFile> files = logFinder.findLogs(path, type, text);
            model.update(new File(path), files);
            view.updateFileStructure(model.getRootDirectory(), model.getFiles());
        } catch (DirectoryNotFoundException e) {
            view.showDirectoryNotFound();
        } catch (FileIsNotDirectoryException e) {
            view.showFileIsNotDirectory();
        }
    }

    public void readFile(File file) {
        try {
            String text = fileReader.readFile(file);
            view.openNewTab(file, text);
        } catch (FileNotFoundException ignored) {
        }
    }

    public void exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.setController(controller);
    }
}
