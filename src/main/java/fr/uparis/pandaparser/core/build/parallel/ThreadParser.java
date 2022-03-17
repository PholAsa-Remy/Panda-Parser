package fr.uparis.pandaparser.core.build.parallel;

import java.util.concurrent.Callable;

/**
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
public record ThreadParser(String input, String output) implements Callable<String> {

    @Override
    public String call() throws Exception {
        return null;
    }
}
