package fr.uparis.pandaparser;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Md2Html implements IParser{
    private final org.commonmark.parser.Parser parser = Parser.builder().build();

    /**
     * Read the content of a file
     * @param fileName the file name we want to read
     * @return fileContent
     * @throws IOException
     */
    private String readFile (String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Parse the header from the file content
     * @param fileContent the content of the file to parse
     * @return head
     */
    private String buildHeader(String fileContent){
        return "<head><title>Title</title></head>";
    }

    /**
     * Parse the body from the file content
     * @param fileContent the content of the file to parse
     * @return body
     */
    private String buildBody(String fileContent){
        Node document = parser.parse(fileContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return "<body>" + renderer.render(document) + "</body>" ;
    }

    /**
     * Parse the html from the file content
     * @param fileContent the content of the file to parse
     * @return html
     */
    private String buildHtml(String fileContent){
        String header = buildHeader(fileContent);
        String body = buildBody(fileContent);
        return "<!DOCTYPE html><html>" + header + body + "</html>" ;
    }

    /**
     * Parse a file to get a Html content
     * @param fileName the name of the file to parse
     * @return html the content of the html page
     */
    @Override
    public String parse(String fileName) {
        try {
            String fileContent = readFile(fileName);
            return buildHtml(fileContent);
        }catch (IOException e){
            //Do Something ?
        }
        return "";
    }
}
