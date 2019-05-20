package com.duoxik.logfinder.controller;

import com.duoxik.logfinder.exceptions.DirectoryNotFoundException;
import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import com.duoxik.logfinder.gui.View;
import com.duoxik.logfinder.model.LogFile;
import com.duoxik.logfinder.model.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Controller implements Runnable {
    private final Model model;
    private final View view;
    private final LogFinderController logFinder = new LogFinderController();
    private final FileReaderController fileReader = new FileReaderController();

    private String path;
    private String type;
    private String text;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void findLogs(String path, String type, String text) {
        this.path = path;
        this.type = type;
        this.text = text;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            view.lockFindButton();
            File directory = new File(path);
            List<LogFile> files = logFinder.findLogs(directory, type, text);
            view.updateFileStructure(directory, files);
            model.update(directory, files);
        } catch (DirectoryNotFoundException e) {
            view.showDirectoryNotFound();
        } catch (FileIsNotDirectoryException e) {
            view.showFileIsNotDirectory();
        } catch (FileNotFoundException e) {
            view.showFilesNotFound();
        } finally {
            view.unlockFindButton();
        }
    }

    public void openFile(File file) {
        LogFile log = model.getLogFile(file);

        if (log != null) {
            String firstPage = fileReader.readFile(log, 1);
            int countPages = fileReader.countPages(log);
            view.openTab(file, countPages, firstPage);
        }
    }

    public String getPage(File file, int pageNumber) {
        LogFile log = model.getLogFile(file);
        return (log != null) ? fileReader.readFile(log, pageNumber) : null;
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
