package fr.uparis.pandaparser.core.cmd;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "build", description = "To  parse file from md To html ...", mixinStandardHelpOptions = true)
public class BuildCommand implements Callable<Integer> {


    @CommandLine.Option(names = {"-i", "--input"}, paramLabel = "INPUT_FILE", description = "...")
    private String input = Config.DEFAULT_INPUT;

    @CommandLine.Option(names = {"-o", "--output"}, paramLabel = "OUTPUT_FILE", description = "...")
    private String output = Config.DEFAULT_OUTPUT;

    @CommandLine.Option(names = {"-j", "--jobs"}, paramLabel = "NB_JOBS", description = "...")
    private Integer jobs = Config.DEFAULT_MACHINE_JOB;

    @CommandLine.Option(names = {"--w", "--watch"}, description = "...")
    private boolean watched;


    @Override
    public Integer call() {
        PandaParser parser = PandaParser.builder()
                .setInput(input).setOutput(output)
                .setJobs(jobs).isWatched(watched)
                .build();
        parser.parse();
        return 0;
    }
}
