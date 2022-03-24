package fr.uparis.pandaparser.core.build.simple;

import lombok.*;
import lombok.extern.java.Log;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

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

    @Getter
    private HashMap<String,Object> metadata;

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
            metadata.put(next,result.get(next));
        }
    }

}
