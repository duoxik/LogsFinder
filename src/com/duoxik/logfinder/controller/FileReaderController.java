package com.duoxik.logfinder.controller;

import com.duoxik.logfinder.model.LogFile;

import java.io.*;

public class FileReaderController {

    private static final int PAGE_SIZE = 1024 * 1024;

    public String readFile(LogFile log, int pageNumber) throws IOException {
        try (
                RandomAccessFile raf = new RandomAccessFile(log.getFile(), "r")
        ) {
            long position = PAGE_SIZE * (pageNumber - 1);
            raf.seek(position);

            byte[] arr = raf.length() - position < PAGE_SIZE
                    ? new byte[(int) (raf.length() - position)]
                    : new byte[PAGE_SIZE];

            raf.read(arr);

            return new String(arr);
        }
    }

    public int countPages(LogFile log) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(log.getFile())
        ) {
            return fis.available() % PAGE_SIZE != 0
                    ? fis.available() / PAGE_SIZE + 1
                    : fis.available() / PAGE_SIZE;

        }
    }
}
