package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.TestConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateProviderTest {

    @Test
    void getEmptyTemplate() throws IOException {
        assertEquals("", TemplateProvider.getTemplate(TestConfig.EMPTY_TEMPLATE_TEST));
    }

    @Test
    void getSimpleTextTemplate() throws IOException {
        assertEquals("This is a text", TemplateProvider.getTemplate(TestConfig.SIMPLE_TEXT_TEMPLATE_TEST));
    }

    @Test
    void getMetadataDateTemplate() throws IOException {
        assertEquals("{{ date }}", TemplateProvider.getTemplate(TestConfig.METADATA_DATE_TEMPLATE_TEST));

    }

    @Test
    void getMetadataTitleTemplate() throws IOException {
        assertEquals("{{ title }}", TemplateProvider.getTemplate(TestConfig.METADATA_TITLE_TEMPLATE_TEST));
    }

    @Test
    void getIncludePTemplate() throws IOException {
        assertEquals("{% include \"P\" %}", TemplateProvider.getTemplate(TestConfig.INCLUDE_P_TEMPLATE_TEST));
    }

}