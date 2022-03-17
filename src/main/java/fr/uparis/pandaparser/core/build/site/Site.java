package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.staticFile.StaticFile;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Set;

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
        try {
            /* parse all files */
            this.parseAllMdFilesToHtml();
            /* move all static files */
            this.moveAllStaticFiles();

        } catch (IOException e) {
            // FIXME
            log.warning("input <" + this.input + "> invalide format");
        }
    }

    /**
     * Parse All MDs files in content directory to html
     */
    private void parseAllMdFilesToHtml() throws IOException {
        this.getAllMdFiles().forEach(inputFilePath -> PandaParser.builder()
                .setInput(inputFilePath)
                .setOutput(this.output)
                .build()
                .parse());
    }

    /**
     * Helper methode to get All MDs filenames from content directory
     *
     * @return set Of filenames
     */
    private Set<String> getAllMdFiles() throws IOException {
        String contentDirectoryPath = this.input + Config.DEFAULT_CONTENT_DIR;
        return FilesUtils.getAllFilesFromDirectory(contentDirectoryPath, Extension.MD);
    }

    private void moveAllStaticFiles() throws IOException{
        try{
            StaticFile.setAllStaticFiles(this.input, this.output);
        } catch (IOException e) {
            log.warning("moveAllStaticFiles failed");
            e.printStackTrace();
        }
    }
}
