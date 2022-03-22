package fr.uparis.pandaparser.core.build;


import org.junit.jupiter.api.Test;

import static fr.uparis.pandaparser.config.TestConfig.INPUT_TEST_DIR;
import static fr.uparis.pandaparser.config.TestConfig.MD_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Parser Type Tests
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
class ParserTypeTest {

    @Test
    void whenGetParserTypeWithEmpty_thenSiteType() {
        assertEquals(ParserType.SITE, ParserType.getType(""));
    }

    @Test
    void whenGetParserTypeWithDirectory_thenSiteType() {
        assertEquals(ParserType.SITE, ParserType.getType(INPUT_TEST_DIR));
    }

    @Test
    void whenGetParserTypeWithMdFile_thenSimpleType() {
        assertEquals(ParserType.SIMPLE, ParserType.getType(MD_FILE));
    }

    @Test
    void whenGetParserTypeWithBlankFile_thenSiteType() {
        assertEquals(ParserType.SITE, ParserType.getType("\t"));
    }

    @Test
    void whenGetParserTypeWithEmptyFile_thenSiteType() {
        assertEquals(ParserType.SITE, ParserType.getType(""));
    }

    @Test
    void whenGetParserTypeWithSpaceFile_thenSiteType() {
        assertEquals(ParserType.SITE, ParserType.getType(" "));
    }

    @Test
    void whenGetParserTypeWithNullFile_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserType.getType(null));
    }
}