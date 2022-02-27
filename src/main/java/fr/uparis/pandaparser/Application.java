package fr.uparis.pandaparser;


import fr.uparis.pandaparser.old.CommandManager;

public class Application {
    public static void main(String[] args) {
        CommandManager.triggerCommand(args);
    }
}
