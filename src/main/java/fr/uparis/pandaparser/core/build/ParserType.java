package fr.uparis.pandaparser.core.build;

import fr.uparis.pandaparser.config.Extension;
import lombok.NonNull;

/**
 * Type de parser
 * <ul>
 *     <li> SIMPLE : Traduction simple de Markdown vers HTML</li>
 *     <li> SITE : Traduction d’un site complet</li>
 * </ul>
 */
public enum ParserType {

    SIMPLE, SITE, SERVE;

    /**
     * Récupérer le type de parser à partir du nom fichier.
     *
     * @param input fichier
     * @return type
     */
    public static ParserType getType(final @NonNull String input) {
        return input.isBlank() || !input.endsWith(Extension.MD.getExtensionName())
                ? SITE
                : SIMPLE;
    }
}
