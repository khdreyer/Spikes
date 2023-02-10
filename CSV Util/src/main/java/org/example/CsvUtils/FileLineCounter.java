package org.example.CsvUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLineCounter {

    public int getLineCount(String fileName) throws IOException {
        int lines = 0;

        try (var reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (Exception ex) {
            throw ex;
        }

        return lines;
    }
}