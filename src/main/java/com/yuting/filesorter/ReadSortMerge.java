package com.yuting.filesorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadSortMerge {

    /**
     * Read in small chunks of the file and sort it.
     * Write the sorted file chunk back onto the disk.  */
    public static List<File> split(String filePath, int maxAvailableMB) {
        List<File> list = new ArrayList<>();
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            long fileSize = raf.length();
            long maxAvailableBytes = maxAvailableMB * 1024L * 1024L;
            long numberOfReads = (long) Math.ceil((float) fileSize / maxAvailableBytes);

            System.out.printf("FileSize: %,d bytes%n", fileSize);
            System.out.printf("maxAvailableBytes: %,d bytes%n", maxAvailableBytes);
            System.out.printf("numberOfReads: %,d%n", numberOfReads);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }



    /**
     * Write a file chunk back to disk */
    public static void writeToDisk() {}

    /**
     * Sort file chunk */
    public static void sort() {}

    /**
     * Sort entire file */
    public static void sortFinalFile() {}

    /**
     * Write sorted file to output file */
    public static void writeToOutput(){}

}
