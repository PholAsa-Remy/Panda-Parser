package fr.uparis.pandaparser.core.cmd;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "build", description = "Traduction d'un ou plusieurs fichiers Markdown en fichier(s) HTML5", mixinStandardHelpOptions = true)
public class BuildCommand implements Callable<Integer> {


    @Option(names = {"-i", "--input-dir"}, paramLabel = "INPUT_DIR", description = "Permet de spécifier une arborescence à traiter différente du répertoire courant.")
    private String input = Config.DEFAULT_INPUT;

    @Option(names = {"-o", "--output-dir"}, paramLabel = "OUTPUT_DIR", description = "Permet de spécifier où le résultat sera stocké ")
    private String output = Config.DEFAULT_OUTPUT;

    @Option(names = {"-j", "--jobs"}, paramLabel = "NB_JOBS", description = "Désactive l’incrémentalité")
    private Integer jobs = Config.DEFAULT_MACHINE_JOB;

    @Option(names = {"-w", "--watch"}, description = "Surveille les fichiers sources et recompile automatiquement le site à chaque modification, avec recalcul")
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
