package fr.uparis.pandaparser.core.build.simple;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * metadata
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Metadata {
    private final String date;
    private final String author;
    private final String title;
    private final boolean draft;
}
