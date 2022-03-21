package fr.uparis.pandaparser.config;


import fr.uparis.pandaparser.core.build.ParserType;

import java.io.File;

public class Config {


    private Config() {
    }


    public static final String APP_NAME = "panda-parser";

    /* Parser configs */
    public static final String DEFAULT_INPUT = "." + File.separator;
    public static final String DEFAULT_OUTPUT = "_output" + File.separator;
    public static final ParserType DEFAULT_PARSER_TYPE = ParserType.SITE;
    public static final int DEFAULT_MACHINE_JOB = Runtime.getRuntime().availableProcessors();
    public static final String DEFAULT_INDEX_NAME = "index.md";

    /* Defaults directories */
    public static final String DEFAULT_CONTENT_DIR = "content" + File.separator;
    public static final String DEFAULT_STATIC_DIR = "static" + File.separator;

    /* Server Compile */
    public static final Integer DEFAULT_PORT = 8080;

    public static final Integer EXIT_SUCCESS = 0;
    public static final int EXIT_FAILURE = 1;

}

