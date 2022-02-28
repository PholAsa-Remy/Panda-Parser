package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Extension;

import java.util.List;

/**
 * les extensions acceptables pour les fichiers statiques
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
public enum StaticFileType {

    IMAGES(List.of(".png", ".jpg", "jpg")),
    VIDEOS(List.of("mp4", "mkv", "avi")),
    STYLES(List.of(".css", ".sass", ".scss"));

    /* List des formats */
    private final List<String> extensions;

    StaticFileType(List<String> extensions) {
        this.extensions = extensions;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
