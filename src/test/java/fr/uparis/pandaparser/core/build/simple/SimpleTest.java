package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testParseEmptyMd(){
        testParse ("EmptyMarkDown");
    }

    @Test
    void testParseSimpleMd(){
        testParse ("BasicMarkDown");
    }

    @Test
    void testParseLoremIpsumMd(){
        testParse ("LoremIpsumMarkDown");
    }
}