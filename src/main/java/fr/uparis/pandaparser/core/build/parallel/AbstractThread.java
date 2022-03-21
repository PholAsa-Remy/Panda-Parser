package fr.uparis.pandaparser.core.build.parallel;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public abstract class  AbstractThread implements Callable<String> {
    protected final String input;
    protected final String output;
}
