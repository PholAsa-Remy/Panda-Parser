package fr.uparis.pandaparser.core.serve;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.serve.http.HttpPandaParserServer;


public class Serve extends PandaParser {

    private final int port;
    public Serve(String input, String output, boolean watch, int jobs, ParserType type, int port) {
        super(input, output, watch, jobs, type);
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
            httpPandaParserServer.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
