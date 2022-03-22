package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.TestConfig;
import fr.uparis.pandaparser.core.build.PandaParser;
import org.junit.jupiter.api.Test;

class SimpleTest {

    private static final PandaParser.Builder parserBuilder = PandaParser.builder();

    private void testParse(String fileName) {
        parserBuilder.setInput(fileName);
        parserBuilder.setOutput(TestConfig.OUTPUT_TEST_DIR);
        PandaParser p = parserBuilder.build();
        p.parse();
    }

    /*Parse Empty MD*/
    @Test
    void testParseEmptyMd() {
        testParse(TestConfig.EMPTY_MD_TEST);
    }

    /*Parse Simple MD */
    @Test
    void testParseSimpleMd() {
        testParse(TestConfig.BASIC_MD_TEST);
    }

    /*Parse Lorem Ipsum MD */
    @Test
    void testParseLoremIpsumMd() {
        testParse(TestConfig.LOREM_IPSUM_MD_TEST);
    }
}