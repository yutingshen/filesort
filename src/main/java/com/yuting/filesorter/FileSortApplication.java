package com.yuting.filesorter;

import java.io.File;
import java.util.List;

public class FileSortApplication {
    public static void main(String[] args) {
        try {
            String filePath = "src/test/resources/input/large.txt";

            System.out.println("Starting FileSortApplication for: " + filePath);

            List<File> listOfFiles = ReadSortMerge.splitAndSort(filePath, 1,
                    "src/test/resources/output", new DescendingComparator());

            ReadSortMerge.merge(listOfFiles, "", new DescendingComparator());

            System.out.println("Finished FileSortApplication");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
