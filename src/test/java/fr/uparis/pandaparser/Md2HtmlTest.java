package fr.uparis.pandaparser;

import org.junit.jupiter.api.Test;
import org.w3c.tidy.Tidy;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Md2HtmlTest {

    /* Check if a Html is valid using the external library JTidy.
     * JTidy is actually also used to make html more beautiful so should we use it ?
     */
    private boolean checkHtmlIsValid(String htmlContent){
        Tidy checker = new Tidy();
        checker.setErrout(null);
        InputStream stream = new ByteArrayInputStream(htmlContent.getBytes());
        checker.parse(stream,null);
        return (checker.getParseErrors() == 0);
    }

    private void testParsingOfFileWithMd2HTMLCreateValidHTML(String filename){
        Md2Html parser = new Md2Html();
        URL resource = getClass().getClassLoader().getResource(filename);
        assert resource != null;
        assertTrue(checkHtmlIsValid(parser.parse(resource.getPath())));
    }

    @Test
    void parseEmptyMarkDown() {
        testParsingOfFileWithMd2HTMLCreateValidHTML("EmptyMarkDown.md");
    }

    @Test
    void parseBasicMarkDown() {
        testParsingOfFileWithMd2HTMLCreateValidHTML("BasicMarkDown.md");
    }

    @Test
    void parseLoremIpsumMarkDown() {
        testParsingOfFileWithMd2HTMLCreateValidHTML("LoremIpsumMarkDown.md");
    }
}