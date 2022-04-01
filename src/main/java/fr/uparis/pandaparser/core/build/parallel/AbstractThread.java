package fr.uparis.pandaparser.core.build.parallel;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.concurrent.Callable;

/**
 * Abstract Thread to create thread with input & output fields
 *
 * <p>
 * see also: {@link Callable} {@link ThreadParser} {@link ThreadStaticFilesCopier}
 * </p>
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
@AllArgsConstructor
public abstract class AbstractThread implements Callable<String> {
    protected final String input;
    protected final String output;
}
