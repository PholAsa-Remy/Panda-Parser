package fr.uparis.pandaparser.build.site;

import fr.uparis.pandaparser.build.PandaParser;
import fr.uparis.pandaparser.build.ParserType;
import lombok.extern.java.Log;

/**
 * Traduction d’un site complet
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Site extends PandaParser {

    public Site(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SITE);
    }


    @Override
    public void parse() {
        log.info("Build sit parser");
        // TODO implement this methode
    }
}
