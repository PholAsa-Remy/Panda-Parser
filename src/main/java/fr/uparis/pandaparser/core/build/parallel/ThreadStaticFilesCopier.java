package fr.uparis.pandaparser.core.build.parallel;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Thread to copy static files
 *
 * <p>see also: {@link java.util.concurrent.Callable} {@link AbstractThread}</p>
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class ThreadStaticFilesCopier extends AbstractThread {

    public ThreadStaticFilesCopier(@NonNull final String input, @NonNull final String output) {
        super(input, output,null);
    }

    /**
     * Create list of thread copier from static input
     *
     * @param input  input directory
     * @param output output directory
     * @return list of thread copier
     */
    public static List<AbstractThread> createListOfThreads(@NonNull final String input, @NonNull String output) {
        String inputStaticFiles = input + Config.DEFAULT_STATIC_DIR;
        String outputStaticFile = output + Config.DEFAULT_STATIC_DIR;
        try {
            Set<String> staticFilesPath = FilesUtils.getAllStaticFilesFromDirectory(inputStaticFiles);
            FilesUtils.createDirectoryIfNotExiste(outputStaticFile);
            return staticFilesPath.stream().map(inputFile -> createFromInputFileAndOutputDir(inputFile, outputStaticFile)).collect(Collectors.toList());
        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    /**
     * Get thread from input file & output directory
     *
     * @param inputFile input file
     * @param output    output directory
     * @return new thread with input & output (generated)
     */
    private static ThreadStaticFilesCopier createFromInputFileAndOutputDir(String inputFile, String output) {
        String outputFile = output + FilesUtils.getFileName(inputFile);
        return new ThreadStaticFilesCopier(inputFile, outputFile);
    }

    @Override
    public String call() throws Exception {
        FilesUtils.copyFileFromInputToOutput(input, output);
        return "Copy file from : " + this.input + " to : " + this.output;
    }

}
