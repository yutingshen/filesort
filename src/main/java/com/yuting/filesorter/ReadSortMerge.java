package com.yuting.filesorter;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadSortMerge {

    /**
     * Read in small chunks of the file and sort it.
     * Write the sorted file chunk into its own output file
     * @param filePath file location
     * @param maxAvailableMB max MB for processing
     * @param tempLocation temp location for storing smaller file chunks
     * @param comparator string comparator
     * @return List</File> a list of temp files where each has its contents sorted in descending order */
    public static List<File> splitAndSort(String filePath, int maxAvailableMB,
                                          String tempLocation, DescendingComparator comparator) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        List<File> list = new ArrayList<>();

        long maxBytes = maxAvailableMB * 1024 * 1024; // get bytes for max RAM MB
        long noOfReads = getNumberOfReads(raf.length(), maxBytes);
        long remainingReadSize = getNumberOfRemainingSize(raf.length(), maxBytes);
        long chunkByteSize = raf.length() / noOfReads;
        int bufferSize = 8 * 1024;

        System.out.printf("File Size in bytes: %,d bytes%n", raf.length());
        System.out.printf("maxBytes: %,d bytes%n", maxBytes);
        System.out.printf("noOfReads: %d%n", noOfReads);
        System.out.printf("chunkByteSize: %,d bytes%n", chunkByteSize);
        System.out.printf("remainingReadSize: %,d bytes%n", remainingReadSize);

        for (long i = 1; i <= noOfReads; i++) {
            File tempFile = newFile(tempLocation, i);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            if (chunkByteSize > bufferSize) {
                bufferLoop(raf, bos, chunkByteSize, bufferSize);
            } else {
                writeToTemp(raf, bos, chunkByteSize);
            }
            tempFile = sort(tempFile, comparator);
            list.add(tempFile);
            bos.close();
            System.out.println("Temp file size: " + tempFile.length());
        }
        if (remainingReadSize > 0) {
            File lastFile = newFile(tempLocation,noOfReads + 1);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(lastFile));
            bufferLoop(raf, bos, remainingReadSize, bufferSize);
            lastFile = sort(lastFile, comparator);
            list.add(lastFile);
            bos.close();
            System.out.println("Last file size: " + lastFile.length());
        }
        return list;
    }

    private static void bufferLoop(RandomAccessFile raf, BufferedOutputStream bos, long chunkByteSize,
                                   int bufferSize) throws IOException {
        long noOfBufferReads = getNumberOfReads(chunkByteSize, bufferSize);
        long remainingSize = getNumberOfRemainingSize(chunkByteSize, bufferSize);
        for (long j = 1; j <= noOfBufferReads; j++) {
            writeToTemp(raf, bos, bufferSize);
        }
        if (remainingSize > 0) {
            writeToTemp(raf, bos, remainingSize);
        }
    }

    private static File newFile(String tempLocation, long i) {
        File tempDir = new File(tempLocation);
        if (tempDir.exists()) tempDir.delete();
        tempDir.mkdir();
        return new File(tempLocation + "/chunk" + i + ".txt");
    }



    /**
     * Sort file chunk */
    public static File sort(File file, DescendingComparator comparator) throws IOException {
//        List<String> words = new ArrayList<>();
//        try (Stream<String> ln = Files.lines(file.toPath())) {
//            words = ln.collect(Collectors.toList());
//        }
//        try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
//            for (String word : words) {
//                bw.write(word);
//            }
//        }
        return file;
    }

    /**
     * writes chunks of file to temp location
     * @param raf random access file
     * @param bos buffer
     * @param bufferSize max buffer size */
    private static void writeToTemp(RandomAccessFile raf, BufferedOutputStream bos, long bufferSize) throws IOException {
        byte[] buffer = new byte[(int) bufferSize];
        int value = raf.read(buffer);
        if (value != -1) {
            bos.write(buffer);
            bos.flush();
        }
    }

    /**
     * Get number of reads */
    public static long getNumberOfReads(long size, long maxBytes) throws IOException {
        return size / maxBytes;
    }

    private static long getNumberOfRemainingSize(long maxBytes, long bufferSize) {
        return maxBytes % bufferSize;
    }


    public static void merge(List<File> listOfFiles, String s, DescendingComparator descendingComparator) {
    }
}
