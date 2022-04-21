package fr.uparis.pandaparser.core.build.parallel;

import fr.uparis.pandaparser.application.Application;
import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ThreadParser - to translate Md files to Html.
 *
 * <p>
 * see also:
 * {@link AbstractThread}
 * {@link fr.uparis.pandaparser.core.build.site.Site}
 * {@link fr.uparis.pandaparser.core.build.simple.Simple}
 * </p>
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class ThreadParser extends AbstractThread {

    public ThreadParser(@NonNull final String input, @NonNull final String output, @NonNull final String template) {
        super(input, output, template);
    }

    /**
     * Create list of thread parser from input directory
     *
     * @param inputDirectory  input
     * @param outputDirectory output
     * @return list of thread
     * @throws IOException io exception
     */
    public static List<AbstractThread> createListOfThreads(String inputDirectory, String outputDirectory, String template) throws IOException {
        String inputContentDirectoryPath = inputDirectory + Config.DEFAULT_CONTENT_DIR;
        String outputContentDirectoryPath = outputDirectory + Config.DEFAULT_CONTENT_DIR;
        FilesUtils.createDirectoryIfNotExiste(outputDirectory);
        FilesUtils.createDirectoryIfNotExiste(outputContentDirectoryPath);
        return FilesUtils.getAllFilesFromDirectory(inputContentDirectoryPath, Extension.MD)
                .stream().map(inputFilePath -> {
                    Path parent = Path.of(inputFilePath.split(Config.DEFAULT_CONTENT_DIR)[1]).getParent();
                    return new ThreadParser(inputFilePath, outputContentDirectoryPath + ((parent == null) ? "" : parent.toString()), template);
                })
                .collect(Collectors.toList());
    }

    @Override
    public String call() {
        PandaParser.builder().setInput(input).setOutput(output).setTemplate(template).build().parse();
        return Thread.currentThread().getName() + " : input : " + this.input + " to out: " + this.output;
    }

}
