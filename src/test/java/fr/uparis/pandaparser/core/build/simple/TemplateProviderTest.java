package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.TestConfig;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TemplateProviderTest {

    @Test
    void getEmptyTemplate () throws IOException {
        assertEquals("",TemplateProvider.getTemplate(TestConfig.EMPTY_TEMPLATE_TEST));
    }

    @Test
    void getSimpleTextTemplate () throws IOException {
        assertEquals("This is a text",TemplateProvider.getTemplate(TestConfig.SIMPLE_TEXT_TEMPLATE_TEST));
    }

    @Test
    void getMetadataDateTemplate () throws IOException {
        assertEquals("{{ metadata.date }}",TemplateProvider.getTemplate(TestConfig.METADATA_DATE_TEMPLATE_TEST));

    }

    @Test
    void getMetadataTitleTemplate () throws IOException {
        assertEquals("{{ metadata.title }}",TemplateProvider.getTemplate(TestConfig.METADATA_TITLE_TEMPLATE_TEST));
    }

    @Test
    void getIncludePTemplate () throws IOException {
        assertEquals("{% include 'P' %}",TemplateProvider.getTemplate(TestConfig.INCLUDE_P_TEMPLATE_TEST));
    }

    @Test
    void getNotExistingTemplate () {
        assertThrows(IOException.class,()->{ TemplateProvider.getTemplate(TestConfig.NOT_EXISTING_TEMPLATE_TEST); });
    }

}