package fr.uparis.pandaparser.config;

import java.io.File;

public class TestConfig {

    public static final String INPUT_TEST_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "builder-test";
    public static final String OUTPUT_TEST_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test-output";
    public static final String MD_FILE = INPUT_TEST_DIR + File.separator + Config.DEFAULT_CONTENT_DIR + Config.DEFAULT_INDEX_NAME;

    public static final String INPUT_UNIT_MD_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "md-file-test" + File.separator + "";
    public static final String EMPTY_MD_TEST = INPUT_UNIT_MD_DIR + "EmptyMarkDown.md";
    public static final String BASIC_MD_TEST = INPUT_UNIT_MD_DIR + "BasicMarkDown.md";
    public static final String LOREM_IPSUM_MD_TEST = INPUT_UNIT_MD_DIR + "LoremIpsumMarkDown.md";

    public static final String TEST_MP3_PATH = "" + File.separator + "static" + File.separator + "01.Ren'ai_Circulation.mp3";
    public static final String TEST_JPG_PATH = "" + File.separator + "static" + File.separator + "koharu.jpg";
    public static final String TEST_CSS_PATH = "" + File.separator + "static" + File.separator + "exampleTest.css";
    public static final String TEST_MD_STATIC_PATH = "" + File.separator + "static" + File.separator + "exampleTest.md";

    public static final String JAVA_EXTENSION_FILE = "NotStaticFile.java";
    public static final String NOT_EXISTING_DIR = "not-existing-dir";
}
