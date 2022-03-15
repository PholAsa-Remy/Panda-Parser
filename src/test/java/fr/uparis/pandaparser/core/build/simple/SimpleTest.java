package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import org.junit.jupiter.api.Test;

import java.net.URL;

class SimpleTest {

    private static final PandaParser.Builder parserBuilder = PandaParser.builder();

    private void testParse (String fileName){
        URL resource = getClass().getClassLoader().getResource(fileName + ".md");
        assert resource != null;
        parserBuilder.setInput(resource.getPath());
        parserBuilder.setOutput("testHTMLBuild");
        PandaParser p = parserBuilder.build();
        p.parse();
    }

    /*Parse Empty MD*/
    @Test
    void testParseEmptyMd(){
        testParse ("EmptyMarkDown");
    }

    /*Parse Simple MD */
    @Test
    void testParseSimpleMd(){
        testParse ("BasicMarkDown");
    }

    /*Parse Lorem Ipsum MD */
    @Test
    void testParseLoremIpsumMd(){
        testParse ("LoremIpsumMarkDown");
    }
}