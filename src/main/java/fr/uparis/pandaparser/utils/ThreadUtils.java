package fr.uparis.pandaparser.utils;

import lombok.extern.java.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class ThreadUtils {

    private ThreadUtils() {
    }

    public static void logFuture(final Future<String> future) throws ExecutionException, InterruptedException {
        log.info(future.get());
    }

    public static void logAllFutures(final List<Future<String>> futures) throws ExecutionException, InterruptedException {
        for (Future<String> future : futures)
            logFuture(future);

    }

}
