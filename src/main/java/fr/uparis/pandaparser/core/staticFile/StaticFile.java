package fr.uparis.pandaparser.core.staticFile;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Set;

@Log
public class StaticFile {

    /**
     * @param input directory source path
     * Get all static filenames in the directory
     * @return set Of static filenames
     */
    private static Set<String> getAllStaticFiles(String input) throws IOException {
        return FilesUtils.getAllStaticFilesFromDirectory(input);
    }

    /**
     * @param input directory source path
     * @param output directory destination path
     * Copy all the static files from input directory to output directory
     */
    public static void setAllStaticFiles(String input, String output) throws IOException {
        String inputDirectoryPath = input + Config.DEFAULT_STATIC_DIR;
        String outputDirectoryPath = output + Config.DEFAULT_STATIC_DIR;
        Set<String> staticFiles = getAllStaticFiles(inputDirectoryPath);
        staticFiles.forEach((file) -> {
            try {
                FilesUtils.copyFileFromInputToOutput( inputDirectoryPath + "/" + file,outputDirectoryPath + "/" + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("static : Successful transfer static files from " + inputDirectoryPath
                + " to " + outputDirectoryPath);
    }
}
