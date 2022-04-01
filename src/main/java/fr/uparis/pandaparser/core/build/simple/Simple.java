package fr.uparis.pandaparser.core.build.simple;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.uparis.pandaparser.utils.FilesUtils.*;

/**
 * Translation from markdown to HTML
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Simple extends PandaParser {

    /* An instance of the parser of common-markdown library */
    private final org.commonmark.parser.Parser parser = Parser.builder().build();

    public Simple(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SIMPLE);
    }

    private String getBodyContentWithoutHeaderHTML (String fileContent){

        // remove the header from the fileContent
        String fileContentWithoutHeader = usePatternToReplace(fileContent, "(?:\\+{3})((?:.|\\n)*?)(?:\\+{3})", "");

        //parse the fileContentWithoutHeader in html for the key "content"
        Node document = parser.parse(fileContentWithoutHeader);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    @Override
    public void parse() {
        try {
            String inputFileName = FilesUtils.getHtmlFilenameFromMdFile(FilesUtils.getFileName(input));
            String fileContent = getFileContent(input);

            String bodyContentHTML = getBodyContentWithoutHeaderHTML(fileContent);
            Metadata meta = new Metadata(fileContent, bodyContentHTML);
            //if there is a path for the template in the metadata, take the path otherwise take the default template
            String FileContentAfterTemplate = TemplateProvider.applyTemplate(meta);
            createFileFromContent(this.output + inputFileName, FileContentAfterTemplate);
            log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + inputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
