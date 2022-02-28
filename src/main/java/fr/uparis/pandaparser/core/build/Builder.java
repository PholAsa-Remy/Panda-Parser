package fr.uparis.pandaparser.core.build;

import fr.uparis.pandaparser.utils.PandaParserPath;

/**
 * Interface Builder
 *
 * <p>
 * Contient des méthodes spécialisées, pour créer les différentes parties
 * des objets du produit.
 * </p>
 *
 * @author Panda-parser group
 * @version 1.0.0
 * @since Frv 2022
 */
public interface Builder {

    /**
     * Spécifier l'arborescence à traiter différente du répertoire courant
     *
     * @param input chemin
     * @return le builder.
     */
    Builder setInput(final String input);

    /**
     * Spécifier l'arborescence à traiter différente du répertoire courant
     *
     * @param pandaParserPath chemin vers l'input
     * @return le builder
     */
    Builder setInput(final PandaParserPath pandaParserPath);

    /**
     * Spécifier l'arborescence ou stocke les résultats de la traduction.
     * (= _output par default)
     *
     * @param output chemin vers le dossier de la sortie
     * @return le builder
     */
    Builder setOutput(final String output);

    /**
     * Specifier le nombre de fils d’exécution alloués à la traduction
     * (= e fils d’exécution que la machine a de cœurs.)
     *
     * @param jobs nombre de cœurs.
     * @return le builder
     */
    Builder setJobs(final int jobs);

    /**
     * Activer la Recompilation automatique.
     * <p>
     * surveille  les  fichiers  sources  et recompile automatiquement le
     * site à chaque modification, avec recalcul minimal. Ce comportement
     * est intégré par défaut à la commande serve.
     * </p>
     *
     * @return le builder
     */
    Builder isWatched();
}
