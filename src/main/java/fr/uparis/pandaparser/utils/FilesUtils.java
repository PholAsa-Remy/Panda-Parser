package fr.uparis.pandaparser.utils;


import java.io.*;

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
    public static String getFileCotent(final String filePath) throws IOException {
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
    public static void createFileFromContent(final String filePath, final String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
        }
    }
}