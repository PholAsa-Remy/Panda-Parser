package fr.uparis.pandaparser.core.commande;

public enum Option {

    INPUT ("--input", true),
    OUTPUT ("--output", true),
    JOBS ("--jobs", true),
    PORT ("--port", true),
    REBUILD_ALL("--rebuild-all", false),
    WATCH ("--watch", false);

    private  final String option;
    private final boolean hasArg;

    Option(final String option, final boolean hasArg) {
        this.option = option;
        this.hasArg = hasArg;
    }

    public String option() {
        return option;
    }

    public boolean hasArg() {
        return hasArg;
    }
}
