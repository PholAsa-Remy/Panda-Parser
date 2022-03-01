package fr.uparis.pandaparser.old_to_delete;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.uparis.pandaparser.config.Config.APP_NAME;
import static fr.uparis.pandaparser.debug.TmpDebug.print;
import static java.lang.System.exit;

public class CommandManager {

    private static final String[] allCommands = {"md2html", "buildsite", "help", "metadata"};

    public static void triggerCommand(String[] args) {
        if (checkArguments(args) != 0) exit(0);
    }

    private static int checkArguments(String[] args) {
        print("Voici les arguments :gg" + Arrays.toString(args));
        if (args.length == 0) {
            print("usage"); // Help.usage();
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
        print(APP_NAME + ": '" + noGoodCommand + "' is not a " + APP_NAME + " command. See '" + APP_NAME + " --help'.");
        String[] similarCommands = likeStrings(allCommands, noGoodCommand);
        if (similarCommands.length > 0) {
            print("The most similar commands are");
            print(Arrays.toString(similarCommands));
        }
    }

    private static String[] likeStrings(String[] all, String target) {
        ArrayList<String> rv = new ArrayList<String>();
        for (String command : all) {
            rv.add(command);
        }
        return (String[]) rv.toArray();
    }

}
