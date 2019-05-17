package com.duoxik.logfinder;

import com.duoxik.logfinder.exceptions.DirectoryNotFoundException;
import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private File rootDirectory;
    private List<File> files = new ArrayList<>();

    public File getRootDirectory() {
        return rootDirectory;
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void findLogs(final File dir, final String type, final String text) throws DirectoryNotFoundException, FileIsNotDirectoryException {
        if (!dir.exists())
            throw new DirectoryNotFoundException();

        if (!dir.isDirectory())
            throw new FileIsNotDirectoryException();

        rootDirectory = dir;
        files.clear();

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith("." + type) || Paths.get(directory.toString(), fileName).toFile().isDirectory();
            }
        };

        recursive(dir, text, filter);
    }

    public void clear() {
        files.clear();
    }

    private void recursive(File dir, String text, FilenameFilter filter) {
        for (File file : dir.listFiles(filter)) {
            if (file.isDirectory()) {
                recursive(file, text, filter);
                continue;
            }

            if (isFileContains(file, text)) {
                files.add(file);
            }
        }
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
