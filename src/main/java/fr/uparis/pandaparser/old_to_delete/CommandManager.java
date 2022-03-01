package fr.uparis.pandaparser.old_to_delete;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static fr.uparis.pandaparser.config.Config.APP_NAME;
import static java.lang.System.exit;

@Log
public class CommandManager {

    private static final String[] allCommands = {"md2html", "buildsite", "help", "metadata"};

    public static void triggerCommand(String[] args) {
        if (checkArguments(args) != 0) exit(0);
    }

    private static int checkArguments(String[] args) {
        log.info("Voici les arguments :gg" + Arrays.toString(args));
        if (args.length == 0) {
            log.info("usage"); // Help.usage();
            return 1;
        }

        String command = args[0];
//        print("Vous avez entrez une commande :"+command);
        if (Arrays.asList(allCommands).contains(command)) {
            mostSimilarCommands(command);
            return 1;
        }
        return 0;
    }


    private static void mostSimilarCommands(String noGoodCommand) {
        log.info(APP_NAME + ": '" + noGoodCommand + "' is not a " + APP_NAME + " command. See '" + APP_NAME + " --help'.");
        String[] similarCommands = likeStrings(allCommands, noGoodCommand);
        if (similarCommands.length > 0) {
            log.info("The most similar commands are");
            log.info(Arrays.toString(similarCommands));
        }
    }

    private static String[] likeStrings(String[] all, String target) {
        ArrayList<String> rv = new ArrayList<String>();
        Collections.addAll(rv, all);
        return (String[]) rv.toArray();
    }

}
