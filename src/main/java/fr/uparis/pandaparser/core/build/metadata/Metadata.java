package fr.uparis.pandaparser.core.build.metadata;

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
    private final HashMap<String, Object> metadataMap;

    /**
     * Constructor Metadata, get the list of metadata in the header of the fileContent and
     * the content under the header of the file
     *
     * @param fileContent file content
     */
    public Metadata(String fileContent, String fileContentWithoutHeader) {
        metadataMap = new HashMap<>();
        String data = getMetadataContent(fileContent);
        TomlParseResult result = Toml.parse(data);
        result.errors().forEach(error -> System.err.println(error.toString()));
        Set<String> keys = result.dottedKeySet();

        for (String next : keys) {
            if (result.isArray(next)) {
                metadataMap.put(next, convertToList((TomlArray) Objects.requireNonNull(result.get(next))));
            } else if (result.isTable(next)) {
                metadataMap.put(next, convertToDictionary((TomlTable) Objects.requireNonNull(result.get(next))));
            } else {
                metadataMap.put(next, result.get(next));
            }
        }
        metadataMap.put("content", fileContentWithoutHeader);
    }

    private List<Object> convertToList(TomlArray tomlArray) {
        List<Object> listConvert = tomlArray.toList();
        for (int i = 0; i < listConvert.size(); i++) {
            Object object = listConvert.get(i);
            if (object instanceof TomlTable tomlTable) {
                listConvert.set(i, convertToDictionary(tomlTable));
            } else if (object instanceof TomlArray castedTomlArray) {
                listConvert.set(i, convertToList(castedTomlArray));
            }
        }
        return listConvert;
    }

    private Map<String, Object> convertToDictionary(TomlTable tomlTable) {
        Map<String, Object> mapConvert = tomlTable.toMap();

        Set<String> setKeys = mapConvert.keySet();
        List<String> listKeys = new ArrayList<>(setKeys);
        for (String listKey : listKeys) {
            Object object = mapConvert.get(listKey);
            if (object instanceof TomlTable castedTomlTable) {
                mapConvert.put(listKey, convertToDictionary(castedTomlTable));
            } else if (object instanceof TomlArray tomlArray) {
                mapConvert.put(listKey, convertToList(tomlArray));
            }
        }
        return mapConvert;
    }
}
