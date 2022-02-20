package fr.uparis.pandaparser;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Md2HtmlTest {

    @Test
    void parseEmptyMarkDown() {
        Md2Html parser = new Md2Html();
        URL basicMarkDownRessource = getClass().getClassLoader().getResource("EmptyMarkDown.md");
        assertEquals("",parser.parse(basicMarkDownRessource.getPath()));
    }

    @Test
    void parseBasicMarkDown() {
        Md2Html parser = new Md2Html();
        URL basicMarkDownRessource = getClass().getClassLoader().getResource("BasicMarkDown.md");
        assertEquals("<p>This is a basic markdown</p>\n",parser.parse(basicMarkDownRessource.getPath()));
    }
}