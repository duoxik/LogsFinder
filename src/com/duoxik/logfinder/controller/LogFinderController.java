package com.duoxik.logfinder.controller;

import com.duoxik.logfinder.exceptions.DirectoryNotFoundException;
import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import com.duoxik.logfinder.model.LogFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFinderController {

    public List<LogFile> findLogs(File directory, String type, String text)
            throws DirectoryNotFoundException, FileIsNotDirectoryException, FileNotFoundException {

        if (!directory.exists())
            throw new DirectoryNotFoundException();

        if (!directory.isDirectory())
            throw new FileIsNotDirectoryException();


        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith("." + type) || Paths.get(directory.toString(), fileName).toFile().isDirectory();
            }
        };

        List<LogFile> files = new ArrayList<>();
        recursive(files, directory, text, filter);

        if (files.isEmpty())
            throw new FileNotFoundException();

        Collections.sort(files);
        return files;
    }

    private void recursive(List<LogFile> files, File dir, String text, FilenameFilter filter) {

        for (File file : dir.listFiles(filter)) {
            if (file.isDirectory()) {
                recursive(files, file, text, filter);
                continue;
            }

            findMatches(files, file, text);
        }
    }

    private void findMatches(List<LogFile> files, File file, String match) {
        LogFile logFile = new LogFile(file);

        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))
        ) {
            Pattern pattern = Pattern.compile(match);

            int pagesCounter = 0;
            while(bis.available() > 0) {
                pagesCounter++;
                byte[] arr = bis.available() < Controller.PAGE_SIZE
                        ? new byte[bis.available()]
                        : new byte[Controller.PAGE_SIZE];

                bis.read(arr);
                String page = new String(arr);
                Matcher matcher = pattern.matcher(page);
                while (matcher.find()) {
                    logFile.addIndex(matcher.start(), matcher.end(), pagesCounter);
                }
            }

            logFile.setCountPages(pagesCounter);
        } catch (FileNotFoundException ignored) {
        } catch (IOException ignored) {}

        if (logFile.getMatchIndexes().size() != 0) {
            files.add(logFile);
        }
    }
}
