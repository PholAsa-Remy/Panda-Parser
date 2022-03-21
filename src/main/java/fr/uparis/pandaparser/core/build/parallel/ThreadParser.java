package fr.uparis.pandaparser.core.build.parallel;

import fr.uparis.pandaparser.core.build.PandaParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.util.concurrent.Callable;

/**
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class ThreadParser extends AbstractThread implements Callable<String> {

    public ThreadParser(@NonNull final String input, @NonNull final String output) {
        super(input, output);
    }

    @Override
    public String call() throws Exception {
        PandaParser.builder().setInput(input).setOutput(output).build().parse();
        return Thread.currentThread().getName() + " : input : " + this.input + " to out: " + this.output;
    }
}
