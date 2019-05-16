package com.duoxik.logfinder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {

    private List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void findLogs(String path, String type, String match) throws FileNotFoundException {

        File dir = new File(path);

        if (!dir.exists())
            throw new FileNotFoundException();

        if (type == null || type.equals(""))
            type = "log";

        if (dir.isDirectory()) {

            for (File file : dir.listFiles()) {
                if (isFileType(file, type) && isFileContains(file, match)) {
                    files.add(file);
                }
            }
        } else {

            if (isFileType(dir, type) && isFileContains(dir, match)) {
                files.add(dir);
            }
        }
    }

    public void clear() {
        files.clear();
    }

    private boolean isFileType(File file, String type) {
        return file.getName().endsWith("." + type);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
