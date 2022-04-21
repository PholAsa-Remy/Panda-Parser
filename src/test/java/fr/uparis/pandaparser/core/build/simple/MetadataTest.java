package fr.uparis.pandaparser.core.build.simple;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
class MetadataTest {


    @Test
    void testSimpleMetadata() {
        String bodyContent = " body pillow";
        Metadata test = new Metadata("+++ title = \"test simple metadata help me please.\" +++" + bodyContent, bodyContent);
        assertEquals("test simple metadata help me please.", test.getMetadata().get("title"));
        assertEquals(bodyContent, test.getMetadata().get("content"));
    }
}