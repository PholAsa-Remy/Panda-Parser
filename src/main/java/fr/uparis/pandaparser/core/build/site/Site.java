package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.build.parallel.ThreadParser;
import fr.uparis.pandaparser.core.staticFile.StaticFile;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Traduction d’un site complet
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Site extends PandaParser {

    private final ExecutorService threadPool;

    public Site(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SITE);
        this.threadPool = Executors.newFixedThreadPool(jobs);
    }


    @Override
    public void parse() {
        try {
            /* parse all files */
            this.parseAllMdFilesToHtml();

        } catch (IOException e) {
            // FIXME
            log.warning("input <" + this.input + "> invalide format");
        }
    }

    /**
     * Parse All MDs files in content directory to html
     */
    private void parseAllMdFilesToHtml() throws IOException {
        this.getAllMdFiles().forEach(inputFilePath -> PandaParser.builder()
                .setInput(inputFilePath)
                .setOutput(this.output)
                .build()
                .parse());
    }

    /**
     * Helper methode to get All MDs filenames from content directory
     *
     * @return set Of filenames
     */
    private Set<String> getAllMdFiles() throws IOException {
        String contentDirectoryPath = this.input + Config.DEFAULT_CONTENT_DIR;
        return FilesUtils.getAllFilesFromDirectory(contentDirectoryPath, Extension.MD);
    }


    private List<ThreadParser> getAllThreadParser() throws IOException {
        return this.getAllMdFiles().stream().map(inputFilePath -> new ThreadParser(input, output)).collect(Collectors.toList());
    }

    private void fastParseAllMdFilesToHtml() throws IOException, InterruptedException {
        List<Future<String>> futures = this.threadPool.invokeAll(this.getAllThreadParser());
    }





    private void moveAllStaticFiles() throws IOException{
        try{
            StaticFile.setAllStaticFiles(this.input, this.output);
        } catch (IOException e) {
            log.warning("moveAllStaticFiles failed");
            e.printStackTrace();
        }
    }

    private void fastParseAllMdFilesToHtml() throws IOException, InterruptedException {
        List<Future<String>> futures = this.threadPool.invokeAll(this.getAllThreadParser());
    }
}
