package fr.uparis.pandaparser.core.build;

import fr.uparis.pandaparser.config.Extension;

/**
 * Type de parser
 * <ul>
 *     <li> SIMPLE : Traduction simple de Markdown vers HTML</li>
 *     <li> SITE : Traduction d’un site complet</li>
 * </ul>
 */
public enum ParserType {

    SIMPLE, SITE;

    /**
     * Récupérer le type de parser à partir du nom fichier.
     *
     * @param input fichier
     * @return type
     */
    public static ParserType getType(String input) {
        return (input == null || input.isBlank() || !input.endsWith(Extension.MD.getExtensionName()))
                ? SITE
                : SIMPLE;
    }
}
