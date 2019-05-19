package com.duoxik.logfinder.controllers;

import java.io.*;

public class FileReaderController {

    public String readFile(File file) throws FileNotFoundException {

        if (!file.exists())
            throw new FileNotFoundException();

        StringBuilder data = new StringBuilder();

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))
        ) {
            while (br.ready()) {
                data.append(br.readLine() + "\n");
            }
        } catch (IOException ignored) {
        }

        return data.toString();
    }
}
