package fr.uparis.pandaparser.core.build.parallel;

import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public record ThreadStaticFilesCopy(String input, String output) implements Callable<String> {

    @Override
    public String call() throws Exception {
        FilesUtils.copyFileFromInputToOutput(input, output);
        return "copy file from : " + this.input + " to : " + this.output;
    }

    public static List<ThreadStaticFilesCopy> createListOfThreadStaticFilesCopy(@NonNull final String input, @NonNull String output) {
        try {
            Set<String> staticFilesPath = FilesUtils.getAllStaticFilesFromDirectory(input);
            FilesUtils.createDirectoryIfNotExiste(output);
            return staticFilesPath.stream().map(inputFile -> getThreadStaticFilesCopy(inputFile, output)).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static ThreadStaticFilesCopy getThreadStaticFilesCopy(String inputFile, String output) {
        String outputFile = output + FilesUtils.getFileName(inputFile);
        return new ThreadStaticFilesCopy(inputFile, outputFile);
    }

}
