package com.duoxik.logfinder.controller;

import com.duoxik.logfinder.model.LogFile;

import java.io.*;

public class FileReaderController {

    public String readFile(LogFile log, int pageNumber) throws IOException {
        try (
                RandomAccessFile raf = new RandomAccessFile(log.getFile(), "r")
        ) {
            long position = Controller.PAGE_SIZE * (pageNumber - 1);
            raf.seek(position);

            byte[] arr = raf.length() - position < Controller.PAGE_SIZE
                    ? new byte[(int) (raf.length() - position)]
                    : new byte[Controller.PAGE_SIZE];

            raf.read(arr);

            return new String(arr);
        }
    }

    public int countPages(LogFile log) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(log.getFile())
        ) {
            return fis.available() % Controller.PAGE_SIZE != 0
                    ? fis.available() / Controller.PAGE_SIZE + 1
                    : fis.available() / Controller.PAGE_SIZE;

        }
    }
}
