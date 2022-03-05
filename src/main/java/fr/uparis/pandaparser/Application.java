package fr.uparis.pandaparser;


import fr.uparis.pandaparser.core.cmd.BuildCommand;
import picocli.CommandLine;
import picocli.CommandLine.*;

/**
 * Application - Main Class
 *
 * @author panda-parser group
 * @version 1.0.0
 * @see BuildCommand
 * @since Fev 2022
 */
@Command(name = "panda-parser",
        subcommands = {HelpCommand.class, BuildCommand.class},
        mixinStandardHelpOptions = true,
        version = "1.0.0"

)
public class Application {
    /**
     * main method
     *
     * @param args args
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }
}
