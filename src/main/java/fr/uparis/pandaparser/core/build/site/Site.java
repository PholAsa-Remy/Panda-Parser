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
 * Traduction d'un site complet
 *
 * <p>
 * Translation of a complete site, stored as a full tree on the filesystem,
 * into another  tree.  The site follows a  very  specific  tree structure.
 * The project must contain the following elements :
 * <ul>
 *     <li>At the root, a site.toml configuration file using the TOML format.
 *     Using the TOML format makes it easily extensible.</li>
 *     <li>In the contents sub-directory, a set of .md files containing the
 *     documents to be converted into web pages including at least one index.md
 *     file which will serve as the root of the site.</li>
 * </ul>
 * </p>
 *
 * <h3> Usage exemple </h3>
 *
 * <pre> {@code
 *  public class PandaParserTest {
 *      private PandaParser siteBuilder(String inputDir, String outputDir) {
 *         return PandaParser.builder()
 *                 .setInput(inputDir).setOutput(outputDir)
 *                 .build();
 *     }
 *     public void parse () {
 *         siteBuilder().parse("my-site", "out");
 *     }
 *  }
 * }</pre>
 * <p>
 *  * See also : {@link PandaParser}
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Site extends PandaParser {

    /*Thread pool service */
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

    /**
     * Parallel translation independent parts of the site:
     * <p>
     *     <ul>
     *         <li>Parallel translation of MDs files to HTMLs</li>
     *         <li>Parallel addition of static files</li>
     *     </ul>
     * <p>
     * See also : {@link ThreadParser}, {@link ThreadStaticFilesCopier}, {@link Thread}, {@link ExecutorService}
     */
    private void startPoolThreadParsing() throws IOException, InterruptedException, ExecutionException {
        List<Future<String>> threadParserResults = this.threadPool.invokeAll(this.getAllThreadParser());
        List<Future<String>> threadStaticCopyResults = this.threadPool.invokeAll(this.getAllThreadStaticFilesCopier());
        this.threadPool.shutdown();
       ThreadUtils.logAllFutures(threadParserResults);
       ThreadUtils.logAllFutures(threadStaticCopyResults);
    }

    /**
     * Get All Threads parser to translate MDs.
     *
     * @return Collection of tasks to submit them in ExecutorService
     */
    private List<AbstractThread> getAllThreadParser() throws IOException {
        return ThreadParser.createListOfThreads(this.input, this.output);
    }

    /**
     * Get All thread to add statics file in the website
     *
     * @return Collection of tasks to submit them in ExecutorService
     */
    private List<AbstractThread> getAllThreadStaticFilesCopier() {
        return ThreadStaticFilesCopier.createListOfThreads(this.input, this.output);
    }
}
