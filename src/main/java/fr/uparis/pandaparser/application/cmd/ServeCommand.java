package fr.uparis.pandaparser.application.cmd;

import fr.uparis.pandaparser.config.Config;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "serve", description = "Compile le site puis lance un serveur HTTP sur le port 8080 par défaut.", mixinStandardHelpOptions = true)
public class ServeCommand implements Callable<Integer> {

    @Option(names = {"-p", "--port"}, description = "Spécifie un autre port que 8080")
    private Integer port = Config.DEFAULT_PORT;


    @Override
    public Integer call() {
        return 0;
    }

}
