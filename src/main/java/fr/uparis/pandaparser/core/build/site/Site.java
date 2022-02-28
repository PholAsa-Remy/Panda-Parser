package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import fr.uparis.pandaparser.utils.PandaParserPath;
import lombok.extern.java.Log;

import java.io.IOException;

/**
 * Traduction d’un site complet
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Site extends PandaParser {

    public Site(PandaParserPath input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SITE);
    }


    @Override
    public void parse() {
        try {
            md2html();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void md2html() throws IOException {
        FilesUtils.getAllFilesFromDirectory(this.input.path(), Extension.MD)
                .forEach(inputFilePath -> PandaParser.builder()
                        .setInput(this.input.clone(inputFilePath))
                        .setOutput(this.output)
                        .build()
                        .parse());
    }
}
