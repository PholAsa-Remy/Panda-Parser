package fr.uparis.pandaparser.core.commande;

/**
 * Les commandes d'application
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Fev 2022
 */
public enum Command {

    BUILD("build"),
    SERVE("serve"),
    HELP("help");

    private final String commande;

    Command(String commande) {
        this.commande = commande;
    }

    public String get() {
        return commande;
    }
}
