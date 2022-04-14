package fr.uparis.pandaparser.application.cmd;

import fr.uparis.pandaparser.config.Config;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "serve", description = "Compile the site and then launch an HTTP server on port 8080 by default.", mixinStandardHelpOptions = true)
public class ServeCommand implements Callable<Integer> {

    @Option(names = {"-i", "--input-dir"}, paramLabel = "INPUT_DIR", description = "Specify a tree to process different from the current directory.")
    private String input = Config.DEFAULT_INPUT;

    @Option(names = {"-o", "--output-dir"}, paramLabel = "OUTPUT_DIR", description = "Specify where the result will be stored")
    private String output = Config.DEFAULT_OUTPUT;

    @Option(names = {"-j", "--jobs"}, paramLabel = "NB_JOBS", description = "Disable incremental")
    private Integer jobs = Config.DEFAULT_MACHINE_JOB;

    @Option(names = {"-w", "--watch"}, description = "Monitors the source files and automatically recompiles the site at each modification, with recalculation")
    private boolean watched;

    @Option(names = {"-p", "--port"}, description = "Specify a port other than 8080")
    private Integer port = Config.DEFAULT_PORT;



    @Override
    public Integer call() {
        return 0;
    }

}
