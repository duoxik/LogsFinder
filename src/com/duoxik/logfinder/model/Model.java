package com.duoxik.logfinder.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private File rootDirectory;
    private List<LogFile> files = new ArrayList<>();

    public File getRootDirectory() {
        return rootDirectory;
    }

    public List<LogFile> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void update(File rootDirectory, List<LogFile> files) {
        this.rootDirectory = rootDirectory;
        this.files = files;
    }
}
