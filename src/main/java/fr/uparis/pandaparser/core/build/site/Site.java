package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.build.parallel.AbstractThread;
import fr.uparis.pandaparser.core.build.parallel.ThreadParser;
import fr.uparis.pandaparser.core.build.parallel.ThreadStaticFilesCopier;
import fr.uparis.pandaparser.utils.ThreadUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Traduction d’un site complet
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Site extends PandaParser {

    /*Thread pool*/
    private final ExecutorService threadPool;


    public Site(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SITE);
        this.threadPool = Executors.newFixedThreadPool(this.jobs);
    }

    @Override
    public void parse() {
        try {

            this.startPoolThreadParsing();

        } catch (Exception exception) {
            log.warning("input <" + this.input + "> invalide format");
        }
    }

    private void startPoolThreadParsing() throws IOException, InterruptedException, ExecutionException {
        List<Future<String>> threadParserResults = this.threadPool.invokeAll(this.getAllThreadParser());
        List<Future<String>> threadStaticCopyResults = this.threadPool.invokeAll(this.getAllThreadStaticFilesCopier());
        this.threadPool.shutdown();
        ThreadUtils.logAllFutures(threadParserResults);
        ThreadUtils.logAllFutures(threadStaticCopyResults);
    }

    /**
     * get All Threads parser
     *
     * @return threads parser
     */
    private List<AbstractThread> getAllThreadParser() throws IOException {
        return ThreadParser.createListOfThreads(this.input, this.output);
    }

    /**
     * get All Threads to copy statics files
     *
     * @return threads parser
     */
    private List<AbstractThread> getAllThreadStaticFilesCopier() {
        return ThreadStaticFilesCopier.createListOfThreads(this.input, this.output);
    }
}
