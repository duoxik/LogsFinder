package com.duoxik.logfinder.model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LogFile implements Comparable<LogFile> {

    private File file;
    private List<MatchIndex> matchIndexes = new ArrayList<>();
    private int countPages = 0;

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

    public List<MatchIndex> getMatchIndexes() {
        return Collections.unmodifiableList(matchIndexes);
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public void addIndex(int start, int end, int page) {
        matchIndexes.add(new MatchIndex(start, end, page));
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

    public static class MatchIndex {
        private int start;
        private int end;
        private int page;

        public MatchIndex(int start, int end, int page) {
            this.start = start;
            this.end = end;
            this.page = page;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int getPage() {
            return page;
        }
    }
}
