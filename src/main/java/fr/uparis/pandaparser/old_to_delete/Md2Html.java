package fr.uparis.pandaparser.old_to_delete;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Md2Html {
    private final org.commonmark.parser.Parser parser = Parser.builder().build();

    /**
     * Read the content of a file
     *
     * @param filePath the file name we want to read
     * @return fileContent
     */
    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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


    public String parse(String fileName) {
        String fileContent = readFile(fileName);
        return buildHtml(fileContent);
    }
}
