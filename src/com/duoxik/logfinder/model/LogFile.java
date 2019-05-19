package com.duoxik.logfinder.model;

import java.io.File;
import java.nio.file.Path;

public class LogFile implements Comparable<LogFile> {

    private File file;
    private String text = null;

    public LogFile(String path) {
        this.file = new File(path);
    }

    public Path getPath() {
        return file.toPath();
    }

    public File getFile() {
        return file;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(LogFile o) {
        return file.toString().compareTo(o.getFile().toString());
    }
}
