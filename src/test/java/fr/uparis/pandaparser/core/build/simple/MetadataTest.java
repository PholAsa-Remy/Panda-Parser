package fr.uparis.pandaparser.core.build.simple;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Log
class MetadataTest {


    @Test
    void testSimpleMetadata (){
        Metadata test = new Metadata("+++ title = \"test simple metadata help me please.\" +++ body pillow");
        test.getMetadata().get("title");
        log.info(test.getMetadata().get("title").toString());
    }
}