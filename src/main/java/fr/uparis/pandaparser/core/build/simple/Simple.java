package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import java.io.IOException;

import static fr.uparis.pandaparser.utils.FilesUtils.createFileFromContent;
import static fr.uparis.pandaparser.utils.FilesUtils.getFileContent;

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

    /**
     * Parse the header from the file content
     *
     * @param fileContent the content of the file to parse
     * @return head
     */
    private String buildHeader(String fileContent) {
        return "<head><title>Title</title></head>";
    }

    /**
     * Parse the body from the file content
     *
     * @param fileContent the content of the file to parse
     * @return body
     */
    private String buildBody(String fileContent) {
        Node document = parser.parse(fileContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return "<body>" + renderer.render(document) + "</body>";
    }

    /**
     * Parse the html from the file content
     *
     * @param fileContent the content of the file to parse
     * @return html
     */
    private String buildHtml(String fileContent) {
        String header = buildHeader(fileContent);
        String body = buildBody(fileContent);
        return "<!DOCTYPE html><html>" + header + body + "</html>";
    }

    @Override
    public void parse() {
        try{
            String fileContent = getFileContent(input);
            String html = buildHtml(fileContent);
            createFileFromContent(output,html);
            log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + FilesUtils.getFileName(this.input));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
