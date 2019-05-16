package com.duoxik.logfinder;

import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {

    private List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void findLogs(final File dir, final String type, final String match) throws FileNotFoundException, FileIsNotDirectoryException {

        if (!dir.exists())
            throw new FileNotFoundException();

        if (!dir.isDirectory())
            throw new FileIsNotDirectoryException();

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith("." + type) || Paths.get(directory.toString(), fileName).toFile().isDirectory();
            }
        };

        for (File file : dir.listFiles(filter)) {

            if (file.isDirectory()) {
                findLogs(file, type, match);
                continue;
            }

            if (isFileContains(file, match)) {
                files.add(file);
            }
        }
    }

    public void clear() {
        files.clear();
    }

    private boolean isFileContains(File file, String match) {

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))
        ) {

            while(br.ready()) {
                String line = br.readLine();
                if (line.contains(match)) {
                    return true;
                }
            }

        } catch (FileNotFoundException ignored) {
        } catch (IOException ignored) {
        }

        return false;
    }
}
