package fr.uparis.pandaparser.core.serve;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.serve.http.HttpPandaParserServer;
import fr.uparis.pandaparser.core.serve.watchdog.DirectoryWatcher;
import lombok.extern.java.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log
public class Serve extends PandaParser {

    private final int port;
    private final ExecutorService watchDogExecutor = Executors.newSingleThreadExecutor();

    public Serve(String input, String output, String template, boolean watch, int jobs, ParserType type, int port) {
        super(input, output, template, watch, jobs, type);
        this.port = port;
    }

    @Override
    public void parse() {
        PandaParser.builder()
                .setInput(input).setOutput(output)
                .setJobs(jobs).isWatched(watch)
                .build().parse();
    }

    public void start() {
        try {
            this.parse();
            HttpPandaParserServer httpPandaParserServer = new HttpPandaParserServer(this.port, this.output);
            watchDog();
            httpPandaParserServer.start();
            this.watchDogExecutor.shutdown();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void watchDog() {
        this.watchDogExecutor.submit(new DirectoryWatcher(input + File.separator + Config.DEFAULT_CONTENT_DIR, output));
        log.info("watch dog Started");
    }
}
