package fr.uparis.pandaparser.application.cmd;

import fr.uparis.pandaparser.config.Config;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "serve", description = "Compile the site and then launch an HTTP server on port 8080 by default.", mixinStandardHelpOptions = true)
public class ServeCommand implements Callable<Integer> {

    @Option(names = {"-p", "--port"}, description = "Specify a port other than 8080")
    private Integer port = Config.DEFAULT_PORT;


    @Override
    public Integer call() {
        return 0;
    }

}
