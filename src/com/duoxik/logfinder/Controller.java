package com.duoxik.logfinder;

import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import com.duoxik.logfinder.gui.View;

import java.io.File;
import java.io.FileNotFoundException;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void findLogs(String path, String type, String text) {
        try {
            model.findLogs(new File(path), type, text);
        } catch (FileNotFoundException e) {
            // TODO
        } catch (FileIsNotDirectoryException e) {
            // TODO
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

        view.init();
    }
}
