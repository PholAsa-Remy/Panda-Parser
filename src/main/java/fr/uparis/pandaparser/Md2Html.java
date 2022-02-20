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

    @Override
    public String parse(String fileName) {
        try {
            String fileContent = readFile(fileName);
            Node document = parser.parse(fileContent);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            return renderer.render(document);
        }catch (IOException e){
            //Do Something ?
        }
        return "";
    }
}
