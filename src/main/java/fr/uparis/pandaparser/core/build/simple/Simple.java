package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.*;

import org.w3c.tidy.Tidy;

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

    /**
     * Make the html more readable
     *
     * @param htmlContent the content of the html to beautify
     * @return beautifiedHtml the beautified html
     */
    private String beautifyHtml(String htmlContent) {
        Tidy tidy = new Tidy();
        tidy.setIndentContent(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parse(inputStream, outputStream);
        return outputStream.toString();
    }

    @Override
    public void parse() {
        try {

            String inputFileName = FilesUtils.getHtmlFilenameFromMdFile(FilesUtils.getFileName(input));
            String fileContent = getFileContent(input);
            String html = beautifyHtml(buildHtml(fileContent));
            createFileFromContent(this.output + inputFileName, html);
            log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + inputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
