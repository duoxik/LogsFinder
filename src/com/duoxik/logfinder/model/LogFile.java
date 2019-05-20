package com.duoxik.logfinder.model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LogFile implements Comparable<LogFile> {

    private File file;
    private String text = null;
    private List<Pair> indexes = new ArrayList<>();
    private long cursor = 0;

    public LogFile(String path) {
        this.file = new File(path);
    }

    public LogFile(File file) {
        this.file = file;
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

    public List<Pair> getIndexes() {
        return Collections.unmodifiableList(indexes);
    }

    public long getCursor() {
        return cursor;
    }

    public void setCursor(long cursor) {
        this.cursor = cursor;
    }

    public void addIndex(long start, long end) {
        indexes.add(new Pair(start, end));
    }

    @Override
    public int compareTo(LogFile o) {
        return file.toString().compareTo(o.getFile().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogFile logFile = (LogFile) o;
        return Objects.equals(file, logFile.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }

    public static class Pair {
        private long x;
        private long y;

        public Pair(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }
    }
}
