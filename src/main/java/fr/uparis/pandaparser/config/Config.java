package fr.uparis.pandaparser.config;


import java.util.Arrays;
import java.util.List;

public class Config {
    public static final String appName = "panda-parser";
    public static int TIMEOUT = 100;
    public static List<String> OPTION = List.of("MD");

    public static final int DEFAULT_MACHINE_JOB = Runtime.getRuntime().availableProcessors();
}

