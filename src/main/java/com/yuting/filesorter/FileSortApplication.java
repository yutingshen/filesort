package com.yuting.filesorter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileSortApplication {
    public static void main(String[] args) {
        try {

            String filePath = "src/test/resources/input/random.txt";

            System.out.println("Starting FileSortApplication for: " + filePath);

            List<File> fileChunks = ReadSortMerge.split(filePath, 100);

            System.out.println("Finished FileSortApplication for: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
