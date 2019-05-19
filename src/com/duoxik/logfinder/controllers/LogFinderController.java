package com.duoxik.logfinder.controllers;

import com.duoxik.logfinder.exceptions.DirectoryNotFoundException;
import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import com.duoxik.logfinder.model.LogFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogFinderController {

    public List<LogFile> findLogs(String path, String type, String text) throws DirectoryNotFoundException, FileIsNotDirectoryException {
        File dir = new File(path);

        if (!dir.exists())
            throw new DirectoryNotFoundException();

        if (!dir.isDirectory())
            throw new FileIsNotDirectoryException();


        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith("." + type) || Paths.get(directory.toString(), fileName).toFile().isDirectory();
            }
        };

        List<LogFile> files = recursive(dir, text, filter);
        Collections.sort(files);
        return files;
    }

    private List<LogFile> recursive(File dir, String text, FilenameFilter filter) {
        List<LogFile> files = new ArrayList<>();

        for (File file : dir.listFiles(filter)) {
            if (file.isDirectory()) {
                files.addAll(recursive(file, text, filter));
                continue;
            }

            if (isFileContains(file, text)) {
                files.add(new LogFile(file.toString()));
            }
        }

        return files;
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
