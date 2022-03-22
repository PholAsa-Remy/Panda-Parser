package fr.uparis.pandaparser.core.build.parallel;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class ThreadParser extends AbstractThread {

    public ThreadParser(@NonNull final String input, @NonNull final String output) {
        super(input, output);
    }

    @Override
    public String call() throws Exception {
        PandaParser.builder().setInput(input).setOutput(output).build().parse();
        return Thread.currentThread().getName() + " : input : " + this.input + " to out: " + this.output;
    }

    /**
     * Create list of thread parser from input directory
     *
     * @param inputDirectory  input
     * @param outputDirectory output
     * @return list of thread
     * @throws IOException io exception
     */
    public static List<AbstractThread> createListOfThreads(String inputDirectory, String outputDirectory) throws IOException {
        String contentDirectoryPath = inputDirectory + Config.DEFAULT_CONTENT_DIR;
        FilesUtils.createDirectoryIfNotExiste(outputDirectory);
        return FilesUtils.getAllFilesFromDirectory(contentDirectoryPath, Extension.MD)
                .stream().map(inputFilePath -> new ThreadParser(inputFilePath, outputDirectory))
                .collect(Collectors.toList());
    }

}
