package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.metadata.Metadata;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
class MetadataTest {


    @Test
    void testSimpleMetadata() {
        String bodyContent = " body pillow";
        Metadata test = new Metadata("+++ title = \"test simple metadata help me please.\" +++" + bodyContent, bodyContent);
        assertEquals("test simple metadata help me please.", test.getMetadataMap().get("title"));
        assertEquals(bodyContent, test.getMetadataMap().get("content"));
    }
}