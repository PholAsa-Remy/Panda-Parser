package fr.uparis.pandaparser.core.build.site;


import fr.uparis.pandaparser.core.build.PandaParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.uparis.pandaparser.config.TestConfig.*;
class SiteTest {

    private PandaParser pandaParser;


    @BeforeEach
    void setUp() {
        pandaParser = PandaParser.builder().setInput(INPUT_TEST_DIR).build();
    }

    @Test
    void whenParseValideDirectory_thenCorrect() {
        this.pandaParser.parse();
    }

    @Test
    void whenParseNotValideDirectory_thenErrorMessage() {
        PandaParser.builder().setInput("").build().parse();
    }
}