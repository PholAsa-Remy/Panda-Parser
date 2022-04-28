package fr.uparis.pandaparser.core.build.simple;

import lombok.Getter;
import lombok.extern.java.Log;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.util.*;

import static fr.uparis.pandaparser.utils.RegExUtils.getMetadataContent;

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
    private HashMap<String, Object> metadata;

    /**
     * Constructor Metadata, get the list of metadata in the header of the fileContent and
     * the content under the header of the file
     *
     * @param fileContent file content
     */
    public Metadata(String fileContent, String fileContentWithoutHeader) {
        metadata = new HashMap<>();
        String data = getMetadataContent(fileContent);
        TomlParseResult result = Toml.parse(data);
        result.errors().forEach(error -> System.err.println(error.toString()));
        Set<String> keys = result.dottedKeySet();
        Iterator<String> itr = keys.iterator();

        while (itr.hasNext()) {
            String next = itr.next();
            if (result.isArray(next)) {
                metadata.put(next, convertToList((TomlArray) Objects.requireNonNull(result.get(next))));
            } else if (result.isTable(next)) {
                metadata.put(next, convertToDictionary((TomlTable) Objects.requireNonNull(result.get(next))));
            } else {
                metadata.put(next, result.get(next));
            }
        }
        metadata.put("content", fileContentWithoutHeader);
    }

    private List<Object> convertToList(TomlArray tomlArray) {
        List<Object> listConvert = tomlArray.toList();
        for (int i = 0; i < listConvert.size(); i++) {
            if (listConvert.get(i) instanceof TomlTable) {
                listConvert.set(i, convertToDictionary((TomlTable) listConvert.get(i)));
            } else if (listConvert.get(i) instanceof TomlArray) {
                listConvert.set(i, convertToList((TomlArray) listConvert.get(i)));
            }
        }
        return listConvert;
    }

    private Map<String, Object> convertToDictionary(TomlTable tomlTable) {
        Map<String, Object> mapConvert = tomlTable.toMap();

        Set<String> setKeys = mapConvert.keySet();
        List<String> listKeys = new ArrayList(setKeys);
        for (int i = 0; i < listKeys.size(); i++) {
            if (mapConvert.get(listKeys.get(i)) instanceof TomlTable) {
                mapConvert.put(listKeys.get(i), convertToDictionary((TomlTable) mapConvert.get(listKeys.get(i))));
            } else if (mapConvert.get(listKeys.get(i)) instanceof TomlArray) {
                mapConvert.put(listKeys.get(i), convertToList((TomlArray) mapConvert.get(listKeys.get(i))));
            }
        }
        return mapConvert;
    }
}
