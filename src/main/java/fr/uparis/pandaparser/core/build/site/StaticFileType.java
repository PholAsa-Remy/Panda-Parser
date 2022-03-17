package fr.uparis.pandaparser.core.build.site;

import fr.uparis.pandaparser.config.Extension;

import java.util.List;
import java.util.Set;

/**
 * les extensions acceptables pour les fichiers statiques
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
public enum StaticFileType {

    IMAGES(Set.of(".png", ".jpg", ".jpeg")),
    VIDEOS(Set.of(".mp4", ".mkv", ".avi", ".mp3")),
    STYLES(Set.of(".css", ".sass", ".scss"));

    /* List des formats */
    private final Set<String> extensions;

    StaticFileType(Set<String> extensions) {
        this.extensions = extensions;
    }

    public Set<String> getExtensions() {
        return extensions;
    }
}
