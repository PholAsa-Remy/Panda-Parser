package fr.uparis.pandaparser.config;


import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.PandaParserPath;

import java.io.File;
import java.util.List;

public class Config {
    public static final String appName = "panda-parser";
    public static List<String> OPTION = List.of("MD");

    public static final PandaParserPath DEFAULT_INPUT = PandaParserPath.builder().hostPath("./").build();
    public static final String DEFAULT_OUTPUT = "_output" + File.separator;
    public static final ParserType DEFAULT_PARSER_TYPE = ParserType.SITE;
    public static final int DEFAULT_MACHINE_JOB = Runtime.getRuntime().availableProcessors();

}

