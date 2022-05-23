package net.sopod.soim.entry.worker;

import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * FutureExecutor
 *
 * @author tmy
 * @date 2022-05-23 23:03
 */
public class FutureExecutor implements Executor {

    private static FutureExecutor INSTANCE;

    private final ThreadPoolExecutor threadPoolExecutor;

    private FutureExecutor() {
        int cpus = Runtime.getRuntime().availableProcessors();
        threadPoolExecutor = new ThreadPoolExecutor(
                2,
                cpus * 4,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new FastThreadLocalThreadFactory("future-exec-%d", Thread.NORM_PRIORITY)
        );
    }

    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }

    public static FutureExecutor getInstance() {
        if (INSTANCE == null) {
            synchronized (FutureExecutor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FutureExecutor();
                }
            }
        }
        return INSTANCE;
    }

}
