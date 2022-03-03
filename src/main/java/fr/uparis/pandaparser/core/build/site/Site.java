package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
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

    public Site(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SITE);
    }


    @Override
    public void parse() {
        log.info("Build site parser: input " + this.input);
        try {
            md2html();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void md2html() throws IOException {
        FilesUtils.getAllFilesFromDirectory(this.input + Config.CONTENT, Extension.MD)
                .forEach(inputFilePath -> PandaParser.builder()
                        .setInput(inputFilePath)
                        .setOutput(this.output)
                        .build()
                        .parse());
    }
}
