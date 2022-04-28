package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.TestConfig;
import fr.uparis.pandaparser.core.build.template.TemplateProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateProviderTest {

    @Test
    void getEmptyTemplate() {
        assertEquals("", TemplateProvider.getTemplate(TestConfig.EMPTY_TEMPLATE_TEST));
    }

    @Test
    void getSimpleTextTemplate() {
        assertEquals("This is a text", TemplateProvider.getTemplate(TestConfig.SIMPLE_TEXT_TEMPLATE_TEST));
    }

    @Test
    void getMetadataDateTemplate() {
        assertEquals("{{ date }}", TemplateProvider.getTemplate(TestConfig.METADATA_DATE_TEMPLATE_TEST));

    }

    @Test
    void getMetadataTitleTemplate() {
        assertEquals("{{ title }}", TemplateProvider.getTemplate(TestConfig.METADATA_TITLE_TEMPLATE_TEST));
    }

    @Test
    void getIncludePTemplate() {
        assertEquals("{% include \"P\" %}", TemplateProvider.getTemplate(TestConfig.INCLUDE_P_TEMPLATE_TEST));
    }

}