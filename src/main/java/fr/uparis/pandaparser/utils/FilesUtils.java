package fr.uparis.pandaparser.utils;


import lombok.NonNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FilesUtils class, help methods to manipulate files.
 *
 * @author pada-parser group
 * @version 1.0.0
 * @since Fev 2022
 */
public class FilesUtils {

    /**
     * private constructor
     */
    private FilesUtils() {
    }


    /**
     * Read the content of a file
     *
     * @param filePath the file name we want to read
     * @return fileContent
     */
    public static String getFileCotent(@NonNull final String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(line -> sb.append(line).append("\n"));
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Create html file from text
     *
     * @param filePath file path
     * @param text     text to write in file
     */
    public static void createFileFromContent(@NonNull final String filePath, @NonNull final String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
        }
    }

    /**
     * list files within a directory.
     * we  try-with-resources statement to  make sure the stream will be closed right
     * after the stream operations are completed.
     *
     * @param directory directory
     * @return set of files path
     * @throws IOException if the directory doesn't exist.
     */
    public static Set<String> getAllFilesFromDirectory(@NonNull final String directory) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
}