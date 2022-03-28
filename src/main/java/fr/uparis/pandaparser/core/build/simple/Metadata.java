package fr.uparis.pandaparser.core.build.simple;

import lombok.*;
import lombok.extern.java.Log;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * metadata
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */

@Log
public class Metadata {

    /* An instance of the parser of common-markdown library */
    private final org.commonmark.parser.Parser parser = Parser.builder().build();

    @Getter
    private HashMap<String,Object> metadata;

    /**
     * Constructor Metadata, get the list of metadata in the header of the fileContent and
     * the content under the header of the file
     * @param fileContent file content
     */
    public Metadata(String fileContent) {
        metadata = new HashMap<>();

        Pattern pattern = Pattern.compile("(?:\\+{3})((?:.|\\n)*?)(?:\\+{3})");
        Matcher m = pattern.matcher(fileContent);
        String data ="";
        while (m.find()) {
            data = m.group(1);
        }

        TomlParseResult result = Toml.parse(data);
        result.errors().forEach(error -> System.err.println(error.toString()));
        Set<String> keys = result.dottedKeySet();
        Iterator<String> itr = keys.iterator();

        while(itr.hasNext()){
            String next = itr.next();
            metadata.put(next, result.get(next).toString());
        }
        // remove the header from the fileContent
        Pattern patternHeader = Pattern.compile("(?:\\+{3})((?:.|\\n)*?)(?:\\+{3})");
        Matcher matchHeader = patternHeader.matcher(fileContent);
        String fileContentNoHeader = matchHeader.replaceAll("");

        //parse the fileContentNoHeader in html for the key "content"
        Node document = parser.parse(fileContentNoHeader);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String newFileContentHTML = renderer.render(document);
        metadata.put("content", newFileContentHTML);
    }

}
