package com.duoxik.logfinder;

import com.duoxik.logfinder.exceptions.FileIsNotDirectoryException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {
    private static final Model MODEL = new Model();
    private static final File TEST_FILE = new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files");

    @Test
    public void clear() {
        File file = TEST_FILE;
        String type = "log";
        String match = "1";

        try {
            MODEL.findLogs(file, type, match);
            Assert.assertTrue(!MODEL.getFiles().isEmpty());

            MODEL.clear();
            Assert.assertTrue(MODEL.getFiles().isEmpty());
        } catch (FileNotFoundException e) {
            Assert.fail("File not found?!");
        } catch (FileIsNotDirectoryException e) {
            Assert.fail("File is not directory?!");
        }
    }

    @Test
    public void findLogsTest1() {
        File file = TEST_FILE;
        String type = "log";
        String match = "4";

        try {
            MODEL.findLogs(file, type, match);

            List<File> expected = new ArrayList<>();
            expected.add(new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files/dir_1/log_file.log"));
            expected.add(new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files/log_file.log"));

            List<File> actual = MODEL.getFiles();

            Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
        } catch (FileNotFoundException e) {
            Assert.fail("File not found?!");
        } catch (FileIsNotDirectoryException e) {
            Assert.fail("File is not directory?!");
        }

        MODEL.clear();
    }

    @Test
    public void findLogsTest2() {
        File file = TEST_FILE;
        String type = "log";
        String match = "0";

        try {
            MODEL.findLogs(file, type, match);

            Assert.assertTrue(MODEL.getFiles().isEmpty());
        } catch (FileNotFoundException e) {
            Assert.fail("File not found?!");
        } catch (FileIsNotDirectoryException e) {
            Assert.fail("File is not directory?!");
        }

        MODEL.clear();
    }

    @Test
    public void findLogsTest3() {
        File file = TEST_FILE;
        String type = "txt";
        String match = "das";

        try {
            MODEL.findLogs(file, type, match);

            List<File> expected = new ArrayList<>();
            expected.add(new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files/dir_1/notLogFile.txt"));
            expected.add(new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files/qq.txt"));

            List<File> actual = MODEL.getFiles();

            Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
        } catch (FileNotFoundException e) {
            Assert.fail("File not found?!");
        } catch (FileIsNotDirectoryException e) {
            Assert.fail("File is not directory?!");
        }

        MODEL.clear();
    }

    @Test
    public void findLogsTest4() {
        File file = new File("path/daspdas");
        String type = "txt";
        String match = "das";

        try {
            MODEL.findLogs(file, type, match);

            Assert.fail("Directory is not exists");

        } catch (FileNotFoundException e) {
        } catch (FileIsNotDirectoryException e) {
            Assert.fail("File is not directory?!");
        }

        MODEL.clear();
    }

    @Test
    public void findLogsTest5() {
        File file = new File("/home/duoxik/IdeaProjects/LogsFinder/test/com/duoxik/logfinder/files/dir_1/log_file.log");
        String type = "txt";
        String match = "das";

        try {
            MODEL.findLogs(file, type, match);

            Assert.fail("File is not directory");
        } catch (FileNotFoundException e) {
            Assert.fail("File not found?!");
        } catch (FileIsNotDirectoryException e) {
        }

        MODEL.clear();
    }
}