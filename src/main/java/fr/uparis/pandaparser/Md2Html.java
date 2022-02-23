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

    private String readFile (String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private String buildHeader(String fileContent){
        return "<head><title>Title</title></head>";
    }

    private String buildBody(String fileContent){
        Node document = parser.parse(fileContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return "<body>" + renderer.render(document) + "</body>" ;
    }

    private String buildHtml(String fileContent){
        String header = buildHeader(fileContent);
        String body = buildBody(fileContent);
        return "<!DOCTYPE html><html>" + header + body + "</html>" ;
    }
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
