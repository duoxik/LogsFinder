package com.duoxik.logfinder.controller;

import com.duoxik.logfinder.model.LogFile;

import java.io.*;

public class FileReaderController {

    private static final int PAGE_SIZE = 1024 * 1024;

    public String readFile(LogFile log, int pageNumber) {
        String resultPage = null;

        try (
                RandomAccessFile raf = new RandomAccessFile(log.getFile(), "r")
        ) {
            long position = PAGE_SIZE * (pageNumber - 1);
            raf.seek(position);

            byte[] arr;
            if (raf.length() - position < PAGE_SIZE) {
                arr = new byte[(int) (raf.length() - position)];
            } else {
                arr = new byte[PAGE_SIZE];
            }

            raf.read(arr);

            resultPage = new String(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultPage;
    }

    public int countPages(LogFile log) {
        int countPages = -1;

        try (
                FileInputStream fis = new FileInputStream(log.getFile())
        ) {
            if (fis.available() % (PAGE_SIZE) != 0)
                countPages = fis.available() / (PAGE_SIZE) + 1;
            else
                countPages = fis.available() / (PAGE_SIZE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countPages;
    }
}
