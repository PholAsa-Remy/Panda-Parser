package fr.uparis.pandaparser.core.build.site;

import org.junit.jupiter.api.Test;

import static fr.uparis.pandaparser.config.TestConfig.JAVA_EXTENSION_FILE;
import static org.junit.jupiter.api.Assertions.*;

class StaticFileTypeTest {

    @Test
    void checkStaticFile_usingJavaExtension_thenFalse() {
        assertFalse(StaticFileType.isStatic(JAVA_EXTENSION_FILE));
    }
}