package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.PandaParserPath;
import lombok.extern.java.Log;

/**
 * Traduction simple de Markdown vers HTML
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Simple extends PandaParser {

    public Simple(PandaParserPath input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SIMPLE);
    }

    @Override
    public void parse() {
        log.info("MD 2 HTML parser");
        // TODO implement this methode
    }
}
